package com.deepblueproject.fbsystem;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static com.deepblueproject.fbsystem.FbsContract.DoctorEntry.COL_DID;
import static com.deepblueproject.fbsystem.FbsContract.DoctorEntry.COL_DOC_NAME_FIRST;
import static com.deepblueproject.fbsystem.FbsContract.DoctorEntry.COL_DOC_NAME_LAST;
import static com.deepblueproject.fbsystem.FbsContract.DoctorEntry.DOCTOR_TABLE;
import static com.deepblueproject.fbsystem.FbsContract.PatientEntry.COL_NAME_FIRST;
import static com.deepblueproject.fbsystem.FbsContract.PatientEntry.COL_NAME_LAST;
import static com.deepblueproject.fbsystem.FbsContract.PatientEntry.COL_PID;
import static com.deepblueproject.fbsystem.FbsContract.PatientEntry.PAT_REG;
import static com.deepblueproject.fbsystem.R.id.et_pid_id;
import static com.deepblueproject.fbsystem.R.id.pid_show;

public class MainMenu extends AppCompatActivity {

    Button button,button1,button2;
    DbHelper mydb = new DbHelper(this);
    String[] columns, details;
    Cursor c,d;
    String dbString;
    int f = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_main_menu);

        //button2 = (Button) findViewById(R.id.btn_fb2_id);

      button1 = (Button) findViewById(R.id.btn_doc_id);
       button = (Button) findViewById(R.id.btn_fb_id);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                // get prompts.xml view
               LayoutInflater li = LayoutInflater.from(MainMenu.this);
        View promptsView = li.inflate(R.layout.dialog_pid, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                MainMenu.this);

        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        final EditText userInput = (EditText) promptsView
                .findViewById(et_pid_id);
                columns = new String[] {COL_PID,COL_NAME_FIRST,COL_NAME_LAST};
                //details = new String[] {COL_NAME_FIRST,COL_NAME_LAST};
        c = mydb.getData(PAT_REG,columns);
       // d = mydb.getData(PAT_REG,details);

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id)
                            {
                                dbString =userInput.getText().toString();
                               // long num = Long.parseLong(dbString);
                                c.moveToFirst();
                               // d.moveToFirst();
                                int flag=0;
                                while (!c.isAfterLast())
                                {
                                   if( (userInput.getText().toString().matches("[a-zA-Z]+")))
                                   {
                                       f = 1;
                                   }
                                    String temp =(c.getString(c.getColumnIndex("pid")));
                                    if(dbString.equals(temp) && dbString != " " && f!=1 )
                                    {
                                        flag=1;
                                        Intent intent3 = new Intent(MainMenu.this, FbFormFirst.class);
                                       // AutoCompleteTextView textView = (AutoCompleteTextView) findViewById(R.id.autocomplete);
                                        //String getrec=userInput.getText().toString();
                                        String name_disp="",disp="";
                                        name_disp += c.getString(c.getColumnIndex("pid"));
                                        disp += "\n";
                                        disp += c.getString(c.getColumnIndex("lname"));
                                        disp += "    ";
                                        disp += c.getString(c.getColumnIndex("fname"));
                                        intent3.putExtra("name",name_disp);
                                        intent3.putExtra("display",disp);
                                        startActivity(intent3);
                                        break;
                                       /* Bundle bundle = new Bundle();

                                        bundle.putString("hi" ,name_disp);
                                        intent3.putExtras(bundle);
                                        startActivity(intent3);
                                        break;*/


                                    }
                                    c.moveToNext();
                                   // d.moveToNext();
                                }

                                if(flag==0)
                                {
                                   /* userInput.requestFocus();
                                    userInput.setError("Enter valid PID"); */
                                    Toast.makeText(MainMenu.this, "Invalid PID ", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                            }
                        });

        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();


            }
        });

        button1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                // get prompts.xml view
                LayoutInflater li = LayoutInflater.from(MainMenu.this);
                View promptsView = li.inflate(R.layout.dialog_pid, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        MainMenu.this);

                // set prompts.xml to alertdialog builder
                alertDialogBuilder.setView(promptsView);

                final EditText userInput = (EditText) promptsView
                        .findViewById(et_pid_id);
                columns = new String[] {COL_DID,COL_DOC_NAME_FIRST,COL_DOC_NAME_LAST};
                //details = new String[] {COL_NAME_FIRST,COL_NAME_LAST};
                c = mydb.getData(DOCTOR_TABLE,columns);
                // d = mydb.getData(PAT_REG,details);

                // set dialog message
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id)
                                    {
                                        dbString =userInput.getText().toString();
                                        // long num = Long.parseLong(dbString);
                                        c.moveToFirst();
                                        // d.moveToFirst();
                                        int flag=0;
                                        while (!c.isAfterLast())
                                        {
                                            if( (userInput.getText().toString().matches("[a-zA-Z]+")))
                                            {
                                                f = 1;
                                            }
                                            String temp =(c.getString(c.getColumnIndex("did")));
                                            if(dbString.equals(temp) && dbString != " " && f!=1 )
                                            {
                                                flag=1;
                                                Intent intent2 = new Intent(MainMenu.this, Doctor.class);
                                                // AutoCompleteTextView textView = (AutoCompleteTextView) findViewById(R.id.autocomplete);
                                                //String getrec=userInput.getText().toString();
                                                String name_disp="",disp="";

                                                name_disp += c.getString(c.getColumnIndex("did"));
                                                name_disp += "\n";
                                                disp += c.getString(c.getColumnIndex("lname"));
                                                disp += "    ";
                                                disp += c.getString(c.getColumnIndex("fname"));
                                                disp += "\n";
                                                intent2.putExtra("name",name_disp);
                                                intent2.putExtra("display",disp);
                                                startActivity(intent2);
                                                break;

                                            }
                                            c.moveToNext();
                                            // d.moveToNext();
                                        }

                                        if(flag==0)
                                        {
                                   /* userInput.requestFocus();
                                    userInput.setError("Enter valid PID"); */
                                            Toast.makeText(MainMenu.this, "Invalid DID ", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        dialog.cancel();
                                    }
                                });

                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();


            }
        });


      /* button2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                // get prompts.xml view
                LayoutInflater li = LayoutInflater.from(MainMenu.this);
                View promptsView = li.inflate(R.layout.dialog_pid, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        MainMenu.this);

                // set prompts.xml to alertdialog builder
                alertDialogBuilder.setView(promptsView);

                final EditText userInput = (EditText) promptsView
                        .findViewById(et_pid_id);
                columns = new String[] {COL_DID,COL_DOC_NAME_FIRST,COL_DOC_NAME_LAST};
                //details = new String[] {COL_NAME_FIRST,COL_NAME_LAST};
                c = mydb.getData(DOCTOR_TABLE,columns);
                // d = mydb.getData(PAT_REG,details);

                // set dialog message
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id)
                                    {
                                        dbString =userInput.getText().toString();
                                        // long num = Long.parseLong(dbString);
                                        c.moveToFirst();
                                        // d.moveToFirst();
                                        int flag=0;
                                        while (!c.isAfterLast())
                                        {
                                            if( (userInput.getText().toString().matches("[a-zA-Z]+")))
                                            {
                                                f = 1;
                                            }
                                            String temp =(c.getString(c.getColumnIndex("did")));
                                            if(dbString.equals(temp) && dbString != " " && f!=1 )
                                            {
                                                flag=1;
                                                Intent intent4 = new Intent(MainMenu.this, FbFormSecond.class);
                                                // AutoCompleteTextView textView = (AutoCompleteTextView) findViewById(R.id.autocomplete);
                                                //String getrec=userInput.getText().toString();
                                                String name_disp="",disp="";

                                                name_disp += c.getString(c.getColumnIndex("did"));
                                                name_disp += "\n";
                                                disp += c.getString(c.getColumnIndex("lname"));
                                                disp += "    ";
                                                disp += c.getString(c.getColumnIndex("fname"));
                                                disp += "\n";
                                                intent4.putExtra("name",name_disp);
                                                intent4.putExtra("display",disp);
                                                startActivity(intent4);
                                                break;

                                            }
                                            c.moveToNext();
                                            // d.moveToNext();
                                        }

                                        if(flag==0)
                                        {*/
                                   /* userInput.requestFocus();
                                    userInput.setError("Enter valid PID"); */
                                           /* Toast.makeText(MainMenu.this, "Invalid DID ", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        dialog.cancel();
                                    }
                                });

                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();


            }
        });*/



    }

    public void clinicPage(View view)
    {
        Intent intent1 = new Intent(this, Clinic.class);
        startActivity(intent1);
    }

    public void ngoAdminPage(View view)
    {
        Intent intent = new Intent(this, NgoAdmin.class);
        startActivity(intent);
    }

 /*   public void feedback2(View view)
    {
        Intent intent4 = new Intent(this,FbFormSecond.class);
        startActivity(intent4);
    }*/

 /*   public void doctorPage(View view)
    {
        Intent intent2 = new Intent(this, Doctor.class);
        startActivity(intent2);
    }*/

   /* public void FbFirstPage(View view)
    {
        LayoutInflater li = LayoutInflater.from(MainMenu.this);
        View promptsView = li.inflate(R.layout.dialog_pid, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                MainMenu.this);

        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        final EditText userInput = (EditText) promptsView
                .findViewById(R.id.et_pid_id);
        String[] columns = new String[] {COL_PID};
        c = mydb.getData(PAT_REG,columns);

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id)
                            {
                               String dbString =userInput.getText().toString();
                                int num = Integer.parseInt(dbString);
                                c.moveToFirst();
                                int flag=0;
                                while (!c.isAfterLast())
                                {
                                    int temp =Integer.parseInt(c.getString(c.getColumnIndex("pid")));
                                    if(temp==num)
                                    {
                                        flag=1;
                                        Intent intent3 = new Intent(MainMenu.this, FbFormFirst.class);
                                        startActivity(intent3);
                                        break;
                                    }
                                    c.moveToNext();
                                }

                                if(flag==0)
                                    userInput.setError("Enter valid PID");
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                            }
                        });

        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();



    }*/
}
