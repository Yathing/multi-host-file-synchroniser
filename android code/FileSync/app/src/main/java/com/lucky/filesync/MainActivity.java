package com.lucky.filesync;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.Response;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.internal.http.multipart.MultipartEntity;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.OkHttpResponseAndStringRequestListener;
import com.androidnetworking.interfaces.UploadProgressListener;
import com.obsez.android.lib.filechooser.ChooserDialog;
import com.unnamed.b.atv.model.TreeNode;
import com.unnamed.b.atv.view.AndroidTreeView;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    LinearLayout ll;
    private static final int READ_REQUEST_CODE = 42;

    String filepath = "";

    TextView selected;

    TextView loading;

    EditText targeturl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidNetworking.initialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        TreeNode root = TreeNode.root();
        TreeNode parent = new TreeNode("MyParentNode");
        TreeNode child0 = new TreeNode("ChildNode0");
        TreeNode child1 = new TreeNode("ChildNode1");
        TreeNode child2 = new TreeNode("ChildNode2");
        TreeNode child3 = new TreeNode("Child3");
        child1.addChild(child2);
        child2.addChild(child3);

        parent.addChildren(child0, child1);
        root.addChild(parent);
        ll = (LinearLayout) findViewById(R.id.linlay);
        AndroidTreeView tView = new AndroidTreeView(this, root);
        ll.addView(tView.getView());
        System.out.println(child3.getPath());

        selected = findViewById(R.id.selected);

        loading = findViewById(R.id.loading);
        loading.setVisibility(View.INVISIBLE);

        targeturl = (EditText) findViewById(R.id.targeturl);

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
        String url = targeturl.getText().toString();
        File file = new File(filepath);
        System.out.println(file.exists());
        System.out.println(file.getName());
        loading.setVisibility(View.VISIBLE);


        AndroidNetworking.upload(url)
                .addMultipartFile("image", file)
                .addMultipartParameter("id", "cat")
                .addMultipartParameter("username", "john")
                .setTag("uploadTest")
                .setPriority(Priority.HIGH)
                .build()
                .setUploadProgressListener(new UploadProgressListener() {
                    @Override
                    public void onProgress(long bytesUploaded, long totalBytes) {
                        System.out.println(bytesUploaded);
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
