
/**
 * This class basically helps to seek data from app and store in database vise-versa
 * Created by user pc on 12/21/2016.
 */

package com.deepblueproject.fbsystem;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;
import static com.deepblueproject.fbsystem.FbsContract.CallListEntry.CALL_LIST_TABLE;
import static com.deepblueproject.fbsystem.FbsContract.CallListEntry.COL_FB1;
import static com.deepblueproject.fbsystem.FbsContract.CallListEntry.COL_FB2;
import static com.deepblueproject.fbsystem.FbsContract.ClinicEntry.CLINIC_TABLE;
import static com.deepblueproject.fbsystem.FbsContract.DoctorEntry.DOCTOR_TABLE;
import static com.deepblueproject.fbsystem.FbsContract.FbFirstEntry.FB_FIRST;
import static com.deepblueproject.fbsystem.FbsContract.FbFirstEntry._ID;
import static com.deepblueproject.fbsystem.FbsContract.FbSecondEntry.FB_SECOND;
//import static com.deepblueproject.fbsystem.FbsContract.PatientDetailsEntry.COL_FK_DID;
import static com.deepblueproject.fbsystem.FbsContract.PatientDetailsEntry.COL_FK_PID;
import static com.deepblueproject.fbsystem.FbsContract.PatientDetailsEntry.PATIENT_DETAILS_TABLE;
import static com.deepblueproject.fbsystem.FbsContract.PatientEntry.COL_CONTACT;
import static com.deepblueproject.fbsystem.FbsContract.PatientEntry.COL_PID;
import static com.deepblueproject.fbsystem.FbsContract.PatientEntry.PAT_REG;

