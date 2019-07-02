package me.trung.projectdemotwo.Class;

import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.RadioGroup;

import java.util.ArrayList;

import me.trung.projectdemotwo.Sql.TableUser;

public class InputValidation {

    private Context context;

    public InputValidation(Context context) {
        this.context = context;
    }

    public boolean isInputEditextField(TextInputLayout textInputLayout, String message) {
        String value = textInputLayout.getEditText().getText().toString().trim();

        if (TextUtils.isEmpty(value)) {
            textInputLayout.setError(message);
            hideKeyBoardFrom(textInputLayout);
            return false;
        } else {
            textInputLayout.setError(null);
            textInputLayout.setErrorEnabled(false);
            return true;
        }
    }

    public String CapitalizString(String temp) {
        return temp.substring(0, 1).toUpperCase() + temp.substring(1).toLowerCase();
    }

    public boolean isInputCountryValidate(TextInputLayout textInputLayout, ArrayList<String> countryList, String country, String message) {

        String temp = country.toLowerCase();
        boolean isExist = false;
        for (String element : countryList) {
            if (element.toLowerCase().contentEquals(temp)) {
                isExist = true;
                break;
            }
        }
        if (!isExist) {
            textInputLayout.setError(message);
            return false;
        } else {
            textInputLayout.setError(null);
            textInputLayout.setErrorEnabled(false);
            return true;
        }
    }

    public boolean isInputeditTextEmail(TextInputLayout textInputLayout, String message) {
        String emailInput = textInputLayout.getEditText().getText().toString().trim();

        if (TextUtils.isEmpty(emailInput)) {
            textInputLayout.setError("Field Can't be Empty!");
            hideKeyBoardFrom(textInputLayout);
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            textInputLayout.setError(message);
            hideKeyBoardFrom(textInputLayout);
            return false;
        } else {
            textInputLayout.setError(null);
            return true;
        }
    }

    public boolean isEmailExist(String email, TextInputLayout textInputLayout, TableUser tableUser) {
        if (tableUser.checkUser(email)) {
            textInputLayout.setError("Email is Exist! Please Enter Email Again!");
            hideKeyBoardFrom(textInputLayout);
            return false;
        } else {
            textInputLayout.setError(null);
            return true;
        }
    }


    public boolean isInputEdittextMatches(TextInputLayout textInputLayout1, TextInputLayout textInputLayout2, String message) {
        String value1 = textInputLayout1.getEditText().getText().toString().trim();
        String value2 = textInputLayout2.getEditText().getText().toString().trim();

        if (!value1.contentEquals(value2)) {
            textInputLayout1.setError(message);
            hideKeyBoardFrom(textInputLayout1);
            return false;
        } else {
            textInputLayout1.setErrorEnabled(false);
            return true;
        }
    }

    public boolean isInputValidationAutoCompletextbox(TextInputLayout textInputLayout, AutoCompleteTextView autotextview, String message) {
        String input = autotextview.getText().toString().trim();
        if (TextUtils.isEmpty(input)) {
            textInputLayout.setError(message);
            hideKeyBoardFrom(textInputLayout);
            return false;
        } else {
            textInputLayout.setError(null);
            textInputLayout.setErrorEnabled(false);
            return true;
        }
    }

    public boolean isInputValidateRadioButtonGroup(TextInputLayout textInputLayout, RadioGroup group, String message) {
        if (group.getCheckedRadioButtonId() == -1) {
            textInputLayout.setError(message);
            return false;
        } else {
            textInputLayout.setError(null);
            textInputLayout.setErrorEnabled(false);
            return true;
        }
    }

    public void hideKeyBoardFrom(View v) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    public boolean isInputCheckBoxClick(TextInputLayout textInputLayout, CheckBox ck, String message) {
        if (!ck.isChecked()) {
            textInputLayout.setError(message);
            return false;
        } else {
            textInputLayout.setError(null);
            textInputLayout.setErrorEnabled(false);
            return true;
        }
    }
}
