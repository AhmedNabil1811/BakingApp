package com.example.android.bakingapp.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.bakingapp.ApiService;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.activities.RecipeInfoActivity;
import com.example.android.bakingapp.adapters.RecipesListAdapter;
import com.example.android.bakingapp.models.Recipe;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RecipesListFragment extends Fragment implements RecipesListAdapter.OnRecipeItemClicked {


    Unbinder unbinder;

    @BindView(R.id.recipes_rv)
    RecyclerView recipesRecyclerView;
    private RecipesListAdapter adapter;

    public RecipesListFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipes_list, container, false);
        unbinder = ButterKnife.bind(this, rootView);

        loadRecipes();

        adapter = new RecipesListAdapter(this);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recipesRecyclerView.setLayoutManager(manager);
        recipesRecyclerView.setAdapter(adapter);
        return rootView;
    }

    @Override
    public void onItemCliced(Recipe recipe) {
        Intent intent = new Intent(getActivity(), RecipeInfoActivity.class);
        intent.setExtrasClassLoader(Recipe.class.getClassLoader());
        intent.putExtra("recipe_extra", recipe);
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void loadRecipes() {
        String RECIPES_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RECIPES_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
         ApiService service = retrofit.create(ApiService.class);
        Call<ArrayList<Recipe>> call = service.getRecipes();
        call.enqueue(new Callback<ArrayList<Recipe>>() {
            @Override
            public void onResponse(Call<ArrayList<Recipe>> call, Response<ArrayList<Recipe>> response) {
                ArrayList<Recipe> recipes = response.body();
                adapter.setRecipesList(recipes);
            }

            @Override
            public void onFailure(Call<ArrayList<Recipe>> call, Throwable t) {
                Log.e("Error", "while loading recipes");
            }
        });
    }
}
