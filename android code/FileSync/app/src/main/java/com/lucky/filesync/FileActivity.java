package com.lucky.filesync;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.ArrayList;

public class FileActivity extends AppCompatActivity {

    RecyclerView rv;
    ArrayList<FilePojo> listFiles;
    RecyclerViewAdapter recyclerViewAdapter;
    private ProgressDialog pDialog;
    public static final int progress_bar_type = 0;
    TextView geththis;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        if(Build.VERSION.SDK_INT>=24){
//        try{
//            Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
//            m.invoke(null);
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//    }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file);
        rv =  (RecyclerView) findViewById(R.id.recycle);
        geththis =  (TextView) findViewById(R.id.downloading);
        listFiles = new ArrayList<>();
        geththis.setVisibility(View.INVISIBLE);
        getfiles();
        recyclerViewAdapter = new RecyclerViewAdapter(listFiles , geththis,this);
        rv.setAdapter(recyclerViewAdapter);
        rv.setLayoutManager(new LinearLayoutManager(this));
    }

    private void getfiles() {
        AndroidNetworking.get("http://46.101.20.26:3001/Files")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // do anything with response
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject current = response.getJSONObject(i);
                                String filename = current.getString("originalname");
                                String number = current.getString("filename");
                                String size = current.getString("size");
                                String date = current.getJSONObject("meta").getString("created");
                                int key = current.getInt("$loki");
                                FilePojo filePojo = new FilePojo(size, date, filename, key, number );
                                listFiles.add(filePojo);
                                recyclerViewAdapter.notifyDataSetChanged();
                                System.out.println(filename);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                    }
                });
    }

    public void goUpload(View view) {
        Intent i = new Intent(FileActivity.this, MainActivity.class);
        startActivity(i);
    }

}
