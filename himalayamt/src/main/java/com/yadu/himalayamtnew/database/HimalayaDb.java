package com.yadu.himalayamtnew.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.yadu.himalayamtnew.constants.CommonString;
import com.yadu.himalayamtnew.delegates.CoverageBean;
import com.yadu.himalayamtnew.xmlGetterSetter.AssetChecklistGetterSetter;
import com.yadu.himalayamtnew.xmlGetterSetter.AssetChecklistReasonGettersetter;
import com.yadu.himalayamtnew.xmlGetterSetter.AssetInsertdataGetterSetter;
import com.yadu.himalayamtnew.xmlGetterSetter.AssetMasterGetterSetter;
import com.yadu.himalayamtnew.xmlGetterSetter.Audit_QuestionDataGetterSetter;
import com.yadu.himalayamtnew.xmlGetterSetter.Audit_QuestionGetterSetter;
import com.yadu.himalayamtnew.xmlGetterSetter.BrandGetterSetter;
import com.yadu.himalayamtnew.xmlGetterSetter.CategoryMasterGetterSetter;
import com.yadu.himalayamtnew.xmlGetterSetter.ChecklistInsertDataGetterSetter;
import com.yadu.himalayamtnew.xmlGetterSetter.CompanyGetterSetter;
import com.yadu.himalayamtnew.xmlGetterSetter.HeaderGetterSetter;
import com.yadu.himalayamtnew.xmlGetterSetter.JCPGetterSetter;
import com.yadu.himalayamtnew.xmlGetterSetter.JourneyPlanGetterSetter;
import com.yadu.himalayamtnew.xmlGetterSetter.MappingAssetChecklistGetterSetter;
import com.yadu.himalayamtnew.xmlGetterSetter.MappingAssetChecklistreasonGetterSetter;
import com.yadu.himalayamtnew.xmlGetterSetter.MappingAssetGetterSetter;
import com.yadu.himalayamtnew.xmlGetterSetter.MappingAvailabilityGetterSetter;
import com.yadu.himalayamtnew.xmlGetterSetter.MappingPromotionGetterSetter;
import com.yadu.himalayamtnew.xmlGetterSetter.NonComplianceChecklistGetterSetter;
import com.yadu.himalayamtnew.xmlGetterSetter.NonWorkingReasonGetterSetter;
import com.yadu.himalayamtnew.xmlGetterSetter.POIGetterSetter;
import com.yadu.himalayamtnew.xmlGetterSetter.PayslipGetterSetter;
import com.yadu.himalayamtnew.xmlGetterSetter.PromotionInsertDataGetterSetter;
import com.yadu.himalayamtnew.xmlGetterSetter.SkuGetterSetter;
import com.yadu.himalayamtnew.xmlGetterSetter.SkuMasterGetterSetter;
import com.yadu.himalayamtnew.xmlGetterSetter.StockGetterSetter;
import com.yadu.himalayamtnew.xmlGetterSetter.StockNewGetterSetter;
import com.yadu.himalayamtnew.xmlGetterSetter.TableBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by yadavendras on 14-11-2017.
 */

