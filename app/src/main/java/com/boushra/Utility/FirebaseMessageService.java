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
import android.nfc.Tag;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.boushra.Activity.CategorySelectionActivity;
import com.boushra.Activity.ChatDetailsActivity;
import com.boushra.Fragment.NotificationFragment;
import com.boushra.Model.Notification;
import com.boushra.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;
import java.util.Objects;

import static com.facebook.FacebookSdk.getApplicationContext;

public class FirebaseMessageService extends FirebaseMessagingService {
    private String TAG=FirebaseMessagingService.class.getSimpleName();


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.e("server_name",remoteMessage.getFrom());
        Map<String,String> dataMap=remoteMessage.getData();
        Log.e("Data:", String.valueOf(remoteMessage.getData()));

        if(NotificationUtils.isAppIsInBackground(getApplicationContext()))
        {
            if(Objects.requireNonNull(remoteMessage.getData().get("type")).equalsIgnoreCase("chat"))
            {
                Intent intent = new Intent("FCM");
                intent.putExtra("FCM", "Yes");
                LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
                Intent push = new Intent(this, ChatDetailsActivity.class);
                Log.e("1","1");

                sendNotification((dataMap.get("title") == null ? "Boushra" : dataMap.get("title")), dataMap.get("body"), push);

            }else {
                Intent intent = new Intent("FCM");
                intent.putExtra("FCM", "Yes");
                intent.putExtra(GlobalVariables.type,remoteMessage.getData().get("type"));
                LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
                Intent push = new Intent(this, CategorySelectionActivity.class);
                sendNotification((dataMap.get("title") == null ? "Boushra" : dataMap.get("title")), dataMap.get("body"), push);
                Log.e("2","2");
            }

        }
        else
        {
            if(remoteMessage.getData().get("title").equalsIgnoreCase("Oops! Chat Off"))
            {
                Intent push = new Intent(this, CategorySelectionActivity.class);
                push.putExtra("FCM", "Yes");
                Log.e("4","4");
                sendNotification((dataMap.get("title") == null ? "Boushra" : dataMap.get("title")), dataMap.get("body"), push);

            }
            else if(remoteMessage.getData().get("type")!=null)
            {
                if(remoteMessage.getData().get("type").equalsIgnoreCase("chat"))
                {
                    Intent push = new Intent(this, ChatDetailsActivity.class);

                    push.putExtra("FCM","Yes");
                    push.putExtra(GlobalVariables.roomId,remoteMessage.getData().get("roomId"));
                    push.putExtra(GlobalVariables.senderId,remoteMessage.getData().get("senderId"));
                    push.putExtra(GlobalVariables.receiverId,remoteMessage.getData().get("receiverId"));
                    push.putExtra(GlobalVariables.name,remoteMessage.getData().get("name"));
                    push.putExtra(GlobalVariables.profile,remoteMessage.getData().get("profile"));
                    Log.e("6","6");
                }
                else
                {
                    Intent push = new Intent(this, CategorySelectionActivity.class);
                    push.putExtra("FCM", "Yes");
                    push.putExtra(GlobalVariables.type,remoteMessage.getData().get("type"));
                    Log.e("4","4");
                    sendNotification((dataMap.get("title") == null ? "Boushra" : dataMap.get("title")), dataMap.get("body"), push);

                }

              //  sendNotification((dataMap.get("title") == null ? "Boushra" : dataMap.get("title")), dataMap.get("body"), push);

            }
            else {

                Intent push = new Intent(this, CategorySelectionActivity.class);
                push.putExtra("FCM", "Yes");
                push.putExtra(GlobalVariables.type,remoteMessage.getData().get("type"));
                Log.e("4","4");
                sendNotification((dataMap.get("title") == null ? "Boushra" : dataMap.get("title")), dataMap.get("body"), push);

            }

        }



    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void sendNotification(String title, String body,Intent intent) {
        NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri uri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        int notificationId = 1;

        String channelId = "channel-01";
        String channelName = "Channel Name";
        int importance = NotificationManager.IMPORTANCE_HIGH;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(channelId, channelName, importance);
            notificationManager.createNotificationChannel(mChannel);
        }

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.mipmap.round_noty)
                .setContentTitle(title)
                .setContentText(body)
                .setSound(uri);



        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addNextIntent(intent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(
                400,
                PendingIntent.FLAG_UPDATE_CURRENT
        );
        mBuilder.setContentIntent(resultPendingIntent);

        notificationManager.notify(notificationId, mBuilder.build());

    }


}

