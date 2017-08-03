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
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.Call;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import static com.deepblueproject.fbsystem.FbsContract.FbSecondEntry.FB_SECOND;
import static com.deepblueproject.fbsystem.FbsContract.PatientEntry.COL_CONTACT;
import static com.deepblueproject.fbsystem.FbsContract.PatientEntry.COL_NAME_FIRST;
import static com.deepblueproject.fbsystem.FbsContract.PatientEntry.COL_NAME_LAST;
import static com.deepblueproject.fbsystem.FbsContract.PatientEntry.COL_PID;
import static com.deepblueproject.fbsystem.FbsContract.PatientEntry.PAT_REG;


public class CallListPage extends AppCompatActivity
{

    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1, SMS_PERM_CONST = 2;

    String dbString, pname_str,phoneNumber,pid_str,smsBody,SMS_SENT,SMS_DELIVERED;
    PendingIntent sentPendingIntent;
    PendingIntent deliveredPendingIntent;

    String[] perm = new String[] {Manifest.permission.CALL_PHONE, Manifest.permission.SEND_SMS};
    DbHelper mydb;
    String[] columns;
    Cursor contact;
    int i;
    TableRow row ;
    TextView name_tv;
    Button sms_b;

    BroadcastReceiver sms_receiver ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_list_page);
        mydb = new DbHelper(this);

        IntentFilter filter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");

        sms_receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                sms_read(context,intent);
            }
        };

        registerReceiver(sms_receiver,filter);



        columns = new String[] {COL_NAME_FIRST,COL_NAME_LAST,COL_CONTACT,COL_PID};
       String[] WHERE_COND = new String[]{"0"};
        contact = mydb.getReadableDatabase().rawQuery("SELECT contact,pid,fname,lname FROM  CALL_LIST_TABLE INNER JOIN PAT_REG ON PAT_REG.pid = CALL_LIST_TABLE.pid_fk WHERE CALL_LIST_TABLE.fb2 = ?",WHERE_COND);
        i=0;
        int rowCount = contact.getCount();

        while (rowCount!=0)
       {    call_list_gen();
            rowCount--;
        }
    }


    public void call_list_gen()
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

        Button b = new Button(this);
        b.setText("CALL");
        b.setId(i);
        b.setTextSize(10);
        b.setOnClickListener(call_click);
        row.addView(b);

         sms_b = new Button(this);
        sms_b.setText("SMS");
        sms_b.setTextSize(10);
        sms_b.setOnClickListener(sms_click);
        row.addView(sms_b);

        i++;

    }





    View.OnClickListener call_click = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {

            contact.moveToPosition(view.getId());
            dbString = contact.getString(contact.getColumnIndex("contact"));
            Intent callIntent;

            callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + dbString));

            if (ActivityCompat.checkSelfPermission(CallListPage.this,
                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED)
            {
                ActivityCompat.requestPermissions(CallListPage.this, perm, MY_PERMISSIONS_REQUEST_CALL_PHONE);
            }

            else
                startActivity(callIntent);

        }

    };



    View.OnClickListener sms_click = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            dbString = contact.getString(contact.getColumnIndex("contact"));

            phoneNumber = dbString;
            smsBody = "PID:"+pid_str+"\nRate 1-Bad to 4-Excellent\n1)Health Improvement\n2)Medicine Effect\n3)System Rating\n(Example:- 432)";

            SMS_SENT = "SMS_SENT";

            SMS_DELIVERED = "SMS_DELIVERED";

            PendingIntent sentPendingIntent = PendingIntent.getBroadcast(CallListPage.this, 0, new Intent(SMS_SENT), 0);
            PendingIntent deliveredPendingIntent = PendingIntent.getBroadcast(CallListPage.this, 0, new Intent(SMS_DELIVERED), 0);

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

            if (ActivityCompat.checkSelfPermission(CallListPage.this,
                    Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED)
            {
                ActivityCompat.requestPermissions(CallListPage.this, perm, SMS_PERM_CONST);
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





    public void sms_read(Context context, Intent intent) {

        Bundle bundle = intent.getExtras();
        Object[] objarr = (Object[]) bundle.get("pdus");
        int[] pointR ;

        for (int i = 0; i < objarr.length; i++) {
            SmsMessage smsMsg;
            smsMsg = SmsMessage.createFromPdu((byte[]) objarr[i]);
            pointR = smsToInt(smsMsg);
            String smsNumber = smsMsg.getOriginatingAddress();

            if (pointR==null)
            {
                Toast.makeText(context, "Not in correct format!", Toast.LENGTH_SHORT).show();
            }

            else {
                DbHelper mydb = new DbHelper(this);
                ContentValues contentValues = new ContentValues();

                contentValues.put("Q1", pointR[0]);
                contentValues.put("Q2", pointR[1]);
                contentValues.put("Q3", pointR[2]);
                //contentValues.put("Q4", pointR[3]);
                //contentValues.put("Q5", pointR[4]);
                // contentValues.put("Q6", point[5] );

               mydb.insertFK_FID_PID_CONTACT(FB_SECOND,smsNumber,smsNumber);
                long check = mydb.insertDB(FB_SECOND,contentValues);

                if(check != -1)
                {
                    Toast.makeText(this, "New row added, row id: " + check, Toast.LENGTH_SHORT).show();
                    mydb.updateF2(pid_str);
                }
                else
                    Toast.makeText(this, "Something wrong", Toast.LENGTH_SHORT).show();

            }
            //  Toast.makeText(context,"ayaaaa!!!", Toast.LENGTH_SHORT).show();
        }
    }



    public int[] smsToInt(SmsMessage sms)
    {
        char[] sms_text = sms.getMessageBody().trim().toCharArray();
        int j=0,k=0,flag=0;
        int[] point = new int[3];
        for(int i=0; i< sms_text.length; i++)
        {
            char p = sms_text[i];

            if((p=='1' || p=='2' || p=='3' || p=='4') && j<=3 )
            {
                if(j==3 && sms_text.length > j)
                {return (point = null);}

                point[j] =  Character.getNumericValue(p);
                  j++;
            }
            else if(p == ' ')
            {

                continue;
            }
            else
            {
                flag=1;
                break;
            }
        }
        if(j!=3 || flag==1)
        {
            point = null;
        }

       return (point);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults)
    {
        switch (requestCode)
        {
            case MY_PERMISSIONS_REQUEST_CALL_PHONE:
            {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    Toast.makeText(CallListPage.this, "Permission granted", Toast.LENGTH_SHORT).show();

                    //String[] columns = new String[]{COL_CONTACT};
                    //Cursor contact = mydb.getContact(columns);

                    //contact.moveToFirst();

                    String dbString = contact.getString(contact.getColumnIndex("contact"));

                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:"+dbString));
                    startActivity(callIntent);
                }
                else
                    Toast.makeText(CallListPage.this, "Permission not granted", Toast.LENGTH_SHORT).show();

            }

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
