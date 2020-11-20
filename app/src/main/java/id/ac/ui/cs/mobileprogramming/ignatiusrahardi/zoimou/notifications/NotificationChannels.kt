package id.ac.ui.cs.mobileprogramming.ignatiusrahardi.zoimou.notifications

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import id.ac.ui.cs.mobileprogramming.ignatiusrahardi.zoimou.R

class NotificationChannels: Application() {


    companion object {
        const val WEATHER_NOTIFICATION_ID : Int = 0
        const val WEATHER_CHANNEL_ID : String = "weather"
    }

    override fun onCreate() {
        super.onCreate()
        createNotificationChannels()
    }

    private fun createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val weatherChannel = NotificationChannel(WEATHER_CHANNEL_ID, "Weather Channel", NotificationManager.IMPORTANCE_LOW ). apply {
                description = "Weather Channel!"
            }
            val notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(weatherChannel)
        }
    }




}