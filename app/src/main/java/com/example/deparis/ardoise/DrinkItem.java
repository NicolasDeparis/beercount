package com.example.deparis.ardoise;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Calendar;

@Entity(tableName = "drinks")
public class DrinkItem {

    @PrimaryKey(autoGenerate = true)
    private int uid;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "hour")
    private int hour;

    @ColumnInfo(name = "minute")
    private int minute;

    @ColumnInfo(name = "price")
    private double price;

    @ColumnInfo(name = "volume")
    private double volume;

    @ColumnInfo(name = "alcohol")
    private double alcohol;

    @ColumnInfo(name = "timeInMillis")
    private long timeInMillis;


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

    public long getTimeInMillis(){
        return timeInMillis;
    }

    public void setTimeInMillis(long timeInMillis){
        this.timeInMillis = timeInMillis;
    }

    public int getHour(){
        return hour;
    }
    public void setHour(int hour){
        this.hour = hour;
    }

    public int getMinute(){
        return minute;
    }
    public void setMinute(int minute){
        this.minute= minute;
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



    public void setDrinkTime(Calendar drinkCal){
        hour = drinkCal.getTime().getHours();
        minute = drinkCal.getTime().getMinutes();
        timeInMillis = drinkCal.getTimeInMillis();
    }
    public String getFormattedDrinkTime(){
        return String.format("%02dh%02d", hour, minute);
    }

}
