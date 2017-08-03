package com.deepblueproject.fbsystem;

import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 *
 * Created by user pc on 12/21/2016.
 */

public final class FbsContract  {

    private FbsContract()
    {}

    public static class FbFirstEntry implements BaseColumns
    {
        public static final String _ID = BaseColumns._ID;
        public static final String FB_FIRST = "fb_first";
        public static final String COL_FK_FID = "fid_fk";
        public static final String COL_FK_PID = "pid_fk";
        public static final String COL_FB1_Q1 = "Q1";
        public static final String COL_FB1_Q2 = "Q2";
        public static final String COL_FB1_Q3 = "Q3";
        public static final String COL_FB1_Q4 = "Q4";
        public static final String COL_FB1_Q5 = "Q5";
        public static final String COL_FB1_Q6 = "Q6";

        public static final int SUPER = 5;
        public static final int GOOD = 4;
        public static final int AVG = 3;
        public static final int BAD = 2;

    }

    public static class FbSecondEntry implements BaseColumns
    {
        public static final String _ID = BaseColumns._ID;
        public static final String FB_SECOND = "fb_second";
        public static final String COL_FK_FID = "fid_fk";
        public static final String COL_FK_PID = "pid_fk";
        //public static final String COL_FK_CONTACT = "contact_fk";
        public static final String COL_FB2_Q1 = "Q1";
        public static final String COL_FB2_Q2 = "Q2";
        public static final String COL_FB2_Q3 = "Q3";
       // public static final String COL_FB2_Q4 = "Q4";
       // public static final String COL_FB2_Q5 = "Q5";
       // public static final String COL_FB2_Q6 = "Q6";

        public static final int SUPER = 5;
        public static final int GOOD = 4;
        public static final int AVG = 3;
        public static final int BAD = 2;

    }

    public  static class  ClinicEntry implements BaseColumns
    {
        public static final String _ID = BaseColumns._ID;
        public static final String COL_CID = "cid";
        public static final String CLINIC_TABLE = "clinic_details";
        public static final String COL_CLINIC_ADD = "address";
        public static final String COL_CLINIC_CONTACT = "contact";
        public static final String COL_CLINIC_EMAIL = "email";

    }

    public static class DoctorEntry implements BaseColumns
    {
        public static final String _ID = BaseColumns._ID;
        public static final String DOCTOR_TABLE = "doc_details";
        public static final String COL_DID = "did";
        public static final String COL_DOC_NAME_FIRST = "fname";
        public static final String COL_DOC_NAME_LAST = "lname";
        public static final String COL_DOC_GENDER = "gender";
        public static final String COL_DOC_DOB = "dob";
        public static final String COL_DOC_CONTACT = "contact";
        public static final String COL_DOC_EMAIL = "email";
        //public static final String COL_SBLD_GRP = "blood_grp";
        //public static final String COL_TYPE = "type";

        public static  int MALE = 0;
        public static  int FEMALE = 1;
        //public static  int STAFF = 0;
        //public static  int DOCTOR = 1;

    }

    public static class PatientDetailsEntry implements BaseColumns
    {
        public static final String _ID = BaseColumns._ID;
        public static final String PATIENT_DETAILS_TABLE = "patient_details_table";
        public static final String COL_FID = "fid";
        public static final String COL_OBS = "obs";
        public static final String COL_DAYS = "days";
        public static final String COL_NOTES = "notes";
        public static final String COL_TEST = "test";
        public static final String COL_DATE = "date";
        public static final String COL_FK_PID = "pid_fk";
        //public static final String COL_FK_DID = "did_fk";
        //public static final String COL_FK_CID = "cid_fk";


    }

    public static class CallListEntry implements BaseColumns
    {
        public static final String _ID = BaseColumns._ID;
        public static final String CALL_LIST_TABLE = "call_list_table";
        public static final String COL_FID_FK = "fid_fk";
        public static final String COL_PID_FK = "pid_fk";
        //public static final String COL_DID_FK = "did_fk";
        public static final String COL_FB1 = "fb1";
        public static final String COL_FB2 = "fb2";


        public static final int FILLED = 1;
        public static final int UNFILLED = 0;
    }

    /*
    public static class RevisitEntry implements BaseColumns
    {
        public static final String _ID = BaseColumns._ID;
        public static final String REVISIT_LIST_TABLE = "revisit_list_table";
        public static final String
    }
*/
    public static class PatientEntry implements BaseColumns
    {
        public static final String _ID = BaseColumns._ID;
        public static final String PAT_REG = "pat_reg";
        public static final String COL_PID = "pid";
        public static final String COL_NAME_FIRST = "fname";
        public static final String COL_NAME_LAST = "lname";
        public static final String COL_GENDER = "gender";
        public static final String COL_DOB = "dob";
        public static final String COL_ADDRESS = "address";
        public static final String COL_CONTACT = "contact";
        public static final String COL_EMAIL = "email";
        //public static final String COL_LANG = "lang";
       // public static final String COL_BLD_GRP = "blood_grp";

        public static final int MALE = 0;
        public static final int FEMALE = 1;
    }

}
