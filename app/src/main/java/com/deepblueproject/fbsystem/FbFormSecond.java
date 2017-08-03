package com.deepblueproject.fbsystem;

import android.content.ContentValues;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import static com.deepblueproject.fbsystem.FbsContract.FbFirstEntry.AVG;
import static com.deepblueproject.fbsystem.FbsContract.FbFirstEntry.BAD;
import static com.deepblueproject.fbsystem.FbsContract.FbFirstEntry.GOOD;
import static com.deepblueproject.fbsystem.FbsContract.FbFirstEntry.SUPER;
import static com.deepblueproject.fbsystem.FbsContract.FbSecondEntry.FB_SECOND;

public class FbFormSecond extends AppCompatActivity {
String use1;
    int[] point = new int[6];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fb_form_second);
        setTitle("Feedback 2");


        TextView pid_fb1_tv = (TextView) findViewById(R.id.pid_show);

        // bundle = getIntent().getExtras();
        use1 =getIntent().getStringExtra("name");
        String use2 =getIntent().getStringExtra("display");
        pid_fb1_tv.setText(use1);
        pid_fb1_tv.append("\n");
        pid_fb1_tv.append(use2);


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


    public void onClickRadioButton(View view)
    {
        int id;
        id = view.getId();
        switch (id)
        {
            case R.id.good_2_1_id:
                point[0] = GOOD;
                break;

            case R.id.super_2_1_id:
                point[0] = SUPER;
                break;

            case R.id.avg_2_1_id:
                point[0] = AVG;
                break;

            case R.id.bad_2_1_id:
                point[0] = BAD;
                break;


            case R.id.good_2_2_id:
                point[1] = GOOD;
                break;
            case R.id.super_2_2_id:
                point[1] = SUPER;
                break;
            case R.id.avg_2_2_id:
                point[1] = AVG;
                break;
            case R.id.bad_2_2_id:
                point[1] = BAD;
                break;


            case R.id.good_2_3_id:
                point[2] = GOOD;
                break;
            case R.id.super_2_3_id:
                point[2] = SUPER;
                break;
            case R.id.avg_2_3_id:
                point[2] = AVG;
                break;
            case R.id.bad_2_3_id:
                point[2] = BAD;
                break;


           /* case R.id.good_2_4_id:
                point[3] = GOOD;
                break;
            case R.id.super_2_4_id:
                point[3] = SUPER;
                break;
            case R.id.avg_2_4_id:
                point[3] = AVG;
                break;
            case R.id.bad_2_4_id:
                point[3] = BAD;
                break;


            case R.id.good_2_5_id:
                point[4] = GOOD;
                break;
            case R.id.super_2_5_id:
                point[4] = SUPER;
                break;
            case R.id.avg_2_5_id:
                point[4] = AVG;
                break;
            case R.id.bad_2_5_id:
                point[4] = BAD;
                break;


            case R.id.good_2_6_id:
                point[5] = GOOD;
                break;
            case R.id.super_2_6_id:
                point[5] = SUPER;
                break;
            case R.id.avg_2_6_id:
                point[5] = AVG;
                break;
            case R.id.bad_2_6_id:
                point[5] = BAD;
                break;*/

        }

    }


    public void onSubmitFb(View view)
    {
        DbHelper mydb = new DbHelper(this);
        ContentValues contentValues = new ContentValues();
        contentValues.put("Q1", point[0]);
        contentValues.put("Q2", point[1]);
        contentValues.put("Q3", point[2]);
       // contentValues.put("Q4", point[3]);
       // contentValues.put("Q5", point[4]);
       // contentValues.put("Q6", point[5] );

        long check = mydb.insertDB(FB_SECOND,contentValues);

        if(check != -1)
            Toast.makeText(FbFormSecond.this, "New row added, row id: " + check, Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(FbFormSecond.this, "Something wrong", Toast.LENGTH_SHORT).show();

        //Toast.makeText(FbFormFirst.this,"Feedack Saved!", Toast.LENGTH_LONG).show();
        //long rowInserted = db.insert(AddNewPhysicalPerson, null, newValues);


    }
}




