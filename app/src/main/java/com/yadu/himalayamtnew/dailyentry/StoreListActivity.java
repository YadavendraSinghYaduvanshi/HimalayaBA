package com.yadu.himalayamtnew.dailyentry;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.yadu.himalayamtnew.R;
import com.yadu.himalayamtnew.constants.AlertandMessages;
import com.yadu.himalayamtnew.constants.CommonString;
import com.yadu.himalayamtnew.database.HimalayaDb;
import com.yadu.himalayamtnew.delegates.CoverageBean;
import com.yadu.himalayamtnew.xmlGetterSetter.JourneyPlanGetterSetter;

import java.util.ArrayList;

import static com.yadu.himalayamtnew.constants.CommonFunctions.getCurrentTime;

public class StoreListActivity extends AppCompatActivity implements LocationListener {

    RecyclerView recyclerView;
    LinearLayout parent_linear, nodata_linear;
    FloatingActionButton fab_button;
    HimalayaDb database;
    SharedPreferences preferences;
    String date,store_intime;
    ArrayList<CoverageBean> coverage;
    boolean flag_deviation = false;
    ArrayList<JourneyPlanGetterSetter> jcplist;
    RecyclerAdapter adapter;
    Context context;
    public static String currLatitude = "0.0";
    public static String currLongitude = "0.0";
    boolean result_flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_list);
        declaration();

        fab_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        setListdata();
    }

    public void setListdata() {

        coverage = database.getCoverageData(date);
        if (database.isSkuMasterDownloaded()) {
            if (isPJPDeviationInvalid()) {

                showPJPDeviationStoreList();
            } else {

                fab_button.setVisibility(View.VISIBLE);
                setTitle("Store List " + date);

                jcplist = database.getJCPData(date);

                if (jcplist.size() > 0) {
                    //setCheckOutData();
                    adapter = new RecyclerAdapter(context, jcplist);
                    recyclerView.setAdapter(adapter);
                    flag_deviation = false;

                } else {
                    recyclerView.setVisibility(View.GONE);
                    parent_linear.setBackgroundColor((getResources().getColor(R.color.grey_light)));
                    nodata_linear.setVisibility(View.VISIBLE);
                }
            }
        } else {
            recyclerView.setVisibility(View.GONE);
            parent_linear.setBackgroundColor((getResources().getColor(R.color.grey_light)));
            nodata_linear.setVisibility(View.VISIBLE);
        }

    }


/*
    public void setCheckOutData() {
        for (int i = 0; i < jcplist.size(); i++) {
            String storeCd = jcplist.get(i).getStore_cd().get(0);

            if (!jcplist.get(i).getCheckOutStatus().get(0).equals(CommonString.KEY_C)
                    && !jcplist.get(i).getCheckOutStatus().get(0).equals(CommonString.KEY_VALID)) {

                boolean flag = true;

                if (database.getStockAvailabilityData(storeCd).size() > 0) {
                    if (database.isOpeningDataFilled(storeCd)) {
                        flag = true;
                    } else {
                        flag = false;
                    }
                }

                if (flag) {
                    if (database.getPromotionBrandData(storeCd).size() > 0) {
                        if (database.isPromotionDataFilled(storeCd)) {
                            flag = true;
                        } else {
                            flag = false;
                        }
                    }
                }

                if (flag) {
                    if (database.isStoreAssetDataFilled(storeCd)) {
                        if (database.getAssetCategoryData(storeCd).size() > 0) {
                            if (database.isAssetDataFilled(storeCd)) {
                                flag = true;
                            } else {
                                flag = false;
                            }
                        }
                    }
                }

                if (flag) {

                    ArrayList<Audit_QuestionGetterSetter> cat_list = database.getCategoryQuestionData();

                    boolean audit_flag = true;

                    for (int j = 0; j < cat_list.size(); j++) {
                        if (database.getAuditQuestionData(cat_list.get(j).getCATEGORY_ID().get(0)).size() > 0) {
                            if (!database.isAuditDataFilled(storeCd, cat_list.get(j).getCATEGORY_ID().get(0))) {
                                audit_flag = false;
                                break;
                            }
                        }

                    }
                    if (audit_flag) {
                        flag = true;
                    } else {
                        flag = false;
                    }

                }

                if (flag) {
                    if (user_type.equals("Promoter")) {
                        if (database.isMiddayDataFilled(storeCd) && database.isClosingDataFilled(storeCd)) {
                            flag = true;
                        } else {
                            flag = false;
                        }
                    }
                }


                if (flag) {

                    database.updateCoverageStatusNew(storeCd,CommonString.KEY_VALID);

                    if(flag_deviation){
                        database.updateDeviationStoreStatusOnCheckout(storeCd, date, CommonString.KEY_VALID);
                        jcplist = database.getPJPDeviationData(date);
                    }
                    else{
                        database.updateStoreStatusOnCheckout(storeCd, date, CommonString.KEY_VALID);
                        jcplist = database.getJCPData(date);
                    }

                }
                //}

                //<editor-fold desc="Previous Code">
                */
