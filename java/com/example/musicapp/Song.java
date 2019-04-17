package com.example.musicapp;

public class Song {
    private String mSongName;
    private String mArtistName;

    public Song(String songName, String artistName) {
        mSongName = songName;
        mArtistName = artistName;
    }

    /**
     * @return name of song
     */
    public String getSongName() {
        return mSongName;
    }

    /**
     * @return name of artist
     */
    public String getArtistName() {
        return mArtistName;
    }
}
