package com.yadu.himalayamtnew.dailyentry;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialcamera.MaterialCamera;
import com.yadu.himalayamtnew.R;
import com.yadu.himalayamtnew.constants.CommonString;
import com.yadu.himalayamtnew.database.HimalayaDb;
import com.yadu.himalayamtnew.delegates.CoverageBean;
import com.yadu.himalayamtnew.xmlGetterSetter.DeepFreezerTypeGetterSetter;
import com.yadu.himalayamtnew.xmlGetterSetter.StockNewGetterSetter;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class OpeningStock extends AppCompatActivity implements View.OnClickListener {
    boolean validate = true, flagcoldroom = false, flagmccain = false, flagstoredf = false;
    int valHeadCount, valChildCount;

    List<Integer> checkValidHeaderArray = new ArrayList<Integer>();
    List<Integer> checkValidChildArray = new ArrayList<Integer>();
    List<Integer> checkHeaderArray = new ArrayList<Integer>();

    private String image1 = "";
    String _pathforcheck, _path, str, img1 = "", img2 = "", img3 = "";

    static int grp_position = -1;
    boolean checkflag = true;
    static int currentapiVersion = 1;

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    Button btnSave;
    TextView tvheader;
    int saveBtnFlag = 0;
    int arrayEditText[] = {R.id.etAs_Per_Mccain, R.id.etactual_listed, R.id.etOpening_Stock_Cold_Room,
            R.id.etOpening_Stock_Mccain_Df, R.id.etTotal_Facing_McCain_DF,
            R.id.etOpening_Stock_Store_DF, R.id.etTotal_Facing_Store_DF,
            R.id.etmaterial_wellness_thawed_quantity};
    List<StockNewGetterSetter> listDataHeader;
    HashMap<StockNewGetterSetter, List<StockNewGetterSetter>> listDataChild;
    ArrayList<StockNewGetterSetter> openingStockData;

    private SharedPreferences preferences;
    String store_cd;

    ArrayList<StockNewGetterSetter> brandData;
    ArrayList<StockNewGetterSetter> skuData;
    StockNewGetterSetter insertData = new StockNewGetterSetter();
    HimalayaDb db;
    EditText et = null;
    String arrayData[] = new String[arrayEditText.length + 1];
    String sku_cd;
    String visit_date, username, intime;

    private ArrayList<StockNewGetterSetter> stockData = new ArrayList<StockNewGetterSetter>();
    boolean dataExists = false;
    boolean openmccaindfFlag = false;
    String Error_Message;
    boolean ischangedflag = false;
    ArrayList<DeepFreezerTypeGetterSetter> deepFreezlist = new ArrayList<DeepFreezerTypeGetterSetter>();
    Snackbar snackbar;
    ImageView img_camOpeningStock;
    boolean isDialogOpen = true;
    boolean cam_flag = true;

    //jeevan
    File saveDir = null;
    private final static int CAMERA_RQ = 6969;
    private SharedPreferences.Editor editor = null;
    int child_position=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opening_stock);
        declaration();
        // preparing list data
        prepareListData();
        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);
        // setting list adapter
        expListView.setAdapter(listAdapter);

        btnSave.setOnClickListener(this);

        expListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            }

            @Override
            public void onScrollStateChanged(AbsListView arg0, int arg1) {
                expListView.invalidateViews();

                InputMethodManager inputManager = (InputMethodManager) getApplicationContext()
                        .getSystemService(Context.INPUT_METHOD_SERVICE);
                if (getCurrentFocus() != null) {
                    inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                    getCurrentFocus().clearFocus();
                }
            }
        });

        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                return false;
            }
        });

        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                InputMethodManager inputManager = (InputMethodManager) getApplicationContext()
                        .getSystemService(Context.INPUT_METHOD_SERVICE);
                if (getWindow().getCurrentFocus() != null) {
                    inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                    getCurrentFocus().clearFocus();
                }
            }
        });

        // Listview Group collasped listener
        expListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                InputMethodManager inputManager = (InputMethodManager) getApplicationContext()
                        .getSystemService(Context.INPUT_METHOD_SERVICE);
                if (getWindow().getCurrentFocus() != null) {
                    inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                    getCurrentFocus().clearFocus();
                }
            }
        });

        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                return false;
            }
        });

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            saveDir = new File(CommonString.FILE_PATH);
            saveDir.mkdirs();
        }
    }

    //Preparing the list data
    private void prepareListData() {
        listDataHeader.clear();
        listDataChild.clear();
        brandData = db.getHeaderStockImageData(store_cd, visit_date);
        if (!(brandData.size() > 0)) {
            brandData = db.getStockAvailabilityData(store_cd);
        }
        if (brandData.size() > 0) {
            for (int i = 0; i < brandData.size(); i++) {
                listDataHeader.add(brandData.get(i));

                skuData = db.getOpeningStockDataFromDatabase(store_cd, brandData.get(i).getCategory_cd());
                if (!(skuData.size() > 0)) {
                    skuData = db.getStockSkuData(store_cd, brandData.get(i).getCategory_cd());
                } else {
                    btnSave.setText("Update");
                }
                List<StockNewGetterSetter> skulist = new ArrayList<>();
                for (int j = 0; j < skuData.size(); j++) {
                    skulist.add(skuData.get(j));
                }
                listDataChild.put(listDataHeader.get(i), skulist); // Header, Child data
            }
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.save_btn) {
            expListView.clearFocus();
            expListView.invalidateViews();

            if (snackbar == null || !snackbar.isShown()) {
                if (validateData(listDataChild, listDataHeader)) {

                    if (isValidateImages()) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setMessage("Are you sure you want to save")
                                .setCancelable(false)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        db.open();
                                        //If data already inserted
                                        if (db.checkStock(store_cd)) {
                                            //Update
                                            db.UpdateHeaderOpeningStocklistData(store_cd, visit_date, listDataHeader);
                                            db.UpdateOpeningStocklistData(store_cd, listDataChild, listDataHeader);
                                        } else {
                                            //Insert
                                            db.InsertHeaderOpeningStocklistData(store_cd, visit_date, listDataHeader);
                                            db.InsertOpeningStocklistData(store_cd, listDataChild, listDataHeader);
                                        }

                                        Toast.makeText(getApplicationContext(), "Data has been saved", Toast.LENGTH_LONG).show();
                                        finish();
                                        overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
                                    }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog alert = builder.create();
                        alert.show();
                    } else {
                        Toast.makeText(getApplicationContext(), Error_Message, Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), Error_Message, Toast.LENGTH_LONG).show();
                }
            }

        }
    }

    public class ExpandableListAdapter extends BaseExpandableListAdapter {
        private Context _context;
        private List<StockNewGetterSetter> _listDataHeader;
        private HashMap<StockNewGetterSetter, List<StockNewGetterSetter>> _listDataChild;
        LayoutInflater infalInflater;

        public ExpandableListAdapter(Context context, List<StockNewGetterSetter> listDataHeader,
                                     HashMap<StockNewGetterSetter, List<StockNewGetterSetter>> listChildData) {
            this._context = context;
            this._listDataHeader = listDataHeader;
            this._listDataChild = listChildData;
            infalInflater = LayoutInflater.from(_context);
        }

        @Override
        public Object getChild(int groupPosition, int childPosititon) {
            return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                    .get(childPosititon);
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild,
                                 View convertView, ViewGroup parent) {

            final StockNewGetterSetter childText = (StockNewGetterSetter) getChild(groupPosition, childPosition);
            ViewHolder holder = null;

            if (convertView == null) {
                convertView = infalInflater.inflate(R.layout.list_item_openingstk, null);
                holder = new ViewHolder();
                holder.cardView = (CardView) convertView.findViewById(R.id.card_view);
                holder.ed_openingStock = (EditText) convertView.findViewById(R.id.etOpening_Stock_total_stock);
                holder.ed_openingFacing = (EditText) convertView.findViewById(R.id.etOpening_Stock_Facing);
                holder.openmccaindf_layout = (LinearLayout) convertView.findViewById(R.id.openmccaindf_layaout);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            TextView txtListChild = (TextView) convertView.findViewById(R.id.lblListItem);
            // txtListChild.setText(childText.getBrand() + " - " + childText.getSku());
            txtListChild.setText(childText.getSku());
            _listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).setSku_cd(childText.getSku_cd());

            holder.ed_openingStock.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {

                    final int position = v.getId();
                    final EditText Caption = (EditText) v;
                    String value1 = Caption.getText().toString().replaceFirst("^0+(?!$)", "");
                    final String facing = _listDataChild.get(listDataHeader.get(groupPosition)).get(position).getEd_openingFacing();

                    if (value1.equals("")) {
                        _listDataChild.get(listDataHeader.get(groupPosition)).get(position).setEd_openingStock("");
                    } else if (facing.equals("")) {
                        ischangedflag = true;
                        _listDataChild.get(listDataHeader.get(groupPosition)).get(position).setEd_openingStock(value1);

                    } else {
                        if (!facing.equals("")) {

                            int totalstk = 0;
                            totalstk = Integer.parseInt(value1);
                            int fac = Integer.parseInt(facing);

                            if (fac > totalstk) {
                                _listDataChild.get(listDataHeader.get(groupPosition)).get(position).setEd_openingFacing("");
                            }
                        }
                        ischangedflag = true;
                        _listDataChild.get(listDataHeader.get(groupPosition)).get(position).setEd_openingStock(value1);
                    }
                }
            });

            final ViewHolder finalHolder = holder;
            holder.ed_openingFacing.setOnFocusChangeListener(new View.OnFocusChangeListener() {

                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    final int position = v.getId();
                    final EditText Caption = (EditText) v;
                    String value1 = Caption.getText().toString().replaceFirst("^0+(?!$)", "");

                    final String stock = _listDataChild.get(listDataHeader.get(groupPosition)).get(position).getEd_openingStock();

                    if (value1.equals("")) {
                        _listDataChild.get(listDataHeader.get(groupPosition)).get(position).setEd_openingFacing("");

                    } else if (childText.getCompany_cd().equals("1") && stock.equals("")) {
                        if (isDialogOpen) {
                            isDialogOpen = !isDialogOpen;
                            AlertDialog.Builder builder = new AlertDialog.Builder(OpeningStock.this);
                            builder.setMessage("First fill Stock data")
                                    .setCancelable(false)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.dismiss();
                                            isDialogOpen = !isDialogOpen;

                                            _listDataChild.get(listDataHeader.get(groupPosition)).get(position).setEd_openingFacing("");
                                            finalHolder.ed_openingFacing.setText("");
                                        }
                                    });
                            AlertDialog alert = builder.create();
                            alert.show();
                        }
                    } else if (childText.getCompany_cd().equals("1")) {
                        int totalstk = 0;
                        totalstk = Integer.parseInt(stock);
                        int facing = Integer.parseInt(value1);

                        if (facing > totalstk) {
                            if (isDialogOpen) {
                                isDialogOpen = !isDialogOpen;
                                AlertDialog.Builder builder = new AlertDialog.Builder(OpeningStock.this);
                                builder.setMessage("Facing cannot be greater than Stock")
                                        .setCancelable(false)
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                dialog.dismiss();
                                                isDialogOpen = !isDialogOpen;
                                                _listDataChild.get(listDataHeader.get(groupPosition)).get(position).setEd_openingFacing("");
                                                finalHolder.ed_openingFacing.setText("");
                                            }
                                        });
                                AlertDialog alert = builder.create();
                                alert.show();
                            }
                        } else {
                            ischangedflag = true;
                            _listDataChild.get(listDataHeader.get(groupPosition)).get(position).setEd_openingFacing(value1);
                        }
                    } else {
                        ischangedflag = true;
                        _listDataChild.get(listDataHeader.get(groupPosition)).get(position).setEd_openingFacing(value1);
                    }
                    //}
                }
            });

            if (childText.getCompany_cd().equals("1")) {
                holder.ed_openingStock.setVisibility(View.VISIBLE);
            } else {
                holder.ed_openingStock.setVisibility(View.INVISIBLE);
            }

            holder.ed_openingStock.setId(childPosition);
            holder.ed_openingFacing.setId(childPosition);

            holder.ed_openingStock.setText(childText.getEd_openingStock());
            holder.ed_openingFacing.setText(childText.getEd_openingFacing());

            if (childText.getEd_openingStock().equals("0")) {
                holder.ed_openingFacing.setText("0");
                _listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).setEd_openingFacing("0");
                holder.ed_openingFacing.setEnabled(false);
            } else {
                holder.ed_openingFacing.setText(childText.getEd_openingFacing());
                holder.ed_openingFacing.setEnabled(true);
            }


            if (!checkflag) {
                boolean tempflag = false;

                if (childText.getCompany_cd().equals("1") && holder.ed_openingStock.getText().toString().equals("")) {
                    holder.ed_openingStock.setHintTextColor(getResources().getColor(R.color.red));
                    holder.ed_openingStock.setHint("Empty");
                    tempflag = true;
                }

                if (holder.ed_openingFacing.getText().toString().equals("")) {
                    holder.ed_openingFacing.setHintTextColor(getResources().getColor(R.color.red));
                    holder.ed_openingFacing.setHint("Empty");
                    tempflag = true;
                }

                if (tempflag) {
                    holder.cardView.setCardBackgroundColor(getResources().getColor(R.color.red));
                } else {
                    holder.cardView.setCardBackgroundColor(getResources().getColor(R.color.white));
                }
            }

            if (!validate) {
                if (checkValidHeaderArray.contains(groupPosition)) {
                    if (checkValidChildArray.contains(childPosition)) {
                        if (flagcoldroom) {
                            holder.ed_openingStock.setTextColor(getResources().getColor(R.color.red));
                        } else {
                            holder.ed_openingStock.setTextColor(getResources().getColor(R.color.black));
                        }

                        if (flagmccain) {
                            holder.ed_openingFacing.setTextColor(getResources().getColor(R.color.red));
                        } else {
                            holder.ed_openingFacing.setTextColor(getResources().getColor(R.color.black));
                        }

                        if (!flagcoldroom && !flagmccain && !flagstoredf) {
                            holder.ed_openingStock.setTextColor(getResources().getColor(R.color.black));
                            holder.ed_openingFacing.setTextColor(getResources().getColor(R.color.black));
                            holder.cardView.setCardBackgroundColor(getResources().getColor(R.color.red));
                        } else {
                            holder.cardView.setCardBackgroundColor(getResources().getColor(R.color.red));
                        }
                    } else {
                        holder.ed_openingStock.setTextColor(getResources().getColor(R.color.black));
                        holder.ed_openingFacing.setTextColor(getResources().getColor(R.color.black));
                        holder.cardView.setCardBackgroundColor(getResources().getColor(R.color.white));
                    }

                } else {
                    holder.ed_openingStock.setTextColor(getResources().getColor(R.color.black));
                    holder.ed_openingFacing.setTextColor(getResources().getColor(R.color.black));
                    holder.cardView.setCardBackgroundColor(getResources().getColor(R.color.white));
                }
            }
            return convertView;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return _listDataChild.get(this._listDataHeader.get(groupPosition))
                    .size();
        }

        @Override
        public Object getGroup(int groupPosition) {
            return _listDataHeader.get(groupPosition);
        }

        @Override
        public int getGroupCount() {
            return _listDataHeader.size();
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            final StockNewGetterSetter headerTitle = (StockNewGetterSetter) getGroup(groupPosition);

            if (convertView == null) {
                // LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.list_group_opening, null);
            }

            CardView card_view = (CardView) convertView.findViewById(R.id.card_view);
            card_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (expListView.isGroupExpanded(groupPosition)) {
                        expListView.collapseGroup(groupPosition);
                    } else {
                        expListView.expandGroup(groupPosition);
                    }
                }
            });
            TextView lblListHeader = (TextView) convertView.findViewById(R.id.lblListHeader);
            ImageView imgcamhim = (ImageView) convertView.findViewById(R.id.img_cam_himalaya);
            ImageView imgcamcat1 = (ImageView) convertView.findViewById(R.id.img_cam_cat1);
            ImageView imgcamcat2 = (ImageView) convertView.findViewById(R.id.img_cam_cat2);

            if (headerTitle.getHimalaya_camera().equals("1")) {
                imgcamhim.setVisibility(View.VISIBLE);
            } else {
                imgcamhim.setVisibility(View.INVISIBLE);
            }

            if (headerTitle.getCategory_camera().equals("1")) {
                imgcamcat1.setVisibility(View.VISIBLE);
            } else {
                imgcamcat1.setVisibility(View.INVISIBLE);
            }

            if (headerTitle.getCategory_camera().equals("1")) {
                imgcamcat2.setVisibility(View.VISIBLE);
            } else {
                imgcamcat2.setVisibility(View.INVISIBLE);
            }

            imgcamhim.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MaterialCamera materialCamera = new MaterialCamera((Activity) _context)
                            .saveDir(saveDir)
                            .showPortraitWarning(true)
                            .allowRetry(true)
                            .defaultToFrontFacing(false)
                            .allowRetry(true)
                            .autoSubmit(false)
                            .labelConfirm(R.string.mcam_use_video);
                    grp_position = groupPosition;
                    _pathforcheck = store_cd + "_" + headerTitle.getCategory_cd() + "HimalayaSTKImg"
                            + visit_date.replace("/", "") + getCurrentTime().replace(":", "") + ".jpg";


                    editor = preferences.edit();
                    editor.putString("imagename", _pathforcheck);
                    editor.commit();
                    materialCamera.stillShot().labelConfirm(R.string.mcam_use_stillshot);
                    materialCamera.start(CAMERA_RQ);
                    child_position = R.id.img_cam_himalaya;
                   /* _path = CommonString.FILE_PATH + _pathforcheck;
                    startCameraActivity(0);*/
                }
            });


            imgcamcat1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MaterialCamera materialCamera = new MaterialCamera((Activity) _context)
                            .saveDir(saveDir)
                            .showPortraitWarning(true)
                            .allowRetry(true)
                            .defaultToFrontFacing(false)
                            .allowRetry(true)
                            .autoSubmit(false)
                            .labelConfirm(R.string.mcam_use_video);
                    grp_position = groupPosition;
                    _pathforcheck = store_cd + "_" + headerTitle.getCategory_cd() + "CatSTKImgOne"
                            + visit_date.replace("/", "") + getCurrentTime().replace(":", "") + ".jpg";

                    editor = preferences.edit();
                    editor.putString("imagename", _pathforcheck);
                    editor.commit();
                    materialCamera.stillShot().labelConfirm(R.string.mcam_use_stillshot);
                    materialCamera.start(CAMERA_RQ);
                    child_position = R.id.img_cam_cat1;
                   /* _path = CommonString.FILE_PATH + _pathforcheck;
                    startCameraActivity(1);*/
                }
            });

            imgcamcat2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MaterialCamera materialCamera = new MaterialCamera((Activity) _context)
                            .saveDir(saveDir)
                            .showPortraitWarning(true)
                            .allowRetry(true)
                            .defaultToFrontFacing(false)
                            .allowRetry(true)
                            .autoSubmit(false)
                            .labelConfirm(R.string.mcam_use_video);
                    grp_position = groupPosition;
                    _pathforcheck = store_cd + "_" + headerTitle.getCategory_cd() + "CatSTKImgTwo"
                            + visit_date.replace("/", "") + getCurrentTime().replace(":", "") + ".jpg";

                    editor = preferences.edit();
                    editor.putString("imagename", _pathforcheck);
                    editor.commit();
                    materialCamera.stillShot().labelConfirm(R.string.mcam_use_stillshot);
                    materialCamera.start(CAMERA_RQ);
                    child_position = R.id.img_cam_cat2;
                    /*_path = CommonString.FILE_PATH + _pathforcheck;
                    startCameraActivity(1);*/
                }
            });

            lblListHeader.setTypeface(null, Typeface.BOLD);
            lblListHeader.setText(headerTitle.getCategory());

            if (!img1.equalsIgnoreCase("")) {
                if (groupPosition == grp_position) {
                    headerTitle.setImg_cam(img1);
                    img1 = "";
                }
            }

            if (!img2.equalsIgnoreCase("")) {
                if (groupPosition == grp_position) {
                    headerTitle.setImg_cat_one(img2);
                    img2 = "";
                }
            }

            if (!img3.equalsIgnoreCase("")) {
                if (groupPosition == grp_position) {
                    headerTitle.setImg_cat_two(img3);
                    img3 = "";
                }
            }

            if (headerTitle.getImg_cam() != null && !headerTitle.getImg_cam().equals("")) {
                imgcamhim.setBackgroundResource(R.mipmap.h_camera_orange);
            } else {
                imgcamhim.setBackgroundResource(R.mipmap.h_camera_white);
            }

            if (headerTitle.getImg_cat_one() != null && !headerTitle.getImg_cat_one().equals("")) {
                imgcamcat1.setBackgroundResource(R.mipmap.camera_orange);
            } else {
                imgcamcat1.setBackgroundResource(R.mipmap.camera_white);
            }

            if (headerTitle.getImg_cat_two() != null && !headerTitle.getImg_cat_two().equals("")) {
                imgcamcat2.setBackgroundResource(R.mipmap.camera_orange);
            } else {
                imgcamcat2.setBackgroundResource(R.mipmap.camera_white);
            }

            if (!checkflag || !cam_flag) {
                if (checkHeaderArray.contains(groupPosition)) {
                    lblListHeader.setBackgroundColor(getResources().getColor(R.color.red));
                } else {
                    lblListHeader.setBackgroundColor(getResources().getColor(R.color.light_teal));
                }
            } else {
                lblListHeader.setBackgroundColor(getResources().getColor(R.color.light_teal));
            }
            return convertView;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }

    public class ViewHolder {
        EditText ed_openingStock, ed_openingFacing;
        LinearLayout openmccaindf_layout;
        CardView cardView;
    }

    boolean validateData(HashMap<StockNewGetterSetter, List<StockNewGetterSetter>> listDataChild2,
                         List<StockNewGetterSetter> listDataHeader2) {
        boolean flag = true;

        checkHeaderArray.clear();

        for (int i = 0; i < listDataHeader2.size(); i++) {
            //for (int i = 0; i < 1; i++) {
            for (int j = 0; j < listDataChild2.get(listDataHeader2.get(i)).size(); j++) {

                String openstocktotal = listDataChild2.get(listDataHeader2.get(i)).get(j).getEd_openingStock();
                String company_cd = listDataChild2.get(listDataHeader2.get(i)).get(j).getCompany_cd();
                String openfacing = listDataChild2.get(listDataHeader2.get(i)).get(j).getEd_openingFacing();

                if ((openfacing.equalsIgnoreCase(""))) {

                    checkflag = false;

                    flag = false;
                    Error_Message = "Please fill all the data";
                    break;
                } else if (company_cd.equals("1") && openstocktotal.equalsIgnoreCase("")) {
                    //} else if ( openstocktotal.equalsIgnoreCase("")) {

                    checkflag = false;

                    flag = false;
                    Error_Message = "Please fill all the data";
                    break;
                } else {
                    checkflag = true;
                    flag = true;
                }
            }

            if (checkflag == false) {
                if (!checkHeaderArray.contains(i)) {
                    checkHeaderArray.add(i);
                }
                break;
            }
        }
        //expListView.invalidate();
        listAdapter.notifyDataSetChanged();

        return checkflag;
    }

    public boolean isValidateImages() {
        boolean flag = true;
        cam_flag = true;

        for (int i = 0; i < listDataHeader.size(); i++) {
            //for (int i = 0; i < 1; i++) {

            if (listDataHeader.get(i).getHimalaya_camera().equals("1") && listDataHeader.get(i).getImg_cam().equals("")) {
                flag = false;
                Error_Message = "Please click Himalaya image";
                //break;
            }

            if (flag && listDataHeader.get(i).getCategory_camera().equals("1") && listDataHeader.get(i).getImg_cat_one().equals("")
                    && listDataHeader.get(i).getImg_cat_two().equals("")) {
                flag = false;
                Error_Message = "Please click at least one of two Category images";
                //break;
            }

            if (flag == false) {
                if (!checkHeaderArray.contains(i)) {
                    checkHeaderArray.add(i);
                }
                cam_flag = false;
                break;
            }
        }

        return flag;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            AlertDialog.Builder builder = new AlertDialog.Builder(OpeningStock.this);
            builder.setTitle("Parinam");
            builder.setMessage(CommonString.ONBACK_ALERT_MESSAGE).setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // NavUtils.navigateUpFromSameTask(this);
                            finish();
                            overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
                        }
                    })
                    .setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();

        AlertDialog.Builder builder = new AlertDialog.Builder(OpeningStock.this);
        builder.setTitle("Parinam");
        builder.setMessage(CommonString.ONBACK_ALERT_MESSAGE).setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                        overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
                    }
                })
                .setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public String getCurrentTime() {

        Calendar m_cal = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        return formatter.format(m_cal.getTime());
    }


    protected void startCameraActivity(int position) {
        try {
            Log.i("MakeMachine", "startCameraActivity()");
            File file = new File(_path);
            Uri outputFileUri = Uri.fromFile(file);
            Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
            startActivityForResult(intent, position);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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
                        switch (child_position) {
                            case R.id.img_cam_himalaya:
                                File file = new File(data.getData().getPath());
                                _pathforcheck = file.getName();
                                image1 = _pathforcheck;
                                img1 = _pathforcheck;
                                break;
                            case R.id.img_cam_cat1:
                                File file1 = new File(data.getData().getPath());
                                _pathforcheck = file1.getName();
                                img2 = _pathforcheck;
                              //  expListView.invalidateViews();
                                break;
                            case R.id.img_cam_cat2:
                                File file2 = new File(data.getData().getPath());
                                _pathforcheck = file2.getName();
                                img3 = _pathforcheck;
                               // expListView.invalidateViews();
                                break;
                        }
                        expListView.invalidateViews();
                        _pathforcheck = "";
                    }
                }
                break;
