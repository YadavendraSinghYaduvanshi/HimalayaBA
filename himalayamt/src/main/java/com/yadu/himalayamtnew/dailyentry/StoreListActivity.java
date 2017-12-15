package com.yadu.himalayamtnew.dailyentry;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.yadu.himalayamtnew.R;
import com.yadu.himalayamtnew.constants.AlertandMessages;
import com.yadu.himalayamtnew.constants.CommonFunctions;
import com.yadu.himalayamtnew.constants.CommonString;
import com.yadu.himalayamtnew.database.HimalayaDb;
import com.yadu.himalayamtnew.delegates.CoverageBean;
import com.yadu.himalayamtnew.xmlGetterSetter.Audit_QuestionGetterSetter;
import com.yadu.himalayamtnew.xmlGetterSetter.JourneyPlanGetterSetter;
import com.yadu.himalayamtnew.xmlHandler.XMLHandlers;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

import static com.yadu.himalayamtnew.constants.CommonFunctions.getCurrentTime;

public class StoreListActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    LinearLayout parent_linear, nodata_linear;
    FloatingActionButton fab_button;
    HimalayaDb database;
    String user_type;
    SharedPreferences preferences;
    private SharedPreferences.Editor editor = null;
    String date, username;
    ArrayList<CoverageBean> coverage;
    boolean flag_deviation = false;
    ArrayList<JourneyPlanGetterSetter> jcplist;
    RecyclerAdapter adapter;
    Context context;
    boolean result_flag = false;
    private Dialog dialog;
    private ProgressBar pb;
    private TextView percentage, message;
    Data data;
    int eventType;
    String str;
    JourneyPlanGetterSetter jcpgettersetter;
    boolean ResultFlag = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_list);
        declaration();
        fab_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (database.isSkuMasterDownloaded()) {
                    if (isStoreInvalid()) {
                        AlertandMessages.showSnackbarMsg(context, "Please checkout from current store");
                    } else {
                        if (database.getPJPDeviationData(date).size() > 0) {
                            showPJPDeviationStoreList();
                        } else {
                            new PJPDownloadTask(context).execute();
                        }
                    }
                } else {
                    AlertandMessages.showSnackbarMsg(context, "Please download data first");
                }


            }
        });

    }

    public boolean isStoreInvalid() {
        boolean flag_is_invalid = false;
        for (int i = 0; i < coverage.size(); i++) {
            if (coverage.get(i).getStatus().equals(CommonString.KEY_INVALID)) {
                flag_is_invalid = true;
                break;
            }
        }

        return flag_is_invalid;
    }


    @Override
    protected void onResume() {
        super.onResume();
        setListdata();
    }

    private class PJPDownloadTask extends AsyncTask<Void, Data, String> {
        private Context context;

        PJPDownloadTask(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.custom);
            pb = (ProgressBar) dialog.findViewById(R.id.progressBar1);
            percentage = (TextView) dialog.findViewById(R.id.percentage);
            message = (TextView) dialog.findViewById(R.id.message);
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                String resultHttp = "";
                data = new Data();

                data.value = 10;
                data.name = "PJP Deviation Downloading";
                publishProgress(data);

                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(true);
                XmlPullParser xpp = factory.newPullParser();

                SoapObject request = new SoapObject(CommonString.NAMESPACE, CommonString.METHOD_NAME_UNIVERSAL_DOWNLOAD);
                request.addProperty("UserName", username);
                request.addProperty("Type", "JOURNEY_DEVIATION");

                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);

                HttpTransportSE androidHttpTransport = new HttpTransportSE(CommonString.URL);
                androidHttpTransport.call(CommonString.SOAP_ACTION_UNIVERSAL, envelope);

                Object result = envelope.getResponse();

                if (result.toString() != null) {
                    xpp.setInput(new StringReader(result.toString()));
                    xpp.next();
                    eventType = xpp.getEventType();

                    jcpgettersetter = XMLHandlers.JCPXMLHandler(xpp, eventType);

                    if (jcpgettersetter.getStore_cd().size() > 0) {
                        resultHttp = CommonString.KEY_SUCCESS;

                    } else {
                        return "JOURNEY_DEVIATION";
                    }

                    data.value = 10;
                    data.name = "PJP Deviation Data Downloading";
                }
                publishProgress(data);

                database.open();
                database.insertPJPDeviationData(jcpgettersetter);

            } catch (MalformedURLException e) {

                ResultFlag = false;
                str = CommonString.MESSAGE_EXCEPTION;
                return CommonString.MESSAGE_EXCEPTION;
            } catch (SocketTimeoutException e) {
                ResultFlag = false;
                str = CommonString.MESSAGE_SOCKETEXCEPTION;
                return CommonString.MESSAGE_SOCKETEXCEPTION;
            } catch (InterruptedIOException e) {
                ResultFlag = false;
                str = CommonString.MESSAGE_EXCEPTION;
                return CommonString.MESSAGE_EXCEPTION;

            } catch (IOException e) {
                ResultFlag = false;
                str = CommonString.MESSAGE_SOCKETEXCEPTION;
                return CommonString.MESSAGE_SOCKETEXCEPTION;
            } catch (XmlPullParserException e) {
                ResultFlag = false;
                str = CommonString.MESSAGE_XmlPull;
                return CommonString.MESSAGE_XmlPull;
            } catch (Exception e) {
                ResultFlag = false;
                str = CommonString.MESSAGE_EXCEPTION;
                return CommonString.MESSAGE_EXCEPTION;
            }

            if (ResultFlag) {
                return "";
            } else {
                return str;
            }

        }

        @Override
        protected void onProgressUpdate(Data... values) {
            // TODO Auto-generated method stub

            pb.setProgress(values[0].value);
            percentage.setText(values[0].value + "%");
            message.setText(values[0].name);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (s.equalsIgnoreCase("")) {
                dialog.dismiss();
                showPJPDeviationStoreList();
            } else if (s.contains(CommonString.MESSAGE_SOCKETEXCEPTION)) {
                dialog.dismiss();
                AlertandMessages.showSnackbarMsg(context, CommonString.MESSAGE_SOCKETEXCEPTION);
            } else {
                dialog.dismiss();
                AlertandMessages.showSnackbarMsg(context, "No PJP Deviation data found ");
            }
        }
    }

    class Data {
        int value;
        String name;
    }

    public void setListdata() {
        coverage = database.getCoverageData(date);
        if (database.isSkuMasterDownloaded()) {
            if (coverage.size() > 0 && isPJPDeviationInvalid()) {
                showPJPDeviationStoreList();
            } else if (database.getJCPData(date).size() == 0 && database.getPJPDeviationData(date).size() > 0) {
                showPJPDeviationStoreList();
            } else if (database.getJCPData(date).size() > 0) {
                jcplist = database.getJCPData(date);
                fab_button.setVisibility(View.VISIBLE);
                setTitle("Store List " + date);
                setCheckOutData();
                adapter = new RecyclerAdapter(context, jcplist);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
                flag_deviation = false;
            } else {
                //////jeevan
                recyclerView.setVisibility(View.VISIBLE);
                parent_linear.setBackgroundColor((getResources().getColor(R.color.grey_light)));
                nodata_linear.setVisibility(View.GONE);
                fab_button.setVisibility(View.VISIBLE);
            }
        } else {
            recyclerView.setVisibility(View.GONE);
            parent_linear.setBackgroundColor((getResources().getColor(R.color.grey_light)));
            nodata_linear.setVisibility(View.VISIBLE);
        }

            /*if (database.isSkuMasterDownloaded()) {
            if (database.getPJPDeviationData(date).size()>0) {
                showPJPDeviationStoreList();
                //////jeevan
            }
           *//* else if (isPJPDeviationInvalid()) {
                showPJPDeviationStoreList();
            }*//* else {
                jcplist = database.getJCPData(date);
                fab_button.setVisibility(View.VISIBLE);
                setTitle("Store List " + date);
                if (jcplist.size() > 0) {
                    setCheckOutData();
                    adapter = new RecyclerAdapter(context, jcplist);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(context));
                    flag_deviation = false;

                } else {
                    //////jeevan
                    recyclerView.setVisibility(View.VISIBLE);
                    parent_linear.setBackgroundColor((getResources().getColor(R.color.grey_light)));
                    nodata_linear.setVisibility(View.GONE);
                    fab_button.setVisibility(View.VISIBLE);
                }
            }
        } else {
            recyclerView.setVisibility(View.GONE);
            parent_linear.setBackgroundColor((getResources().getColor(R.color.grey_light)));
            nodata_linear.setVisibility(View.VISIBLE);
        }*/

    }

    public boolean isPJPDeviationInvalid() {
        boolean flag_is_invalid = false;
        for (int i = 0; i < coverage.size(); i++) {
            if (coverage.get(i).getStatus().equals(CommonString.KEY_INVALID) ||
                    coverage.get(i).getStatus().equals(CommonString.KEY_VALID)) {
                if (coverage.get(i).isPJPDeviation())
                    flag_is_invalid = true;

                break;
            }
        }

        return flag_is_invalid;
    }

    public void showPJPDeviationStoreList() {
        //jeevan
        flag_deviation = true;
        jcplist = database.getPJPDeviationData(date);
        setCheckOutData();
        adapter = new RecyclerAdapter(context, jcplist);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
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
            View view = inflator.inflate(R.layout.storelistrow, parent, false);
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

            }
           else if (jcplist.get(position).getUploadStatus().get(0).equals(CommonString.KEY_D)) {
                holder.img.setVisibility(View.VISIBLE);
                holder.img.setBackgroundResource(R.drawable.tick_d);
                holder.checkout.setVisibility(View.INVISIBLE);

            }
            else if (jcplist.get(position).getUploadStatus().get(0).equals(CommonString.KEY_P)) {
                holder.img.setVisibility(View.VISIBLE);
                holder.img.setBackgroundResource(R.drawable.tick_p);
                holder.checkout.setVisibility(View.INVISIBLE);

            }
            else if (coveragespecific.size() > 0 &&
                    coveragespecific.get(0).getStatus().equalsIgnoreCase(CommonString.STORE_STATUS_LEAVE)) {
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
            holder.storename.setText(jcplist.get(position).getStore_name().get(0) +"  "+ "(ID : "+jcplist.get(position).
                    getStore_cd().get(0)+ ")");
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
                                    if (CommonFunctions.CheckNetAvailability(context)) {
                                        Intent i = new Intent(context, CheckOutStoreActivity.class);
                                        i.putExtra(CommonString.KEY_PJP_DEVIATION, flag_deviation);
                                        i.putExtra(CommonString.KEY_STORE_CD, jcplist.get(position).getStore_cd().get(0));
                                        startActivity(i);
                                    } else {
                                        AlertandMessages.showSnackbarMsg(context, "No internet connection ");
                                    }

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
            return jcplist.size();
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

        }
       else if (upload_status.equals(CommonString.KEY_D)) {
            AlertandMessages.showSnackbarMsg(context, "Data Uploaded");

        }
       else if (upload_status.equals(CommonString.KEY_P)) {
            AlertandMessages.showSnackbarMsg(context, "Data Parsing Error. Please try again");

        }
        else if (checkoutstatus.equals(CommonString.KEY_C)) {
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


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // NavUtils.navigateUpFromSameTask(this);
            finish();
            overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);

        }
        return super.onOptionsItemSelected(item);
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
                    editor.putString(CommonString.KEY_STORE_NAME, storeName);
                    editor.putString(CommonString.KEY_STORE_CD, storeCd);
                    editor.commit();
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
                        Intent in = new Intent(context, StoreImageActivity.class);
                        in.putExtra(CommonString.KEY_PJP_DEVIATION, flag_deviation);
                        startActivity(in);
                        overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
                    } else {
                        Intent in = new Intent(context, StoreEntry.class);
                        startActivity(in);
                        overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
                    }

                } else if (checkedId == R.id.no) {
                    dialog.cancel();
                    ArrayList<CoverageBean>specific_data;
                    specific_data=database.getCoverageSpecificData(storeCd);
                    if (specific_data.size()>0 && specific_data.get(0).getStatus().equalsIgnoreCase(CommonString.KEY_INVALID) ||
                            specific_data.size()>0 &&  specific_data.get(0).getStatus().equalsIgnoreCase(CommonString.KEY_VALID)) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setMessage(CommonString.DATA_DELETE_ALERT_MESSAGE)
                                .setCancelable(false)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        UpdateData(storeCd);
                                        SharedPreferences.Editor editor = preferences.edit();
                                        editor.putString(CommonString.KEY_STORE_CD, storeCd);
                                        editor.putString(CommonString.KEY_STOREVISITED, "");
                                        editor.putString(CommonString.KEY_STOREVISITED_STATUS, "");
                                        editor.commit();
                                        Intent in = new Intent(context, NonWorkingReason.class);
                                        in.putExtra(CommonString.KEY_PJP_DEVIATION, flag_deviation);
                                        startActivity(in);
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
                        UpdateData(storeCd);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString(CommonString.KEY_STORE_CD, storeCd);
                        editor.putString(CommonString.KEY_STOREVISITED, "");
                        editor.putString(CommonString.KEY_STOREVISITED_STATUS, "");
                        editor.commit();
                        Intent in = new Intent(context, NonWorkingReason.class);
                        in.putExtra(CommonString.KEY_PJP_DEVIATION, flag_deviation);
                        startActivity(in);
                    }
                }
            }

        });

        dialog.show();
    }
    public void UpdateData(String storeCd) {
        database.open();
        database.deleteSpecificStoreData(storeCd);
        if (flag_deviation){
            database.updateDeviationStoreStatusOnCheckout(storeCd, jcplist.get(0).getVISIT_DATE().get(0), "N");
        }else {
            database.updateStoreStatusOnCheckout(storeCd, jcplist.get(0).getVISIT_DATE().get(0), "N");

        }
    }

    @Override
    public void onBackPressed() {
        ((Activity) context).finish();
        overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
    }

    void declaration() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.list);
        nodata_linear = (LinearLayout) findViewById(R.id.no_data_lay);
        parent_linear = (LinearLayout) findViewById(R.id.parent_linear);
        fab_button = (FloatingActionButton) findViewById(R.id.fab);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        date = preferences.getString(CommonString.KEY_DATE, null);
        username = preferences.getString(CommonString.KEY_USERNAME, null);
        user_type = preferences.getString(CommonString.KEY_USER_TYPE, null);
        context = this;
        setTitle("Store List - " + date);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        database = new HimalayaDb(this);
        database.open();
    }


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
                    database.updateCoverageStatusNew(storeCd, CommonString.KEY_VALID);
                    if (flag_deviation) {
                        database.updateDeviationStoreStatusOnCheckout(storeCd, date, CommonString.KEY_VALID);
                        jcplist = database.getPJPDeviationData(date);
                    } else {
                        database.updateStoreStatusOnCheckout(storeCd, date, CommonString.KEY_VALID);
                        jcplist = database.getJCPData(date);
                    }

                }
            }
        }
    }

}
