package com.banana.instagrab.helper;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

import com.banana.instagrab.MainActivity;
import com.banana.instagrab.MainService;
import com.banana.instagrab.R;

import java.io.File;

/**
 * Created by jihun.im on 2017-03-08.
 */

public class NotiHelper {
    static final private int NOTI_ID = 7234;
    static final private int NOTI_ID2 = 6137;
    static final private int P_NOTI_ID = 5354;
    static final private int P_NOTI_ID2 = 3534;


    static public void destoryMainNotification(Context context) {
        ((NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE)).cancel(NOTI_ID);
    }

    static public void startNotiFromActivityWithCancelCallback(Context context) {
        // manager
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // notification
        Notification.Builder mBuilder = new Notification.Builder(context);
        mBuilder.setSmallIcon(R.mipmap.ic_launcher);
        //mBuilder.setLargeIcon(R.mipmap.ic_launcher);
        mBuilder.setContentTitle("Instagrab is running");
        //mBuilder.setContentText("");
        mBuilder.setDefaults(Notification.DEFAULT_VIBRATE);
        mBuilder.setAutoCancel(false);
        mBuilder.setOngoing(true);

        //mBuilder.setStyle(new Notification.BigTextStyle().bigText("B Text~~~~~"));

        // cancel intent
        Intent cancelIntent = new Intent(context, CancelNotification.class);
        Bundle extras = new Bundle();
        extras.putInt("notification_id", NOTI_ID);
        cancelIntent.putExtras(extras);
        PendingIntent pendingCancelIntent =
                PendingIntent.getBroadcast(context, P_NOTI_ID, cancelIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.addAction(R.mipmap.ic_close_black_48dp, "stop service", pendingCancelIntent);

//        Intent startGelleryIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(
//                "content://media/internal/images/media"));
//        PendingIntent pendingStartGalleryIntent =
//                PendingIntent.getActivity(context, NOTI_ID + 11, startGelleryIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//        mBuilder.addAction(R.mipmap.ic_close_black_48dp, "start gallery", pendingStartGalleryIntent);

        // building notification
        Notification notification = mBuilder.build();
        notificationManager.notify(NOTI_ID, notification);
    }

    public static class CancelNotification extends BroadcastReceiver {

        private int id;

        @Override
        public void onReceive(Context context, Intent intent) {
            id = intent.getIntExtra("notification_id", NOTI_ID);
            NotificationManager notificationManager =
                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.cancel(id);

            Intent myService = new Intent(context, MainService.class);
            context.stopService(myService);
        }
    }

    static public void startNotiFromActivityForGallery(Context context, String directoryPath) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
        mBuilder.setSmallIcon(R.mipmap.ic_file_download_black_48dp);
        mBuilder.setContentTitle("Title");
        mBuilder.setContentText("Text");
        mBuilder.setAutoCancel(true);


        Intent resultIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(
                "content://media/internal/images/media"));


//        String dcimPath = Environment.getExternalStorageDirectory() + File.separator + Environment.DIRECTORY_DCIM;
//        Intent intent=new Intent();
//        intent.setAction(Intent.ACTION_VIEW);
//        intent.setDataAndType(Uri.fromFile(new File(dcimPath + File.separator + directoryPath)), "image/*");


        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        Log.d("haha","resultPendingIntent : " + resultPendingIntent);
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(NOTI_ID+4, mBuilder.build());
    }


    // storage/emulated/0/DCIM/abc/1233.jpg
    //"/sdcard/test.jpg"
    static public void startNotiFromActivityForGalleryWithSpecificPicutrePath(Context context, String path, String fileName) {

        String dcimPath = Environment.getExternalStorageDirectory() + File.separator + Environment.DIRECTORY_DCIM;
        File dirFile = new File(dcimPath + File.separator + path);
        String imgPath = dirFile.getPath() + File.separator + fileName;

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
        mBuilder.setSmallIcon(R.mipmap.ic_file_download_black_48dp);
        mBuilder.setContentTitle("Instagrab download finished");
        mBuilder.setContentText("Click to check");
        mBuilder.setAutoCancel(true);
        Intent resultIntent = new Intent();
        resultIntent.setAction(Intent.ACTION_VIEW);

        resultIntent.setDataAndType(Uri.parse("file://" + imgPath), "image/*");
        //TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        //stackBuilder.addParentStack(MainActivity.class);
        //stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(context, 0, resultIntent,PendingIntent.FLAG_UPDATE_CURRENT);
                //stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(NOTI_ID2, mBuilder.build());
    }

    static public void startNotiFromService(Context context, Service service) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
        mBuilder.setSmallIcon(R.mipmap.ic_file_download_black_48dp);
        mBuilder.setContentTitle("Title");
        mBuilder.setContentText("Text");
        mBuilder.setAutoCancel(true);
        //mBuilder.setr
        Intent resultIntent = new Intent(context, MainActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(NOTI_ID, mBuilder.build());


    }
}
