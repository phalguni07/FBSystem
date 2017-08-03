package com.deepblueproject.fbsystem;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;

import static android.R.attr.phoneNumber;
import static com.deepblueproject.fbsystem.FbsContract.PatientEntry.FEMALE;
import static com.deepblueproject.fbsystem.FbsContract.PatientEntry.MALE;
import static com.deepblueproject.fbsystem.FbsContract.PatientEntry.PAT_REG;

public class Clinic extends AppCompatActivity {

    private static final int SMS_PERM_CONST = 1;
    String smsBody;
    PendingIntent sentPendingIntent , deliveredPendingIntent;

    String[] perm = new String[] {Manifest.permission.SEND_SMS};

    private EditText et_fname, et_lname, et_email, et_address, et_phone, et_bloodgrp , et_lang;
    private int rb_gender;
    boolean a=false,b=false,c=false,d=false,e=false,f=false;
    private Button et_submit;

   //IncomingSms incomingSms;

    //private Matcher
    private EditText pDisplayDate;
    private Button pPickDate;
    private int pYear;
    private int pMonth;
    private int pDay;



    static final int DATE_DIALOG_ID = 0;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    private DatePickerDialog.OnDateSetListener pDateSetListener =
            new DatePickerDialog.OnDateSetListener() {

                public void onDateSet(DatePicker view, int year,
                                      int monthOfYear, int dayOfMonth) {
                    pYear = year;
                    pMonth = monthOfYear;
                    pDay = dayOfMonth;
                    updateDisplay();
                    displayToast();
                }
            };

    private void updateDisplay() {
        pDisplayDate.setText(
                new StringBuilder()
                        // Month is 0 based so add 1

                        .append(pDay).append("/")
                        .append(pMonth + 1).append("/")
                        .append(pYear).append(" "));
    }

    private void displayToast() {
        Toast.makeText(this, new StringBuilder().append("Date choosen is ").append(pDisplayDate.getText()),  Toast.LENGTH_SHORT).show();

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clinic);

      //  et_lang = (EditText) findViewById(R.id.et_lang_id);
        et_fname = (EditText) findViewById(R.id.et_fname_id);
        et_lname = (EditText) findViewById(R.id.et_lname_id);
        et_email = (EditText) findViewById(R.id.et_email_id);
        et_address = (EditText) findViewById(R.id.et_address_id);
        et_phone = (EditText) findViewById(R.id.et_phone_id);
        et_submit = (Button) findViewById(R.id.btn_submit_id);
        pDisplayDate = (EditText) findViewById(R.id.et_dob_id);
        pPickDate = (Button) findViewById(R.id.b_pickDate_id);
        //et_bloodgrp = (EditText) findViewById(R.id.et_bloodgrp_id);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.

        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

        pPickDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                createdDialog(DATE_DIALOG_ID).show();
            }
        });
        final Calendar cal = Calendar.getInstance();
        pYear = cal.get(Calendar.YEAR);
        pMonth = cal.get(Calendar.MONTH);
        pDay = cal.get(Calendar.DAY_OF_MONTH);

        updateDisplay();


        et_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if ((et_fname.getText().toString().length() == 0) || (!et_fname.getText().toString().matches("[a-zA-Z]+"))) {
                    et_fname.setError("Enter valid first name");
                    a = et_fname.requestFocus();
                }
                else
                    a=false;


                if ((et_lname.getText().toString().length() == 0) || (!et_lname.getText().toString().matches("[a-zA-Z]+"))) {
                    et_lname.setError("Enter valid last name");
                    b = et_lname.requestFocus();
                    //b=false;
                }
                else
                    b=false;

                if ((et_email.getText().toString().length() == 0) || (!et_email.getText().toString().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$"))) {
                    et_email.setError("Enter valid email");
                    c = et_email.requestFocus();
                    //c=false;
                }
                else
                    c=false;


               /* if ((et_dob.getText().toString().length() == 0) ||
                        (!et_dob.getText().toString().matches("^(0[1-9]|[12][0-9]|3[01])[- /.](0[1-9]|1[012])[- /.](19|20)\\d\\d$"))) {
                    et_dob.setError("Enter valid Date of birth");
                    d = et_dob.requestFocus();
                    //d=false;
                }

                 else
                 d=false;*/

/*
                if ((et_bloodgrp.getText().toString().length() == 0) || (!et_bloodgrp.getText().toString().matches("^(A|B|AB|O)[+-]?$"))) {
                    et_bloodgrp.setError("Enter valid Blood Group");
                    d=et_bloodgrp.requestFocus();
                }
                else
                    d=false;
*/


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
                    onSubmitReg();

                    //Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();
                }


            }
        });
    }



    protected Dialog  createdDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this,
                        pDateSetListener,
                        pYear, pMonth, pDay);
        }
        return null;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        setTitle("Clinic");
        getMenuInflater().inflate(R.menu.clinic_menu, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
switch(id)
{
    case R.id.CallListId:

        Intent CallListIntent = new Intent(this, CallListPage.class);
        startActivity(CallListIntent);
        return true;

    case R.id.StaffRegId :

        Intent StaffRegIntent = new Intent(this, StaffReg.class);
        startActivity(StaffRegIntent);
        return true;

    case R.id.AddDocId:

        Intent AddDocIntent = new Intent(this, AddClinic.class);
        startActivity(AddDocIntent);
        return true;

}
        return true;
    }

    public void onClickRadioButton(View view) {
        int id = view.getId();

        switch (id) {
            case R.id.rb_female_id:
                rb_gender = FEMALE;
                break;
            case R.id.rb_male_id:
                rb_gender = MALE;
                break;
        }
    }


    public void onSubmitReg() {

        DbHelper mydb = new DbHelper(this);

        Date dNow = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("yyMMddhhmmss");
        String datetime = ft.format(dNow);

        ContentValues contentValues = new ContentValues();
        contentValues.put("pid", Long.parseLong(datetime));
        contentValues.put("fname", et_fname.getText().toString());
        contentValues.put("lname", et_lname.getText().toString());
        contentValues.put("gender", rb_gender);
        contentValues.put("dob", pDisplayDate.getText().toString());
        contentValues.put("address", et_address.getText().toString());
        contentValues.put("contact", et_phone.getText().toString());
        contentValues.put("email", et_email.getText().toString());
       // contentValues.put("blood_grp", et_bloodgrp.getText().toString());
        //contentValues.put("lang", et_lang.getText().toString());

        long check = mydb.insertDB(PAT_REG, contentValues);

        if (check != -1)
            Toast.makeText(Clinic.this, "PID SMS SENT " + check, Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(Clinic.this, "Something wrong", Toast.LENGTH_SHORT).show();

      String  Number = et_phone.getText().toString();
       smsBody = "PATIENT :" + et_fname.getText().toString() +"\nPID: " + datetime;

       String SMS_SENT = "SMS_SENT";

        String SMS_DELIVERED = "SMS_DELIVERED";

         sentPendingIntent = PendingIntent.getBroadcast(Clinic.this, 0, new Intent(SMS_SENT), 0);
         deliveredPendingIntent = PendingIntent.getBroadcast(Clinic.this, 0, new Intent(SMS_DELIVERED), 0);

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

        if (ActivityCompat.checkSelfPermission(Clinic.this,
                Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(Clinic.this, perm, SMS_PERM_CONST);
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

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }


}
