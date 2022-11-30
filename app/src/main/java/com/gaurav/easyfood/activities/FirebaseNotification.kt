package com.gaurav.easyfood.activities

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.gaurav.easyfood.R
import com.gaurav.easyfood.fragments.HomeFragment
import com.gaurav.easyfood.fragments.HomeFragment.Companion.MEAL_ID
import com.gaurav.easyfood.fragments.HomeFragment.Companion.MEAL_NAME
import com.gaurav.easyfood.fragments.HomeFragment.Companion.MEAL_THUMB
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


class FirebaseNotification : FirebaseMessagingService() {
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        val title = remoteMessage.notification!!.title
        val text = remoteMessage.notification!!.body
        val channelId = "HEADS_UP_NOTIFICATION"
        NotificationChannel(channelId, "Heads Up Notification", NotificationManager.IMPORTANCE_DEFAULT)

        val builder: NotificationCompat.Builder = NotificationCompat.Builder(this, "MyNotification")
            .setContentTitle(title)
            .setSmallIcon(R.mipmap.applogo)
            .setAutoCancel(true) //.setColor(ContextCompat.getColor(this, R.color.color))
            .setContentText(text)


        val manager = NotificationManagerCompat.from(this)
        manager.notify(0, builder.build())

        val mealName= remoteMessage.data["MEAL_NAME"]
        val mealId= remoteMessage.data["MEAL_ID"]
        val mealThumb= remoteMessage.data["MEAL_THUMB"]
        Log.d("notificationData" ,mealName.toString()+""+mealId.toString()+""+mealThumb.toString())
            val intent = Intent(applicationContext, MealActivity::class.java)
            intent.putExtra(MEAL_ID, mealId)
            intent.putExtra(MEAL_NAME, mealName)
            intent.putExtra(MEAL_THUMB, mealThumb)
            startActivity(intent)

    }
}