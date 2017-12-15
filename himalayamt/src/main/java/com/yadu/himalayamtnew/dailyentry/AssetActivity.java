package com.yadu.himalayamtnew.dailyentry;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;


import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;


import com.yadu.himalayamtnew.R;

import com.yadu.himalayamtnew.constants.CommonString;
import com.yadu.himalayamtnew.database.HimalayaDb;
import com.yadu.himalayamtnew.xmlGetterSetter.AssetChecklistReasonGettersetter;
import com.yadu.himalayamtnew.xmlGetterSetter.AssetInsertdataGetterSetter;
import com.yadu.himalayamtnew.xmlGetterSetter.BrandGetterSetter;
import com.yadu.himalayamtnew.xmlGetterSetter.ChecklistInsertDataGetterSetter;
import com.yadu.himalayamtnew.xmlGetterSetter.MappingAssetChecklistGetterSetter;
import com.yadu.himalayamtnew.xmlGetterSetter.SkuGetterSetter;


import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;


public class AssetActivity extends AppCompatActivity implements View.OnClickListener {

    boolean checkflag = true;
    List<Integer> checkHeaderArray = new ArrayList<Integer>();
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    Button btnSave;
    List<AssetInsertdataGetterSetter> listDataHeader;
    HashMap<AssetInsertdataGetterSetter, List<AssetInsertdataGetterSetter>> listDataChild;
    ArrayList<AssetInsertdataGetterSetter> categoryData;
    ArrayList<AssetInsertdataGetterSetter> skuData;
    HimalayaDb db;
    private SharedPreferences preferences;
    String store_cd, visit_date, username;
    ImageView img;
    boolean ischangedflag = false;
    String _pathforcheck, _path, str;
    private String image1 = "";
    String img1 = "";
    static int grp_position = -1;
    static int child_position = -1;
    Context context;
    ArrayList<ChecklistInsertDataGetterSetter> checklistInsertDataGetterSetters;
    ArrayList<MappingAssetChecklistGetterSetter> mappingChecklistDataGetterSetters;
    ListView listView;
    String msg_error = "Please fill all the fields";
    //jeevan
    File saveDir = null;
    private final static int CAMERA_RQ = 6969;
    private SharedPreferences.Editor editor = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asset);
        declaration();
        // preparing list data
        prepareListData();
        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);
        expListView.setAdapter(listAdapter);
        btnSave.setOnClickListener(this);
        expListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            }
            @Override
            public void onScrollStateChanged(AbsListView arg0, int arg1) {
                expListView.invalidateViews();
            }
        });
        if (Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission(getApplicationContext(),
                        android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(getApplicationContext(),
                        android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_GRANTED) {
            saveDir = new File(CommonString.FILE_PATH);
            saveDir.mkdirs();
        }

    }

    private void prepareListData() {
        listDataHeader.clear();
        listDataChild.clear();
        mappingChecklistDataGetterSetters = db.getMapingCheckListData();
        categoryData = db.getAssetCategoryData(store_cd);
        if (categoryData.size() > 0) {
            // Adding child data
            for (int i = 0; i < categoryData.size(); i++) {
                List<AssetInsertdataGetterSetter> skulist = new ArrayList<>();
                listDataHeader.add(categoryData.get(i));
                skuData = db.getAssetDataFromdatabase(store_cd, categoryData.get(i).getCategory_cd());
                if (!(skuData.size() > 0) || (skuData.get(0).getAsset() == null) || (skuData.get(0).getAsset().equals(""))) {
                    skuData = db.getAssetData(categoryData.get(i).getCategory_cd(), store_cd);
                    for (int j = 0; j < skuData.size(); j++) {
                        skulist.add(skuData.get(j));
                    }
                } else {
                    for (int j = 0; j < skuData.size(); j++) {
                        ArrayList<SkuGetterSetter> listSkuData = db.getPaidVisibilitySkuListData(skuData.get(j).getKey_id());
                        if (listSkuData.size() > 0) {
                            skuData.get(j).setSkulist(listSkuData);
                        }

                        ArrayList<ChecklistInsertDataGetterSetter> check_list =
                                db.getCheckListWithReasonData(skuData.get(j).getKey_id());

                        if (check_list.size() > 0) {
                            skuData.get(j).setChecklist(check_list);
                        }

                        skulist.add(skuData.get(j));
                    }
                    btnSave.setText("Update");
                }
                listDataChild.put(listDataHeader.get(i), skulist); // Header, Child data
            }
        } else {
            expListView.setVisibility(View.GONE);
            btnSave.setVisibility(View.INVISIBLE);
            img.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.save_btn) {
            expListView.clearFocus();

            if (validateData(listDataChild, listDataHeader)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Are you sure you want to save")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                db.open();
                                db.deleteAssetData(store_cd, db.getAssetHeaderData(store_cd));
                                db.InsertAssetData(store_cd, listDataChild, listDataHeader, visit_date);
                                Toast.makeText(getApplicationContext(), "Data has been saved", Toast.LENGTH_SHORT).show();
                                finish();
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
                listAdapter.notifyDataSetChanged();
                Toast.makeText(getApplicationContext(), msg_error, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public class ExpandableListAdapter extends BaseExpandableListAdapter {
        private Context _context;
        private List<AssetInsertdataGetterSetter> _listDataHeader; // header titles
        // child data in format of header title, child title
        private HashMap<AssetInsertdataGetterSetter, List<AssetInsertdataGetterSetter>> _listDataChild;
        LayoutInflater infalInflater;

        public ExpandableListAdapter(Context context, List<AssetInsertdataGetterSetter> listDataHeader,
                                     HashMap<AssetInsertdataGetterSetter, List<AssetInsertdataGetterSetter>> listChildData) {
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

        @SuppressLint("NewApi")
        @Override
        public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

            final AssetInsertdataGetterSetter childText = (AssetInsertdataGetterSetter) getChild(groupPosition, childPosition);
            ViewHolder holder = null;

            if (convertView == null) {
                convertView = infalInflater.inflate(R.layout.asset_entry, null);
                holder = new ViewHolder();
                holder.cardView = (CardView) convertView.findViewById(R.id.card_view);
                holder.etremark = (EditText) convertView.findViewById(R.id.etremarks);
                holder.tbpresent = (ToggleButton) convertView.findViewById(R.id.tbpresent);
                holder.btn_cam = (Button) convertView.findViewById(R.id.btncam);
                holder.cam_layout = (LinearLayout) convertView.findViewById(R.id.cam_layout);
                holder.remark_layout = (LinearLayout) convertView.findViewById(R.id.remark_layout);
                holder.btn_checkList = (Button) convertView.findViewById(R.id.btn_checkList);
                holder.btn_skuList = (Button) convertView.findViewById(R.id.btn_skuList);
                holder.img_ref = (ImageButton) convertView.findViewById(R.id.img_ref);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.img_ref.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showPlanogram(_listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).getPlanogram_img());
                }
            });

            holder.tbpresent.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    ischangedflag = true;
                    String val = ((ToggleButton) v).getText().toString();
                    _listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).setPresent(val);
                    if (val.equals("NO")) {
                        _listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).setImg("");
                        _listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).getChecklist().clear();
                        _listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).getSkulist().clear();
                    } else {
                        _listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).setRemark("");
                    }
                    expListView.clearFocus();
                    expListView.invalidateViews();
                }
            });

            holder.btn_checkList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showChecklistDialogue(childText.getAsset_cd(),
                            childText.getCategory_cd(),
                            _listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition));
                }
            });

            holder.btn_cam.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
