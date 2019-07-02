package me.trung.projectdemotwo.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import me.trung.projectdemotwo.Class.Helpers;
import me.trung.projectdemotwo.Model.TaskDisplayItem;
import me.trung.projectdemotwo.R;

public class Custom_Task_Recycle_View_Archived extends RecyclerView.Adapter<Custom_Task_Recycle_View_Archived.TaskViewHolder> {

    private ArrayList<TaskDisplayItem> items;
    private OnItemClickListener mListener;
    private Context context;

    public Custom_Task_Recycle_View_Archived(ArrayList<TaskDisplayItem> items, Context context) {
        this.items = items;
        this.context = context;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    //set Onclick interface
    public interface OnItemClickListener {
        void onItemReActiveClick(int position);
    }


    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_archived_individual_task, viewGroup, false);
        return new TaskViewHolder(v, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder taskViewHolder, int i) {
        TaskDisplayItem currentItem = items.get(i);
        if (currentItem != null) {
            taskViewHolder.bind(currentItem, context);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName;
        public TextView tvTime, tvDate;
        public ImageView imgRepeatTypeIcon, imgCatIcon;
        public ImageButton imgReactive;

        public TaskViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvTaskName);
            tvDate = itemView.findViewById(R.id.tvTaskDate);
            tvTime = itemView.findViewById(R.id.tvTaskTime);
            imgRepeatTypeIcon = itemView.findViewById(R.id.imgIconRepeatType);
            imgCatIcon = itemView.findViewById(R.id.imgcategoryicon);
            imgReactive = itemView.findViewById(R.id.imgRactive);
            setOnClickListener(listener);
        }

        public void bind(TaskDisplayItem task, Context context) {
            tvName.setText(task.getmTaskName());
            imgRepeatTypeIcon.setImageResource(Helpers.getResoureImageID(context, task.getmRepeatype().toLowerCase()));
            tvTime.setText(task.getmTime());
            imgCatIcon.setImageResource(Helpers.getResoureImageID(context, task.getmIconImage()));
            tvDate.setText(task.getmDate());
        }

        public void setOnClickListener(final OnItemClickListener listener) {

            imgReactive.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemReActiveClick(position);
                        }
                    }
                }
            });


        }
    }
}
