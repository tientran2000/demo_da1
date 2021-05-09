package com.example.demo_da1.API;

import com.example.demo_da1.Object.PostNews;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface APIService {
    Gson gson=new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();

    APIService apiService=new Retrofit.Builder().baseUrl("https://docbao.conveyor.cloud/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(APIService.class);

    @POST("api/tindaxems")
    Call<PostNews> sendPosts(@Body PostNews Post);
    @POST("api/tindaluus")
    Call<PostNews> sendPostLuus(@Body PostNews Post);

    @DELETE("api/tindaluus/{id}")
    Call<Void> sendDeleteLuu(@Path("id")String id);
    @DELETE("api/tindaxems/{id}")
    Call<Void> sendDelete(@Path("id")String id);

    //@PUT("api/GiaoViens/{id}")
    //Call<Void> putPost(@Path("id")String id,@Body post p);
}
