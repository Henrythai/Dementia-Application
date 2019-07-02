package me.trung.projectdemotwo.Comparator;

import java.util.Calendar;
import java.util.Comparator;

import me.trung.projectdemotwo.Model.Task;
import me.trung.projectdemotwo.Model.TaskDisplayItem;

public class TimeComparator implements Comparator<TaskDisplayItem> {
    @Override
    public int compare(TaskDisplayItem task1, TaskDisplayItem task2) {
        if (getTime(task1).compareTo(getTime(task2)) > 0) {
            return 1;
        }
        return -1;
    }

    private Calendar getTime(TaskDisplayItem task) {
        String mTime = task.getmTime();
        String[] mTimeSlip = mTime.split(":");

        int mHour = Integer.parseInt(mTimeSlip[0]);
        int mMinute = Integer.parseInt(mTimeSlip[1]);

        Calendar time = Calendar.getInstance();
        time.set(Calendar.HOUR_OF_DAY, mHour);
        time.set(Calendar.MINUTE, mMinute);
        return time;
    }
}
