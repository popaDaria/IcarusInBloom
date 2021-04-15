package com.sunny.icarusinbloom.webservice;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {
    private static Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
            .baseUrl("https://trefle.io")
            .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = retrofitBuilder.build();

    private static PlantApi plantApi = retrofit.create(PlantApi.class);

    public static PlantApi getPlantApi(){
        return plantApi;
    }
}
