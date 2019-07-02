package me.trung.projectdemotwo.Class;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.Locale;

import me.trung.projectdemotwo.R;
import me.trung.projectdemotwo.activities.LoginActivity;
import me.trung.projectdemotwo.custom_control.ToggleButtonGroupTableLayout;

public class Helpers {

    public static String user_email = null;

    // Show Toast
    public static void showToast(Context context, String msg) {
        Toast myToast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        myToast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 100);
        myToast.show();
    }

    // Return Resource Image ID
    public static int getResoureImageID(Context context, String imageName) {
        return context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());
    }

    // Return Resource Name By ID
    public static String getResoureNameByID(Context context, int resourceID) {
        return context.getResources().getResourceEntryName(resourceID);
    }

    public static void setupUI(View view, final Activity activity) {

        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    try {
                        hideSoftKeyboard(activity);
                    } catch (Exception ex) {
                        Log.e("Error_Keyboard", ex.getMessage());
                    }

                    return false;
                }
            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(innerView, activity);
            }
        }
    }

    public static Calendar getTime(String time) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
        try {
            cal.setTime(sdf.parse(time));// all done
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return cal;
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }

    public static String showTime(Calendar calendar, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);  // 12:25 PM
        return simpleDateFormat.format(calendar.getTime());
    }


    public static String getDatabasePath(String fileNname, Context context) {
        return context.getDatabasePath(fileNname).getAbsolutePath();
    }

    //encode Image
    public static byte[] encodeImage(ImageView imageView) {
        imageView.setDrawingCacheEnabled(true);
        imageView.buildDrawingCache();
        Bitmap bitmap = imageView.getDrawingCache();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] data = baos.toByteArray();
        return data;
    }

    public static void activeClickButton(LinearLayout linearLayout) {
        for (int i = 0; i < linearLayout.getChildCount(); ++i) {
            if (linearLayout.getChildAt(i) instanceof Button) {
                Button btn = (Button) linearLayout.getChildAt(i);
                btn.performClick();
                break;
            }
        }
    }

    //reset CheckBox Group
    public static void resetCheckBoxGroup(LinearLayout linearLayout) {
        int count = linearLayout.getChildCount();
        for (int i = 0; i < count; i++) {
            View o = linearLayout.getChildAt(i);
            if (o instanceof CheckBox) {
                CheckBox checkBox = (CheckBox) o;
                checkBox.setChecked(false);
            }
        }
    }

    //reset Radio Group
    public static void resetRadioGroup(RadioGroup radioGroup) {
        int count = radioGroup.getChildCount();
        for (int i = 0; i < count; i++) {
            View o = radioGroup.getChildAt(i);
            if (o instanceof RadioButton) {
                RadioButton radioButton = (RadioButton) o;
                radioButton.setChecked(false);
            }
        }
    }

    //set selected radio button by user
    public static void setSelectesRadioButtonByUser(RadioGroup radioGroup, String request) {
        int count = radioGroup.getChildCount();
        for (int i = 0; i < count; i++) {
            RadioButton rd = (RadioButton) radioGroup.getChildAt(i);
            if (rd.getText().toString().contentEquals(request)) {
                rd.setChecked(true);
                return;
            }
        }
    }

    public static void createADialogBacktoLogin(final Context context, String Title, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(Title);
        builder.setMessage(Message);

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Helpers.user_email = null;
                Intent logoutIntent = new Intent(((Activity) context), LoginActivity.class);
                logoutIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                context.startActivity(logoutIntent);
            }
        });

        builder.setNegativeButton("No", null);

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public static String getRadioButtonSelectedName(Context context, RadioGroup rdGroup) {
        int SelectedId = rdGroup.getCheckedRadioButtonId();
        RadioButton rd = ((Activity) context).findViewById(SelectedId);
        return rd.getText().toString();
    }

    public static void setDisableRadioGroup(RadioGroup radioGroup) {
        for (int i = 0; i < radioGroup.getChildCount(); i++) {
            radioGroup.getChildAt(i).setFocusable(false);
            radioGroup.getChildAt(i).setClickable(false);
        }
    }

    public static void setDisableCheckBoxGroup(LinearLayout linearLayout) {
        for (int i = 0; i < linearLayout.getChildCount(); i++) {
            linearLayout.getChildAt(i).setFocusable(false);
            linearLayout.getChildAt(i).setClickable(false);
        }
    }

    public static void setEnableRadioGroup(RadioGroup radioGroup) {
        for (int i = 0; i < radioGroup.getChildCount(); i++) {
            radioGroup.getChildAt(i).setFocusable(true);
            radioGroup.getChildAt(i).setClickable(true);
        }
    }

    public static void setEnableView(View v) {
        v.setClickable(true);
        v.setFocusable(true);
    }

    public static int getRepeatDayInToggleGroup(ToggleButtonGroupTableLayout group, Hashtable<String, Integer> dayofWeek) {
        int count = group.getChildCount();
        for (int i = 0; i < count; ++i) {
            TableRow row = (TableRow) group.getChildAt(i);
            for (int j = 0; j < row.getChildCount(); ++j) {
                RadioButton rd = (RadioButton) row.getChildAt(j);
                if (rd.isChecked()) {
                    return dayofWeek.get(rd.getText().toString());
                }
            }
        }
        return -1;
    }

    public static void setRadioButtonInToggleGroupByName(String name, ToggleButtonGroupTableLayout group) {
        int count = group.getChildCount();
        for (int i = 0; i < count; ++i) {
            TableRow row = (TableRow) group.getChildAt(i);
            for (int j = 0; j < row.getChildCount(); ++j) {
                RadioButton rd = (RadioButton) row.getChildAt(j);
                if (rd.getText().toString().equalsIgnoreCase(name)) {
                    rd.setChecked(true);
                    group.setActiveRadioButton(rd);
                    break;
                }
            }
        }
    }

    //create View
    public static TextView createTextViewLabelWeekDay(String day, int tagDay, Context context) {
        TextView tv = new TextView(context);
        float scale = context.getResources().getDisplayMetrics().density;
        int dpAsPixels = (int) (10 * scale + 0.5f);
        int padding = (int) (15 * scale + 0.5f);
        LinearLayout.LayoutParams rd_param = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rd_param.setMargins(0, dpAsPixels, 0, 0);
        tv.setLayoutParams(rd_param);
        String value = day + " Time";
        tv.setText(value);
        tv.setTag(tagDay);
        tv.setTextColor(Color.parseColor("#3F51B5"));
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        tv.setTypeface(Typeface.create("sans-serif-condensed", Typeface.NORMAL));
        return tv;
    }

    public static RadioButton getRadioButton(String name, int Sub_id, String icon, Context context, CompoundButton.OnCheckedChangeListener subCategoryClickListener) {
        float scale = context.getResources().getDisplayMetrics().density;
        int dpAsPixels = (int) (10 * scale + 0.5f);
        LinearLayout.LayoutParams rd_param = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1);
        rd_param.setMargins(dpAsPixels, 0, 0, 0);
        RadioButton rd = new RadioButton(context);
        rd.setLayoutParams(rd_param);
        rd.setBackground(context.getDrawable(R.drawable.custom_button_background));
        rd.setClickable(true);
        rd.setCompoundDrawablesWithIntrinsicBounds(0, Helpers.getResoureImageID(context, icon), 0, 0);
        rd.setGravity(Gravity.CENTER);
        rd.setTypeface(Typeface.create("sans-serif-condensed", Typeface.NORMAL));
        rd.setPadding(dpAsPixels, dpAsPixels, dpAsPixels, dpAsPixels);
        rd.setText(name);
        rd.setButtonDrawable(null);
        rd.setTag(Sub_id);
        rd.setOnCheckedChangeListener(subCategoryClickListener);

        return rd;
    }

    public static Button createTextButton(int tagDay, Context context, View.OnClickListener clickListener) {

        float scale = context.getResources().getDisplayMetrics().density;
        int dpAsPixels = (int) (10 * scale + 0.5f);
        int padding = (int) (15 * scale + 0.5f);
        LinearLayout.LayoutParams rd_param = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 4);
        rd_param.setMargins(0, dpAsPixels, 0, 0);
        Button tv = new Button(context);
        tv.setLayoutParams(rd_param);
        tv.setBackground(context.getDrawable(R.drawable.custom_indivitidual_edittext));
        tv.setClickable(true);
        tv.setCompoundDrawablesWithIntrinsicBounds(Helpers.getResoureImageID(context, "ic_watch"), 0, 0, 0);
        tv.setCompoundDrawablePadding(dpAsPixels);
        tv.setTypeface(Typeface.create("sans-serif-condensed", Typeface.NORMAL));
        tv.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
        tv.setPadding(padding, padding, padding, padding);
        tv.setText("-1");
        tv.setTag(tagDay);
        tv.setOnClickListener(clickListener);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
        return tv;
    }

    public static CheckBox createCheckBox(int tag, Context context) {
        float scale = context.getResources().getDisplayMetrics().density;
        int dpAsPixels = (int) (10 * scale + 0.5f);
        LinearLayout.LayoutParams rd_param = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        rd_param.setMargins(dpAsPixels, 0, 0, 0);
        CheckBox ck = new CheckBox(context);
        ck.setLayoutParams(rd_param);
        ck.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
        ck.setButtonTintList(ContextCompat.getColorStateList(context, R.color.tintcolor));
        ck.setGravity(Gravity.BOTTOM);
        ck.setTag(tag);
        return ck;

    }


    public static LinearLayout createLinearTimeItem(int tagItem, Context context, View.OnClickListener clickListener) {
        float scale = context.getResources().getDisplayMetrics().density;
        int dpAsPixels = (int) (10 * scale + 0.5f);
        LinearLayout.LayoutParams rd_param = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        LinearLayout linear = new LinearLayout(context);
        linear.setLayoutParams(rd_param);
        linear.setOrientation(LinearLayout.HORIZONTAL);
        Button newButton = Helpers.createTextButton(tagItem, context, clickListener);
        linear.addView(newButton);
        CheckBox ck = Helpers.createCheckBox(tagItem, context);
        linear.addView(ck);
//        ck.setVisibility(View.GONE);
        linear.setTag(tagItem);
        return linear;
    }

    public static void clearRadioGroup(RadioGroup group) {
        int group_size = group.getChildCount();
        if (group_size > 0) {
            for (int i = group_size - 1; i >= 0; --i) {
                group.removeViewAt(i);
            }
        }

    }
}
