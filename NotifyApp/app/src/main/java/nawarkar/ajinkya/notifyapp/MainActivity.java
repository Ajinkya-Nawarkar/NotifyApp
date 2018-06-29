package nawarkar.ajinkya.notifyapp;

import android.annotation.TargetApi;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

@TargetApi(26)
public class MainActivity extends AppCompatActivity {

    NotificationCompat.Builder notification;
    private static final int uniqueID = 45612;

    public static final String NOTIFICATION_CHANNEL_ID = "notification_channel";
    //Notification Channel

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "notificationChannel", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        notification = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
        notification.setAutoCancel(true);

    }

    public void notifyBtnClicked(View view)
    {
        int notify_limit = 5;
        String URL = "http://172.22.112.89:5002/notifications";
        String mResponse = "";
        String title = "Error: No Notification Folks.";
        String headline = "Oops";

        Toast toast = Toast.makeText(getApplicationContext(), "Notifying started!", Toast.LENGTH_LONG);
        toast.show();

        for (int i =0; i< notify_limit; i++) {
            get_GetJSON(URL);
            SharedPreferences m = PreferenceManager.getDefaultSharedPreferences(this);
            mResponse = m.getString("Response", "");

            try {
                JSONObject reader = new JSONObject(mResponse);
                title = reader.getString("origin");
                headline = reader.getString("headline");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            //Build Notification
            notification.setSmallIcon(R.mipmap.ic_launcher)
                    .setTicker("This is ticker")
                    .setWhen(System.currentTimeMillis())
                    .setContentTitle(title)
                    .setContentText(headline)
                    .setDeleteIntent(createOnDismissedIntent(this, uniqueID, mResponse));

            //Builds the notifications and issues it
            NotificationManager nm = (NotificationManager) this.getSystemService(NOTIFICATION_SERVICE);
            nm.notify(uniqueID, notification.build());

            Intent finalIntent = new Intent(this, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, finalIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            notification.setContentIntent(pendingIntent);

            try {
                Thread.sleep(3000);                 //1000 milliseconds is one second.
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }

        }
        toast = Toast.makeText(getApplicationContext(), "Notifying completed!", Toast.LENGTH_LONG);
        toast.show();

    }

    private PendingIntent createOnDismissedIntent(Context context, int notificationId, String mResponse) {
        Intent intent = new Intent(context, NotificationDismissedReceiver.class);
        intent.putExtra("com.my.app.notificationId", notificationId);
        try {
            JSONObject reader = new JSONObject(mResponse);

            intent.putExtra("origin", reader.getString("origin"));
            intent.putExtra("timestamp", reader.getString("timestamp"));
            intent.putExtra("headline", reader.getString("headline"));
            intent.putExtra("uuid", reader.getString("uuid"));
            intent.putExtra("classification", reader.getString("classification"));
            System.out.println("Really HAHAHHAHAHAHAHAHAHAHHAHAAHAHAHHA");
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            System.out.println("HAHAHHAHAHAHAHAHAHAHHAHAAHAHAHHA");
        }

        PendingIntent pendingIntent =
                PendingIntent.getBroadcast(context.getApplicationContext(),
                        notificationId, intent, 0);
        return pendingIntent;
    }

    private void sharedResponse(String response) {
        SharedPreferences m = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = m.edit();
        editor.putString("Response", response);
        editor.commit();
    }

    public void get_GetJSON(String URL) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest getRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        sharedResponse(response.toString());
                        Log.e("Rest response", response.toString());

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Rest response", error.toString());
                    }
                }
        );

        requestQueue.add(getRequest);
    }

    public void start_notifyBtnClicked(View view)
    {
        int notify_limit = 1;
        String URL = "http://172.22.112.89:5002/notifications";
        String mResponse = "";
        String title = "Error: No Notification Folks.";
        String headline = "Oops";

        Toast toast = Toast.makeText(getApplicationContext(), "Notifying started!", Toast.LENGTH_LONG);
        toast.show();

            get_GetJSON(URL);
            SharedPreferences m = PreferenceManager.getDefaultSharedPreferences(this);
            mResponse = m.getString("Response", "");

            try {
                JSONObject reader = new JSONObject(mResponse);
                title = reader.getString("origin");
                headline = reader.getString("headline");
            }
            catch(Exception e){
                System.out.println(e.getMessage());
            }
            //Build Notification
            notification.setSmallIcon(R.mipmap.ic_launcher)
                        .setTicker("This is ticker")
                        .setWhen(System.currentTimeMillis())
                        .setContentTitle(title)
                        .setContentText(headline)
                        .setDeleteIntent(createOnDismissedIntent(this, uniqueID, mResponse));

            //Builds the notifications and issues it
            NotificationManager nm = (NotificationManager) this.getSystemService(NOTIFICATION_SERVICE);
            nm.notify(uniqueID, notification.build());

            Intent finalIntent = new Intent(this, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, finalIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            notification.setContentIntent(pendingIntent);

            try {
                Thread.sleep(3000);                 //1000 milliseconds is one second.
            } catch(InterruptedException ex) {
                Thread.currentThread().interrupt();
            }


        toast = Toast.makeText(getApplicationContext(), "Notifying completed!", Toast.LENGTH_LONG);
        toast.show();

    }


}

