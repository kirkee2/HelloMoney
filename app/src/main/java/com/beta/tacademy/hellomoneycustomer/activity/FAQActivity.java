package com.beta.tacademy.hellomoneycustomer.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.beta.tacademy.hellomoneycustomer.R;
import com.beta.tacademy.hellomoneycustomer.common.CommonClass;
import com.beta.tacademy.hellomoneycustomer.module.httpConnectionModule.OKHttp3ApplyCookieManager;
import com.beta.tacademy.hellomoneycustomer.recyclerViews.FAQRecyclerView.FAQRecyclerViewAdapter;
import com.beta.tacademy.hellomoneycustomer.recyclerViews.FAQRecyclerView.FAQValueObject;
import com.beta.tacademy.hellomoneycustomer.recyclerViews.mainRecyclerView.MainRecyclerViewAdapter;
import com.beta.tacademy.hellomoneycustomer.recyclerViews.mainRecyclerView.MainValueObject;
import com.beta.tacademy.hellomoneycustomer.viewPagers.mainViewpager.MainPageViewPagerObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.beta.tacademy.hellomoneycustomer.R.id.refreshLayout;
public class FAQActivity extends AppCompatActivity {


    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private FAQRecyclerViewAdapter faqRecyclerViewAdapter;
    private ProgressBar progressBar;
    private ArrayList<FAQValueObject> faqValueObjectArrayList;
    private FAQGet faqGet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        faqRecyclerViewAdapter = new FAQRecyclerViewAdapter();
        faqValueObjectArrayList = new ArrayList<>();
;
        faqGet = new FAQGet();

        setSupportActionBar(toolbar); //Toolbar를 현재 Activity의 Actionbar로 설정.

        //Toolbar 설정
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        toolbar.setTitle(getResources().getString(R.string.faq));
        toolbar.setTitleTextColor(ResourcesCompat.getColor(getApplicationContext().getResources(),R.color.normalTypo,null));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false); //RecyclerView에 설정 할 LayoutManager 초기화


        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(faqRecyclerViewAdapter);


        faqGet.execute();
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private class FAQGet extends AsyncTask<Void, Void, Integer> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //시작 전에 ProgressBar를 보여주어 사용자와 interact
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Integer doInBackground(Void... params) {
            boolean flag;
            Response response = null;
            OkHttpClient toServer;

            JSONObject jsonObject = null;

            try{
                toServer = OKHttp3ApplyCookieManager.getOkHttpNormalClient();

                Request request = new Request.Builder()
                        .url(getResources().getString(R.string.faq_url))
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
                                faqValueObjectArrayList.add(new FAQValueObject(String.valueOf(jsonData.get("question")),String.valueOf(jsonData.get("answer"))));
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
                initItems(faqValueObjectArrayList);
                progressBar.setVisibility(View.GONE);
            }else if(result == 1){
            }else{
            }
        }
    }

    public void initItems(ArrayList<FAQValueObject> faqValueObjectArrayList){
        faqRecyclerViewAdapter.initItem(faqValueObjectArrayList);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        if (faqGet.getStatus() == AsyncTask.Status.RUNNING) {
            faqGet.cancel(true);
        }

    }
}
