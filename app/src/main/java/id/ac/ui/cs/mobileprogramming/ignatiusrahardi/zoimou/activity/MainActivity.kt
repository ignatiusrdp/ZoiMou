package id.ac.ui.cs.mobileprogramming.ignatiusrahardi.zoimou.activity

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import id.ac.ui.cs.mobileprogramming.ignatiusrahardi.zoimou.R
import id.ac.ui.cs.mobileprogramming.ignatiusrahardi.zoimou.services.GetWeatherService
import id.ac.ui.cs.mobileprogramming.ignatiusrahardi.zoimou.viewmodels.WeatherViewModel

class MainActivity : AppCompatActivity() {


    private val REQUEST_CODE_LOCATION = 69
    private lateinit var weatherViewModel: WeatherViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        weatherViewModel =
            ViewModelProvider(this).get(WeatherViewModel::class.java)

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.navigation_notes,
            R.id.navigation_todo,
            R.id.navigation_images,
            R.id.navigation_weather
        ))

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        when{
            checkPermission() -> {
               if (!isMyServiceRunning(GetWeatherService::class.java)){
                   if(isLocationEnabled()){
                       weatherViewModel.deleteWeather()
                       getWeatherData()
                   } else {
                       Toast.makeText(this,"Please enable your location service", Toast.LENGTH_SHORT).show()
                   }
               }
            }
            else -> {
                grantPermission()
            }
        }

    }
    override fun onStop() {
        super.onStop()
    }

    override fun onStart() {
        super.onStart()
    }


    override fun onDestroy() {
        super.onDestroy()
        val intent = Intent(this, GetWeatherService::class.java)
        stopService(intent)
    }


    fun checkPermission():Boolean{
        if(
            ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) === PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_COARSE_LOCATION) === PackageManager.PERMISSION_GRANTED
        ){
            return true
        }
        return false
    }
    private fun grantPermission(){
        ActivityCompat.requestPermissions(
            this,
            arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_CODE_LOCATION
        )
    }

    private fun isLocationEnabled():Boolean {
        var locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER)
    }

    fun getWeatherData(){
        val intent = Intent(this, GetWeatherService::class.java)
        startService(intent)
    }

    fun isMyServiceRunning(serviceClass: Class<*>): Boolean {
        val manager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        return manager.getRunningServices(Integer.MAX_VALUE)
            .any { it.service.className == serviceClass.name }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if(requestCode == REQUEST_CODE_LOCATION){
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getWeatherData()
            } else {
                Toast.makeText(this,"You wont be able to access the weather function in this app", Toast.LENGTH_LONG).show()
            }
        }
    }


}