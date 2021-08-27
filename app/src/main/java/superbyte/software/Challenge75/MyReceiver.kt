package superbyte.software.Challenge75

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent


class MyReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        val intent1 = Intent(context, NotificationService::class.java)
        context.startService(intent1)
    }
}