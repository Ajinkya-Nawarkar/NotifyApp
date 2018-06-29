package nawarkar.ajinkya.notifyapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class NotificationDismissedReceiver extends BroadcastReceiver {

    String URL = "http://172.22.112.89:5002/notifications";

    public void post_PostJSON(Context context, String URL, Map<String, String> parameters) {
        final Map<String, String> params = parameters;
        RequestQueue queue = Volley.newRequestQueue(context);

        System.out.println("Inside Post MAAAAANNN");
        System.out.println(Arrays.asList(params));
        StringRequest postRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        String response = "Error while making a POST request";
                        Log.d("Error.Response", response);
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                return params;
            }
        };
        queue.add(postRequest);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        int notificationId = 0;
        String classification = "0";
        String origin = "hi";
        String timestamp = "hi";
        String headline = "hi";
        String uuid = "hi";

        Bundle extras = intent.getExtras();

        notificationId = intent.getExtras().getInt("com.my.app.notificationId");


//        classification = extras.getString("classification");
        /*classification = "dismissed";
        origin = extras.getString("origin");
        timestamp = extras.getString("timestamp");
        headline = extras.getString("headline");
        uuid = extras.getString("uuid");*/

        if(origin == null){
            System.out.println("origin was null, but this is not allowed here.");
        }
        if(timestamp == null){
            System.out.println("timestmp was null, but this is not allowed here.");
        }
        if(headline == null){
            System.out.println("HEADINE was null, but this is not allowed here.");
        }
        if(uuid == null){
            System.out.println("uuid was null, but this is not allowed here.");
        }

        Map<String, String> params = new HashMap<String, String>();
        params.put("origin", origin);
        params.put("timestamp", timestamp);
        params.put("headline",headline);
        params.put("uuid", uuid);
        params.put("classification", classification);

        post_PostJSON(context, URL, params);
        Toast.makeText(context, "Dismiss Intent detected and posted.", Toast.LENGTH_LONG).show();
        System.out.println("DIIIISSSMMMIISSEEDD");
    }



}
