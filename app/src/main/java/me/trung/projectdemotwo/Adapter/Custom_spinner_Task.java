package me.trung.projectdemotwo.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;

import me.trung.projectdemotwo.Class.Helpers;
import me.trung.projectdemotwo.Model.ModelTask;
import me.trung.projectdemotwo.R;
import me.trung.projectdemotwo.Sql.TableSubCategory;

public class Custom_spinner_Task extends ArrayAdapter<ModelTask> {
    private Context context;
    private int resource;
    private ArrayList<ModelTask> items;
    private String[] colors = {"#F1F3FF", "#ECFFFB"};
    private Hashtable<Integer, String> imageIcons;

    class ViewHolder {
        private TextView tvTaskName;
        private ImageView imgCategoryIcom;

        ViewHolder(View v) {
            tvTaskName = v.findViewById(R.id.tvTaskName);
            imgCategoryIcom = v.findViewById(R.id.imgcategoryicon);
        }
    }

    public Custom_spinner_Task(Context context, int resource, ArrayList<ModelTask> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.items = objects;

        imageIcons = new Hashtable<>();
        imageIcons.putAll(new TableSubCategory(context).getImageSubCategoryIcon());
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent, false);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent, true);
    }

    private View getCustomView(int position, View convertView, ViewGroup parent, boolean isgetView) {
        View view = convertView;
        ViewHolder viewHolder;


        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(resource, parent, false);

            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }
        viewHolder = (ViewHolder) view.getTag();


        ModelTask task_current = items.get(position);

        if (task_current != null) {
            viewHolder.tvTaskName.setText(task_current.getTask_name());
            viewHolder.imgCategoryIcom.setImageResource(Helpers.getResoureImageID(context, imageIcons.get(task_current.getSubCat_id())));
        }

        if (!isgetView) {
            view.setBackgroundColor(Color.parseColor(colors[position % 2]));
        } else {
            viewHolder.tvTaskName.setTextColor(Color.parseColor("#F44336"));
        }
        return view;
    }

}
