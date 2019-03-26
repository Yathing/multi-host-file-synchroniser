package com.lucky.filesync;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import androidx.core.content.FileProvider;

class DownloadFileFromURL extends AsyncTask<String, String, String> {

    private  String filename;
    TextView loading;
    String size;

    public DownloadFileFromURL(String filename, TextView loading, String size ){
        this.filename = filename;
        this.loading = loading;
        this.size = size;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... f_url) {
        int count;
        try {
            URL url = new URL(f_url[0]);
            URLConnection conection = url.openConnection();
            conection.connect();
            int lenghtOfFile = conection.getContentLength();

            InputStream input = new BufferedInputStream(url.openStream(), 8192);
            OutputStream output = new FileOutputStream(Environment.getExternalStorageDirectory().toString() + "/Download/" + filename);

            byte data[] = new byte[1024];
            long total = 0;

            while ((count = input.read(data)) != -1) {
                total += count;

                publishProgress("" + (int) ((total * 100) / lenghtOfFile));

                output.write(data, 0, count);
            }

            output.flush();

            output.close();
            input.close();

        } catch (Exception e) {
            Log.e("Error: ", e.getMessage());
        }

        return null;
    }


    protected void onProgressUpdate(String... progress) {
        loading.setVisibility(View.VISIBLE);
        double up = (double) Double.parseDouble(progress[0]) /-100;
        double total = (double) Double.parseDouble(size);
        double percent = up / total;
        int userpercent = (int) (percent * 100.0);
        loading.setText("Loading: " + userpercent + "%");

    }


    @Override
    protected void onPostExecute(String file_url) {
        loading.setVisibility(View.INVISIBLE);

    }

}
