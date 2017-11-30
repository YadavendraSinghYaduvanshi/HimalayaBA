package com.yadu.himalayamtnew.himalaya;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.yadu.himalayamtnew.R;
import com.yadu.himalayamtnew.constants.AlertandMessages;
import com.yadu.himalayamtnew.constants.CommonFunctions;
import com.yadu.himalayamtnew.constants.CommonString;
import com.yadu.himalayamtnew.dailyentry.StoreListActivity;
import com.yadu.himalayamtnew.database.HimalayaDb;
import com.yadu.himalayamtnew.delegates.CoverageBean;
import com.yadu.himalayamtnew.download.DownloadActivity;
import com.yadu.himalayamtnew.fragment.MainFragment;
import com.yadu.himalayamtnew.upload.CheckoutNUpload;
import com.yadu.himalayamtnew.upload.UploadAllImageActivity;
import com.yadu.himalayamtnew.upload.UploadDataActivity;
import com.yadu.himalayamtnew.xmlGetterSetter.JourneyPlanGetterSetter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MainMenuActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private SharedPreferences preferences = null;
    private String date, user_name, user_type, visit_date;
    HimalayaDb database;
    JourneyPlanGetterSetter storestatus = new JourneyPlanGetterSetter();
    Context context;
    ArrayList<CoverageBean> cdata = new ArrayList<>();
    ArrayList<JourneyPlanGetterSetter> jcplist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        declaration();
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
        overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_route_plan) {
            // Handle the camera action
            Intent startDownload = new Intent(this, StoreListActivity.class);
            startActivity(startDownload);
            overridePendingTransition(R.anim.activity_in, R.anim.activity_out);

        } else if (id == R.id.nav_download) {

            AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
            builder1.setTitle("Parinaam");
            builder1.setMessage("Want to download data")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            if (CommonFunctions.CheckNetAvailability(context)) {
                                if (database.isCoverageDataFilled(date)) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                    builder.setTitle("Parinaam");
                                    builder.setMessage("Please Upload Previous Data First")
                                            .setCancelable(false)
                                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    Intent startUpload = new Intent(MainMenuActivity.this, CheckoutNUpload.class);
                                                    startActivity(startUpload);
                                                    finish();
                                                }
                                            });

                                    AlertDialog alert = builder.create();
                                    alert.show();
                                } else {
                                    Intent startDownload = new Intent(getApplicationContext(), DownloadActivity.class);
                                    startActivity(startDownload);
                                }
                            } else {
                                AlertandMessages.showSnackbarMsg(context, "No Network Available");
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

        } else if (id == R.id.nav_upload) {

            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
            builder1.setTitle("Parinaam");
            builder1.setMessage("Want to upload data")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            if (CommonFunctions.CheckNetAvailability(context)) {
                                jcplist = database.getJCPData(date);
                                //it works even there is no jcp
                                database.open();
                                if (database.isSkuMasterDownloaded()) {
                                    cdata = database.getCoverageData(date);
                                    if (isStoreInvalid(cdata)) {
                                        AlertandMessages.showSnackbarMsg(context, "First checkout of store");
                                    } else {
                                        if (cdata.size() == 0) {
                                            AlertandMessages.showSnackbarMsg(context, CommonString.MESSAGE_NO_DATA);
                                        } else {
                                            if ((validate_data())) {
                                                Intent i = new Intent(getBaseContext(), UploadDataActivity.class);
                                                i.putExtra("UploadAll", false);
                                                startActivity(i);
                                                finish();
                                            } else if (validate()) {
                                                Intent i = new Intent(getBaseContext(), UploadAllImageActivity.class);
                                                //i.putExtra("UploadAll", false);
                                                startActivity(i);
                                                finish();
                                            } else {
                                                AlertandMessages.showSnackbarMsg(context, CommonString.MESSAGE_NO_DATA);
                                            }
                                        }
                                    }
                                } else {
                                    AlertandMessages.showSnackbarMsg(context, "Please Download Data First");
                                }
                            } else {
                                AlertandMessages.showSnackbarMsg(context, "No Network Available");
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

        } else if (id == R.id.nav_exit) {

            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
            builder1.setTitle("Parinaam");
            builder1.setMessage("Want to exit app")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            Intent startDownload = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(startDownload);
                            finish();

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

        } else if (id == R.id.nav_export_database) {
            showExportDialog();
           /* AlertDialog.Builder builder1 = new AlertDialog.Builder(MainMenuActivity.this);

            builder1.setMessage("Are you sure you want to take the backup of your data")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @SuppressWarnings("resource")
                        public void onClick(DialogInterface dialog, int id) {

                            try {
                                File file = new File(Environment.getExternalStorageDirectory(), "Himalaya_MT_backup");
                                if (!file.isDirectory()) {
                                    file.mkdir();
                                }

                                File sd = Environment.getExternalStorageDirectory();
                                File data = Environment.getDataDirectory();

                                if (sd.canWrite()) {
                                    long date = System.currentTimeMillis();

                                    SimpleDateFormat sdf = new SimpleDateFormat("MMM/dd/yy");
                                    String dateString = sdf.format(date);

                                    String currentDBPath = "//data//com.yadu.himalayamt//databases//" + HimalayaDb.DATABASE_NAME;
                                    String backupDBPath = "Himalaya_MT_backup" + dateString.replace('/', '_') + ".db";

                                    String path = Environment.getExternalStorageDirectory().getPath() + "/Himalaya_MT_backup";

                                    File currentDB = new File(data, currentDBPath);
                                    File backupDB = new File(path, backupDBPath);

                                    AlertandMessages.showSnackbarMsg(context, "Database Exported Successfully");

                                    if (currentDB.exists()) {
                                        @SuppressWarnings("resource")
                                        FileChannel src = new FileInputStream(currentDB).getChannel();
                                        FileChannel dst = new FileOutputStream(backupDB).getChannel();
                                        dst.transferFrom(src, 0, src.size());
                                        src.close();
                                        dst.close();
                                    }
                                }
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                            }

                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            AlertDialog alert1 = builder1.create();
            alert1.show();*/

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        FragmentManager fragmentManager = getSupportFragmentManager();
        MainFragment cartfrag = new MainFragment();
        fragmentManager.beginTransaction()
                .replace(R.id.frame_layout, cartfrag)
                .commit();

    }

    public boolean isStoreInvalid(ArrayList<CoverageBean> coverage) {
        boolean flag_is_invalid = false;
        for (int i = 0; i < coverage.size(); i++) {
            if (coverage.get(i).getStatus().equals(CommonString.KEY_INVALID)) {
                flag_is_invalid = true;
                break;
            }
        }
        return flag_is_invalid;
    }

    public boolean validate_data() {
        boolean result = false;
        database.open();
        cdata = database.getCoverageData(date);
        for (int i = 0; i < cdata.size(); i++) {
            if (cdata.get(i).isPJPDeviation()) {
                storestatus = database.getDeviationStoreStatus(cdata.get(i).getStoreId());
            } else {
                storestatus = database.getStoreStatus(cdata.get(i).getStoreId());
            }
            if (!storestatus.getUploadStatus().get(0).equalsIgnoreCase(CommonString.KEY_U)) {
                if ((storestatus.getCheckOutStatus().get(0).equalsIgnoreCase(CommonString.KEY_C)
                        || storestatus.getUploadStatus().get(0).equalsIgnoreCase(
                        CommonString.KEY_P) || cdata.get(i).getStatus()
                        .equalsIgnoreCase(CommonString.STORE_STATUS_LEAVE))) {
                    result = true;
                    break;

                }
            }
        }

        return result;
    }

    public boolean validate() {
        boolean result = false;

        database.open();
        cdata = database.getCoverageData(date);

        for (int i = 0; i < cdata.size(); i++) {

            if (cdata.get(i).getStatus().equalsIgnoreCase(CommonString.KEY_D)) {
                result = true;
                break;

            }
        }

        return result;
    }


    public void showExportDialog() {
        android.support.v7.app.AlertDialog.Builder builder1 = new android.support.v7.app.AlertDialog.Builder(context);
        builder1.setMessage("Are you sure you want to take the backup of your data")
                .setCancelable(false)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @SuppressWarnings("resource")
                    public void onClick(DialogInterface dialog, int id) {
                        try {
                            File file = new File(Environment.getExternalStorageDirectory(), "Himalaya_MT_backup");
                            if (!file.isDirectory()) {
                                file.mkdir();
                            }

                            File sd = Environment.getExternalStorageDirectory();
                            File data = Environment.getDataDirectory();

                            if (sd.canWrite()) {
                                long date = System.currentTimeMillis();

                                SimpleDateFormat sdf = new SimpleDateFormat("MMM/dd/yy");
                                String dateString = sdf.format(date);

                                String currentDBPath = "//data//com.yadu.himalayamt//databases//" + HimalayaDb.DATABASE_NAME;
                                String backupDBPath = "Himalaya_MT_backup" + user_name.replace(".", "") + "_backup" + dateString.replace('/', '_') + CommonFunctions.getCurrentTime().replace(":", "") + ".db";

                                String path = Environment.getExternalStorageDirectory().getPath() + "/Himalaya_MT_backup";

                                File currentDB = new File(data, currentDBPath);
                                File backupDB = new File(path, backupDBPath);
                                AlertandMessages.showSnackbarMsg(context, "Database Exported Successfully");

                                if (currentDB.exists()) {
                                    @SuppressWarnings("resource")
                                    FileChannel src = new FileInputStream(currentDB).getChannel();
                                    FileChannel dst = new FileOutputStream(backupDB).getChannel();
                                    dst.transferFrom(src, 0, src.size());
                                    src.close();
                                    dst.close();
                                }
                                dialog.dismiss();
                            }
                        } catch (Exception e) {
                            dialog.dismiss();
                            e.printStackTrace();
                            AlertandMessages.showAlert((Activity) context, "Error in Database Exporting", true);
                        }
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        android.support.v7.app.AlertDialog alert1 = builder1.create();
        alert1.show();
    }


    void declaration() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context = this;
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        user_name = preferences.getString(CommonString.KEY_USERNAME, null);
        user_type = preferences.getString(CommonString.KEY_USER_TYPE, null);
        visit_date = preferences.getString(CommonString.KEY_DATE, null);
        date = preferences.getString(CommonString.KEY_DATE, null);
        getSupportActionBar().setTitle("Main Menu - " + visit_date);
        database = new HimalayaDb(this);
        database.open();
    }
}
