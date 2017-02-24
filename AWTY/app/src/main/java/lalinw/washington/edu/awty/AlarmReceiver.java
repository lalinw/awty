package lalinw.washington.edu.awty;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by IreneW on 2017-02-21.
 */

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        // For our recurring task, we'll just display a message
        String phone = intent.getStringExtra("phone");
        String msg = intent.getStringExtra("message");

        String phoneStyled = "(" + phone.substring(0, 3) + ") " + phone.substring(3, 6) + "-" + phone.substring(6);
        String phn_msg = phoneStyled + ": " + msg;
        Toast.makeText(context, phn_msg, Toast.LENGTH_SHORT).show();
    }
}
