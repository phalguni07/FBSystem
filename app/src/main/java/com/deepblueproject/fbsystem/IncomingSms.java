/*package com.deepblueproject.fbsystem;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import static android.R.id.message;


public class IncomingSms extends BroadcastReceiver {

    Bundle bundle;
    //SmsMessage currentSMS;

    @Override
    public void onReceive(Context context, Intent intent) {


        Bundle bundle = intent.getExtras();
        Object[] objarr = (Object[])bundle.get("pdus");
        String sms ="";

        for(int i =0; i<objarr.length; i++)
        {
            SmsMessage smsMsg;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                String format = bundle.getString("format");
                smsMsg = SmsMessage.createFromPdu((byte[]) objarr[i], format);
            }
            else
            {
                smsMsg = SmsMessage.createFromPdu((byte[])objarr[i]);
            }
            String smsBody = smsMsg.getMessageBody();
            String senderNumber = smsMsg.getDisplayOriginatingAddress();
            sms += "From: " + senderNumber + "\t Content: " +smsBody ;
        }
        Toast.makeText(context, sms, Toast.LENGTH_SHORT).show();

        // Retrieves a map of extended data from the intent.
       /* if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {
            bundle = intent.getExtras();
            if (bundle != null) {
                Object[] pdu_Objects = (Object[]) bundle.get("pdus");
                if (pdu_Objects != null) {

                    for (Object aObject : pdu_Objects) {

                        currentSMS = getIncomingMessage(aObject, bundle);

                        String senderNo = currentSMS.getDisplayOriginatingAddress();

                        String message = currentSMS.getDisplayMessageBody();

                    }
                    this.abortBroadcast();
                    // End of loop
                }
                Toast.makeText(this, "senderNum: " + senderNo + " :\n message: " + message, Toast.LENGTH_LONG).show();

            }
        } // bundle null*/
    //}



/*
    private SmsMessage getIncomingMessage(Object aObject, Bundle bundle) {
        SmsMessage currentSMS;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String format = bundle.getString("format");
            currentSMS = SmsMessage.createFromPdu((byte[]) aObject, format);
        } else {
            currentSMS = SmsMessage.createFromPdu((byte[]) aObject);
        }
        return currentSMS;
    }




}*/