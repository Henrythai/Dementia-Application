package me.trung.projectdemotwo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.io.IOException;

import me.trung.projectdemotwo.Class.Helpers;
import me.trung.projectdemotwo.Class.InputValidation;
import me.trung.projectdemotwo.R;
import me.trung.projectdemotwo.Sql.DatabaseHelper;
import me.trung.projectdemotwo.Sql.TableUser;


public class ForgotPasswordActivity extends AppCompatActivity {

    private final AppCompatActivity activity = ForgotPasswordActivity.this;

    private NestedScrollView nestedScrollView;
    private TextInputLayout textInputLayout_email;
    private AppCompatButton btnconfirm;

    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;

    private Toolbar myToolbar;
    private ActionBar actionBar;
    private TableUser tableUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);


        init();
        initObjects();
        initListeners();

        Helpers.setupUI(findViewById(R.id.nestedScrollView), activity);

        setSupportActionBar(myToolbar);
        actionBar = this.getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Forgot Password?");
    }

    private void init() {
        nestedScrollView = findViewById(R.id.nestedScrollView);
        textInputLayout_email = findViewById(R.id.textinput_email);
        btnconfirm = findViewById(R.id.btnConfirm);
        myToolbar = findViewById(R.id.myToolbar);
    }

    private void initObjects() {
        inputValidation = new InputValidation(activity);
        tableUser = new TableUser(activity);

    }

    private void initListeners() {
        btnconfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!inputValidation.isInputEditextField(textInputLayout_email, getString(R.string.texterroremail))) {
                    return;
                }

                if (!inputValidation.isInputeditTextEmail(textInputLayout_email, getString(R.string.texterroremail))) {
                    return;
                }

                String email = textInputLayout_email.getEditText().getText().toString().trim();


                if (tableUser.checkUser(email)) {
                    Intent resetIntent = new Intent(activity, ResetPasswordActivity.class);
                    resetIntent.putExtra("email", email);
                    startActivity(resetIntent);
                    textInputLayout_email.getEditText().getText().clear();
                } else {

                    Snackbar.make(nestedScrollView, getString(R.string.no_exist_email), Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }
}
