package com.app.connectall;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.squareup.picasso.Picasso;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

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
        Picasso.with(context).load(almList.get(position).getImgUrl()).into(holder.cvAlm);
        holder.tvItemBatch.setText("Batch of " + almList.get(position).getGradYear());
        holder.tvItemName.setText(almList.get(position).getName());
        holder.tvItemBranch.setText("Branch: " + almList.get(position).getBranch());
        holder.tvItemMail.setText("Mail: " + almList.get(position).getMail());
        holder.tvItemLinkedin.setText("Linkedin:" + almList.get(position).getLinkedIn());

        boolean isExpanded = almList.get(position).isExpanded();
        holder.expandableLayout.setVisibility(isExpanded ? View.VISIBLE : View.GONE);

        holder.tvItemLinkedin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "linkedin", Toast.LENGTH_SHORT).show();
            }
        });

        holder.tvItemMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "mail", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return almList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvItemBatch,tvItemName, tvItemBranch, tvItemMail, tvItemLinkedin;
        MaterialCardView materialCardView, expandableLayout;
        CircleImageView cvAlm;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvItemName = itemView.findViewById(R.id.tvItemName);
            tvItemBatch = itemView.findViewById(R.id.tvItemBatch);
            tvItemBranch = itemView.findViewById(R.id.tvBranch);
            tvItemMail = itemView.findViewById(R.id.tvMail);
            tvItemLinkedin = itemView.findViewById(R.id.tvLinkedin);
            cvAlm = itemView.findViewById(R.id.cvStuImg);

            materialCardView = itemView.findViewById(R.id.materialCardView);
            expandableLayout = itemView.findViewById(R.id.expandableLayout);

            materialCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlumniModelClass a = almList.get(getAdapterPosition());
                    a.setExpanded(!a.isExpanded());
                    notifyItemChanged(getAdapterPosition());
                }
            });
        }
    }
}
