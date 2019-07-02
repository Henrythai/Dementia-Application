package me.trung.projectdemotwo.AlarmService;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.JobIntentService;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.Calendar;

import me.trung.projectdemotwo.Class.Key;
import me.trung.projectdemotwo.Class.MusicControl;
import me.trung.projectdemotwo.Model.ModelTask;
import me.trung.projectdemotwo.Model.Task;
import me.trung.projectdemotwo.R;
import me.trung.projectdemotwo.Sql.TableTask;
import me.trung.projectdemotwo.Sql.TableTaskDefault;
import me.trung.projectdemotwo.activities.MissionActivity;

public class AlarmService extends JobIntentService {

    /* Give the Job a Unique Id */
    private static final int JOB_ID = 1000;

    public static void enqueueWork(Context ctx, Intent intent) {
        enqueueWork(ctx, AlarmService.class, JOB_ID, intent);

    }

    private Intent createIntent(String ReceiverId, Context context) {
        Intent intent = new Intent(context, MissionActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(Key.EXTRA_REMINDER_ID, ReceiverId);
        return intent;
    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        Log.e("my Service", "service Welcome");
        int mReceiverID = Integer.parseInt(intent.getStringExtra(Key.EXTRA_REMINDER_ID));
        // Get notification title from Reminder Database
        TableTask rb = new TableTask(this);
        TableTaskDefault taskDefault = new TableTaskDefault(this);
        Task reminder = rb.getTaskById(mReceiverID);
        ModelTask modelTask = taskDefault.getMTaskById(reminder.getTaskID());
        String mTitle = modelTask.getTask_name();

        Intent missionIntent = createIntent(Integer.toString(mReceiverID), this);
        PendingIntent missionPIntent = PendingIntent.getActivity(this, mReceiverID, missionIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent snoozeItent = new Intent(this, SetAlarmSnoozeReceiver.class);
        snoozeItent.setAction("Snooze");
        snoozeItent.putExtra(Key.EXTRA_REMINDER_ID, Integer.toString(mReceiverID));
        PendingIntent pendingIntentSnooze = PendingIntent.getBroadcast(this, -mReceiverID, snoozeItent, PendingIntent.FLAG_CANCEL_CURRENT);

        Intent stopItent = new Intent(this, SetAlarmSnoozeReceiver.class);
        stopItent.setAction("Stop");
        stopItent.putExtra(Key.EXTRA_REMINDER_ID, Integer.toString(mReceiverID));
        PendingIntent pendingIntentStop = PendingIntent.getBroadcast(this, -mReceiverID, stopItent, PendingIntent.FLAG_CANCEL_CURRENT);


        String channelId = "Chanel1";
        // Create Notification
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, channelId)
                .setLargeIcon(BitmapFactory.decodeResource(this.getResources(), R.mipmap.ic_icon_image_round))
                .setSmallIcon(R.drawable.logo)
                .setContentTitle(this.getResources().getString(R.string.app_name))
                .setTicker(mTitle)
                .setContentIntent(missionPIntent)
                .setContentText(mTitle)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setAutoCancel(true)
                .setOnlyAlertOnce(true);

        //fix icon
        mBuilder.addAction(android.R.drawable.stat_notify_more, "Snooze", pendingIntentSnooze);
        mBuilder.addAction(android.R.drawable.ic_notification_clear_all, "Stop", pendingIntentStop);
        mBuilder.setDeleteIntent(pendingIntentStop);

        NotificationManager nManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "Chanel1",
                    NotificationManager.IMPORTANCE_HIGH);

            nManager.createNotificationChannel(channel);
        }


        nManager.notify(mReceiverID, mBuilder.build());
        String pathSave = reminder.getSoundName();
        if (pathSave != null && pathSave.length() > 0 && !pathSave.equals("N/A")) {
            MusicControl.getInstance(this).playMusic(pathSave);
        } else {
            MusicControl.getInstance(this).playMusic("");
        }

        //delete if exist
        AlarmReceiver alarmReceiver = new AlarmReceiver();
        alarmReceiver.cancelAlarm(this, -mReceiverID);

        Calendar now = Calendar.getInstance();
        now.add(Calendar.MINUTE, 5);
        alarmReceiver.setAlarm(this, now, mReceiverID);


    }


}
