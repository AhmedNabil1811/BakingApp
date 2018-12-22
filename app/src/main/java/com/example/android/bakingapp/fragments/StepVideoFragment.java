package com.example.android.bakingapp.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.bakingapp.R;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class StepVideoFragment extends Fragment {
    private static final String PLAY_WHEN_READY_KEY = "playing";
    private static final String PLAYBACK_POSITION_KEY = "selected_position";
    private static final String MEDIA_URI_EXTRA = "media_uri";

    @BindView(R.id.player_view)
    SimpleExoPlayerView playerView;

    @BindView(R.id.place_holder_image_View)
    ImageView placeHolderImageView;

    Unbinder unbinder;

    SimpleExoPlayer exoPlayer;

    Uri mediaUri;
    private long playbackPosition;
    private boolean playWhenReady;

    public StepVideoFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_step_video, container, false);
        unbinder = ButterKnife.bind(this, view);

        if (savedInstanceState != null) {
            playbackPosition = savedInstanceState.getLong(PLAYBACK_POSITION_KEY);
            playWhenReady = savedInstanceState.getBoolean(PLAY_WHEN_READY_KEY);

            if (savedInstanceState.containsKey(MEDIA_URI_EXTRA)) {
                mediaUri = Uri.parse(savedInstanceState.getString(MEDIA_URI_EXTRA));
            }
        }

        return view;
    }

    private void intializePlayer() {
        if (mediaUri == null) {
            playerView.setVisibility(View.GONE);
            placeHolderImageView.setVisibility(View.VISIBLE);
            Picasso.get().load(R.drawable.baking).into(placeHolderImageView);
        } else {
            playerView.setVisibility(View.VISIBLE);
            placeHolderImageView.setVisibility(View.GONE);
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            exoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector, loadControl);
            playerView.setPlayer(exoPlayer);
            String userAgent = Util.getUserAgent(getActivity(), "BakingApp");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(getActivity(), userAgent),
                    new DefaultExtractorsFactory(), null, null);
            exoPlayer.prepare(mediaSource, false, false);
            exoPlayer.seekTo(playbackPosition);
            exoPlayer.setPlayWhenReady(playWhenReady);

        }
    }

    public void setMediaUri(Uri mediaUri) {
        this.mediaUri = mediaUri;
    }

    private void releasePlayer() {
        if (exoPlayer != null) {
            exoPlayer.release();
            exoPlayer = null;
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(PLAYBACK_POSITION_KEY, playbackPosition);
        outState.putBoolean(PLAY_WHEN_READY_KEY, playWhenReady);
        if (mediaUri != null) {
            outState.putString(MEDIA_URI_EXTRA, mediaUri.toString());
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            intializePlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if ((Util.SDK_INT <= 23 || exoPlayer == null)) {
            intializePlayer();
        }
    }

    @Override
    public void onPause() {
        savePlayerState();

        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

    private void savePlayerState() {
        if (exoPlayer != null) {
            playbackPosition = exoPlayer.getCurrentPosition();
            playWhenReady = exoPlayer.getPlayWhenReady();
        }
    }
}
