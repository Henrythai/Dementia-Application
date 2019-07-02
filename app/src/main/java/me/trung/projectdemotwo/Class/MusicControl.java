package me.trung.projectdemotwo.Class;

import android.content.Context;
import android.media.MediaPlayer;
import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;

import me.trung.projectdemotwo.R;

public class MusicControl {
    private static MusicControl sInstance;
    private Context mContext;
    private MediaPlayer mMediaPlayer;

    public MusicControl(Context context) {
        mContext = context;
    }

    public static MusicControl getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new MusicControl(context);
        }
        return sInstance;
    }

    public void playMusic(String pathSave) {

        if (!TextUtils.isEmpty(pathSave)) {
            mMediaPlayer = new MediaPlayer();
            try {
                mMediaPlayer.setDataSource(pathSave);
                mMediaPlayer.prepare();
                mMediaPlayer.start();
                Log.e("Service_mc", "Main Playing.............");
            } catch (IOException e) {
                Log.e("Service_mc", "Catch Playing.............");
                playDefaultMusic();
            }
        } else {
            playDefaultMusic();
        }
    }

    private void playDefaultMusic() {
        mMediaPlayer = MediaPlayer.create(mContext, R.raw.noticationsound);
        Log.e("Service_mc", "Playing.............");
        mMediaPlayer.start();
    }

    public void stopMusic() {
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.seekTo(0);
            Log.e("Service_mc", "Stop.............");
        }
    }
}
