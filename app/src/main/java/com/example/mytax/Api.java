package com.example.mytax;

import java.util.List;

import retrofit.http.GET;
import retrofit2.Call;


public interface Api {

    String BASE_URL = "https://api.myjson.com/bins/";

    @GET("z40as")

    Call<List<Kommune>> getKommune();
}
