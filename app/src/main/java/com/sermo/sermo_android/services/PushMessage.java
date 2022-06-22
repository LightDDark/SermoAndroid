package com.sermo.sermo_android.services;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.sermo.sermo_android.MessageActivity;
import com.sermo.sermo_android.MyApplication;
import com.sermo.sermo_android.R;

public class PushMessage extends FirebaseMessagingService {
    private static final String CHANNEL_ID = "1";
    public PushMessage() {
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Intent intent = new Intent();
        intent.setAction(MyApplication.context.getString(R.string.channel_intent));
        boolean sent = LocalBroadcastManager.getInstance(MyApplication.context).sendBroadcast(intent);
        if (remoteMessage.getNotification() != null && !sent) {
            createNotificationChannel();
            // Create an Intent for the activity you want to start
            Intent resultIntent = new Intent(this, MessageActivity.class);
            resultIntent.putExtra("id", remoteMessage.getData().get("from"));
            // Create the TaskStackBuilder and add the intent, which inflates the back stack
            TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
            stackBuilder.addNextIntentWithParentStack(resultIntent);
            // Get the PendingIntent containing the entire back stack
            PendingIntent resultPendingIntent =
                    stackBuilder.getPendingIntent(0,
                            PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setContentTitle(remoteMessage.getNotification().getTitle())
                    .setContentText(remoteMessage.getNotification().getBody())
                    .setContentIntent(resultPendingIntent)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
            notificationManager.notify(1, builder.build());
        }
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = MyApplication.context.getString(R.string.channel_name);
            String description = MyApplication.context.getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        Context context = MyApplication.context;
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(context.getString(R.string.Firebase), s);
        editor.apply();
    }
}