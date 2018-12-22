package com.example.android.bakingapp;

import com.example.android.bakingapp.models.Recipe;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("baking.json")
    Call<ArrayList<Recipe>> getRecipes();
}