/*
                    MaterialCamera materialCamera = new MaterialCamera((Activity) _context)
                            .saveDir(saveDir)
                            .showPortraitWarning(true)
                            .allowRetry(true)
                            .defaultToFrontFacing(false)
                            .allowRetry(true)
                            .autoSubmit(false)
                            .labelConfirm(R.string.mcam_use_video);
*/
                    grp_position = groupPosition;
                    child_position = childPosition;

                    _pathforcheck = store_cd + "_AssetImage_" +
                            visit_date.replace("/", "") + "_" + getCurrentTime().replace(":", "") + ".jpg";

                   /* editor = preferences.edit();
                    editor.putString("imagename", _pathforcheck);
                    editor.commit();
                    materialCamera.stillShot().labelConfirm(R.string.mcam_use_stillshot);
                    materialCamera.start(CAMERA_RQ);*/
                    _path = CommonString.FILE_PATH + _pathforcheck;
                    startCameraActivity();
                   // CommonFunctions.startCameraActivity((Activity) context, _path);
                }
            });

            holder.etremark.setOnFocusChangeListener(new View.OnFocusChangeListener() {

                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus) {
                        final EditText Caption = (EditText) v;
                        String value1 = Caption.getText().toString().trim();
                        value1 = value1.replaceAll("[&^<>{}'$]", "");

                        if (value1.equals("") || value1.substring(0).equals(".") || value1.substring(0).equals(",")) {
                            _listDataChild.get(listDataHeader.get(groupPosition))
                                    .get(childPosition).setRemark("");
                        } else {
                            ischangedflag = true;
                            _listDataChild.get(listDataHeader.get(groupPosition))
                                    .get(childPosition).setRemark(value1);
                        }
                    }
                }
            });
            holder.etremark.setText(childText.getRemark());

            holder.tbpresent.setChecked(_listDataChild.get(listDataHeader.get(groupPosition))
                    .get(childPosition).getPresent().equals("YES"));

            if (!img1.equalsIgnoreCase("")) {
                if (childPosition == child_position && groupPosition == grp_position) {
                    //childText.get(childPosition).setCamera("YES");
                    _listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).setImg(img1);
                    //childText.setImg(img1);
                    img1 = "";
                }
            }

            if (_listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).getPresent().equals("YES")) {
                //holder.etremark.setVisibility(View.INVISIBLE);
                holder.remark_layout.setVisibility(View.GONE);
                holder.cam_layout.setVisibility(View.VISIBLE);
                // holder.btn_skuList.setVisibility(View.VISIBLE);

                if (_listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).getImg() != null &&
                        !_listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).getImg().equals("")) {
                    holder.btn_cam.setBackgroundResource(R.drawable.camera_done);
                } else {
                    holder.btn_cam.setBackgroundResource(R.drawable.camera);
                }

            } else if (_listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).getPresent().equals("NO")) {
                //holder.etremark.setVisibility(View.VISIBLE);
                holder.remark_layout.setVisibility(View.VISIBLE);
                holder.cam_layout.setVisibility(View.GONE);
                holder.btn_skuList.setVisibility(View.GONE);
                holder.etremark.setText(childText.getRemark());
            }

            TextView txtListChild = (TextView) convertView.findViewById(R.id.lblListItem);
            txtListChild.setText(childText.getAsset());

            for (int m = 0; m < mappingChecklistDataGetterSetters.size(); m++) {

                if (mappingChecklistDataGetterSetters.get(m).getAsset_cd().get(0)
                        .equals(_listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).getAsset_cd())) {

                    if (_listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).getPresent().equals("YES")) {
                        holder.btn_checkList.setVisibility(View.VISIBLE);
                    } else {
                        holder.btn_checkList.setVisibility(View.GONE);
                    }

                    break;
                } else {
                    holder.btn_checkList.setVisibility(View.GONE);
                }
            }


            holder.btn_skuList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showSkuDialog(childText);
                }
            });

            if (!checkflag) {
                boolean tempflag = false;
                if (_listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).getPresent().equals("NO")) {

                    if (holder.etremark.getText().toString().equals("")) {
                        holder.etremark.setHintTextColor(getResources().getColor(R.color.green));
                        holder.etremark.setHint("Empty");
                        tempflag = true;
                    }
                } else {
                    if (_listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).getImg().equals("")) {
                        holder.btn_cam.setBackgroundResource(R.drawable.camera_not_done);
                    } else {
                        holder.btn_cam.setBackgroundResource(R.drawable.camera_done);
                    }

                    if (_listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).getChecklist().size() == 0) {
                        holder.btn_checkList.setTextColor(getResources().getColor(R.color.red));
                    } else {
                        holder.btn_checkList.setTextColor(getResources().getColor(R.color.black));
                    }

                    if (_listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).getSkulist().size() == 0) {
                        holder.btn_skuList.setTextColor(getResources().getColor(R.color.red));
                    } else {
                        holder.btn_skuList.setTextColor(getResources().getColor(R.color.black));
                    }
                }

                if (tempflag) {
                    holder.cardView.setCardBackgroundColor(getResources().getColor(R.color.red));
                } else {
                    holder.cardView.setCardBackgroundColor(getResources().getColor(R.color.white));
                }

            }
            //holder.tvpromo.setText(childText.getAsset());
            return convertView;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return _listDataChild.get(this._listDataHeader.get(groupPosition)).size();
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
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            AssetInsertdataGetterSetter headerTitle = (AssetInsertdataGetterSetter) getGroup(groupPosition);
            if (convertView == null) {
                convertView = infalInflater.inflate(R.layout.list_group, null);
            }

            TextView lblListHeader = (TextView) convertView.findViewById(R.id.lblListHeader);
            lblListHeader.setTypeface(null, Typeface.BOLD);
            lblListHeader.setText(headerTitle.getCategory());

            if (!checkflag) {
                if (checkHeaderArray.contains(groupPosition)) {
                    lblListHeader.setBackgroundColor(getResources().getColor(R.color.red));
                } else {
                    lblListHeader.setBackgroundColor(getResources().getColor(R.color.light_teal));
                }
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
        EditText etremark;
        ToggleButton tbpresent;
        CardView cardView;
        LinearLayout cam_layout, remark_layout;
        Button btn_cam, btn_checkList, btn_skuList;
        ImageButton img_ref;

    }

    public String getCurrentTime() {

        Calendar m_cal = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        return formatter.format(m_cal.getTime());
    }

    boolean validateData(HashMap<AssetInsertdataGetterSetter, List<AssetInsertdataGetterSetter>> listDataChild2,
                         List<AssetInsertdataGetterSetter> listDataHeader2) {
        //boolean flag = true;
        checkHeaderArray.clear();

        for (int i = 0; i < listDataHeader2.size(); i++) {
            for (int j = 0; j < listDataChild2.get(listDataHeader2.get(i)).size(); j++) {

                ArrayList<ChecklistInsertDataGetterSetter> checklistInsertDataGetterSetter;
                String present = listDataChild2.get(listDataHeader2.get(i)).get(j).getPresent();
                String remark = listDataChild2.get(listDataHeader2.get(i)).get(j).getRemark();
                String img = listDataChild2.get(listDataHeader2.get(i)).get(j).getImg();

                checklistInsertDataGetterSetter = listDataChild2.get(listDataHeader2.get(i)).get(j).getChecklist();

                if (present.equalsIgnoreCase("NO")) {
                    if (remark.equalsIgnoreCase("")) {

                        if (!checkHeaderArray.contains(i)) {
                            checkHeaderArray.add(i);
                        }
                        msg_error = "Please fill remark";
                        checkflag = false;

                        break;

                    } else {
                        checkflag = true;
                    }
                } else if (present.equalsIgnoreCase("YES")) {
                    if (img.equals("")) {
                        if (!checkHeaderArray.contains(i)) {
                            checkHeaderArray.add(i);
                        }

                        msg_error = "Please click image";
                        checkflag = false;
                        break;

                    } else {

                        checkflag = true;
                    }
                }

                if (checkflag && present.equalsIgnoreCase("YES")) {

                    if (checklistInsertDataGetterSetter.size() > 0) {
                        checkflag = true;
                    } else {
                        msg_error = "Please fill Checklist data";
                        checkflag = false;
                        break;
                    }

                }

                if (!checkflag) {
                    if (!checkHeaderArray.contains(i)) {
                        checkHeaderArray.add(i);
                        break;
                    }
                }
            }

            if (checkflag == false) {
                break;
            }

        }

        return checkflag;

    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(AssetActivity.this);
        builder.setMessage(CommonString.ONBACK_ALERT_MESSAGE)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                        overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.empty_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            AlertDialog.Builder builder = new AlertDialog.Builder(AssetActivity.this);
            builder.setMessage(CommonString.ONBACK_ALERT_MESSAGE)
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            finish();
                            overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i("MakeMachine", "resultCode: " + resultCode);
        switch (resultCode) {
            case 0:
                Log.i("MakeMachine", "User cancelled");
                break;
            case -1:
/*
               if (requestCode == CAMERA_RQ) {
                    if (resultCode == RESULT_OK) {
                        File file = new File(data.getData().getPath());
                        _pathforcheck = file.getName();
                        image1 = _pathforcheck;
                        img1 = _pathforcheck;
                        expListView.invalidateViews();
                        _pathforcheck = "";
                    }
                }
*/

                if (_pathforcheck != null && !_pathforcheck.equals("")) {
                    if (new File(str + _pathforcheck).exists()) {
                        image1 = _pathforcheck;
                        img1 = _pathforcheck;
                        expListView.invalidateViews();
                        _pathforcheck = "";
                    }
                }

                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    //CheckList
    public void showChecklistDialogue(final String asset_cd, final String category_cd,
                                      final AssetInsertdataGetterSetter asset_child) {
        boolean update_flag = false;
        checklistInsertDataGetterSetters = asset_child.getChecklist();
        if (!(checklistInsertDataGetterSetters.size() > 0)) {
            checklistInsertDataGetterSetters = db.getCheckListData(asset_cd);
        } else {
            update_flag = true;
        }

        if (checklistInsertDataGetterSetters.size() > 0) {
            final Dialog dialog = new Dialog(AssetActivity.this);
            dialog.setCancelable(false);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.checklist_dialogue_layout);
            listView = (ListView) dialog.findViewById(R.id.lv_checklist);
            listView.setAdapter(new MyAdaptor(context));
            Button btnsave = (Button) dialog.findViewById(R.id.btn_save_checklist);
            Button btncancel = (Button) dialog.findViewById(R.id.btn_cancel_checklist);

            if (update_flag) {
                btnsave.setText("UPDATE");
            }

            btncancel.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    dialog.cancel();
                }
            });

            btnsave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listView.clearFocus();
                    boolean isvalid = true;
                    String error_msg = "";

                    for (int i = 0; i < checklistInsertDataGetterSetters.size(); i++) {
                        if (checklistInsertDataGetterSetters.get(i).getChecklist_type().equals("FREETEXT")
                                && checklistInsertDataGetterSetters.get(i).getChecklist_text().equals("")) {
                            isvalid = false;
                            error_msg = "Please fill text in text field";
                            break;
                        } else if (checklistInsertDataGetterSetters.get(i).getChecklist_type().equals("TOGGLE")
                                && checklistInsertDataGetterSetters.get(i).getChecklist_text().equalsIgnoreCase("NO")) {
                            if (checklistInsertDataGetterSetters.get(i).getReason_cd().equals("0")) {
                                isvalid = false;
                                error_msg = "Please select reason";
                                break;
                            }
                        }
                    }

                    if (isvalid) {
                        asset_child.setChecklist(checklistInsertDataGetterSetters);
                        dialog.cancel();
                    } else {
                        Snackbar sb = Snackbar.make(listView, error_msg, Snackbar.LENGTH_SHORT);
                        sb.getView().setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorAccent));
                        sb.show();
                    }
                }
            });
            dialog.show();
        }
    }

    private class MyAdaptor extends BaseAdapter {

        LayoutInflater inflater;

        MyAdaptor(Context context) {
            inflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return checklistInsertDataGetterSetters.size();
        }

        @Override
        public Object getItem(int position) {

            return position;
        }

        @Override
        public long getItemId(int position) {

            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewCheckHolder holder = null;
            if (convertView == null) {
                holder = new ViewCheckHolder();
                convertView = inflater.inflate(R.layout.check_list_child, null);
                holder.dfavail = (TextView) convertView.findViewById(R.id.tv_checklist);
                holder.tbpresent = (ToggleButton) convertView.findViewById(R.id.toggle_checklist);
                holder.etremark = (EditText) convertView.findViewById(R.id.et_checklist);
                holder.reason_lay = (LinearLayout) convertView.findViewById(R.id.lay_reason);
                holder.spin_reason = (Spinner) convertView.findViewById(R.id.spin_reason);
                convertView.setTag(holder);

            } else {
                holder = (ViewCheckHolder) convertView.getTag();
            }

            holder.etremark.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus) {
                        final int position = v.getId();
                        final EditText Caption = (EditText) v;
                        String value1 = Caption.getText().toString();
                        if (value1.equals("")) {
                            checklistInsertDataGetterSetters.get(position).setChecklist_text("");
                        } else {
                            checklistInsertDataGetterSetters.get(position)
                                    .setChecklist_text(value1.toString().replaceAll("[&^<>{}'$]", ""));
                        }
                    }
                }
            });


            holder.tbpresent.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    String val = ((ToggleButton) v).getText().toString();
                    final int position = v.getId();
                    checklistInsertDataGetterSetters.get(position).setChecklist_text(val);
                    listView.invalidateViews();
                }
            });

            holder.dfavail.setId(position);
            holder.etremark.setId(position);
            holder.tbpresent.setId(position);
            holder.dfavail.setText(checklistInsertDataGetterSetters.get(position).getChecklist());
            holder.etremark.setText(checklistInsertDataGetterSetters.get(position).getChecklist_text());

            if (checklistInsertDataGetterSetters.get(position).getChecklist_type().equals("FREETEXT")) {
                holder.etremark.setVisibility(View.VISIBLE);
                holder.tbpresent.setVisibility(View.GONE);
            } else if (checklistInsertDataGetterSetters.get(position).getChecklist_type().equals("TOGGLE")) {

                holder.etremark.setVisibility(View.GONE);
                holder.tbpresent.setVisibility(View.VISIBLE);

                if (checklistInsertDataGetterSetters.get(position).getChecklist_text().equals("YES")) {
                    holder.tbpresent.setChecked(true);
                    holder.reason_lay.setVisibility(View.GONE);
                } else {

                    holder.tbpresent.setChecked(false);
                    holder.reason_lay.setVisibility(View.VISIBLE);
                    final ArrayList<AssetChecklistReasonGettersetter> reason_list = db.getAssetCheckListReasonData(checklistInsertDataGetterSetters.get(position).getChecklist_id());
                    AssetChecklistReasonGettersetter select = new AssetChecklistReasonGettersetter();
                    select.setReason("Select");
                    select.setReason_id("0");
                    reason_list.add(0, select);

                    holder.spin_reason.setAdapter(new ReasonSpinnerAdapter(AssetActivity.this, R.layout.spinner_text_view, reason_list));

                    for (int i = 0; i < reason_list.size(); i++) {
                        if (reason_list.get(i).getReason_id().equals(checklistInsertDataGetterSetters.get(position).getReason_cd())) {
                            holder.spin_reason.setSelection(i);
                            break;
                        }
                    }

                    holder.spin_reason.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                            AssetChecklistReasonGettersetter ans = reason_list.get(pos);
                            checklistInsertDataGetterSetters.get(position).setReason_cd(ans.getReason_id());
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                }

            }

            return convertView;
        }

    }

    private class ViewCheckHolder {
        TextView dfavail;
        ToggleButton tbpresent;
        EditText etremark;
        LinearLayout reason_lay;
        Spinner spin_reason;

    }

    public class ReasonSpinnerAdapter extends ArrayAdapter<AssetChecklistReasonGettersetter> {
        List<AssetChecklistReasonGettersetter> list;
        Context context;
        int resourceId;

        public ReasonSpinnerAdapter(Context context, int resourceId, ArrayList<AssetChecklistReasonGettersetter> list) {
            super(context, resourceId, list);
            this.context = context;
            this.list = list;
            this.resourceId = resourceId;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater inflater = getLayoutInflater();
                convertView = inflater.inflate(resourceId, parent, false);
            }
            TextView txt_spinner = (TextView) convertView.findViewById(R.id.txt_sp_text);
            txt_spinner.setText(list.get(position).getReason());
            return convertView;
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater inflater = getLayoutInflater();
                convertView = inflater.inflate(resourceId, parent, false);
            }
            TextView txt_spinner = (TextView) convertView.findViewById(R.id.txt_sp_text);
            txt_spinner.setText(list.get(position).getReason());
            return convertView;
        }

    }

    public void showSkuDialog(final AssetInsertdataGetterSetter asset_child) {

        final ArrayList<SkuGetterSetter> skuAddedList = asset_child.getSkulist();

        final SkuGetterSetter[] sku_selected = new SkuGetterSetter[1];
        final BrandGetterSetter[] brand_selected = new BrandGetterSetter[1];

        final ArrayList<BrandGetterSetter> brandList = db.getBrandDataForPaidvisibility(store_cd, asset_child.getCategory_cd());
        BrandGetterSetter brand = new BrandGetterSetter();
        brand.setBrand("select");
        brand.setBrand_cd("0");
        brandList.add(0, brand);

        final Dialog dialog = new Dialog(AssetActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.t2p_sku_dialog_layout);
        //pb = (ProgressBar) dialog.findViewById(R.id.progressBar1);
        //dialog.setCancelable(false);
        final Spinner spinner_brand = (Spinner) dialog.findViewById(R.id.spinner_brand);
        final Spinner spinner_sku = (Spinner) dialog.findViewById(R.id.spinner_sku);
        Button btn_add = (Button) dialog.findViewById(R.id.btn_add);
        Button btn_cancel = (Button) dialog.findViewById(R.id.btn_cancel);
        final EditText et_stock = (EditText) dialog.findViewById(R.id.et_stock);
        final RecyclerView rec_sku = (RecyclerView) dialog.findViewById(R.id.rec_sku);


        final ArrayList<SkuGetterSetter> sku_list = new ArrayList<>();

        if (skuAddedList.size() > 0) {

            rec_sku.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            SkuAddedAdapter skuAdapter = new SkuAddedAdapter(skuAddedList);
            rec_sku.setAdapter(skuAdapter);

        }

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isDuplicate = false;
                if (brand_selected[0] == null || sku_selected[0] == null || et_stock.getText().toString().equals("")) {
                    Snackbar.make(v, getResources().getString(R.string.enter_the_values), Snackbar.LENGTH_SHORT).show();
                } else {
                    if (skuAddedList.size() > 0) {
                        for (int i = 0; i < skuAddedList.size(); i++) {
                            if (skuAddedList.get(i).getSKU_ID().equalsIgnoreCase(sku_selected[0].getSKU_ID())) {
                                isDuplicate = true;
                                break;
                            }
                        }
                    }
                    if (isDuplicate) {
                        Snackbar.make(v, getResources().getString(R.string.entry_already_exist), Snackbar.LENGTH_SHORT).show();
                    } else {
                        SkuGetterSetter sku = new SkuGetterSetter();
                        sku.setBRAND_ID(brand_selected[0].getBrand_cd().get(0));
                        sku.setBRAND(brand_selected[0].getBrand().get(0));
                        sku.setSKU(sku_selected[0].getSKU());
                        sku.setSKU_ID(sku_selected[0].getSKU_ID());
                        sku.setQUANTITY(et_stock.getText().toString().replaceFirst("^0+(?!$)", ""));

                        skuAddedList.add(sku);

                        rec_sku.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        SkuAddedAdapter skuAdapter = new SkuAddedAdapter(skuAddedList);
                        rec_sku.setAdapter(skuAdapter);

                        et_stock.setText("");
                        spinner_brand.setSelection(0);

                        SkuGetterSetter select = new SkuGetterSetter();
                        select.setSKU(getString(R.string.select));
                        sku_list.clear();
                        sku_list.add(select);
                        CustomSkuAdapter skuadapter = new CustomSkuAdapter(AssetActivity.this, R.layout.custom_spinner_item, sku_list);
                        spinner_sku.setAdapter(skuadapter);

                        spinner_sku.setSelection(0);

                        brand_selected[0] = null;
                        sku_selected[0] = null;
                    }

                }

            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                listAdapter.notifyDataSetChanged();
            }
        });

        // Create custom adapter object ( see below CustomAdapter.java )
        CustomAdapter adapter = new CustomAdapter(AssetActivity.this, R.layout.custom_spinner_item, brandList);
        // Set adapter to spinner
        spinner_brand.setAdapter(adapter);
        SkuGetterSetter select = new SkuGetterSetter();
        select.setSKU(getString(R.string.select));
        sku_list.add(select);
        CustomSkuAdapter skuadapter = new CustomSkuAdapter(AssetActivity.this, R.layout.custom_spinner_item, sku_list);
        spinner_sku.setAdapter(skuadapter);


        spinner_brand.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position != 0) {

                    sku_list.clear();

                    brand_selected[0] = brandList.get(position);

                    String brand_id = brandList.get(position).getBrand_cd().get(0);

                    ArrayList<SkuGetterSetter> temp_list = db.getSkuDataForPaidvisibility(store_cd, brand_id);

                    for (int k = 0; k < temp_list.size(); k++) {
                        sku_list.add(temp_list.get(k));
                    }

                    SkuGetterSetter select = new SkuGetterSetter();
                    select.setSKU(getString(R.string.select));
                    select.setSKU_ID("0");
                    sku_list.add(0, select);
                    // Create custom adapter object ( see below CustomSkuAdapter.java )
                    CustomSkuAdapter skuadapter = new CustomSkuAdapter(AssetActivity.this, R.layout.custom_spinner_item, sku_list);
                    // Set adapter to spinner
                    spinner_sku.setAdapter(skuadapter);

                    spinner_sku.setSelection(0);

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner_sku.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position != 0) {

                    sku_selected[0] = sku_list.get(position);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        dialog.setCancelable(false);
        dialog.show();

    }

    public class SkuAddedAdapter extends RecyclerView.Adapter<SkuAddedAdapter.ViewHolder> {

        private ArrayList<SkuGetterSetter> list;
        LayoutInflater inflater;

        public SkuAddedAdapter(ArrayList<SkuGetterSetter> skuList) {
            list = skuList;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = inflater.inflate(R.layout.sku_added_item_layout, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {

            final SkuGetterSetter mItem = list.get(position);
            holder.tv_brand.setText(mItem.getBRAND());
            holder.tv_sku.setText(mItem.getSKU().trim());
            holder.tv_stock.setText(mItem.getQUANTITY());
            holder.btn_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    list.remove(position);
                    SkuAddedAdapter.this.notifyDataSetChanged();
                }
            });

        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final ImageButton btn_delete;
            public final LinearLayout parentLayout;
            public final TextView tv_brand, tv_sku, tv_stock;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                tv_brand = (TextView) mView.findViewById(R.id.tv_brand);
                tv_sku = (TextView) mView.findViewById(R.id.tv_sku);
                tv_stock = (TextView) mView.findViewById(R.id.tv_stock);
                parentLayout = (LinearLayout) mView.findViewById(R.id.parent_layout);
                btn_delete = (ImageButton) mView.findViewById(R.id.delete);
            }

        }
    }

    public class CustomSkuAdapter extends ArrayAdapter<String> {

        SkuGetterSetter tempValues = null;
        LayoutInflater inflater;
        private Activity activity;
        private ArrayList data;

        /*************
         * CustomAdapter Constructor
         *****************/
        public CustomSkuAdapter(
                AssetActivity activitySpinner,
                int textViewResourceId,
                ArrayList objects
        ) {
            super(activitySpinner, textViewResourceId, objects);

            /********** Take passed values **********/
            activity = activitySpinner;
            data = objects;
            /***********  Layout inflator to call external xml layout () **********************/
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        // This funtion called for each row ( Called data.size() times )
        public View getCustomView(int position, View convertView, ViewGroup parent) {

            /********** Inflate spinner_rows.xml file for each row ( Defined below ) ************/
            View row = inflater.inflate(R.layout.custom_spinner_item, parent, false);

            /***** Get each Model object from Arraylist ********/
            tempValues = null;
            tempValues = (SkuGetterSetter) data.get(position);

            TextView label = (TextView) row.findViewById(R.id.tv_text);

            if (position == 0) {

                // Default selected Spinner item
                label.setText(getString(R.string.select));
                //sub.setText("");
            } else {
                // Set values for spinner each row
                label.setText(tempValues.getSKU());
            }

            return row;
        }
    }

    public class CustomAdapter extends ArrayAdapter<String> {

        BrandGetterSetter tempValues = null;
        LayoutInflater inflater;
        private Activity activity;
        private ArrayList data;

        /*************
         * CustomAdapter Constructor
         *****************/
        public CustomAdapter(
                AssetActivity activitySpinner,
                int textViewResourceId,
                ArrayList objects
        ) {
            super(activitySpinner, textViewResourceId, objects);
            /********** Take passed values **********/
            activity = activitySpinner;
            data = objects;
            /***********  Layout inflator to call external xml layout () **********************/
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        // This funtion called for each row ( Called data.size() times )
        public View getCustomView(int position, View convertView, ViewGroup parent) {

            /********** Inflate spinner_rows.xml file for each row ( Defined below ) ************/
            View row = inflater.inflate(R.layout.custom_spinner_item, parent, false);

            /***** Get each Model object from Arraylist ********/
            tempValues = null;
            tempValues = (BrandGetterSetter) data.get(position);

            TextView label = (TextView) row.findViewById(R.id.tv_text);

            if (position == 0) {
                // Default selected Spinner item
                label.setText(getString(R.string.select));
            } else {
                // Set values for spinner each row
                label.setText(tempValues.getBrand().get(0));
            }

            return row;
        }
    }

    public void showPlanogram(String planogram_image) {

        final Dialog dialog = new Dialog(AssetActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.planogram_dialog_layout);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.setCancelable(false);
        WebView webView = (WebView) dialog.findViewById(R.id.webview);
        webView.setWebViewClient(new MyWebViewClient());

        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        String html = "<html><head></head><body><img src=\"" + planogram_image + "\"></body></html>";
        webView.loadDataWithBaseURL("", html, "text/html", "utf-8", "");

        dialog.show();

        ImageView cancel = (ImageView) dialog.findViewById(R.id.img_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                dialog.dismiss();
            }
        });
    }

    private class MyWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            view.clearCache(true);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }
    }

    void declaration() {
        context = this;
        expListView = (ExpandableListView) findViewById(R.id.lvExp);
        btnSave = (Button) findViewById(R.id.save_btn);
        img = (ImageView) findViewById(R.id.imgnodata);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        db = new HimalayaDb(getApplicationContext());
        db.open();
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        store_cd = preferences.getString(CommonString.KEY_STORE_CD, null);
        visit_date = preferences.getString(CommonString.KEY_DATE, null);
        username = preferences.getString(CommonString.KEY_USERNAME, null);
        setTitle("Paid Visibility - " + visit_date);
        str = CommonString.FILE_PATH;
        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<>();
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

}
