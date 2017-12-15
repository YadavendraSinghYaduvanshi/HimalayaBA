package com.yadu.himalayamtnew.dailyentry;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;


import com.yadu.himalayamtnew.R;
import com.yadu.himalayamtnew.constants.CommonFunctions;
import com.yadu.himalayamtnew.constants.CommonString;
import com.yadu.himalayamtnew.database.HimalayaDb;
import com.yadu.himalayamtnew.delegates.CoverageBean;
import com.yadu.himalayamtnew.xmlGetterSetter.JourneyPlanGetterSetter;
import com.yadu.himalayamtnew.xmlGetterSetter.NonWorkingReasonGetterSetter;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class NonWorkingReason extends AppCompatActivity implements AdapterView.OnItemSelectedListener,
        View.OnClickListener {
    private Spinner reasonspinner;
    private HimalayaDb database;
    String reasonname, reasonid, entry_allow, image, entry, _path, reason_reamrk, intime;
    Button save;
    protected String str;
    protected String _pathforcheck = "";
    private String image1 = "";
    private SharedPreferences preferences;
    String _UserId, visit_date, store_id;
    EditText text;
    AlertDialog alert;
    ImageButton camera;
    RelativeLayout reason_lay, rel_cam;
    private ArrayAdapter<CharSequence> reason_adapter;
    ArrayList<NonWorkingReasonGetterSetter> reasondata = new ArrayList<>();
    ArrayList<JourneyPlanGetterSetter> jcp;
    //jeevan
    File saveDir = null;
    private final static int CAMERA_RQ = 6969;
    private SharedPreferences.Editor editor = null;
    boolean flag_deviation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_non_working_reason);
        reasonspinner = (Spinner) findViewById(R.id.spinner2);
        camera = (ImageButton) findViewById(R.id.imgcam);
        save = (Button) findViewById(R.id.save);
        text = (EditText) findViewById(R.id.reasontxt);
        reason_lay = (RelativeLayout) findViewById(R.id.layout_reason);
        rel_cam = (RelativeLayout) findViewById(R.id.relimgcam);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        _UserId = preferences.getString(CommonString.KEY_USERNAME, "");
        visit_date = preferences.getString(CommonString.KEY_DATE, null);
        store_id = preferences.getString(CommonString.KEY_STORE_CD, "");
        getSupportActionBar().setTitle("Non Working -" + visit_date);
        flag_deviation = getIntent().getBooleanExtra(CommonString.KEY_PJP_DEVIATION, false);

        database = new HimalayaDb(this);
        database.open();
        str = CommonString.FILE_PATH;
        jcp = database.getJCPData(visit_date);
        if (jcp.size() > 0) {
            try {
                for (int i = 0; i < jcp.size(); i++) {
                    boolean flag = false;
                    if (jcp.get(i).getUploadStatus().get(0).equals(CommonString.KEY_U) ||
                            jcp.get(i).getUploadStatus().get(0).equals(CommonString.KEY_D)
                            || jcp.get(i).getUploadStatus().get(0).equals(CommonString.STORE_STATUS_LEAVE)) {
                        flag = true;
                        reasondata.clear();
                        reasondata = database.getNonWorkingDataByFlag(flag);
                        break;
                    } else {
                        reasondata = database.getNonWorkingDataByFlag(flag);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        reason_adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        reason_adapter.add("Select Reason");
        for (int i = 0; i < reasondata.size(); i++) {
            reason_adapter.add(reasondata.get(i).getReason().get(0));
        }

        reasonspinner.setAdapter(reason_adapter);
        reason_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        reasonspinner.setOnItemSelectedListener(this);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
            saveDir = new File(CommonString.FILE_PATH);
            saveDir.mkdirs();
        }

        intime = getCurrentTime();
        camera.setOnClickListener(this);
        save.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        finish();
        overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
    }

    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
        // TODO Auto-generated method stub

        switch (arg0.getId()) {
            case R.id.spinner2:
                if (position != 0) {
                    reasonname = reasondata.get(position - 1).getReason().get(0);
                    reasonid = reasondata.get(position - 1).getReason_cd().get(0);
                    entry_allow = reasondata.get(position - 1).getEntry_allow().get(0);
                    if (reasonname.equalsIgnoreCase("Store Closed")) {
                        rel_cam.setVisibility(View.VISIBLE);
                        image = "true";
                    } else {
                        rel_cam.setVisibility(View.GONE);
                        image = "false";
                    }
                    reason_reamrk = "true";
                    if (reason_reamrk.equalsIgnoreCase("true")) {
                        reason_lay.setVisibility(View.VISIBLE);
                    } else {
                        reason_lay.setVisibility(View.GONE);
                    }
                } else {
                    reasonname = "";
                    reasonid = "";
                }
                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }


    @SuppressWarnings("deprecation")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i("MakeMachine", "resultCode: " + resultCode);
        switch (resultCode) {
            case 0:
                Log.i("MakeMachine", "User cancelled");
                break;
            case -1:
                if (_pathforcheck != null && !_pathforcheck.equals("")) {
                    if (new File(str + _pathforcheck).exists()) {
                        camera.setImageDrawable(getResources().getDrawable(R.drawable.camera_list_tick));
                        image1 = _pathforcheck;
                        _pathforcheck = "";
                    }
                }

                break;
        }
    }

    /* @SuppressWarnings("deprecation")
     @Override
     protected void onActivityResult(int requestCode, int resultCode, Intent data) {
         Log.i("MakeMachine", "resultCode: " + resultCode);
         switch (resultCode) {
             case 0:
                 Log.i("MakeMachine", "User cancelled");
                 break;
             case -1:
                 if (requestCode == CAMERA_RQ) {
                     if (resultCode == RESULT_OK) {
                         File file = new File(data.getData().getPath());
                         _pathforcheck = file.getName();
                         camera.setImageDrawable(getResources().getDrawable(R.drawable.camera_list_tick));
                         image1 = _pathforcheck;
                     }
                 }


                 break;
         }

     }
 */
    public boolean imageAllowed() {
        boolean result = true;
        if (image.equalsIgnoreCase("true")) {
            if (image1.equalsIgnoreCase("")) {
                result = false;
            }
        }
        return result;
    }

    public boolean textAllowed() {
        boolean result = true;
        return result;
    }

    @Override
    public void onClick(View v) {
       /* MaterialCamera materialCamera = new MaterialCamera(this)
                .saveDir(saveDir)
                .showPortraitWarning(true)
                .allowRetry(true)
                .defaultToFrontFacing(false)
                .allowRetry(true)
                .autoSubmit(false)
                .labelConfirm(R.string.mcam_use_video);*/
        if (v.getId() == R.id.imgcam) {
            _pathforcheck = store_id + "_NonWorking_" + _UserId + ".jpg";
            _path = CommonString.FILE_PATH + _pathforcheck;
            startCameraActivity();
          //  CommonFunctions.startCameraActivity(this, _path);

            //  editor = preferences.edit();
           /* editor.putString("imagename", _pathforcheck);
            editor.commit();
            materialCamera.stillShot().labelConfirm(R.string.mcam_use_stillshot);
            materialCamera.start(CAMERA_RQ);*/
        }
        if (v.getId() == R.id.save) {

            if (validatedata()) {

                if (imageAllowed()) {

                    if (textAllowed()) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(NonWorkingReason.this);
                        builder.setMessage("Do you want to save the data ")
                                .setCancelable(false)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(
                                            DialogInterface dialog,
                                            int id) {
                                        alert.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
                                        if (entry_allow.equals("0")) {
                                            database.deleteAllTables();

                                            //jeevan
                                            if (database.getJCPData(visit_date).size() > 0) {
                                                jcp = database.getJCPData(visit_date);
                                            } else {
                                                jcp = database.getPJPDeviationData(visit_date);
                                            }

                                            for (int i = 0; i < jcp.size(); i++) {
                                                String stoteid = jcp.get(i).getStore_cd().get(0);
                                                CoverageBean cdata = new CoverageBean();
                                                cdata.setStoreId(stoteid);
                                                cdata.setVisitDate(visit_date);
                                                cdata.setUserId(_UserId);
                                                cdata.setInTime(intime);
                                                cdata.setOutTime(getCurrentTime());
                                                cdata.setReason(reasonname);
                                                cdata.setReasonid(reasonid);
                                                cdata.setLatitude("0.0");
                                                cdata.setLongitude("0.0");
                                                cdata.setImage(image1);
                                                cdata.setPJPDeviation(flag_deviation);
                                                cdata.setRemark(text.getText().
                                                        toString().replaceAll("[&^<>{}'$]", " "));
                                                cdata.setStatus(CommonString.STORE_STATUS_LEAVE);

                                                database.InsertCoverageData(cdata);
                                                if (flag_deviation) {
                                                    database.InsertPJPDeviationData(store_id, visit_date);
                                                    database.updatePJPStoreStatus(store_id,
                                                            visit_date, CommonString.STORE_STATUS_LEAVE);
                                                } else {
                                                    database.updateStoreStatusOnLeave(store_id,
                                                            visit_date, CommonString.STORE_STATUS_LEAVE);
                                                }

                                                SharedPreferences.Editor editor = preferences.edit();
                                                editor.putString(CommonString.KEY_STOREVISITED_STATUS + stoteid, "No");
                                                editor.putString(CommonString.KEY_STOREVISITED_STATUS, "");
                                                editor.commit();
                                            }

                                        } else {
                                            CoverageBean cdata = new CoverageBean();
                                            cdata.setStoreId(store_id);
                                            cdata.setVisitDate(visit_date);
                                            cdata.setUserId(_UserId);
                                            cdata.setInTime(intime);
                                            cdata.setOutTime(getCurrentTime());
                                            cdata.setReason(reasonname);
                                            cdata.setReasonid(reasonid);
                                            cdata.setLatitude("0.0");
                                            cdata.setLongitude("0.0");
                                            cdata.setImage(image1);
                                            cdata.setPJPDeviation(flag_deviation);

                                            cdata.setRemark(text.getText().toString().replaceAll("[&^<>{}'$]", " "));
                                            cdata.setStatus(CommonString.STORE_STATUS_LEAVE);
                                            database.InsertCoverageData(cdata);
//jeevan

                                            if (flag_deviation) {
                                                database.InsertPJPDeviationData(store_id, visit_date);
                                                database.updatePJPStoreStatus(store_id,
                                                        visit_date, CommonString.STORE_STATUS_LEAVE);
                                            } else {
                                                database.updateStoreStatusOnLeave(store_id,
                                                        visit_date, CommonString.STORE_STATUS_LEAVE);
                                            }


                                            SharedPreferences.Editor editor = preferences.edit();
                                            editor.putString(CommonString.KEY_STOREVISITED_STATUS + store_id, "No");
                                            editor.putString(CommonString.KEY_STOREVISITED_STATUS, "");
                                            editor.commit();
                                        }
                                        finish();
                                    }
                                })
                                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    public void onClick(
                                            DialogInterface dialog,
                                            int id) {
                                        dialog.cancel();
                                    }
                                });

                        alert = builder.create();
                        alert.show();
                    } else {
                        Toast.makeText(getApplicationContext(),
                                "Please enter required remark reason", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Please Capture Image", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getApplicationContext(),
                        "Please Select a Reason", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public boolean validatedata() {
        boolean result = false;
        if (reasonid != null && !reasonid.equalsIgnoreCase("")) {
            result = true;
        }

        return result;

    }

    public String getCurrentTime() {

        Calendar m_cal = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        String cdate = formatter.format(m_cal.getTime());
        return cdate;

    }

    protected void startCameraActivity() {
        try {
            Log.i("MakeMachine", "startCameraActivity()");
            File file = new File(_path);
            Uri outputFileUri = Uri.fromFile(file);
            Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
            startActivityForResult(intent, 0);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
