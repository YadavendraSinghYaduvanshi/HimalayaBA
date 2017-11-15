package com.yadu.himalayamtnew.constants;

/**
 * Created by yadavendras on 13-11-2017.
 */

public class CommonString {

    public static final String KEY_USERNAME = "username";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_REMEMBER = "remember";
    public static final String KEY_RIGHT_NAME = "right_name";
    public static final String KEY_PATH = "path";
    public static final String KEY_VERSION = "version";
    public static final String NAMESPACE = "http://tempuri.org/";
    public static final String METHOD_LOGIN = "UserLoginDetail";
    public static final String URL = "http://himalaya.parinaam.in/himalaya.asmx";
    public static final String SOAP_ACTION_LOGIN = "http://tempuri.org/"
            + METHOD_LOGIN;
    public static final String KEY_FAILURE = "Failure";
    public static final String MESSAGE_FAILURE = "Server Error.Please Access After Some Time";
    public static final String MESSAGE_FALSE = "Invalid User";
    public static final String KEY_CHANGED = "Changed";
    public static final String KEY_FALSE = "False";
    public static final String MESSAGE_CHANGED = "Invalid UserId Or Password / Password Has Been Changed.";
    public static final String KEY_DATE = "date";
    public static final String KEY_USER_TYPE = "RIGHTNAME";
    public static final String KEY_SUCCESS = "Success";
    public static final String MESSAGE_EXCEPTION = "Problem Occured : Report The Problem To Parinaam";
    public static final String MESSAGE_SOCKETEXCEPTION = "Network Communication Failure. Check Your Network Connection";
    public static final String TABLE_COVERAGE_DATA = "COVERAGE_DATA";
    public static final String KEY_ID = "id";
    public static final String KEY_STORE_ID = "store_id";
    public static final String KEY_IN_TIME = "in_time";
    public static final String KEY_OUT_TIME = "out_time";
    public static final String KEY_VISIT_DATE = "visit_date";
    public static final String KEY_LATITUDE = "latitude";
    public static final String KEY_LONGITUDE = "longitude";
    public static final String KEY_COVERAGE_STATUS = "Coverage";
    public static final String KEY_REASON_ID = "reason_id";
    public static final String KEY_REASON = "reason";
    public static final String KEY_MERCHANDISER_ID = "merchandiser_id";
    public static final String KEY_PJP_DEVIATION = "pjp_deviation";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_COVERAGE_REMARK = "remark";
    public static final String KEY_USER_ID = "user_id";
    public static final String KEY_INVALID = "INVALID";
    public static final String KEY_VALID = "Valid";
    public static final String KEY_P = "P";
    public static final String KEY_D = "D";
    public static final String KEY_U = "U";
    public static final String KEY_C = "Y";
    public static final String STORE_STATUS_LEAVE = "L";
    public static final String KEY_STOREVISITED = "L";
    public static final String KEY_STOREVISITED_STATUS = "STORE_VISITED_STATUS";
    public static final String KEY_STORE_NAME = "STORE_NAME";
    public static final String KEY_STORE_CD = "STORE_CD";
    public static final String KEY_STORE_IN_TIME = "Store_in_time";
    public static final String DATA_DELETE_ALERT_MESSAGE = "Saved data will be lost - Do you want to continue?";

    public static final String CREATE_TABLE_COVERAGE_DATA = "CREATE TABLE  IF NOT EXISTS "
            + TABLE_COVERAGE_DATA + " ("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
            + KEY_STORE_ID + " VARCHAR,USER_ID VARCHAR, "
            + KEY_IN_TIME + " VARCHAR,"
            + KEY_OUT_TIME + " VARCHAR,"
            + KEY_VISIT_DATE + " VARCHAR,"
            + KEY_LATITUDE + " VARCHAR,"
            + KEY_LONGITUDE + " VARCHAR,"
            + KEY_MERCHANDISER_ID + " VARCHAR,"
            + KEY_COVERAGE_STATUS + " VARCHAR,"
            + KEY_IMAGE + " VARCHAR,"
            + KEY_REASON_ID + " VARCHAR,"
            + KEY_PJP_DEVIATION + " VARCHAR,"
            + KEY_COVERAGE_REMARK + " VARCHAR,"
            + KEY_REASON + " VARCHAR)";

}
