package com.deepblueproject.fbsystem;

import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.Point;
import org.achartengine.model.SeriesSelection;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;
import android.content.Context;
import android.graphics.Color;
//import org.achartengine.renderer.SimpleSeriesRenderer;


import static com.deepblueproject.fbsystem.FbsContract.CallListEntry.CALL_LIST_TABLE;
import static com.deepblueproject.fbsystem.FbsContract.CallListEntry.COL_FB1;
import static com.deepblueproject.fbsystem.FbsContract.CallListEntry.COL_FB2;
import static com.deepblueproject.fbsystem.FbsContract.CallListEntry.COL_PID_FK;
import static com.deepblueproject.fbsystem.FbsContract.DoctorEntry.COL_DID;
import static com.deepblueproject.fbsystem.FbsContract.DoctorEntry.COL_DOC_CONTACT;
import static com.deepblueproject.fbsystem.FbsContract.DoctorEntry.COL_DOC_DOB;
import static com.deepblueproject.fbsystem.FbsContract.DoctorEntry.COL_DOC_EMAIL;
import static com.deepblueproject.fbsystem.FbsContract.DoctorEntry.COL_DOC_GENDER;
import static com.deepblueproject.fbsystem.FbsContract.DoctorEntry.COL_DOC_NAME_FIRST;
import static com.deepblueproject.fbsystem.FbsContract.DoctorEntry.COL_DOC_NAME_LAST;
import static com.deepblueproject.fbsystem.FbsContract.DoctorEntry.DOCTOR_TABLE;
import static com.deepblueproject.fbsystem.FbsContract.FbFirstEntry.COL_FB1_Q1;
import static com.deepblueproject.fbsystem.FbsContract.FbFirstEntry.COL_FB1_Q2;
import static com.deepblueproject.fbsystem.FbsContract.FbFirstEntry.COL_FB1_Q3;
import static com.deepblueproject.fbsystem.FbsContract.FbFirstEntry.COL_FB1_Q4;
import static com.deepblueproject.fbsystem.FbsContract.FbFirstEntry.COL_FB1_Q5;
import static com.deepblueproject.fbsystem.FbsContract.FbFirstEntry.COL_FB1_Q6;
import static com.deepblueproject.fbsystem.FbsContract.FbFirstEntry.COL_FK_FID;
import static com.deepblueproject.fbsystem.FbsContract.FbFirstEntry.COL_FK_PID;
import static com.deepblueproject.fbsystem.FbsContract.FbFirstEntry.FB_FIRST;
import static com.deepblueproject.fbsystem.FbsContract.FbSecondEntry.COL_FB2_Q1;
import static com.deepblueproject.fbsystem.FbsContract.FbSecondEntry.COL_FB2_Q2;
import static com.deepblueproject.fbsystem.FbsContract.FbSecondEntry.COL_FB2_Q3;
//import static com.deepblueproject.fbsystem.FbsContract.FbSecondEntry.COL_FB2_Q4;
//import static com.deepblueproject.fbsystem.FbsContract.FbSecondEntry.COL_FB2_Q5;
//import static com.deepblueproject.fbsystem.FbsContract.FbSecondEntry.COL_FB2_Q6;
import static com.deepblueproject.fbsystem.FbsContract.FbSecondEntry.FB_SECOND;
import static com.deepblueproject.fbsystem.FbsContract.PatientEntry.COL_ADDRESS;
//import static com.deepblueproject.fbsystem.FbsContract.PatientEntry.COL_BLD_GRP;
//import static com.deepblueproject.fbsystem.FbsContract.PatientEntry.COL_BLD_GRP;
import static com.deepblueproject.fbsystem.FbsContract.PatientEntry.COL_CONTACT;
import static com.deepblueproject.fbsystem.FbsContract.PatientEntry.COL_DOB;
import static com.deepblueproject.fbsystem.FbsContract.PatientEntry.COL_EMAIL;
import static com.deepblueproject.fbsystem.FbsContract.PatientEntry.COL_GENDER;
//import static com.deepblueproject.fbsystem.FbsContract.PatientEntry.COL_LANG;
//import static com.deepblueproject.fbsystem.FbsContract.PatientEntry.COL_LANG;
import static com.deepblueproject.fbsystem.FbsContract.PatientEntry.COL_NAME_FIRST;
import static com.deepblueproject.fbsystem.FbsContract.PatientEntry.COL_NAME_LAST;
import static com.deepblueproject.fbsystem.FbsContract.PatientEntry.COL_PID;
import static com.deepblueproject.fbsystem.FbsContract.PatientEntry.PAT_REG;

