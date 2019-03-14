package com.route.gpstrackerg2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class YoutubePlayerActivity extends YouTubeBaseActivity {



    YouTubePlayerView playerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_player);
        playerView=findViewById(R.id.youtube_view);

        playerView.initialize(getString(R.string.google_maps_key), new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                final YouTubePlayer youTubePlayer,
                                                boolean wasRestored) {
                if (!wasRestored) {
                    youTubePlayer.cueVideo("wKJ9KzGQq0w");
                youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);
                youTubePlayer.setPlayerStateChangeListener(new YouTubePlayer.PlayerStateChangeListener() {
                    @Override
                    public void onLoading() {

                    }

                    @Override
                    public void onLoaded(String s) {
                        youTubePlayer.play();
                    }

                    @Override
                    public void onAdStarted() {

                    }

                    @Override
                    public void onVideoStarted() {

                    }

                    @Override
                    public void onVideoEnded() {

                    }

                    @Override
                    public void onError(YouTubePlayer.ErrorReason errorReason) {

                    }
                });
                }

            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider,
                        YouTubeInitializationResult youTubeInitializationResult) {
                Toast.makeText(YoutubePlayerActivity.this, R.string.error, Toast.LENGTH_SHORT).show();
            }
        });

    };

    public void GotoMainActivity(View view) {
        startActivity(new Intent(this,MainActivity.class));
    }
}
