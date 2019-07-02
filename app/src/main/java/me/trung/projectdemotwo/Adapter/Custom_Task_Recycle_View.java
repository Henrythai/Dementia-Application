package me.trung.projectdemotwo.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Hashtable;

import me.trung.projectdemotwo.Class.Helpers;
import me.trung.projectdemotwo.Model.TaskDisplayItem;
import me.trung.projectdemotwo.R;

public class Custom_Task_Recycle_View extends RecyclerView.Adapter<Custom_Task_Recycle_View.TaskViewHolder> {

    private ArrayList<TaskDisplayItem> items;
    private OnItemClickListener mListener;
    private Context context;

    public Custom_Task_Recycle_View(ArrayList<TaskDisplayItem> items, Context context) {
        this.items = items;
        this.context = context;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    //set Onclick interface
    public interface OnItemClickListener {
        void onItemEditClick(int position);

        void onItemDeleteClick(int position);

        void onItemDoneClick(int position);
    }


    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_individual_task, viewGroup, false);
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
        public ImageView imgIconDate;
        public TextView tvTime, tvDate;
        public ImageView imgRepeatTypeIcon, imgCatIcon;
        public ImageButton imgEdit, imgDone;
        private Hashtable<Integer, String> WeekOfday = new Hashtable<>();

        public TaskViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvTaskName);
            tvTime = itemView.findViewById(R.id.tvTaskTime);
            imgRepeatTypeIcon = itemView.findViewById(R.id.imgIconRepeatType);
            imgDone = itemView.findViewById(R.id.imgRactive);
            imgEdit = itemView.findViewById(R.id.iconEdit);
            imgCatIcon = itemView.findViewById(R.id.imgcategoryicon);
            tvDate = itemView.findViewById(R.id.tvTaskDate);
            imgIconDate = itemView.findViewById(R.id.imgIcondate);
            WeekOfday.put(2, "Mon");
            WeekOfday.put(3, "Tue");
            WeekOfday.put(4, "Wed");
            WeekOfday.put(5, "Thu");
            WeekOfday.put(6, "Fri");
            WeekOfday.put(7, "Sat");
            WeekOfday.put(1, "Sun");
            setOnClickListener(listener);
        }

        public void bind(TaskDisplayItem task, Context context) {
            if (task.getmRepeatype().equals("Weekly")) {
                imgIconDate.setVisibility(View.VISIBLE);
                tvDate.setVisibility(View.VISIBLE);
                tvDate.setText(WeekOfday.get(Integer.parseInt(task.getmRepeatID())));
            } else if (task.getmRepeatype().equals("Monthly")) {
                imgIconDate.setVisibility(View.VISIBLE);
                tvDate.setVisibility(View.VISIBLE);
                tvDate.setText(task.getmDate());
            }else {
                imgIconDate.setVisibility(View.GONE);
                tvDate.setVisibility(View.GONE);
            }

            tvName.setText(task.getmTaskName());
            imgRepeatTypeIcon.setImageResource(Helpers.getResoureImageID(context, task.getmRepeatype().toLowerCase()));
            tvTime.setText(task.getmTime());
            imgCatIcon.setImageResource(Helpers.getResoureImageID(context, task.getmIconImage()));
        }

        public void setOnClickListener(final OnItemClickListener listener) {

            imgEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemEditClick(position);
                        }
                    }
                }
            });

            imgDone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemDoneClick(position);
                        }
                    }
                }
            });
        }
    }
}
