package com.example.android.bakingapp.activities;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.adapters.RecipeInfoAdapter;
import com.example.android.bakingapp.fragments.StepDiscreptionFragment;
import com.example.android.bakingapp.fragments.StepVideoFragment;
import com.example.android.bakingapp.models.Ingredient;
import com.example.android.bakingapp.models.Recipe;
import com.example.android.bakingapp.models.Step;

import java.util.ArrayList;

public class RecipeInfoActivity extends AppCompatActivity implements RecipeInfoAdapter.OnRecipeStepClicked {

    private boolean twoBane;

    private Recipe recipe;
    private FragmentManager fragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_info);

        recipe = getIntent().getParcelableExtra("recipe_extra");
        this.setTitle(recipe.getName());

        fragmentManager = getSupportFragmentManager();


        if (findViewById(R.id.step_discreption_linear_layout) != null) {
            twoBane = true;

            StepVideoFragment videoFragment = new StepVideoFragment();
            videoFragment.setMediaUri(null);
            fragmentManager.beginTransaction().add(R.id.step_video_container, videoFragment).commit();

            StepDiscreptionFragment discreptionFragment = new StepDiscreptionFragment();
            discreptionFragment.setStepDiscreption("");
            fragmentManager.beginTransaction().add(R.id.step_discreption_container, discreptionFragment).commit();

        } else twoBane = false;

    }

    @Override
    public void onStepClicked(Step step, int position, ArrayList<Step> steps) {

            if (twoBane) {

                StepVideoFragment videoFragment = new StepVideoFragment();
                videoFragment.setMediaUri(getMediaUri(step));
                fragmentManager.beginTransaction().replace(R.id.step_video_container, videoFragment).commit();

                StepDiscreptionFragment discreptionFragment = new StepDiscreptionFragment();
                discreptionFragment.setStepDiscreption(step.getDescription());
                fragmentManager.beginTransaction().replace(R.id.step_discreption_container, discreptionFragment).commit();
            } else {
                Intent intent = new Intent(RecipeInfoActivity.this, StepInstructionActivity.class);
                intent.setExtrasClassLoader(Step.class.getClassLoader());
                intent.putExtra("step_extra", step);
                intent.putExtra("position_extra", position);
                intent.putExtra("step_list_extra" , steps);
                startActivity(intent);
            }
        }



    @Override
    public void onIngrientsClicked() {
        Intent intent = new Intent(RecipeInfoActivity.this, IngredientsActivity.class);
        intent.setExtrasClassLoader(Ingredient.class.getClassLoader());
        intent.putExtra("ingredients_extra", recipe.getIngredients());
        startActivity(intent);

    }

    private Uri getMediaUri(Step step) {
        if (!(step.getVideoUrl().isEmpty())) {
            return Uri.parse(step.getVideoUrl());
        } else if (!(step.getThumbnailUrl().isEmpty())) {
            return Uri.parse(step.getThumbnailUrl());
        }
        return null;
    }
}