package com.banana.instagrab.helper;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.banana.instagrab.MainService;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetJSONTask extends AsyncTask<String, Void, String> {

    private HttpURLConnection urlConnection;
    private final String JSON_URL = "https://www.instagram.com/p/BRWwhciFQlk/";
    private ProgressDialog progressDialog;
    private MainService mainServiceInstance;


    public GetJSONTask(MainService mainServiceInstance) {
        this.mainServiceInstance = mainServiceInstance;
        //progressDialog = ProgressDialog.show(activity, "Connecting...", "Downloading JSON...", true);
    }

    @Override
    protected String doInBackground(String... params) {
        StringBuilder result = new StringBuilder();

        try {
            URL url = new URL(params[0]);
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());

            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            urlConnection.disconnect();
        }
        return result.toString();
    }

    @Override
    protected void onPostExecute(String result) {
        mainServiceInstance.callbackFromGetJSONTask(result);
        //progressDialog.dismiss(); //dismiss dialog
    }
}
