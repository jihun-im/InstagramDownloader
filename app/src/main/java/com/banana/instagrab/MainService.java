package com.banana.instagrab;

import android.app.Service;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;

import com.banana.instagrab.helper.GetImageTask;
import com.banana.instagrab.helper.GetJSONTask;
import com.banana.instagrab.helper.MyBitmap;
import com.banana.instagrab.helper.NotiHelper;
import com.banana.instagrab.helper.SavingPictureUtils;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Jihun on 2017-03-08.
 */

public class MainService extends Service {
    ClipboardManager.OnPrimaryClipChangedListener clipChangedListener;
    final private String DIRECTORY_PATH = "instagrab";

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("haha", "service started");
        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putBoolean(getString(R.string.clipboard_service_on_off), true).apply();
        clipChangedListenerSetupAndAddListener();
        NotiHelper.startNotiFromActivityWithCancelCallback(this);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.d("haha", "service stoped");
        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putBoolean(getString(R.string.clipboard_service_on_off), false).apply();
        ((ClipboardManager) getSystemService(CLIPBOARD_SERVICE)).removePrimaryClipChangedListener(clipChangedListener);
        ((ClipboardManager) getSystemService(CLIPBOARD_SERVICE)).removePrimaryClipChangedListener(clipChangedListener);
        NotiHelper.destoryMainNotification(this);
        super.onDestroy();
    }

    private void clipChangedListenerSetupAndAddListener() {
        clipChangedListener = new ClipboardManager.OnPrimaryClipChangedListener() {
            @Override
            public void onPrimaryClipChanged() {
                Log.d("haha", "clipChangedListener called!!");
                ClipData clipData;
                ClipDescription clipDescription;
                String clipString;
                ClipboardManager cm = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                if (!cm.hasPrimaryClip()) {
                    Log.i("haha", "hasPrimaryClip==false");
                    return;
                }
                clipData = cm.getPrimaryClip();
                clipDescription = cm.getPrimaryClipDescription();
                clipString = clipData.getItemAt(0).getText().toString();

                //Log.d("haha", "service started3 , clipString : " + clipString);
                //Log.d("haha", "service started3 , MainService.this : " + MainService.this);
                //Toast.makeText(getApplicationContext(), "clip : " + clipString, Toast.LENGTH_SHORT).show();
                //Toast.makeText(getApplicationContext(), "img url : " + url, Toast.LENGTH_SHORT).show();

                new GetJSONTask(MainService.this).execute(clipString);
                //NotificationUtils.remindUserBecauseCharging(getApplicationContext());
            }
        };
        ((ClipboardManager) getSystemService(CLIPBOARD_SERVICE)).addPrimaryClipChangedListener(clipChangedListener);
    }

    public void callbackFromGetJSONTask(String resultString) {
        //Log.d("haha", " parsed resultString in MainService : " + resultString);
        ArrayList<String> displaySrcList = SavingPictureUtils.getMachedStringArray(resultString, SavingPictureUtils.DISPLAY_SRC_PATTERN);
        ArrayList<String> displayUrlList = SavingPictureUtils.getMachedStringArray(resultString, SavingPictureUtils.DISPLAY_URL_PATTERN);
        ArrayList<String> displayVideoList = SavingPictureUtils.getMachedStringArray(resultString, SavingPictureUtils.DISPLAY_VIDEO_PATTERN);
        for (String s : displaySrcList) {
            Log.d("haha", "displaySrcList child : " + s);
            new GetImageTask(this).execute(s);
        }
        for (String s : displayUrlList) {
            Log.d("haha", "displayUrlList child : " + s);
            new GetImageTask(this).execute(s);
        }
        for (String s : displayVideoList) {
            Log.d("haha", "displayVideoList child : " + s);
            new GetImageTask(this).execute(s);
        }
    }

    public void callbackFromGetImageTask(MyBitmap myBitmap) {
        Log.d("haha", "callbackFromGetImageTask bitmap received : " + myBitmap.getBitmap().toString());
        String[] stringArray = myBitmap.getString().split("/");
        String fileName = stringArray[stringArray.length - 1];
        try {
            SavingPictureUtils.saveFile(myBitmap.getBitmap(), getDirectoryPath(), fileName, getApplicationContext());
            NotiHelper.startNotiFromActivityForGalleryWithSpecificPicutrePath(this, getDirectoryPath(),fileName);
        } catch (IOException e) {
            Log.d("haha", "can not store picture! " + e);
            e.printStackTrace();
        }
    }

    private String getDirectoryPath() {
        return DIRECTORY_PATH;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
