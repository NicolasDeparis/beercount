package com.example.deparis.ardoise;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Calendar;

@Entity(tableName = "conso")
public class ConsoItem {

    @PrimaryKey(autoGenerate = true)
    private int uid;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "price")
    private double price;

    @ColumnInfo(name = "volume")
    private double volume;

    @ColumnInfo(name = "alcohol")
    private double alcohol;



    public int getUid(){
        return uid;
    }
    public void setUid(int uid){
        this.uid = uid;
    }

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }

    public double getPrice(){
        return price;
    }
    public void setPrice(double price){
        this.price = price;
    }

    public double getVolume(){
        return volume;
    }
    public void setVolume(double volume){
        this.volume = volume;
    }

    public double getAlcohol(){
        return alcohol;
    }
    public void setAlcohol(double alcohol){
        this.alcohol = alcohol;
    }

}
