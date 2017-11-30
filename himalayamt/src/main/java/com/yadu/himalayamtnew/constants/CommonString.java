package com.yadu.himalayamtnew.constants;

import android.os.Environment;

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
    public static final String KEY_NO_DATA = "NoData";
    public static final String MESSAGE_FAILURE = "Server Error.Please Access After Some Time";
    public static final String MESSAGE_FALSE = "Invalid User";
    public static final String KEY_CHANGED = "Changed";
    public static final String KEY_FALSE = "False";
    public static final String MESSAGE_CHANGED = "Invalid UserId Or Password / Password Has Been Changed.";
    public static final String KEY_DATE = "date";
    public static final String METHOD_Checkout_StatusNew = "Upload_Store_ChecOut_Status_V1";
    public static final String KEY_FOOD_STORE = "FOOD_STORE";
    public static final String KEY_USER_TYPE = "RIGHTNAME";
    public static final String KEY_SUCCESS = "Success";
    public static final String MESSAGE_EXCEPTION = "Problem Occured : Report The Problem To Parinaam";
    public static final String MESSAGE_SOCKETEXCEPTION = "Network Communication Failure. Check Your Network Connection";
    public static final String MESSAGE_XmlPull = "Problem Occured xml pull: Report The Problem To Parinaam";
    public static final String ONBACK_ALERT_MESSAGE = "Unsaved data will be lost - Do you want to continue?";
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
    public static final String KEY_CATEGORY_ID = "CATEGORY_ID";
    public static final String KEY_C = "Y";
    public static final String KEY_POSITION = "POSITION";
    public static final String STORE_STATUS_LEAVE = "L";
    public static final String KEY_STOREVISITED = "L";
    public static final String KEY_STOREVISITED_STATUS = "STORE_VISITED_STATUS";
    public static final String KEY_STORE_NAME = "STORE_NAME";
    public static final String KEY_STORE_CD = "STORE_CD";
    public static final String KEY_STORE_IN_TIME = "Store_in_time";
    public static final String DATA_DELETE_ALERT_MESSAGE = "Saved data will be lost - Do you want to continue?";
    //region ysy
    public static final String METHOD_UPLOAD_XML = "DrUploadXml";
    public static final String SOAP_ACTION = "http://tempuri.org/";
    public static final String KEY_STATUS = "STATUS";
    public static final String KEY_NEW_UPDATE = "New Update Available";
    public static final String KEY_CHECKOUT_STATUS = "CHECKOUT_STATUS";
    public static final String METHOD_UPLOAD_DR_STORE_COVERAGE = "UPLOAD_COVERAGE";
    public static final String MEHTOD_UPLOAD_COVERAGE_STATUS = "UploadCoverage_Status";
    public static final String METHOD_NAME_UNIVERSAL_DOWNLOAD = "Download_Universal";
    public static final String SOAP_ACTION_UNIVERSAL = "http://tempuri.org/"
            + METHOD_NAME_UNIVERSAL_DOWNLOAD;
    public static final String METHOD_UPLOAD_IMAGE = "GetImageWithFolderName";
    public static final String URL_Notice_Board = "http://himalaya.parinaam.in/notice/notice.html";
    public static final String ERROR = " PROBLEM OCCURED IN ";
    public static final String CHECK_LIST_ID = "CHECK_LIST_ID";
    public static final String CHECK_LIST = "CHECK_LIST";
    public static final String CHECK_LIST_TEXT = "CHECK_LIST_TEXT";
    public static final String CHECK_LIST_TYPE = "CHECK_LIST_TYPE";
    public static final String ASSET_CD = "ASSET_CD";
    public static final String COMMONID = "COMMONID";
    public static final String REASON_ID = "REASON_ID";
    public static final String MESSAGE_DOWNLOAD = "Data Downloaded Successfully";
    public static final String MESSAGE_UPLOAD_DATA = "Data Uploaded Successfully";
    public static final String MESSAGE_UPLOAD_IMAGE = "Images Uploaded Successfully";
    public static final String MESSAGE_JCP_FALSE = "Data is not found in ";
    public static final String MESSAGE_NO_DATA = "No Data For Upload";
    public static final String FILE_PATH = Environment.getExternalStorageDirectory() + "/Himalaya_MT_Images/";
    //endregion
    public static final String KEY_SUCCESS_chkout = "Success";
    public static final String SOAP_ACTION_UPLOAD_IMAGE = "http://tempuri.org/" + METHOD_UPLOAD_IMAGE;
    public static final String CREATE_TABLE_COVERAGE_DATA = "CREATE TABLE  IF NOT EXISTS "
            + TABLE_COVERAGE_DATA + " ("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
            + KEY_STORE_ID + " VARCHAR,USER_ID VARCHAR, "
            + KEY_IN_TIME + " VARCHAR,"
            + KEY_OUT_TIME + " VARCHAR,"
            + KEY_VISIT_DATE + " VARCHAR,"
            + KEY_LATITUDE + " VARCHAR,"
            + KEY_LONGITUDE + " VARCHAR,"
            + KEY_MERCHANDISER_ID + " INTEGER,"
            + KEY_COVERAGE_STATUS + " VARCHAR,"
            + KEY_IMAGE + " VARCHAR,"
            + KEY_REASON_ID + " INTEGER,"
            + KEY_PJP_DEVIATION + " VARCHAR,"
            + KEY_COVERAGE_REMARK + " VARCHAR,"
            + KEY_REASON + " VARCHAR)";

    public static final String TABLE_AUDIT_DATA_SAVE = "Audit_Data_Save";
    public static final String CREATE_TABLE_AUDIT_DATA_SAVE = "CREATE TABLE IF NOT EXISTS "
            + TABLE_AUDIT_DATA_SAVE
            + " ("
            + "KEY_ID"
            + " INTEGER PRIMARY KEY AUTOINCREMENT ,"

            + "STORE_CD"
            + " INTEGER,"

            + "QUESTION_ID"
            + " INTEGER,"

            + "QUESTION"
            + " VARCHAR,"

            + "ANSWER_ID"
            + " INTEGER,"

            + "CATEGORY_ID"
            + " INTEGER"

            + ")";

    public static final String TABLE_DEEPFREEZER_DATA = "DEEPFREEZER_DATA";
    public static final String CREATE_TABLE_DEEPFREEZER_DATA =
            "CREATE TABLE IF NOT EXISTS " + TABLE_DEEPFREEZER_DATA +
                    "(" +
                    "FID" +
                    " INTEGER, " +

                    "STORE_CD" +
                    " INTEGER," +

                    "DEEP_FREEZER" +
                    " VARCHAR, " +

                    "FREEZER_TYPE" +
                    " VARCHAR, " +

                    "STATUS" +
                    " VARCHAR, " +

                    "REMARK" +
                    " VARCHAR" +
                    ")";

    public static final String TABLE_FACING_COMPETITOR_DATA = "FACING_COMPETITOR_DATA";
    public static final String CREATE_TABLE_FACING_COMPETITOR_DATA =
            "CREATE TABLE IF NOT EXISTS " + TABLE_FACING_COMPETITOR_DATA +
                    "(" +
                    "KEY_ID" +
                    " INTEGER PRIMARY KEY AUTOINCREMENT, " +

                    "STORE_CD" +
                    " INTEGER, " +

                    "CATEGORY_CD" +
                    " INTEGER, " +

                    "CATEGORY" +
                    " VARCHAR, " +

                    "BRAND" +
                    " VARCHAR, " +

                    "BRAND_CD" +
                    " INTEGER, " +

                    "FACING" +
                    " INTEGER " +
                    ")";

    public static final String TABLE_OPENING_STOCK_DATA = "OPENING_STOCK_DATA";
    public static final String CREATE_TABLE_OPENING_STOCK_DATA = "CREATE TABLE IF NOT EXISTS OPENING_STOCK_DATA(Common_Id INTEGER, SKU_CD INTEGER,STORE_CD INTEGER, CATEGORY_TYPE VARCHAR, AS_PER_MCCAIN VARCHAR, ACTUAL_LISTED VARCHAR, OPENING_STOCK_COLD_ROOM VARCHAR, OPENING_STOCK_MCCAIN_DF VARCHAR, TOTAL_FACING_MCCAIN_DF VARCHAR, OPENING_STOCK_STORE_DF VARCHAR, TOTAL_FACING_STORE_DF VARCHAR, MATERIAL_WELLNESS VARCHAR)";

    public static final String TABLE_CLOSING_STOCK_DATA = "CLOSING_STOCK_DATA";
    public static final String CREATE_TABLE_CLOSING_STOCK_DATA = "CREATE TABLE IF NOT EXISTS CLOSING_STOCK_DATA(Common_Id INTEGER, SKU_CD INTEGER,STORE_CD INTEGER, COLD_ROOM VARCHAR, MCCAIN_DF VARCHAR, STORE_DF VARCHAR)";

    public static final String TABLE_MIDDAY_STOCK_DATA = "MIDDAY_STOCK_DATA";
    public static final String CREATE_TABLE_MIDDAY_STOCK_DATA = "CREATE TABLE IF NOT EXISTS MIDDAY_STOCK_DATA(Common_Id INTEGER,SKU_CD INTEGER,STORE_CD INTEGER, MIDDAY_STOCK VARCHAR)";

    public static final String TABLE_FOOD_STORE_DATA = "FOOD_STORE_DATA";
    public static final String CREATE_TABLE_FOOD_STORE_DATA = "CREATE TABLE IF NOT EXISTS FOOD_STORE_DATA(Common_Id INTEGER,STORE_CD INTEGER, SKU_CD VARCHAR, SKU VARCHAR,AS_PER_MCCAIN VARCHAR,ACTUAL_LISTED VARCHAR, MCCAIN_DF VARCHAR, STORE_DF VARCHAR, MTD_SALES VARCHAR, PACKING_SIZE VARCHAR)";

    public static final String TABLE_ASSET_DATA = "ASSET_DATA";
    public static final String CREATE_TABLE_ASSET_DATA = "CREATE TABLE IF NOT EXISTS ASSET_DATA" +
            "(" +
            KEY_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            "Common_Id INTEGER," +
            "ASSET_CD INTEGER," +
            "STORE_CD INTEGER, " +
            "ASSET VARCHAR, " +
            "PRESENT VARCHAR, " +
            "REMARK VARCHAR," +
            "PLANOGRAM_IMG VARCHAR," +
            "IMAGE VARCHAR" +
            ")";

    public static final String TABLE_CALLS_DATA = "CALLS_DATA";
    public static final String CREATE_TABLE_CALLS_DATA = "CREATE TABLE IF NOT EXISTS CALLS_DATA(Key_Id INTEGER PRIMARY KEY AUTOINCREMENT, STORE_CD INTEGER, TOTAL_CALLS VARCHAR, PRODUCTIVE_CALLS VARCHAR)";

    public static final String TABLE_COMPETITION_POI = "COMPETITION_POI";
    public static final String CREATE_TABLE_COMPETITION_POI = "CREATE TABLE IF NOT EXISTS COMPETITION_POI(Key_Id INTEGER PRIMARY KEY AUTOINCREMENT, STORE_CD INTEGER, CATEGORY_CD VARCHAR, ASSET_CD VARCHAR, CATEGORY VARCHAR, ASSET VARCHAR, BRAND_CD VARCHAR, BRAND VARCHAR, REMARK VARCHAR)";

    public static final String TABLE_POI = "POI";
    public static final String CREATE_TABLE_POI = "CREATE TABLE IF NOT EXISTS POI(Key_Id INTEGER PRIMARY KEY AUTOINCREMENT, STORE_CD INTEGER, CATEGORY_CD VARCHAR, ASSET_CD VARCHAR, CATEGORY VARCHAR, ASSET VARCHAR, BRAND_CD VARCHAR, BRAND VARCHAR, REMARK VARCHAR, IMAGE_POI VARCHAR)";

    public static final String TABLE_COMPETITION_PROMOTION = "COMPETITION_PROMOTION";
    public static final String CREATE_TABLE_COMPETITION_PROMOTION = "CREATE TABLE IF NOT EXISTS COMPETITION_PROMOTION(Key_Id INTEGER PRIMARY KEY AUTOINCREMENT, STORE_CD INTEGER, CATEGORY_CD VARCHAR, CATEGORY VARCHAR, BRAND_CD VARCHAR, BRAND VARCHAR, REMARK VARCHAR, PROMOTION VARCHAR)";

    public static final String TABLE_ASSET_CHECKLIST_INSERT = "ASSET_CHECKLIST_INSERT";

    public static final String CREATE_TABLE_ASSET_CHECKLIST_INSERT = "CREATE TABLE "
            + TABLE_ASSET_CHECKLIST_INSERT
            + " ("
            + KEY_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
            + CHECK_LIST_ID
            + " VARCHAR,"
            + CHECK_LIST
            + " VARCHAR,"
            + CHECK_LIST_TEXT
            + " VARCHAR,"
            + CHECK_LIST_TYPE
            + " VARCHAR,"
            + KEY_STORE_CD
            + " INTEGER,"
            + ASSET_CD
            + " VARCHAR,"

            + "CATEGORY_CD"
            + " VARCHAR,"

            + KEY_VISIT_DATE
            + " VARCHAR)";


    //New Changes 12-04-2017
    public static final String TABLE_INSERT_OPENINGHEADER_DATA = "openingHeader_data";

    public static final String CREATE_TABLE_insert_OPENINGHEADER_DATA =
            "CREATE TABLE IF NOT EXISTS " + TABLE_INSERT_OPENINGHEADER_DATA
                    + " ("
                    + "KEY_ID" +
                    " INTEGER PRIMARY KEY AUTOINCREMENT,"

                    + "STORE_CD" +
                    " INTEGER,"

                    + "CATEGORY_CD" +
                    " INTEGER,"

                    + "CATEGORY" +
                    " VARCHAR"
                    + ")";

    public static final String TABLE_STOCK_DATA = "STOCK_DATA";

    public static final String CREATE_TABLE_STOCK_DATA =
            "CREATE TABLE IF NOT EXISTS " + TABLE_STOCK_DATA +
                    "(" +
                    "Common_Id" +
                    " INTEGER, " +

                    "STORE_CD" +
                    " INTEGER, " +

                    "CATEGORY_CD" +
                    " INTEGER, " +

                    "CATEGORY" +
                    " VARCHAR, " +

                    "BRAND_CD" +
                    " INTEGER, " +

                    "BRAND" +
                    " VARCHAR, " +

                    "SKU_CD" +
                    " INTEGER," +

                    "SKU" +
                    " VARCHAR, " +

                    "STOCK_UNDER_DAYS" +
                    " VARCHAR, " +

                    "OPENING_STOCK" +
                    " INTEGER, " +

                    "OPENING_FACING" +
                    " INTEGER, " +

                    "MIDDAY_STOCK" +
                    " INTEGER, " +

                    "COMPANY_CD" +
                    " INTEGER, " +

                    "CLOSING_STOCK" +
                    " INTEGER" +
                    ")";

    public static final String TABLE_STOCK_IMAGE = "STOCK_IMAGE";

    public static final String CREATE_TABLE_STOCK_IMAGE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_STOCK_IMAGE +
                    "(" +
                    "Key_Id" +
                    " INTEGER PRIMARY KEY AUTOINCREMENT, " +

                    "STORE_CD" +
                    " INTEGER, " +

                    "CATEGORY_CD" +
                    " INTEGER, " +

                    "HIMALAYA_PHOTO" +
                    " INTEGER, " +

                    "CATEGORY_PHOTO" +
                    " INTEGER, " +

                    "CATEGORY" +
                    " VARCHAR, " +

                    "IMAGE_STK" +
                    " VARCHAR, " +

                    "IMAGE_CAT_ONE" +
                    " VARCHAR, " +

                    "IMAGE_CAT_TWO" +
                    " VARCHAR, " +

                    "VISIT_DATE" +
                    " VARCHAR" +
                    ")";


    public static final String TABLE_INSERT_PROMOTION_HEADER_DATA = "openingHeader_Promotion_data";
    public static final String CREATE_TABLE_insert_HEADER_PROMOTION_DATA = "CREATE TABLE IF NOT EXISTS "
            + TABLE_INSERT_PROMOTION_HEADER_DATA
            + "("
            + " KEY_ID"
            + " INTEGER PRIMARY KEY AUTOINCREMENT ,"

            + "STORE_CD"
            + " INTEGER,"

            + "BRAND_CD"
            + " INTEGER,"

            + "BRAND"
            + " VARCHAR" +
            ")";

    public static final String TABLE_PROMOTION_DATA = "PROMOTION_DATA";
    public static final String CREATE_TABLE_PROMOTION_DATA = "CREATE TABLE IF NOT EXISTS "
            + TABLE_PROMOTION_DATA
            + "("
            + "Common_Id "
            + "INTEGER,"

            + "PID"
            + " INTEGER,"

            + "STORE_CD"
            + " INTEGER,"

            + "PROMOTION"
            + " VARCHAR, "

            + "CATEGORY_TYPE"
            + " VARCHAR, "

            + "PRESENT"
            + " VARCHAR, "

            + "REMARK"
            + " VARCHAR, "

            + "CAMERA"
            + " VARCHAR, "

            + "PROMO_STOCK"
            + " VARCHAR, "

            + "PROMO_TALKER"
            + " VARCHAR, "

            + "RUNNING_POS"
            + " VARCHAR, "

            + "IMAGE"
            + " VARCHAR" +
            ")";


    public static final String TABLE_ASSET_SKU_CHECKLIST_INSERT = "Paid_Visibility_SkuDailog";
    public static final String CREATE_TABLE_ASSET_SKU_CHECKLIST_INSERT = "CREATE TABLE IF NOT EXISTS "
            + TABLE_ASSET_SKU_CHECKLIST_INSERT
            + " ("
            + "KEY_ID"
            + " INTEGER PRIMARY KEY AUTOINCREMENT ,"

            + "VISIT_DATE"
            + " VARCHAR,"

            + "STORE_CD"
            + " INTEGER,"

            + "ASSET_CD"
            + " INTEGER,"

            + "CATEGORY_CD"
            + " INTEGER,"

            + "SKU_CD"
            + " INTEGER,"

            + "SKU"
            + " VARCHAR,"

            + "BRAND_CD"
            + " VARCHAR,"

            + "BRAND"
            + " VARCHAR,"

            + "COMMONID"
            + " VARCHAR,"

            + "SKU_QUANTITY"
            + " VARCHAR"
            + ")";

    public static final String TABLE_INSERT_OPENINGHEADER_CLOSING_DATA = "openingHeader_Closing_data";
    public static final String CREATE_TABLE_insert_OPENINGHEADER_CLOSING_DATA =
            "CREATE TABLE IF NOT EXISTS " + TABLE_INSERT_OPENINGHEADER_CLOSING_DATA
                    + "("
                    + "KEY_ID"
                    + " INTEGER PRIMARY KEY AUTOINCREMENT ,"

                    + "STORE_CD"
                    + " INTEGER,"

                    + "BRAND_CD"
                    + " INTEGER,"

                    + "BRAND"
                    + " VARCHAR" +
                    ")";

    public static final String TABLE_INSERT_HEADER_MIDDAY_DATA = "openingHeader_Midday_data";
    public static final String CREATE_TABLE_insert_HEADER_MIDDAY_DATA = "CREATE TABLE IF NOT EXISTS "
            + TABLE_INSERT_HEADER_MIDDAY_DATA
            + "("
            + "KEY_ID"
            + " INTEGER PRIMARY KEY AUTOINCREMENT ,"

            + "STORE_CD"
            + " INTEGER,"

            + "BRAND_CD"
            + " VARCHAR,"

            + "BRAND"
            + " VARCHAR" +
            ")";

    public static final String TABLE_INSERT_ASSET_HEADER_DATA = "openingHeader_Asset_data";
    public static final String CREATE_TABLE_insert_HEADER_ASSET_DATA = "CREATE TABLE IF NOT EXISTS "
            + TABLE_INSERT_ASSET_HEADER_DATA
            + "("
            + "KEY_ID"
            + " INTEGER PRIMARY KEY AUTOINCREMENT ,"

            + "STORE_CD"
            + " INTEGER,"

            + "CATEGORY_CD"
            + " INTEGER,"

            + "CATEGORY"
            + " VARCHAR" +
            ")";

    public static final String TABLE_INSERT_HEADER_FOOD_STORE_DATA = "openingHeader_FOOD_STORE_data";
    public static final String CREATE_TABLE_insert_HEADER_FOOD_STORE_DATA =
            "CREATE TABLE IF NOT EXISTS " + TABLE_INSERT_HEADER_FOOD_STORE_DATA
                    + "("
                    + "KEY_ID"
                    + " INTEGER PRIMARY KEY AUTOINCREMENT ,"

                    + "STORE_CD"
                    + " INTEGER,"

                    + "BRAND_CD"
                    + " INTEGER,"

                    + "BRAND"
                    + " VARCHAR" +
                    ")";

    public static final String TABLE_ASSET_CHECKLIST_DATA = "ASSET_CHECKLIST_DATA";
    public static final String CREATE_ASSET_CHECKLIST_DATA = "CREATE TABLE "
            + TABLE_ASSET_CHECKLIST_DATA
            + " ("
            + KEY_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
            + CHECK_LIST_ID
            + " VARCHAR,"
            + CHECK_LIST
            + " VARCHAR,"
            + CHECK_LIST_TEXT
            + " VARCHAR,"
            + CHECK_LIST_TYPE
            + " VARCHAR,"
            + COMMONID
            + " VARCHAR,"
            + REASON_ID
            + " INTEGER)";

    public static final String TABLE_PJP_DEVIATION = "PJP_DEVIATION";
    public static final String CREATE_TABLE_PJP_DEVIATION = "CREATE TABLE  IF NOT EXISTS "
            + TABLE_PJP_DEVIATION + " (" + KEY_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
            + KEY_STORE_CD + " VARCHAR, "
            + KEY_VISIT_DATE + " VARCHAR)";

}
