package com.example.iotapplication.API;

import com.example.iotapplication.Adapter.AddNewDevice.AddNewInfo;
import com.example.iotapplication.Adapter.AddNewDevice.GetAllInfo;
import com.example.iotapplication.Adapter.AsyncPostDataIoT.GetDataIoT;
import com.example.iotapplication.Adapter.AsyncPostDataIoT.GetDataIotResponse;
import com.example.iotapplication.Adapter.AsyncPostDataIoT.PostDataIoTResponse;
import com.example.iotapplication.Adapter.EditDeviceData.EditDetailInfo;
import com.example.iotapplication.Adapter.LoginRegister.LoginResponse;
import com.example.iotapplication.Adapter.LoginRegister.RegisUser;
import com.example.iotapplication.Adapter.LoginRegister.User;
import com.example.iotapplication.Adapter.ResponseStatus;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UserAPIs {

    Gson gson = new GsonBuilder()
            .setDateFormat("dd-MM-yyyy HH:mm:ss")
            .create();

    //Link API: localhost:52939
    UserAPIs userApi = new Retrofit.Builder()
            .baseUrl("http://10.0.2.2:62791/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(UserAPIs.class);


    UserAPIs iotApi = new Retrofit.Builder()
            .baseUrl("http://192.168.1.100/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(UserAPIs.class);


//    @FormUrlEncoded
//    @POST("api/users/authentication/login")
//    Call<User> postLoginUser(@Field("username_or_email") String username_or_email,
//                             @Field("password") String password);

    @GET("api/sensors/get-data/")
    Call<GetDataIotResponse> getDataIotRes(@Query("user_id") String user_id);

    @GET("api/core/device/")
    Call<GetAllInfo> callAllInfo(@Query("user_id") String user_id);

    @FormUrlEncoded
    @PUT("api/core/device/{slug}/")
    Call<EditDetailInfo> updateInfo(@Field("name") String name,
                                    @Field("desc") String desc,
                                    @Field("status") String status,
                                    @Path("slug") String slug);

    @DELETE("api/core/device/{slug}/")
    Call<ResponseStatus> deleteDevice(@Path("slug") String slug);

    @FormUrlEncoded
    @POST("api/core/device/")// checked successfully
    Call<AddNewInfo> addNewDevice1(@Field("user_id") String user_id,
                                   @Field("device_id") String device_id,
                                   @Field("name") String name,
                                   @Field("description") String description);

//    @FormUrlEncoded
    @POST("api/core/data/prediction/")// checked successfully
    Call<PostDataIoTResponse> storeDataIot(@Body GetDataIoT getDataIoT);

//    @FormUrlEncoded
    @POST("api/users/authentication/login/")
    Call<LoginResponse> postLoginUser(@Body User user);

    @FormUrlEncoded
    @POST("api/users/authentication/register/")
    Call<RegisUser> postRegisUser(@Field("email") String email,
                                  @Field("username") String username,
                                  @Field("password") String password);

//    @FormUrlEncoded
//    @POST("api/users/authentication/login")
//    Call<User> postLoginUser(@FieldMap Map<String, String> fields);

}
