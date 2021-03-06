package com.beta.tacademy.hellomoneycustomer.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.beta.tacademy.hellomoneycustomer.R;
import com.beta.tacademy.hellomoneycustomer.common.formatter.CustomAxisValueFormatter;
import com.beta.tacademy.hellomoneycustomer.common.formatter.CustomValueFormatter;
import com.beta.tacademy.hellomoneycustomer.common.HelloMoneyCustomerApplication;
import com.beta.tacademy.hellomoneycustomer.common.util.TimeUtil;
import com.beta.tacademy.hellomoneycustomer.module.httpConnectionModule.OKHttp3ApplyCookieManager;
import com.bumptech.glide.Glide;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PostscriptDetailActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private int postscriptId;
    private BarChart barChart;
    private ArrayList<Float> rate;
    private TextView finalQuotationCount;
    //private TextView averageInterestRate;
    private TextView bank;
    private TextView name;
    private RelativeLayout goCounselor;
    private ImageView loanType;
    private CircleImageView image;
    //private TextView region;
    private TextView apt;
    private RatingBar starRatingBar;
    private TextView content;
    //private TextView pastTime;
    private ProgressBar progressBar;

    private String imageInfo;
    private String bankInfo;
    private String nameInfo;
    private String loanTypeInfo;
    private String regionInfo;
    private String aptInfo;
    private float starInfo;
    private String contentInfo;
    private String counselorId;
    private String pastTimeInfo;
    private Activity activity;
    private TextView aptTime;
    private TextView minRateText;
    private TextView maxRateText;
    private ImageView next;

    private PostscriptInterest postscriptInterest;
    private PostscriptDetail postscriptDetail;

    private float minRate;
    private float maxRate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postscript_detail);

        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        barChart = (BarChart)findViewById(R.id.barChart);
        rate = new ArrayList<>();
        finalQuotationCount = (TextView)findViewById(R.id.finalQuotationCount);
        //averageInterestRate = (TextView)findViewById(R.id.averageInterestRate);
        bank = (TextView)findViewById(R.id.bank);
        name = (TextView)findViewById(R.id.name);
        goCounselor =(RelativeLayout)findViewById(R.id.goCounselor);
        loanType = (ImageView)findViewById(R.id.loanType);
        image = (CircleImageView)findViewById(R.id.image);
        //region = (TextView)findViewById(R.id.region);
        apt = (TextView)findViewById(R.id.apt);
        starRatingBar = (RatingBar)findViewById(R.id.starRatingBar);
        content = (TextView)findViewById(R.id.content);
        //pastTime = (TextView)findViewById(R.id.pastTime);
        aptTime = (TextView)findViewById(R.id.apt_time);
        maxRateText = (TextView)findViewById(R.id.maxRate);
        minRateText = (TextView)findViewById(R.id.minRate);
        next = (ImageView)findViewById(R.id.next);

        activity = this;
        starRatingBar.setEnabled(false);

        postscriptInterest = new PostscriptInterest();
        postscriptDetail = new PostscriptDetail();

        Intent intent = getIntent();
        postscriptId = intent.getIntExtra("id",-1);

        minRate = 0;
        maxRate = 0;
        if(intent.getBooleanExtra("goCounselor",true)){
            goCounselor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(PostscriptDetailActivity.this,CounselorDetailActivity.class);
                    intent.putExtra("agentId",counselorId);
                    startActivity(intent);
                }
            });
            next.setVisibility(View.VISIBLE);
        }else{
            next.setVisibility(View.GONE);
        }
        setSupportActionBar(toolbar);

        //Toolbar 설정
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }


        //toolbar
        toolbar.setTitle(getResources().getString(R.string.postscript_detail));
        toolbar.setTitleTextColor(ResourcesCompat.getColor(getApplicationContext().getResources(),R.color.title_color,null));

        postscriptInterest.execute();
    }

    private class PostscriptInterest extends AsyncTask<Void, Void, Integer> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Integer doInBackground(Void... params) {
            boolean flag;
            Response response = null;
            OkHttpClient toServer;

            JSONObject jsonObject = null;

            try{
                toServer =  OKHttp3ApplyCookieManager.getOkHttpNormalClient();

                Request request = new Request.Builder()
                        .url(String.format(getResources().getString(R.string.post_script_interest_url),String.valueOf(postscriptId)))
                        .get()
                        .build();

                //동기 방식
                response = toServer.newCall(request).execute();

                flag = response.isSuccessful();

                String returedJSON;

                if(flag){ //성공했다면
                    returedJSON = response.body().string();

                    try {
                        jsonObject = new JSONObject(returedJSON);
                    }catch(JSONException jsone){
                        Log.e("json에러", jsone.toString());
                    }
                }else{
                    return 2;
                }
            }catch (UnknownHostException une) {
            } catch (UnsupportedEncodingException uee) {
            } catch (Exception e) {
            } finally{
                if(response != null) {
                    response.close(); //3.* 이상에서는 반드시 닫아 준다.
                }
            }

            if(jsonObject != null){
                try {
                    if(jsonObject.get(getResources().getString(R.string.url_message)).equals(getResources().getString(R.string.url_success))){
                        JSONArray data= jsonObject.getJSONArray(getResources().getString(R.string.url_data));

                        for(int i = 0 ; i < data.length(); i++){
                            try {
                                JSONObject jsonData = (JSONObject)data.get(i);

                                rate.add((float) jsonData.getDouble("interest_rate"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        return 0;
                    }else if(jsonObject.get(getResources().getString(R.string.url_message)).equals(getResources().getString(R.string.url_no_data))){
                        return 1;
                    }else{
                        return 3;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else{
                return 4;
            }

            return 5;
        }

        @Override
        protected void onPostExecute(Integer result) {
            if(result == 0){

                ArrayList<BarEntry> entries = new ArrayList<>();
                ArrayList<BarEntry> entriesMin = new ArrayList<>();
                float min = 100;
                float max = -100;
                int minIndex = 0;
                int maxIndex = 0;

                for(int i = 0 ; i <rate.size() ; i++){
                    if(rate.get(i) < min){
                        min = rate.get(i);
                        minIndex = i;
                    }

                    if(rate.get(i) > max){
                        max = rate.get(i);
                        maxIndex = i;
                    }


                    entries.add(new BarEntry(i*0.5F,rate.get(i)));
                }

                minRate = rate.get(minIndex);
                maxRate = rate.get(maxIndex);

                entriesMin.add(entries.get(minIndex));
                entries.remove(minIndex);

                Description d = new Description();
                d.setText("");

                BarDataSet dataSet = new BarDataSet(entries, "금리");
                BarDataSet dataSetMin = new BarDataSet(entriesMin, "최저 금리");

                dataSet.setColor(0xFFBDBDBD);
                dataSet.setHighlightEnabled(false);
                dataSet.setValueTextSize(10);
                dataSet.setValueFormatter(new CustomValueFormatter());

                dataSetMin.setColor(0xFF1bb0f5);
                dataSetMin.setHighlightEnabled(false);
                dataSetMin.setValueTextSize(10);
                dataSetMin.setValueFormatter(new CustomValueFormatter());

                BarData data = new BarData(dataSet);
                data.addDataSet(dataSetMin);

                data.setBarWidth(0.15F);
                barChart.setData(data);
                barChart.getAxisRight().setEnabled(false);
                barChart.getXAxis().setEnabled(false);
                barChart.setDescription(d);
                barChart.setEnabled(false);
                barChart.animateY(500);
                barChart.setDoubleTapToZoomEnabled(false);
                barChart.setScaleEnabled(false);
                YAxis yAxis = barChart.getAxisLeft();
                yAxis.setValueFormatter(new CustomAxisValueFormatter());


                barChart.invalidate();

                //
                finalQuotationCount.setText(String.valueOf(rate.size()));
                double averageInterestRateTmp = 0;
                for(int i = 0 ; i < rate.size() ; i++){
                    averageInterestRateTmp += rate.get(i);
                }

                averageInterestRateTmp = averageInterestRateTmp/(double)rate.size();
                double tmp2 = Double.parseDouble(String.format("%.1f",averageInterestRateTmp));
                //averageInterestRate.setText(String.valueOf(tmp2)+"%");
                maxRateText.setText(maxRate + "%");
                minRateText.setText(minRate + "%");

                postscriptDetail.execute();
            }else if(result == 1){
            }else{
            }
        }
    }


    private class PostscriptDetail extends AsyncTask<Void, Void, Integer> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Integer doInBackground(Void... params) {
            boolean flag;
            Response response = null;
            OkHttpClient toServer;

            JSONObject jsonObject = null;

            try{
                toServer =  OKHttp3ApplyCookieManager.getOkHttpNormalClient();

                Request request = new Request.Builder()
                        .url(String.format(getResources().getString(R.string.post_script_detail_url),String.valueOf(postscriptId)))
                        .get()
                        .build();

                //동기 방식
                response = toServer.newCall(request).execute();

                flag = response.isSuccessful();

                String returedJSON;

                if(flag){ //성공했다면
                    returedJSON = response.body().string();

                    try {
                        jsonObject = new JSONObject(returedJSON);
                    }catch(JSONException jsone){
                        Log.e("json에러", jsone.toString());
                    }
                }else{
                    return 2;
                }
            }catch (UnknownHostException une) {
            } catch (UnsupportedEncodingException uee) {
            } catch (Exception e) {
            } finally{
                if(response != null) {
                    response.close(); //3.* 이상에서는 반드시 닫아 준다.
                }
            }

            if(jsonObject != null){
                try {
                    if(jsonObject.get(getResources().getString(R.string.url_message)).equals(getResources().getString(R.string.url_success))){
                        JSONObject data= jsonObject.getJSONObject(getResources().getString(R.string.url_data));

                        imageInfo = data.getString("photo");
                        bankInfo = data.getString("company_name");
                        nameInfo = data.getString("name");
                        loanTypeInfo = data.getString("loan_type");
                        //regionInfo = data.getString("region_1") + " " + data.getString("region_2") + " " + data.getString("region_3");
                        regionInfo = data.getString("region_1") + " " + data.getString("region_2");
                        aptInfo = data.getString("apt_name");
                        starInfo = (float) data.getDouble("score");
                        contentInfo = data.getString("content");
                        counselorId = data.getString("agent_id");
                        pastTimeInfo = data.getString("register_time");

                        return 0;
                    }else if(jsonObject.get(getResources().getString(R.string.url_message)).equals(getResources().getString(R.string.url_no_data))){
                        return 1;
                    }else{
                        return 3;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else{
                return 4;
            }

            return 5;
        }

        @Override
        protected void onPostExecute(Integer result) {
            if(result == 0){
                Glide.with(HelloMoneyCustomerApplication.getInstance())
                        .load(imageInfo)
                        .animate(android.R.anim.slide_in_left)
                        .placeholder(R.drawable.loading)
                        .error(R.drawable.error)
                        .into(image);
                //bank.setText(bankInfo);
                name.setText(nameInfo);

                if(loanTypeInfo.equals("주택담보대출")){
                    loanType.setImageResource(R.drawable.secured_loan);
                }else{
                    loanType.setImageResource(R.drawable.lease_loan);
                }

                //pastTime.setText(TimeUtil.timeParsing(pastTimeInfo));
                //region.setText(regionInfo);

                aptTime.setText(regionInfo + " / " + TimeUtil.timeParsing(pastTimeInfo));
                apt.setText(aptInfo + " 대출 고객");
                starRatingBar.setRating(starInfo);
                starRatingBar.setEnabled(false);
                content.setText(contentInfo);

                progressBar.setVisibility(View.GONE);
            }else if(result == 1){
            }else{
            }
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent();
            setResult(RESULT_OK, intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }


    @Override
    protected void onDestroy(){
        super.onDestroy();
        if (postscriptDetail.getStatus() == AsyncTask.Status.RUNNING) {
            postscriptDetail.cancel(true);
        }

        if (postscriptInterest.getStatus() == AsyncTask.Status.RUNNING) {
            postscriptInterest.cancel(true);
        }

    }
}
