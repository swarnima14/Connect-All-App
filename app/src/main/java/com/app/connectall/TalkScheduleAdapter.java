package com.app.connectall;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.CalendarContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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
        holder.host.setText("By- " + scheduleModelArrayList.get(position).getHost());
        holder.platform.setText("On- " + scheduleModelArrayList.get(position).getPlatform());
        holder.date.setText(scheduleModelArrayList.get(position).getDate());
        holder.time.setText("at " + scheduleModelArrayList.get(position).getTime());
        holder.link.setText("Joining link- " + scheduleModelArrayList.get(position).getLink());

        holder.calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {

                    AddCalendarEvent(holder, position);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

        holder.link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse(scheduleModelArrayList.get(position).getLink().toString());
                Intent i = new Intent(Intent.ACTION_VIEW, uri);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return scheduleModelArrayList.size();
    }

    class TalkViewHolder extends RecyclerView.ViewHolder{

        TextView topic, host, platform, date, time, link;
        ImageView calendar;

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

    private void AddCalendarEvent(TalkViewHolder holder, int position) throws ParseException {

       String startDate = scheduleModelArrayList.get(position).getDate().trim();
       String startTime = scheduleModelArrayList.get(position).getTime().trim();

        String start = startDate + " " + startTime;
        Date sdf1 = new SimpleDateFormat("dd/MM/yyyy hh:mm aa").parse(start);


        /*  Date time = new SimpleDateFormat("hh:mm aa").parse(startTime);
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm aa");
        String strTime = sdf.format(time);



        Date d = new SimpleDateFormat("dd/MM/yyyy").parse(startDate);
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String strDate= formatter.format(d);*/






        Calendar calendarEvent = Calendar.getInstance();

        Intent i = new Intent(Intent.ACTION_EDIT).setData(CalendarContract.Events.CONTENT_URI);
        i.setType("vnd.android.cursor.item/event");
        i.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, sdf1.getTime());
        //i.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, e);
        i.putExtra("time", true);
        i.putExtra("rule", "FREQ=YEARLY");
        i.putExtra(CalendarContract.Events.TITLE, scheduleModelArrayList.get(position).getTopic());
        context.startActivity(i);
    }
}
