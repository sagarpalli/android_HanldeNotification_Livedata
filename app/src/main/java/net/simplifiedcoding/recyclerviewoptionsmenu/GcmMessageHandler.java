package net.simplifiedcoding.recyclerviewoptionsmenu;


import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

public class GcmMessageHandler extends IntentService {
    public static final String KEY_REFERAL = "REFERALS";
    public static MutableLiveData<Bundle>
            liveData=new MutableLiveData<>();;
    SharedPreferences sp;

    public GcmMessageHandler() {
        super("GcmMessageHandler");
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        // Retrieve data extras from push notification
        Log.d("Cline", "GCM msg Received");
        sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        // The getMessageType() intent parameter must be the intent you received
        // in your BroadcastReceiver.
        //Toast.makeText(getApplicationContext(), "GCM PUSH received", Toast.LENGTH_SHORT).show();
        // Keys in the data are shown as extras
        String msg = intent.getStringExtra("message");
        try {
            JSONObject data=new JSONObject(msg);
            String itemname = data.getString("itemname");
            String quantity = data.getString("quantity");
            String tablename = data.getString("tablenumber");
            String category = data.getString("category");
            String selectedCategory = sp.getString("CATEGORY", null);
            if(selectedCategory!=null && !category.equals(selectedCategory)){
                return;
            }

            showTextNotification(itemname+" : Added");
            Log.e("MSG", msg);
           // Intent intnet = new Intent(ItemBroadcastReciever.ACTION);
            Bundle bundle=new Bundle();
            bundle.putString("name",itemname);
            bundle.putString("quantity",quantity);
            bundle.putString("tablename",tablename);
            //intnet.putExtras(bundle);
            liveData.postValue(bundle);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void showTextNotification(String message) {
        PendingIntent pendingIntent = null;
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext());
        builder.setTicker(getString(R.string.app_name))
                .setSmallIcon(R.drawable.ic_media_play_light)
                .setContentTitle(getString(R.string.app_name))
                .setAutoCancel(true)
                .setColor(getResources().getColor(R.color.colorPrimary))
                .setLights(getResources().getColor(R.color.colorPrimary), 2000, 2000);
        builder.setPriority(Notification.PRIORITY_HIGH);
        builder.setCategory(Notification.CATEGORY_MESSAGE);
        builder.setContentText(message);

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String uri = sp.getString("pref_tone", "");

        if (uri.length() > 0) {
            builder.setSound(Uri.parse(uri));
        } else {
            Uri defaultUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            builder.setSound(defaultUri);
        }

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("key", KEY_REFERAL);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        pendingIntent =
                PendingIntent.getActivity(
                        getApplicationContext(),
                        0,
                        intent,
                        PendingIntent.FLAG_CANCEL_CURRENT
                );
        builder.setContentIntent(pendingIntent);

        builder.setDefaults(Notification.DEFAULT_VIBRATE);
        Notification notification = builder.build();
        int notificationid = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);
        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(notificationid, notification);
    }

}
