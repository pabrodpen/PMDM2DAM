package com.example.ex1ev3pablorodriguez;


import android.media.Image;

public class ListItem {
    private String itemName;
    private int image;
    private String soundFilePath;

    public ListItem(int image, String soundFilePath) {
        this.image=image;
        this.soundFilePath = soundFilePath;
    }

    public String getItemName() {
        return itemName;
    }

    public int getImage() {
        return image;
    }

    public String getSoundFilePath() {
        return soundFilePath;
    }

    @Override
    public String toString() {
        return itemName;
    }
}

