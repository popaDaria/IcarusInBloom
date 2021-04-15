package com.sunny.icarusinbloom.webservice;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PlantApi {

    @GET("/api/v1/species/search")
    Call<SpeciesSearchResponse> searchForSpecies(@Query("q") String speciesName,
                                           @Query("token") String token);

    @GET("/api/v1/species/{id}")
    Call<SpeciesResponse> getSpeciesInfo(@Path("id") int id,
            @Query("token") String token);
}
