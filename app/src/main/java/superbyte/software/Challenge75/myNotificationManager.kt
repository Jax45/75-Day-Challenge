package superbyte.software.Challenge75

import android.app.AlarmManager
import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService
import java.util.*

class myNotificationManager(private val ctx: Context,
                            private val alarmManager: AlarmManager,
                            private val sharedPreferences: SharedPreferences,
                            private val nightNotify: Boolean,
                            private val noonNotify: Boolean) {

    val NOTIFICATION_CHANNEL_ID = "10001" ;
    private val default_notification_channel_id = "default"

    fun sendNightNotify(forToday: Boolean){
        if (nightNotify){
            val alarmStartTime: Calendar = Calendar.getInstance()
            val now: Calendar = Calendar.getInstance()
            alarmStartTime.set(Calendar.HOUR_OF_DAY, 17)
            alarmStartTime.set(Calendar.MINUTE, 0)
            alarmStartTime.set(Calendar.SECOND, 0)
            if (now.after(alarmStartTime) || !forToday) {
                alarmStartTime.add(Calendar.DATE, 1)
            }
            scheduleNotification(
                    getNotification(
                            "You have not completed your Challenges for the day.",
                            "5:00PM Reminder"
                    ), alarmStartTime.timeInMillis
            )
        }
    }
    fun sendNoonNotify(forToday: Boolean){
        if(noonNotify) {
            val alarmStartTime: Calendar = Calendar.getInstance()
            val now: Calendar = Calendar.getInstance()
            alarmStartTime.set(Calendar.HOUR_OF_DAY, 12)
            alarmStartTime.set(Calendar.MINUTE, 0)
            alarmStartTime.set(Calendar.SECOND, 0)
            if (now.after(alarmStartTime) || !forToday) {
                alarmStartTime.add(Calendar.DATE, 1)
            }
            scheduleNotification(
                    getNotification(
                            "Halfway through the day! Make sure to drink water, you should be at 64oz.",
                            "12:00PM Reminder"
                    ), alarmStartTime.timeInMillis
            )
        }
    }
    fun dayFinished(){
        sendNightNotify(false)
        sendNoonNotify(false)
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DATE,1)
        sharedPreferences.edit().putLong("time",calendar.timeInMillis).apply()

    }

    private fun getNotification(content: String, noteTitle: String): Notification {
        val builder = NotificationCompat.Builder(ctx, default_notification_channel_id)
        builder.setContentTitle(noteTitle)
        builder.setContentText(content)
        builder.setSmallIcon(R.drawable.ic_launcher_foreground)
        builder.setAutoCancel(true)
        builder.setChannelId(NOTIFICATION_CHANNEL_ID)
        return builder.build()
    }

    private fun scheduleNotification(notification: Notification, delay: Long) {
        val notificationIntent = Intent(ctx, MyNotificationPublisher::class.java)
        notificationIntent.putExtra(MyNotificationPublisher.NOTIFICATION_ID, 1)
        notificationIntent.putExtra(MyNotificationPublisher.NOTIFICATION, notification)
        val pendingIntent = PendingIntent.getBroadcast(
                ctx,
                0,
                notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
        )
        alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                delay,
                AlarmManager.INTERVAL_DAY,
                pendingIntent
        );
    }
}