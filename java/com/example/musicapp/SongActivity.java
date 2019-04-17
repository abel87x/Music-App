package com.example.musicapp;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Explode;
import android.transition.Slide;
import android.util.Pair;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class SongActivity extends AppCompatActivity {

    ArrayList<Song> mSongArrayList = new ArrayList<>();
    int mBackColorResourceId;
    int mImageResourceId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Enter transition animation for the activity
        getWindow().setEnterTransition(new Slide());
        setContentView(R.layout.activity_song);

        //Check which card/artist the user clicked on, in order to populate the activity ListView with perspective song list
        if (MyAdapter.s0) {
            mBackColorResourceId = R.color.firstCardColor;
            mImageResourceId = R.drawable.art;
            mSongArrayList.add(new Song("S.T.O.P", "David Guetta ft. Ryan Tedder"));
            mSongArrayList.add(new Song("Listen", "David Guetta ft John Legend"));
            mSongArrayList.add(new Song("Say My Name", "David Guetta ft Bebe Rexha"));
            mSongArrayList.add(new Song("Shot Me Down", "David Guetta ft Skylar Grey"));
            mSongArrayList.add(new Song("Titanium", "David Guetta ft. Sia"));
            mSongArrayList.add(new Song("Flames", "David Guetta ft Sia"));
            mSongArrayList.add(new Song("Lift Me Up", "David Guetta ft Nico & Vinz"));
            mSongArrayList.add(new Song("Hey Mama", "David Guetta ft Nicki Minaj"));
            mSongArrayList.add(new Song("Better When You're Gone", "David Guetta ft. Brooks & Loote"));
            mSongArrayList.add(new Song("Another Life", "David Guetta ft Ester Dean"));
        } else if (MyAdapter.s1) {
            mBackColorResourceId = R.color.secCardColor;
            mImageResourceId = R.drawable.artaudience;
            mSongArrayList.add(new Song("Broken Arrows", "Avicii ft Zac Brown"));
            mSongArrayList.add(new Song("Hey Brother", "Avicii"));
            mSongArrayList.add(new Song("I'm Gonna Love Ya", "Avicii"));
            mSongArrayList.add(new Song("Wake Me Up", "Avicii"));
            mSongArrayList.add(new Song("Levels", "Avicii"));
            mSongArrayList.add(new Song("Waiting For Love", "Avicii"));
            mSongArrayList.add(new Song("Ten More Days", "Avicii"));
            mSongArrayList.add(new Song("For A Better Day", "Avicii"));
            mSongArrayList.add(new Song("Pure Grinding", "Avicii"));
            mSongArrayList.add(new Song("You Make me", "Avicii"));
        } else if (MyAdapter.s2) {
            mBackColorResourceId = R.color.thirdCardColor;
            mImageResourceId = R.drawable.artblack;
            mSongArrayList.add(new Song("Born To Be Yours", "Kygo ft Imagine Dragons"));
            mSongArrayList.add(new Song("Remind Me To Forget", "Kygo ft Miguel"));
            mSongArrayList.add(new Song("Stranger Things", "Kygo ft One Direction"));
            mSongArrayList.add(new Song("Firestone", "Kygo ft Conrad Sewell"));
            mSongArrayList.add(new Song("Stargazing", "Kygo ft Justin Jesso"));
            mSongArrayList.add(new Song("Never Let You Go", "Kygo ft John Newman"));
            mSongArrayList.add(new Song("This Town", "Kygo ft Sasha"));
            mSongArrayList.add(new Song("I See You", "Kygo ft Billy Raffoul"));
            mSongArrayList.add(new Song("With You", "Kygo ft Wrabel"));
        } else if (MyAdapter.s3) {
            mBackColorResourceId = R.color.fourthCardColor;
            mImageResourceId = R.drawable.artband;
            mSongArrayList.add(new Song("Giant", "Calvin Harris ft Ragnbone Man"));
            mSongArrayList.add(new Song("How Deep Is Your Love", "Calvin Harris ft Disciples"));
            mSongArrayList.add(new Song("Feels", "Calvin Harris"));
            mSongArrayList.add(new Song("Blame", "Calvin Harris ft John Newman"));
            mSongArrayList.add(new Song("Summer", "Calvin Harris"));
            mSongArrayList.add(new Song("Thinking About You", "Calvin Harris ft Ayah Marar"));
            mSongArrayList.add(new Song("We Found Love", "Calvin Harris ft Rihanna"));
            mSongArrayList.add(new Song("Flashback", "Calvin Harris"));
            mSongArrayList.add(new Song("Let's Go", "Calvin Harris"));
        } else if (MyAdapter.s4) {
            mBackColorResourceId = R.color.fifthCardColor;
            mImageResourceId = R.drawable.artlights;
            mSongArrayList.add(new Song("Helium", "Sia"));
            mSongArrayList.add(new Song("Never Give Up", "Sia"));
            mSongArrayList.add(new Song("The Greatest", "Sia ft Kendrick Lamar"));
            mSongArrayList.add(new Song("To Be Human", "Sia ft Labrinth"));
            mSongArrayList.add(new Song("Chandelier", "Sia"));
            mSongArrayList.add(new Song("Don't Bring Me Down", "Sia"));
            mSongArrayList.add(new Song("Wild Ones", "Sia ft Flo Rida"));
            mSongArrayList.add(new Song("Unstoppable", "Sia"));
            mSongArrayList.add(new Song("No New Friends", "Sia ft Dua Lipa"));
        }

        //defining the ListView and initializing the adapter, then binding them to populate the ListView
        ListView mSongList = findViewById(R.id.song_list);
        SongAdapter mAdapter = new SongAdapter(this, mSongArrayList, mBackColorResourceId, mImageResourceId);
        mSongList.setAdapter(mAdapter);

        //Configure what happens when the user clicks on an item, the corresponding song should be played in a new activity
        mSongList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //preparing the Intent with the required data to be passed onto the next activity
                TextView songNameTextView = view.findViewById(R.id.item_song_name);
                String songNameText = songNameTextView.getText().toString();

                TextView artistNameTextView = view.findViewById(R.id.item_artist_name);
                String artistNameText = artistNameTextView.getText().toString();

                Intent playIntent = new Intent(SongActivity.this, NowPlayingActivity.class);
                playIntent.putExtra("song_name", songNameText);
                playIntent.putExtra("artist_name", artistNameText);
                playIntent.putExtra("song_id", R.raw.onerepublic_liftmeup);
                playIntent.putExtra("img_id", mImageResourceId);

                //Configuring the shared view transition animation and starting the activity
                Pair sharedImage = new Pair<>(view.findViewById(R.id.item_song_img), "song_img_transition");
                startActivity(playIntent, ActivityOptions.makeSceneTransitionAnimation((Activity) SongActivity.this, sharedImage).toBundle());
            }
        });

        //Exit transition animation for the activity
        getWindow().setExitTransition(new Explode());
    }
}
