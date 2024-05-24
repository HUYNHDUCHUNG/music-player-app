package com.example.musicplay;

import android.content.Context;
import android.media.MediaPlayer;

public class Song {
    private String name;
    private int image;
    private int resource;
    private String nameSinger;

    private  String category;


    public Song(String name, int image, int resource, String nameSinger, String category) {
        this.name = name;
        this.image = image;
        this.resource = resource;
        this.nameSinger = nameSinger;
        this.category = category;
    }

    public String getCategory(){
        return category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getResource() {
        return resource;
    }

    public void setResource(int resource) {
        this.resource = resource;
    }

    public String getNameSinger() {
        return nameSinger;
    }

    public void setNameSinger(String nameSinger) {
        this.nameSinger = nameSinger;
    }

    public String getSongTime(Context context) {
        MediaPlayer player = MediaPlayer.create(context,resource);
        return MainActivity.formatTime(player.getDuration());
    }



}
