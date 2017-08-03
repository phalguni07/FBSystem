package com.deepblueproject.fbsystem;

import android.content.ContentValues;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.deepblueproject.fbsystem.FbsContract.CallListEntry.CALL_LIST_TABLE;
//import static com.deepblueproject.fbsystem.FbsContract.CallListEntry.COL_PID;
import static com.deepblueproject.fbsystem.FbsContract.CallListEntry.COL_PID_FK;
import static com.deepblueproject.fbsystem.FbsContract.FbFirstEntry.AVG;
import static com.deepblueproject.fbsystem.FbsContract.FbFirstEntry.BAD;
import static com.deepblueproject.fbsystem.FbsContract.FbFirstEntry.FB_FIRST;
import static com.deepblueproject.fbsystem.FbsContract.FbFirstEntry.GOOD;
import static com.deepblueproject.fbsystem.FbsContract.FbFirstEntry.SUPER;
import static com.deepblueproject.fbsystem.FbsContract.PatientEntry.COL_PID;

public class FbFormFirst extends AppCompatActivity {

    int[] point = new int[6];
    ContentValues contentValues;
    ContentValues contentValues1;
    private RadioGroup r_1,r_2,r_3,r_4,r_5,r_6;
    private Boolean a=false,b=false,c=false,d=false,e=false,f=false;
    private Button fb1_submit;
    Bundle bundle;
    String use1;
    DbHelper mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fb_form_first);
        setTitle("Feedback 1");
        addListenerOnButton();
        contentValues1 = new ContentValues();


        mydb = new DbHelper(this);

        TextView pid_fb1_tv = (TextView) findViewById(R.id.pid_show);

       // bundle = getIntent().getExtras();
         use1 =getIntent().getStringExtra("name");
        String use2 =getIntent().getStringExtra("display");
        pid_fb1_tv.setText(use1);
        pid_fb1_tv.append("\n");
        pid_fb1_tv.append(use2);

        contentValues = new ContentValues();
        contentValues.put("pid_fk", use1.trim() );
        mydb.insertFK_FID_PID(FB_FIRST,use1.trim());
        mydb.insertFK_FID_PID(CALL_LIST_TABLE,use1.trim());

      /*  LinearLayout lView = new LinearLayout(this);

        TextView myText = new TextView(this);
        int id = rg_fb1_one;
        myText.setText(Integer.toString(id));

        myText.append(Integer.toString(rg_fb1_two));
        myText.append(Integer.toString(rg_fb1_three));
        myText.append(Integer.toString(rg_fb1_four));
        myText.append(Integer.toString(rg_fb1_five));
        myText.append(Integer.toString(rg_fb1_six));

        lView.addView(myText);

        setContentView(lView);
        //System.out.println(rg_fb1_one);
        //System.out.println(rg_fb1_six);
        */

    }

    public void addListenerOnButton() {



        r_1 = (RadioGroup) findViewById(R.id.rg_fb1_one);
        r_2= (RadioGroup) findViewById(R.id.rg_fb1_two);
        r_3= (RadioGroup) findViewById(R.id.rg_fb1_three);
        r_4= (RadioGroup) findViewById(R.id.rg_fb1_four);
        r_5= (RadioGroup) findViewById(R.id.rg_fb1_five);
        r_6= (RadioGroup) findViewById(R.id.rg_fb1_six);
        fb1_submit = (Button) findViewById(R.id.btn_submit_id_fb1);

        fb1_submit.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                // get selected radio button from radioGroup
                int id1 = r_1.getCheckedRadioButtonId();
                int id2 = r_2.getCheckedRadioButtonId();
                int id3 = r_3.getCheckedRadioButtonId();
                int id4 = r_4.getCheckedRadioButtonId();
                int id5 = r_5.getCheckedRadioButtonId();
                int id6 = r_6.getCheckedRadioButtonId();



                if (id1 != -1) {
                    a = true;
                }
                else
                    a=false;

                if (id2 != -1)
                    b=true;
                else
                    b=false;

                if (id3 != -1)
                    c=true;
                else
                    c=false;


                if (id4 != -1)
                    d=true;
                else
                    d=false;


                if (id5 != -1)
                    e=true;
                else
                    e=false;

                if (id6 != -1)
                    f=true;
                else
                    f=false;

                if(a==true && b==true && c==true && d==true && e==true && f==true)
                {
                    onSubmitFb();

                }
                else
                    Toast.makeText(FbFormFirst.this, "Fill all the choices", Toast.LENGTH_SHORT).show();




                // find the radiobutton by returned id
                //b_1 = (RadioButton) findViewById(selectedId);

                //Toast.makeText(TestprojectActivity.this,radioSexButton.getText(), Toast.LENGTH_SHORT).show();

            }

        });
    }



    public void onClickRadioButton(View view)
    {
        //Log.i("onClickRadioButton ", " Not going inside it");
        int id; //i=0;
        //RadioGroup radioGroup = new RadioGroup(this);

        //  switch (view.getId())
        //{
        //  case R.id.rg_fb1_one:
        id = view.getId(); //radioGroup.getCheckedRadioButtonId();
        switch (id)
        {
            case R.id.good_1_id:
                point[0] = GOOD;
                break;

            case R.id.super_1_id:
                point[0] = SUPER;
                break;

            case R.id.avg_1_id:
                point[0] = AVG;
                break;

            case R.id.bad_1_id:
                point[0] = BAD;
                break;


            case R.id.good_2_id:
                point[1] = GOOD;
                break;
            case R.id.super_2_id:
                point[1] = SUPER;
                break;
            case R.id.avg_2_id:
                point[1] = AVG;
                break;
            case R.id.bad_2_id:
                point[1] = BAD;
                break;


            case R.id.good_3_id:
                point[2] = GOOD;
                break;
            case R.id.super_3_id:
                point[2] = SUPER;
                break;
            case R.id.avg_3_id:
                point[2] = AVG;
                break;
            case R.id.bad_3_id:
                point[2] = BAD;
                break;


            case R.id.good_4_id:
                point[3] = GOOD;
                break;
            case R.id.super_4_id:
                point[3] = SUPER;
                break;
            case R.id.avg_4_id:
                point[3] = AVG;
                break;
            case R.id.bad_4_id:
                point[3] = BAD;
                break;


            case R.id.good_5_id:
                point[4] = GOOD;
                break;
            case R.id.super_5_id:
                point[4] = SUPER;
                break;
            case R.id.avg_5_id:
                point[4] = AVG;
                break;
            case R.id.bad_5_id:
                point[4] = BAD;
                break;


            case R.id.good_6_id:
                point[5] = GOOD;
                break;
            case R.id.super_6_id:
                point[5] = SUPER;
                break;
            case R.id.avg_6_id:
                point[5] = AVG;
                break;
            case R.id.bad_6_id:
                point[5] = BAD;
                break;

        }

    }


    public void onSubmitFb()
    {


        Date dNow = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("yyMMddhhmmssMs");
        String datetime = ft.format(dNow);


        contentValues.put("Q1", point[0]);
        contentValues.put("Q2", point[1]);
        contentValues.put("Q3", point[2]);
        contentValues.put("Q4", point[3]);
        contentValues.put("Q5", point[4]);
        contentValues.put("Q6",point[5] );


       // mydb.insertFK_FID_PID();

        long check = mydb.insertDB(FB_FIRST,contentValues);




        //contentValues1.put("fid", datetime);
        contentValues1.put("fb1", 1);
        contentValues1.put("fb2", 0);

        mydb.insertwithWhere(CALL_LIST_TABLE,use1);
        //long check2 = mydb.insertDB(,contentValues1);


        if(check != -1 )
            Toast.makeText(FbFormFirst.this, "New row added, row id: " + check, Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(FbFormFirst.this, "Something wrong", Toast.LENGTH_SHORT).show();

        //Toast.makeText(FbFormFirst.this,"Feedack Saved!", Toast.LENGTH_LONG).show();
        //long rowInserted = db.insert(AddNewPhysicalPerson, null, newValues);
    }

}