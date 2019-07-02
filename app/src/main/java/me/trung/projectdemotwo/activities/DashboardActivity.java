package me.trung.projectdemotwo.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.github.abdularis.civ.CircleImageView;

import me.trung.projectdemotwo.Class.Helpers;
import me.trung.projectdemotwo.Class.Key;
import me.trung.projectdemotwo.Model.User;
import me.trung.projectdemotwo.R;
import me.trung.projectdemotwo.Sql.TableUser;

public class DashboardActivity extends AppCompatActivity implements View.OnClickListener {

    private final AppCompatActivity activity = DashboardActivity.this;

    private CardView cvAccount, cvDoneTask, cvAddTask, cvActiveTask, cvLogout, cvInformation;

    private TextView tvEmail, tvTermConditions;

    private CircleImageView imgAvatar;
    private TableUser tableUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        init();
        intObjects();
        initListeners();

    }

    private void init() {
        cvAddTask = findViewById(R.id.cardview_addtask);
        cvAccount = findViewById(R.id.cardview_account);
        cvActiveTask = findViewById(R.id.cardview_acctivetask);
        cvDoneTask = findViewById(R.id.cardview_taskdone);
        cvLogout = findViewById(R.id.cardview_logout);
        tvEmail = findViewById(R.id.tvEmail);
        imgAvatar = findViewById(R.id.imgAvatar);
        cvInformation = findViewById(R.id.cardview_information);
        tvTermConditions = findViewById(R.id.tvtermandcondition);

    }

    private void intObjects() {
        if (Helpers.user_email == null) {
            Helpers.user_email = getIntent().getStringExtra("user_email");
        }
        tvEmail.setText(Helpers.user_email);

        tableUser = new TableUser(activity);
        User user = tableUser.getuser(Helpers.user_email);
        imgAvatar.setImageBitmap(BitmapFactory.decodeByteArray(user.getImageName(), 0, user.getImageName().length));

    }

    private void initListeners() {
        cvAddTask.setOnClickListener(this);
        cvAccount.setOnClickListener(this);
        cvDoneTask.setOnClickListener(this);
        cvActiveTask.setOnClickListener(this);
        cvLogout.setOnClickListener(this);
        cvInformation.setOnClickListener(this);
        tvTermConditions.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cardview_addtask:
                Intent CategoryIntent = new Intent(DashboardActivity.this, CategoryActivity.class);
                startActivity(CategoryIntent);
                break;
            case R.id.cardview_logout:
                Helpers.createADialogBacktoLogin(activity, "Log out", "Are you Sure?");
                break;
            case R.id.cardview_account:
                Intent accountIntent = new Intent(activity, RegisterActivity.class);
                accountIntent.putExtra("user_email", Helpers.user_email);
                startActivity(accountIntent);
                break;
            case R.id.cardview_acctivetask:
                Intent displayactivetaskItent = new Intent(activity, DisplayTaskActivity.class);
                startActivity(displayactivetaskItent);
                break;
            case R.id.cardview_taskdone:
                Intent ArchiveTask = new Intent(activity, ArchiveTaskActivity.class);
                startActivity(ArchiveTask);
                break;
            case R.id.cardview_information:
                Intent informationIntent = new Intent(activity, Condition_Activity.class);
                informationIntent.setAction(Key.KEY_INFORMATION);
                startActivity(informationIntent);
                break;
            case R.id.tvtermandcondition:
                Intent conditionItent = new Intent(activity, Condition_Activity.class);
                conditionItent.setAction(Key.KEY_TERM_CONDITION);
                startActivity(conditionItent);
                break;

        }
    }


    //write func to deal with this
    @Override
    public void onBackPressed() {

        Helpers.createADialogBacktoLogin(activity, "Log out", "Are you Sure?");
    }


}
