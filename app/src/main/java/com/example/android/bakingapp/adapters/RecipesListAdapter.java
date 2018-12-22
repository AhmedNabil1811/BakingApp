package com.example.android.bakingapp.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.models.Recipe;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipesListAdapter extends RecyclerView.Adapter<RecipesListAdapter.RecipesListViewHolder> {

    private ArrayList<Recipe> recipesList;
    private OnRecipeItemClicked item;

    public RecipesListAdapter(OnRecipeItemClicked item) {
        this.item = item;
    }

    public void setRecipesList(ArrayList<Recipe> recipesList) {
        this.recipesList = recipesList;
        notifyDataSetChanged();
    }

    public interface OnRecipeItemClicked {
        void onItemCliced(Recipe recipe);
    }

    @Override
    public RecipesListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipes_list_item, parent, false);
        return new RecipesListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipesListAdapter.RecipesListViewHolder holder, int position) {
        holder.RecipeTextView.setText(recipesList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return recipesList == null ? 0 : recipesList.size();
    }

    public class RecipesListViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.recipe_tv)
        TextView RecipeTextView;
        @BindView(R.id.card_view)
        CardView cardView;

        public RecipesListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    item.onItemCliced(recipesList.get(getAdapterPosition()));
                }
            });
        }
    }
}