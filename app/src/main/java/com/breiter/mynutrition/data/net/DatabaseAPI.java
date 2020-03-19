package com.breiter.mynutrition.data.net;

import retrofit2.Call;
import retrofit2.http.GET;

public interface DatabaseAPI {

    @GET("nutritiondatabase.php")
    Call<DatabaseResponse> searchIngredients();
}
