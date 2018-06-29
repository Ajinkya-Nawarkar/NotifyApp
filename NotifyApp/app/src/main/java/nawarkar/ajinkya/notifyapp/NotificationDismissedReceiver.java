package nawarkar.ajinkya.notifyapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class NotificationDismissedReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        int notificationId = intent.getExtras().getInt("com.my.app.notificationId");
        /* Your code to handle the event here */
        Toast.makeText(context, "Intent Detected.", Toast.LENGTH_LONG).show();
        System.out.println("DIIIISSSMMMIISSEEDD");
    }

}
