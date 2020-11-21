package id.ac.ui.cs.mobileprogramming.ignatiusrahardi.zoimou.broadcastreceivers

import android.app.Notification
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.navigation.NavDeepLinkBuilder
import id.ac.ui.cs.mobileprogramming.ignatiusrahardi.zoimou.R
import id.ac.ui.cs.mobileprogramming.ignatiusrahardi.zoimou.activity.MainActivity
import id.ac.ui.cs.mobileprogramming.ignatiusrahardi.zoimou.notifications.NotificationChannels

class NotificationBroadcastReceiver : BroadcastReceiver(){
    override fun onReceive(context: Context, intent: Intent?) {
        sendNotification(context)
    }

    fun sendNotification(context: Context){
        val pendingIntent: PendingIntent = NavDeepLinkBuilder(context)
            .setComponentName(MainActivity::class.java)
            .setGraph(R.navigation.mobile_navigation)
            .setDestination(R.id.navigation_weather)
            .createPendingIntent()
        val builder : Notification = NotificationCompat.Builder(context, NotificationChannels.WEATHER_CHANNEL_ID)
            .setSmallIcon(R.drawable.weather_placeholder)
            .setContentTitle("There is an update in your weather info!")
            .setContentText("The Weather data is updated! check it out")
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .build()
        with(NotificationManagerCompat.from(context)){
            notify(NotificationChannels.WEATHER_NOTIFICATION_ID, builder)
        }
    }


}