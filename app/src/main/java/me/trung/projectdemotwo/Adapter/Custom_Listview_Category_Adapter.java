package me.trung.projectdemotwo.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import me.trung.projectdemotwo.Model.Category;
import me.trung.projectdemotwo.Class.Helpers;
import me.trung.projectdemotwo.R;


public class Custom_Listview_Category_Adapter extends ArrayAdapter<Category> {

    private Context context;
    private int resource;
    private ArrayList<Category> items;
    private ArrayList<Category> backup_items;

    class ViewHolder {
        TextView tvCatname;
        ImageView imgicon;

        ViewHolder(View view) {
            tvCatname = view.findViewById(R.id.tvCategory_Name);
            imgicon = view.findViewById(R.id.imgcategoryicon);
        }
    }


    public Custom_Listview_Category_Adapter(Context context, int resource, ArrayList<Category> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.items = objects;
        backup_items = new ArrayList<>();
        backup_items.addAll(items);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder viewHolder = null;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(resource, parent, false);

            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }
        viewHolder = (ViewHolder) view.getTag();


        Category cat_current = items.get(position);

        if (cat_current != null) {
            viewHolder.imgicon.setImageResource(Helpers.getResoureImageID(context,cat_current.getIcon()));
            viewHolder.tvCatname.setText(cat_current.getName());
        }
        return view;
    }
}
