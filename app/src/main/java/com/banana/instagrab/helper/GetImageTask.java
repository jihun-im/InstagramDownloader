package com.banana.instagrab.helper;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.banana.instagrab.MainService;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetImageTask extends AsyncTask<String, Void, MyBitmap> {

    private MainService mainServiceInstance;
    private ProgressDialog progressDialog;
    private HttpURLConnection connection;



    public GetImageTask(MainService mainServiceInstance) {
        this.mainServiceInstance = mainServiceInstance;
        //progressDialog = ProgressDialog.show(activity, "Connecting...", "Downloading Image...", true);
    }

    @Override
    protected MyBitmap doInBackground(String... params) {
        try {
            //get Image from URL by URLConnection
            URL url = new URL(params[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();

            //InputStream and decode to Bitmap
            InputStream input = connection.getInputStream();
            Bitmap bmp = BitmapFactory.decodeStream(input);
            MyBitmap myBitmap = new MyBitmap(bmp, params[0]);
            return myBitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            connection.disconnect();
        }
    }

    @Override
    protected void onPostExecute(MyBitmap myBitmap) {
        super.onPostExecute(myBitmap);
        //progressDialog.dismiss(); //dismiss progress bar when task finished!
        if (myBitmap.getBitmap() == null) {
            //Toast.makeText(activity, "Wrong Link!", Toast.LENGTH_SHORT).show();
        } else {
            mainServiceInstance.callbackFromGetImageTask(myBitmap);
        }
    }
}