public class NgoAdmin extends AppCompatActivity
{
    TextView myText;
    DbHelper mydb;
    Cursor recordSet;



    /*private static int[] COLORS = new int[] { Color.GREEN, Color.BLUE,Color.MAGENTA, Color.CYAN };

    private static double[] VALUES = new double[] { 10, 11, 12, 13 };

    private static String[] NAME_LIST = new String[] { "A", "B", "C", "D" };

    private CategorySeries mSeries = new CategorySeries("");

    private DefaultRenderer mRenderer = new DefaultRenderer();

    private GraphicalView mChartView;*/



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ngo_admin);

      //  drawPieChart();
       /* mRenderer.setApplyBackgroundColor(true);
        mRenderer.setBackgroundColor(Color.argb(100, 50, 50, 50));
        mRenderer.setChartTitleTextSize(20);
        mRenderer.setLabelsTextSize(15);
        mRenderer.setLegendTextSize(15);
        mRenderer.setMargins(new int[] { 20, 30, 15, 0 });
        mRenderer.setZoomButtonsVisible(true);
        mRenderer.setStartAngle(90);

        for (int i = 0; i < VALUES.length; i++) {
            mSeries.add(NAME_LIST[i] + " " + VALUES[i], VALUES[i]);
            SimpleSeriesRenderer renderer = new SimpleSeriesRenderer();
            renderer.setColor(COLORS[(mSeries.getItemCount() - 1) % COLORS.length]);
            mRenderer.addSeriesRenderer(renderer);*/
        //}

        //if (mChartView != null) {
          //  mChartView.repaint();
        //}


        myText = (TextView) findViewById(R.id.ngo_report1_id);
        myText.append("\n\nFB1 DETAILS\n");


        myText.append(FbsContract.FbFirstEntry.COL_FB1_Q1 + " " + FbsContract.FbFirstEntry.COL_FB1_Q2 +
                " " + FbsContract.FbFirstEntry.COL_FB1_Q3 +  " " + FbsContract.FbFirstEntry.COL_FB1_Q4 +
                " " + FbsContract.FbFirstEntry.COL_FB1_Q5 + " " + FbsContract.FbFirstEntry.COL_FB1_Q6);



        Log.e("Append:", " Failed");

        mydb = new DbHelper(this);


        reportFbOne();
        myText.append("\n\nFB2 DETAILS\n");

        myText.append("\n" + FbsContract.FbSecondEntry.COL_FB2_Q1 + " " + FbsContract.FbSecondEntry.COL_FB2_Q2 +
                " " + FbsContract.FbSecondEntry.COL_FB2_Q3 /*+  " " + FbsContract.FbSecondEntry.COL_FB2_Q4 +
                " " + FbsContract.FbSecondEntry.COL_FB2_Q5 + " " + FbsContract.FbSecondEntry.COL_FB2_Q6*/ );

       reportFbTwo();
        patientDetails();
       callListDisplay();
        staffDetails();
        /*
        LinearLayout lView = new LinearLayout(this);
        TextView myText = new TextView(this);
        myText.setText("Report");
        lView.addView(myText);
        */
    //}

   // @Override
    //protected void onResume() {
      //  super.onResume();
        //if (mChartView == null) {
           /* LinearLayout layout = (LinearLayout) findViewById(R.id.activity_ngo_admin);
            mChartView = ChartFactory.getPieChartView(this, mSeries, mRenderer);
            mRenderer.setClickEnabled(true);
            mRenderer.setSelectableBuffer(10);

            mChartView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SeriesSelection seriesSelection = mChartView.getCurrentSeriesAndPoint();

                    if (seriesSelection == null) {
                        Toast.makeText(NgoAdmin.this, "No chart element was clicked", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(NgoAdmin.this, "Chart element data point index " + (seriesSelection.getPointIndex() + 1) + " was clicked" + " point value=" + seriesSelection.getValue(), Toast.LENGTH_SHORT).show();
                    }
                }
            });*/

       }

          /*  mChartView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    SeriesSelection seriesSelection = mChartView.getCurrentSeriesAndPoint();
                    if (seriesSelection == null) {
                        Toast.makeText(NgoAdmin.this, "No chart element was long pressed", Toast.LENGTH_SHORT);
                        return false;
                    } else {
                        Toast.makeText(NgoAdmin.this, "Chart element data point index " + seriesSelection.getPointIndex() + " was long pressed", Toast.LENGTH_SHORT);
                        return true;
                    }
                }
            });
            layout.addView(mChartView);*/
            //, new ActionBar.LayoutParams(ActionBar.LayoutParams.FILL_PARENT, ActionBar.LayoutParams.FILL_PARENT
        /*}
        else {
            Toast.makeText(NgoAdmin.this, "M-chart is Not null", Toast.LENGTH_LONG);

            //mChartView.repaint();

        }
        */



   /* private void drawPieChart(){
        // Ceate CategorySeries
        CategorySeries categorySeries=new CategorySeries("Pie chart categories");
        // Add categories
        categorySeries.add("Female",312000);
        categorySeries.add("Male",21200);


        // Add series render to each slide of the pie chart
        int[] colors={Color.GREEN, Color.MAGENTA};
        DefaultRenderer defaultRenderer=new DefaultRenderer();
        for(int i = 0 ;i<categorySeries.getItemCount();i++){
            SimpleSeriesRenderer seriesRenderer = new SimpleSeriesRenderer();
            // Spcify render options
            seriesRenderer.setColor(colors[i]);
            seriesRenderer.setDisplayChartValues(true);
           // seriesRenderer.setDisplayChartValuesDistance(0);
            //seriesRenderer.setShowLegendItem(true);
            defaultRenderer.addSeriesRenderer(seriesRenderer);
        }

        // Specify default render options
        defaultRenderer.setPanEnabled(true);
        defaultRenderer.setAntialiasing(true);
        defaultRenderer.setShowLabels(true);
        defaultRenderer.setShowLegend(true);
        defaultRenderer.setChartTitle("Female and Male Garment Workers in 2015");

        // Get pie chart view
        GraphicalView chartView = ChartFactory.getPieChartView(this, categorySeries,defaultRenderer);
        // Add the pie chart view to the layout to show
        LinearLayout chartlayout=(LinearLayout)findViewById(R.id.chart);
        chartlayout.addView(chartView);
    }*/

        @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        setTitle("NGO Admin");
        getMenuInflater().inflate( R.menu.ngo_admin_menu, menu);
        return true;
    }


    public void onDelete(View view)
    {
        DbHelper mydb = new DbHelper(this);
        mydb.dropTable();
        Toast.makeText(NgoAdmin.this, "TABLE DROPPED " , Toast.LENGTH_SHORT).show();
    }

   /*
    @Override
    protected void onStart() {
        super.onStart();
        reportOne_2();

    }
*/

    public void reportFbOne()
    {
        String[] columns = new String[]{COL_FB1_Q1, COL_FB1_Q2, COL_FB1_Q3, COL_FB1_Q4, COL_FB1_Q5, COL_FB1_Q6};

        Cursor recordSet = mydb.getData(FB_FIRST,columns);

        if (recordSet == null)
        {
            Toast.makeText(NgoAdmin.this, "Table empty" , Toast.LENGTH_SHORT).show();
            return;

        }
        recordSet.moveToFirst();

        //Position after the last row means the end of the results
        while (!recordSet.isAfterLast()) {
            // null could happen if we used our empty constructor
            String dbString = "";
            if (recordSet.getString(recordSet.getColumnIndex("Q1")) != null) {
                //dbString += recordSet.getString(recordSet.getColumnIndex("id"));
                dbString += "\n  ";
                dbString += recordSet.getString(recordSet.getColumnIndex("Q1"));
                dbString += "   ";
                dbString += recordSet.getString(recordSet.getColumnIndex("Q2"));
                dbString += "   ";
                dbString += recordSet.getString(recordSet.getColumnIndex("Q3"));
                dbString += "   ";
                dbString += recordSet.getString(recordSet.getColumnIndex("Q4"));
                dbString += "   ";
                dbString += recordSet.getString(recordSet.getColumnIndex("Q5"));
                dbString += "   ";
                dbString += recordSet.getString(recordSet.getColumnIndex("Q6"));
                dbString += "   ";


                myText.append(dbString);
            }
            recordSet.moveToNext();
        }
    }



    public void reportFbTwo()
    {

        String[] columns = new String[]{COL_FB2_Q1, COL_FB2_Q2, COL_FB2_Q3};

        Cursor recordSet = mydb.getData(FB_SECOND,columns);

        if (recordSet == null)
        {
            Toast.makeText(NgoAdmin.this, "Table empty" , Toast.LENGTH_SHORT).show();
            return;

        }
        recordSet.moveToFirst();

        //Position after the last row means the end of the results
        while (!recordSet.isAfterLast()) {
            // null could happen if we used our empty constructor
            String dbString = "";
            if (recordSet.getString(recordSet.getColumnIndex("Q1")) != null) {
                //dbString += recordSet.getString(recordSet.getColumnIndex("id"));
                dbString += "\n  ";
                dbString += recordSet.getString(recordSet.getColumnIndex("Q1"));
                dbString += "   ";
                dbString += recordSet.getString(recordSet.getColumnIndex("Q2"));
                dbString += "   ";
                dbString += recordSet.getString(recordSet.getColumnIndex("Q3"));
                dbString += "   ";
               // dbString += recordSet.getString(recordSet.getColumnIndex("Q4"));
                //dbString += "   ";
               // dbString += recordSet.getString(recordSet.getColumnIndex("Q5"));
                //dbString += "   ";
               // dbString += recordSet.getString(recordSet.getColumnIndex("Q6"));
                //dbString += "   ";


                myText.append(dbString);
            }
            recordSet.moveToNext();
        }
    }

    public void callListDisplay()
    {
        myText.append("\n\nCALL-LIST DETAILS\n");

        String[] columns = new String[]{COL_FK_FID, COL_FK_PID,COL_FB1, COL_FB2};

        Cursor call_ptr = mydb.getData(CALL_LIST_TABLE,columns);

        int  cnt = call_ptr.getCount();

        call_ptr.moveToFirst();
        //while (cnt != 0)
        do
        {

            String dbString = "";
            if (call_ptr.getString(call_ptr.getColumnIndex("fid_fk")) != null)
            {
                dbString += "\n  ";
                dbString +=call_ptr.getString(call_ptr.getColumnIndex("fid_fk"));
                dbString += "    ";
                dbString +=call_ptr.getString(call_ptr.getColumnIndex("fb1"));
                dbString += "    ";
                dbString +=call_ptr.getString(call_ptr.getColumnIndex("fb2"));
                dbString += "    ";
                dbString +=call_ptr.getString(call_ptr.getColumnIndex("pid_fk"));
                dbString += "    ";

                myText.append(dbString);

                //cnt--;
                //if(cnt!=0)
            }
        }while (call_ptr.moveToNext());

        /*while (cnt != 0)
        { call_ptr.moveToNext();
            String dbString = "";
            if (call_ptr.getString(call_ptr.getColumnIndex("fid_fk")) != null)
            {
                dbString += "\n  ";
                dbString +=call_ptr.getString(call_ptr.getColumnIndex("fid_fk"));
                dbString += "    ";
                dbString +=call_ptr.getString(call_ptr.getColumnIndex("fb1"));
                dbString += "    ";
                dbString +=call_ptr.getString(call_ptr.getColumnIndex("fb2"));
                dbString += "    ";
                dbString +=call_ptr.getString(call_ptr.getColumnIndex("pid_fk"));
                dbString += "    ";

                myText.append(dbString);

                cnt--;
            }
        }*/

    }
    public void patientDetails()
    {

        myText.append("\n\nPATIENT DETAILS\n");
        String[] columns = new String[]{COL_PID,COL_NAME_FIRST,COL_NAME_LAST,COL_DOB,COL_EMAIL,COL_CONTACT,COL_ADDRESS,COL_GENDER};

        Cursor recordSet = mydb.getData(PAT_REG,columns);

        if (recordSet == null)
        {
            Toast.makeText(NgoAdmin.this, "Table empty" , Toast.LENGTH_SHORT).show();
            return;

        }
        recordSet.moveToFirst();

        //Position after the last row means the end of the results
        while (!recordSet.isAfterLast()) {
            // null could happen if we used our empty constructor
            String dbString = "";
            if (recordSet.getString(recordSet.getColumnIndex("fname")) != null) {
                //dbString += recordSet.getString(recordSet.getColumnIndex("id"));
                dbString += "\n  ";
                dbString +=recordSet.getString(recordSet.getColumnIndex("pid"));
                dbString += "      ";
                dbString += recordSet.getString(recordSet.getColumnIndex("fname"));
                dbString += "      ";
                dbString += recordSet.getString(recordSet.getColumnIndex("lname"));
                dbString += "      ";
                dbString += recordSet.getString(recordSet.getColumnIndex("dob"));
                dbString += "      ";
                dbString += recordSet.getString(recordSet.getColumnIndex("email"));
                dbString += "      ";
                dbString += recordSet.getString(recordSet.getColumnIndex("contact"));
                dbString += "      ";
                dbString += recordSet.getString(recordSet.getColumnIndex("gender"));
                dbString += "      ";
               // dbString += recordSet.getString(recordSet.getColumnIndex("lang"));
                dbString += "      ";
                //dbString += recordSet.getString(recordSet.getColumnIndex("blood_grp"));
                //dbString += "      ";


                myText.append(dbString);
            }
            recordSet.moveToNext();
        }
    }


    public void staffDetails()
    {

        myText.append("\n\nDOCTOR DETAILS\n");
        String[] columns = new String[]{COL_DID,COL_DOC_NAME_FIRST,COL_DOC_NAME_LAST,COL_DOC_DOB,COL_DOC_EMAIL,COL_DOC_CONTACT,COL_DOC_GENDER};//,COL_TYPE};

        Cursor recordSet = mydb.getData(DOCTOR_TABLE,columns);

        if (recordSet == null)
        {
            Toast.makeText(NgoAdmin.this, "Table empty" , Toast.LENGTH_SHORT).show();
            return;

        }
        recordSet.moveToFirst();

        //Position after the last row means the end of the results
        while (!recordSet.isAfterLast()) {
            // null could happen if we used our empty constructor
            String dbString = "";
            if (recordSet.getString(recordSet.getColumnIndex("fname")) != null) {
                //dbString += recordSet.getString(recordSet.getColumnIndex("id"));
                dbString += "\n  ";
                dbString +=recordSet.getString(recordSet.getColumnIndex("did"));
                dbString += "      ";
                dbString += recordSet.getString(recordSet.getColumnIndex("fname"));
                dbString += "      ";
                dbString += recordSet.getString(recordSet.getColumnIndex("lname"));
                dbString += "      ";
                dbString += recordSet.getString(recordSet.getColumnIndex("dob"));
                dbString += "      ";
                dbString += recordSet.getString(recordSet.getColumnIndex("email"));
                dbString += "      ";
                dbString += recordSet.getString(recordSet.getColumnIndex("contact"));
                dbString += "      ";
                dbString += recordSet.getString(recordSet.getColumnIndex("gender"));
                dbString += "      ";
                //dbString += recordSet.getString(recordSet.getColumnIndex("type"));
                //dbString += "      ";
                //dbString += recordSet.getString(recordSet.getColumnIndex("blood_grp"));
                //dbString += "      ";


                myText.append(dbString);
            }
            recordSet.moveToNext();
        }
    }


