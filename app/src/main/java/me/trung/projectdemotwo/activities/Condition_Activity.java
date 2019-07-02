package me.trung.projectdemotwo.activities;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import me.trung.projectdemotwo.Class.Helpers;
import me.trung.projectdemotwo.Class.Key;
import me.trung.projectdemotwo.R;

public class Condition_Activity extends AppCompatActivity {
    private TextView tvTitle,tvContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_condition);

        init();


        String action= getIntent().getAction();
        if(action.equals(Key.KEY_INFORMATION))
        { ;
            tvTitle.setText("Disclaimer");
            tvContent.setText(getResources().getString(R.string.disclaimer));
        }
        else {
            tvTitle.setText("Terms And Conditions");
            tvContent.setText(getResources().getString(R.string.termandCondition));
        }
    }

    public void goback(View v){
        finish();
    }

    private void init(){
        tvTitle=findViewById(R.id.tvTitle);
        tvContent=findViewById(R.id.tvcontent);
    }
}
