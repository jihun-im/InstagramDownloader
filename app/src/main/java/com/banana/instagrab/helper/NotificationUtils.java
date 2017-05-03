/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.banana.instagrab.helper;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;


/**
 * Utility class for creating hydration notifications
 */
public class NotificationUtils {

    /*
     * This notification ID can be used to access our notification after we've displayed it. This
     * can be handy when we need to cancel the notification, or perhaps update it. This number is
     * arbitrary and can be set to whatever you like. 1138 is in no way significant.
     */
    private static final int WATER_REMINDER_NOTIFICATION_ID = 1118;
    /**
     * This pending intent id is used to uniquely reference the pending intent
     */
    private static final int WATER_REMINDER_PENDING_INTENT_ID = 3447;

    // COMPLETED (7) Create a method called remindUserBecauseCharging which takes a Context.
    // This method will create a notification for charging. It might be helpful
    // to take a look at this guide to see an example of what the code in this method will look like:
    // https://developer.android.com/training/notify-user/build-notification.html
    public static void remindUserBecauseCharging(Context context) {
        // COMPLETED (8) In the remindUser method use NotificationCompat.Builder to create a notification
        // that:
        // - has a color of R.colorPrimary - use ContextCompat.getColor to get a compatible color
        // - has ic_drink_notification as the small icon
        // - uses icon returned by the largeIcon helper method as the large icon
        // - sets the title to the charging_reminder_notification_title String resource
        // - sets the text to the charging_reminder_notification_body String resource
        // - sets the style to NotificationCompat.BigTextStyle().bigText(text)
        // - sets the notification defaults to vibrate
        // - uses the content intent returned by the contentIntent helper method for the contentIntent
        // - automatically cancels the notification when the notification is clicked
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                //.setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                //.setSmallIcon(R.drawable.ic_drink_notification)
                //.setLargeIcon(largeIcon(context))
                .setContentTitle("타이틀!")
                .setContentText("바디텍스트!")
                //.setStyle(new NotificationCompat.MessagingStyle.Message()
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setContentIntent(contentIntent(context))
                .setAutoCancel(false);

        Log.d("hahaha","1");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            notificationBuilder.setPriority(Notification.PRIORITY_HIGH);
        }
        Log.d("hahaha","2");
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        Log.d("hahaha","3");
        notificationManager.notify(WATER_REMINDER_NOTIFICATION_ID, notificationBuilder.build());
        Log.d("hahaha","4");
    }

    // COMPLETED (1) Create a helper method called contentIntent with a single parameter for a Context. It
    // should return a PendingIntent. This method will create the pending intent which will trigger when
    // the notification is pressed. This pending intent should open up the MainActivity.
    private static PendingIntent contentIntent(Context context) {

//        String path = "abc"; //folder name
//        String fileName = "1234.jpg";
//        String directoryPath = Environment.getExternalStorageDirectory() + File.separator + Environment.DIRECTORY_DCIM;
//        File dirFile = new File(directoryPath + File.separator + path);
//        if (!dirFile.exists()) {
//            Log.d("JSONTEST", "dirFile.mkdir() result : " + dirFile.mkdir());
//        }
//        File toBeSavedFile = new File(dirFile.getPath() + File.separator + fileName);
//        Intent intent = new Intent();
//        intent.setAction(Intent.ACTION_VIEW);
//        intent.setDataAndType(Uri.parse(toBeSavedFile.toString()), "image/*");

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "Awesome message");
        sendIntent.setType("text/plain");

        return PendingIntent.getService(context,
                WATER_REMINDER_PENDING_INTENT_ID,
                sendIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

//        return PendingIntent.getActivity(
//                context,
//                WATER_REMINDER_PENDING_INTENT_ID,
//                intent,
//                PendingIntent.FLAG_UPDATE_CURRENT);
    }


    // COMPLETED (4) Create a helper method called largeIcon which takes in a Context as a parameter and
    // returns a Bitmap. This method is necessary to decode a bitmap needed for the notification.
//    private static Bitmap largeIcon(Context context) {
//        // COMPLETED (5) Get a Resources object from the context.
//        Resources res = context.getResources();
//        // COMPLETED (6) Create and return a bitmap using BitmapFactory.decodeResource, passing in the
//        // resources object and R.drawable.ic_local_drink_black_24px
//        //Bitmap largeIcon = BitmapFactory.decodeResource(res, R.drawable.ic_local_drink_black_24px);
//        return largeIcon;
//    }
}
