package com.example.iotapplication.API;

import com.example.iotapplication.Adapter.AddedDeviceAdapter;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

public interface JsonPlaceHolderApi {
    @GET("add")
    Call<List<AddedDeviceAdapter>> getPosts(
            @Query("userId") Integer[] userId,
            @Query("_sort") String sort,
            @Query("_order") String order
    );

    @GET("add")
    Call<List<AddedDeviceAdapter>> getPosts(@QueryMap Map<String, String> parameters);

    @GET("add/{id}/comments")
    Call<List<AddedDeviceAdapter>> getDevice(@Path("id") int postId);

    @GET
    Call<List<AddedDeviceAdapter>> getDevice(@Url String url);
}
