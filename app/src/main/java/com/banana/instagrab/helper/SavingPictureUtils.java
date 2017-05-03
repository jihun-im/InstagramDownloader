package com.banana.instagrab.helper;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by jihun.im on 2017-03-07.
 */

public class SavingPictureUtils {

    final public static String OG_IMG_PATTERN = "<meta property=\"og:image\" content=\"(.*?)\" />";
    final public static String DISPLAY_SRC_PATTERN = ", \"display_src\": \"(.*?)\",";
    final public static String DISPLAY_URL_PATTERN = ", \"display_url\": \"(.*?)\",";
    final public static String DISPLAY_VIDEO_PATTERN = ", \"video_url\": \"(.*?)\",";
    // Pattern pattern = Pattern.compile("<meta property=\"og:image\" content=\"(.*?)\" />");

    private static void scanFileForGalleryAppToReconizeTheJpegFile(File file, Context context) {
        MediaScannerConnection.scanFile(context, new String[]{file.getPath()}, new String[]{"image/jpeg"}, null);
    }

    public static void saveFile(Bitmap bitmap, String path, String fileName, Context context) throws IOException {
        try {
            String dcimPath = Environment.getExternalStorageDirectory() + File.separator + Environment.DIRECTORY_DCIM;
            File dirFile = new File(dcimPath + File.separator + path);
            if (!dirFile.exists()) {
                Log.d("JSONTEST", "dirFile.mkdir() result : " + dirFile.mkdir());
            }
            File toBeSavedFile = new File(dirFile.getPath() + File.separator + fileName);
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(toBeSavedFile));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);//100 is max quality
            bos.flush();
            bos.close();
            SavingPictureUtils.scanFileForGalleryAppToReconizeTheJpegFile(toBeSavedFile, context);
        } catch (Exception e) {
            Log.d("BG", "Exception : " + e);
        }
        Log.d("BG", "Ext storage state: " + Environment.getExternalStorageState().toString());
    }


    public static ArrayList<String> getMachedStringArray(String sourcecode, String macher_pattern) {
        ArrayList<String> stringList = new ArrayList<String>();
        Pattern pattern = Pattern.compile(macher_pattern);
        Matcher matcher = pattern.matcher(sourcecode);
        while(matcher.find()) {
            stringList.add(matcher.group(1));
        }
        return stringList;
    }

}
