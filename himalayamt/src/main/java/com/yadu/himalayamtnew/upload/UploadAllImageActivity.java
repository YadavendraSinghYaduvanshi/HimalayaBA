package com.yadu.himalayamtnew.upload;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.yadu.himalayamtnew.R;
import com.yadu.himalayamtnew.constants.AlertandMessages;
import com.yadu.himalayamtnew.constants.CommonString;
import com.yadu.himalayamtnew.database.HimalayaDb;
import com.yadu.himalayamtnew.delegates.CoverageBean;
import com.yadu.himalayamtnew.xmlGetterSetter.AssetInsertdataGetterSetter;
import com.yadu.himalayamtnew.xmlGetterSetter.FailureGetterSetter;
import com.yadu.himalayamtnew.xmlGetterSetter.POIGetterSetter;
import com.yadu.himalayamtnew.xmlGetterSetter.PromotionInsertDataGetterSetter;
import com.yadu.himalayamtnew.xmlGetterSetter.StockNewGetterSetter;
import com.yadu.himalayamtnew.xmlHandler.FailureXMLHandler;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.util.ArrayList;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;


public class UploadAllImageActivity extends Activity {

    private Dialog dialog;
    private ProgressBar pb;
    private TextView percentage, message;
    private String visit_date;
    private SharedPreferences preferences;
    private HimalayaDb database;
    private FailureGetterSetter failureGetterSetter = null;
    String result, username;
    String errormsg = "";
    String status;

    private ArrayList<CoverageBean> coverageBeanlist = new ArrayList<CoverageBean>();

    private ArrayList<AssetInsertdataGetterSetter> assetInsertdata = new ArrayList<>();
    private ArrayList<PromotionInsertDataGetterSetter> promotionData = new ArrayList<>();
    private ArrayList<POIGetterSetter> poiInsertdata = new ArrayList<>();
    ArrayList<StockNewGetterSetter> stockImgData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        visit_date = preferences.getString(CommonString.KEY_DATE, null);
        username = preferences.getString(CommonString.KEY_USERNAME, null);

