package a20156410.chuthetai.com.timeanddatedialog;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Admin on 11/6/2017.
 */

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String chuoi=intent.getExtras().getString("extra");
        //String chuoi1=intent.getExtras().getString("extra1");
        Intent myintent = new Intent(context, Music.class);
        myintent.putExtra("extra",chuoi);
        //myintent.putExtra("extra1",chuoi1);
        context.startService(myintent);
    }
}
