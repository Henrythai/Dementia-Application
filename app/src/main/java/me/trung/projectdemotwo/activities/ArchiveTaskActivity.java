package me.trung.projectdemotwo.activities;

import android.content.DialogInterface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

import me.trung.projectdemotwo.Adapter.Custom_Task_Recycle_View_Archived;
import me.trung.projectdemotwo.AlarmService.AlarmReceiver;
import me.trung.projectdemotwo.Class.Helpers;
import me.trung.projectdemotwo.Class.Key;
import me.trung.projectdemotwo.Comparator.DateTimeComparator;
import me.trung.projectdemotwo.Model.Task;
import me.trung.projectdemotwo.Model.TaskDisplayItem;
import me.trung.projectdemotwo.R;
import me.trung.projectdemotwo.Sql.TableTask;
import me.trung.projectdemotwo.Sql.TableUser;

public class ArchiveTaskActivity extends AppCompatActivity {

    private final AppCompatActivity activity = ArchiveTaskActivity.this;

    private RecyclerView rvtask;
    private Custom_Task_Recycle_View_Archived mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    ArrayList<TaskDisplayItem> taskList;

    private Toolbar myToolbar;
    private ActionBar actionBar;

    private AlarmReceiver alarmReceiver;
    private TextView tvNoRecord;


    TableTask tableTask;
    TableUser tableUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_archive_task);

        init();
        initObjects();


        setSupportActionBar(myToolbar);
        actionBar = this.getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Archived Tasks");

        rvtask.setHasFixedSize(true);
        mAdapter = new Custom_Task_Recycle_View_Archived(taskList, activity);
        rvtask.setLayoutManager(layoutManager);
        rvtask.setAdapter(mAdapter);
        initListeners();

    }

    private void init() {
        rvtask = findViewById(R.id.recycleView_archived);
        myToolbar = findViewById(R.id.myToolbar);
        tvNoRecord = findViewById(R.id.tvNoRecord);
    }

    private void initObjects() {
        alarmReceiver = new AlarmReceiver();
        layoutManager = new LinearLayoutManager(activity);
        tableTask = new TableTask(activity);
        tableUser = new TableUser(activity);

        taskList = new ArrayList<>();
        try {
            taskList.addAll(tableTask.getUnActiveTask(tableUser.getUserIdbyEmail(Helpers.user_email)));
            Collections.sort(taskList, new DateTimeComparator());

        } catch (Exception ex) {
            Log.e("error",ex.getMessage());
        }

        if (!taskList.isEmpty()) {
            tvNoRecord.setVisibility(View.GONE);
        }

    }

    private void initListeners() {
        mAdapter.setOnItemClickListener(new Custom_Task_Recycle_View_Archived.OnItemClickListener() {
            @Override
            public void onItemReActiveClick(final int position) {
                final int position_temp = position;
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setTitle("Warning!");
                builder.setMessage("Do you want to Re Active Task?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        activeTask(position);
                        Helpers.showToast(activity, "Activated");
                    }
                });

                builder.setNegativeButton("No", null);

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }

    private void activeTask(int position) {
        TaskDisplayItem displayItem = taskList.get(position);
        int id = displayItem.getId();
        tableTask.ActivateTaskbyID(id);

        Task currenTask = tableTask.getTaskById(id);

        int mReceivedId = currenTask.getId();
        String mRepeatType = currenTask.getRepeatType();
        String mDate = currenTask.getDate();
        String mTime = currenTask.getTime();
        String[] mDateSlip = mDate.split("/");
        String[] mTimeSlip = mTime.split(":");

        int mDay = Integer.parseInt(mDateSlip[0]);
        int mHour = Integer.parseInt(mTimeSlip[0]);
        int mMinutes = Integer.parseInt(mTimeSlip[1]);
        long mRepeatTime = 0;

        Calendar now = Calendar.getInstance();
        Calendar mCalendar = Calendar.getInstance();

        mCalendar.set(Calendar.HOUR_OF_DAY, mHour);
        mCalendar.set(Calendar.MINUTE, mMinutes);
        mCalendar.set(Calendar.SECOND, 0);

        if (mRepeatType.equals("Daily")) {
            if (mCalendar.getTimeInMillis() - now.getTimeInMillis() < 0) {
                mCalendar.add(Calendar.DAY_OF_MONTH, 1);
            }
            mRepeatTime = Key.milDay;
            alarmReceiver.setRepeatAlarm(getApplicationContext(), mCalendar, mReceivedId, mRepeatTime);

        } else if (mRepeatType.equals("Monthly")) {
            mCalendar.set(Calendar.DAY_OF_MONTH, mDay);
            if (mCalendar.getTimeInMillis() - now.getTimeInMillis() < 0) {
                mCalendar.add(Calendar.MONTH, 1);
            }
            mRepeatTime = Key.milMonth;

            alarmReceiver.setRepeatAlarm(getApplicationContext(), mCalendar, mReceivedId, mRepeatTime);
        } else {
            String mRepeatId = currenTask.getRepeatId();
            mRepeatTime = Key.milWeek;
            mCalendar.set(Calendar.DAY_OF_WEEK, Integer.parseInt(mRepeatId));

            alarmReceiver.setRepeatAlamrbyDay(mReceivedId, activity, mCalendar, mRepeatTime);
        }

        resetRecycleView();
    }

    private void resetRecycleView() {
        taskList.clear();
        try {
            taskList.addAll(tableTask.getUnActiveTask(tableUser.getUserIdbyEmail(Helpers.user_email)));
        } catch (Exception ex) {
            Helpers.showToast(activity, "No Record Found!");
        }
        if (taskList.isEmpty()) {
            tvNoRecord.setVisibility(View.VISIBLE);
        }
        mAdapter.notifyDataSetChanged();
    }

}
