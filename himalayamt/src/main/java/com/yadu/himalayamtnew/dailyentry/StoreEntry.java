package com.yadu.himalayamtnew.dailyentry;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.yadu.himalayamtnew.R;
import com.yadu.himalayamtnew.constants.CommonString;
import com.yadu.himalayamtnew.database.HimalayaDb;
import com.yadu.himalayamtnew.xmlGetterSetter.Audit_QuestionGetterSetter;
import com.yadu.himalayamtnew.xmlGetterSetter.NavMenuItemGetterSetter;
import com.yadu.himalayamtnew.xmlGetterSetter.StockGetterSetter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StoreEntry extends AppCompatActivity {
    HimalayaDb db;
    private SharedPreferences preferences;
    String store_cd, visit_date;
    String user_type = "";
    ValueAdapter adapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_entry);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        db = new HimalayaDb(getApplicationContext());
        db.open();

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        visit_date = preferences.getString(CommonString.KEY_DATE, null);
        store_cd = preferences.getString(CommonString.KEY_STORE_CD, null);
        user_type = preferences.getString(CommonString.KEY_USER_TYPE, null);
        setTitle("Store Entry - " + visit_date);

    }

    @Override
    protected void onResume() {
        super.onResume();
        recyclerView = (RecyclerView) findViewById(R.id.drawer_layout_recycle);
        adapter = new ValueAdapter(getApplicationContext(), getdata());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == android.R.id.home) {
            // NavUtils.navigateUpFromSameTask(this);
            overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public class ValueAdapter extends RecyclerView.Adapter<ValueAdapter.MyViewHolder> {
        private LayoutInflater inflator;
        List<NavMenuItemGetterSetter> data = Collections.emptyList();

        public ValueAdapter(Context context, List<NavMenuItemGetterSetter> data) {
            inflator = LayoutInflater.from(context);
            this.data = data;
        }

        @Override
        public ValueAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int i) {
            View view = inflator.inflate(R.layout.custom_row, parent, false);
            MyViewHolder holder = new MyViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(final ValueAdapter.MyViewHolder viewHolder, final int position) {
            final NavMenuItemGetterSetter current = data.get(position);
            //viewHolder.txt.setText(current.txt);

            viewHolder.icon.setImageResource(current.getIconImg());
            viewHolder.icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (current.getIconImg() == R.drawable.opening_stock || current.getIconImg() == R.drawable.opening_stock_done) {
                        if (!db.isClosingDataFilled(store_cd)) {
                            Intent in1 = new Intent(getApplicationContext(), OpeningStock.class);
                            startActivity(in1);
                            overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
                        } else {
                            Snackbar.make(recyclerView, "Data cannot be changed", Snackbar.LENGTH_SHORT).show();
                        }
                    }

                    if (current.getIconImg() == R.drawable.midday_stock || current.getIconImg() == R.drawable.midday_stock_done) {
                        if (db.isClosingDataFilled(store_cd)) {
                            Snackbar.make(recyclerView, "Data cannot be changed", Snackbar.LENGTH_SHORT).show();

                        } else if (db.isOpeningDataFilled(store_cd)) {
                            Intent in3 = new Intent(getApplicationContext(), MidDayStock.class);
                            startActivity(in3);
                            overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
                        } else {
                            Snackbar.make(recyclerView, "First fill Opening Stock Data", Snackbar.LENGTH_SHORT).show();
                        }
                    }

                    if (current.getIconImg() == R.drawable.closing_stock || current.getIconImg() == R.drawable.closing_stock_done) {
                        if (db.isOpeningDataFilled(store_cd)) {
                            if (db.isMiddayDataFilled(store_cd)) {
                                Intent in2 = new Intent(getApplicationContext(), ClosingStock.class);
                                startActivity(in2);
                                overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
                            } else {
                                Snackbar.make(recyclerView, "First fill Midday Stock Data", Snackbar.LENGTH_SHORT).show();
                            }
                        } else {
                            Snackbar.make(recyclerView, "First fill Opening Stock data", Snackbar.LENGTH_SHORT).show();
                        }
                    }

                    if (current.getIconImg() == R.drawable.promotion || current.getIconImg() == R.drawable.promotion_done) {
                        Intent in4 = new Intent(getApplicationContext(), PromotionActivity.class);
                        startActivity(in4);
                        overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
                    }

                    if (current.getIconImg() == R.drawable.asset || current.getIconImg() == R.drawable.asset_done) {
                        Intent in5 = new Intent(getApplicationContext(), AssetActivity.class);
                        startActivity(in5);
                        overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
                    }

                    //Audit
                    if (current.getIconImg() == R.drawable.audit || current.getIconImg() == R.drawable.audit_done) {
                        Intent in7 = new Intent(getApplicationContext(), QuizTabbedActivity.class);
                        startActivity(in7);
                        overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            //TextView txt;
            ImageView icon;

            public MyViewHolder(View itemView) {
                super(itemView);
                //txt=(TextView) itemView.findViewById(R.id.list_txt);
                icon = itemView.findViewById(R.id.list_icon);
            }
        }
    }

    public List<NavMenuItemGetterSetter> getdata() {
        List<NavMenuItemGetterSetter> data = new ArrayList<>();

        int openingImg, middayImg, closingImg, promotionImg, assetImg, audit, additionalImg, competitionImg;

        if (db.isOpeningDataFilled(store_cd)) {
            openingImg = R.drawable.opening_stock_done;
        } else {
            openingImg = R.drawable.opening_stock;
        }

        if (db.isMiddayDataFilled(store_cd)) {
            middayImg = R.drawable.midday_stock_done;
        } else {
            middayImg = R.drawable.midday_stock;
        }

        if (db.isClosingDataFilled(store_cd)) {
            closingImg = R.drawable.closing_stock_done;
        } else {
            closingImg = R.drawable.closing_stock;
        }

        if (db.isAssetDataFilled(store_cd)) {
            assetImg = R.drawable.asset_done;
        } else {
            assetImg = R.drawable.asset;
        }

        if (db.isPromotionDataFilled(store_cd)) {
            promotionImg = R.drawable.promotion_done;
        } else {
            promotionImg = R.drawable.promotion;
        }

        //Audit Option
        ArrayList<Audit_QuestionGetterSetter> cat_list = db.getCategoryQuestionData();

        boolean audit_flag = true;

        for (int i = 0; i < cat_list.size(); i++) {
            if (!db.isAuditDataFilled(store_cd, cat_list.get(i).getCATEGORY_ID().get(0))) {
                audit_flag = false;
                break;
            }
        }
        if (audit_flag) {
            audit = R.drawable.audit_done;
        } else {
            audit = R.drawable.audit;
        }

        if (user_type.equals("Promoter")) {
            int img[] = {openingImg, middayImg, closingImg, promotionImg, assetImg, audit};//, additionalImg, competitionImg};
            for (int i = 0; i < img.length; i++) {

                NavMenuItemGetterSetter recData = new NavMenuItemGetterSetter();
                recData.setIconImg(img[i]);

                data.add(recData);
            }
        } else if (user_type.equals("Merchandiser")) {
            int img[] = {openingImg, promotionImg, assetImg, audit};//, additionalImg, competitionImg};
            for (int i = 0; i < img.length; i++) {

                NavMenuItemGetterSetter recData = new NavMenuItemGetterSetter();
                recData.setIconImg(img[i]);
                data.add(recData);
            }
        }

        return data;
    }



}
