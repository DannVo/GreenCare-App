package com.example.iotapplication.Async;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.example.iotapplication.API.AsyncCallBack;
import com.example.iotapplication.API.UserAPIs;
import com.example.iotapplication.Adapter.AsyncPostDataIoT.GetDataIoT;
import com.example.iotapplication.Adapter.AsyncPostDataIoT.PostDataIoTResponse;
import com.example.iotapplication.GetPostActivity;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostAsyncTask extends AsyncTask<GetDataIoT, Void, String> {

    SharedPreferences dataIotSharedPref;
    Context context;

    Float temp, mois, humid;
    private GetPostActivity activity;
    private AsyncCallBack asyncCallBack;
    private ProgressBar progressBar;

    public PostAsyncTask setInstance(Context context, ProgressBar progressBar){
        this.activity = (GetPostActivity) context;
        asyncCallBack = (AsyncCallBack) context;
        this.progressBar = progressBar;
        return this;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected String doInBackground(GetDataIoT... getDataIoTS) {
        String response1 = "";
        BufferedReader reader = null;
        StringBuilder sb = new StringBuilder();
//        GetDataIoT data_response= new GetDataIoT("DV00001","Cay_Mai",21.7f,32.5f,123);
//        PostDataIoTResponse data_response = null;

        try {
            GetDataIoT data = getDataIoTS[0];


            UserAPIs.userApi.storeDataIot(data).enqueue(new Callback<PostDataIoTResponse>() {
                @Override
                public void onResponse(Call<PostDataIoTResponse> call, Response<PostDataIoTResponse> response) {
                    Log.i("Tag:","getData "+ response.raw());
                    try {
                        JSONObject jsonObject = new JSONObject(new Gson().toJson(response.body()));
                        Log.i("Tag:","getData "+ jsonObject.toString());

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
//                    if (response.isSuccessful()){
//                        PostDataIoTResponse postDataIoTResponse = response.body();
//                        DataIoTResponse get = postDataIoTResponse.getDataIoT();
//                        dataIotSharedPref = activity.getSharedPreferences("dataIot", Context.MODE_PRIVATE);
//
//                        Log.i("Tag:","getData "+ get.getId().toString());
//                        SharedPreferences.Editor homeEditor = dataIotSharedPref.edit();
//                        homeEditor.putString("iot_id",get.getId());
//                        homeEditor.putString("iot_device_id",get.getDevice_id());
//                        homeEditor.putString("iot_name",get.getName());
//                        homeEditor.putFloat("iot_hum",get.getHumidity());
//                        homeEditor.putFloat("iot_mois",get.getMoisture());
//                        homeEditor.putFloat("iot_temp",get.getTemperature());
//                        homeEditor.putString("iot_status",get.getPredict_status());
//                        homeEditor.commit();
//
//
//                        Log.i("CHECK","THE RESULT " + get.getDevice_id()+" " + get.getId()+" " + get.getName()+" " + get.getHumidity()+" "
//                                + get.getMoisture()+" " + get.getTemperature()+" " + get.getPredict_status()+" ");
//                    }

                }

                @Override
                public void onFailure(Call<PostDataIoTResponse> call, Throwable t) {
//                    Toast.makeText(, "Cannot Find ID", Toast.LENGTH_SHORT).show();
                }
            });
//            URL url = new URL("http://10.0.2.2:50903/api/core/data/prediction/");
//
////            String data = strings[0];
//            Log.e("NOTICE","DATA: "+data.toString());
//
//            URLConnection conn = url.openConnection();
//            conn.setDoOutput(true);
//
////            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
////            conn.setDoOutput(true);
////            conn.setRequestMethod("POST");
//
//
//            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
//
//
//            wr.write(data.toString());
////            wr.write(data.getDevice_id());
////            wr.write(data.getName());
////            wr.write(String.valueOf(data.getTemperature()));
////            wr.write(String.valueOf(data.getHumidity()));
////            wr.write(String.valueOf(data.getMoisture()));
//            wr.flush();
//
//            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//
//            String line = null;
//            while ((line = reader.readLine())!=null){
//                sb.append(line + "\n");
//
//            }
////            data_response = sb;
//            response = sb.toString();


        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try{
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.GONE);
                    }
                });
                reader.close();
            }catch (Exception e){
            }
        }
        return "SUCCESSFUL";
    }

//    @Override
//    protected void onPostExecute(PostDataIoTResponse postDataIoTResponse) {
//        super.onPostExecute(postDataIoTResponse);
//        asyncCallBack.setDataResult(postDataIoTResponse);
//    }


    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        asyncCallBack.setResult(s);
    }


}
