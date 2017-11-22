package com.yadu.himalayamtnew.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.yadu.himalayamtnew.constants.CommonString;
import com.yadu.himalayamtnew.delegates.CoverageBean;
import com.yadu.himalayamtnew.xmlGetterSetter.JourneyPlanGetterSetter;

import java.util.ArrayList;

/**
 * Created by yadavendras on 14-11-2017.
 */

public class HimalayaDb extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "HIMALAYA_MT_DATABASE1";
    public static final int DATABASE_VERSION = 2;
    private SQLiteDatabase db;

    public HimalayaDb(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void open() {
        try {
            db = this.getWritableDatabase();
        } catch (Exception e) {
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CommonString.CREATE_TABLE_COVERAGE_DATA);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    //region Deepak_getCoverageData
    public ArrayList<CoverageBean> getCoverageData(String visitdate) {
        ArrayList<CoverageBean> list = new ArrayList<CoverageBean>();
        Cursor dbcursor = null;

        try {
            dbcursor = db.rawQuery("SELECT  * from " + CommonString.TABLE_COVERAGE_DATA
                    + " where " + CommonString.KEY_VISIT_DATE + "='" + visitdate + "'", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();

                while (!dbcursor.isAfterLast()) {
                    CoverageBean sb = new CoverageBean();

                    sb.setStoreId(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_STORE_ID)));
                    sb.setUserId((dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_USER_ID))));
                    sb.setInTime(((dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_IN_TIME)))));
                    sb.setOutTime(((dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_OUT_TIME)))));
                    sb.setVisitDate((((dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_VISIT_DATE))))));
                    sb.setLatitude(((dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_LATITUDE)))));
                    sb.setLongitude(((dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_LONGITUDE)))));
                    sb.setStatus((((dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_COVERAGE_STATUS))))));
                    sb.setImage((((dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_IMAGE))))));
                    sb.setReason((((dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_REASON))))));
                    sb.setReasonid((((dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_REASON_ID))))));
                    sb.setPJPDeviation(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_PJP_DEVIATION)).equals("1"));
                    sb.setMID(Integer.parseInt(((dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_ID))))));

                    if (dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_COVERAGE_REMARK)) == null) {
                        sb.setRemark("");
                    } else {
                        sb.setRemark((((dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_COVERAGE_REMARK))))));
                    }

                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }
        } catch (Exception e) {
            Log.d("Exception ", "when fetching Coverage Data!!!!!!!!!!!!!!!!!!!!!" + e.toString());
        }

        return list;
    }
    //endregion

    //region Deepak_isSkuMasterDownloaded
    public boolean isSkuMasterDownloaded() {
        boolean filled = false;
        Cursor dbcursor = null;
        try {
            dbcursor = db.rawQuery("SELECT * FROM SKU_MASTER ", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                int icount = dbcursor.getInt(0);
                dbcursor.close();
                if (icount > 0) {
                    filled = true;
                } else {
                    filled = false;
                }
            }

        } catch (Exception e) {
            return filled;
        }

        return filled;
    }
    //endregion

    //region Deepak_getJCPData
    public ArrayList<JourneyPlanGetterSetter> getJCPData(String date) {
        Log.d("Fetching", "Storedata--------------->Start<------------");
        ArrayList<JourneyPlanGetterSetter> list = new ArrayList<JourneyPlanGetterSetter>();
        Cursor dbcursor = null;

        try {
            dbcursor = db.rawQuery("SELECT * from JOURNEY_PLAN where VISIT_DATE = '" + date + "'", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();

                while (!dbcursor.isAfterLast()) {
                    JourneyPlanGetterSetter sb = new JourneyPlanGetterSetter();

                    sb.setStore_cd(dbcursor.getString(dbcursor.getColumnIndexOrThrow("STORE_CD")));
                    sb.setKey_account(dbcursor.getString(dbcursor.getColumnIndexOrThrow("KEYACCOUNT")));
                    sb.setStore_name((dbcursor.getString(dbcursor.getColumnIndexOrThrow("STORENAME"))));
                    sb.setCity((dbcursor.getString(dbcursor.getColumnIndexOrThrow("CITY"))));
                    sb.setUploadStatus((dbcursor.getString(dbcursor.getColumnIndexOrThrow("UPLOAD_STATUS"))));
                    sb.setCheckOutStatus((dbcursor.getString(dbcursor.getColumnIndexOrThrow("CHECKOUT_STATUS"))));
                    sb.setVISIT_DATE((dbcursor.getString(dbcursor.getColumnIndexOrThrow("VISIT_DATE"))));

                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }
        } catch (Exception e) {
            Log.d("Exception", " when fetching JCP!!!!!!!!!!!!!!!!!!!!!" + e.toString());
            return list;
        }

        Log.d("Fetching", "JCP data---------------------->Stop<-----------");
        return list;
    }
    //endregion

    //region Deepak_getPJPDeviationData
    public ArrayList<JourneyPlanGetterSetter> getPJPDeviationData(String date) {
        Log.d("Fetching", "Storedata--------------->Start<------------");
        ArrayList<JourneyPlanGetterSetter> list = new ArrayList<JourneyPlanGetterSetter>();
        Cursor dbcursor = null;

        try {
            dbcursor = db.rawQuery("SELECT * from JOURNEY_DEVIATION " +
                    "where VISIT_DATE = '" + date + "'", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();

                while (!dbcursor.isAfterLast()) {
                    JourneyPlanGetterSetter sb = new JourneyPlanGetterSetter();

                    sb.setStore_cd(dbcursor.getString(dbcursor.getColumnIndexOrThrow("STORE_CD")));
                    sb.setKey_account(dbcursor.getString(dbcursor.getColumnIndexOrThrow("KEYACCOUNT")));
                    sb.setStore_name((dbcursor.getString(dbcursor.getColumnIndexOrThrow("STORENAME"))));
                    sb.setCity((dbcursor.getString(dbcursor.getColumnIndexOrThrow("CITY"))));
                    sb.setUploadStatus((dbcursor.getString(dbcursor.getColumnIndexOrThrow("UPLOAD_STATUS"))));
                    sb.setCheckOutStatus((dbcursor.getString(dbcursor.getColumnIndexOrThrow("CHECKOUT_STATUS"))));
                    sb.setVISIT_DATE((dbcursor.getString(dbcursor.getColumnIndexOrThrow("VISIT_DATE"))));

                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }
        } catch (Exception e) {
            Log.d("Exception", " when fetching JCP!!!!!!!!!!!!!!!!!!!!!" + e.toString());
            return list;
        }

        Log.d("Fetching", "JCP data---------------------->Stop<-----------");
        return list;
    }
    //endregion

    //region Deepak_getCoverageSpecificData
    public ArrayList<CoverageBean> getCoverageSpecificData(String store_id) {

        ArrayList<CoverageBean> list = new ArrayList<CoverageBean>();
        Cursor dbcursor = null;

        try {

            dbcursor = db.rawQuery("SELECT  * from " + CommonString.TABLE_COVERAGE_DATA + " where " + CommonString.KEY_STORE_ID + "='" + store_id + "'",
                    null);

            if (dbcursor != null) {

                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    CoverageBean sb = new CoverageBean();

                    sb.setUserId((dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_USER_ID))));
                    sb.setInTime(((dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_IN_TIME)))));
                    sb.setOutTime(((dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_OUT_TIME)))));
                    sb.setVisitDate((((dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_VISIT_DATE))))));
                    sb.setLatitude(((dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_LATITUDE)))));
                    sb.setLongitude(((dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_LONGITUDE)))));
                    sb.setStatus((((dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_COVERAGE_STATUS))))));
                    sb.setReasonid(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_REASON_ID)));
                    sb.setPJPDeviation(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_PJP_DEVIATION)).equals("1"));

                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            Log.d("Exception when fetching Coverage Data!!!!!!!!!!!!!!!!!!!!!",
                    e.toString());

        }

        return list;

    }
    //endregion

    //region Deepak_Non working reason
    public String getNonEntryAllowReasonData(String reason_cd) {
        String entry_allow = "";
        Cursor dbcursor = null;

        try {

            dbcursor = db
                    .rawQuery(
                            "SELECT ENTRY_ALLOW FROM NON_WORKING_REASON WHERE REASON_CD = '" + reason_cd + "'"
                            , null);

            if (dbcursor != null) {

                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    entry_allow = dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("ENTRY_ALLOW"));
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return entry_allow;
            }
        } catch (Exception e) {
            Log.d("Exception when fetching entry Allow!!!!!!!!!!!",
                    e.toString());
            return entry_allow;
        }
        Log.d("Fetching non working data---------------------->Stop<-----------",
                "-------------------");
        return entry_allow;
    }
    //endregion

    //region Deepak_deleteAllCoverage
    public void deleteAllCoverage() {
        db.delete(CommonString.TABLE_COVERAGE_DATA, null, null);
    }
    //endregion

    //region Deepak_deleteSpecificCoverage
    public void deleteSpecificCoverage(String storeid) {
        db.delete(CommonString.TABLE_COVERAGE_DATA, CommonString.KEY_STORE_ID + "='" + storeid + "'", null);
    }
    //endregion



}
