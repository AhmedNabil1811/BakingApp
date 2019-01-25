package com.example.android.bakingapp.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.adapters.RecipeInfoAdapter;
import com.example.android.bakingapp.models.Recipe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class RecipeInfoFragment extends Fragment {
    Unbinder unbinder;

    @BindView(R.id.recipe_info_rv)
    RecyclerView recipeInfoRecyclerView;
    RecipeInfoAdapter adapter;

    public RecipeInfoFragment() {
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe_info, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        adapter = new RecipeInfoAdapter((RecipeInfoAdapter.OnRecipeStepClicked) getActivity());
        Recipe recipe = getActivity().getIntent().getParcelableExtra("recipe_extra");
       adapter.setSteps(recipe.getSteps());
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        recipeInfoRecyclerView.setLayoutManager(manager);
        recipeInfoRecyclerView.setAdapter(adapter);
        recipeInfoRecyclerView.getAdapter();
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
