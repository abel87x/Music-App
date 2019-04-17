package com.example.musicapp;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter {

    public static boolean s0, s1, s2, s3, s4;
    private Context mContext;
    private ArrayList<Card> mDataSet;

    public MyAdapter(ArrayList<Card> myDataSet, Context context) {
        mDataSet = myDataSet;
        mContext = context;
    }

    //Creating the view item and initializing custom ViewHolder object here
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.main_item, parent, false);

        MyViewHolder holder = new MyViewHolder(v, mContext);

        return holder;
    }

    //Binding the ViewHolder/view item with the corresponding data from the ArrayList here
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        final MyViewHolder holder1 = (MyViewHolder) holder;
        holder1.mainText.setText(mDataSet.get(position).getArtistName());
        holder1.mainImage.setImageResource(mDataSet.get(position).getMainImageResource());
        holder1.secImage.setImageResource(mDataSet.get(position).getSecImageResource());
    }

    //Returning the size of the ArrayList holding the data
    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    //Custom ViewHolder adapter to accommodate for the complex view item
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView mainText;
        public ImageView mainImage;
        public ImageView secImage;

        public MyViewHolder(View v, final Context c) {
            super(v);
            mainText = v.findViewById(R.id.txt);
            mainImage = v.findViewById(R.id.main_img);
            secImage = v.findViewById(R.id.img2);

            //Defining the OnClickListener for the RecyclerView items
            //We are using it to start the new activity depending on the clicked item, using the boolean variables and adapter position
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (getAdapterPosition() == 0) {
                        s0 = true;
                        s1 = s2 = s3 = s4 = false;
                    } else if (getAdapterPosition() == 1) {
                        s1 = true;
                        s0 = s2 = s3 = s4 = false;
                    } else if (getAdapterPosition() == 2) {
                        s2 = true;
                        s0 = s1 = s3 = s4 = false;
                    } else if (getAdapterPosition() == 3) {
                        s3 = true;
                        s0 = s2 = s1 = s4 = false;
                    } else if (getAdapterPosition() == 4) {
                        s4 = true;
                        s0 = s1 = s2 = s3 = false;
                    }
                    c.startActivity(new Intent(c, SongActivity.class), ActivityOptions.makeSceneTransitionAnimation((Activity) c).toBundle());
                }
            });
        }


    }

}
