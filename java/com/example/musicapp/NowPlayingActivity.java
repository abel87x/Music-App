package com.example.musicapp;

import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Fade;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

public class NowPlayingActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mPlayPauseImage;
    private ImageView mStopImage;
    private ImageView mForwardImage;
    private ImageView mRewindImage;
    private ImageView mNowPlayingImage;
    private MediaPlayer mPlayer;
    private AnimationDrawable mPlayImageAnimation;
    private int mIsPlaying = 0;
    private int mSongId;
    private int mImageId;
    private SeekBar mSeekBar;
    private Runnable mRunnable;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Enter transition animation for the activity
        getWindow().setEnterTransition(new Fade());
        setContentView(R.layout.activity_now_playing);

        //Get the required data from the previous activity to be populated in the current activity
        // Data like song name, artist name and song id, need to be passed from previous activity using and intent bundle
        Bundle extra = getIntent().getExtras();
        TextView mSongName = findViewById(R.id.songname);
        mSongName.setText(extra.getString("song_name"));
        TextView mArtistName = findViewById(R.id.artistname);
        mArtistName.setText(extra.getString("artist_name"));
        mSongId = extra.getInt("song_id");
        mImageId = extra.getInt("img_id");

        //This part of code is responsible for the rotating animation of the record CD
        // It starts rotating along with the start of the song.
        final ImageView record = findViewById(R.id.record_cd);
        record.setAnimation(AnimationUtils.loadAnimation(this, R.anim.rotate));
        record.getAnimation().start();

        //Here we reference Play/Pause button used in XML Layout to apply functionality to it using OnClickListener
        //Also note that we apply drawable animation to the button so it can change the state/image from playing to pausing
        //This is done using animation drawable, with the help of a drawable animation XML file created in the drawable folder
        mPlayPauseImage = findViewById(R.id.playbutton);
        mPlayPauseImage.setBackgroundResource(R.drawable.play_animation);
        mPlayImageAnimation = (AnimationDrawable) mPlayPauseImage.getBackground();

        //Here we continue referencing/initializing remaining views of the XML / objects, in order to use them later
        mNowPlayingImage = findViewById(R.id.now_playing_img);
        mStopImage = findViewById(R.id.stopbutton);
        mSeekBar = findViewById(R.id.seekbar);
        mForwardImage = findViewById(R.id.forwardbutton);
        mRewindImage = findViewById(R.id.rewindbutton);
        mHandler = new Handler();
        TextView durTextView = findViewById(R.id.duration);

        //Making sure MediaPlayer starts playing with the activity creation
        mPlayer = MediaPlayer.create(this, mSongId);
        mPlayer.start();
        mPlayImageAnimation.start();
        mIsPlaying = 1;
        mNowPlayingImage.setImageResource(mImageId);
        initializeSeekBar();
        changeSeekBar();
        double d1 = mPlayer.getDuration() / (1000.0 * 60.0) - mPlayer.getDuration() / (1000 * 60);
        durTextView.setText(String.valueOf(mPlayer.getDuration() / (1000 * 60)) + ":" + String.valueOf(Math.round(d1 * 60)));

        //Setting OnClickListeners for both seek buttons, Overriding onClick method later in the code
        mForwardImage.setOnClickListener(this);
        mRewindImage.setOnClickListener(this);

        //Defining OnClickListener for the Play/Pause button or image
        //It involves starting or pausing the MediaPlayer as well as the animations of the button itself and the record CD
        mPlayPauseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (mIsPlaying) {
                    case 0:
                        mPlayImageAnimation.start();
                        mIsPlaying = 1;
                        if (mPlayer == null) {
                            mPlayer = MediaPlayer.create(NowPlayingActivity.this, mSongId);
                            mPlayer.start();
                            changeSeekBar();
                            record.startAnimation(AnimationUtils.loadAnimation(NowPlayingActivity.this, R.anim.rotate));
                        } else {
                            mPlayer.start();
                            record.startAnimation(AnimationUtils.loadAnimation(NowPlayingActivity.this, R.anim.rotate));
                        }
                        break;
                    case 1:
                        mPlayImageAnimation.stop();
                        mPlayImageAnimation.selectDrawable(0);
                        mIsPlaying = 0;
                        record.clearAnimation();
                        if (mPlayer != null) {
                            mPlayer.pause();
                        }
                        break;
                }
            }
        });

        //Defining OnClickListener for the stop button, releasing MediaPlayer resources and stopping animations
        mStopImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                releaseMediaPlayer();
                mPlayImageAnimation.stop();
                mPlayImageAnimation.selectDrawable(0);
                mIsPlaying = 0;
                if (record.getAnimation() != null) {
                    record.getAnimation().cancel();
                }
                mSeekBar.setProgress(0);
            }
        });

        //Defining what should happen after changing seekBar position by user
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    mPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        //Exit transition animation for the activity
        getWindow().setExitTransition(new Fade());
    }

    private void releaseMediaPlayer() {
        if (mPlayer != null) {
            mPlayer.stop();
            mPlayer.release();
            mPlayer = null;
            if (mHandler != null) {
                mHandler.removeCallbacks(mRunnable);
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

    private void initializeSeekBar() {
        mSeekBar.setMax(mPlayer.getDuration());
    }

    private void changeSeekBar() {
        if (mPlayer.isPlaying()) {
            mRunnable = new Runnable() {
                @Override
                public void run() {
                    mSeekBar.setProgress(mPlayer.getCurrentPosition());
                    mHandler.postDelayed(mRunnable, 500);
                }
            };
            mHandler.postDelayed(mRunnable, 500);
        }
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (mPlayer != null) {
            switch (v.getId()) {
                case R.id.forwardbutton:
                    mPlayer.seekTo(mPlayer.getCurrentPosition() + 5000);
                    break;
                case R.id.rewindbutton:
                    mPlayer.seekTo(mPlayer.getCurrentPosition() - 5000);
                    break;
            }
        }
    }
}
