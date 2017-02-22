package lalinw.washington.edu.awty;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by IreneW on 2017-02-21.
 */

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        // For our recurring task, we'll just display a message
        Log.i("onRECEIVE", "run run run");
        Toast.makeText(context, "AWTY is running nowwww", Toast.LENGTH_SHORT).show();
    }
}
