package com.example.android.bakingapp.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class StepDiscreptionFragment extends Fragment {
    @BindView(R.id.step_discreption_tv)
    TextView stepDiscreptionTextView;
    Unbinder unbinder;

    private String stepDiscreption;

    public void setStepDiscreption(String stepDiscreption) {
        this.stepDiscreption = stepDiscreption;
    }

    public StepDiscreptionFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_step_discreption, container, false);
        unbinder = ButterKnife.bind(this, view);
        stepDiscreptionTextView.setText(stepDiscreption);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}