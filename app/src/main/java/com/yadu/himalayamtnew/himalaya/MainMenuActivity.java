package com.yadu.himalayamtnew.himalaya;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.yadu.himalayamtnew.constants.CommonString;
import com.yadu.himalayamtnew.dailyentry.StoreListActivity;
import com.yadu.himalayamtnew.database.HimalayaDb;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;

public class MainMenuActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private SharedPreferences preferences = null;
    private String date, user_name, user_type, visit_date;
    HimalayaDb database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        declaration();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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

        }
/*        else if (id == R.id.nav_download) {

            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
            builder1.setTitle("Parinaam");
            builder1.setMessage("Want to download data")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            if (checkNetIsAvailable()) {
                                if (database.isCoverageDataFilled(date)) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(MainMenuActivity.this);
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
                                    // Toast.makeText(getBaseContext(), AlertMessage.MESSAGE_NO_DATA, Toast.LENGTH_LONG).show();
                                } else {
                                    Intent startDownload = new Intent(getApplicationContext(), CompleteDownloadActivity.class);
                                    startActivity(startDownload);
                                    finish();
                                }
                            } else {
                                Snackbar.make(frameLayout, "No Network Available", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
                                //Toast.makeText(getApplicationContext(), "No Network Available", Toast.LENGTH_SHORT).show();
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

                            if (checkNetIsAvailable()) {
                                jcplist = database.getJCPData(date);
                               *//* if (jcplist.size() == 0) {
                                    Snackbar.make(frameLayout, "Please Download Data First", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
                                    //  Toast.makeText(getBaseContext(), "Please Download Data First", Toast.LENGTH_LONG).show();
                                }*//*
                                //it works even there is no jcp
                                if (database.isSkuMasterDownloaded()) {

                                    cdata = database.getCoverageData(date);
                                    if(isStoreInvalid(cdata)){
                                        //if (preferences.getString(CommonString.KEY_STOREVISITED_STATUS, "").equals("Yes")) {
                                        Snackbar.make(frameLayout, "First checkout of store", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
                                        //Toast.makeText(getApplicationContext(), "First checkout of store", Toast.LENGTH_SHORT).show();
                                    } else {
                                        //ArrayList<GeotaggingBeans> gdata = new ArrayList<GeotaggingBeans>();

                                        //gdata = database.getGeotaggingData("Y");
                                        if (cdata.size() == 0) {
                                            Snackbar.make(frameLayout, AlertMessage.MESSAGE_NO_DATA, Snackbar.LENGTH_SHORT).setAction("Action", null).show();
                                            // Toast.makeText(getBaseContext(), AlertMessage.MESSAGE_NO_DATA, Toast.LENGTH_LONG).show();
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
                                                Snackbar.make(frameLayout, AlertMessage.MESSAGE_NO_DATA, Snackbar.LENGTH_SHORT).setAction("Action", null).show();
                                            }
                                        }
                                    }
                    *//*intent = new Intent(getBaseContext(),UploadOptionActivity.class);
                    startActivity(intent);
					MainMenuActivity.this.finish();*//*

                                }else {

                                    Snackbar.make(frameLayout, "Please Download Data First", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
                                    //  Toast.makeText(getBaseContext(), "Please Download Data First", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Snackbar.make(frameLayout, "No Network Available", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
                                //  Toast.makeText(getApplicationContext(), "No Network Available", Toast.LENGTH_SHORT).show();
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

        } else if (id == R.id.nav_services) {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(MainMenuActivity.this);
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

                                    String currentDBPath = "//data//com.cpm.himalaya//databases//" + GSKDatabase.DATABASE_NAME;
                                    String backupDBPath = "Himalaya_MT_backup" + dateString.replace('/', '-');

                                    String path = Environment.getExternalStorageDirectory().getPath();

                                    File currentDB = new File(data, currentDBPath);
                                    File backupDB = new File(path, backupDBPath);

                                    Snackbar.make(frameLayout, "Database Exported Successfully", Snackbar.LENGTH_SHORT).show();

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
            alert1.show();

        } *//*else if (id == R.id.nav_payslip) {
            jcplist = database.getJCPData(date);

            if (jcplist.size() == 0) {
                Snackbar.make(frameLayout, "Please Download Data First", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
                //  Toast.makeText(getBaseContext(), "Please Download Data First", Toast.LENGTH_LONG).show();
            } else {
                payslip = database.getPaySlipData();
                if (payslip.size() == 0) {
                    Snackbar.make(frameLayout, "No Payslip data found", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
                } else {
                    Intent in = new Intent(this, PaySlip.class);
                    startActivity(in);
                    overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
                }
            }
        }*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    void declaration()
    {
        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        user_name = preferences.getString(CommonString.KEY_USERNAME, null);
        user_type = preferences.getString(CommonString.KEY_USER_TYPE, null);
        visit_date = preferences.getString(CommonString.KEY_DATE, null);

        database = new HimalayaDb(this);
        database.open();
    }
}
