package a20156410.chuthetai.com.timeanddatedialog;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

/**
 * Created by Admin on 11/6/2017.
 */

public class Music extends Service {
    int id;
    MediaPlayer mp;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String NhanKQ=intent.getExtras().getString("extra");
        //String NhanKQ1=intent.getExtras().getString("extra1");
        if (NhanKQ.equals("on")) {
            id =1;
        }
        else if (NhanKQ.equals("off"))
        {
            id = 0;
        }

        if (id == 1)
        {
            mp= MediaPlayer.create( this,R.raw.chuong );
            mp.start();

        }
        else if(id==0)
        {
            mp.stop();
            mp.reset();
        }

        Toast.makeText(this, NhanKQ, Toast.LENGTH_LONG).show();
        return START_NOT_STICKY;
    }
}
