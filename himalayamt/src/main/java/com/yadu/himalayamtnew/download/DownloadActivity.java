package com.yadu.himalayamtnew.download;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.yadu.himalayamtnew.R;
import com.yadu.himalayamtnew.constants.AlertandMessages;
import com.yadu.himalayamtnew.constants.CommonString;
import com.yadu.himalayamtnew.database.HimalayaDb;
import com.yadu.himalayamtnew.fragment.MainFragment;
import com.yadu.himalayamtnew.xmlGetterSetter.AssetChecklistGetterSetter;
import com.yadu.himalayamtnew.xmlGetterSetter.AssetMasterGetterSetter;
import com.yadu.himalayamtnew.xmlGetterSetter.Audit_QuestionGetterSetter;
import com.yadu.himalayamtnew.xmlGetterSetter.BrandGetterSetter;
import com.yadu.himalayamtnew.xmlGetterSetter.CategoryMasterGetterSetter;
import com.yadu.himalayamtnew.xmlGetterSetter.CompanyGetterSetter;
import com.yadu.himalayamtnew.xmlGetterSetter.JourneyPlanGetterSetter;
import com.yadu.himalayamtnew.xmlGetterSetter.MappingAssetChecklistGetterSetter;
import com.yadu.himalayamtnew.xmlGetterSetter.MappingAssetChecklistreasonGetterSetter;
import com.yadu.himalayamtnew.xmlGetterSetter.MappingAssetGetterSetter;
import com.yadu.himalayamtnew.xmlGetterSetter.MappingAvailabilityGetterSetter;
import com.yadu.himalayamtnew.xmlGetterSetter.MappingPromotionGetterSetter;
import com.yadu.himalayamtnew.xmlGetterSetter.NonComplianceChecklistGetterSetter;
import com.yadu.himalayamtnew.xmlGetterSetter.NonWorkingReasonGetterSetter;
import com.yadu.himalayamtnew.xmlGetterSetter.PayslipGetterSetter;
import com.yadu.himalayamtnew.xmlGetterSetter.SkuMasterGetterSetter;
import com.yadu.himalayamtnew.xmlGetterSetter.TableBean;
import com.yadu.himalayamtnew.xmlHandler.XMLHandlers;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.net.MalformedURLException;


public class DownloadActivity extends AppCompatActivity {

    private Dialog dialog;
    private ProgressBar pb;
    private TextView percentage, message;
    private Data data;
    int eventType;

    HimalayaDb db;
    TableBean tb;
    String _UserId, visit_date;
    private SharedPreferences preferences;
    boolean promotion_flag = true;
    boolean asset_flag = true;
    JourneyPlanGetterSetter jcpgettersetter;
    SkuMasterGetterSetter skumastergettersetter;
    MappingAvailabilityGetterSetter mappingavailgettersetter;
    MappingPromotionGetterSetter mappingprormotgettersetter;
    MappingAssetGetterSetter mappingassetgettersetter;
    AssetMasterGetterSetter assetmastergettersetter;
    CompanyGetterSetter companyGetterSetter;
    BrandGetterSetter brandGetterSetter;
    NonWorkingReasonGetterSetter nonworkinggettersetter;
    CategoryMasterGetterSetter categorygettersetter;
    AssetChecklistGetterSetter assetChecklistGetterSetter;
    MappingAssetChecklistGetterSetter mappingAssetChecklistGetterSetter;
    PayslipGetterSetter payslipGetterSetter;
    Audit_QuestionGetterSetter audit_questionGetterSetter;

    NonComplianceChecklistGetterSetter nonComplianceChecklistGetterSetter;
    MappingAssetChecklistreasonGetterSetter mappingAssetChecklistreasonGetterSetter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        _UserId = preferences.getString(CommonString.KEY_USERNAME, "");
        visit_date = preferences.getString(CommonString.KEY_DATE, null);

        //getSupportActionBar().setTitle("Main Menu - " + visit_date);
        tb = new TableBean();
        db = new HimalayaDb(this);

