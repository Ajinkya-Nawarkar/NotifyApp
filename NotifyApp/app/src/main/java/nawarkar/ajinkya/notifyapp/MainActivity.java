package nawarkar.ajinkya.notifyapp;

import android.annotation.TargetApi;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

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
        Toast toast = Toast.makeText(getApplicationContext(), "Ok, Notified!", Toast.LENGTH_LONG);
        toast.show();
        String main = "Aj";
        int id = 0;
        try {
            String result = "\"id\":711,\n" +
                    "      \"main\":\"Smoke\",\n" +
                    "      \"description\":\"smoke\",\n" +
                    "      \"icon\":\"50n\"";

            /*StringBuilder sb = new StringBuilder(); // for final result
            String line = "";
            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream())); // read whatever is returned by server
            while ((line = reader.readLine()) != null) {
                sb.append(line); // get response by line and append to StringBulder
            }
            reader.close();
            String result = sb.toString(); //this is a response to parse.*/


            JSONObject jsonResult = new JSONObject(result);// convert response to JSONObject
            main = jsonResult.getString("main"); // Smoke
            id = jsonResult.getInt("id"); // 711 and so on.

        }
        catch (Exception e) {

            System.out.println(e.getMessage());

        }

        //Build Notificationn
        notification.setSmallIcon(R.drawable.news);
        notification.setTicker("This is ticker");
        notification.setWhen(System.currentTimeMillis());
        notification.setContentTitle(main);
        notification.setContentText(id+"");

        Intent newsIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, newsIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        notification.setContentIntent(pendingIntent);

        //Builds the notifications and issues it
        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nm.notify(uniqueID, notification.build());

    }

    private PendingIntent createOnDismissedIntent(Context context, int notificationId) {
        Intent intent = new Intent(context, NotificationDismissedReceiver.class);
        intent.putExtra("com.my.app.notificationId", notificationId);

        PendingIntent pendingIntent =
                PendingIntent.getBroadcast(context.getApplicationContext(),
                        notificationId, intent, 0);
        return pendingIntent;
    }


    public void start_notifyBtnClicked(View view)
    {
        Toast toast = Toast.makeText(getApplicationContext(), "Notifying started!", Toast.LENGTH_LONG);
        toast.show();
        String main = "Aj";
        int id = 0;

        for (int i=0; i< 5; i++) {

            if ("news" == "news") {
                //Build Notificationn

                notification.setSmallIcon(R.drawable.news)
                            .setTicker("This is ticker")
                            .setWhen(System.currentTimeMillis())
                            .setContentTitle(main)
                            .setContentText(i + "")
                            .setDeleteIntent(createOnDismissedIntent(this, uniqueID));

            }

            //Builds the notifications and issues it
            NotificationManager nm = (NotificationManager) this.getSystemService(NOTIFICATION_SERVICE);
            nm.notify(uniqueID, notification.build());

            Intent newsIntent = new Intent(this, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, newsIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            notification.setContentIntent(pendingIntent);

            try {
                Thread.sleep(3000);                 //1000 milliseconds is one second.
            } catch(InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }

        toast = Toast.makeText(getApplicationContext(), "Notifying completed!", Toast.LENGTH_LONG);
        toast.show();

    }


}

