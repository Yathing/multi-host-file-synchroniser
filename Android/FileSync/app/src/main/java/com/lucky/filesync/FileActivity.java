package com.lucky.filesync;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * This class facilitates the viewing of  files on the server
 */
public class FileActivity extends AppCompatActivity {

    private RecyclerView rv;
    private ArrayList<FilePojo> listFiles;
    private  RecyclerViewAdapter recyclerViewAdapter;
    private  TextView geththis;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file);
        rv =  (RecyclerView) findViewById(R.id.recycle);
        geththis =  (TextView) findViewById(R.id.downloading);
        listFiles = new ArrayList<>();
        geththis.setVisibility(View.INVISIBLE);
        recyclerViewAdapter = new RecyclerViewAdapter(listFiles , geththis,this);
        rv.setAdapter(recyclerViewAdapter);
        rv.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onResume(){
        super.onResume();
        getfiles();

    }


    /**
     * fetches files for server
     */
    private void getfiles() {
        listFiles.clear();
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

    /**
     * @param view
     * goes to MainActivity
     */
    public void goUpload(View view) {
        Intent i = new Intent(FileActivity.this, MainActivity.class);
        i.putExtra("files",getNames());
        startActivity(i);
    }

    /**
     * @return ArrayList<String> names
     * gets names of files on server
     */
    public ArrayList<String> getNames(){
        ArrayList<String> names = new ArrayList<>();
        for (FilePojo fp: listFiles) {
            names.add(fp.getFilename());
        }
        return names;
    }

    public void doRefresh(View view) {
        getfiles();
    }
}
