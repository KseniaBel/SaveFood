package com.savefood.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

public class RestClient
{
    private static final String BASE_URL = "http://api.edamam.com";
    private ApiService apiService;

    public RestClient()
    {

        Gson gson = new GsonBuilder().create();

        Retrofit restAdapter = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create(gson)).build();

        apiService = restAdapter.create(ApiService.class);
    }

    public ApiService getApiService()
    {
        return apiService;
    }
}