package com.example.android.bakingapp.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.models.Step;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeInfoAdapter extends RecyclerView.Adapter<RecipeInfoAdapter.RecipeInfoViewHolder> {

    public static ArrayList<Step> steps;
    private OnRecipeStepClicked itemClicked;

    public interface OnRecipeStepClicked {
        void onStepClicked(Step step, int position);
    }

    public RecipeInfoAdapter(OnRecipeStepClicked itemClicked) {
        this.itemClicked = itemClicked;
    }

    @NonNull
    @Override
    public RecipeInfoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_info_list_item, parent, false);
        return new RecipeInfoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeInfoViewHolder holder, int position) {
        if (position == 0) {
            holder.recipeStepTextView.setText(R.string.ingredient);
        } else {
            holder.recipeStepTextView.setText(steps.get(position - 1).getShortDescription());
        }
    }

    @Override
    public int getItemCount() {
        return steps.size() + 1;
    }

    public class RecipeInfoViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.recipe_step_tv)
        TextView recipeStepTextView;
        @BindView(R.id.card_view)
        CardView cardView;

        public RecipeInfoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (getAdapterPosition() == 0) {
                        itemClicked.onStepClicked(null, 0);
                    } else {
                        itemClicked.onStepClicked(steps.get(getAdapterPosition() - 1), getAdapterPosition() - 1);
                    }
                }
            });
        }
    }
}