/*
                if (resultCode == -1) {
                    if (_pathforcheck != null && !_pathforcheck.equals("")) {
                        if (new File(str + _pathforcheck).exists()) {
                            image1 = _pathforcheck;
                            img1 = _pathforcheck;
                            expListView.invalidateViews();
                            _pathforcheck = "";
                        }
                    }
                } else {
                    Log.i("MakeMachine", "User cancelled");
                    _pathforcheck = "";
                }
                break;

            case 1:
                if (resultCode == -1) {
                    if (_pathforcheck != null && !_pathforcheck.equals("")) {
                        if (new File(str + _pathforcheck).exists()) {
                            if (_pathforcheck.contains("ImgTwo")) {
                                img3 = _pathforcheck;
                            } else {
                                img2 = _pathforcheck;
                            }

                            expListView.invalidateViews();
                            _pathforcheck = "";
                        }
                    }
                } else {
                    Log.i("MakeMachine", "User cancelled");
                    _pathforcheck = "";
                }
                break;*/
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    void declaration() {
        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<>();
        currentapiVersion = android.os.Build.VERSION.SDK_INT;
        expListView = (ExpandableListView) findViewById(R.id.lvExp);
        btnSave = (Button) findViewById(R.id.save_btn);
        img_camOpeningStock = (ImageView) findViewById(R.id.img_camOpeningStock);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        str = CommonString.FILE_PATH;
        db = new HimalayaDb(getApplicationContext());
        db.open();
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        store_cd = preferences.getString(CommonString.KEY_STORE_CD, null);
        visit_date = preferences.getString(CommonString.KEY_DATE, null);
        username = preferences.getString(CommonString.KEY_USERNAME, null);
        intime = preferences.getString(CommonString.KEY_STORE_IN_TIME, "");
        openmccaindfFlag = preferences.getBoolean("opnestkmccaindf", false);
        setTitle("Availability - " + visit_date);
    }
}
