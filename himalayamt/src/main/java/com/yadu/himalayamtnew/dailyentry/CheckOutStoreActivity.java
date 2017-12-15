package com.yadu.himalayamtnew.dailyentry;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.yadu.himalayamtnew.R;
import com.yadu.himalayamtnew.constants.AlertandMessages;
import com.yadu.himalayamtnew.constants.CommonFunctions;
import com.yadu.himalayamtnew.constants.CommonString;
import com.yadu.himalayamtnew.database.HimalayaDb;
import com.yadu.himalayamtnew.delegates.CoverageBean;
import com.yadu.himalayamtnew.xmlGetterSetter.FailureGetterSetter;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class CheckOutStoreActivity extends Activity  {

    private Dialog dialog;
    private ProgressBar pb;
    private TextView percentage, message;
    private String username, visit_date, store_id;
    private Data data;
    private HimalayaDb db;
    private SharedPreferences preferences = null;
    boolean flag_deviation;
    ArrayList<CoverageBean> specificCDADA = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out_store);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        username = preferences.getString(CommonString.KEY_USERNAME, "");
        store_id = preferences.getString(CommonString.KEY_STORE_CD, "");
        visit_date = preferences.getString(CommonString.KEY_DATE, "");
        db = new HimalayaDb(this);
        db.open();
        flag_deviation = getIntent().getBooleanExtra(CommonString.KEY_PJP_DEVIATION, false);
        store_id = getIntent().getStringExtra(CommonString.KEY_STORE_CD);
        specificCDADA = db.getCoverageSpecificData(store_id);
        new BackgroundTask(this).execute();
    }

    @Override
    protected void onResume() {
        super.onResume();
        db.open();
    }

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
        db.close();
    }

    private class BackgroundTask extends AsyncTask<Void, Data, String> {
        private Context context;

        BackgroundTask(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            dialog = new Dialog(context);
            dialog.setContentView(R.layout.custom);
            dialog.setTitle("Sending Checkout Data");
            dialog.setCancelable(false);
            dialog.show();
            pb = (ProgressBar) dialog.findViewById(R.id.progressBar1);
            percentage = (TextView) dialog.findViewById(R.id.percentage);
            message = (TextView) dialog.findViewById(R.id.message);
        }

        @SuppressWarnings("deprecation")
        @Override
        protected String doInBackground(Void... params) {
            try {

                if (specificCDADA.size() > 0) {
                    data = new Data();
                    data.value = 20;
                    data.name = "Checked out Data Uploading";
                    publishProgress(data);
                    String onXML = "[STORE_CHECK_OUT_STATUS]"
                            + "[USER_ID]" + username + "[/USER_ID]"
                            + "[STORE_ID]" + store_id + "[/STORE_ID]"
                            + "[LATITUDE]" + "0.0" + "[/LATITUDE]"
                            + "[LOGITUDE]" + "0.0" + "[/LOGITUDE]"
                            + "[CHECKOUT_DATE]" + visit_date + "[/CHECKOUT_DATE]"
                            + "[CHECK_OUTTIME]" + getCurrentTime() + "[/CHECK_OUTTIME]"
                            + "[CHECK_INTIME]" + specificCDADA.get(0).getInTime() + "[/CHECK_INTIME]"
                            + "[CREATED_BY]" + username + "[/CREATED_BY]"
                            + "[/STORE_CHECK_OUT_STATUS]";

                    final String sos_xml = "[DATA]" + onXML + "[/DATA]";
                    SoapObject request = new SoapObject(CommonString.NAMESPACE, "Upload_Store_ChecOut_Status");
                    request.addProperty("onXML", sos_xml);
                    SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                    envelope.dotNet = true;
                    envelope.setOutputSoapObject(request);
                    HttpTransportSE androidHttpTransport = new HttpTransportSE(CommonString.URL);
                    androidHttpTransport.call(CommonString.SOAP_ACTION + "Upload_Store_ChecOut_Status", envelope);
                    Object result = (Object) envelope.getResponse();
                    if (result.toString().equalsIgnoreCase(CommonString.KEY_SUCCESS_chkout)) {
                        db.updateCoverageStoreOutTime(store_id, visit_date, getCurrentTime(), CommonString.KEY_C);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString(CommonString.KEY_STOREVISITED, "");
                        editor.putString(CommonString.KEY_STOREVISITED_STATUS, "");
                        editor.commit();
                        if (flag_deviation) {
                            db.updateDeviationStoreStatusOnCheckout(store_id, visit_date, CommonString.KEY_C);
                        } else {
                            db.updateStoreStatusOnCheckout(store_id, visit_date, CommonString.KEY_C);
                        }
                        data.value = 100;
                        data.name = "Checkout Done";
                        publishProgress(data);
                        return CommonString.KEY_SUCCESS;
                    } else {
                        return CommonString.METHOD_Checkout_StatusNew;
                    }
                }
                return CommonString.KEY_SUCCESS;


            } catch (MalformedURLException e) {
                return CommonString.MESSAGE_EXCEPTION;
            } catch (IOException e) {
                return CommonString.MESSAGE_SOCKETEXCEPTION;
            } catch (Exception e) {
                return CommonString.MESSAGE_EXCEPTION;
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
            if (result.equals(CommonString.KEY_SUCCESS)) {
                AlertandMessages.showAlert((Activity) context, "Successfully Checked out", false);
                finish();
            } else if (!result.equals("")) {
                AlertandMessages.showToastMsg(context, "Network Error Try Again");
                finish();
            }
        }

    }

    class Data {
        int value;
        String name;
    }


    public String getCurrentTime() {
        Calendar m_cal = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        String cdate = formatter.format(m_cal.getTime());
        return cdate;

    }

}
