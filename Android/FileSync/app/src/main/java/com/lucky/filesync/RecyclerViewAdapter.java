package com.lucky.filesync;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


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


               DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                       switch (which){
                           case DialogInterface.BUTTON_POSITIVE:

                               String fileURL = "http://46.101.20.26:3001/download/" + holder.file.getKey();

                               doDownload(fileURL, holder.file.getFilename());


                               break;

                           case DialogInterface.BUTTON_NEGATIVE:
                               //No button clicked
                               break;
                       }
                   }
               };

               AlertDialog.Builder builder = new AlertDialog.Builder(context);
               builder.setMessage("Download file: " + holder.file.getFilename() +"?").setPositiveButton("Yes", dialogClickListener)
                       .setNegativeButton("No", dialogClickListener).show();


           }
       });

    }


    public void doDownload(String url, String filename){

        Uri uri= Uri.parse(url);

        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI |
                DownloadManager.Request.NETWORK_MOBILE);

        request.setTitle(filename);
        request.setDescription("Downloading file form FileSync app");

        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,filename);
        request.setMimeType("*/*");
        downloadManager.enqueue(request);

        Toast.makeText(context,  filename + " downloaded", Toast.LENGTH_SHORT).show();

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



