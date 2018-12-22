package com.example.android.bakingapp.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.adapters.IngredientsAdapter;
import com.example.android.bakingapp.models.Ingredient;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IngredientsActivity extends AppCompatActivity {

    @BindView(R.id.ingredients_rv)
    RecyclerView ingredientsRecyclerView;
    IngredientsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredients);
        ButterKnife.bind(this);
        ArrayList<Ingredient> ingredients = getIntent().getParcelableArrayListExtra("ingredients_extra");
        adapter = new IngredientsAdapter();
        adapter.setIngredients(ingredients);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        ingredientsRecyclerView.setLayoutManager(manager);
        ingredientsRecyclerView.setAdapter(adapter);
    }
}
