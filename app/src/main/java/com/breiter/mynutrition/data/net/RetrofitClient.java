package com.breiter.mynutrition.data.net;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static final String BASE_URL = "http://breitercoding.info/";

    private static Retrofit getRetrofit() {

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

    }

    private static DatabaseAPI databaseAPI = getRetrofit().create(DatabaseAPI.class);

    public static Call<DatabaseResponse> searchIngredients() {
        return databaseAPI.searchIngredients();
    }
}
