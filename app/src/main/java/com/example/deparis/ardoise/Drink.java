package com.example.deparis.ardoise;

public class Drink{

    private long _id;
    private String _name = null;
    private double _price = 0;
    private String _hour = null;


    public Drink() {
        super();
        _id=0;
        _name=null;
        _price=0;
        _hour=null;
    }



    public Drink(long id, String name, double price, String hour) {
        super();
        _id=id;
        _name=name;
        _price=price;
        _hour=hour;
    }


    public long getId() {
        return _id;
    }


    public void setId(long id) {
        _id = id;
    }

    public String getName(){
        return _name;
    }

    public void setName(String name){
        _name = name;
    }

    public String getHour(){
        return _hour;
    }

    public void setHour(String hour){
        _hour = hour;
    }

    public double getPrice(){
        return _price;
    }

    public void setPrice(double price){
        _price=price;
    }

}
