package com.app.connectall;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class UploadAdapter extends RecyclerView.Adapter<UploadAdapter.UploadViewHolder> {

    Context context;
    ArrayList<UploadModel> uploadModelArrayList;

    UploadAdapter(Context context, ArrayList<UploadModel> uploadModelArrayList)
    {
        this.context = context;
        this.uploadModelArrayList = uploadModelArrayList;
    }

    @NonNull
    @Override
    public UploadAdapter.UploadViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UploadViewHolder(LayoutInflater.from(context).inflate(R.layout.uploads_list_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull UploadAdapter.UploadViewHolder holder, int position) {

        holder.uploadName.setText("Name: " + uploadModelArrayList.get(position).getName());
        holder.uploadDesc.setText("Desc: " + uploadModelArrayList.get(position).getDesc());

    }

    @Override
    public int getItemCount() {
        return uploadModelArrayList.size();
    }

    public class UploadViewHolder extends RecyclerView.ViewHolder{

        TextView uploadName, uploadDesc;
        ImageView img;

        public UploadViewHolder(@NonNull View itemView) {
            super(itemView);

            uploadName = itemView.findViewById(R.id.resName);
            uploadDesc = itemView.findViewById(R.id.resDesc);
            img = itemView.findViewById(R.id.imgdownload);
        }
    }
}
