package com.example.soccerleauge;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;


public interface TeamsApi {

    @GET("teams")
    Call<List<Team>> getTeams();
}