/*
    public void reportOne_2(View view)
    {
        Log.i("Entered in reportOne_2", " success" );
        Log.e("Entered in reportOne_2", " success" );


        DbHelper mydb = new DbHelper(this);

        Cursor cursor = mydb.getData();
        int idColumnIndex = cursor.getColumnIndex(FbsContract.FbFirstEntry._ID);
        int q1ColumnIndex = cursor.getColumnIndex(FbsContract.FbFirstEntry.COL_FB1_Q1);
        int q2ColumnIndex = cursor.getColumnIndex(FbsContract.FbFirstEntry.COL_FB1_Q2);
        int q3ColumnIndex = cursor.getColumnIndex(FbsContract.FbFirstEntry.COL_FB1_Q3);
        int q4ColumnIndex = cursor.getColumnIndex(FbsContract.FbFirstEntry.COL_FB1_Q4);
        int q5ColumnIndex = cursor.getColumnIndex(FbsContract.FbFirstEntry.COL_FB1_Q5);
        int q6ColumnIndex = cursor.getColumnIndex(FbsContract.FbFirstEntry.COL_FB1_Q6);




        TextView myText = (TextView) findViewById(id.ngo_report1_id);

        while (cursor.moveToNext()) {
            // Use that index to extract the String or Int value of the word
            // at the current row the cursor is on.
            int currentID = cursor.getInt(idColumnIndex);
            int currentq1 = cursor.getInt(q1ColumnIndex);
            int currentq2 = cursor.getInt(q2ColumnIndex);
            int currentq3 = cursor.getInt(q3ColumnIndex);
            int currentq4 = cursor.getInt(q4ColumnIndex);
            int currentq5 = cursor.getInt(q5ColumnIndex);
            int currentq6 = cursor.getInt(q6ColumnIndex);


            // Display the values from each column of the current row in the cursor in the TextView
            myText.append("\n" + currentID + " " +
                    currentq1 + " " +
                    currentq2 + " " +
                    currentq3 + " " +
                    currentq4 + " " +
                    currentq5 + " " +
                    currentq6 + " " );
        }

        cursor.close();
    }
*/


