package com.boushra.Utility;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.boushra.Activity.CategorySelectionActivity;
import com.boushra.Fragment.NotificationFragment;
import com.boushra.Model.Notification;
import com.boushra.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

import static com.facebook.FacebookSdk.getApplicationContext;

public class FirebaseMessageService extends FirebaseMessagingService {


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.e("server_name",remoteMessage.getFrom());
        Map<String,String> dataMap=remoteMessage.getData();
        Log.e("Data:", String.valueOf(remoteMessage.getData()));




        if(NotificationUtils.isAppIsInBackground(getApplicationContext()))
        {
            Intent intent=new Intent("FCM");
            intent.putExtra("FCM","yes");
            LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
            Intent push=new Intent(this,CategorySelectionActivity.class);
            sendNotification((dataMap.get("title")==null?"Boushra":dataMap.get("title")), dataMap.get("body"),push);
        }else
        {
            Intent intent=new Intent(this,CategorySelectionActivity.class);
            intent.putExtra("FCM","yes");
            LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
            sendNotification((dataMap.get("title")==null?"Boushra":dataMap.get("title")), dataMap.get("body"),intent);

        }



    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void sendNotification(String title, String message,Intent intent) {

        NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        int notificationId = 1;
        String channelId = "channel-01";
        String channelName = "Channel Name";
        int importance = NotificationManager.IMPORTANCE_HIGH;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(
                    channelId, channelName, importance);
            notificationManager.createNotificationChannel(mChannel);
        }

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.mipmap.round_noty)
                .setContentTitle(title)
                .setContentText(message)
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI);


        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addNextIntent(intent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(
                0,
                PendingIntent.FLAG_UPDATE_CURRENT
        );
        mBuilder.setContentIntent(resultPendingIntent);

        notificationManager.notify(notificationId, mBuilder.build());



    }




}