public class HimalayaDb extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "HIMALAYA_MT_DATABASE2";
    public static final int DATABASE_VERSION = 3;
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

        db.execSQL(TableBean.getjcptable());
        db.execSQL(TableBean.getSkumastertable());
        db.execSQL(TableBean.getMappingavailtable());
        db.execSQL(TableBean.getMappingpromotable());
        db.execSQL(TableBean.getMappingassettable());
        db.execSQL(TableBean.getAssetmastertable());
        db.execSQL(TableBean.getCompanytable());
        db.execSQL(TableBean.getNonworkingtable());
        db.execSQL(TableBean.getBrandtable());
        db.execSQL(TableBean.getAsset_checklist_table());
        db.execSQL(TableBean.getMapping_asset_checklist_table());
        db.execSQL(TableBean.getEmp_payslip_table());
        db.execSQL(TableBean.getCategorymastertable());
        db.execSQL(TableBean.getAudit_question_table());
        db.execSQL(TableBean.getTable_NON_COMPLIANCE_CHECKLIST());
        db.execSQL(TableBean.getTable_MAPPING_ASSET_CHECKLIST_REASON());

        db.execSQL(CommonString.CREATE_TABLE_DEEPFREEZER_DATA);
        db.execSQL(CommonString.CREATE_TABLE_OPENING_STOCK_DATA);
        db.execSQL(CommonString.CREATE_TABLE_CLOSING_STOCK_DATA);
        db.execSQL(CommonString.CREATE_TABLE_MIDDAY_STOCK_DATA);
        db.execSQL(CommonString.CREATE_TABLE_insert_OPENINGHEADER_DATA);
        db.execSQL(CommonString.CREATE_TABLE_insert_OPENINGHEADER_CLOSING_DATA);
        db.execSQL(CommonString.CREATE_TABLE_insert_HEADER_MIDDAY_DATA);
        db.execSQL(CommonString.CREATE_TABLE_insert_HEADER_PROMOTION_DATA);
        db.execSQL(CommonString.CREATE_TABLE_PROMOTION_DATA);
        db.execSQL(CommonString.CREATE_TABLE_insert_HEADER_ASSET_DATA);
        db.execSQL(CommonString.CREATE_TABLE_ASSET_DATA);
        db.execSQL(CommonString.CREATE_TABLE_FACING_COMPETITOR_DATA);
        db.execSQL(CommonString.CREATE_TABLE_FOOD_STORE_DATA);
        db.execSQL(CommonString.CREATE_TABLE_insert_HEADER_FOOD_STORE_DATA);
        db.execSQL(CommonString.CREATE_TABLE_COVERAGE_DATA);
        db.execSQL(CommonString.CREATE_TABLE_STOCK_DATA);
        db.execSQL(CommonString.CREATE_TABLE_CALLS_DATA);
        db.execSQL(CommonString.CREATE_TABLE_COMPETITION_POI);
        db.execSQL(CommonString.CREATE_TABLE_POI);
        db.execSQL(CommonString.CREATE_TABLE_COMPETITION_PROMOTION);
        db.execSQL(CommonString.CREATE_TABLE_STOCK_IMAGE);
        db.execSQL(CommonString.CREATE_TABLE_ASSET_CHECKLIST_INSERT);
        db.execSQL(CommonString.CREATE_TABLE_ASSET_SKU_CHECKLIST_INSERT);
        db.execSQL(CommonString.CREATE_ASSET_CHECKLIST_DATA);
        db.execSQL(CommonString.CREATE_TABLE_AUDIT_DATA_SAVE);
        db.execSQL(CommonString.CREATE_TABLE_PJP_DEVIATION);
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
                    sb.setUserId((dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_MERCHANDISER_ID))));
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
            dbcursor = db.rawQuery("SELECT * from JOURNEY_DEVIATION " + "where VISIT_DATE = '" + date + "'", null);
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
                            .getColumnIndexOrThrow(CommonString.KEY_MERCHANDISER_ID))));
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
            e.printStackTrace();
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
            e.printStackTrace();
            return entry_allow;
        }
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

    //region Description_insertJCPData
    public void insertJCPData(JourneyPlanGetterSetter data) {

        db.delete("JOURNEY_PLAN", null, null);
        ContentValues values = new ContentValues();
        try {
            for (int i = 0; i < data.getStore_cd().size(); i++) {

                values.put("STORE_CD", Integer.parseInt(data.getStore_cd().get(i)));
                values.put("EMP_CD", Integer.parseInt(data.getEmp_cd().get(i)));
                values.put("VISIT_DATE", data.getVISIT_DATE().get(i));
                values.put("KEYACCOUNT", data.getKey_account().get(i));
                values.put("STORENAME", data.getStore_name().get(i));
                values.put("CITY", data.getCity().get(i));
                values.put("STORETYPE", data.getStore_type().get(i));
                values.put("UPLOAD_STATUS", data.getUploadStatus().get(i));
                values.put("CHECKOUT_STATUS", data.getCheckOutStatus().get(i));
                db.insert("JOURNEY_PLAN", null, values);

            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
    //endregion

    //region Description_insertSkuMasterData
    public void insertSkuMasterData(SkuMasterGetterSetter data) {

        db.delete("SKU_MASTER", null, null);
        ContentValues values = new ContentValues();

        try {
            for (int i = 0; i < data.getSku_cd().size(); i++) {

                values.put("SKU_CD", Integer.parseInt(data.getSku_cd().get(i)));
                values.put("SKU", data.getSku().get(i));
                values.put("BRAND_CD", Integer.parseInt(data.getBrand_cd().get(i)));
                values.put("BRAND", data.getBrand().get(i));
                values.put("CATEGORY_CD", Integer.parseInt(data.getCategory_cd().get(i)));
                values.put("CATEGORY", data.getCategory().get(i));
                values.put("SKU_SEQUENCE", data.getSku_sequence().get(i));
                values.put("BRAND_SEQUENCE", data.getBrand_sequence().get(i));
                values.put("CATEGORY_SEQUENCE", data.getCategory_sequence().get(i));
                values.put("HIMALAYA_PHOTO", data.getHIMALAYA_PHOTO().get(i));
                values.put("CATEGORY_PHOTO", data.getCATEGORY_PHOTO().get(i));

                db.insert("SKU_MASTER", null, values);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
    //endregion

    //region Description_insertMappingAvailData
    public void insertMappingAvailData(MappingAvailabilityGetterSetter data) {

        db.delete("MAPPING_AVAILABILITY", null, null);
        ContentValues values = new ContentValues();
        try {
            for (int i = 0; i < data.getStore_cd().size(); i++) {

                values.put("STORE_CD", Integer.parseInt(data.getStore_cd().get(i)));
                values.put("SKU_CD", Integer.parseInt(data.getSku_cd().get(i)));
                db.insert("MAPPING_AVAILABILITY", null, values);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
    //endregion

    //region Description_insertMappingPromotionData
    public void insertMappingPromotionData(MappingPromotionGetterSetter data) {

        db.delete("MAPPING_PROMOTION", null, null);
        ContentValues values = new ContentValues();
        try {

            for (int i = 0; i < data.getStore_cd().size(); i++) {

                values.put("STORE_CD", Integer.parseInt(data.getStore_cd().get(i)));
                values.put("PROMOTION", data.getPromotion().get(i));
                values.put("PID", data.getPid().get(i));
                values.put("BRAND_CD", data.getBrand_cd().get(i));

                db.insert("MAPPING_PROMOTION", null, values);

            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
    //endregion

    //region Description_deletePromotionMapping
    public void deletePromotionMapping() {
        db.delete("MAPPING_PROMOTION", null, null);
    }
    //endregion

    //region Description_insertMappingAssetData
    public void insertMappingAssetData(MappingAssetGetterSetter data) {

        db.delete("MAPPING_ASSET", null, null);
        ContentValues values = new ContentValues();
        try {
            for (int i = 0; i < data.getStore_cd().size(); i++) {

                values.put("STORE_CD", Integer.parseInt(data.getStore_cd().get(i)));
                values.put("CATEGORY_CD", Integer.parseInt(data.getCategory_cd().get(i)));
                values.put("ASSET_CD", data.getAsset_cd().get(i));
                values.put("IMAGE_URL ", data.getIMAGE_URL().get(i));

                db.insert("MAPPING_ASSET", null, values);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
    //endregion

    //region Description_deleteAssetMapping
    public void deleteAssetMapping() {
        db.delete("MAPPING_ASSET", null, null);
    }
    //endregion

    //region Description_insertAssetMasterData
    public void insertAssetMasterData(AssetMasterGetterSetter data) {

        db.delete("ASSET_MASTER", null, null);
        ContentValues values = new ContentValues();

        try {

            for (int i = 0; i < data.getAsset_cd().size(); i++) {

                values.put("ASSET_CD", Integer.parseInt(data.getAsset_cd().get(i)));
                values.put("ASSET", data.getAsset().get(i));

                db.insert("ASSET_MASTER", null, values);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
    //endregion

    //region Description_insertCompanyMasterData
    public void insertCompanyMasterData(CompanyGetterSetter data) {

        db.delete("COMPANY_MASTER", null, null);
        ContentValues values = new ContentValues();
        try {
            for (int i = 0; i < data.getCompany_cd().size(); i++) {

                values.put("COMPANY_CD", Integer.parseInt(data.getCompany_cd().get(i)));
                values.put("COMPANY", data.getCompany().get(i));

                db.insert("COMPANY_MASTER", null, values);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
    //endregion

    //region Description_insertBrandMasterData
    public void insertBrandMasterData(BrandGetterSetter data) {

        db.delete("BRAND_MASTER", null, null);
        ContentValues values = new ContentValues();
        try {

            for (int i = 0; i < data.getBrand_cd().size(); i++) {

                values.put("BRAND_CD", Integer.parseInt(data.getBrand_cd().get(i)));
                values.put("BRAND", data.getBrand().get(i));
                values.put("BRAND_SEQUENCE", data.getBrand_sequence().get(i));
                values.put("COMPANY_CD", Integer.parseInt(data.getCompany_cd().get(i)));
                values.put("CATEGORY_CD", Integer.parseInt(data.getCategory_cd().get(i)));

                db.insert("BRAND_MASTER", null, values);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
    //endregion

    //region Description_insertAssetChecklistData
    public void insertAssetChecklistData(AssetChecklistGetterSetter data) {

        db.delete("ASSET_CHECKLIST", null, null);
        ContentValues values = new ContentValues();

        try {

            for (int i = 0; i < data.getCHECKLIST_ID().size(); i++) {

                values.put("CHECKLIST_ID", Integer.parseInt(data.getCHECKLIST_ID().get(i)));
                values.put("CHECKLIST", data.getCHECKLIST().get(i));
                values.put("CHECKLIST_TYPE", data.getTYPE().get(i));

                db.insert("ASSET_CHECKLIST", null, values);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
    //endregion

    //region Description_insertMappingAssetChecklistData
    public void insertMappingAssetChecklistData(MappingAssetChecklistGetterSetter data) {

        db.delete("MAPPING_ASSET_CHECKLIST", null, null);
        ContentValues values = new ContentValues();

        try {

            for (int i = 0; i < data.getAsset_cd().size(); i++) {

                values.put("ASSET_CD", Integer.parseInt(data.getAsset_cd().get(i)));
                values.put("CHECKLIST_ID", data.getCheck_list_id().get(i));

                db.insert("MAPPING_ASSET_CHECKLIST", null, values);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    //endregion

    //region Description_deleteMappingAssetChecklist
    public void deleteMappingAssetChecklist() {
        db.delete("MAPPING_ASSET_CHECKLIST", null, null);
    }
    //endregion

    //region Description_insertCategoryMasterData
    public void insertCategoryMasterData(CategoryMasterGetterSetter data) {

        db.delete("CATEGORY_MASTER", null, null);
        ContentValues values = new ContentValues();

        try {

            for (int i = 0; i < data.getCategory_cd().size(); i++) {

                values.put("CATEGORY_CD", Integer.parseInt(data.getCategory_cd().get(i)));
                values.put("CATEGORY", data.getCategory().get(i));
                values.put("HIMALAYA_PHOTO", data.getHIMALAYA_PHOTO().get(i));
                values.put("CATEGORY_PHOTO", data.getCATEGORY_PHOTO().get(i));

                db.insert("CATEGORY_MASTER", null, values);

            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
    //endregion

    //region Description_insertNonWorkingReasonData
    public void insertNonWorkingReasonData(NonWorkingReasonGetterSetter data) {

        db.delete("NON_WORKING_REASON", null, null);
        ContentValues values = new ContentValues();
        try {

            for (int i = 0; i < data.getReason_cd().size(); i++) {

                values.put("REASON_CD", Integer.parseInt(data.getReason_cd().get(i)));
                values.put("REASON", data.getReason().get(i));
                values.put("ENTRY_ALLOW", data.getEntry_allow().get(i));

                db.insert("NON_WORKING_REASON", null, values);

            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    //endregion

    //region Description_insertPaySlipdata
    public void insertPaySlipdata(PayslipGetterSetter data) {
        db.delete("EMP_SALARY", null, null);
        ContentValues values = new ContentValues();

        try {
            values.put("SALARY_MONTH", data.getMONTH().get(0));
            values.put("SALARY_YEAR", data.getSALARY_YEAR().get(0));
            values.put("ECODE", data.getECODE().get(0));
            values.put("EMP_NAME", data.getEMP_NAME().get(0));
            values.put("PAYMENT_MODE", data.getPAYMENT_MODE().get(0));
            values.put("PRESENT_DAYS", data.getPRESENT_DAYS().get(0));
            values.put("INCENTIVE", data.getINCENTIVE().get(0));
            values.put("NATIONAL_H", data.getNATIONAL_H().get(0));
            values.put("TOTAL_EARNING", data.getTOTAL_EARNING().get(0));
            values.put("PF", data.getPF().get(0));
            values.put("ESI", data.getESI().get(0));
            values.put("PT", data.getPT().get(0));
            values.put("LWF", data.getLWF().get(0));
            values.put("MIS_DEDUCTION", data.getMIS_DEDUCTION().get(0));
            values.put("TDS", data.getTDS().get(0));
            values.put("TAKE_HOME", data.getTAKE_HOME().get(0));

            db.insert("EMP_SALARY", null, values);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    //endregion

    //region Description_insertAuditQuestionData
    public void insertAuditQuestionData(Audit_QuestionGetterSetter data) {
        db.delete("AUDIT_QUESTION_CATEGORYWISE", null, null);
        ContentValues values = new ContentValues();

        try {
            for (int i = 0; i < data.getQUESTION_ID().size(); i++) {
                values.put("QUESTION_ID", data.getQUESTION_ID().get(i));
                values.put("QUESTION", data.getQUESTION().get(i));
                values.put("ANSWER_ID", data.getANSWER_ID().get(i));
                values.put("ANSWER", data.getANSWER().get(i));
                values.put("QUESTION_TYPE", data.getQUESTION_TYPE().get(i));
                values.put("CATEGORY_ID", data.getCATEGORY_ID().get(i));
                values.put("CATEGORY", data.getCATEGORY().get(i));

                db.insert("AUDIT_QUESTION_CATEGORYWISE", null, values);
            }
        } catch (Exception ex) {
            Log.d("Database Exception", " while Insert Audit Question Data " + ex.toString());
        }
    }
    //endregion

    //region Description_insertNonComplianceChecklistData
    public void insertNonComplianceChecklistData(NonComplianceChecklistGetterSetter data) {

        db.delete("NON_COMPLIANCE_CHECKLIST", null, null);
        ContentValues values = new ContentValues();
        try {
            for (int i = 0; i < data.getCREASON_ID().size(); i++) {

                values.put("CREASON_ID", data.getCREASON_ID().get(i));
                values.put("CREASON", data.getCREASON().get(i));

                db.insert("NON_COMPLIANCE_CHECKLIST", null, values);

            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
    //endregion

    //region Description_insertMappingAssetChecklistReasonData
    public void insertMappingAssetChecklistReasonData(MappingAssetChecklistreasonGetterSetter data) {

        db.delete("MAPPING_ASSET_CHECKLIST_REASON", null, null);
        ContentValues values = new ContentValues();

        try {

            for (int i = 0; i < data.getCREASON_ID().size(); i++) {

                values.put("CREASON_ID", data.getCREASON_ID().get(i));
                values.put("CHECKLIST_ID", data.getCHECKLIST_ID().get(i));

                db.insert("MAPPING_ASSET_CHECKLIST_REASON", null, values);

            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
    //endregion

    //region Description_isCoverageDataFilled
    public boolean isCoverageDataFilled(String visit_date) {
        boolean filled = false;
        Cursor dbcursor = null;

        try {
            dbcursor = db.rawQuery("SELECT * FROM COVERAGE_DATA " +
                    "where " + CommonString.KEY_VISIT_DATE + "<>'" + visit_date + "'", null);

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
            e.printStackTrace();
            return filled;
        }
        return filled;
    }
    //endregion

    //region Description_insertPJPDeviationData
    public void insertPJPDeviationData(JourneyPlanGetterSetter data) {

        db.execSQL(data.getTable_journey_plan());

        db.delete("JOURNEY_DEVIATION", null, null);
        ContentValues values = new ContentValues();

        try {

            for (int i = 0; i < data.getStore_cd().size(); i++) {

                values.put("STORE_CD", Integer.parseInt(data.getStore_cd().get(i)));
                values.put("EMP_CD", Integer.parseInt(data.getEmp_cd().get(i)));

                values.put("VISIT_DATE", data.getVISIT_DATE().get(i));
                //values.put("VISIT_DATE", "04/05/2017");
                values.put("KEYACCOUNT", data.getKey_account().get(i));

                values.put("STORENAME", data.getStore_name().get(i));
                values.put("CITY", data.getCity().get(i));
                values.put("STORETYPE", data.getStore_type().get(i));
                //values.put("CATEGORY_TYPE", data.getCategory_type().get(i));

                values.put("UPLOAD_STATUS", data.getUploadStatus().get(i));
                values.put("CHECKOUT_STATUS", data.getCheckOutStatus().get(i));

						/*values.put("UPLOAD_STATUS", "N");
                        values.put("CHECKOUT_STATUS","N");
*/
                db.insert("JOURNEY_DEVIATION", null, values);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
    //endregion

    //region Description_getStockAvailabilityData
    public ArrayList<StockNewGetterSetter> getStockAvailabilityData(String store_cd) {
        Log.d("Fetching", "Storedata--------------->Start<------------");
        ArrayList<StockNewGetterSetter> list = new ArrayList<StockNewGetterSetter>();
        Cursor dbcursor = null;

        try {
            dbcursor = db.rawQuery("SELECT DISTINCT SD.CATEGORY_CD, SD.CATEGORY, SD.HIMALAYA_PHOTO, SD.CATEGORY_PHOTO " +
                    "FROM MAPPING_AVAILABILITY CD " +
                    "INNER JOIN SKU_MASTER SD " +
                    "ON CD.SKU_CD = SD.SKU_CD " +
                    "WHERE CD.STORE_CD ='" + store_cd + "' " +
                    "ORDER BY SD.BRAND_SEQUENCE ", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    StockNewGetterSetter sb = new StockNewGetterSetter();

                    sb.setCategory_cd(dbcursor.getString(dbcursor.getColumnIndexOrThrow("CATEGORY_CD")));
                    sb.setCategory(dbcursor.getString(dbcursor.getColumnIndexOrThrow("CATEGORY")));
                    sb.setHimalaya_camera(dbcursor.getString(dbcursor.getColumnIndexOrThrow("HIMALAYA_PHOTO")));
                    sb.setCategory_camera(dbcursor.getString(dbcursor.getColumnIndexOrThrow("CATEGORY_PHOTO")));

                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }
        } catch (Exception e) {
            Log.d("Exception ", "when fetching opening stock!!!!!!!!!!!" + e.toString());
            return list;
        }

        Log.d("Fetching ", "opening stock---------------------->Stop<-----------");
        return list;
    }
    //endregion

    //region Description_isOpeningDataFilled
    public boolean isOpeningDataFilled(String storeId) {
        boolean filled = false;
        Cursor dbcursor = null;

        try {
            dbcursor = db.rawQuery("SELECT OPENING_STOCK, OPENING_FACING " +
                    "FROM STOCK_DATA WHERE STORE_CD= '" + storeId + "'", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                int icount = dbcursor.getCount();
                dbcursor.close();
                if (icount > 0) {
                    filled = true;
                } else {
                    filled = false;
                }
            }

        } catch (Exception e) {
            Log.d("Exception ", "when fetching Records!!!!!!!!!!!!!!!!!!!!!" + e.toString());
            return filled;
        }

        return filled;
    }
    //endregion

    //region Description_getPromotionBrandData
    public ArrayList<StockNewGetterSetter> getPromotionBrandData(String store_cd) {
        Log.d("Fetching", "Storedata--------------->Start<------------");
        ArrayList<StockNewGetterSetter> list = new ArrayList<StockNewGetterSetter>();
        Cursor dbcursor = null;

        try {
            dbcursor = db.rawQuery("SELECT DISTINCT SD.BRAND_CD, SD.BRAND " +
                    "FROM MAPPING_PROMOTION CD " +
                    "INNER JOIN BRAND_MASTER SD " +
                    "ON CD.BRAND_CD = SD.BRAND_CD " +
                    "WHERE CD.STORE_CD ='" + store_cd + "' " +
                    "ORDER BY SD.BRAND_SEQUENCE", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    StockNewGetterSetter sb = new StockNewGetterSetter();

                    sb.setBrand_cd(dbcursor.getString(dbcursor.getColumnIndexOrThrow("BRAND_CD")));
                    sb.setBrand(dbcursor.getString(dbcursor.getColumnIndexOrThrow("BRAND")));

                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }
        } catch (Exception e) {
            Log.d("Exception", " when fetching opening stock!!!!!!!!!!!" + e.toString());
            return list;
        }
        Log.d("Fetching ", "opening stock---------------------->Stop<-----------");
        return list;
    }
    //endregion

    //region Description_isPromotionDataFilled
    public boolean isPromotionDataFilled(String storeId) {
        boolean filled = false;
        Cursor dbcursor = null;
        try {
            dbcursor = db.rawQuery("SELECT * FROM PROMOTION_DATA WHERE STORE_CD= '" + storeId + "'", null);
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
            e.printStackTrace();
            return filled;
        }

        return filled;
    }
    //endregion

    //region Description_isStoreAssetDataFilled
    public boolean isStoreAssetDataFilled(String storeId) {
        boolean filled = false;
        Cursor dbcursor = null;

        try {
            dbcursor = db.rawQuery("SELECT DISTINCT CATEGORY_CD, CATEGORY " +
                    "FROM CATEGORY_MASTER BD " +
                    "WHERE CATEGORY_CD IN( SELECT DISTINCT CATEGORY_CD FROM MAPPING_ASSET " +
                    "WHERE STORE_CD ='" + storeId + "' ) ", null);

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
            Log.d("Exception ", "when fetching Records!!!!!!!!!!!!!!!!!!!!!" + e.toString());
            return filled;
        }
        return filled;
    }
    //endregion

    //region Description_getAssetCategoryData
    public ArrayList<AssetInsertdataGetterSetter> getAssetCategoryData(String store_cd) {
        Log.d("Fetching", "Storedata--------------->Start<------------");
        ArrayList<AssetInsertdataGetterSetter> list = new ArrayList<AssetInsertdataGetterSetter>();
        Cursor dbcursor = null;

        try {
            dbcursor = db.rawQuery("SELECT DISTINCT CATEGORY_CD, CATEGORY " +
                    "FROM CATEGORY_MASTER BD " +
                    "WHERE CATEGORY_CD IN( SELECT DISTINCT CATEGORY_CD FROM MAPPING_ASSET " +
                    "WHERE STORE_CD ='" + store_cd + "' ) ", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    AssetInsertdataGetterSetter sb = new AssetInsertdataGetterSetter();

                    sb.setCategory_cd(dbcursor.getString(dbcursor.getColumnIndexOrThrow("CATEGORY_CD")));
                    sb.setCategory(dbcursor.getString(dbcursor.getColumnIndexOrThrow("CATEGORY")));

                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }
        } catch (Exception e) {
            Log.d("Exception ", "when fetching opening stock!!!!!!!!!!!" + e.toString());
            return list;
        }

        Log.d("Fetching ", "Asset brand---------------------->Stop<-----------");
        return list;
    }
    //endregion

    //region Description_isAssetDataFilled
    public boolean isAssetDataFilled(String storeId) {
        boolean filled = false;
        Cursor dbcursor = null;

        try {
            dbcursor = db.rawQuery("SELECT * FROM ASSET_DATA WHERE STORE_CD= '" + storeId + "'", null);

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
            Log.d("Exception", " when fetching Records!!!!!!!!!!!!!!!!!!!!!" + e.toString());
            return filled;
        }
        return filled;
    }
    //endregion

    //region Description_getCategoryQuestionData
    public ArrayList<Audit_QuestionGetterSetter> getCategoryQuestionData() {
        Log.d("Fetching", "Category Data--------------->Start<------------");
        ArrayList<Audit_QuestionGetterSetter> list = new ArrayList<>();
        Cursor dbcursor = null;

        try {
            dbcursor = db.rawQuery("Select DISTINCT CATEGORY_ID, CATEGORY " +
                    "From AUDIT_QUESTION_CATEGORYWISE ", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    Audit_QuestionGetterSetter sb = new Audit_QuestionGetterSetter();

                    sb.setCATEGORY(dbcursor.getString(dbcursor.getColumnIndexOrThrow("CATEGORY")));
                    sb.setCATEGORY_ID(dbcursor.getString(dbcursor.getColumnIndexOrThrow("CATEGORY_ID")));

                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }
        } catch (Exception e) {
            Log.d("Exception ", "when fetching Category Data!!!!!!!!!!!" + e.toString());
            return list;
        }

        Log.d("Fetching ", "Category Data---------------------->Stop<-----------");
        return list;
    }
    //endregion

    //region Description_getAuditQuestionData
    public ArrayList<Audit_QuestionDataGetterSetter> getAuditQuestionData(String category_id) {
        Log.d("Fetching", "AuditQuestion Data--------------->Start<------------");
        ArrayList<Audit_QuestionDataGetterSetter> list = new ArrayList<>();
        Cursor dbcursor = null;

        try {
            dbcursor = db.rawQuery("Select DISTINCT QUESTION_ID,QUESTION " +
                    "From AUDIT_QUESTION_CATEGORYWISE WHERE CATEGORY_ID='" + category_id + "'", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    Audit_QuestionDataGetterSetter sb = new Audit_QuestionDataGetterSetter();

                    sb.setQuestion_id(dbcursor.getString(dbcursor.getColumnIndexOrThrow("QUESTION_ID")));
                    sb.setQuestion(dbcursor.getString(dbcursor.getColumnIndexOrThrow("QUESTION")));
                    sb.setSp_answer_id("0");

                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }
        } catch (Exception e) {
            Log.d("Exception ", "when fetching opening stock!!!!!!!!!!!" + e.toString());
            return list;
        }

        Log.d("Fetching ", "opening stock---------------------->Stop<-----------");
        return list;
    }
    //endregion

    //region Description_isAuditDataFilled
    public boolean isAuditDataFilled(String storeId, String category_id) {
        boolean filled = false;
        Cursor dbcursor = null;

        try {
            dbcursor = db.rawQuery("SELECT * FROM " + CommonString.TABLE_AUDIT_DATA_SAVE + " WHERE STORE_CD= '" + storeId + "'AND CATEGORY_ID ='" + category_id + "'", null);

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
            Log.d("Exception", " when fetching Records!!!!!!!!!!!!!!!!!!!!!" + e.toString());
            return filled;
        }
        return filled;
    }
    //endregion

    //region Description_isMiddayDataFilled
    public boolean isMiddayDataFilled(String storeId) {
        boolean filled = false;
        Cursor dbcursor = null;

        try {
            dbcursor = db.rawQuery("SELECT MIDDAY_STOCK FROM STOCK_DATA " +
                    "WHERE STORE_CD= '" + storeId + "'", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {

                    if (dbcursor.getString(dbcursor.getColumnIndexOrThrow("MIDDAY_STOCK")).equals("")) {
                        filled = false;
                        break;
                    } else {
                        filled = true;
                    }

                    dbcursor.moveToNext();
                }
                dbcursor.close();
            }
        } catch (Exception e) {
            Log.d("Exception ", "when fetching Records!!!!!!!!!!!!!!!!!!!!!" + e.toString());
            return filled;
        }
        return filled;
    }
    //endregion

    //region Description_isClosingDataFilled
    public boolean isClosingDataFilled(String storeId) {
        boolean filled = false;

        Cursor dbcursor = null;

        try {
            dbcursor = db.rawQuery("SELECT CLOSING_STOCK FROM STOCK_DATA " +
                    "WHERE STORE_CD= '" + storeId + "'", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {

                    if (dbcursor.getString(dbcursor.getColumnIndexOrThrow("CLOSING_STOCK")).equals("")) {
                        filled = false;
                        break;
                    } else {
                        filled = true;
                    }

                    dbcursor.moveToNext();
                }
                dbcursor.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
            return filled;
        }
        return filled;
    }
    //endregion

    //region Description_updateCoverageStatusNew
    public void updateCoverageStatusNew(String store_id, String status) {
        try {
            ContentValues values = new ContentValues();
            values.put(CommonString.KEY_COVERAGE_STATUS, status);

            db.update(CommonString.TABLE_COVERAGE_DATA, values,
                    CommonString.KEY_STORE_ID + "=" + store_id, null);
        } catch (Exception e) {

        }
    }
    //endregion

    //region Description_updateDeviationStoreStatusOnCheckout
    public void updateDeviationStoreStatusOnCheckout(String storeid, String visitdate, String status) {
        try {
            ContentValues values = new ContentValues();
            values.put(CommonString.KEY_CHECKOUT_STATUS, status);

            db.update("JOURNEY_DEVIATION", values, CommonString.KEY_STORE_CD + "='" + storeid + "' AND "
                    + CommonString.KEY_VISIT_DATE + "='" + visitdate + "'", null);
        } catch (Exception e) {

        }
    }
    //endregion

    //region Description_updateStoreStatusOnCheckout
    public void updateStoreStatusOnCheckout(String storeid, String visitdate, String status) {
        try {
            ContentValues values = new ContentValues();
            values.put(CommonString.KEY_CHECKOUT_STATUS, status);

            db.update("JOURNEY_PLAN", values, CommonString.KEY_STORE_CD + "='" + storeid + "' AND "
                    + CommonString.KEY_VISIT_DATE + "='" + visitdate + "'", null);
        } catch (Exception e) {

        }
    }

    //endregion

    public void deleteSpecificStoreData(String storeid) {
        db.delete(CommonString.TABLE_PJP_DEVIATION, CommonString.KEY_STORE_CD + "='" + storeid + "'", null);
        db.delete(CommonString.TABLE_COVERAGE_DATA, CommonString.KEY_STORE_ID + "='" + storeid + "'", null);
        db.delete(CommonString.TABLE_INSERT_OPENINGHEADER_DATA, CommonString.KEY_STORE_CD + "='" + storeid + "'", null);
        db.delete(CommonString.TABLE_STOCK_DATA, CommonString.KEY_STORE_CD + "='" + storeid + "'", null);
        db.delete(CommonString.TABLE_INSERT_PROMOTION_HEADER_DATA, CommonString.KEY_STORE_CD + "='" + storeid + "'", null);
        db.delete(CommonString.TABLE_PROMOTION_DATA, CommonString.KEY_STORE_CD + "='" + storeid + "'", null);
        db.delete(CommonString.TABLE_INSERT_ASSET_HEADER_DATA, CommonString.KEY_STORE_CD + "='" + storeid + "'", null);
        db.delete(CommonString.TABLE_ASSET_DATA, CommonString.KEY_STORE_CD + "='" + storeid + "'", null);
        db.delete(CommonString.TABLE_ASSET_CHECKLIST_INSERT, CommonString.KEY_STORE_CD + "='" + storeid + "'", null);
        db.delete(CommonString.TABLE_ASSET_SKU_CHECKLIST_INSERT, CommonString.KEY_STORE_CD + "='" + storeid + "'", null);
        db.delete(CommonString.TABLE_AUDIT_DATA_SAVE, CommonString.KEY_STORE_CD + "='" + storeid + "'", null);

    }

    public long InsertCoverageData(CoverageBean data) {
        ContentValues values = new ContentValues();

        try {
            values.put(CommonString.KEY_STORE_ID, data.getStoreId());
            values.put(CommonString.KEY_USER_ID, data.getUserId());
            values.put(CommonString.KEY_IN_TIME, data.getInTime());
            values.put(CommonString.KEY_OUT_TIME, data.getOutTime());
            values.put(CommonString.KEY_VISIT_DATE, data.getVisitDate());
            values.put(CommonString.KEY_LATITUDE, data.getLatitude());
            values.put(CommonString.KEY_LONGITUDE, data.getLongitude());
            values.put(CommonString.KEY_REASON_ID, data.getReasonid());
            values.put(CommonString.KEY_REASON, data.getReason());
            values.put(CommonString.KEY_COVERAGE_STATUS, data.getStatus());
            values.put(CommonString.KEY_IMAGE, data.getImage());
            values.put(CommonString.KEY_COVERAGE_REMARK, data.getRemark());
            values.put(CommonString.KEY_REASON_ID, data.getReasonid());
            values.put(CommonString.KEY_REASON, data.getReason());
            values.put(CommonString.KEY_PJP_DEVIATION, data.isPJPDeviation());
            //values.put(CommonString.KEY_other, data.getOtherreson());

            return db.insert(CommonString.TABLE_COVERAGE_DATA, null, values);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    public long InsertPJPDeviationData(String store_cd, String visit_date) {
        ContentValues values = new ContentValues();
        try {
            values.put(CommonString.KEY_STORE_CD, store_cd);
            values.put(CommonString.KEY_VISIT_DATE, visit_date);
            return db.insert(CommonString.TABLE_PJP_DEVIATION, null, values);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    public ArrayList<StockNewGetterSetter> getHeaderStockImageData(String store_cd, String visit_date) {
        Log.d("Fetching", "Storedata--------------->Start<------------");
        ArrayList<StockNewGetterSetter> list = new ArrayList<StockNewGetterSetter>();
        Cursor dbcursor = null;

        try {
            dbcursor = db.rawQuery("SELECT DISTINCT CATEGORY_CD, CATEGORY, IMAGE_STK, IMAGE_CAT_ONE, IMAGE_CAT_TWO," +
                    "HIMALAYA_PHOTO, CATEGORY_PHOTO " +
                    "FROM STOCK_IMAGE " +
                    "WHERE STORE_CD ='" + store_cd + "'AND VISIT_DATE  ='" + visit_date + "'", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    StockNewGetterSetter sb = new StockNewGetterSetter();

                    sb.setCategory_cd(dbcursor.getString(dbcursor.getColumnIndexOrThrow("CATEGORY_CD")));
                    sb.setCategory(dbcursor.getString(dbcursor.getColumnIndexOrThrow("CATEGORY")));
                    sb.setImg_cam(dbcursor.getString(dbcursor.getColumnIndexOrThrow("IMAGE_STK")));
                    sb.setImg_cat_one(dbcursor.getString(dbcursor.getColumnIndexOrThrow("IMAGE_CAT_ONE")));
                    sb.setImg_cat_two(dbcursor.getString(dbcursor.getColumnIndexOrThrow("IMAGE_CAT_TWO")));
                    sb.setHimalaya_camera(dbcursor.getString(dbcursor.getColumnIndexOrThrow("HIMALAYA_PHOTO")));
                    sb.setCategory_camera(dbcursor.getString(dbcursor.getColumnIndexOrThrow("CATEGORY_PHOTO")));

                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }
        } catch (Exception e) {
            Log.d("Exception", " when fetching Header!!!!!!!!!!! " + e.toString());
            return list;
        }
        Log.d("Fetching ", "Header stock---------------------->Stop<-----------");
        return list;
    }

    public ArrayList<StockNewGetterSetter> getOpeningStockDataFromDatabase(String store_cd, String categord_cd) {
        Log.d("FetchingOpening", " Stock data--------------->Start<------------");
        ArrayList<StockNewGetterSetter> list = new ArrayList<StockNewGetterSetter>();
        Cursor dbcursor = null;

        try {
            dbcursor = db.rawQuery("SELECT SD.SKU_CD, SD.SKU, SD.BRAND_CD, SD.BRAND, SD.OPENING_STOCK, SD.OPENING_FACING ,SD.STOCK_UNDER_DAYS,SD.COMPANY_CD " +
                    "FROM openingHeader_data CD " +
                    "INNER JOIN STOCK_DATA SD " +
                    "ON CD.KEY_ID=SD.Common_Id " +
                    "WHERE CD.STORE_CD= '" + store_cd + "' AND CD.CATEGORY_CD = '" + categord_cd + "'", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    StockNewGetterSetter sb = new StockNewGetterSetter();

                    sb.setSku_cd(dbcursor.getString(dbcursor.getColumnIndexOrThrow("SKU_CD")));
                    sb.setSku(dbcursor.getString(dbcursor.getColumnIndexOrThrow("SKU")));
                    sb.setBrand_cd(dbcursor.getString(dbcursor.getColumnIndexOrThrow("BRAND_CD")));
                    sb.setBrand(dbcursor.getString(dbcursor.getColumnIndexOrThrow("BRAND")));
                    sb.setEd_openingStock(dbcursor.getString(dbcursor.getColumnIndexOrThrow("OPENING_STOCK")));
                    sb.setEd_openingFacing(dbcursor.getString(dbcursor.getColumnIndexOrThrow("OPENING_FACING")));
                    sb.setStock_under45days(dbcursor.getString(dbcursor.getColumnIndexOrThrow("STOCK_UNDER_DAYS")));
                    sb.setCompany_cd(dbcursor.getString(dbcursor.getColumnIndexOrThrow("COMPANY_CD")));

                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }
        } catch (Exception e) {
            Log.d("Exception", " when fetching Records!!!!!!!!!!!!!!!!!!!!!" + e.toString());
            return list;
        }
        Log.d("FetchingOPening", " midday---------------------->Stop<-----------");

        return list;
    }

    public ArrayList<StockNewGetterSetter> getStockSkuData(String store_cd, String categord_cd) {
        Log.d("Fetching", "Storedata--------------->Start<------------");
        ArrayList<StockNewGetterSetter> list = new ArrayList<StockNewGetterSetter>();
        Cursor dbcursor = null;

        try {
            dbcursor = db.rawQuery("SELECT DISTINCT SD.SKU_CD, SD.SKU,SD.BRAND_CD,SD.BRAND ,BM.COMPANY_CD " +
                    "FROM MAPPING_AVAILABILITY CD " +
                    "INNER JOIN SKU_MASTER SD " +
                    "ON CD.SKU_CD = SD.SKU_CD " +
                    "INNER JOIN BRAND_MASTER BM " +
                    "ON BM.BRAND_CD = SD.BRAND_CD " +
                    "WHERE CD.STORE_CD= '" + store_cd + "' AND SD.CATEGORY_CD ='" + categord_cd + "' " +
                    "ORDER BY SD.SKU_SEQUENCE ", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    StockNewGetterSetter sb = new StockNewGetterSetter();

                    sb.setSku_cd(dbcursor.getString(dbcursor.getColumnIndexOrThrow("SKU_CD")));
                    sb.setSku(dbcursor.getString(dbcursor.getColumnIndexOrThrow("SKU")));
                    sb.setBrand_cd(dbcursor.getString(dbcursor.getColumnIndexOrThrow("BRAND_CD")));
                    sb.setBrand(dbcursor.getString(dbcursor.getColumnIndexOrThrow("BRAND")));
                    sb.setCompany_cd(dbcursor.getString(dbcursor.getColumnIndexOrThrow("COMPANY_CD")));
                    sb.setEd_openingStock("");
                    sb.setEd_openingFacing("");
                    sb.setStock_under45days("");

                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }
        } catch (Exception e) {
            Log.d("Exception", " when fetching opening stock!!!!!!!!!!! " + e.toString());
            return list;
        }

        return list;
    }


    public boolean checkStock(String storeId) {
        ArrayList<StockGetterSetter> list = new ArrayList<>();
        Cursor dbcursor = null;

        try {
            dbcursor = db.rawQuery("SELECT DISTINCT SD.SKU_CD " +
                    "FROM openingHeader_data CD " +
                    "INNER JOIN STOCK_DATA SD " +
                    "ON CD.KEY_ID=SD.Common_Id " +
                    "WHERE CD.STORE_CD= '" + storeId + "'", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    StockGetterSetter sb = new StockGetterSetter();

                    sb.setSku_cd(dbcursor.getString(dbcursor.getColumnIndexOrThrow("SKU_CD")));

                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();

                if (list.size() > 0) {
                    return true;
                } else {
                    return false;
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    public void UpdateHeaderOpeningStocklistData(
            String storeid, String visit_date,
            List<StockNewGetterSetter> save_listDataHeader) {

        ContentValues values = new ContentValues();
        try {
            db.beginTransaction();

            for (int i = 0; i < save_listDataHeader.size(); i++) {
                values.put("IMAGE_STK", save_listDataHeader.get(i).getImg_cam());
                values.put("IMAGE_CAT_ONE", save_listDataHeader.get(i).getImg_cat_one());
                values.put("IMAGE_CAT_TWO", save_listDataHeader.get(i).getImg_cat_two());

                db.update(CommonString.TABLE_STOCK_IMAGE, values, "STORE_CD" + "='" + storeid +
                        "' AND CATEGORY_CD " + "='" + Integer.parseInt(save_listDataHeader.get(i).getCategory_cd()) +
                        "' AND VISIT_DATE  ='" + visit_date + "'", null);
            }
            db.setTransactionSuccessful();
            db.endTransaction();
        } catch (Exception ex) {
            Log.d("Database Exception", " while Insert Posm Master Data " + ex.toString());
        }
    }


    public void UpdateOpeningStocklistData(
            String storeid, HashMap<StockNewGetterSetter, List<StockNewGetterSetter>> data,
            List<StockNewGetterSetter> save_listDataHeader) {

        ContentValues values1 = new ContentValues();
        try {
            ArrayList<HeaderGetterSetter> list;
            list = getHeaderStock(storeid);

            db.beginTransaction();
            for (int i = 0; i < list.size(); i++) {

                for (int j = 0; j < data.get(save_listDataHeader.get(i)).size(); j++) {
                    values1.put("OPENING_STOCK", data.get(save_listDataHeader.get(i)).get(j).getEd_openingStock());
                    values1.put("OPENING_FACING", data.get(save_listDataHeader.get(i)).get(j).getEd_openingFacing());
                    values1.put("STOCK_UNDER_DAYS", data.get(save_listDataHeader.get(i)).get(j).getStock_under45days());

                    db.update(CommonString.TABLE_STOCK_DATA, values1,
                            "Common_Id" + "='" + Integer.parseInt(list.get(i).getKeyId()) + "' AND SKU_CD " +
                                    "='" + Integer.parseInt(data.get(save_listDataHeader.get(i)).get(j).getSku_cd()) + "'", null);
                }
            }
            db.setTransactionSuccessful();
            db.endTransaction();
        } catch (Exception ex) {
            Log.d("Database Exception", " while Insert Posm Master Data " + ex.toString());
        }
    }


    public ArrayList<HeaderGetterSetter> getHeaderStock(String storeId) {
        ArrayList<HeaderGetterSetter> list = new ArrayList<HeaderGetterSetter>();
        Cursor dbcursor = null;

        try {

            dbcursor = db
                    .rawQuery(
                            "SELECT KEY_ID FROM openingHeader_data WHERE STORE_CD= '"
                                    + storeId + "'", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    HeaderGetterSetter sb = new HeaderGetterSetter();

                    sb.setKeyId(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("KEY_ID")));

                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return list;
        }
        return list;
    }

    public void InsertHeaderOpeningStocklistData(
            String storeid, String visit_date, List<StockNewGetterSetter> save_listDataHeader) {

        ContentValues values = new ContentValues();
        try {
            db.beginTransaction();

            for (int i = 0; i < save_listDataHeader.size(); i++) {
                values.put("STORE_CD", storeid);
                values.put("VISIT_DATE", visit_date);
                values.put("CATEGORY_CD", save_listDataHeader.get(i).getCategory_cd());
                values.put("CATEGORY", save_listDataHeader.get(i).getCategory());
                values.put("IMAGE_STK", save_listDataHeader.get(i).getImg_cam());
                values.put("IMAGE_CAT_ONE", save_listDataHeader.get(i).getImg_cat_one());
                values.put("IMAGE_CAT_TWO", save_listDataHeader.get(i).getImg_cat_two());
                values.put("HIMALAYA_PHOTO", save_listDataHeader.get(i).getHimalaya_camera());
                values.put("CATEGORY_PHOTO", save_listDataHeader.get(i).getCategory_camera());

                long l = db.insert(CommonString.TABLE_STOCK_IMAGE, null, values);
            }
            db.setTransactionSuccessful();
            db.endTransaction();
        } catch (Exception ex) {
            Log.d("Database Exception", " while Insert Header Data " + ex.toString());
        }
    }

    public void InsertOpeningStocklistData(String storeid,
                                           HashMap<StockNewGetterSetter, List<StockNewGetterSetter>> data,
                                           List<StockNewGetterSetter> save_listDataHeader) {
        ContentValues values = new ContentValues();
        ContentValues values1 = new ContentValues();

        try {
            db.beginTransaction();
            for (int i = 0; i < save_listDataHeader.size(); i++) {
                values.put("STORE_CD", storeid);
                values.put("CATEGORY_CD", save_listDataHeader.get(i).getCategory_cd());
                values.put("CATEGORY", save_listDataHeader.get(i).getCategory());

                long l = db.insert(CommonString.TABLE_INSERT_OPENINGHEADER_DATA, null, values);

                for (int j = 0; j < data.get(save_listDataHeader.get(i)).size(); j++) {
                    values1.put("Common_Id", (int) l);
                    values1.put("STORE_CD", storeid);
                    values1.put("CATEGORY_CD", save_listDataHeader.get(i).getCategory_cd());
                    values1.put("CATEGORY", save_listDataHeader.get(i).getCategory());
                    values1.put("BRAND_CD", save_listDataHeader.get(i).getBrand_cd());
                    values1.put("BRAND", save_listDataHeader.get(i).getBrand());
                    values1.put("SKU", data.get(save_listDataHeader.get(i)).get(j).getSku());
                    values1.put("SKU_CD", Integer.parseInt(data.get(save_listDataHeader.get(i)).get(j).getSku_cd()));
                    values1.put("OPENING_STOCK", data.get(save_listDataHeader.get(i)).get(j).getEd_openingStock());
                    values1.put("OPENING_FACING", data.get(save_listDataHeader.get(i)).get(j).getEd_openingFacing());
                    values1.put("STOCK_UNDER_DAYS", data.get(save_listDataHeader.get(i)).get(j).getStock_under45days());

                    values1.put("COMPANY_CD", data.get(save_listDataHeader.get(i)).get(j).getCompany_cd());

                    db.insert(CommonString.TABLE_STOCK_DATA, null, values1);
                }
            }
            db.setTransactionSuccessful();
            db.endTransaction();
        } catch (Exception ex) {
            Log.d("Database Exception", " while Insert Posm Master Data " + ex.toString());
        }
    }

    public ArrayList<NonWorkingReasonGetterSetter> getNonWorkingData() {
        ArrayList<NonWorkingReasonGetterSetter> list = new ArrayList<NonWorkingReasonGetterSetter>();
        Cursor dbcursor = null;
        try {

            dbcursor = db.rawQuery("SELECT * FROM NON_WORKING_REASON", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    NonWorkingReasonGetterSetter sb = new NonWorkingReasonGetterSetter();
                    sb.setReason_cd(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("REASON_CD")));
                    sb.setReason(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("REASON")));
                    sb.setEntry_allow(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("ENTRY_ALLOW")));

                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return list;
        }

        return list;
    }

    public void deleteAllTables() {
        db.delete(CommonString.TABLE_COVERAGE_DATA, null, null);
        db.delete(CommonString.TABLE_INSERT_OPENINGHEADER_DATA, null, null);
        db.delete(CommonString.TABLE_STOCK_DATA, null, null);
        db.delete(CommonString.TABLE_INSERT_PROMOTION_HEADER_DATA, null, null);
        db.delete(CommonString.TABLE_PROMOTION_DATA, null, null);
        db.delete(CommonString.TABLE_INSERT_ASSET_HEADER_DATA, null, null);
        db.delete(CommonString.TABLE_ASSET_DATA, null, null);
        db.delete(CommonString.TABLE_ASSET_CHECKLIST_INSERT, null, null);
        db.delete(CommonString.TABLE_ASSET_SKU_CHECKLIST_INSERT, null, null);
        db.delete(CommonString.TABLE_AUDIT_DATA_SAVE, null, null);

    }

    public void updateStoreStatusOnLeave(String storeid, String visitdate, String status) {
        try {
            ContentValues values = new ContentValues();
            values.put("UPLOAD_STATUS", status);
            db.update("JOURNEY_PLAN", values, CommonString.KEY_STORE_CD + "='" + storeid + "' AND "
                    + CommonString.KEY_VISIT_DATE + "='" + visitdate + "'", null);
        } catch (Exception e) {
        }
    }

    public ArrayList<StockNewGetterSetter> getMiddayStockDataFromDatabase(String store_cd, String categord_cd) {
        Log.d("Fetching", "Mid Stock data--------------->Start<------------");
        ArrayList<StockNewGetterSetter> list = new ArrayList<StockNewGetterSetter>();
        Cursor dbcursor = null;
        try {
            dbcursor = db.rawQuery("SELECT DISTINCT SD.SKU_CD, SD.SKU,SD.BRAND_CD,SD.BRAND, " +
                    "S.OPENING_STOCK,S.OPENING_FACING,S.MIDDAY_STOCK,S.STOCK_UNDER_DAYS " +
                    "FROM MAPPING_AVAILABILITY CD " +
                    "INNER JOIN SKU_MASTER SD " +
                    "ON CD.SKU_CD = SD.SKU_CD " +
                    "INNER JOIN STOCK_DATA S " +
                    "on S.SKU_CD=SD.SKU_CD " +
                    "WHERE CD.STORE_CD= '" + store_cd + "' AND SD.CATEGORY_CD ='" + categord_cd + "' " +
                    "ORDER BY SD.SKU_SEQUENCE", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    StockNewGetterSetter sb = new StockNewGetterSetter();

                    sb.setSku_cd(dbcursor.getString(dbcursor.getColumnIndexOrThrow("SKU_CD")));
                    sb.setSku(dbcursor.getString(dbcursor.getColumnIndexOrThrow("SKU")));
                    sb.setBrand_cd(dbcursor.getString(dbcursor.getColumnIndexOrThrow("BRAND_CD")));
                    sb.setBrand(dbcursor.getString(dbcursor.getColumnIndexOrThrow("BRAND")));
                    sb.setStock_under45days(dbcursor.getString(dbcursor.getColumnIndexOrThrow("STOCK_UNDER_DAYS")));
                    sb.setEd_openingStock(dbcursor.getString(dbcursor.getColumnIndexOrThrow("OPENING_STOCK")));
                    sb.setEd_openingFacing(dbcursor.getString(dbcursor.getColumnIndexOrThrow("OPENING_FACING")));
                    sb.setEd_midFacing(dbcursor.getString(dbcursor.getColumnIndexOrThrow("MIDDAY_STOCK")));

                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }
        } catch (Exception e) {
            Log.d("Exception ", "when fetching Records Mid Stock!!!!!!!!!!!!!!!!!!!!!" + e.toString());
            return list;
        }
        Log.d("Fetching", "Mid Stock---------------------->Stop<-----------");
        return list;
    }

    public ArrayList<StockNewGetterSetter> getStockSkuMiddayData(String categord_cd, String store_cd) {
        Log.d("Fetching", "Storedata--------------->Start<------------");
        ArrayList<StockNewGetterSetter> list = new ArrayList<StockNewGetterSetter>();
        Cursor dbcursor = null;
        try {
            dbcursor = db.rawQuery("SELECT DISTINCT SD.SKU_CD, SD.SKU,SD.BRAND_CD,SD.BRAND, S.OPENING_STOCK,S.OPENING_FACING " +
                    "FROM MAPPING_AVAILABILITY CD " +
                    "INNER JOIN SKU_MASTER SD " +
                    "ON CD.SKU_CD = SD.SKU_CD " +
                    "INNER JOIN STOCK_DATA S " +
                    "on S.SKU_CD=SD.SKU_CD " +
                    "WHERE CD.STORE_CD= '" + store_cd + "' AND SD.CATEGORY_CD ='" + categord_cd + "' " +
                    " AND S.COMPANY_CD = '1' ORDER BY SD.SKU_SEQUENCE", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    StockNewGetterSetter sb = new StockNewGetterSetter();

                    sb.setSku_cd(dbcursor.getString(dbcursor.getColumnIndexOrThrow("SKU_CD")));
                    sb.setSku(dbcursor.getString(dbcursor.getColumnIndexOrThrow("SKU")));
                    sb.setBrand_cd(dbcursor.getString(dbcursor.getColumnIndexOrThrow("BRAND_CD")));
                    sb.setBrand(dbcursor.getString(dbcursor.getColumnIndexOrThrow("BRAND")));
                    sb.setEd_openingStock(dbcursor.getString(dbcursor.getColumnIndexOrThrow("OPENING_STOCK")));
                    sb.setEd_openingFacing(dbcursor.getString(dbcursor.getColumnIndexOrThrow("OPENING_FACING")));
                    sb.setEd_midFacing("");

                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }
        } catch (Exception e) {
            Log.d("Exception ", "when fetching opening stock!!!!!!!!!!!" + e.toString());
            return list;
        }
        return list;
    }

    public void UpdateMiddayStocklistData(String storeid,
                                          HashMap<StockNewGetterSetter, List<StockNewGetterSetter>> data,
                                          List<StockNewGetterSetter> save_listDataHeader) {
        ContentValues values1 = new ContentValues();

        try {
            ArrayList<HeaderGetterSetter> list = new ArrayList<HeaderGetterSetter>();
            list = getHeaderStock(storeid);

            for (int i = 0; i < list.size(); i++) {
                for (int j = 0; j < data.get(save_listDataHeader.get(i)).size(); j++) {

                    values1.put("MIDDAY_STOCK", data.get(save_listDataHeader.get(i)).get(j).getEd_midFacing());

                    db.update(CommonString.TABLE_STOCK_DATA, values1,
                            "Common_Id" + "='" + Integer.parseInt(list.get(i).getKeyId()) + "' AND SKU_CD " + "='"
                                    + Integer.parseInt(data.get(save_listDataHeader.get(i)).get(j).getSku_cd()) + "'", null);
                }
            }
        } catch (Exception ex) {
            Log.d("Database ", "Exception while Insert Posm Master Data " + ex.toString());
        }
    }

    public ArrayList<StockNewGetterSetter> getClosingStockDataFromDatabase(String store_cd, String category_cd) {
        Log.d("Fetching", "Opening Stock data--------------->Start<------------");
        ArrayList<StockNewGetterSetter> list = new ArrayList<StockNewGetterSetter>();
        Cursor dbcursor = null;
        try {
            dbcursor = db.rawQuery("SELECT DISTINCT SD.SKU_CD, SD.SKU,SD.BRAND_CD,SD.BRAND, " +
                    "S.OPENING_STOCK,S.OPENING_FACING,S.MIDDAY_STOCK,S.CLOSING_STOCK,S.STOCK_UNDER_DAYS " +
                    "FROM MAPPING_AVAILABILITY CD " +
                    "INNER JOIN SKU_MASTER SD " +
                    "ON CD.SKU_CD = SD.SKU_CD " +
                    "INNER JOIN STOCK_DATA S " +
                    "on S.SKU_CD=SD.SKU_CD " +
                    "WHERE CD.STORE_CD= '" + store_cd + "' AND SD.CATEGORY_CD ='" + category_cd + "' " +
                    "ORDER BY SD.SKU_SEQUENCE", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    StockNewGetterSetter sb = new StockNewGetterSetter();

                    sb.setSku_cd(dbcursor.getString(dbcursor.getColumnIndexOrThrow("SKU_CD")));
                    sb.setSku(dbcursor.getString(dbcursor.getColumnIndexOrThrow("SKU")));
                    sb.setBrand_cd(dbcursor.getString(dbcursor.getColumnIndexOrThrow("BRAND_CD")));
                    sb.setBrand(dbcursor.getString(dbcursor.getColumnIndexOrThrow("BRAND")));
                    sb.setStock_under45days(dbcursor.getString(dbcursor.getColumnIndexOrThrow("STOCK_UNDER_DAYS")));
                    sb.setEd_openingStock(dbcursor.getString(dbcursor.getColumnIndexOrThrow("OPENING_STOCK")));
                    sb.setEd_openingFacing(dbcursor.getString(dbcursor.getColumnIndexOrThrow("OPENING_FACING")));
                    sb.setEd_midFacing(dbcursor.getString(dbcursor.getColumnIndexOrThrow("MIDDAY_STOCK")));
                    sb.setEd_closingFacing(dbcursor.getString(dbcursor.getColumnIndexOrThrow("CLOSING_STOCK")));

                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }
        } catch (Exception e) {
            Log.d("Exception", " when fetching Records!!!!!!!!!!!!!!!!!!!!!" + e.toString());
            return list;
        }

        Log.d("Fetching", "OPening midday---------------------->Stop<-----------");
        return list;
    }

    public ArrayList<StockNewGetterSetter> getStockSkuClosingData(String store_cd, String category_cd) {
        Log.d("Fetching", "Storedata--------------->Start<------------");
        ArrayList<StockNewGetterSetter> list = new ArrayList<StockNewGetterSetter>();
        Cursor dbcursor = null;

        try {
            dbcursor = db.rawQuery("SELECT DISTINCT SD.SKU_CD, SD.SKU,SD.BRAND_CD,SD.BRAND, " +
                    "S.OPENING_STOCK,S.OPENING_FACING,S.MIDDAY_STOCK,S.STOCK_UNDER_DAYS " +
                    "FROM MAPPING_AVAILABILITY CD " +
                    "INNER JOIN SKU_MASTER SD " +
                    "ON CD.SKU_CD = SD.SKU_CD " +
                    "INNER JOIN STOCK_DATA S " +
                    "on S.SKU_CD=SD.SKU_CD " +
                    "WHERE CD.STORE_CD= '" + store_cd + "' AND SD.CATEGORY_CD ='" + category_cd + "' " +
                    "AND S.COMPANY_CD = '1' ORDER BY SD.SKU_SEQUENCE", null);


            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    StockNewGetterSetter sb = new StockNewGetterSetter();

                    sb.setSku_cd(dbcursor.getString(dbcursor.getColumnIndexOrThrow("SKU_CD")));
                    sb.setSku(dbcursor.getString(dbcursor.getColumnIndexOrThrow("SKU")));
                    sb.setBrand_cd(dbcursor.getString(dbcursor.getColumnIndexOrThrow("BRAND_CD")));
                    sb.setBrand(dbcursor.getString(dbcursor.getColumnIndexOrThrow("BRAND")));
                    sb.setStock_under45days(dbcursor.getString(dbcursor.getColumnIndexOrThrow("STOCK_UNDER_DAYS")));
                    sb.setEd_openingStock(dbcursor.getString(dbcursor.getColumnIndexOrThrow("OPENING_STOCK")));
                    sb.setEd_openingFacing(dbcursor.getString(dbcursor.getColumnIndexOrThrow("OPENING_FACING")));
                    sb.setEd_midFacing(dbcursor.getString(dbcursor.getColumnIndexOrThrow("MIDDAY_STOCK")));
                    sb.setEd_closingFacing("");

                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }
        } catch (Exception e) {
            Log.d("Exception ", "when fetching opening stock!!!!!!!!!!!" + e.toString());
            return list;
        }

        Log.d("Fetching ", "opening stock---------------------->Stop<-----------");
        return list;
    }

    public void UpdateClosingStocklistData(String storeid,
                                           HashMap<StockNewGetterSetter, List<StockNewGetterSetter>> data,
                                           List<StockNewGetterSetter> save_listDataHeader) {

        ContentValues values1 = new ContentValues();

        try {
            ArrayList<HeaderGetterSetter> list = new ArrayList<>();
            list = getHeaderStock(storeid);

            db.beginTransaction();
            for (int i = 0; i < list.size(); i++) {
                for (int j = 0; j < data.get(save_listDataHeader.get(i)).size(); j++) {
                    values1.put("CLOSING_STOCK", data.get(save_listDataHeader.get(i)).get(j).getEd_closingFacing());

                    db.update(CommonString.TABLE_STOCK_DATA, values1,
                            "Common_Id" + "='" + Integer.parseInt(list.get(i).getKeyId())
                                    + "' AND SKU_CD " + "='" + Integer.parseInt(data.get(save_listDataHeader.get(i)).get(j).getSku_cd())
                                    + "'", null);
                }
            }
            db.setTransactionSuccessful();
            db.endTransaction();
        } catch (Exception ex) {
            Log.d("Database", " Exception while Insert Posm Master Data " + ex.toString());
        }
    }

    public ArrayList<PromotionInsertDataGetterSetter> getPromotionDataFromDatabase(String storeId, String brand_cd) {
        Log.d("Fetching", "Promotionuploaddata--------------->Start<------------");
        ArrayList<PromotionInsertDataGetterSetter> list = new ArrayList<PromotionInsertDataGetterSetter>();
        Cursor dbcursor = null;

        try {
            dbcursor = db.rawQuery("SELECT SD.PID, SD.IMAGE, SD.PROMOTION,SD.PRESENT,SD.REMARK, CD.BRAND_CD,CD.BRAND," +
                    "SD.CAMERA,SD.PROMO_STOCK,SD.PROMO_TALKER,SD.RUNNING_POS " +
                    "FROM openingHeader_Promotion_data CD " +
                    "INNER JOIN PROMOTION_DATA SD " +
                    "ON CD.KEY_ID=SD.Common_Id " +
                    "WHERE CD.STORE_CD= '" + storeId + "' AND CD.BRAND_CD = '" + brand_cd + "'", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    PromotionInsertDataGetterSetter sb = new PromotionInsertDataGetterSetter();

                    sb.setPid(dbcursor.getString(dbcursor.getColumnIndexOrThrow("PID")));
                    sb.setPromotion_txt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("PROMOTION")));
                    sb.setImg(dbcursor.getString(dbcursor.getColumnIndexOrThrow("IMAGE")));
                    sb.setPresent(dbcursor.getString(dbcursor.getColumnIndexOrThrow("PRESENT")));
                    sb.setRemark(dbcursor.getString(dbcursor.getColumnIndexOrThrow("REMARK")));
                    sb.setBrand_cd(dbcursor.getString(dbcursor.getColumnIndexOrThrow("BRAND_CD")));
                    sb.setBrand(dbcursor.getString(dbcursor.getColumnIndexOrThrow("BRAND")));

                    sb.setCamera(dbcursor.getString(dbcursor.getColumnIndexOrThrow("CAMERA")));
                    sb.setPromoStock(dbcursor.getString(dbcursor.getColumnIndexOrThrow("PROMO_STOCK")));
                    sb.setPromoTalker(dbcursor.getString(dbcursor.getColumnIndexOrThrow("PROMO_TALKER")));
                    sb.setRunningPOS(dbcursor.getString(dbcursor.getColumnIndexOrThrow("RUNNING_POS")));

                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }
        } catch (Exception e) {
            Log.d("Exception ", "when fetching Records!!!!!!!!!!!!!!!!!!!!!" + e.toString());
            return list;
        }

        Log.d("Fetching", "Storedat---------------------->Stop<-----------");
        return list;
    }

    public ArrayList<PromotionInsertDataGetterSetter> getPromotionSkuData(String brand_cd, String store_cd) {
        Log.d("Fetching", "Storedata--------------->Start<------------");
        ArrayList<PromotionInsertDataGetterSetter> list = new ArrayList<PromotionInsertDataGetterSetter>();
        Cursor dbcursor = null;

        try {
            dbcursor = db.rawQuery("SELECT DISTINCT CD.PROMOTION, CD.PID " +
                    "FROM MAPPING_PROMOTION CD " +
                    "INNER JOIN BRAND_MASTER SD " +
                    "ON CD.BRAND_CD = SD.BRAND_CD " +
                    "WHERE SD.BRAND_CD ='" + brand_cd + "' AND CD.STORE_CD ='" + store_cd + "'", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    PromotionInsertDataGetterSetter sb = new PromotionInsertDataGetterSetter();

                    sb.setPromotion_txt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("PROMOTION")));
                    sb.setPid(dbcursor.getString(dbcursor.getColumnIndexOrThrow("PID")));
                    sb.setRemark("");
                    sb.setPresent("NO");
                    sb.setImg("");

                    sb.setCamera("");
                    sb.setPromoStock("0");
                    sb.setPromoTalker("0");
                    sb.setRunningPOS("0");

                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }
        } catch (Exception e) {
            Log.d("Exception ", "when fetching opening stock!!!!!!!!!!!" + e.toString());
            return list;
        }
        Log.d("Fetching ", "opening stock---------------------->Stop<-----------");
        return list;
    }

    public void deletePromotionData(String storeid) {
        try {
            db.delete(CommonString.TABLE_PROMOTION_DATA,
                    CommonString.KEY_STORE_CD + "='" + storeid + "'", null);

            db.delete(CommonString.TABLE_INSERT_PROMOTION_HEADER_DATA,
                    CommonString.KEY_STORE_CD + "='" + storeid + "'", null);
        } catch (Exception e) {
            Log.d("Exception", " when fetching Records!!!!!!!!!!!!!!!!!!!!!" + e.toString());
        }
    }

    public void InsertPromotionData(String storeid,
                                    HashMap<StockNewGetterSetter, List<PromotionInsertDataGetterSetter>> data,
                                    List<StockNewGetterSetter> save_listDataHeader) {

        ContentValues values = new ContentValues();
        ContentValues values1 = new ContentValues();

        try {
            db.beginTransaction();

            for (int i = 0; i < save_listDataHeader.size(); i++) {
                values.put("STORE_CD", storeid);
                values.put("BRAND_CD", save_listDataHeader.get(i).getBrand_cd());
                values.put("BRAND", save_listDataHeader.get(i).getBrand());

                long l = db.insert(CommonString.TABLE_INSERT_PROMOTION_HEADER_DATA, null, values);

                for (int j = 0; j < data.get(save_listDataHeader.get(i)).size(); j++) {
                    values1.put("Common_Id", (int) l);
                    values1.put("STORE_CD", storeid);
                    values1.put("PID", Integer.parseInt(data.get(save_listDataHeader.get(i)).get(j).getPid()));
                    values1.put("IMAGE", data.get(save_listDataHeader.get(i)).get(j).getImg());
                    values1.put("PROMOTION", data.get(save_listDataHeader.get(i)).get(j).getPromotion_txt());
                    values1.put("REMARK", data.get(save_listDataHeader.get(i)).get(j).getRemark());
                    values1.put("PRESENT", data.get(save_listDataHeader.get(i)).get(j).getPresent());

                    values1.put("CAMERA", data.get(save_listDataHeader.get(i)).get(j).getCamera());
                    values1.put("PROMO_STOCK", data.get(save_listDataHeader.get(i)).get(j).getPromoStock());
                    values1.put("PROMO_TALKER", data.get(save_listDataHeader.get(i)).get(j).getPromoTalker());
                    values1.put("RUNNING_POS", data.get(save_listDataHeader.get(i)).get(j).getRunningPOS());

                    db.insert(CommonString.TABLE_PROMOTION_DATA, null, values1);
                }
            }
            db.setTransactionSuccessful();
            db.endTransaction();
        } catch (Exception ex) {
            Log.d("Database ", "Exception while Insert Posm Master Data " + ex.toString());
        }
    }

    public void saveAuditQuestionAnswerData(ArrayList<Audit_QuestionDataGetterSetter> questionAnswerList, String store_cd, String category_cd) {
        db.delete(CommonString.TABLE_AUDIT_DATA_SAVE, "STORE_CD" + "='" + store_cd + "' AND CATEGORY_ID ='" + category_cd + "'", null);

        ContentValues values = new ContentValues();
        try {

            for (int i = 0; i < questionAnswerList.size(); i++) {
                Audit_QuestionDataGetterSetter data = questionAnswerList.get(i);

                values.put("STORE_CD", store_cd);
                values.put("QUESTION_ID", data.getQuestion_id());
                values.put("QUESTION", data.getQuestion());
                values.put("ANSWER_ID", data.getSp_answer_id());
                values.put("CATEGORY_ID", category_cd);

                db.insert(CommonString.TABLE_AUDIT_DATA_SAVE, null, values);
            }
        } catch (Exception ex) {
            Log.d("Database ", "Exception while Insert Audit Data " + ex.toString());
        }
    }

    public ArrayList<Audit_QuestionDataGetterSetter> getAfterSaveAuditQuestionAnswerData(String store_cd, String category_id) {

        ArrayList<Audit_QuestionDataGetterSetter> list = new ArrayList<>();
        Cursor dbcursor = null;

        try {
            dbcursor = db.rawQuery("Select * " + "From " + CommonString.TABLE_AUDIT_DATA_SAVE
                    + " where STORE_CD='" + store_cd + "' AND CATEGORY_ID ='" + category_id + "'", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    Audit_QuestionDataGetterSetter sb = new Audit_QuestionDataGetterSetter();

                    sb.setQuestion_id(dbcursor.getString(dbcursor.getColumnIndexOrThrow("QUESTION_ID")));
                    sb.setQuestion(dbcursor.getString(dbcursor.getColumnIndexOrThrow("QUESTION")));
                    sb.setSp_answer_id(dbcursor.getString(dbcursor.getColumnIndexOrThrow("ANSWER_ID")));
                    sb.setCategory_id(dbcursor.getString(dbcursor.getColumnIndexOrThrow("CATEGORY_ID")));

                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return list;
        }

        return list;
    }

    public ArrayList<Audit_QuestionDataGetterSetter> getAuditAnswerData(String store_cd, String question_id) {
        Log.d("Fetching", "Storedata--------------->Start<------------");

        ArrayList<Audit_QuestionDataGetterSetter> list = new ArrayList<>();
        Audit_QuestionDataGetterSetter sb1 = new Audit_QuestionDataGetterSetter();
        sb1.setAnswer_id("0");
        sb1.setAnswer("Select");
        list.add(0, sb1);

        Cursor dbcursor = null;

        try {
            dbcursor = db.rawQuery("Select * from AUDIT_QUESTION_CATEGORYWISE " +
                    "where QUESTION_ID='" + question_id + "'", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    Audit_QuestionDataGetterSetter sb = new Audit_QuestionDataGetterSetter();

                    sb.setAnswer_id(dbcursor.getString(dbcursor.getColumnIndexOrThrow("ANSWER_ID")));
                    sb.setAnswer(dbcursor.getString(dbcursor.getColumnIndexOrThrow("ANSWER")));

                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }
        } catch (Exception e) {
            Log.d("Exception", " when fetching opening stock!!!!!!!!!!! " + e.toString());
            return list;
        }
        Log.d("Fetching", " opening stock---------------------->Stop<-----------");
        return list;
    }

    public ArrayList<MappingAssetChecklistGetterSetter> getMapingCheckListData() {
        ArrayList<MappingAssetChecklistGetterSetter> list = new ArrayList<>();
        Cursor dbcursor = null;
        try {
            dbcursor = db
                    .rawQuery(
                            "SELECT * FROM MAPPING_ASSET_CHECKLIST"
                            , null);
            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    MappingAssetChecklistGetterSetter sb = new MappingAssetChecklistGetterSetter();
                    sb.setCheck_list_id(dbcursor.getString(dbcursor.getColumnIndexOrThrow("CHECKLIST_ID")));
                    sb.setAsset_cd(dbcursor.getString(dbcursor.getColumnIndexOrThrow("ASSET_CD")));
                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return list;
        }
        return list;
    }

    public ArrayList<AssetInsertdataGetterSetter> getAssetDataFromdatabase(String storeId, String category_cd) {
        Log.d("Fetching", "Assetuploaddata--------------->Start<------------");
        ArrayList<AssetInsertdataGetterSetter> list = new ArrayList<AssetInsertdataGetterSetter>();
        Cursor dbcursor = null;

        try {
            dbcursor = db.rawQuery("SELECT SD._id,SD.ASSET_CD, SD.ASSET, SD.PRESENT, SD.REMARK, SD.IMAGE, SD.PLANOGRAM_IMG,CD.CATEGORY_CD, CD.CATEGORY " +
                    "FROM openingHeader_Asset_data CD " +
                    "INNER JOIN ASSET_DATA SD " +
                    "ON CD.KEY_ID=SD.Common_Id " +
                    "WHERE CD.STORE_CD= '" + storeId + "' AND CD.CATEGORY_CD = '" + category_cd + "'", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    AssetInsertdataGetterSetter sb = new AssetInsertdataGetterSetter();

                    sb.setKey_id(dbcursor.getString(dbcursor.getColumnIndexOrThrow("_id")));
                    sb.setAsset_cd(dbcursor.getString(dbcursor.getColumnIndexOrThrow("ASSET_CD")));
                    sb.setAsset(dbcursor.getString(dbcursor.getColumnIndexOrThrow("ASSET")));
                    sb.setPresent(dbcursor.getString(dbcursor.getColumnIndexOrThrow("PRESENT")));
                    sb.setRemark(dbcursor.getString(dbcursor.getColumnIndexOrThrow("REMARK")));
                    sb.setImg(dbcursor.getString(dbcursor.getColumnIndexOrThrow("IMAGE")));
                    sb.setPlanogram_img(dbcursor.getString(dbcursor.getColumnIndexOrThrow("PLANOGRAM_IMG")));
                    sb.setCategory_cd(dbcursor.getString(dbcursor.getColumnIndexOrThrow("CATEGORY_CD")));

                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }
        } catch (Exception e) {
            Log.d("Exception ", "when fetching Records!!!!!!!!!!!!!!!!!!!!!" + e.toString());
            return list;
        }

        Log.d("Fetching", "Storedat---------------------->Stop<-----------");
        return list;
    }


    public ArrayList<AssetInsertdataGetterSetter> getAssetData(String category_cd, String store_cd) {
        Log.d("Fetching", "Assetdata--------------->Start<------------");
        ArrayList<AssetInsertdataGetterSetter> list = new ArrayList<AssetInsertdataGetterSetter>();
        Cursor dbcursor = null;

        try {
            dbcursor = db.rawQuery("SELECT AM.ASSET_CD, AM.ASSET, M.CATEGORY_CD, M.IMAGE_URL " +
                    "FROM MAPPING_ASSET M " +
                    "INNER JOIN ASSET_MASTER AM " +
                    "ON M.ASSET_CD = AM.ASSET_CD " +
                    "WHERE M.STORE_CD ='" + store_cd + "' AND M.CATEGORY_CD ='" + category_cd + "'", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    AssetInsertdataGetterSetter sb = new AssetInsertdataGetterSetter();

                    sb.setCategory_cd(category_cd);
                    sb.setAsset(dbcursor.getString(dbcursor.getColumnIndexOrThrow("ASSET")));
                    sb.setAsset_cd(dbcursor.getString(dbcursor.getColumnIndexOrThrow("ASSET_CD")));
                    sb.setPlanogram_img(dbcursor.getString(dbcursor.getColumnIndexOrThrow("IMAGE_URL")));
                    sb.setPresent("NO");
                    sb.setRemark("");
                    sb.setImg("");

                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }
        } catch (Exception e) {
            Log.d("Exception ", "when fetching Asset!!!!!!!!!!!" + e.toString());
            return list;
        }

        Log.d("Fetching ", "asset data---------------------->Stop<-----------");
        return list;
    }

    public ArrayList<SkuGetterSetter> getPaidVisibilitySkuListData(String key_id) {
        Log.d("Fetching ", "checklist data--------------->Start<------------");
        ArrayList<SkuGetterSetter> list = new ArrayList<>();
        Cursor dbcursor = null;

        try {
            dbcursor = db.rawQuery("SELECT * FROM " + CommonString.TABLE_ASSET_SKU_CHECKLIST_INSERT +
                    " WHERE COMMONID='" + key_id + "'", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    SkuGetterSetter sb = new SkuGetterSetter();

                    sb.setSKU_ID(dbcursor.getString(dbcursor.getColumnIndexOrThrow("SKU_CD")));
                    sb.setSKU(dbcursor.getString(dbcursor.getColumnIndexOrThrow("SKU")));
                    sb.setBRAND_ID(dbcursor.getString(dbcursor.getColumnIndexOrThrow("BRAND_CD")));
                    sb.setBRAND(dbcursor.getString(dbcursor.getColumnIndexOrThrow("BRAND")));
                    sb.setQUANTITY(dbcursor.getString(dbcursor.getColumnIndexOrThrow("SKU_QUANTITY")));

                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }
        } catch (Exception e) {
            Log.d("Exception ", "when fetching checklist data!!!!!!!!!!!" + e.toString());
            return list;
        }

        Log.d("Fetching", " checklist data---------------------->Stop<-----------");
        return list;
    }

    public ArrayList<ChecklistInsertDataGetterSetter> getCheckListWithReasonData(String key_id) {
        Log.d("Fetching ", "checklist data--------------->Start<------------");
        ArrayList<ChecklistInsertDataGetterSetter> list = new ArrayList<ChecklistInsertDataGetterSetter>();
        Cursor dbcursor = null;

        try {
            dbcursor = db.rawQuery("SELECT * FROM ASSET_CHECKLIST_DATA " +
                    "WHERE COMMONID = '" + key_id + "'", null);


            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    ChecklistInsertDataGetterSetter sb = new ChecklistInsertDataGetterSetter();

                    sb.setChecklist(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.CHECK_LIST)));
                    sb.setChecklist_id(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.CHECK_LIST_ID)));
                    sb.setChecklist_type(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.CHECK_LIST_TYPE)));
                    sb.setChecklist_text(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.CHECK_LIST_TEXT)));
                    sb.setReason_cd(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.REASON_ID)));

                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }
        } catch (Exception e) {
            Log.d("Exception ", "when fetching checklist data!!!!!!!!!!!" + e.toString());
            return list;
        }

        Log.d("Fetching", " checklist data---------------------->Stop<-----------");
        return list;
    }

    public void deleteAssetData(String storeid, ArrayList<HeaderGetterSetter> header_list) {
        try {

            for (int i = 0; i < header_list.size(); i++) {
                db.delete(CommonString.TABLE_ASSET_CHECKLIST_DATA, CommonString.COMMONID + "='" + header_list.get(i).getKeyId() + "'", null);
                db.delete(CommonString.TABLE_ASSET_SKU_CHECKLIST_INSERT, CommonString.COMMONID + "='" + header_list.get(i).getKeyId() + "'", null);
            }

            db.delete(CommonString.TABLE_ASSET_DATA, CommonString.KEY_STORE_CD + "='" + storeid + "'", null);
            db.delete(CommonString.TABLE_INSERT_ASSET_HEADER_DATA, CommonString.KEY_STORE_CD + "='" + storeid + "'", null);


        } catch (Exception e) {
            Log.d("Exception ", "when fetching Records!!!!!!!!!!!!!!!!!!!!!" + e.toString());
        }
    }

    public ArrayList<HeaderGetterSetter> getAssetHeaderData(String storeId) {
        Log.d("Fetching", "Assetuploaddata--------------->Start<------------");
        ArrayList<HeaderGetterSetter> list = new ArrayList<>();
        Cursor dbcursor = null;

        try {
            dbcursor = db.rawQuery("SELECT KEY_ID FROM openingHeader_Asset_data " +
                    "WHERE STORE_CD= '" + storeId + "'", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    HeaderGetterSetter sb = new HeaderGetterSetter();

                    sb.setKeyId(dbcursor.getString(dbcursor.getColumnIndexOrThrow("KEY_ID")));

                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }
        } catch (Exception e) {
            Log.d("Exception ", "when fetching Records!!!!!!!!!!!!!!!!!!!!!" + e.toString());
            return list;
        }

        Log.d("Fetching", "Storedat---------------------->Stop<-----------");
        return list;
    }

    public void InsertAssetData(String storeid, HashMap<AssetInsertdataGetterSetter, List<AssetInsertdataGetterSetter>> data,
                                List<AssetInsertdataGetterSetter> save_listDataHeader, String visit_date) {
        ContentValues values = new ContentValues();
        ContentValues values1 = new ContentValues();
        ContentValues values4 = new ContentValues();

        try {
            db.beginTransaction();

            for (int i = 0; i < save_listDataHeader.size(); i++) {

                values.put("STORE_CD", storeid);
                values.put("CATEGORY_CD", save_listDataHeader.get(i).getCategory_cd());
                values.put("CATEGORY", save_listDataHeader.get(i).getCategory());

                long l = db.insert(CommonString.TABLE_INSERT_ASSET_HEADER_DATA, null, values);

                for (int j = 0; j < data.get(save_listDataHeader.get(i)).size(); j++) {
                    values1.put("Common_Id", (int) l);
                    values1.put("STORE_CD", storeid);
                    values1.put("ASSET_CD", Integer.parseInt(data.get(save_listDataHeader.get(i)).get(j).getAsset_cd()));
                    values1.put("ASSET", data.get(save_listDataHeader.get(i)).get(j).getAsset());
                    values1.put("REMARK", data.get(save_listDataHeader.get(i)).get(j).getRemark());
                    values1.put("PRESENT", data.get(save_listDataHeader.get(i)).get(j).getPresent());
                    values1.put("PLANOGRAM_IMG", data.get(save_listDataHeader.get(i)).get(j).getPlanogram_img());
                    values1.put("IMAGE", data.get(save_listDataHeader.get(i)).get(j).getImg());

                    long m = db.insert(CommonString.TABLE_ASSET_DATA, null, values1);

                    for (int k = 0; k < (data.get(save_listDataHeader.get(i)).get(j).getChecklist()).size(); k++) {
                        ChecklistInsertDataGetterSetter data1 = (data.get(save_listDataHeader.get(i)).get(j).getChecklist()).get(k);

                        values4.put("COMMONID", m);
                        values4.put("CHECK_LIST_ID", data1.getChecklist_id());
                        values4.put("CHECK_LIST", data1.getChecklist());
                        values4.put("CHECK_LIST_TEXT", data1.getChecklist_text());
                        values4.put("CHECK_LIST_TYPE", data1.getChecklist_type());
                        values4.put("REASON_ID", data1.getReason_cd());

                        db.insert(CommonString.TABLE_ASSET_CHECKLIST_DATA, null, values4);
                    }
                }
            }
            db.setTransactionSuccessful();
            db.endTransaction();
        } catch (Exception ex) {
            Log.d("Database Exception ", "while Insert Posm Master Data " + ex.toString());
        }
    }

    public ArrayList<ChecklistInsertDataGetterSetter> getCheckListData(String asset_cd) {
        ArrayList<ChecklistInsertDataGetterSetter> list = new ArrayList<>();
        Cursor dbcursor = null;

        try {
            dbcursor = db.rawQuery("SELECT DISTINCT AC.CHECKLIST, AC.CHECKLIST_ID,AC.CHECKLIST_TYPE " +
                    "FROM MAPPING_ASSET_CHECKLIST MA " +
                    "INNER JOIN ASSET_CHECKLIST AC " +
                    "ON MA.CHECKLIST_ID = AC.CHECKLIST_ID " +
                    "WHERE MA.ASSET_CD= '" + asset_cd + "'", null);


            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    ChecklistInsertDataGetterSetter sb = new ChecklistInsertDataGetterSetter();

                    sb.setChecklist(dbcursor.getString(dbcursor.getColumnIndexOrThrow("CHECKLIST")));
                    sb.setChecklist_id(dbcursor.getString(dbcursor.getColumnIndexOrThrow("CHECKLIST_ID")));
                    sb.setChecklist_type(dbcursor.getString(dbcursor.getColumnIndexOrThrow("CHECKLIST_TYPE")));
                    sb.setChecklist_text("NO");

                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return list;
        }
        return list;
    }

    public ArrayList<AssetChecklistReasonGettersetter> getAssetCheckListReasonData(String checklist_id) {

        ArrayList<AssetChecklistReasonGettersetter> list = new ArrayList<>();
        Cursor dbcursor = null;

        try {
            dbcursor = db.rawQuery("SELECT N.CREASON, N.CREASON_ID FROM MAPPING_ASSET_CHECKLIST_REASON M JOIN NON_COMPLIANCE_CHECKLIST N ON M.CREASON_ID = N.CREASON_ID WHERE CHECKLIST_ID = '" + checklist_id + "'"
                    , null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    AssetChecklistReasonGettersetter df = new AssetChecklistReasonGettersetter();

                    df.setReason(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("CREASON")));
                    df.setReason_id(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("CREASON_ID")));

                    list.add(df);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return list;
        }
        return list;

    }

    public ArrayList<BrandGetterSetter> getBrandDataForPaidvisibility(String store_cd, String category_cd) {
        ArrayList<BrandGetterSetter> list = new ArrayList<>();
        Cursor dbcursor = null;

        try {
            dbcursor = db.rawQuery("SELECT DISTINCT B.BRAND, B.BRAND_CD FROM MAPPING_AVAILABILITY M INNER JOIN SKU_MASTER S ON S.SKU_CD = M.SKU_CD INNER JOIN BRAND_MASTER B ON S.BRAND_CD = B.BRAND_CD WHERE M.STORE_CD = '" + store_cd + "' AND B.CATEGORY_CD = '" + category_cd + "' AND B.COMPANY_CD = 1", null);


            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    BrandGetterSetter sb = new BrandGetterSetter();

                    sb.setBrand(dbcursor.getString(dbcursor.getColumnIndexOrThrow("BRAND")));
                    sb.setBrand_cd(dbcursor.getString(dbcursor.getColumnIndexOrThrow("BRAND_CD")));

                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return list;
        }

        return list;
    }

    public ArrayList<SkuGetterSetter> getSkuDataForPaidvisibility(String store_cd, String brand_cd) {

        ArrayList<SkuGetterSetter> list = new ArrayList<>();
        Cursor dbcursor = null;

        try {
            dbcursor = db.rawQuery("SELECT DISTINCT S.SKU, S.SKU_CD FROM MAPPING_AVAILABILITY M INNER JOIN SKU_MASTER S ON S.SKU_CD = M.SKU_CD INNER JOIN BRAND_MASTER B ON S.BRAND_CD = B.BRAND_CD WHERE M.STORE_CD = '" + store_cd + "' AND B.BRAND_CD = '" + brand_cd + "'", null);


            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    SkuGetterSetter sb = new SkuGetterSetter();

                    sb.setSKU_ID(dbcursor.getString(dbcursor.getColumnIndexOrThrow("SKU_CD")));
                    sb.setSKU(dbcursor.getString(dbcursor.getColumnIndexOrThrow("SKU")));

                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return list;
        }
        return list;
    }

    public JourneyPlanGetterSetter getDeviationStoreStatus(String id) {
        JourneyPlanGetterSetter sb = new JourneyPlanGetterSetter();
        Cursor dbcursor = null;

        try {
            dbcursor = db.rawQuery("SELECT  * from  JOURNEY_DEVIATION"
                    + "  WHERE STORE_CD = '"
                    + id + "'", null);

            if (dbcursor != null) {
                int numrows = dbcursor.getCount();

                dbcursor.moveToFirst();
                for (int i = 0; i < numrows; i++) {

                    sb.setStore_cd(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_STORE_CD)));

                    sb.setCheckOutStatus((dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("CHECKOUT_STATUS"))));

                    sb.setUploadStatus(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("UPLOAD_STATUS")));
                    dbcursor.moveToNext();

                }
                dbcursor.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb;

    }

    public JourneyPlanGetterSetter getStoreStatus(String id) {

        JourneyPlanGetterSetter sb = new JourneyPlanGetterSetter();
        Cursor dbcursor = null;

        try {
            dbcursor = db.rawQuery("SELECT  * from  JOURNEY_PLAN"
                    + "  WHERE STORE_CD = '"
                    + id + "'", null);

            if (dbcursor != null) {
                int numrows = dbcursor.getCount();

                dbcursor.moveToFirst();
                for (int i = 0; i < numrows; i++) {

                    sb.setStore_cd(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_STORE_CD)));

                    sb.setCheckOutStatus((dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("CHECKOUT_STATUS"))));

                    sb.setUploadStatus(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("UPLOAD_STATUS")));

                    dbcursor.moveToNext();

                }

                dbcursor.close();

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb;

    }

    public ArrayList<JCPGetterSetter> getPJPDeviationStoreData(String  visit_date) {
        ArrayList<JCPGetterSetter> list = new ArrayList<>();
        Cursor dbcursor = null;

        try {
            dbcursor = db.rawQuery("SELECT * from PJP_DEVIATION WHERE VISIT_DATE= '" + visit_date + "'", null);
            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    JCPGetterSetter fc = new JCPGetterSetter();
                    fc.setStoreid(dbcursor.getString(dbcursor.getColumnIndexOrThrow("STORE_CD")));
                    fc.setVisitdate(dbcursor.getString(dbcursor.getColumnIndexOrThrow("VISIT_DATE")));
                    list.add(fc);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return list;
        }
        return list;

    }

    public void deletePJPDeviationStores() {

        db.delete(CommonString.TABLE_PJP_DEVIATION, null, null);
    }

    public void updateCoverageStatus(int mid, String status) {

        try {
            ContentValues values = new ContentValues();
            values.put(CommonString.KEY_COVERAGE_STATUS, status);

            db.update(CommonString.TABLE_COVERAGE_DATA, values,
                    CommonString.KEY_ID + "=" + mid, null);
        } catch (Exception e) {

        }
    }

    public ArrayList<StockNewGetterSetter> getOpeningStockUpload(String storeId) {
        ArrayList<StockNewGetterSetter> list = new ArrayList<>();
        Cursor dbcursor = null;

        try {
            dbcursor = db.rawQuery("SELECT * FROM " + CommonString.TABLE_STOCK_DATA +
                    " WHERE STORE_CD= '" + storeId + "'", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    StockNewGetterSetter sb = new StockNewGetterSetter();

                    sb.setStore_cd(dbcursor.getString(dbcursor.getColumnIndexOrThrow("STORE_CD")));
                    sb.setCategory_cd(dbcursor.getString(dbcursor.getColumnIndexOrThrow("CATEGORY_CD")));
                    sb.setSku_cd(dbcursor.getString(dbcursor.getColumnIndexOrThrow("SKU_CD")));
                    sb.setEd_openingStock(dbcursor.getString(dbcursor.getColumnIndexOrThrow("OPENING_STOCK")));
                    sb.setEd_openingFacing(dbcursor.getString(dbcursor.getColumnIndexOrThrow("OPENING_FACING")));

                    String midday_stock = dbcursor.getString(dbcursor.getColumnIndexOrThrow("MIDDAY_STOCK"));
                    if (midday_stock == null || midday_stock.equals("")) {
                        sb.setEd_midFacing("0");
                    } else {
                        sb.setEd_midFacing(midday_stock);
                    }

                    String closing_stock = dbcursor.getString(dbcursor.getColumnIndexOrThrow("CLOSING_STOCK"));
                    if (closing_stock == null || closing_stock.equals("")) {
                        sb.setEd_closingFacing("0");
                    } else {
                        sb.setEd_closingFacing(closing_stock);
                    }
                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return list;
        }
        return list;
    }

    public ArrayList<StockNewGetterSetter> getStockImageUploadData(String store_cd) {
        ArrayList<StockNewGetterSetter> list = new ArrayList<>();
        Cursor dbcursor = null;

        try {
            dbcursor = db.rawQuery("SELECT DISTINCT CATEGORY_CD, CATEGORY, IMAGE_STK, IMAGE_CAT_ONE, IMAGE_CAT_TWO," +
                    "HIMALAYA_PHOTO, CATEGORY_PHOTO  " +
                    "FROM STOCK_IMAGE " +
                    "WHERE STORE_CD ='" + store_cd + "'", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    StockNewGetterSetter sb = new StockNewGetterSetter();

                    sb.setCategory_cd(dbcursor.getString(dbcursor.getColumnIndexOrThrow("CATEGORY_CD")));
                    sb.setCategory(dbcursor.getString(dbcursor.getColumnIndexOrThrow("CATEGORY")));
                    sb.setImg_cam(dbcursor.getString(dbcursor.getColumnIndexOrThrow("IMAGE_STK")));
                    sb.setImg_cat_one(dbcursor.getString(dbcursor.getColumnIndexOrThrow("IMAGE_CAT_ONE")));
                    sb.setImg_cat_two(dbcursor.getString(dbcursor.getColumnIndexOrThrow("IMAGE_CAT_TWO")));
                    sb.setHimalaya_camera(dbcursor.getString(dbcursor.getColumnIndexOrThrow("HIMALAYA_PHOTO")));
                    sb.setCategory_camera(dbcursor.getString(dbcursor.getColumnIndexOrThrow("CATEGORY_PHOTO")));

                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return list;
        }
        return list;
    }


    public ArrayList<PromotionInsertDataGetterSetter> getPromotionUploadData(String storeId) {
        ArrayList<PromotionInsertDataGetterSetter> list = new ArrayList<>();
        Cursor dbcursor = null;

        try {
            dbcursor = db.rawQuery("SELECT SD.PID, SD.IMAGE, SD.PROMOTION,SD.PRESENT,SD.REMARK, CD.BRAND_CD,CD.BRAND," +
                    "SD.CAMERA,SD.PROMO_STOCK,SD.PROMO_TALKER,SD.RUNNING_POS " +
                    "FROM openingHeader_Promotion_data CD " +
                    "INNER JOIN PROMOTION_DATA SD " +
                    "ON CD.KEY_ID=SD.Common_Id " +
                    "WHERE CD.STORE_CD= '" + storeId + "'", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    PromotionInsertDataGetterSetter sb = new PromotionInsertDataGetterSetter();

                    sb.setPid(dbcursor.getString(dbcursor.getColumnIndexOrThrow("PID")));
                    sb.setPromotion_txt(dbcursor.getString(dbcursor.getColumnIndexOrThrow("PROMOTION")));
                    sb.setImg(dbcursor.getString(dbcursor.getColumnIndexOrThrow("IMAGE")));
                    sb.setPresent(dbcursor.getString(dbcursor.getColumnIndexOrThrow("PRESENT")));
                    sb.setRemark(dbcursor.getString(dbcursor.getColumnIndexOrThrow("REMARK")));
                    sb.setBrand_cd(dbcursor.getString(dbcursor.getColumnIndexOrThrow("BRAND_CD")));
                    sb.setBrand(dbcursor.getString(dbcursor.getColumnIndexOrThrow("BRAND")));

                    sb.setCamera(dbcursor.getString(dbcursor.getColumnIndexOrThrow("CAMERA")));
                    sb.setPromoStock(dbcursor.getString(dbcursor.getColumnIndexOrThrow("PROMO_STOCK")));
                    sb.setPromoTalker(dbcursor.getString(dbcursor.getColumnIndexOrThrow("PROMO_TALKER")));
                    sb.setRunningPOS(dbcursor.getString(dbcursor.getColumnIndexOrThrow("RUNNING_POS")));

                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return list;
        }

        return list;
    }

    public ArrayList<AssetInsertdataGetterSetter> getAssetUploadData(String storeId) {
        Log.d("Fetching", "Assetuploaddata--------------->Start<------------");
        ArrayList<AssetInsertdataGetterSetter> list = new ArrayList<AssetInsertdataGetterSetter>();
        Cursor dbcursor = null;

        try {
            dbcursor = db.rawQuery("SELECT  CD.KEY_ID, SD.ASSET_CD, SD.ASSET, SD.PRESENT, SD.REMARK, SD.IMAGE, CD.CATEGORY_CD, CD.CATEGORY " +
                    "FROM openingHeader_Asset_data CD " +
                    "INNER JOIN ASSET_DATA SD " +
                    "ON CD.KEY_ID=SD.Common_Id " +
                    "WHERE CD.STORE_CD= '" + storeId + "'", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    AssetInsertdataGetterSetter sb = new AssetInsertdataGetterSetter();

                    sb.setAsset_cd(dbcursor.getString(dbcursor.getColumnIndexOrThrow("ASSET_CD")));
                    sb.setAsset(dbcursor.getString(dbcursor.getColumnIndexOrThrow("ASSET")));
                    sb.setRemark(dbcursor.getString(dbcursor.getColumnIndexOrThrow("REMARK")));
                    sb.setImg(dbcursor.getString(dbcursor.getColumnIndexOrThrow("IMAGE")));
                    sb.setCategory_cd(dbcursor.getString(dbcursor.getColumnIndexOrThrow("CATEGORY_CD")));
                    sb.setKey_id(dbcursor.getString(dbcursor.getColumnIndexOrThrow("KEY_ID")));

                    String toggle = dbcursor.getString(dbcursor.getColumnIndexOrThrow("PRESENT"));
                    if (toggle.equalsIgnoreCase("Yes")) {
                        sb.setPresent("1");
                    } else {
                        sb.setPresent("0");
                    }

                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return list;
        }
        return list;
    }


    public ArrayList<ChecklistInsertDataGetterSetter> getAssetCheckListUploadData(String key_id) {
        ArrayList<ChecklistInsertDataGetterSetter> list = new ArrayList<>();
        Cursor dbcursor = null;

        try {
            dbcursor = db.rawQuery("SELECT * FROM ASSET_CHECKLIST_DATA " +
                    "WHERE COMMONID = '" + key_id + "'", null);


            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    ChecklistInsertDataGetterSetter sb = new ChecklistInsertDataGetterSetter();

                    sb.setChecklist(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.CHECK_LIST)));
                    sb.setChecklist_id(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.CHECK_LIST_ID)));
                    sb.setChecklist_type(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.CHECK_LIST_TYPE)));

                    String toggle = dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.CHECK_LIST_TEXT));
                    if (toggle.equalsIgnoreCase("yes")) {
                        sb.setChecklist_text("1");
                    } else {
                        sb.setChecklist_text("0");
                    }

                    sb.setReason_cd(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.REASON_ID)));

                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return list;
        }
        return list;
    }

    public void updatePJPStoreStatus(String storeid, String visitdate, String status) {
        try {
            ContentValues values = new ContentValues();
            values.put("UPLOAD_STATUS", status);

            db.update("JOURNEY_DEVIATION", values, CommonString.KEY_STORE_CD + "='" + storeid + "' AND "
                    + CommonString.KEY_VISIT_DATE + "='" + visitdate + "'", null);
        } catch (Exception e) {
        }
    }

    public ArrayList<AssetInsertdataGetterSetter> getAssetUpload(String storeId) {
        Log.d("Fetching", "Assetuploaddata--------------->Start<------------");
        ArrayList<AssetInsertdataGetterSetter> list = new ArrayList<AssetInsertdataGetterSetter>();
        Cursor dbcursor = null;

        try {

            dbcursor = db.rawQuery("SELECT SD.ASSET_CD,SD.ASSET,SD.PRESENT,SD.REMARK, SD.IMAGE,CD.CATEGORY_CD, CD.CATEGORY " +
                    "FROM openingHeader_Asset_data CD INNER JOIN ASSET_DATA SD ON CD.KEY_ID=SD.Common_Id WHERE CD.STORE_CD= '"
                    + storeId + "'", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    AssetInsertdataGetterSetter sb = new AssetInsertdataGetterSetter();

                    sb.setAsset_cd(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("ASSET_CD")));

                    sb.setAsset(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("ASSET")));
                    sb.setImg(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("IMAGE")));

                    sb.setPresent(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("PRESENT")));
                    sb.setRemark(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("REMARK")));
                    sb.setCategory_cd(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("CATEGORY_CD")));


                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return list;
        }
        return list;
    }

    public ArrayList<PromotionInsertDataGetterSetter> getPromotionUpload(String storeId) {
        ArrayList<PromotionInsertDataGetterSetter> list = new ArrayList<>();
        Cursor dbcursor = null;

        try {

            dbcursor = db
                    .rawQuery(
                            "SELECT SD.PROMOTION, SD.PID, SD.PRESENT, SD.REMARK, SD.IMAGE, CD.BRAND_CD, CD.BRAND " +
                                    "FROM openingHeader_Promotion_data CD INNER JOIN PROMOTION_DATA SD ON CD.KEY_ID=SD.Common_Id WHERE CD.STORE_CD= '"
                                    + storeId + "'", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    PromotionInsertDataGetterSetter sb = new PromotionInsertDataGetterSetter();

                    sb.setPromotion_txt(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("PROMOTION")));
                    sb.setPid(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("PID")));
                    sb.setPresent(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("PRESENT")));
                    sb.setRemark(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("REMARK")));
                    sb.setImg(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("IMAGE")));
                    sb.setBrand_cd(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("BRAND_CD")));
                    sb.setBrand(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("BRAND")));


                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return list;
        }
        return list;
    }


    public ArrayList<POIGetterSetter> getPOIData(String store_cd) {

        ArrayList<POIGetterSetter> list = new ArrayList<>();
        Cursor dbcursor = null;

        try {
            dbcursor = db.rawQuery("SELECT * from POI WHERE STORE_CD= '" + store_cd + "'"
                    , null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    POIGetterSetter fc = new POIGetterSetter();

                    fc.setId(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("Key_Id")));
                    fc.setCategory_cd(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("CATEGORY_CD")));
                    fc.setCategory(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("CATEGORY")));
                    fc.setAsset_cd(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("ASSET_CD")));
                    fc.setAsset(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("ASSET")));
                    fc.setBrand_cd(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("BRAND_CD")));
                    fc.setBrand(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("BRAND")));
                    fc.setRemark(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("REMARK")));
                    fc.setImage(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("IMAGE_POI")));
                    list.add(fc);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return list;
        }
        return list;

    }

    public ArrayList<JourneyPlanGetterSetter> getAllJCPData() {
        ArrayList<JourneyPlanGetterSetter> list = new ArrayList<>();
        Cursor dbcursor = null;
        try {

            dbcursor = db.rawQuery("SELECT * from JOURNEY_PLAN ", null);
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
            e.printStackTrace();
            return list;
        }
        return list;

    }


    public ArrayList<JourneyPlanGetterSetter> getAllPJPDEVIATIONData() {
        ArrayList<JourneyPlanGetterSetter> list = new ArrayList<>();
        Cursor dbcursor = null;
        try {

            dbcursor = db.rawQuery("SELECT * from JOURNEY_DEVIATION ", null);
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
            e.printStackTrace();
            return list;
        }
        return list;

    }


    public ArrayList<ChecklistInsertDataGetterSetter> getAssetCheckUploadData(String store_cd) {
        ArrayList<ChecklistInsertDataGetterSetter> list = new ArrayList<>();
        Cursor dbcursor = null;

        try {
            dbcursor = db.rawQuery("SELECT * FROM ASSET_CHECKLIST_INSERT " +
                    "WHERE STORE_CD = '" + store_cd + "'", null);


            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    ChecklistInsertDataGetterSetter sb = new ChecklistInsertDataGetterSetter();

                    sb.setCategory_cd(dbcursor.getString(dbcursor.getColumnIndexOrThrow("CATEGORY_CD")));
                    sb.setAsset_cd(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.ASSET_CD)));
                    sb.setChecklist(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.CHECK_LIST)));
                    sb.setChecklist_id(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.CHECK_LIST_ID)));
                    sb.setChecklist_type(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.CHECK_LIST_TYPE)));

                    String toggle = dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.CHECK_LIST_TEXT));
                    if (toggle.equalsIgnoreCase("yes")) {
                        sb.setChecklist_text("1");
                    } else {
                        sb.setChecklist_text("0");
                    }


                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return list;
        }
        return list;
    }

    public ArrayList<StockNewGetterSetter> getAssetSkuListData(String store_cd) {
        Log.d("Fetching ", "skulist data--------------->Start<------------");
        ArrayList<StockNewGetterSetter> list = new ArrayList<>();
        Cursor dbcursor = null;

        try {
            dbcursor = db.rawQuery("SELECT * FROM " + CommonString.TABLE_ASSET_SKU_CHECKLIST_INSERT +
                    " WHERE STORE_CD = '" + store_cd + "'", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    StockNewGetterSetter sb = new StockNewGetterSetter();

                    sb.setSku_cd(dbcursor.getString(dbcursor.getColumnIndexOrThrow("SKU_CD")));
                    sb.setSku(dbcursor.getString(dbcursor.getColumnIndexOrThrow("SKU")));
                    sb.setBrand_cd(dbcursor.getString(dbcursor.getColumnIndexOrThrow("BRAND_CD")));
                    sb.setBrand(dbcursor.getString(dbcursor.getColumnIndexOrThrow("BRAND")));
                    sb.setChk_skuBox(dbcursor.getString(dbcursor.getColumnIndexOrThrow("SKU_CHECK_BOX")));
                    sb.setCategory_cd(dbcursor.getString(dbcursor.getColumnIndexOrThrow("CATEGORY_CD")));
                    sb.setAsset_cd(dbcursor.getString(dbcursor.getColumnIndexOrThrow("ASSET_CD")));

                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }
        } catch (Exception e) {
            Log.d("Exception ", "when fetching checklist data!!!!!!!!!!!" + e.toString());
            return list;
        }

        Log.d("Fetching", " checklist data---------------------->Stop<-----------");
        return list;
    }

    public void updateCoverageStoreOutTime(String StoreId, String VisitDate, String outtime, String status) {
        try {
            ContentValues values = new ContentValues();
            values.put(CommonString.KEY_OUT_TIME, outtime);
            values.put(CommonString.KEY_COVERAGE_STATUS, status);

            db.update(CommonString.TABLE_COVERAGE_DATA, values, CommonString.KEY_STORE_ID + "='" + StoreId + "' AND "
                    + CommonString.KEY_VISIT_DATE + "='" + VisitDate + "'", null);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public String getVisiteDateFromCoverage(String storeid) {

        Cursor dbcursor = null;
        String visite_date = "";
        try {
            dbcursor = db.rawQuery("SELECT * from "
                    + CommonString.TABLE_COVERAGE_DATA + " WHERE "
                    + CommonString.KEY_STORE_ID + " ='" + storeid
                    + "'", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();

                visite_date = dbcursor.getString(dbcursor
                        .getColumnIndexOrThrow(CommonString.KEY_VISIT_DATE));

                dbcursor.close();

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return visite_date;
    }


}