/*if (database.isOpeningDataFilled(storeCd) && database.getFacingCompetitorData(storeCd).size() > 0
                        && database.getPOIData(storeCd).size() > 0 && database.getCompetitionPOIData(storeCd).size() > 0
                        && database.getCompetitionPromotionData(storeCd).size() > 0) {

                    boolean flag = true;

                    if (database.getPromotionBrandData(storeCd).size() > 0) {
                        if (database.isPromotionDataFilled(storeCd)) {
                            flag = true;
                        } else {
                            flag = false;
                        }
                    }

                    if (flag) {
                        if (user_type.equals("Promoter")) {
                            if (database.isClosingDataFilled(storeCd) && database.isMiddayDataFilled(storeCd)) {
                                flag = true;
                            } else {
                                flag = false;
                            }
                        }
                    }

                    if (flag) {
                        if (database.getAssetCategoryData(storeCd).size() > 0) {
                            if (database.isAssetDataFilled(storeCd)) {
                                flag = true;
                            } else {
                                flag = false;
                            }
                        }
                    }

                    if (flag) {
                        database.updateStoreStatusOnCheckout(storeCd, date, CommonString.KEY_VALID);
                        jcplist = database.getJCPData(date);
                    }
                }*//*

                //</editor-fold>
            }
        }
    }
*/


    public boolean isPJPDeviationInvalid() {
        boolean flag_is_invalid = false;

        for (int i = 0; i < coverage.size(); i++) {

            if (coverage.get(i).getStatus().equals(CommonString.KEY_INVALID) || coverage.get(i).getStatus().equals(CommonString.KEY_VALID)) {
                if (coverage.get(i).isPJPDeviation())
                    flag_is_invalid = true;

                break;
            }
        }

        return flag_is_invalid;
    }

    public void showPJPDeviationStoreList() {
        flag_deviation = true;
        jcplist = database.getPJPDeviationData(date);
        //setCheckOutData();
        adapter = new RecyclerAdapter(context, jcplist);
        recyclerView.setAdapter(adapter);
        recyclerView.setVisibility(View.VISIBLE);

        setTitle("PJP Deviation " + date);
        fab_button.setVisibility(View.GONE);
        nodata_linear.setVisibility(View.GONE);
    }


    class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
        ArrayList<JourneyPlanGetterSetter> jcplist;
        Context context;
        LayoutInflater inflator;

        RecyclerAdapter(Context context, ArrayList<JourneyPlanGetterSetter> jcplist) {
            this.context = context;
            this.jcplist = jcplist;
            inflator = LayoutInflater.from(context);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = inflator.inflate(R.layout.storelistrow, parent);
            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {

            String storecd = jcplist.get(position).getStore_cd().get(0);
            ArrayList<CoverageBean> coveragespecific = database.getCoverageSpecificData(storecd);

            if (jcplist.get(position).getUploadStatus().get(0).equals(CommonString.KEY_U)) {

                holder.img.setVisibility(View.VISIBLE);
                holder.img.setBackgroundResource(R.drawable.tick_u);
                holder.checkout.setVisibility(View.INVISIBLE);

            } else if (coveragespecific.size() > 0 && coveragespecific.get(0).getStatus().equalsIgnoreCase(CommonString.STORE_STATUS_LEAVE)) {
                holder.img.setBackgroundResource(R.drawable.leave_tick);
                holder.checkout.setVisibility(View.INVISIBLE);

            } else if ((jcplist.get(position).getCheckOutStatus().get(0).equals(CommonString.KEY_C))) {

                holder.checkinclose.setBackgroundResource(R.drawable.tick_c);
                holder.checkinclose.setVisibility(View.VISIBLE);
                holder.checkout.setVisibility(View.INVISIBLE);

            } else if (coveragespecific.size() > 0 && coveragespecific.get(0).getStatus().equalsIgnoreCase(CommonString.KEY_VALID)) {

                holder.checkout.setBackgroundResource(R.drawable.checkout);
                holder.checkout.setVisibility(View.VISIBLE);
                holder.checkout.setEnabled(true);

            } else if (coveragespecific.size() > 0 && coveragespecific.get(0).getStatus().equalsIgnoreCase(CommonString.KEY_INVALID)) {

                holder.checkout.setVisibility(View.INVISIBLE);
                holder.checkinclose.setBackgroundResource(R.drawable.checkin_ico);
                holder.checkinclose.setVisibility(View.VISIBLE);
            } else {

                holder.checkout.setEnabled(false);
                holder.checkout.setVisibility(View.INVISIBLE);
                holder.checkinclose.setEnabled(false);
                holder.checkinclose.setVisibility(View.INVISIBLE);
            }
            holder.storename.setText(jcplist.get(position).getStore_name().get(0));
            holder.city.setText(jcplist.get(position).getCity().get(0));
            holder.keyaccount.setText(jcplist.get(position).getKey_account().get(0));


            holder.checkout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Are you sure you want to Checkout")
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(
                                        DialogInterface dialog, int id) {

                                /*    if (CheckNetAvailability()) {
                                        editor = preferences.edit();
                                        editor.putString(CommonString.KEY_STORE_CD, jcplist.get(position).getStore_cd().get(0));
                                        editor.putString(CommonString.KEY_STORE_NAME, jcplist.get(position).getStore_name().get(0));
                                        editor.commit();

                                        Intent i = new Intent(DailyEntryScreen.this, CheckOutStoreActivity.class);
                                        i.putExtra(CommonString.KEY_PJP_DEVIATION, flag_deviation);
                                        startActivity(i);
                                    } else {
                                        Snackbar.make(lv, "No Network", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
                                        //Toast.makeText(DailyEntryScreen.this, "No Network", Toast.LENGTH_SHORT).show();
                                    }*/

                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                public void onClick(
                                        DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            });

            holder.listitem_ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setListData(position);
                }
            });

        }

        @Override
        public int getItemCount() {
            return 0;
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView storename, city, keyaccount;
            ImageView img, checkinclose;
            Button checkout;
            LinearLayout listitem_ll;

            ViewHolder(View convertView) {
                super(convertView);
                storename = (TextView) convertView.findViewById(R.id.tvstorename);
                city = (TextView) convertView.findViewById(R.id.tvcity);
                keyaccount = (TextView) convertView.findViewById(R.id.tvkeyaccount);
                img = (ImageView) convertView.findViewById(R.id.img);
                checkout = (Button) convertView.findViewById(R.id.chkout);
                checkinclose = (ImageView) convertView.findViewById(R.id.closechkin);
                listitem_ll = (LinearLayout) convertView.findViewById(R.id.listitem_ll);
            }
        }


    }


    void setListData(int position) {

        final String store_cd = jcplist.get(position).getStore_cd().get(0);
        final String upload_status = jcplist.get(position).getUploadStatus().get(0);
        final String checkoutstatus = jcplist.get(position).getCheckOutStatus().get(0);

        if (upload_status.equals(CommonString.KEY_U)) {
            AlertandMessages.showSnackbarMsg(context, "All Data Uploaded");

        } else if (((checkoutstatus.equals(CommonString.KEY_C)))) {
            AlertandMessages.showSnackbarMsg(context, "Store already checked out");

        } else if (isStoreCoverageLeave(store_cd)) {

            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
            builder1.setTitle("Parinaam");
            builder1.setMessage("Want to enter store, it is already closed. \nData will be deleted.")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            ArrayList<CoverageBean> coveragespecific = database.getCoverageSpecificData(store_cd);
                            String reason_cd = coveragespecific.get(0).getReasonid();
                            String entry_allow = database.getNonEntryAllowReasonData(reason_cd);

                            if (entry_allow.equals("0")) {
                                database.deleteAllCoverage();
                                setListdata();
                            } else {
                                database.deleteSpecificCoverage(store_cd);
                                setListdata();
                            }

                        }
                    })
                    .setNegativeButton("Cancel",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    dialog.cancel();

                                }
                            });
            AlertDialog alert = builder1.create();
            alert.show();

        } else {
            if (!setcheckedmenthod(store_cd)) {
                boolean enteryflag = true;

                if (coverage.size() > 0) {
                    int i;
                    for (i = 0; i < coverage.size(); i++) {
                        if (coverage.get(i).getInTime() != null) {

                            if (coverage.get(i).getOutTime() == null) {
                                if (!store_cd.equals(coverage.get(i).getStoreId())) {
                                    AlertandMessages.showSnackbarMsg(context, "Please checkout from current store");
                                    enteryflag = false;
                                }
                                break;
                            }
                        }
                    }
                }

                if (enteryflag) {
                    showMyDialog(store_cd, jcplist.get(position).getStore_name().get(0), "Yes",
                            jcplist.get(position).getVISIT_DATE().get(0),
                            jcplist.get(position).getCheckOutStatus().get(0));
                }
            } else {
                AlertandMessages.showSnackbarMsg(context, "Data already filled");
            }
        }

    }

    public boolean isStoreCoverageLeave(String store_cd) {
        boolean result = false;
        for (int i = 0; i < coverage.size(); i++) {

            if (store_cd.equals(coverage.get(i).getStoreId())) {

                if (coverage.get(i).getStatus().equalsIgnoreCase(CommonString.STORE_STATUS_LEAVE)) {
                    result = true;
                    break;
                }
            } else {
                result = false;
            }
        }
        return result;
    }

    public boolean setcheckedmenthod(String store_cd) {

        for (int i = 0; i < coverage.size(); i++) {
            if (store_cd.equals(coverage.get(i).getStoreId())) {
                if (coverage.get(i).getOutTime() != null) {
                    result_flag = true;
                    break;
                }
            } else {
                result_flag = false;

            }
        }

        return result_flag;
    }


    void showMyDialog(final String storeCd, final String storeName, final String status,
                      final String visitDate, final String checkout_status) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialogbox);

        RadioGroup radioGroup = (RadioGroup) dialog.findViewById(R.id.radiogrpvisit);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // find which radio button is selected
                if (checkedId == R.id.yes) {

                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString(CommonString.KEY_STOREVISITED, storeCd);
                    editor.putString(CommonString.KEY_STOREVISITED_STATUS, "Yes");
                    editor.putString(CommonString.KEY_LATITUDE, currLatitude);
                    editor.putString(CommonString.KEY_LONGITUDE, currLongitude);
                    editor.putString(CommonString.KEY_STORE_NAME, storeName);
                    editor.putString(CommonString.KEY_STORE_CD, storeCd);

                    if (!visitDate.equals("")) {
                        editor.putString(CommonString.KEY_VISIT_DATE, visitDate);
                    }

                    if (status.equals("Yes")) {
                        editor.putString(CommonString.KEY_STOREVISITED_STATUS, "Yes");
                    }

                    //database.updateStoreStatusOnCheckout(storeCd, date, CommonString.KEY_INVALID);

                    editor.commit();

                    if (store_intime.equalsIgnoreCase("")) {
                        editor = preferences.edit();
                        editor.putString(CommonString.KEY_STORE_IN_TIME, getCurrentTime());
                        editor.putString(CommonString.KEY_STOREVISITED_STATUS, "Yes");
                        //editor.putString(CommonString.KEY_STOREVISITED, store_id);
                        editor.commit();
                    }
                    dialog.cancel();


                    boolean flag = true;

                    if (coverage.size() > 0) {
                        for (int i = 0; i < coverage.size(); i++) {
                            if (storeCd.equals(coverage.get(i).getStoreId())) {
                                flag = false;
                                break;
                            }
                        }
                    }

                    if (flag == true) {
                       // Intent in = new Intent(DailyEntryScreen.this, StoreImageActivity.class);
                       // in.putExtra(CommonString.KEY_PJP_DEVIATION, flag_deviation);
                       // startActivity(in);
                      //  overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
                    } else {
                       // Intent in = new Intent(DailyEntryScreen.this, StoreEntry.class);
                       // startActivity(in);
                       // overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
                    }

                    /*Intent in = new Intent(DailyEntryScreen.this, StoreImageActivity.class);
                    startActivity(in);
                    overridePendingTransition(R.anim.activity_in, R.anim.activity_out);*/
                    //finish();

                } else if (checkedId == R.id.no) {
                    /*Toast.makeText(getApplicationContext(), "choice: No",Toast.LENGTH_SHORT).show();*/
                    dialog.cancel();

                    if (checkout_status.equals(CommonString.KEY_INVALID) || checkout_status.equals(CommonString.KEY_VALID)) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setMessage(CommonString.DATA_DELETE_ALERT_MESSAGE)
                                .setCancelable(false)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {

                                      //  UpdateData(storeCd);

                                        SharedPreferences.Editor editor = preferences.edit();
                                        editor.putString(CommonString.KEY_STORE_CD, storeCd);
                                        editor.putString(CommonString.KEY_STORE_IN_TIME, "");
                                        editor.putString(CommonString.KEY_STOREVISITED, "");
                                        editor.putString(CommonString.KEY_STOREVISITED_STATUS, "");
                                        editor.putString(CommonString.KEY_LATITUDE, "");
                                        editor.putString(CommonString.KEY_LONGITUDE, "");
                                        editor.commit();


                                       // Intent in = new Intent(DailyEntryScreen.this, NonWorkingReason.class);
                                        //startActivity(in);
                                    }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int id) {
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog alert = builder.create();
                        alert.show();
                    } else {
                       // UpdateData(storeCd);

                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString(CommonString.KEY_STORE_CD, storeCd);
                        editor.putString(CommonString.KEY_STORE_IN_TIME, "");
                        editor.putString(CommonString.KEY_STOREVISITED, "");
                        editor.putString(CommonString.KEY_STOREVISITED_STATUS, "");
                        editor.putString(CommonString.KEY_LATITUDE, "");
                        editor.putString(CommonString.KEY_LONGITUDE, "");
                        editor.commit();

                       // Intent in = new Intent(DailyEntryScreen.this, NonWorkingReason.class);
                      //  startActivity(in);
                    }
                    //finish();
                }
            }

        });

        dialog.show();
    }

    @Override
    public void onLocationChanged(Location location) {
        // TODO Auto-generated method stub
        currLatitude = Double.toString(location.getLatitude());
        currLongitude = Double.toString(location.getLongitude());
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    void declaration() {
        recyclerView = (RecyclerView) findViewById(R.id.list);
        nodata_linear = (LinearLayout) findViewById(R.id.no_data_lay);
        parent_linear = (LinearLayout) findViewById(R.id.parent_linear);
        fab_button = (FloatingActionButton) findViewById(R.id.fab);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        date = preferences.getString(CommonString.KEY_DATE, null);
        store_intime = preferences.getString(CommonString.KEY_STORE_IN_TIME, "");
        context = this;
        setTitle("Store List - " + date);
        getSupportActionBar().setHomeButtonEnabled(true);
        database = new HimalayaDb(this);
        database.open();

    }
}
