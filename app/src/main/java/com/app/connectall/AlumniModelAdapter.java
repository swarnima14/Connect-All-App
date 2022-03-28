package com.app.connectall;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.List;

public class AlumniModelAdapter extends RecyclerView.Adapter<AlumniModelAdapter.MyViewHolder> {

    Context context;
    ArrayList<AlumniModelClass> almList;

    AlumniModelAdapter(Context context, ArrayList<AlumniModelClass> almList)
    {
        this.context = context;
        this.almList = almList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.alumni_list_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tvItemBatch.setText(almList.get(position).getGradYear());
        holder.tvItemName.setText(almList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return almList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvItemBatch,tvItemName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvItemName = itemView.findViewById(R.id.tvItemName);
            tvItemBatch = itemView.findViewById(R.id.tvItemBatch);
        }
    }
}
