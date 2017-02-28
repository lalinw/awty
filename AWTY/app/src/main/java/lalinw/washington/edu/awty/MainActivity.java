package lalinw.washington.edu.awty;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public PendingIntent pendingIntent;
    public Intent alarmIntent;
    private static boolean start = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button stst = (Button) findViewById(R.id.start_stop);

        if (start) {
            stst.setText("Stop");
            stst.setBackgroundColor(Color.parseColor("#e60000"));
        } else {
            stst.setText("Start");
            stst.setBackgroundColor(Color.parseColor("#a4c639"));
        }
        //red hex #e60000
        //green hex #a4c639


        final EditText et_msg = (EditText) findViewById(R.id.message);
        final EditText et_phn = (EditText) findViewById(R.id.phone);
        final EditText et_frq = (EditText) findViewById(R.id.time);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (MainActivity.this.checkSelfPermission(Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                MainActivity.this.requestPermissions(new String[] {
                        Manifest.permission.SEND_SMS
                }, 1);
            }
        }

        stst.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                /* Retrieve a PendingIntent that will perform a broadcast */

                alarmIntent = new Intent(getApplicationContext(), AlarmReceiver.class);
                pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                String msg = et_msg.getText().toString();
                String phn = et_phn.getText().toString();
                String frq_str = et_frq.getText().toString();
                int frq = Integer.parseInt(frq_str);

                if (msg.length() > 0 && phn.length() > 3 && frq > 0) {
                    if (start) {
                        //alarm manager is running, stop the service
                        start = false;
                        stst.setText("Start");
                        stst.setBackgroundColor(Color.parseColor("#a4c639"));
                        cancel();
                    } else {
                        //starts the alarm manager
                        alarmIntent.putExtra("phone", phn);
                        alarmIntent.putExtra("message", msg);

                        pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                        start = true;
                        stst.setText("Stop");
                        stst.setBackgroundColor(Color.parseColor("#e60000"));
                        start(frq);
                    }

                } else {
                    AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                    alertDialog.setTitle("Attention!");
                    alertDialog.setMessage("Please fill in all fields with valid values.");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                }


            }
        });

    }

    public void start(int minutes) {
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        int interval = 1000 * 60 * minutes; //time in milliseconds
        manager.setInexactRepeating(AlarmManager.RTC_WAKEUP, SystemClock.elapsedRealtime(), interval, pendingIntent);
        Toast.makeText(this, "AWTY started", Toast.LENGTH_SHORT).show();
    }

    public void cancel() {
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        manager.cancel(pendingIntent);
        Toast.makeText(this, "AWTY stopped", Toast.LENGTH_SHORT).show();
    }


}
