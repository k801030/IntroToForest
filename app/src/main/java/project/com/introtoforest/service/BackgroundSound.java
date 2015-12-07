package project.com.introtoforest.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;

import project.com.introtoforest.R;

/**
 * Created by Vison on 2015/12/7.
 */
public class BackgroundSound extends Service {

    MediaPlayer mp;
    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }
    public void onCreate()
    {
        mp = MediaPlayer.create(this, R.raw.background);
        mp.setLooping(true);
        mp.start();
    }
    public void onDestroy()
    {
        mp.stop();
    }


}
