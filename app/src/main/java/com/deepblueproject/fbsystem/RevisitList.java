package com.deepblueproject.fbsystem;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import static com.deepblueproject.fbsystem.FbsContract.CallListEntry.CALL_LIST_TABLE;
import static com.deepblueproject.fbsystem.FbsContract.PatientEntry.COL_CONTACT;
import static com.deepblueproject.fbsystem.FbsContract.PatientEntry.COL_NAME_FIRST;
import static com.deepblueproject.fbsystem.FbsContract.PatientEntry.COL_NAME_LAST;
import static com.deepblueproject.fbsystem.FbsContract.PatientEntry.COL_PID;
import static com.deepblueproject.fbsystem.FbsContract.PatientEntry.PAT_REG;

public class RevisitList extends AppCompatActivity
{
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1, SMS_PERM_CONST = 2;

    String dbString, pname_str,phoneNumber,pid_str,smsBody,SMS_SENT,SMS_DELIVERED;
    PendingIntent sentPendingIntent;
    PendingIntent deliveredPendingIntent;
    Button sms_b;


    String[] perm = new String[] { Manifest.permission.SEND_SMS};



    DbHelper mydb;
    String[] columns;
    Cursor contact;
    int i;
    TableRow row;
    TextView name_tv;
    String lname_str, fname_str;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revisit_list);

        mydb = new DbHelper(this);

        i=0;
        columns = new String[]{COL_NAME_LAST ,COL_NAME_FIRST,COL_PID};
        //contact = mydb.getDataWithWhere(CALL_LIST_TABLE,columns);
        //contact1 = mydb.getDataWithWhere(CALL_LIST_TABLE,columns);
        String[] WHERE_COND = new String[]{"1"};

        contact = mydb.getReadableDatabase().rawQuery("SELECT contact,pid,fname,lname FROM  CALL_LIST_TABLE INNER JOIN PAT_REG ON PAT_REG.pid = CALL_LIST_TABLE.fk_pid WHERE CALL_LIST_TABLE.fb2 = ?",WHERE_COND);

        int rowCount = contact.getCount();


        while (rowCount!=0)

        {
            revisit_list_gen();
            rowCount--;
        }

    }

    public void revisit_list_gen()
    {
        TableLayout table = (TableLayout) findViewById( R.id.call_tab_1_id );
        row = new TableRow( this );
        table.addView( row );

        name_tv = new TextView(this);
        contact.moveToPosition(i);
        pname_str = contact.getString(contact.getColumnIndex("fname"));
        pname_str += "  ";
        pname_str += contact.getString(contact.getColumnIndex("lname"));
        pname_str += "  ";
        // pname_str += contact.getString(contact.getColumnIndex("fb1"));
        // pname_str += contact.getString(contact.getColumnIndex("fb2"));

        name_tv.setText(pname_str);
        pid_str = "\n";
        pid_str += contact.getString(contact.getColumnIndex("pid"));
        name_tv.append(pid_str);
        row.addView(name_tv);


        sms_b = new Button(this);
        sms_b.setText("Revisit!");
        sms_b.setTextSize(10);
        sms_b.setOnClickListener(sms_click);
        row.addView(sms_b);

        i++;


        /*TableLayout table = (TableLayout) findViewById( R.id.call_tab_1_id );

        row = new TableRow( this );

        table.addView( row );


        name_tv = new TextView(this);
        contact.moveToPosition(i);
        fname_str = contact.getString(contact.getColumnIndex("fname"));
        name_tv.setText(fname_str);
        lname_str = contact.getString(contact.getColumnIndex("lname"));
        name_tv.append("  "+lname_str);
        row.addView(name_tv);

        CheckBox cb = new CheckBox(getApplicationContext());
        cb.setText("Revisit!");
        row.addView(cb);

        i++;*/
    }

    View.OnClickListener sms_click = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            dbString = contact.getString(contact.getColumnIndex("contact"));

            phoneNumber = dbString;
            smsBody = "Please Revisit the Clinic. :)!";

            SMS_SENT = "SMS_SENT";

            SMS_DELIVERED = "SMS_DELIVERED";

            PendingIntent sentPendingIntent = PendingIntent.getBroadcast(RevisitList.this, 0, new Intent(SMS_SENT), 0);
            PendingIntent deliveredPendingIntent = PendingIntent.getBroadcast(RevisitList.this, 0, new Intent(SMS_DELIVERED), 0);

// For when the SMS has been sent
            registerReceiver(new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {

                    switch (getResultCode()) {
                        case Activity.RESULT_OK:
                            Toast.makeText(context, "SMS sent successfully", Toast.LENGTH_SHORT).show();
                            break;
                        case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                            Toast.makeText(context, "Generic failure cause", Toast.LENGTH_SHORT).show();
                            break;
                        case SmsManager.RESULT_ERROR_NO_SERVICE:
                            Toast.makeText(context, "Service is currently unavailable", Toast.LENGTH_SHORT).show();
                            break;
                        case SmsManager.RESULT_ERROR_NULL_PDU:
                            Toast.makeText(context, "No pdu provided", Toast.LENGTH_SHORT).show();
                            break;
                        case SmsManager.RESULT_ERROR_RADIO_OFF:
                            Toast.makeText(context, "Radio was explicitly turned off", Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
            }, new IntentFilter(SMS_SENT));

// For when the SMS has been delivered
            registerReceiver(new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    switch (getResultCode())
                    {
                        case Activity.RESULT_OK:
                            Toast.makeText(getBaseContext(), "SMS delivered", Toast.LENGTH_SHORT).show();
                            break;
                        case Activity.RESULT_CANCELED:
                            Toast.makeText(getBaseContext(), "SMS not delivered", Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
            }, new IntentFilter(SMS_DELIVERED));

            if (ActivityCompat.checkSelfPermission(RevisitList.this,
                    Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED)
            {
                ActivityCompat.requestPermissions(RevisitList.this, perm, SMS_PERM_CONST);
            }

            else
            {
                // Get the default instance of SmsManager
                SmsManager smsManager = SmsManager.getDefault();
                // Send a text based SMS
                smsManager.sendTextMessage(phoneNumber, null, smsBody, sentPendingIntent, deliveredPendingIntent);
            }
        }
    };


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults)
    {
        switch (requestCode)
        {
            case SMS_PERM_CONST :
            {
                // Get the default instance of SmsManager
                SmsManager smsManager = SmsManager.getDefault();
                // Send a text based SMS
                smsManager.sendTextMessage(phoneNumber, null, smsBody, sentPendingIntent, deliveredPendingIntent);
            }
        }
    }

}
