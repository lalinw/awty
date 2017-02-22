package lalinw.washington.edu.awty;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public PendingIntent pendingIntent;
    private static boolean start = false;
    public AlarmManager alarmManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //red hex #e60000
        //green hex #a4c639

        final EditText et_msg = (EditText) findViewById(R.id.message);
        final EditText et_phn = (EditText) findViewById(R.id.phone);
        final EditText et_frq = (EditText) findViewById(R.id.time);

        /* Retrieve a PendingIntent that will perform a broadcast */
        Intent alarmIntent = new Intent(MainActivity.this, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, alarmIntent, 0);

        final Button stst = (Button) findViewById(R.id.start_stop);
        stst.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String msg = et_msg.getText().toString();
                String phn = et_phn.getText().toString();
                String frq_str = et_frq.getText().toString();
                int frq = Integer.parseInt(frq_str);

                if (start) {
                    //alarm manager is running, stop the service
                    start = false;
                    stst.setText("Start");
                    stst.setBackgroundColor(Color.parseColor("#a4c639"));
                    cancel();
                } else {
                    //starts the alarm manager
                    start = true;
                    stst.setText("Stop");
                    stst.setBackgroundColor(Color.parseColor("#e60000"));
                    start(msg, phn, frq);
                }
            }
        });

    }

    public void start(String message, String phone, int minutes) {
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        int interval = 1000 * 2 * minutes; //time in milliseconds
        String phoneStyled = "(" + phone.substring(0, 3) + ") " + phone.substring(3, 6) + "-" + phone.substring(6);

        manager.setRepeating(AlarmManager.RTC_WAKEUP, SystemClock.elapsedRealtime(), interval, pendingIntent);
        Toast.makeText(this, phoneStyled + ": " + message, Toast.LENGTH_SHORT).show();
    }

    public void cancel() {
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        manager.cancel(pendingIntent);
        Toast.makeText(this, "AWTY stopped", Toast.LENGTH_SHORT).show();
    }


}