        database = new HimalayaDb(this);
        database.open();
        new UploadTask(this).execute();
    }

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
        // database.close();
    }

    private class UploadTask extends AsyncTask<Void, Void, String> {
        private Context context;

        UploadTask(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();

            dialog = new Dialog(context);
            dialog.setContentView(R.layout.custom_upload);
            dialog.setTitle("Uploading Image");
            dialog.setCancelable(false);
            dialog.show();
            pb = (ProgressBar) dialog.findViewById(R.id.progressBar1);
            percentage = (TextView) dialog.findViewById(R.id.percentage);
            message = (TextView) dialog.findViewById(R.id.message);
        }

        @Override
        protected String doInBackground(Void... params) {
            // TODO Auto-generated method stub

            try {

                coverageBeanlist = database.getCoverageData(visit_date);
                if (coverageBeanlist.size() > 0) {
                    for (int i = 0; i < coverageBeanlist.size(); i++) {

                        status = coverageBeanlist.get(i).getStatus();
                        if (status.equals(CommonString.STORE_STATUS_LEAVE)) {

                            String path = coverageBeanlist.get(i).getImage();
                            if (path != null && !path.equals("")) {
                                result = UploadImage(path);
                            } else {
                                result = CommonString.KEY_SUCCESS;
                            }
                        }

                        assetInsertdata = database.getAssetUpload(coverageBeanlist.get(i).getStoreId());

                        if (assetInsertdata.size() > 0) {

                            for (int j = 0; j < assetInsertdata.size(); j++) {

                                if (assetInsertdata.get(j).getImg() != null
                                        && !assetInsertdata.get(j)
                                        .getImg().equals("")) {

                                    if (new File(CommonString.FILE_PATH + assetInsertdata.get(j).getImg()).exists()) {

                                        result = UploadAssetImage(assetInsertdata.get(j).getImg());

                                        if (!result
                                                .toString()
                                                .equalsIgnoreCase(
                                                        CommonString.KEY_SUCCESS)) {

                                            return "Asset Images";
                                        } else if (result
                                                .toString()
                                                .equalsIgnoreCase(
                                                        CommonString.KEY_FALSE)) {

                                            return "Asset Images";
                                        } else if (result
                                                .equalsIgnoreCase(CommonString.KEY_FAILURE)) {

                                            return "Asset Images"
                                                    + "," + errormsg;
                                        }

                                        runOnUiThread(new Runnable() {

                                            public void run() {


                                                message.setText("Asset Images Uploaded");
                                            }
                                        });


                                    }
                                }

                            }

                        }

                        promotionData = database.getPromotionUpload(coverageBeanlist.get(i).getStoreId());

                        if (promotionData.size() > 0) {

                            for (int j = 0; j < promotionData.size(); j++) {

                                if (promotionData.get(j).getImg() != null
                                        && !promotionData.get(j)
                                        .getImg().equals("")) {

                                    if (new File(CommonString.FILE_PATH + promotionData.get(j).getImg()).exists()) {

                                        result = UploadPromotionImage(promotionData.get(j).getImg());

                                        if (!result
                                                .toString()
                                                .equalsIgnoreCase(
                                                        CommonString.KEY_SUCCESS)) {

                                            return "Promotion Images";
                                        } else if (result
                                                .toString()
                                                .equalsIgnoreCase(
                                                        CommonString.KEY_FALSE)) {

                                            return "Promotion Images";
                                        } else if (result
                                                .equalsIgnoreCase(CommonString.KEY_FAILURE)) {

                                            return "Promotion Images"
                                                    + "," + errormsg;
                                        }

                                        runOnUiThread(new Runnable() {

                                            public void run() {


                                                message.setText("Promotion Images Uploaded");
                                            }
                                        });


                                    }

                                }

                            }

                        }

                        poiInsertdata = database.getPOIData(coverageBeanlist.get(i).getStoreId());

                        if (poiInsertdata.size() > 0) {

                            for (int j = 0; j < poiInsertdata.size(); j++) {

                                if (poiInsertdata.get(j).getImage() != null
                                        && !poiInsertdata.get(j)
                                        .getImage().equals("")) {

                                    if (new File(CommonString.FILE_PATH + poiInsertdata.get(j).getImage()).exists()) {

                                        result = UploadPOIImage(poiInsertdata.get(j).getImage());

                                        if (!result
                                                .toString()
                                                .equalsIgnoreCase(
                                                        CommonString.KEY_SUCCESS)) {

                                            return "POI Images";
                                        } else if (result
                                                .toString()
                                                .equalsIgnoreCase(
                                                        CommonString.KEY_FALSE)) {

                                            return "POI Images";
                                        } else if (result
                                                .equalsIgnoreCase(CommonString.KEY_FAILURE)) {

                                            return "POI Images"
                                                    + "," + errormsg;
                                        }

                                        runOnUiThread(new Runnable() {

                                            public void run() {

                                                message.setText("POI Images Uploaded");
                                            }
                                        });

                                    }

                                }

                            }

                        }

                        stockImgData = database.getHeaderStockImageData(coverageBeanlist.get(i).getStoreId(), visit_date);

                        if (stockImgData.size() > 0) {

                            for (int j = 0; j < stockImgData.size(); j++) {

                                if (stockImgData.get(j).getImg_cam() != null
                                        && !stockImgData.get(j)
                                        .getImg_cam().equals("")) {

                                    if (new File(
                                            CommonString.FILE_PATH + stockImgData.get(j).getImg_cam())
                                            .exists()) {

                                        result = UploadStockImage(stockImgData.get(j).getImg_cam());

                                        if (!result
                                                .toString()
                                                .equalsIgnoreCase(
                                                        CommonString.KEY_SUCCESS)) {

                                            return "Stock Images";
                                        } else if (result
                                                .toString()
                                                .equalsIgnoreCase(
                                                        CommonString.KEY_FALSE)) {

                                            return "Stock Images";
                                        } else if (result
                                                .equalsIgnoreCase(CommonString.KEY_FAILURE)) {

                                            return "Stock Images"
                                                    + "," + errormsg;
                                        }

                                        runOnUiThread(new Runnable() {

                                            public void run() {

                                                message.setText("Stock Images Uploaded");
                                            }
                                        });

                                    }

                                }

                            }

                        }

                        return CommonString.KEY_SUCCESS;
                    }
                }


            } catch (MalformedURLException e) {
                return CommonString.MESSAGE_EXCEPTION;
            } catch (IOException e) {
                return CommonString.MESSAGE_SOCKETEXCEPTION;
            } catch (Exception e) {
                return CommonString.MESSAGE_EXCEPTION;
            }

            return "";
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            dialog.dismiss();
            if (result.equals(CommonString.KEY_SUCCESS)) {
                database.open();
                database.deleteAllTables();
                AlertandMessages.showAlert((Activity) context, CommonString.MESSAGE_UPLOAD_IMAGE, true);
            } else if (!result.equals("")) {
                AlertandMessages.showAlert((Activity) context, result, true);
            }
        }


        public String UploadImage(String path) throws Exception {

            errormsg = "";
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(CommonString.FILE_PATH + path, o);

            // The new size we want to scale to
            final int REQUIRED_SIZE = 1024;

            // Find the correct scale value. It should be the power of 2.
            int width_tmp = o.outWidth, height_tmp = o.outHeight;
            int scale = 1;

            while (true) {
                if (width_tmp < REQUIRED_SIZE && height_tmp < REQUIRED_SIZE)
                    break;
                width_tmp /= 2;
                height_tmp /= 2;
                scale *= 2;
            }

            // Decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            Bitmap bitmap = BitmapFactory.decodeFile(
                    CommonString.FILE_PATH + path, o2);

            ByteArrayOutputStream bao = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, bao);
            byte[] ba = bao.toByteArray();
            String ba1 = Base64.encodeBytes(ba);

            SoapObject request = new SoapObject(CommonString.NAMESPACE,
                    CommonString.METHOD_UPLOAD_IMAGE);

            String[] split = path.split("/");
            String path1 = split[split.length - 1];

            request.addProperty("img", ba1);
            request.addProperty("name", path1);
            request.addProperty("FolderName", "StoreImages");

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);

            HttpTransportSE androidHttpTransport = new HttpTransportSE(
                    CommonString.URL);

            androidHttpTransport
                    .call(CommonString.SOAP_ACTION_UPLOAD_IMAGE,
                            envelope);
            Object result = (Object) envelope.getResponse();

            if (!result.toString().equalsIgnoreCase(CommonString.KEY_SUCCESS)) {

                if (result.toString().equalsIgnoreCase(CommonString.KEY_FALSE)) {
                    return CommonString.KEY_FALSE;
                }

                SAXParserFactory saxPF = SAXParserFactory.newInstance();
                SAXParser saxP = saxPF.newSAXParser();
                XMLReader xmlR = saxP.getXMLReader();

                // for failure
                FailureXMLHandler failureXMLHandler = new FailureXMLHandler();
                xmlR.setContentHandler(failureXMLHandler);

                InputSource is = new InputSource();
                is.setCharacterStream(new StringReader(result.toString()));
                xmlR.parse(is);

                failureGetterSetter = failureXMLHandler
                        .getFailureGetterSetter();

                if (failureGetterSetter.getStatus().equalsIgnoreCase(
                        CommonString.KEY_FAILURE)) {
                    errormsg = failureGetterSetter.getErrorMsg();
                    return CommonString.KEY_FAILURE;
                }
            } else {
                new File(CommonString.FILE_PATH + path).delete();
            }

            return "";
        }


        public String UploadPromotionImage(String path) throws Exception {

            errormsg = "";
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(CommonString.FILE_PATH + path, o);

            // The new size we want to scale to
            final int REQUIRED_SIZE = 1024;

            // Find the correct scale value. It should be the power of 2.
            int width_tmp = o.outWidth, height_tmp = o.outHeight;
            int scale = 1;

            while (true) {
                if (width_tmp < REQUIRED_SIZE && height_tmp < REQUIRED_SIZE)
                    break;
                width_tmp /= 2;
                height_tmp /= 2;
                scale *= 2;
            }

            // Decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            Bitmap bitmap = BitmapFactory.decodeFile(
                    CommonString.FILE_PATH + path, o2);

            ByteArrayOutputStream bao = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, bao);
            byte[] ba = bao.toByteArray();
            String ba1 = Base64.encodeBytes(ba);

            SoapObject request = new SoapObject(CommonString.NAMESPACE,
                    CommonString.METHOD_UPLOAD_IMAGE);

            String[] split = path.split("/");
            String path1 = split[split.length - 1];

            request.addProperty("img", ba1);
            request.addProperty("name", path1);
            request.addProperty("FolderName", "PromotionImages");

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);

            HttpTransportSE androidHttpTransport = new HttpTransportSE(
                    CommonString.URL);

            androidHttpTransport
                    .call(CommonString.SOAP_ACTION_UPLOAD_IMAGE,
                            envelope);
            Object result = (Object) envelope.getResponse();

            if (!result.toString().equalsIgnoreCase(CommonString.KEY_SUCCESS)) {

                if (result.toString().equalsIgnoreCase(CommonString.KEY_FALSE)) {
                    return CommonString.KEY_FALSE;
                }

                SAXParserFactory saxPF = SAXParserFactory.newInstance();
                SAXParser saxP = saxPF.newSAXParser();
                XMLReader xmlR = saxP.getXMLReader();

                // for failure
                FailureXMLHandler failureXMLHandler = new FailureXMLHandler();
                xmlR.setContentHandler(failureXMLHandler);

                InputSource is = new InputSource();
                is.setCharacterStream(new StringReader(result.toString()));
                xmlR.parse(is);

                failureGetterSetter = failureXMLHandler
                        .getFailureGetterSetter();

                if (failureGetterSetter.getStatus().equalsIgnoreCase(
                        CommonString.KEY_FAILURE)) {
                    errormsg = failureGetterSetter.getErrorMsg();
                    return CommonString.KEY_FAILURE;
                }
            } else {
                new File(CommonString.FILE_PATH + path).delete();
            }

            return "";
        }


        public String UploadAssetImage(String path) throws Exception {

            errormsg = "";
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(CommonString.FILE_PATH + path, o);

            // The new size we want to scale to
            final int REQUIRED_SIZE = 1024;

            // Find the correct scale value. It should be the power of 2.
            int width_tmp = o.outWidth, height_tmp = o.outHeight;
            int scale = 1;

            while (true) {
                if (width_tmp < REQUIRED_SIZE && height_tmp < REQUIRED_SIZE)
                    break;
                width_tmp /= 2;
                height_tmp /= 2;
                scale *= 2;
            }

            // Decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            Bitmap bitmap = BitmapFactory.decodeFile(
                    CommonString.FILE_PATH + path, o2);

            ByteArrayOutputStream bao = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, bao);
            byte[] ba = bao.toByteArray();
            String ba1 = Base64.encodeBytes(ba);

            SoapObject request = new SoapObject(CommonString.NAMESPACE,
                    CommonString.METHOD_UPLOAD_IMAGE);

            String[] split = path.split("/");
            String path1 = split[split.length - 1];

            request.addProperty("img", ba1);
            request.addProperty("name", path1);
            request.addProperty("FolderName", "AssetImages");

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);

            HttpTransportSE androidHttpTransport = new HttpTransportSE(
                    CommonString.URL);

            androidHttpTransport
                    .call(CommonString.SOAP_ACTION_UPLOAD_IMAGE,
                            envelope);
            Object result = (Object) envelope.getResponse();

            if (!result.toString().equalsIgnoreCase(CommonString.KEY_SUCCESS)) {

                if (result.toString().equalsIgnoreCase(CommonString.KEY_FALSE)) {
                    return CommonString.KEY_FALSE;
                }

                SAXParserFactory saxPF = SAXParserFactory.newInstance();
                SAXParser saxP = saxPF.newSAXParser();
                XMLReader xmlR = saxP.getXMLReader();

                // for failure
                FailureXMLHandler failureXMLHandler = new FailureXMLHandler();
                xmlR.setContentHandler(failureXMLHandler);

                InputSource is = new InputSource();
                is.setCharacterStream(new StringReader(result.toString()));
                xmlR.parse(is);

                failureGetterSetter = failureXMLHandler
                        .getFailureGetterSetter();

                if (failureGetterSetter.getStatus().equalsIgnoreCase(
                        CommonString.KEY_FAILURE)) {
                    errormsg = failureGetterSetter.getErrorMsg();
                    return CommonString.KEY_FAILURE;
                }
            } else {
                new File(CommonString.FILE_PATH + path).delete();
            }

            return "";
        }


        public String UploadPOIImage(String path) throws Exception {

            errormsg = "";
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(CommonString.FILE_PATH + path, o);

            // The new size we want to scale to
            final int REQUIRED_SIZE = 1024;

            // Find the correct scale value. It should be the power of 2.
            int width_tmp = o.outWidth, height_tmp = o.outHeight;
            int scale = 1;

            while (true) {
                if (width_tmp < REQUIRED_SIZE && height_tmp < REQUIRED_SIZE)
                    break;
                width_tmp /= 2;
                height_tmp /= 2;
                scale *= 2;
            }

            // Decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            Bitmap bitmap = BitmapFactory.decodeFile(
                    CommonString.FILE_PATH + path, o2);

            ByteArrayOutputStream bao = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, bao);
            byte[] ba = bao.toByteArray();
            String ba1 = Base64.encodeBytes(ba);

            SoapObject request = new SoapObject(CommonString.NAMESPACE,
                    CommonString.METHOD_UPLOAD_IMAGE);

            String[] split = path.split("/");
            String path1 = split[split.length - 1];

            request.addProperty("img", ba1);
            request.addProperty("name", path1);
            request.addProperty("FolderName", "POIImages");

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);

            HttpTransportSE androidHttpTransport = new HttpTransportSE(
                    CommonString.URL);

            androidHttpTransport
                    .call(CommonString.SOAP_ACTION_UPLOAD_IMAGE,
                            envelope);
            Object result = (Object) envelope.getResponse();

            if (!result.toString().equalsIgnoreCase(CommonString.KEY_SUCCESS)) {

                if (result.toString().equalsIgnoreCase(CommonString.KEY_FALSE)) {
                    return CommonString.KEY_FALSE;
                }

                SAXParserFactory saxPF = SAXParserFactory.newInstance();
                SAXParser saxP = saxPF.newSAXParser();
                XMLReader xmlR = saxP.getXMLReader();

                // for failure
                FailureXMLHandler failureXMLHandler = new FailureXMLHandler();
                xmlR.setContentHandler(failureXMLHandler);

                InputSource is = new InputSource();
                is.setCharacterStream(new StringReader(result.toString()));
                xmlR.parse(is);

                failureGetterSetter = failureXMLHandler
                        .getFailureGetterSetter();

                if (failureGetterSetter.getStatus().equalsIgnoreCase(
                        CommonString.KEY_FAILURE)) {
                    errormsg = failureGetterSetter.getErrorMsg();
                    return CommonString.KEY_FAILURE;
                }
            } else {
                new File(CommonString.FILE_PATH + path).delete();
            }

            return "";
        }


        public String UploadStockImage(String path) throws Exception {

            errormsg = "";
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(CommonString.FILE_PATH + path, o);

            // The new size we want to scale to
            final int REQUIRED_SIZE = 1024;

            // Find the correct scale value. It should be the power of 2.
            int width_tmp = o.outWidth, height_tmp = o.outHeight;
            int scale = 1;

            while (true) {
                if (width_tmp < REQUIRED_SIZE && height_tmp < REQUIRED_SIZE)
                    break;
                width_tmp /= 2;
                height_tmp /= 2;
                scale *= 2;
            }

            // Decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            Bitmap bitmap = BitmapFactory.decodeFile(
                    CommonString.FILE_PATH + path, o2);

            ByteArrayOutputStream bao = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, bao);
            byte[] ba = bao.toByteArray();
            String ba1 = Base64.encodeBytes(ba);

            SoapObject request = new SoapObject(CommonString.NAMESPACE,
                    CommonString.METHOD_UPLOAD_IMAGE);

            String[] split = path.split("/");
            String path1 = split[split.length - 1];

            request.addProperty("img", ba1);
            request.addProperty("name", path1);
            request.addProperty("FolderName", "StockImages");

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);

            HttpTransportSE androidHttpTransport = new HttpTransportSE(
                    CommonString.URL);

            androidHttpTransport
                    .call(CommonString.SOAP_ACTION_UPLOAD_IMAGE,
                            envelope);
            Object result = (Object) envelope.getResponse();

            if (!result.toString().equalsIgnoreCase(CommonString.KEY_SUCCESS)) {

                if (result.toString().equalsIgnoreCase(CommonString.KEY_FALSE)) {
                    return CommonString.KEY_FALSE;
                }

                SAXParserFactory saxPF = SAXParserFactory.newInstance();
                SAXParser saxP = saxPF.newSAXParser();
                XMLReader xmlR = saxP.getXMLReader();

                // for failure
                FailureXMLHandler failureXMLHandler = new FailureXMLHandler();
                xmlR.setContentHandler(failureXMLHandler);

                InputSource is = new InputSource();
                is.setCharacterStream(new StringReader(result.toString()));
                xmlR.parse(is);

                failureGetterSetter = failureXMLHandler
                        .getFailureGetterSetter();

                if (failureGetterSetter.getStatus().equalsIgnoreCase(
                        CommonString.KEY_FAILURE)) {
                    errormsg = failureGetterSetter.getErrorMsg();
                    return CommonString.KEY_FAILURE;
                }
            } else {
                new File(CommonString.FILE_PATH + path).delete();
            }

            return "";
        }


    }


}
