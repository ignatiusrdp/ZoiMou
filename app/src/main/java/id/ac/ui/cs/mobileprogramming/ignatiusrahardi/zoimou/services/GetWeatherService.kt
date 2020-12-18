package id.ac.ui.cs.mobileprogramming.ignatiusrahardi.zoimou.services

import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.IBinder
import android.os.Looper
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.core.app.ActivityCompat
import androidx.lifecycle.LifecycleService
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.gms.location.*
import id.ac.ui.cs.mobileprogramming.ignatiusrahardi.zoimou.AppDatabase
import id.ac.ui.cs.mobileprogramming.ignatiusrahardi.zoimou.broadcastreceivers.NotificationBroadcastReceiver
import id.ac.ui.cs.mobileprogramming.ignatiusrahardi.zoimou.data.Weather
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class GetWeatherService : LifecycleService() {

    private val job = CoroutineScope(IO)
    private var isActive = true
    private val API_ID = "4ae8ee65631e1ab1295a19cc7cefe9b9"
    private lateinit var db: AppDatabase
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    val notificationBroadcastReceiver: NotificationBroadcastReceiver = NotificationBroadcastReceiver()
    val notificationIntent: Intent = Intent("id.ac.ui.cs.mobileprogramming.ignatiusrahardi.zoimou.WeatherNotification")

    external fun getTime(): String

    companion object {
        // Used to load the 'native-lib' library on application startup.
        init {
            System.loadLibrary("native-lib")
        }
    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        val weatherFilter = IntentFilter("id.ac.ui.cs.mobileprogramming.ignatiusrahardi.zoimou.WeatherNotification")
        registerReceiver(notificationBroadcastReceiver, weatherFilter)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        db = AppDatabase.getDatabase(application)

        if (checkPermission()){
            fusedLocationProviderClient.lastLocation.addOnCompleteListener { task ->
                var location: Location = task.result
                job.launch {
                    coroutine(location.latitude, location.longitude)
                }

            }
        }

        return START_STICKY
    }
    private fun checkPermission():Boolean{
        if(
            ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
        ){
            return true
        }
        return false
    }

    private suspend fun coroutine(lat: Double, lon: Double){
        val queue = Volley.newRequestQueue(this)
        val url = "https://api.openweathermap.org/data/2.5/weather?lat=$lat&lon=$lon&appid=$API_ID&units=metric"
        while(isActive){
            val currentTime = getTime()

            val stringRequest = StringRequest(
                Request.Method.GET, url,
                { response ->
                    val jsonObj = JSONObject(response)

                    val main = jsonObj.getJSONObject("main")
                    val sys = jsonObj.getJSONObject("sys")
                    val weatherObj = jsonObj.getJSONArray("weather").getJSONObject(0)
                    val updatedAt:Long = jsonObj.getLong("dt")

                    val address = jsonObj.getString("name")+", "+sys.getString("country")

                    val time = "Updated at: $currentTime"
                    val temp = main.getString("temp")+"°C"
                    val feelsLike= main.getString("feels_like")+"°C"
                    val weatherMain = weatherObj.getString("main")
                    val weatherDescription = weatherObj.getString("description")
                    val weatherIcon = weatherObj.getString("icon")
                    val weatherUrl = "http://openweathermap.org/img/w/$weatherIcon.png;"
                    val weather = Weather(0,weatherMain, weatherDescription,temp,feelsLike,time,address, weatherUrl)
                    job.launch {
                        insertData(weather)
                    }
                },
                {
                    err ->

                    Toast.makeText(this,"Error when gathering weather data: $err", Toast.LENGTH_SHORT).show()
                })
                queue.add(stringRequest)
            delay(18000000)
        }

    }

    private suspend fun insertData(weather: Weather){

        if(db.weatherDao().checkIfExist(0)){
            sendBroadcast(notificationIntent)
            db.weatherDao().updateWeather(weather)

        } else {
            db.weatherDao().addWeather(weather)
        }
    }



    override fun onDestroy() {
        super.onDestroy()
        isActive = false
        job.cancel()
        unregisterReceiver(notificationBroadcastReceiver)
    }

    @Nullable
    override fun onBind(intent: Intent): IBinder? {
        super.onBind(intent)
        return null
    }
}