        new BackgroundTask(this).execute();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // db.open();
        FragmentManager fragmentManager = getSupportFragmentManager();
        MainFragment cartfrag = new MainFragment();
        fragmentManager.beginTransaction()
                .replace(R.id.frame_layout, cartfrag)
                .commit();

    }

    class Data {
        int value;
        String name;
    }

    private class BackgroundTask extends AsyncTask<Void, Data, String> {
        private Context context;

        boolean success_flag = true;
        String error_msg = "";

        BackgroundTask(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.custom);
            //dialog.setTitle("Download Files");
            dialog.setCancelable(false);
            dialog.show();
            pb = (ProgressBar) dialog.findViewById(R.id.progressBar1);
            percentage = (TextView) dialog.findViewById(R.id.percentage);
            message = (TextView) dialog.findViewById(R.id.message);
        }

        @Override
        protected String doInBackground(Void... params) {
            String resultHttp = "";
            try {
                data = new Data();
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(true);
                XmlPullParser xpp = factory.newPullParser();

                // Store List Master
                SoapObject request = new SoapObject(CommonString.NAMESPACE,
                        CommonString.METHOD_NAME_UNIVERSAL_DOWNLOAD);
                request.addProperty("UserName", _UserId);
                request.addProperty("Type", "SKU_MASTER");
                SoapSerializationEnvelope envelope = new
                        SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);
                HttpTransportSE androidHttpTransport = new HttpTransportSE(CommonString.URL);
                androidHttpTransport.call(CommonString.SOAP_ACTION_UNIVERSAL, envelope);
                Object result = (Object) envelope.getResponse();
                if (result.toString() != null) {
                    xpp.setInput(new StringReader(result.toString()));
                    xpp.next();
                    eventType = xpp.getEventType();
                    skumastergettersetter = XMLHandlers.storeListXML(xpp, eventType);
                    if (skumastergettersetter.getSku_cd().size() > 0) {
                        String skutable = skumastergettersetter.getSku_master_table();
                        if (skutable != null) {
                            resultHttp = CommonString.KEY_SUCCESS;
                            TableBean.setSkumastertable(skutable);
                        }
                    } else {
                        return CommonString.MESSAGE_JCP_FALSE + "SKU_MASTER";
                    }
                    data.value = 20;
                    data.name = "Store Data Download";
                }
                publishProgress(data);

                // Company Master data
                request = new SoapObject(CommonString.NAMESPACE, CommonString.METHOD_NAME_UNIVERSAL_DOWNLOAD);
                request.addProperty("UserName", _UserId);
                request.addProperty("Type", "COMPANY_MASTER");

                envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);

                androidHttpTransport = new HttpTransportSE(CommonString.URL);
                androidHttpTransport.call(CommonString.SOAP_ACTION_UNIVERSAL, envelope);

                Object resultcompany = (Object) envelope.getResponse();

                if (resultcompany.toString() != null) {
                    xpp.setInput(new StringReader(resultcompany.toString()));
                    xpp.next();
                    eventType = xpp.getEventType();

                    companyGetterSetter = XMLHandlers.comapnyMasterXML(xpp, eventType);

                    if (companyGetterSetter.getCompany_cd().size() > 0) {
                        resultHttp = CommonString.KEY_SUCCESS;
                        String companytable = companyGetterSetter.getCompany_master_table();
                        TableBean.setCompanytable(companytable);
                    } else {
                        return CommonString.MESSAGE_JCP_FALSE + "COMPANY_MASTER";
                    }
                    data.value = 25;
                    data.name = "Company Master Data Downloading";
                }
                publishProgress(data);


                // Brand Master data
                request = new SoapObject(CommonString.NAMESPACE, CommonString.METHOD_NAME_UNIVERSAL_DOWNLOAD);
                request.addProperty("UserName", _UserId);
                request.addProperty("Type", "BRAND_MASTER");

                envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);

                androidHttpTransport = new HttpTransportSE(CommonString.URL);
                androidHttpTransport.call(CommonString.SOAP_ACTION_UNIVERSAL, envelope);

                Object resultbrand = (Object) envelope.getResponse();

                if (resultbrand.toString() != null) {
                    xpp.setInput(new StringReader(resultbrand.toString()));
                    xpp.next();
                    eventType = xpp.getEventType();

                    brandGetterSetter = XMLHandlers.brandMasterXML(xpp, eventType);

                    if (brandGetterSetter.getBrand_cd().size() > 0) {
                        resultHttp = CommonString.KEY_SUCCESS;
                        String brandtable = brandGetterSetter.getBrand_master_table();
                        TableBean.setBrandtable(brandtable);
                    } else {
                        return CommonString.MESSAGE_JCP_FALSE + "BRAND_MASTER";
                    }
                    data.value = 30;
                    data.name = "Brand Master Data Downloading";
                }
                publishProgress(data);


                //Category master data
                request = new SoapObject(CommonString.NAMESPACE, CommonString.METHOD_NAME_UNIVERSAL_DOWNLOAD);
                request.addProperty("UserName", _UserId);
                request.addProperty("Type", "CATEGORY_MASTER");

                envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);

                androidHttpTransport = new HttpTransportSE(CommonString.URL);
                androidHttpTransport.call(CommonString.SOAP_ACTION_UNIVERSAL, envelope);

                Object resultcategorymaster = (Object) envelope.getResponse();

                if (resultcategorymaster.toString() != null) {
                    xpp.setInput(new StringReader(resultcategorymaster.toString()));
                    xpp.next();
                    eventType = xpp.getEventType();

                    categorygettersetter = XMLHandlers.categoryMasterXML(xpp, eventType);

                    if (categorygettersetter.getCategory_cd().size() > 0) {
                        resultHttp = CommonString.KEY_SUCCESS;
                        String categorytable = categorygettersetter.getCategory_master_table();
                        TableBean.setCategorymastertable(categorytable);
                    } else {
                        return CommonString.MESSAGE_JCP_FALSE + "CATEGORY_MASTER";
                    }
                    data.value = 40;
                    data.name = "Category Master Downloading";
                }
                publishProgress(data);


                // Mapping Availability data
                request = new SoapObject(CommonString.NAMESPACE, CommonString.METHOD_NAME_UNIVERSAL_DOWNLOAD);
                request.addProperty("UserName", _UserId);
                request.addProperty("Type", "MAPPING_AVAILABILITY");

                envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);

                androidHttpTransport = new HttpTransportSE(CommonString.URL);
                androidHttpTransport.call(CommonString.SOAP_ACTION_UNIVERSAL, envelope);

                Object resultnonWorking = (Object) envelope.getResponse();

                if (resultnonWorking.toString() != null) {
                    xpp.setInput(new StringReader(resultnonWorking.toString()));
                    xpp.next();
                    eventType = xpp.getEventType();

                    mappingavailgettersetter = XMLHandlers.mappingavailXML(xpp, eventType);

                    if (mappingavailgettersetter.getMapping_avail_table() != null) {
                        String mappingtable = mappingavailgettersetter.getMapping_avail_table();
                        TableBean.setMappingavailtable(mappingtable);
                    }

                    if (mappingavailgettersetter.getSku_cd().size() > 0) {
                        resultHttp = CommonString.KEY_SUCCESS;
                        data.value = 50;
                        data.name = "Mapping Availability data Downloading";
                    }
                }
                publishProgress(data);


                // Mapping Promotion data
                request = new SoapObject(CommonString.NAMESPACE, CommonString.METHOD_NAME_UNIVERSAL_DOWNLOAD);
                request.addProperty("UserName", _UserId);
                request.addProperty("Type", "MAPPING_PROMOTION");

                envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);

                androidHttpTransport = new HttpTransportSE(CommonString.URL);
                androidHttpTransport.call(CommonString.SOAP_ACTION_UNIVERSAL, envelope);

                Object resultmapping = (Object) envelope.getResponse();

                if (resultmapping.toString() != null) {
                    xpp.setInput(new StringReader(resultmapping.toString()));
                    xpp.next();
                    eventType = xpp.getEventType();

                    mappingprormotgettersetter = XMLHandlers.mappingpromotXML(xpp, eventType);

                    //if (mappingprormotgettersetter.getMapping_promotion_table() != null) {
                    String mappingtable = mappingprormotgettersetter.getMapping_promotion_table();
                    TableBean.setMappingpromotable(mappingtable);
                    //}

                    if (mappingprormotgettersetter.getPid().size() > 0) {
                        resultHttp = CommonString.KEY_SUCCESS;
                        data.value = 55;
                        data.name = "Mapping Promotion Data Downloading";
                    } else {
                        promotion_flag = false;
                    }
                }
                publishProgress(data);


                // Mapping Asset data
                request = new SoapObject(CommonString.NAMESPACE, CommonString.METHOD_NAME_UNIVERSAL_DOWNLOAD);
                request.addProperty("UserName", _UserId);
                request.addProperty("Type", "MAPPING_ASSET_NEW");
                envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);

                androidHttpTransport = new HttpTransportSE(CommonString.URL);
                androidHttpTransport.call(CommonString.SOAP_ACTION_UNIVERSAL, envelope);

                Object resultmappingasst = (Object) envelope.getResponse();

                if (resultmappingasst.toString() != null) {
                    xpp.setInput(new StringReader(resultmappingasst.toString()));
                    xpp.next();
                    eventType = xpp.getEventType();

                    mappingassetgettersetter = XMLHandlers.mappingassetXML(xpp, eventType);

                    if (mappingassetgettersetter.getMapping_asset_table() != null) {
                        String mappingtable = mappingassetgettersetter.getMapping_asset_table();
                        TableBean.setMappingassettable(mappingtable);
                    }

                    if (mappingassetgettersetter.getStore_cd().size() > 0) {
                        resultHttp = CommonString.KEY_SUCCESS;
                        data.value = 60;
                        data.name = "Mapping Asset Data Downloading";
                    } else {
                        asset_flag = false;
                    }
                }
                publishProgress(data);


                // Asset Master data
                request = new SoapObject(CommonString.NAMESPACE, CommonString.METHOD_NAME_UNIVERSAL_DOWNLOAD);
                request.addProperty("UserName", _UserId);
                request.addProperty("Type", "ASSET_MASTER");

                envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);

                androidHttpTransport = new HttpTransportSE(CommonString.URL);
                androidHttpTransport.call(CommonString.SOAP_ACTION_UNIVERSAL, envelope);

                Object resultasset = (Object) envelope.getResponse();

                if (resultasset.toString() != null) {
                    xpp.setInput(new StringReader(resultasset.toString()));
                    xpp.next();
                    eventType = xpp.getEventType();

                    assetmastergettersetter = XMLHandlers.assetMasterXML(xpp, eventType);

                    if (assetmastergettersetter.getAsset_cd().size() > 0) {
                        resultHttp = CommonString.KEY_SUCCESS;
                        String assettable = assetmastergettersetter.getAsset_master_table();
                        TableBean.setAssetmastertable(assettable);
                    } else {
                        return CommonString.MESSAGE_JCP_FALSE + "ASSET_MASTER";
                    }
                    data.value = 70;
                    data.name = "Asset Master Data Downloading";
                }
                publishProgress(data);

                // ASSET_CHECKLIST data
                request = new SoapObject(CommonString.NAMESPACE, CommonString.METHOD_NAME_UNIVERSAL_DOWNLOAD);
                request.addProperty("UserName", _UserId);
                request.addProperty("Type", "ASSET_CHECKLIST");

                envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);

                androidHttpTransport = new HttpTransportSE(CommonString.URL);
                androidHttpTransport.call(CommonString.SOAP_ACTION_UNIVERSAL, envelope);

                Object resultasset_check = (Object) envelope.getResponse();

                if (resultasset_check.toString() != null) {
                    xpp.setInput(new StringReader(resultasset_check.toString()));
                    xpp.next();
                    eventType = xpp.getEventType();

                    assetChecklistGetterSetter = XMLHandlers.assetChecklistXMLHandler(xpp, eventType);

                    if (assetChecklistGetterSetter.getAssetchecklist_insert_table() != null) {
                        String assetchecklist_table = assetChecklistGetterSetter.getAssetchecklist_insert_table();
                        TableBean.setAsset_checklist_table(assetchecklist_table);
                    }

                    if (assetChecklistGetterSetter.getCHECKLIST_ID().size() > 0) {
                        resultHttp = CommonString.KEY_SUCCESS;
                    } else {
                        //return "BRAND_MASTER";
                    }
                    data.value = 80;
                    data.name = "Checklist Data Downloading";
                }
                publishProgress(data);

                // MAPPING_ASSET_CHECKLIST
                request = new SoapObject(CommonString.NAMESPACE, CommonString.METHOD_NAME_UNIVERSAL_DOWNLOAD);
                request.addProperty("UserName", _UserId);
                request.addProperty("Type", "MAPPING_ASSET_CHECKLIST");

                envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);

                androidHttpTransport = new HttpTransportSE(CommonString.URL);
                androidHttpTransport.call(CommonString.SOAP_ACTION_UNIVERSAL, envelope);

                Object resultmapping_asset_check = (Object) envelope.getResponse();

                if (resultmapping_asset_check.toString() != null) {
                    xpp.setInput(new StringReader(resultmapping_asset_check.toString()));
                    xpp.next();
                    eventType = xpp.getEventType();

                    mappingAssetChecklistGetterSetter = XMLHandlers.mappingAssetChecklistXMLHandler(xpp, eventType);

                    if (mappingAssetChecklistGetterSetter.getMapping_asset_checklist_table() != null) {
                        String mappingAssetchecklist_table = mappingAssetChecklistGetterSetter.getMapping_asset_checklist_table();
                        TableBean.setMapping_asset_checklist_table(mappingAssetchecklist_table);
                    }

                    if (mappingAssetChecklistGetterSetter.getAsset_cd().size() > 0) {
                        resultHttp = CommonString.KEY_SUCCESS;
                    } else {
                        //return "BRAND_MASTER";
                    }
                    data.value = 85;
                    data.name = "Checklist Mapping Downloading";
                }
                publishProgress(data);


                //Non Working Reason data
                request = new SoapObject(CommonString.NAMESPACE, CommonString.METHOD_NAME_UNIVERSAL_DOWNLOAD);
                request.addProperty("UserName", _UserId);
                request.addProperty("Type", "NON_WORKING_REASON_NEW");

                envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);

                androidHttpTransport = new HttpTransportSE(CommonString.URL);
                androidHttpTransport.call(CommonString.SOAP_ACTION_UNIVERSAL, envelope);

                Object resultnonworking = (Object) envelope.getResponse();

                if (resultnonworking.toString() != null) {
                    xpp.setInput(new StringReader(resultnonworking.toString()));
                    xpp.next();
                    eventType = xpp.getEventType();

                    nonworkinggettersetter = XMLHandlers.nonWorkinReasonXML(xpp, eventType);

                    if (nonworkinggettersetter.getReason_cd().size() > 0) {
                        resultHttp = CommonString.KEY_SUCCESS;
                        String nonworkingtable = nonworkinggettersetter.getNonworking_table();
                        TableBean.setNonworkingtable(nonworkingtable);
                    } else {
                        return CommonString.MESSAGE_JCP_FALSE + "NON_WORKING_REASON_NEW";
                    }
                    data.value = 90;
                    data.name = "Non Working Reason Downloading";

                }
                publishProgress(data);

                //NON_COMPLIANCE_CHECKLIST
                request = new SoapObject(CommonString.NAMESPACE, CommonString.METHOD_NAME_UNIVERSAL_DOWNLOAD);
                request.addProperty("UserName", _UserId);
                request.addProperty("Type", "NON_COMPLIANCE_CHECKLIST");

                envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);

                androidHttpTransport = new HttpTransportSE(CommonString.URL);
                androidHttpTransport.call(CommonString.SOAP_ACTION_UNIVERSAL, envelope);

                Object result_noncompliance_cecklist = (Object) envelope.getResponse();

                if (result_noncompliance_cecklist.toString() != null) {
                    xpp.setInput(new StringReader(result_noncompliance_cecklist.toString()));
                    xpp.next();
                    eventType = xpp.getEventType();

                    nonComplianceChecklistGetterSetter = XMLHandlers.nonComplianceChecklistXML(xpp, eventType);

                    if (nonComplianceChecklistGetterSetter.getCREASON_ID().size() > 0) {
                        resultHttp = CommonString.KEY_SUCCESS;
                        String noncomliancetable = nonComplianceChecklistGetterSetter.getTable_NON_COMPLIANCE_CHECKLIST();
                        TableBean.setTable_NON_COMPLIANCE_CHECKLIST(noncomliancetable);
                    } else {
                        return CommonString.MESSAGE_JCP_FALSE + "NON_COMPLIANCE_CHECKLIST";
                    }
                    data.value = 90;
                    data.name = "Non Working Reason Downloading";

                }
                publishProgress(data);

                //MAPPING_ASSET_CHECKLIST_REASON
                request = new SoapObject(CommonString.NAMESPACE, CommonString.METHOD_NAME_UNIVERSAL_DOWNLOAD);
                request.addProperty("UserName", _UserId);
                request.addProperty("Type", "MAPPING_ASSET_CHECKLIST_REASON");

                envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);

                androidHttpTransport = new HttpTransportSE(CommonString.URL);
                androidHttpTransport.call(CommonString.SOAP_ACTION_UNIVERSAL, envelope);

                Object result_mapping_asset_cecklist = (Object) envelope.getResponse();

                if (result_mapping_asset_cecklist.toString() != null) {
                    xpp.setInput(new StringReader(result_mapping_asset_cecklist.toString()));
                    xpp.next();
                    eventType = xpp.getEventType();

                    mappingAssetChecklistreasonGetterSetter = XMLHandlers.mappingAssetChecklistReasonXML(xpp, eventType);

                    if (mappingAssetChecklistreasonGetterSetter.getCHECKLIST_ID().size() > 0) {
                        resultHttp = CommonString.KEY_SUCCESS;
                        String checklist_reasontable = mappingAssetChecklistreasonGetterSetter.getTable_MAPPING_ASSET_CHECKLIST_REASON();
                        TableBean.setTable_MAPPING_ASSET_CHECKLIST_REASON(checklist_reasontable);
                        data.value = 90;
                        data.name = "Non Working Reason Downloading";

                    } else {
                        //return "MAPPING_ASSET_CHECKLIST_REASON";
                    }

                }
                publishProgress(data);

                //Payment Slip Data
                request = new SoapObject(CommonString.NAMESPACE, CommonString.METHOD_NAME_UNIVERSAL_DOWNLOAD);
                request.addProperty("UserName", _UserId);
                request.addProperty("Type", "EMP_SALARY");

                envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);

                androidHttpTransport = new HttpTransportSE(CommonString.URL);
                androidHttpTransport.call(CommonString.SOAP_ACTION_UNIVERSAL, envelope);

                Object result1 = (Object) envelope.getResponse();

                if (result1.toString() != null) {
                    xpp.setInput(new StringReader(result1.toString()));
                    xpp.next();
                    eventType = xpp.getEventType();

                    payslipGetterSetter = XMLHandlers.payslipXML(xpp, eventType);

                    if (payslipGetterSetter.getEmp_salary_table() != null) {
                        String payslipTable = payslipGetterSetter.getEmp_salary_table();
                        TableBean.setEmp_payslip_table(payslipTable);
                    } else {
                        return CommonString.MESSAGE_JCP_FALSE + "EMP_SALARY";
                    }

                    data.value = 95;
                    data.name = "EMP_SALARY Downloading";
                }

                //AUDIT_QUESTION_CATEGORYWISE
                request = new SoapObject(CommonString.NAMESPACE, CommonString.METHOD_NAME_UNIVERSAL_DOWNLOAD);
                request.addProperty("UserName", _UserId);
                request.addProperty("Type", "AUDIT_QUESTION_CATEGORYWISE");

                envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);

                androidHttpTransport = new HttpTransportSE(CommonString.URL);
                androidHttpTransport.call(CommonString.SOAP_ACTION_UNIVERSAL, envelope);

                Object auditQuestionResult_category = (Object) envelope.getResponse();

                if (auditQuestionResult_category.toString() != null) {
                    xpp.setInput(new StringReader(auditQuestionResult_category.toString()));
                    xpp.next();
                    eventType = xpp.getEventType();

                    audit_questionGetterSetter = XMLHandlers.audit_QuestionXML(xpp, eventType);

                    String auditQuestionTable = audit_questionGetterSetter.getAudit_question_table();
                    TableBean.setAudit_question_table(auditQuestionTable);

                    data.value = 100;
                    data.name = "AUDIT_QUESTION_CATEGORYWISE Downloading";
                }

                // JCP

                request = new SoapObject(CommonString.NAMESPACE,
                        CommonString.METHOD_NAME_UNIVERSAL_DOWNLOAD);
                request.addProperty("UserName", _UserId);
                request.addProperty("Type", "JOURNEY_PLAN");

                envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);

                androidHttpTransport = new HttpTransportSE(CommonString.URL);
                androidHttpTransport.call(CommonString.SOAP_ACTION_UNIVERSAL, envelope);

                Object result_jcp = (Object) envelope.getResponse();

                if (result_jcp.toString() != null) {
                    xpp.setInput(new StringReader(result_jcp.toString()));
                    xpp.next();
                    eventType = xpp.getEventType();

                    jcpgettersetter = XMLHandlers.JCPXMLHandler(xpp, eventType);
                    String jcpTable = jcpgettersetter.getTable_journey_plan();
                    TableBean.setjcptable(jcpTable);

                    if (jcpgettersetter.getStore_cd().size() > 0) {
                        resultHttp = CommonString.KEY_SUCCESS;

                        data.name = "JCP Data Downloading";
                        publishProgress(data);
                    }

                }

                //Database insert method calling
                db.open();
                db.insertJCPData(jcpgettersetter);
                db.insertSkuMasterData(skumastergettersetter);
                db.insertMappingAvailData(mappingavailgettersetter);
                if (promotion_flag) {
                    db.insertMappingPromotionData(mappingprormotgettersetter);
                } else {
                    db.deletePromotionMapping();
                }

                if (asset_flag) {
                    db.insertMappingAssetData(mappingassetgettersetter);
                } else {
                    db.deleteAssetMapping();
                }


                db.insertAssetMasterData(assetmastergettersetter);
                db.insertCompanyMasterData(companyGetterSetter);
                db.insertBrandMasterData(brandGetterSetter);

                db.insertAssetChecklistData(assetChecklistGetterSetter);

                if (mappingAssetChecklistGetterSetter.getAsset_cd().size() > 0) {
                    db.insertMappingAssetChecklistData(mappingAssetChecklistGetterSetter);
                } else {
                    db.deleteMappingAssetChecklist();
                }

                db.insertCategoryMasterData(categorygettersetter);

                db.insertNonWorkingReasonData(nonworkinggettersetter);

                db.insertPaySlipdata(payslipGetterSetter);
                //Audit
                db.insertAuditQuestionData(audit_questionGetterSetter);

                db.insertNonComplianceChecklistData(nonComplianceChecklistGetterSetter);
                db.insertMappingAssetChecklistReasonData(mappingAssetChecklistreasonGetterSetter);

                data.value = 100;
                data.name = "Finishing";
                publishProgress(data);

                return resultHttp;

            } catch (MalformedURLException e) {
                success_flag = false;
                error_msg = CommonString.MESSAGE_EXCEPTION;

            } catch (IOException e) {
                success_flag = false;
                error_msg = CommonString.MESSAGE_SOCKETEXCEPTION;
            } catch (Exception e) {
                success_flag = false;
                error_msg = CommonString.MESSAGE_EXCEPTION;
            }

            if (success_flag) {
                return CommonString.KEY_SUCCESS;
            } else {
                return error_msg;
            }

        }

        @Override
        protected void onProgressUpdate(Data... values) {
            pb.setProgress(values[0].value);
            percentage.setText(values[0].value + "%");
            message.setText(values[0].name);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            dialog.dismiss();
            if (success_flag){
                if (result.equals(CommonString.KEY_SUCCESS)) {
                    AlertandMessages.showAlert(DownloadActivity.this, CommonString.MESSAGE_DOWNLOAD, true);
                } else {
                    AlertandMessages.showAlert(DownloadActivity.this, result, true);
                }
            }

        }

    }
}
