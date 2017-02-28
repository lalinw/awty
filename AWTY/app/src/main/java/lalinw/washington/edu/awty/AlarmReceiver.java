package lalinw.washington.edu.awty;

import android.Manifest;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by IreneW on 2017-02-21.
 */

public class AlarmReceiver extends BroadcastReceiver {

    private Context cntx;

    @Override
    public void onReceive(Context context, Intent intent) {

        // For our recurring task, we'll just display a message
        String phone = intent.getStringExtra("phone");
        String msg = intent.getStringExtra("message");

        //String phoneStyled = "(" + phone.substring(0, 3) + ") " + phone.substring(3, 6) + "-" + phone.substring(6);
        //String phn_msg = phoneStyled + ": " + msg;
        //Toast.makeText(context, phone + msg, Toast.LENGTH_SHORT).show();


        Intent smsIntent = new Intent(context, AlarmReceiver.class);
        PendingIntent pi = PendingIntent.getActivity(context, 0, smsIntent,0);
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phone, null, msg, pi, null);


    }


}
