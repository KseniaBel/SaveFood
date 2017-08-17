package com.savefood.rest;

import com.savefood.rest.object.v2.Response;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Query;

public interface ApiService {
//&q=chicken tomato&from=0&to=1000
    @GET("/search?app_id=bf072c88&app_key=780c0848d34138da4cffc716ef86c22f")
    Call<Response> searchRecipes(@Query("q") String searchQuery,
                                 @Query("from") Integer from,
                                 @Query("to") Integer to);

}
