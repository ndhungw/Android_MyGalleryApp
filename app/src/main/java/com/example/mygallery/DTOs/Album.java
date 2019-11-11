package com.example.mygallery.DTOs;

import java.io.Serializable;

//Album class
public class Album implements Serializable {
    //To change this list to be list of URI because will load from Gallery
    private String albumName;
    private int albumID;
    private String albumDate;
    private static int count=0;
    //created date

    public Album(String albumName){
        this.albumName = albumName;
    }

    public Album(int id, String name, String date){
        super();
        albumID = id;
        albumName = name;
        albumDate = date;
    }

    public Album(String name, String date){
        super();
        albumName = name;
        albumDate = date;
    }

    //Album name
    public String getAlbumName() {return albumName;}
    public void setAlbumName(String newName) {albumName = newName;}

    //Album ID
    public int getId(){
        return albumID;
    }

    public void setId(int id){
        albumID=id;
    }

    //Album Date
    public String getDate(){
        return albumDate;
    }

    public void setDate(String date){
        albumDate=date;
    }
}
