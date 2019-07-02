package me.trung.projectdemotwo.activities;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import me.trung.projectdemotwo.Adapter.Custom_Listview_Category_Adapter;
import me.trung.projectdemotwo.Model.Category;
import me.trung.projectdemotwo.R;
import me.trung.projectdemotwo.Sql.TableCategory;

public class CategoryActivity extends AppCompatActivity {
    private AppCompatActivity activity = CategoryActivity.this;
    private Toolbar myToolbar;
    private ActionBar actionBar;
    private ListView lvCategory;
    private ArrayList<Category> list;
    private TableCategory tableCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        init();
        initObjects();
        intListerers();
        setSupportActionBar(myToolbar);
        actionBar = this.getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Task Category");


        Custom_Listview_Category_Adapter myAdapter =
                new Custom_Listview_Category_Adapter(CategoryActivity.this, R.layout.custom_listview_category, list);
        lvCategory.setAdapter(myAdapter);

        lvCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Category selected = (Category) parent.getAdapter().getItem(position);
                Intent addtaskIntent = new Intent(activity, AddTaskActivity.class);
                addtaskIntent.putExtra("taskid", selected.getId());
                startActivity(addtaskIntent);
            }
        });
    }



    private void initObjects() {

        tableCategory = new TableCategory(activity);
        list = new ArrayList<>();

        list.addAll(tableCategory.listCategories());
    }

    private void intListerers() {

    }

    private void init() {
        lvCategory = findViewById(R.id.lvCategory);
        myToolbar = findViewById(R.id.myToolbar);
    }
}
