package com.example.deparis.ardoise;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import static com.example.deparis.ardoise.Main.get_happy;
import static com.example.deparis.ardoise.Main.setAlcoolemieResult;

public class Alarm extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        get_happy();
        setAlcoolemieResult();
    }
}