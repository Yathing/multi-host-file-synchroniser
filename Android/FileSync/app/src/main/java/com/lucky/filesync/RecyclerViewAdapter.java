package com.lucky.filesync;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.StrictMode;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.DownloadListener;
import com.androidnetworking.interfaces.DownloadProgressListener;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.OkHttpResponseAndStringRequestListener;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.protocol.BasicHttpContext;
import org.json.JSONArray;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;
import okhttp3.Response;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    private ArrayList<FilePojo> list;
    private Context context;
    private TextView loading;

    public RecyclerViewAdapter(ArrayList<FilePojo> list, TextView loading, Context context) {
        this.list = list;
        this.context = context;
        this.loading = loading;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singlefile, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

       holder.size.setText(list.get(position).getSize());

        Date date = new Date(Long.parseLong(list.get(position).getDate()));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String sdate = simpleDateFormat.format(date);
        //System.out.println(sdate);

        holder.date.setText(sdate.toString());
       holder.name.setText(list.get(position).getFilename());
       holder.file = list.get(position);

       holder.parentLayout.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               String fileURL = "http://46.101.20.26:3001/download/" + holder.file.getKey();
               new DownloadFileFromURL(holder.file.getFilename(),loading,  list.get(position).getSize()){
                   @Override
                   protected void onPostExecute(String file_url) {
                       loading.setVisibility(View.INVISIBLE);
                       Toast.makeText(context, "File Downloaded", Toast.LENGTH_SHORT).show();
                   }
               }.execute(fileURL);



           }
       });

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        TextView date;
        TextView size;
        RelativeLayout parentLayout;
        FilePojo file;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.filename);
            date = itemView.findViewById(R.id.date);
            size = itemView.findViewById(R.id.size);
            parentLayout = itemView.findViewById(R.id.parent_layout);

        }
    }



}



