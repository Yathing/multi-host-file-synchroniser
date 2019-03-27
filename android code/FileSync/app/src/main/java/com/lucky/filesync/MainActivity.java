package com.lucky.filesync;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.OkHttpResponseAndStringRequestListener;
import com.androidnetworking.interfaces.UploadProgressListener;
import com.obsez.android.lib.filechooser.ChooserDialog;

import java.io.File;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private LinearLayout ll;

    private String filepath = "";

    private TextView selected;

    private TextView loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidNetworking.initialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        ll = (LinearLayout) findViewById(R.id.linlay);

        selected = findViewById(R.id.selected);

        loading = findViewById(R.id.loading);
        loading.setVisibility(View.INVISIBLE);


    }


    public void filechooser(View view) {
        new ChooserDialog(MainActivity.this)
                .withFilter(false, true)
                .withChosenListener(new ChooserDialog.Result() {
                    @Override
                    public void onChoosePath(String path, File pathFile) {
                        Toast.makeText(MainActivity.this, "FOLDER: " + path, Toast.LENGTH_SHORT).show();
                        filepath = path;
                        selected.setText(path);
                    }
                })
                .build()
                .show();
    }


    public void uploadbut(View view) {
        if (filepath.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please select a file", Toast.LENGTH_SHORT).show();
            return;
        }

        //String url = targeturl.getText().toString();
        String url = "http://46.101.20.26:3001/UploadSingle";
        File file = new File(filepath);
        loading.setVisibility(View.VISIBLE);

        AndroidNetworking.upload(url)
                .addMultipartFile("singlefile", file)
                .setPriority(Priority.HIGH)
                .build()
                .setUploadProgressListener(new UploadProgressListener() {
                    @Override
                    public void onProgress(long bytesUploaded, long totalBytes) {
                        double up = (double) bytesUploaded;
                        double total = (double) totalBytes;
                        double percent = up / total;
                        int userpercent = (int) (percent * 100.0);
                        if (userpercent == 100) {
                            loading.setText("Processing...");
                        } else {
                            loading.setText("Loading: " + userpercent + "%");
                        }
                    }
                }).getAsOkHttpResponseAndString(new OkHttpResponseAndStringRequestListener() {
            @Override
            public void onResponse(Response okHttpResponse, String response) {
                loading.setVisibility(View.INVISIBLE);
                System.out.println(response);
                Toast.makeText(getApplicationContext(), "Upload Successful", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onError(ANError anError) {
                Toast.makeText(getApplicationContext(), "Whoopsie-Daisy! That's a failed upload.", Toast.LENGTH_SHORT).show();
                System.out.println(anError);


            }
        });


    }


    public void doLogout(View view) {
        SharedPreferences preferences = getSharedPreferences("FileSync", 0);
        preferences.edit().remove("Password").apply();
        Intent i = new Intent(MainActivity.this, MyLogin.class);
        startActivity(i);
    }
}
