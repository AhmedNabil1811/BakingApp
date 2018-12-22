package com.example.android.bakingapp.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.models.Ingredient;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.IngredientsAdapterViewHolder> {
    private ArrayList<Ingredient> ingredients;

    public void setIngredients(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public IngredientsAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredients_list_item, parent, false);
        return new IngredientsAdapterViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientsAdapterViewHolder holder, int position) {
        Ingredient ingredient = ingredients.get(position);
        if (ingredient.getMeasure().length() == 1) {
            holder.ingredientsTextView.append(ingredient.getQuantity() + " ");
            holder.ingredientsTextView.append(ingredient.getMeasure());
            holder.ingredientsTextView.append(" of " + ingredient.getIngredient());
        } else {
            holder.ingredientsTextView.append(ingredient.getQuantity() + " ");
            holder.ingredientsTextView.append(ingredient.getMeasure() + 'S');
            holder.ingredientsTextView.append(" of " + ingredient.getIngredient());
        }
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    public class IngredientsAdapterViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ingredient_tv)
        TextView ingredientsTextView;

        public IngredientsAdapterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
