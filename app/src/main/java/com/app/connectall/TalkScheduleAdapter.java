package com.app.connectall;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TalkScheduleAdapter extends RecyclerView.Adapter<TalkScheduleAdapter.TalkViewHolder>{

    Context context;
    ArrayList<TalkScheduleModel> scheduleModelArrayList;

    TalkScheduleAdapter(Context context, ArrayList<TalkScheduleModel> scheduleModelArrayList){
        this.context = context;
        this.scheduleModelArrayList = scheduleModelArrayList;
    }

    @NonNull
    @Override
    public TalkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TalkViewHolder(LayoutInflater.from(context).inflate(R.layout.talk_list_item_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TalkViewHolder holder, int position) {

        holder.topic.setText(scheduleModelArrayList.get(position).getTopic());
        holder.host.setText(scheduleModelArrayList.get(position).getHost());
        holder.platform.setText(scheduleModelArrayList.get(position).getPlatform());
        holder.date.setText(scheduleModelArrayList.get(position).getDate());
        holder.time.setText(scheduleModelArrayList.get(position).getTime());
        holder.link.setText(scheduleModelArrayList.get(position).getLink());

        holder.calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        holder.link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
            }
        });
    }

    @Override
    public int getItemCount() {
        return scheduleModelArrayList.size();
    }

    class TalkViewHolder extends RecyclerView.ViewHolder{

        TextView topic, host, platform, date, time, link;
        ImageButton calendar;

        public TalkViewHolder(@NonNull View itemView) {
            super(itemView);

            topic = itemView.findViewById(R.id.tvTopic);
            host = itemView.findViewById(R.id.tvHost);
            platform = itemView.findViewById(R.id.tvPlatform);
            date = itemView.findViewById(R.id.tvDate);
            time = itemView.findViewById(R.id.tvTime);
            link = itemView.findViewById(R.id.tvLink);
            calendar = itemView.findViewById(R.id.ibCalendar);
        }
    }
}
