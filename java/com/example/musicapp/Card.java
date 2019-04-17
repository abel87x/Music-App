package com.example.musicapp;

public class Card {
    private int mMainImageResource = 0;
    private String mArtistName;
    private int mSecImageResource = 0;

    public Card(int mainImageResource, int secImageResource, String mainName) {
        mMainImageResource = mainImageResource;
        mSecImageResource = secImageResource;
        mArtistName = mainName;

    }

    /**
     * @return the resource Id of the main image in the card
     */
    public int getMainImageResource() {
        return mMainImageResource;
    }

    /**
     * @return the resource id of the second image in the card
     */
    public int getSecImageResource() {
        return mSecImageResource;
    }

    /**
     * @return artist name
     */
    public String getArtistName() {
        return mArtistName;
    }
}
