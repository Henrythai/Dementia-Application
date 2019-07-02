package me.trung.projectdemotwo.AlarmService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;

import me.trung.projectdemotwo.Class.Helpers;
import me.trung.projectdemotwo.Class.Key;
import me.trung.projectdemotwo.Model.Task;
import me.trung.projectdemotwo.Sql.TableTask;

public class BootReceiver extends BroadcastReceiver {
    private AlarmReceiver mAlarmReceiver;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            Log.e("Alarm Boot", "Hi");
            TableTask rb = new TableTask(context);
            mAlarmReceiver = new AlarmReceiver();

            ArrayList<Task> reminders = rb.getAllActiveTask();

            for (Task task : reminders) {
                Log.e("BootReceiver", task.getId() + "");

                int mReceivedId = task.getId();
                String mRepeatType = task.getRepeatType();
                String mDate = task.getDate();
                String mTime = task.getTime();
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
                    Log.e("Daily", Helpers.showTime(mCalendar, "dd/MM/yyyy HH:mm"));
                    if (mCalendar.getTimeInMillis() - now.getTimeInMillis() < 0) {
                        mCalendar.add(Calendar.DAY_OF_MONTH, 1);
                    }
                    mRepeatTime = Key.milDay;
                    mAlarmReceiver.setRepeatAlarm(context, mCalendar, mReceivedId, mRepeatTime);

                } else if (mRepeatType.equals("Monthly")) {
                    mCalendar.set(Calendar.DAY_OF_MONTH, mDay);
                    Log.e("Monthly", Helpers.showTime(mCalendar, "dd/MM/yyyy HH:mm"));
                    if (mCalendar.getTimeInMillis() - now.getTimeInMillis() < 0) {
                        mCalendar.add(Calendar.MONTH, 1);
                    }
                    mRepeatTime = Key.milMonth;
                    mAlarmReceiver.setRepeatAlarm(context, mCalendar, mReceivedId, mRepeatTime);
                } else {
                    String mRepeatId = task.getRepeatId();
                    mRepeatTime = Key.milWeek;
                    mCalendar.set(Calendar.DAY_OF_WEEK, Integer.parseInt(mRepeatId));
                    Log.e("ChildId", Helpers.showTime(mCalendar, "dd/MM/yyyy HH:mm"));
                    mAlarmReceiver.setRepeatAlamrbyDay(mReceivedId, context, mCalendar, mRepeatTime);
                }

            }
        }

    }
}
