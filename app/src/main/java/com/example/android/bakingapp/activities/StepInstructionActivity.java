package com.example.android.bakingapp.activities;

import android.content.res.Configuration;
import android.net.Uri;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.adapters.RecipeInfoAdapter;
import com.example.android.bakingapp.fragments.StepDiscreptionFragment;
import com.example.android.bakingapp.fragments.StepVideoFragment;
import com.example.android.bakingapp.models.Step;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepInstructionActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.app_bar)
    AppBarLayout appBarLayout;

    @BindView(R.id.step_discreption_container)
    FrameLayout stepDicreptionContainer;

    @BindView(R.id.navigation_card_view)
    CardView navigationCardView;

    @BindView(R.id.next_tv)
    TextView nextTextView;

    @BindView(R.id.back_tv)
    TextView backTextView;

    Step step;

    int position;

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_instruction);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("BakingApp");


        fragmentManager = getSupportFragmentManager();

        step = getIntent().getParcelableExtra("step_extra");
        position = getIntent().getIntExtra("position_extra", 0);
        if (savedInstanceState == null) {
            StepVideoFragment videoFragment = new StepVideoFragment();
            videoFragment.setMediaUri(getMediaUri());
            fragmentManager.beginTransaction().add(R.id.step_video_container, videoFragment).commit();
        }

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            StepDiscreptionFragment discreptionFragment = new StepDiscreptionFragment();
            discreptionFragment.setStepDiscreption(step.getDescription());
            fragmentManager.beginTransaction().add(R.id.step_discreption_container, discreptionFragment).commit();
        } else {
            appBarLayout.setVisibility(View.GONE);
            stepDicreptionContainer.setVisibility(View.GONE);
            navigationCardView.setVisibility(View.GONE);
        }

        nextTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                step = null;
                if (position < RecipeInfoAdapter.steps.size() - 1) {
                    step = RecipeInfoAdapter.steps.get(++position);

                    StepVideoFragment newVideoFragment = new StepVideoFragment();
                    newVideoFragment.setMediaUri(getMediaUri());
                    fragmentManager.beginTransaction().replace(R.id.step_video_container, newVideoFragment).commit();

                    StepDiscreptionFragment newDescreptionFragment = new StepDiscreptionFragment();
                    newDescreptionFragment.setStepDiscreption(step.getDescription());
                    fragmentManager.beginTransaction().replace(R.id.step_discreption_container, newDescreptionFragment).commit();
                }
            }
        });


        backTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                step = null;
                if (position > 0) {
                    step = RecipeInfoAdapter.steps.get(--position);

                    StepVideoFragment newVideoFragment = new StepVideoFragment();
                    newVideoFragment.setMediaUri(getMediaUri());
                    fragmentManager.beginTransaction().replace(R.id.step_video_container, newVideoFragment).commit();


                    StepDiscreptionFragment newDescreptionFragment = new StepDiscreptionFragment();
                    newDescreptionFragment.setStepDiscreption(step.getDescription());
                    fragmentManager.beginTransaction().replace(R.id.step_discreption_container, newDescreptionFragment).commit();
                }
            }
        });
    }


    private Uri getMediaUri() {
        if (!(step.getVideoUrl().isEmpty())) {
            return Uri.parse(step.getVideoUrl());
        } else if (!(step.getThumbnailUrl().isEmpty())) {
            return Uri.parse(step.getThumbnailUrl());
        }
        return null;
    }
}