package me.trung.projectdemotwo.Model;

import java.util.ArrayList;

public class ModelTask {
    private int Task_Id;
    private String Task_name;
    private int SubCat_id;

    public int getTask_Id() {
        return Task_Id;
    }

    public void setTask_Id(int task_Id) {
        Task_Id = task_Id;
    }

    public String getTask_name() {
        return Task_name;
    }

    public void setTask_name(String task_name) {
        Task_name = task_name;
    }

    public int getSubCat_id() {
        return SubCat_id;
    }

    public void setSubCat_id(int subCat_id) {
        SubCat_id = subCat_id;
    }

    public ModelTask(int task_Id, String task_name, int subCat_id) {
        Task_Id = task_Id;
        Task_name = task_name;
        SubCat_id = subCat_id;
    }

    public static int getPositionbyTaskId(int taskid, ArrayList<ModelTask> taskitems) {
        for (int i = 0; i < taskitems.size(); ++i) {
            if (taskitems.get(i).getTask_Id() == taskid) return i;
        }
        return -1;
    }
}
