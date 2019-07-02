package me.trung.projectdemotwo.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;

import me.trung.projectdemotwo.Adapter.Custom_Task_Recycle_View;
import me.trung.projectdemotwo.AlarmService.AlarmReceiver;
import me.trung.projectdemotwo.Class.Helpers;
import me.trung.projectdemotwo.Class.Key;
import me.trung.projectdemotwo.Comparator.TimeComparator;
import me.trung.projectdemotwo.Model.TaskDisplayItem;
import me.trung.projectdemotwo.R;
import me.trung.projectdemotwo.Sql.TableCategory;
import me.trung.projectdemotwo.Sql.TableTask;
import me.trung.projectdemotwo.Sql.TableUser;
import me.trung.projectdemotwo.custom_control.ToggleButtonGroupTableLayout;

public class DisplayTaskActivity extends AppCompatActivity {
    private Hashtable<String, Integer> CategoryDic;
    private final AppCompatActivity activity = DisplayTaskActivity.this;
    private TableUser tableUser;

    private RecyclerView rvtask;
    private Custom_Task_Recycle_View mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    ArrayList<TaskDisplayItem> taskList;

    private Toolbar myToolbar;
    private ActionBar actionBar;

    TableCategory tableCategory;
    private TextView tvNoRecord;
    private TableTask tableTask;
    private AlarmReceiver mAlarmReceiver;

    private String mRepeatId;
    private String[] mRepeateIdSlip;

    private ToggleButtonGroupTableLayout radioGroupCategory;
    private RadioButton rdPersonal, rdHousekeeping, rdHealth, rdSocial;

    private RadioGroup radioGroupRepeatType;
    private RadioButton rdDaily, rdWeekly, rdMonthly;

    private String mRepeateType;
    private int mCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_task);

        init();
        initObjects();


        setSupportActionBar(myToolbar);
        actionBar = this.getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("My Tasks");

        rvtask.setHasFixedSize(true);
        mAdapter = new Custom_Task_Recycle_View(taskList, activity);
        rvtask.setLayoutManager(layoutManager);
        rvtask.setAdapter(mAdapter);

        initListeners();
    }

    private void init() {
        rvtask = findViewById(R.id.rvTask);
        myToolbar = findViewById(R.id.myToolbar);
        radioGroupCategory = findViewById(R.id.radioGroupCategory);
        radioGroupRepeatType = findViewById(R.id.radioGroupRepeatType);
        rdPersonal = findViewById(R.id.radioPersonal);
        rdHousekeeping = findViewById(R.id.radioHouseKeeping);
        rdHealth = findViewById(R.id.radioHealth);
        rdSocial = findViewById(R.id.radioSocialLersure);
        rdDaily = findViewById(R.id.radioDaily);
        rdWeekly = findViewById(R.id.radioWeekly);
        rdMonthly = findViewById(R.id.radioMonthly);
        tvNoRecord = findViewById(R.id.tvNoRecord);
    }

    private void initObjects() {
        layoutManager = new LinearLayoutManager(activity);
        CategoryDic = new Hashtable<>();
        CategoryDic.put("Personal", 1);
        CategoryDic.put("Leisure And Social", 2);
        CategoryDic.put("Health and Medical", 3);
        CategoryDic.put("House Keeping", 4);
        mCategory = -1;
        mRepeateType = "";
        tableCategory = new TableCategory(activity);
        tableTask = new TableTask(activity);
        tableUser = new TableUser(activity);
        taskList = new ArrayList<>();

        try {
            ArrayList<TaskDisplayItem> displayItems = tableTask.getActiveTask(mCategory, mRepeateType, tableUser.getUserIdbyEmail(Helpers.user_email));
            if (displayItems != null) taskList.addAll(displayItems);
            Collections.sort(taskList, new TimeComparator());
        } catch (Exception ex) {
            Log.e("Error", ex.getMessage());
            Helpers.showToast(activity, ex.getMessage());
        }

        if (!taskList.isEmpty()) {
            tvNoRecord.setVisibility(View.GONE);
        }

        mAdapter = new Custom_Task_Recycle_View(taskList, activity);
        mAlarmReceiver = new AlarmReceiver();

    }


    private void initListeners() {
        mAdapter.setOnItemClickListener(new Custom_Task_Recycle_View.OnItemClickListener() {
            @Override
            public void onItemEditClick(int position) {
                TaskDisplayItem displayItem = taskList.get(position);
                int id = displayItem.getId();
                Intent updateIntent = new Intent(getApplicationContext(), UpdateTaskActivity.class);
                updateIntent.putExtra(Key.EXTRA_REMINDER_ID, id);
                startActivityForResult(updateIntent, 1);
            }

            @Override
            public void onItemDeleteClick(int position) {

            }

            @Override
            public void onItemDoneClick(int position) {
                final int position_temp = position;
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setTitle("Warning!");
                builder.setMessage("Do you want to Delete?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ModifyTask(position_temp, "done");
                        Helpers.showToast(activity, "Deleted");
                    }
                });

                builder.setNegativeButton("No", null);

                AlertDialog alertDialog = builder.create();
                alertDialog.show();


            }
        });

        CompoundButton.OnCheckedChangeListener radioCategoryListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    RadioButton rd = (RadioButton) buttonView;
                    mCategory = CategoryDic.get(rd.getText().toString());
                    resetRecycleView();
                }

            }
        };

        rdHealth.setOnCheckedChangeListener(radioCategoryListener);
        rdSocial.setOnCheckedChangeListener(radioCategoryListener);
        rdHousekeeping.setOnCheckedChangeListener(radioCategoryListener);
        rdPersonal.setOnCheckedChangeListener(radioCategoryListener);

        radioGroupRepeatType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rd = findViewById(group.getCheckedRadioButtonId());
                mRepeateType = rd.getText().toString();
                resetRecycleView();

            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            resetRecycleView();
        }
    }

    public void ModifyTask(int position, String mode) {

        TaskDisplayItem displayItem = taskList.get(position);
        int id = displayItem.getId();
        if (mode.equals("delete")) {
            tableTask.DeleteTaskbyId(id);
        } else if (mode.equals("done")) {
            tableTask.setDoneTaskbyID(id);
        }

        mAlarmReceiver.cancelAlarm(getApplicationContext(), id);


        resetRecycleView();
    }


    private void resetRecycleView() {
        taskList.clear();
        try {
            ArrayList<TaskDisplayItem> displayItems = tableTask.getActiveTask(mCategory, mRepeateType, tableUser.getUserIdbyEmail(Helpers.user_email));
            if (displayItems != null) {
                taskList.addAll(displayItems);
                Collections.sort(taskList, new TimeComparator());
            }
        } catch (Exception ex) {
            Helpers.showToast(activity, "No Record Found!");
        }
        if (taskList.isEmpty()) {
            tvNoRecord.setVisibility(View.VISIBLE);
        } else {
            tvNoRecord.setVisibility(View.GONE);
        }

        mAdapter.notifyDataSetChanged();
    }

}
