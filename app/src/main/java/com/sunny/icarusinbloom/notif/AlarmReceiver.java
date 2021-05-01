package com.sunny.icarusinbloom.notif;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.sunny.icarusinbloom.LogInActivity;
import com.sunny.icarusinbloom.MainActivity;
import com.sunny.icarusinbloom.R;

import static android.provider.Settings.System.getString;

public class AlarmReceiver extends BroadcastReceiver {
    private static String CHANNEL_ID ="1";

    @Override
    public void onReceive(Context context, Intent intent) {
        showNotification(context);
        System.out.println("running in alarm receiver");
    }

    private void showNotification(Context context) {
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, new Intent(context, LogInActivity.class), 0);


        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.watering)
                .setContentTitle("Have you checked on your plants today?")
                .setContentText("Open the app and see what your plants need c:")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(contentIntent)
                .setAutoCancel(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Bloom notifications";
            String description = "This is Bloom's channel";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationManagerCompat mNotificationManager = NotificationManagerCompat.from(context);
        mNotificationManager.notify(1, builder.build());

    }

}
