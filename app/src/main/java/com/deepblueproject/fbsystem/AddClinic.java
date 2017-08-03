package com.deepblueproject.fbsystem;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.deepblueproject.fbsystem.FbsContract.ClinicEntry.CLINIC_TABLE;
import static com.deepblueproject.fbsystem.FbsContract.PatientEntry.PAT_REG;

public class AddClinic extends AppCompatActivity {

    private static final int SMS_PERM_CONST = 1;
    String smsBody;
    PendingIntent sentPendingIntent , deliveredPendingIntent;

    String[] perm = new String[] {Manifest.permission.SEND_SMS};

    private EditText et_email, et_address, et_phone;
    private Button et_submit;
    boolean a=false,b=false,c=false,d=false,e=false,f=false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_clinic);


        et_email = (EditText) findViewById(R.id.et_email_id);
        et_address = (EditText) findViewById(R.id.et_address_id);
        et_phone = (EditText) findViewById(R.id.et_phone_id);
        et_submit = (Button) findViewById(R.id.btn_submit_id);

        et_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if ((et_email.getText().toString().length() == 0) || (!et_email.getText().toString().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$"))) {
                    et_email.setError("Enter valid email");
                    c = et_email.requestFocus();
                    //c=false;
                }
                else
                    c=false;



                if (et_address.getText().toString().length() == 0) {
                    et_address.setError("Enter valid address");
                    e = et_address.requestFocus();
                    //e=false;
                }
                else
                    e=false;


                if ((et_phone.getText().toString().length() == 0)
                        || (!et_phone.getText().toString().matches("^(?:(?:\\+|0{0,2})91(\\s*[\\-]\\s*)?|[0]?)?[789]\\d{9}$"))) {
                    et_phone.setError("Enter valid phone number");
                    f = et_phone.requestFocus();
                    //f=false;
                }
                else
                    f=false;

                if(a==false && b==false && c==false && d==false && e==false && f==false)
                {
                    onAddClinic();

                    //Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();
                }


            }
        });
    }

    public void onAddClinic() {

        DbHelper mydb = new DbHelper(this);

        Date dNow = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("yyMMddhhmmss");
        String datetime = ft.format(dNow);

        ContentValues contentValues = new ContentValues();
        contentValues.put("cid", Long.parseLong(datetime));
        contentValues.put("address", et_address.getText().toString());
        contentValues.put("contact", et_phone.getText().toString());
        contentValues.put("email", et_email.getText().toString());
        // contentValues.put("blood_grp", et_bloodgrp.getText().toString());
        //contentValues.put("lang", et_lang.getText().toString());

        long check = mydb.insertDB(CLINIC_TABLE, contentValues);

        if (check != -1)
            Toast.makeText(this, "CID SMS SENT " + check, Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "Something wrong", Toast.LENGTH_SHORT).show();

        // String  Number = et_phone.getText().toString();
       smsBody = "\nCID: " + datetime;

       String SMS_SENT = "SMS_SENT";

        String SMS_DELIVERED = "SMS_DELIVERED";

         sentPendingIntent = PendingIntent.getBroadcast(this, 0, new Intent(SMS_SENT), 0);
         deliveredPendingIntent = PendingIntent.getBroadcast(this, 0, new Intent(SMS_DELIVERED), 0);

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

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, perm, SMS_PERM_CONST);
        }

        else
        {
            // Get the default instance of SmsManager
            SmsManager smsManager = SmsManager.getDefault();
            // Send a text based SMS
            smsManager.sendTextMessage(et_phone.getText().toString(), null, smsBody, sentPendingIntent, deliveredPendingIntent);
        }

    }

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
                smsManager.sendTextMessage(et_phone.getText().toString(), null, smsBody, sentPendingIntent, deliveredPendingIntent);
            }
        }
    }


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Clinic Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }




}


