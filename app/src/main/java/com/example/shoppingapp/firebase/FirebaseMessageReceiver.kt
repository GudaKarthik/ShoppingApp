package com.example.shoppingapp.firebase

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.widget.RemoteViews
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.test.core.app.ApplicationProvider
import com.example.shoppingapp.R
import com.example.shoppingapp.ui.DashboardActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FirebaseMessageReceiver : FirebaseMessagingService() {

    var CHANNEL_ID = "2"
    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        if (message.notification != null){
            showNotification(
                message.notification!!.title.toString(),
                message.notification!!.body.toString()
            )
        }
    }

    private fun getCustomDesign(title: String,message: String) : RemoteViews{
        val mremoteViews = RemoteViews(ApplicationProvider.getApplicationContext<Context>().packageName,R.layout.notification)
        mremoteViews.setTextViewText(R.id.titleS,title)
        mremoteViews.setTextViewText(R.id.messageS,message)
        return mremoteViews
    }

    fun showNotification(title : String,message : String){
        notificationChannel()
        var intent = Intent(this,DashboardActivity::class.java)
        var channel_id = "notification_channel"
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val notificationLayout = RemoteViews(packageName, R.layout.notification)
        notificationLayout.setTextViewText(R.id.titleS,title)
        notificationLayout.setTextViewText(R.id.messageS,message)

        var pendingintent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_IMMUTABLE)
        var builder : Notification = NotificationCompat.Builder(ApplicationProvider.getApplicationContext(),CHANNEL_ID)
            .setSmallIcon(R.drawable.fav_icon)
            .setAutoCancel(true)
            .setVibrate(longArrayOf(
                1000, 1000, 1000,
                1000, 1000
            ))
            .setOnlyAlertOnce(true)
            .setContentIntent(pendingintent)
            .setCustomContentView(notificationLayout)
            .build()

        var nManager = NotificationManagerCompat.from(this)
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {

        }
        nManager.notify(1,builder)
    }

    private fun notificationChannel(){
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Create Notification Channel for Android O+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Shop App",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }
    }
}