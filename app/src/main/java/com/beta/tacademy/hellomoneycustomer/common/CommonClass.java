package com.beta.tacademy.hellomoneycustomer.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;

import com.beta.tacademy.hellomoneycustomer.R;
import com.beta.tacademy.hellomoneycustomer.module.httpConnectionModule.OkHttpInitSingtonManager;
import com.beta.tacademy.hellomoneycustomer.module.webhook.WebHook;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.UUID;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by kirkee on 2017. 6. 8..
 */

public class CommonClass {
    public static String getUUID() {
        SharedPreferences sharedPreferences = HelloMoneyCustomerApplication.getInstance().getSharedPreferences("helloMoney", MODE_PRIVATE);
        return sharedPreferences.getString("UUID",null);
    }

    public static void saveUUID() {
        SharedPreferences sharedPreferences = HelloMoneyCustomerApplication.getInstance().getSharedPreferences("helloMoney", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        if(sharedPreferences.getString("UUID",null) == null){
            final TelephonyManager tm = (TelephonyManager) HelloMoneyCustomerApplication.getInstance().getSystemService(Context.TELEPHONY_SERVICE);
            final String tmDevice, tmSerial, androidId;
            tmDevice = String.valueOf(tm.getDeviceId());
            tmSerial = String.valueOf(tm.getSimSerialNumber());
            androidId = String.valueOf(android.provider.Settings.Secure.getString(HelloMoneyCustomerApplication.getInstance().getContentResolver(), android.provider.Settings.Secure.ANDROID_ID));
            UUID deviceUuid = new UUID(androidId.hashCode(), ((long)tmDevice.hashCode() << 32) | tmSerial.hashCode());
            String deviceId = deviceUuid.toString();
            editor.putString("UUID", deviceId);
            editor.apply();
        }
    }

    public static void saveIntro(){
        SharedPreferences sharedPreferences = HelloMoneyCustomerApplication.getInstance().getSharedPreferences("helloMoney", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        if(!sharedPreferences.getBoolean("beenIntro",false)){
            editor.putBoolean("beenIntro", true);
            editor.apply();
        }
    }
    public static boolean getIntro() {
        SharedPreferences sharedPreferences = HelloMoneyCustomerApplication.getInstance().getSharedPreferences("helloMoney", MODE_PRIVATE);
        return sharedPreferences.getBoolean("beenIntro",false);
    }

    /*
    //http 통신으로 HTML을 받아와 원하는 정보만을 파싱하여 저장해두는 AsyncTask
    private class MainAsyncTask extends AsyncTask<Void, Void, ArrayList<JSONArray>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //시작 전에 ProgressBar를 보여주어 사용자와 interact
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList doInBackground(Void... params) {
            boolean flag1;
            boolean flag2;
            Response response1 = null;
            Response response2 = null;
            OkHttpClient toServer1;
            OkHttpClient toServer2;

            JSONObject jsonObject1 = null;
            JSONObject jsonObject2 = null;

            try{
                toServer1 = OkHttpInitSingtonManager.getOkHttpClient();
                toServer2 = OkHttpInitSingtonManager.getOkHttpClient();

                Request request1 = new Request.Builder()
                        .url(String.format(getResources().getString(R.string.my_request_quotation_url), CommonClass.getUUID()))
                        .get()
                        .build();

                Request request2 = new Request.Builder()
                        .url(getResources().getString(R.string.get_post_script_url))
                        .get()
                        .build();

                //동기 방식
                response1 = toServer1.newCall(request1).execute();
                response2 = toServer2.newCall(request2).execute();

                flag1 = response1.isSuccessful();
                flag2 = response1.isSuccessful();

                String returedJSON1;
                String returedJSON2;

                if(flag1 && flag2){ //성공했다면
                    returedJSON1 = response1.body().string();
                    returedJSON2 = response2.body().string();

                    try {
                        jsonObject1 = new JSONObject(returedJSON1);
                        jsonObject2 = new JSONObject(returedJSON2);
                    }catch(JSONException jsone){
                        Log.e("json에러", jsone.toString());
                    }
                }else{
                    return  null;
                }
            }catch (UnknownHostException une) {
            } catch (UnsupportedEncodingException uee) {
            } catch (Exception e) {
            } finally{
                if(response1 != null) {
                    response1.close(); //3.* 이상에서는 반드시 닫아 준다.
                }

                if(response2 != null){
                    response2.close(); //3.* 이상에서는 반드시 닫아 준다.
                }
            }

            if(jsonObject1 != null && jsonObject2 != null){
                try {
                    if(jsonObject1.get("msg").equals("success") && jsonObject2.get("msg").equals("success")){
                        JSONArray data1= jsonObject1.getJSONArray("data");
                        JSONArray data2= jsonObject2.getJSONArray("data");
                        ArrayList<JSONArray> tmp = new ArrayList();
                        tmp.add(data1);
                        tmp.add(data2);
                        return tmp;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else{

            }

            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<JSONArray> result) {
            //RecyclerView에 해더 및 아이템 추가
            //addHeaders();
            //addItems();

            if(result == null){
                new WebHook().execute("안옴");
            }else{

                new WebHook().execute("내 견적 목록");
                for(int i = 0 ; i < result.get(0).length(); i++){
                    try {
                        new WebHook().execute("1           " + i + "                   "+ String.valueOf(result.get(0).get(i)));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                new WebHook().execute("후기 목록");
                for(int i = 0 ; i < result.get(1).length(); i++){
                    try {
                        new WebHook().execute("2           " + i + "                   " + String.valueOf(result.get(1).get(i)));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            //마무리 된 이후에 ProgressBar 제거하고 SwipeRefreshLayout을 사용할 수 있게 설정
            progressBar.setVisibility(View.GONE);
            refreshLayout.setEnabled(true);
        }
    }

    //http 통신으로 HTML을 받아와 원하는 정보만을 파싱하여 저장해두는 AsyncTask
    private class MainAsyncTask2 extends AsyncTask<Void, Void, ArrayList<JSONArray>>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //시작 전에 ProgressBar를 보여주어 사용자와 interact
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList doInBackground(Void... params) {
            boolean flag1;
            Response response1 = null;

            OkHttpClient toServer1;


            JSONObject jsonObject1 = null;

            try{
                toServer1 = OkHttpInitSingtonManager.getOkHttpClient();

                Request request1 = new Request.Builder()
                        .url(String.format(getResources().getString(R.string.my_quotation_list_url), "1"))
                        .get()
                        .build();

                //동기 방식
                response1 = toServer1.newCall(request1).execute();

                flag1 = response1.isSuccessful();

                String returedJSON1;

                if(flag1){ //성공했다면
                    returedJSON1 = response1.body().string();

                    try {
                        jsonObject1 = new JSONObject(returedJSON1);
                    }catch(JSONException jsone){
                        Log.e("json에러", jsone.toString());
                    }
                }else{
                    return  null;
                }
            }catch (UnknownHostException une) {
            } catch (UnsupportedEncodingException uee) {
            } catch (Exception e) {
            } finally{
                if(response1 != null) {
                    response1.close(); //3.* 이상에서는 반드시 닫아 준다.
                }
            }

            if(jsonObject1 != null){
                try {
                    if(jsonObject1.get("msg").equals("success")){
                        JSONArray data1= jsonObject1.getJSONArray("data");
                        ArrayList<JSONArray> tmp = new ArrayList();
                        tmp.add(data1);
                        return tmp;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else{

            }

            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<JSONArray> result) {
            //RecyclerView에 해더 및 아이템 추가
            //addHeaders();
            //addItems();

            if(result == null){
                new WebHook().execute("안옴");
            }else{

                new WebHook().execute("내 견적 리스트");
                for(int i = 0 ; i < result.get(0).length(); i++){
                    try {
                        new WebHook().execute("3           " + i + "                   "+ String.valueOf(result.get(0).get(i)));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            //마무리 된 이후에 ProgressBar 제거하고 SwipeRefreshLayout을 사용할 수 있게 설정
            progressBar.setVisibility(View.GONE);
            refreshLayout.setEnabled(true);
        }
    }

    private class MainAsyncTask3 extends AsyncTask<Void, Void, ArrayList<JSONArray>>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //시작 전에 ProgressBar를 보여주어 사용자와 interact
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList doInBackground(Void... params) {
            boolean flag1;
            Response response1 = null;

            OkHttpClient toServer1;


            JSONObject jsonObject1 = null;

            try{
                toServer1 = OkHttpInitSingtonManager.getOkHttpClient();

                Request request1 = new Request.Builder()
                        .url(String.format(getResources().getString(R.string.my_quotation_detail_url),"1"))
                        .get()
                        .build();
                //동기 방식
                response1 = toServer1.newCall(request1).execute();

                flag1 = response1.isSuccessful();

                String returedJSON1;

                if(flag1){ //성공했다면
                    returedJSON1 = response1.body().string();

                    try {
                        jsonObject1 = new JSONObject(returedJSON1);
                    }catch(JSONException jsone){
                        Log.e("json에러", jsone.toString());
                    }
                }else{
                    return  null;
                }
            }catch (UnknownHostException une) {
            } catch (UnsupportedEncodingException uee) {
            } catch (Exception e) {
            } finally{
                if(response1 != null) {
                    response1.close(); //3.* 이상에서는 반드시 닫아 준다.
                }
            }

            if(jsonObject1 != null){
                try {
                    if(jsonObject1.get("msg").equals("success")){
                        JSONArray data1= jsonObject1.getJSONArray("data");
                        ArrayList<JSONArray> tmp = new ArrayList();
                        tmp.add(data1);
                        return tmp;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else{

            }

            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<JSONArray> result) {
            //RecyclerView에 해더 및 아이템 추가
            //addHeaders();
            //addItems();

            if(result == null){
                new WebHook().execute("안옴");
            }else{

                new WebHook().execute("내 견적 상세");
                for(int i = 0 ; i < result.get(0).length(); i++){
                    try {
                        new WebHook().execute("4           " + i + "                   "+ String.valueOf(result.get(0).get(i)));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            //마무리 된 이후에 ProgressBar 제거하고 SwipeRefreshLayout을 사용할 수 있게 설정
            progressBar.setVisibility(View.GONE);
            refreshLayout.setEnabled(true);
        }
    }

    private class MainAsyncTask4 extends AsyncTask<Void, Void, ArrayList<JSONArray>>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //시작 전에 ProgressBar를 보여주어 사용자와 interact
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList doInBackground(Void... params) {
            boolean flag1;
            Response response1 = null;

            OkHttpClient toServer1;


            JSONObject jsonObject1 = null;

            try{
                toServer1 = OkHttpInitSingtonManager.getOkHttpClient();

                Request request1 = new Request.Builder()
                        .url(String.format(getResources().getString(R.string.quotation_detail_counselor_feedback_url),"1"))
                        .get()
                        .build();

                //동기 방식
                response1 = toServer1.newCall(request1).execute();

                flag1 = response1.isSuccessful();

                String returedJSON1;

                if(flag1){ //성공했다면
                    returedJSON1 = response1.body().string();

                    try {
                        jsonObject1 = new JSONObject(returedJSON1);
                    }catch(JSONException jsone){
                        Log.e("json에러", jsone.toString());
                    }
                }else{
                    return  null;
                }
            }catch (UnknownHostException une) {
            } catch (UnsupportedEncodingException uee) {
            } catch (Exception e) {
            } finally{
                if(response1 != null) {
                    response1.close(); //3.* 이상에서는 반드시 닫아 준다.
                }
            }

            if(jsonObject1 != null){
                try {
                    if(jsonObject1.get("msg").equals("success")){
                        JSONArray data1= jsonObject1.getJSONArray("data");
                        ArrayList<JSONArray> tmp = new ArrayList();
                        tmp.add(data1);
                        return tmp;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else{

            }

            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<JSONArray> result) {
            //RecyclerView에 해더 및 아이템 추가
            //addHeaders();
            //addItems();

            if(result == null){
                new WebHook().execute("안옴");
            }else{

                new WebHook().execute("내 견적 상담 피드백 리스트");
                for(int i = 0 ; i < result.get(0).length(); i++){
                    try {
                        new WebHook().execute("5           " + i + "                   " + String.valueOf(result.get(0).get(i)));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            //마무리 된 이후에 ProgressBar 제거하고 SwipeRefreshLayout을 사용할 수 있게 설정
            progressBar.setVisibility(View.GONE);
            refreshLayout.setEnabled(true);
        }
    }


    private class MainAsyncTask5 extends AsyncTask<Void, Void, Integer>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //시작 전에 ProgressBar를 보여주어 사용자와 interact
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Integer doInBackground(Void... params) {
            boolean flag1;
            Response response1 = null;

            OkHttpClient toServer1;


            JSONObject jsonObject1 = null;

            try{
                toServer1 = OkHttpInitSingtonManager.getOkHttpClient();

                RequestBody postBody = new FormBody.Builder()
                        .add("selectedEstimateId","7")
                        .add("status","상담중")
                        .build();

                Request request1 = new Request.Builder()
                        .url(String.format(getResources().getString(R.string.request_counsel_url),"1"))
                        .put(postBody)
                        .build();

                //동기 방식
                response1 = toServer1.newCall(request1).execute();

                flag1 = response1.isSuccessful();

                String returedJSON1;

                if(flag1){ //성공했다면
                    returedJSON1 = response1.body().string();

                    try {
                        jsonObject1 = new JSONObject(returedJSON1);
                    }catch(JSONException jsone){
                        Log.e("json에러", jsone.toString());
                    }
                }else{
                    return  null;
                }
            }catch (UnknownHostException une) {
            } catch (UnsupportedEncodingException uee) {
            } catch (Exception e) {
            } finally{
                if(response1 != null) {
                    response1.close(); //3.* 이상에서는 반드시 닫아 준다.
                }
            }

            if(jsonObject1 != null){
                try {
                    if(jsonObject1.get("msg").equals("success")){
                        //JSONArray data1= jsonObject1.getJSONArray("data");
                        //ArrayList<JSONArray> tmp = new ArrayList();
                        //tmp.add(data1);
                        return 1;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else{

            }

            return 0;
        }

        @Override
        protected void onPostExecute(Integer result) {
            //RecyclerView에 해더 및 아이템 추가
            //addHeaders();
            //addItems();

            if(result == 0){
                new WebHook().execute("안옴");
            }else{
                new WebHook().execute("상담 신청하기 성공");
            }

            //마무리 된 이후에 ProgressBar 제거하고 SwipeRefreshLayout을 사용할 수 있게 설정
            progressBar.setVisibility(View.GONE);
            refreshLayout.setEnabled(true);
        }
    }

    private class MainAsyncTask6 extends AsyncTask<Void, Void, Integer>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //시작 전에 ProgressBar를 보여주어 사용자와 interact
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Integer doInBackground(Void... params) {
            boolean flag1;
            Response response1 = null;

            OkHttpClient toServer1;


            JSONObject jsonObject1 = null;

            try{
                toServer1 = OkHttpInitSingtonManager.getOkHttpClient();

                RequestBody postBody = new FormBody.Builder()
                        .add("requestId","1")
                        .add("content","하하")
                        .add("score","4.5")
                        .build();

                Request request1 = new Request.Builder()
                        .url(getResources().getString(R.string.write_post_script_url))
                        .put(postBody)
                        .build();

                //동기 방식
                response1 = toServer1.newCall(request1).execute();

                flag1 = response1.isSuccessful();

                String returedJSON1;

                if(flag1){ //성공했다면
                    returedJSON1 = response1.body().string();

                    try {
                        jsonObject1 = new JSONObject(returedJSON1);
                    }catch(JSONException jsone){
                        Log.e("json에러", jsone.toString());
                    }
                }else{
                    return  null;
                }
            }catch (UnknownHostException une) {
            } catch (UnsupportedEncodingException uee) {
            } catch (Exception e) {
            } finally{
                if(response1 != null) {
                    response1.close(); //3.* 이상에서는 반드시 닫아 준다.
                }
            }

            if(jsonObject1 != null){
                try {
                    if(jsonObject1.get("msg").equals("success")){
                        //JSONArray data1= jsonObject1.getJSONArray("data");
                        //ArrayList<JSONArray> tmp = new ArrayList();
                        //tmp.add(data1);
                        return 1;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else{

            }

            return 0;
        }

        @Override
        protected void onPostExecute(Integer result) {
            //RecyclerView에 해더 및 아이템 추가
            //addHeaders();
            //addItems();

            if(result == 0){
                new WebHook().execute("안옴");
            }else{
                new WebHook().execute("후기 작성 성공");
            }

            //마무리 된 이후에 ProgressBar 제거하고 SwipeRefreshLayout을 사용할 수 있게 설정
            progressBar.setVisibility(View.GONE);
            refreshLayout.setEnabled(true);
        }
    }

    private class MainAsyncTask7 extends AsyncTask<Void, Void, ArrayList<JSONArray>>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //시작 전에 ProgressBar를 보여주어 사용자와 interact
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList doInBackground(Void... params) {
            boolean flag1;
            Response response1 = null;

            OkHttpClient toServer1;


            JSONObject jsonObject1 = null;

            try{
                toServer1 = OkHttpInitSingtonManager.getOkHttpClient();

                Request request1 = new Request.Builder()
                        .url(String.format(getResources().getString(R.string.detail_counselor_url),"agent1@naver.com"))
                        .get()
                        .build();

                //동기 방식
                response1 = toServer1.newCall(request1).execute();

                flag1 = response1.isSuccessful();

                String returedJSON1;

                if(flag1){ //성공했다면
                    returedJSON1 = response1.body().string();

                    try {
                        jsonObject1 = new JSONObject(returedJSON1);
                    }catch(JSONException jsone){
                        Log.e("json에러", jsone.toString());
                    }
                }else{
                    return  null;
                }
            }catch (UnknownHostException une) {
            } catch (UnsupportedEncodingException uee) {
            } catch (Exception e) {
            } finally{
                if(response1 != null) {
                    response1.close(); //3.* 이상에서는 반드시 닫아 준다.
                }
            }

            if(jsonObject1 != null){
                try {
                    if(jsonObject1.get("msg").equals("success")){
                        JSONArray data1= jsonObject1.getJSONArray("data");
                        ArrayList<JSONArray> tmp = new ArrayList();
                        tmp.add(data1);
                        return tmp;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else{

            }

            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<JSONArray> result) {
            //RecyclerView에 해더 및 아이템 추가
            //addHeaders();
            //addItems();

            if(result == null){
                new WebHook().execute("안옴");
            }else{

                new WebHook().execute("대출 모집인 상세");
                for(int i = 0 ; i < result.get(0).length(); i++){
                    try {
                        new WebHook().execute("6           " + i + "                   " + String.valueOf(result.get(0).get(i)));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            //마무리 된 이후에 ProgressBar 제거하고 SwipeRefreshLayout을 사용할 수 있게 설정
            progressBar.setVisibility(View.GONE);
            refreshLayout.setEnabled(true);
        }
    }

    private class MainAsyncTask8 extends AsyncTask<Void, Void, ArrayList<JSONArray>>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //시작 전에 ProgressBar를 보여주어 사용자와 interact
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList doInBackground(Void... params) {
            boolean flag1;
            Response response1 = null;

            OkHttpClient toServer1;


            JSONObject jsonObject1 = null;

            try{
                toServer1 = OkHttpInitSingtonManager.getOkHttpClient();

                Request request1 = new Request.Builder()
                        .url(String.format(getResources().getString(R.string.detail_counselor_post_script_list_url),"agent1@naver.com"))
                        .get()
                        .build();

                //동기 방식
                response1 = toServer1.newCall(request1).execute();

                flag1 = response1.isSuccessful();

                String returedJSON1;

                if(flag1){ //성공했다면
                    returedJSON1 = response1.body().string();

                    try {
                        jsonObject1 = new JSONObject(returedJSON1);
                    }catch(JSONException jsone){
                        Log.e("json에러", jsone.toString());
                    }
                }else{
                    return  null;
                }
            }catch (UnknownHostException une) {
            } catch (UnsupportedEncodingException uee) {
            } catch (Exception e) {
            } finally{
                if(response1 != null) {
                    response1.close(); //3.* 이상에서는 반드시 닫아 준다.
                }
            }

            if(jsonObject1 != null){
                try {
                    if(jsonObject1.get("msg").equals("success")){
                        JSONArray data1= jsonObject1.getJSONArray("data");
                        ArrayList<JSONArray> tmp = new ArrayList();
                        tmp.add(data1);
                        return tmp;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else{

            }

            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<JSONArray> result) {
            //RecyclerView에 해더 및 아이템 추가
            //addHeaders();
            //addItems();

            if(result == null){
                new WebHook().execute("안옴");
            }else{

                new WebHook().execute("대출 모집인 후기 리스트");
                for(int i = 0 ; i < result.get(0).length(); i++){
                    try {
                        new WebHook().execute("7           " + i + "                   " + String.valueOf(result.get(0).get(i)));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            //마무리 된 이후에 ProgressBar 제거하고 SwipeRefreshLayout을 사용할 수 있게 설정
            progressBar.setVisibility(View.GONE);
            refreshLayout.setEnabled(true);
        }
    }
    */
}
