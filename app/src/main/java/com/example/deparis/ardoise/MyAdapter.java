package com.example.deparis.ardoise;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import static com.example.deparis.ardoise.Main.drinkList;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

    List<DrinkItem> list;

    public MyAdapter(List<DrinkItem> list) {
        this.list = list;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int itemType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cell_cards,viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int position) {
        DrinkItem myObject = list.get(position);
        myViewHolder.bind(myObject);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void removeItem(DrinkDatabase database, int position){
        database.drinkDAO().delete(drinkList.get(position));
        drinkList.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(DrinkItem item, int position) {
        drinkList.add(position, item);
        notifyItemInserted(position);
    }
}