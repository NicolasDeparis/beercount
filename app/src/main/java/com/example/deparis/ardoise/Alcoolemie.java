package com.example.deparis.ardoise;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Alcoolemie {

    // buveur
    Drinker drinker=new Drinker(92,true);

    double get(List<DrinkItem> drinkList){
        double A=0;

        for(int i = 0; i < drinkList.size(); i++){
            A += getOneDrink(drinkList.get(i));
        }
        return A;
    }

    double getOneDrink(DrinkItem drink){

        double T=drink.getAlcohol();
        double v=drink.getVolume();

        double M=drinker.getMass();
        double C=drinker.getCoef();

        double k=2.71*0.79*v*T/(M*C);

        double t0= Calendar.getInstance().getTimeInMillis();
        double t1=drink.getTimeInMillis();
        double t=(t0-t1)/1000/60/60;

        t/=2./3.; // Max après 40 min

        return k* t * Math.exp(-t);
    }
}