/*
    public void reportOne()
    {


        SQLiteDatabase db = mydb.getReadableDatabase();

        TextView myText = (TextView) findViewById(id.ngo_report1_id);


        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                FbsContract.FbFirstEntry._ID,
                FbsContract.FbFirstEntry.COL_FB1_Q1,
                FbsContract.FbFirstEntry.COL_FB1_Q2,
                FbsContract.FbFirstEntry.COL_FB1_Q3,
                FbsContract.FbFirstEntry.COL_FB1_Q4,
                FbsContract.FbFirstEntry.COL_FB1_Q5,
                FbsContract.FbFirstEntry.COL_FB1_Q6  };


        // Perform a query on the pets table
        Cursor cursor = db.query(
                FbsContract.FbFirstEntry.FB_FIRST,   // The table to query
                projection,            // The columns to return
                null,                  // The columns for the WHERE clause
                null,                  // The values for the WHERE clause
                null,                  // Don't group the rows
                null,                  // Don't filter by row groups
                null);                   // The sort order




        myText.append(FbsContract.FbFirstEntry.COL_FB1_Q1 + " " + FbsContract.FbFirstEntry.COL_FB1_Q2 +
                " " + FbsContract.FbFirstEntry.COL_FB1_Q3 +  " " + FbsContract.FbFirstEntry.COL_FB1_Q4 +
                " " + FbsContract.FbFirstEntry.COL_FB1_Q5 + " " + FbsContract.FbFirstEntry.COL_FB1_Q6);


        int idColumnIndex = cursor.getColumnIndex(FbsContract.FbFirstEntry._ID);
        int q1ColumnIndex = cursor.getColumnIndex(FbsContract.FbFirstEntry.COL_FB1_Q1);
        int q2ColumnIndex = cursor.getColumnIndex(FbsContract.FbFirstEntry.COL_FB1_Q2);
        int q3ColumnIndex = cursor.getColumnIndex(FbsContract.FbFirstEntry.COL_FB1_Q3);
        int q4ColumnIndex = cursor.getColumnIndex(FbsContract.FbFirstEntry.COL_FB1_Q4);
        int q5ColumnIndex = cursor.getColumnIndex(FbsContract.FbFirstEntry.COL_FB1_Q5);
        int q6ColumnIndex = cursor.getColumnIndex(FbsContract.FbFirstEntry.COL_FB1_Q6);


        while (cursor.moveToNext()) {
            // Use that index to extract the String or Int value of the word
            // at the current row the cursor is on.
            int currentID = cursor.getInt(idColumnIndex);
            int currentq1 = cursor.getInt(q1ColumnIndex);
            int currentq2 = cursor.getInt(q2ColumnIndex);
            int currentq3 = cursor.getInt(q3ColumnIndex);
            int currentq4 = cursor.getInt(q4ColumnIndex);
            int currentq5 = cursor.getInt(q5ColumnIndex);
            int currentq6 = cursor.getInt(q6ColumnIndex);

            // Display the values from each column of the current row in the cursor in the TextView
            myText.append("\n" + currentID + " " +
                    currentq1 + " " +
                    currentq2 + " " +
                    currentq3 + " " +
                    currentq4 + " " +
                    currentq5 + " " +
                    currentq6 + " " );
        }

        cursor.close();

    }*/

}
