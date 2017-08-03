package com.deepblueproject.fbsystem;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

//import static com.deepblueproject.fbsystem.FbsContract.PatientEntry.COL_BLD_GRP;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


import static com.deepblueproject.fbsystem.FbsContract.PatientDetailsEntry.PATIENT_DETAILS_TABLE;

import static com.deepblueproject.fbsystem.R.id.et_docpid_id;
import static com.deepblueproject.fbsystem.R.id.et_pid_id;

public class Doctor extends AppCompatActivity {

    String dbString1;
    DbHelper mydb = new DbHelper(this);
    String[] columns ;
    Cursor cursor;
    TextView myText;
    private EditText et_obs , et_days,et_test,et_notes;
    private Button et_done;
    EditText userInput;
    String use1;
    boolean a=false,b=false,c=false,d=false;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);

        et_obs = (EditText) findViewById(R.id.et_dis_id);
        et_days = (EditText) findViewById(R.id.et_days_id);
        et_test = (EditText) findViewById(R.id.et_testname_id);
        et_notes = (EditText) findViewById(R.id.et_notedisp_id);
        userInput = (EditText) findViewById(et_docpid_id);



        TextView pid_fb1_tv = (TextView) findViewById(R.id.pid_2show);

        // bundle = getIntent().getExtras();
        use1 =getIntent().getStringExtra("name");
        String use2 =getIntent().getStringExtra("display");
        pid_fb1_tv.setText(use1);
        pid_fb1_tv.append("\n");
        pid_fb1_tv.append(use2);


        //Button button = (Button) findViewById(R.id.btn_retrieve_id);
        Button button1 = (Button) findViewById(R.id.btn_done_id);
        //button.setTextSize(10);
        //button.setWidth(25);

        /*button.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View arg0)
            {
                onSubmitRetrieve();
            }
        });
*/

        button1.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View arg0)
            {
                if ((et_obs.getText().toString().length() == 0) || (!et_obs.getText().toString().matches("[a-zA-Z]+"))) {
                    et_obs.setError("Enter observations");
                    a = et_obs.requestFocus();
                }
                else
                    a=false;

                if ((et_test.getText().toString().length() == 0) || (!et_test.getText().toString().matches("[a-zA-Z]+"))) {
                    et_test.setError("Enter tests");
                    b = et_test.requestFocus();
                }
                else
                    b=false;

                if ((et_notes.getText().toString().length() == 0) || (!et_notes.getText().toString().matches("[a-zA-Z]+"))) {
                    et_notes.setError("Enter notes");
                    c = et_notes.requestFocus();
                }
                else
                    c=false;

                if ((et_days.getText().toString().length() == 0) || (et_days.getText().toString().matches("[a-zA-Z]+"))) {
                    et_days.setError("Enter course of medication");
                    d = et_days.requestFocus();
                }
                else
                    d=false;

                if(a==false && b==false && c==false && d==false)
                {
                    onSubmitDetails();

                    //Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    public void onSubmitDetails() {

        String pid_from_et = userInput.getText().toString();


        DbHelper mydb = new DbHelper(this);

        Date dNow = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("yyMMddhhmmssMs");
        String datetime = ft.format(dNow);


        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());

        ContentValues contentValues = new ContentValues();
        contentValues.put("fid", datetime);

        contentValues.put("obs", et_obs.getText().toString());
        contentValues.put("days", et_days.getText().toString());
        contentValues.put("notes", et_notes.getText().toString());
        contentValues.put("test", et_test.getText().toString());
        contentValues.put("date", currentDateTimeString);

        mydb.insertFK_PD(pid_from_et);

        long check = mydb.insertDB(PATIENT_DETAILS_TABLE, contentValues);

        if (check != -1)
            Toast.makeText(Doctor.this, "New patient details, row id: " + check, Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(Doctor.this, "Something wrong", Toast.LENGTH_SHORT).show();

    }
    /*
    public void onSubmitRetrieve()
    {
       // columns = new String[] {COL_PID,COL_NAME_FIRST,COL_NAME_LAST,COL_GENDER};
        columns = new String[] {COL_FK_PID,COL_FK_DID,COL_FID,COL_OBS,COL_NOTES,COL_DAYS,COL_DATE,COL_TEST};
        cursor = mydb.getData(PATIENT_DETAILS_TABLE,columns);
        dbString1 = userInput.getText().toString();
         String dbString = "";
        cursor.moveToFirst();
        while(!cursor.isAfterLast())
        {
            String temp = cursor.getString(cursor.getColumnIndex("fk_pid"));
            if (dbString1.equals(temp))
            {
                    dbString += cursor.getString(cursor.getColumnIndex("fk_pid"));
                    dbString += "      ";
                    dbString += cursor.getString(cursor.getColumnIndex("date"));
                    dbString += "      ";
                    dbString += cursor.getString(cursor.getColumnIndex("obs"));
                    dbString += "      ";
                    //dbString += cursor.getString(cursor.getColumnIndex("bld_grp"));
                    //dbString += "      ";
                    dbString += cursor.getString(cursor.getColumnIndex("days"));
                    dbString += "      ";

                    myText = (TextView) findViewById(R.id.doc_report_id);
                    myText.append(dbString);

            }
            cursor.moveToNext();
        }
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        setTitle("Doctor");
        getMenuInflater().inflate( R.menu.doctor_menu, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        if (id == R.id.RevisitId) {

            Intent RevisitListIntent = new Intent(this, RevisitList.class);
            startActivity(RevisitListIntent);

        }
        return true;

    }

}