public class DbHelper extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version..........
    public static final int DATABASE_VERSION = 61;
    public static final String DATABASE_NAME = "fbsystem.db";

    private static final String SQL_CREATE_ENTRIES_FB_FIRST =
            "CREATE TABLE IF NOT EXISTS " + FB_FIRST + " (" +
                    FbsContract.FbFirstEntry._ID + " INTEGER ," +
                    FbsContract.FbFirstEntry.COL_FK_FID + " INT," +
                    FbsContract.FbFirstEntry.COL_FK_PID + " INTEGER ," +
                    FbsContract.FbFirstEntry.COL_FB1_Q1 + " INT," +
                    FbsContract.FbFirstEntry.COL_FB1_Q2 + " INT," +
                    FbsContract.FbFirstEntry.COL_FB1_Q3 + " INT,"+
                    FbsContract.FbFirstEntry.COL_FB1_Q4 + " INT,"+
                    FbsContract.FbFirstEntry.COL_FB1_Q5 + " INT,"+
                    FbsContract.FbFirstEntry.COL_FB1_Q6 + " INT," +
                    "   FOREIGN KEY (" +
                    FbsContract.FbFirstEntry.COL_FK_PID + ") REFERENCES PAT_REG(" +
                    FbsContract.PatientEntry.COL_PID +
                    ")"+
                    " ,  FOREIGN KEY (" +
                    FbsContract.FbFirstEntry.COL_FK_FID+ ") REFERENCES PATIENT_DETAILS_TABLE(" +
                    FbsContract.PatientDetailsEntry.COL_FID +
                    "))";

    private static final String SQL_CREATE_ENTRIES_FB_SECOND =
            "CREATE TABLE IF NOT EXISTS " + FB_SECOND + " (" +
                    FbsContract.FbSecondEntry._ID + " INT, " +
                    FbsContract.FbSecondEntry.COL_FB2_Q1 + " INT," +
                    FbsContract.FbSecondEntry.COL_FB2_Q2 + " INT," +
                    FbsContract.FbSecondEntry.COL_FB2_Q3 + " INT," +
                    //FbsContract.FbSecondEntry.COL_FB2_Q4 + " INT," +
                    //FbsContract.FbSecondEntry.COL_FB2_Q5 + " INT," +
                    //FbsContract.FbSecondEntry.COL_FID_FK + " INT," +
                    FbsContract.FbSecondEntry.COL_FK_PID + " INT," +
                    FbsContract.FbSecondEntry.COL_FK_FID + " INT," +
                    //FbsContract.FbSecondEntry.COL_FK_CONTACT + " INT," +
                    "   FOREIGN KEY (" +
                    FbsContract.FbSecondEntry.COL_FK_PID + ") REFERENCES PAT_REG(" +
                    FbsContract.PatientEntry.COL_PID + ")" +
                    ", FOREIGN KEY (" +
                    FbsContract.FbSecondEntry.COL_FK_FID + ") REFERENCES PATIENT_DETAILS_TABLE(" +
                    FbsContract.PatientDetailsEntry.COL_FID+ ")"+
                    //"   FOREIGN KEY (" +
                    //FbsContract.FbSecondEntry.COL_FK_CONTACT+ ") REFERENCES PAT_REG(" +
                    //FbsContract.PatientEntry.COL_CONTACT+
                    ")";
    //+
                    //FbsContract.FbSecondEntry.COL_FB2_Q6 + " INT)";

    private static final String SQL_CREATE_ENTRIES_CALL_LIST_TABLE =
            "CREATE TABLE IF NOT EXISTS " + CALL_LIST_TABLE + " ( " +
                    FbsContract.CallListEntry._ID + " INT, " +
                    FbsContract.CallListEntry.COL_FID_FK+ " INT, " +
                    FbsContract.CallListEntry.COL_PID_FK + " INT,"+
                    FbsContract.CallListEntry.COL_FB1 + " INT, " +
                    FbsContract.CallListEntry.COL_FB2 + " INT, " +
                  "   FOREIGN KEY (" +
                  FbsContract.CallListEntry.COL_PID_FK + ") REFERENCES PAT_REG(" +
                 FbsContract.PatientEntry.COL_PID +")"+
                    "  , FOREIGN KEY (" +
                    FbsContract.CallListEntry.COL_FID_FK + ") REFERENCES PATIENT_DETAILS_TABLE(" +
                    FbsContract.PatientDetailsEntry.COL_FID +"))";


    private static final String SQL_CREATE_ENTRIES_CLINIC_REG =
            "CREATE TABLE IF NOT EXISTS " + CLINIC_TABLE + " (" +
                    FbsContract.ClinicEntry._ID + " INTEGER, " +
                    FbsContract.ClinicEntry.COL_CID + " INTEGER PRIMARY KEY, " +
                    FbsContract.ClinicEntry.COL_CLINIC_ADD + " TEXT, " +
                    FbsContract.ClinicEntry.COL_CLINIC_CONTACT+ " INT, " +
                    FbsContract.ClinicEntry.COL_CLINIC_EMAIL + " TEXT)";



    private static final String SQL_CREATE_ENTRIES_DOC_REG =
            "CREATE TABLE IF NOT EXISTS " + DOCTOR_TABLE + " (" +
                    FbsContract.DoctorEntry._ID + " INTEGER, " +
                    FbsContract.DoctorEntry.COL_DID + " INTEGER PRIMARY KEY, " +
                    FbsContract.DoctorEntry.COL_DOC_NAME_FIRST + " TEXT, " +
                    FbsContract.DoctorEntry.COL_DOC_NAME_LAST + " TEXT, " +
                    FbsContract.DoctorEntry.COL_DOC_GENDER + " INT, " +
                    //FbsContract.DoctorEntry.COL_SBLD_GRP + " TEXT, " +
                    FbsContract.DoctorEntry.COL_DOC_CONTACT + " INT, " +
                    FbsContract.DoctorEntry.COL_DOC_DOB + " DATE, " +
                    FbsContract.DoctorEntry.COL_DOC_EMAIL + " TEXT )";

    private static final String SQL_CREATE_ENTRIES_PATIENT_DETAILS =
            "CREATE TABLE IF NOT EXISTS " + PATIENT_DETAILS_TABLE + " (" +
                    FbsContract.PatientDetailsEntry._ID + " INT, " +
                    FbsContract.PatientDetailsEntry.COL_FID + " INTEGER PRIMARY KEY, " +
                    FbsContract.PatientDetailsEntry.COL_DATE + " DATE, " +
                    FbsContract.PatientDetailsEntry.COL_DAYS + " INT, " +
                    FbsContract.PatientDetailsEntry.COL_NOTES + " TEXT, " +
                    FbsContract.PatientDetailsEntry.COL_OBS + " TEXT, " +
                    FbsContract.PatientDetailsEntry.COL_TEST + " TEXT, " +
                    FbsContract.PatientDetailsEntry.COL_FK_PID + " INT, " +
                    //FbsContract.PatientDetailsEntry.COL_FK_DID + " INT, " +
                    //FbsContract.PatientDetailsEntry.COL_FK_CID + " INT, " +
                    "   FOREIGN KEY (" +
                    FbsContract.PatientDetailsEntry.COL_FK_PID + ") REFERENCES PAT_REG(" +
                    FbsContract.PatientEntry.COL_PID + ")" + /*" FOREIGN KEY (" +
    FbsContract.PatientDetailsEntry.COL_FK_CID + ") REFERENCES CLINIC_TABLE(" +
    FbsContract.ClinicEntry.COL_CID+ */")";
                    //" ,  FOREIGN KEY (" +
                   // FbsContract.PatientDetailsEntry.COL_FK_DID+ ") REFERENCES DOCTOR_TABLE(" +
                    //FbsContract.DoctorEntry.COL_DID + ") )";



    private static final String SQL_CREATE_ENTRIES_PAT_REG =
            "CREATE TABLE IF NOT EXISTS " + PAT_REG + " (" +
                    FbsContract.PatientEntry._ID + " INTEGER, " +
                    FbsContract.PatientEntry.COL_PID + " INTEGER PRIMARY KEY, " +
                    FbsContract.PatientEntry.COL_NAME_FIRST + " TEXT, " +
                    FbsContract.PatientEntry.COL_NAME_LAST + " TEXT, " +
                    FbsContract.PatientEntry.COL_GENDER + " INT,  " +
                    FbsContract.PatientEntry.COL_ADDRESS + " TEXT, " +
                    FbsContract.PatientEntry.COL_CONTACT + " INT, " +
                    FbsContract.PatientEntry.COL_EMAIL + " TEXT, " +
                    //FbsContract.PatientEntry.COL_LANG + " TEXT, " +
                    //FbsContract.PatientEntry.COL_BLD_GRP + " TEXT, " +
                    FbsContract.PatientEntry.COL_DOB + " DATE )" ;

   // private static final String SQL_CREATE_ENTRIES_REVISIT_TABLE =
     //       "CREATE TABLE IF NOT EXISTS " +
   /* private static final String SQL_CREATE_ENTRIES_PAT_REG_COPY =
           "CREATE TABLE IF NOT EXISTS PAT_REG_COPY  ( pid INTEGER,fname TEXT,lname TEXT,gender INTEGER," +
                   "address TEXT, contact INTEGER, email TEXT,dob DATE,language TEXT, blood_group TEXT, PRIMARY KEY( pid ))";*/




    private static final String SQL_ALTER_TABLE_PAT =
            "ALTER TABLE " + PAT_REG + " ADD COLUMN " +  COL_PID + " INTEGER  ";


    private static final String SQL_DELETE_PAT_REG =
            "DROP TABLE IF EXISTS " + PAT_REG;
    private static final String SQL_DELETE_CALL_LIST_ =
            "DROP TABLE IF EXISTS " + CALL_LIST_TABLE;
