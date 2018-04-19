package com.example.deparis.ardoise;

public class Drinker {

    private double mass;
    private boolean sex; // True=Male

    Drinker(double mass, boolean sex){
        this.mass=mass;
        this.sex=sex;
    }



    public void setMasse(double mass){
        this.mass=mass;
    }

    public double getMass(){
        return mass;
    }

    public void setSex(boolean sex){
        this.sex=sex;
    }

    public boolean getSex(){
        return sex;
    }

    public double getCoef(){
        if (sex) {
            return 0.7;
        }else{
            return 0.6;
        }
    }

}

