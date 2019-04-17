package com.example.musicapp;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Explode;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView musicRecycler;
    RecyclerView.LayoutManager manager;
    RecyclerView.Adapter mAdapter;
    ArrayList<Card> cardData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Enter transition animation for the activity
        getWindow().setEnterTransition(new Explode());
        setContentView(R.layout.activity_main);

        //Initializing the ArrayList with data
        cardData.add(new Card(R.drawable.david, R.drawable.cd, "David Guetta"));
        cardData.add(new Card(R.drawable.avicii, R.drawable.cd, "avicii"));
        cardData.add(new Card(R.drawable.kygoo, R.drawable.cd, "kygo"));
        cardData.add(new Card(R.drawable.calvinnn, R.drawable.cd, "calvin harris"));
        cardData.add(new Card(R.drawable.sia, R.drawable.cd, "sia"));

        //Finding the RecyclerView in the layout and defining its manager
        musicRecycler = findViewById(R.id.recycle);
        manager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        //Defining and adding an item divider between items of the RecyclerView
        DividerItemDecoration itemDivider = new DividerItemDecoration(musicRecycler.getContext(), DividerItemDecoration.HORIZONTAL);
        itemDivider.setDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.divider_shape));
        musicRecycler.addItemDecoration(itemDivider);

        //Binding the manager and adapter with the RecyclerView and showing items on the screen
        musicRecycler.setLayoutManager(manager);
        mAdapter = new MyAdapter(cardData, MainActivity.this);
        musicRecycler.setAdapter(mAdapter);

        //Exit transition animation for the activity
        getWindow().setExitTransition(new Explode());

    }
}