private static final String SQL_DELETE_FB_ONE =
            "DROP TABLE IF EXISTS " + FB_FIRST;
    private static final String SQL_DELETE_FB_TWO =
            "DROP TABLE IF EXISTS " + FB_SECOND;
private static final String SQL_DELETE_CLINIC_REG =
            "DROP TABLE IF EXISTS " + CLINIC_TABLE;

private static final String SQL_DELETE_DOC_REG =
            "DROP TABLE IF EXISTS " + DOCTOR_TABLE;

private static final String SQL_DELETE_PAT_DET =
            "DROP TABLE IF EXISTS " + PATIENT_DETAILS_TABLE;


    public DbHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

/* Create new table*/
    public void onCreate(SQLiteDatabase db)
    {
       // db=openOrCreateDatabase("fbsystem", 0, null);

        db.execSQL(SQL_CREATE_ENTRIES_CLINIC_REG);
      db.execSQL(SQL_CREATE_ENTRIES_FB_FIRST);

        db.execSQL(SQL_CREATE_ENTRIES_FB_SECOND);
        Log.i("Database created"," success");

        db.execSQL(SQL_CREATE_ENTRIES_PAT_REG);

        db.execSQL(SQL_CREATE_ENTRIES_CALL_LIST_TABLE);
        db.execSQL(SQL_CREATE_ENTRIES_DOC_REG);
        db.execSQL(SQL_CREATE_ENTRIES_PATIENT_DETAILS);

        //db.execSQL(SQL_ALTER_TABLE_PAT);

    }
