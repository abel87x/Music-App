package com.example.musicapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class SongAdapter extends ArrayAdapter<Song> {

    private int mBackColorResource;
    private int mImageResource;

    public SongAdapter(Context c, ArrayList<Song> songList, int colorId, int imageId) {
        super(c, 0, songList);
        mBackColorResource = colorId;
        mImageResource = imageId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        //Get ArrayList Item at the current position
        Song currentSongItem = (Song) getItem(position);

        //Getting the recycled view and checking whether its null or not, if null we will inflate the view
        View songListViewItem = convertView;
        if (songListViewItem == null) {
            songListViewItem = LayoutInflater.from(getContext()).inflate(
                    R.layout.song_item, parent, false);
        }

        //Populating the recycled view with the correct data, then returning the view to be shown on screen
        TextView songName = songListViewItem.findViewById(R.id.item_song_name);
        songName.setText(currentSongItem.getSongName());

        TextView artistName = songListViewItem.findViewById(R.id.item_artist_name);
        artistName.setText(currentSongItem.getArtistName());

        RelativeLayout layout = songListViewItem.findViewById(R.id.song_item_layout);
        layout.setBackgroundResource(mBackColorResource);

        ImageView itemImage = songListViewItem.findViewById(R.id.item_song_img);
        itemImage.setImageResource(mImageResource);

        return songListViewItem;
    }
}
