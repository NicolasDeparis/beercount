package com.example.deparis.ardoise;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MyViewHolder extends RecyclerView.ViewHolder{


    private TextView textName;
    private TextView textHour;
    private ImageView image;


    public RelativeLayout viewBackground, viewForeground;

    public MyViewHolder(View itemView) {
        super(itemView);

        textName =  itemView.findViewById(R.id.name);
        textHour =  itemView.findViewById(R.id.hour);
        image =  itemView.findViewById(R.id.image);

        viewBackground = itemView.findViewById(R.id.view_background);
        viewForeground = itemView.findViewById(R.id.view_foreground);

    }

    public void bind(DrinkItem drink){
        textName.setText(drink.getName());
        textHour.setText(drink.getFormattedDrinkTime());

        if (drink.getName() == "Tigre") image.setImageResource(R.drawable.tigre);
        if (drink.getName() == "Vedett") image.setImageResource(R.drawable.vedett);
        if (drink.getName() == "Karmeliet") image.setImageResource(R.drawable.karmeliet);

    }



}