/* dropTable: It drops the table*/
    public void dropTable()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(SQL_DELETE_CALL_LIST_);
        db.execSQL(SQL_DELETE_PAT_REG);
        db.execSQL(SQL_DELETE_FB_ONE);
        db.execSQL(SQL_DELETE_FB_TWO);
        db.execSQL(SQL_DELETE_CLINIC_REG);
        db.execSQL(SQL_DELETE_DOC_REG);
        db.execSQL(SQL_DELETE_PAT_DET);

        Log.i("Table deleted", " success");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        //db.execSQL("DROP TABLE IF EXISTS contacts");
        onCreate(db);
/*
     if (newVersion >= oldVersion)
        {
               // db.execSQL(SQL_CREATE_ENTRIES_STAFF_REG);
            db.execSQL(SQL_CREATE_ENTRIES_PAT_REG);

           // db.execSQL(SQL_ALTER_TABLE_PAT);
    }*/
    }


    public long insertDB(String tableName, ContentValues contentValues)
    {
        SQLiteDatabase db = this.getWritableDatabase();
       // db.execSQL(SQL_ALTER_TABLE_PAT);

        long rowInserted = db.insert(tableName, null, contentValues);

        Log.e("data insertion", " failed");

        return rowInserted;
    }


    public Cursor getData(String tableName, String[] columns)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(tableName, columns, null, null, null, null, null);
    }

   // String selection = FbsContract.CallListEntry.COL_FB2+ "=?";
    //String[] selectionArgs = new String[] {"1"};

    public Cursor getDataWithWhere(String tableName, String[] columns, String selection, String[] selectionArgs)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        //db.query()
        return db.query(tableName, columns, selection, selectionArgs, null,null,null);
    }

    public void insertwithWhere (String TableName, String arg1)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE CALL_LIST_TABLE SET fb1 = '1', fb2='0' WHERE pid_fk =?",
                new Object[] {arg1});


        /*db.execSQL("INSERT INTO "+TableName+
                        "(fb1,fb2)"+
                        "VALUES("+
                        "(SELECT fid FROM  PATIENT_DETAILS_TABLE WHERE pid_fk =?),"+arg1+
                        //"(SELECT sid FROM  PA_TABLE WHERE sid =?)"+ ,arg3 ,fk_did  , String arg3
                        ")",
                new Object[]{ arg1});
    */
    }

    public void updateF2 (String dbString)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE CALL_LIST_TABLE  SET fb2 = 1 WHERE pid_fk = " + dbString);

    }

    public void insertFK_PD (String arg1)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("INSERT INTO PATIENT_DETAILS_TABLE"+
                "(pid_fk)"+
                        "VALUES((SELECT pid FROM PAT_REG WHERE pid =?)"+
                        //"(SELECT did FROM  DOCTOR_TABLE WHERE did =?)"+
                        //"(SELECT sid FROM  STAFF_TABLE WHERE sid =?)"+ ,arg3 ,fk_did  , String arg3
                        ")",
                        new Object[]{ arg1});
    }


    public void insertFK_FID_PID (String TableName, String arg1)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("INSERT INTO "+TableName+
                        "(fid_fk,pid_fk)"+
                        "VALUES("+
                        "(SELECT fid FROM  PATIENT_DETAILS_TABLE WHERE pid_fk =?),"+arg1+
                        //"(SELECT sid FROM  PA_TABLE WHERE sid =?)"+ ,arg3 ,fk_did  , String arg3
                        ")",
                new Object[]{ arg1});
    }



    public void insertFK_FID_PID_CONTACT (String TableName, String arg1,String arg2)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("INSERT INTO "+TableName+
                        "(pid_fk,fid_fk)"+
                        "VALUES("+
                        "(SELECT pid FROM  PAT_REG WHERE  contact=?),"+
                        "(SELECT fid FROM  PATIENT_DETAILS_TABLE WHERE (SELECT pid FROM  PAT_REG WHERE  contact=?))"+
                        //"(SELECT sid FROM  STAFF_TABLE WHERE sid =?)"+ ,arg3 ,fk_did  , String arg3
                        ")",
                new Object[]{ arg1,arg2});
    }




/*
    public Cursor getContact(String[] columns)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.execSQL("INSERT INTO " + CALL_LIST_TABLE +" VALUES ( " +COL_FID_PK +" ,( SELECT  "+COL_PID+ " FROM "+PAT_REG+" WHERE " + COL_FB1 + " , "
                +" , "+COL_FB2+                " ) ";
    }
*/


}
