package me.trung.projectdemotwo.Comparator;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;

import me.trung.projectdemotwo.Model.TaskDisplayItem;

public class DateTimeComparator implements Comparator<TaskDisplayItem> {
    private DateFormat df = new SimpleDateFormat("dd/MM/yyyy hh:mm");

    @Override
    public int compare(TaskDisplayItem task1, TaskDisplayItem task2) {
        String date1 = task1.getmDate() + " " + task1.getmTime();
        String date2 = task2.getmDate() + " " + task2.getmTime();


        try {
            return df.parse(date1).compareTo(df.parse(date2));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }


}
