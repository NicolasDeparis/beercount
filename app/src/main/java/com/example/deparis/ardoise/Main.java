package com.example.deparis.ardoise;


import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.arch.persistence.room.Room;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.preference.SwitchPreference;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import java.util.Timer;
import java.util.TimerTask;


public class Main extends AppCompatActivity implements View.OnTouchListener, View.OnClickListener, RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {

    private double totalPrice=0;
    public static TextView result = null;
    public static TextView alcoolemieResult = null;
    private ListView listview = null;

    private RecyclerView recyclerView;
    private MyAdapter adapter;

    DrinkDatabase drinkDB  = DrinkDatabase.getDrinkDatabase(Main.this);
    ConsoDatabase consoDB  = ConsoDatabase.getConsoDatabase(Main.this);

    public static List<DrinkItem> drinkList = new ArrayList<DrinkItem>();

    public static FloatingActionMenu materialDesignFAM;
    private FloatingActionButton floatingActionButton1, floatingActionButton2, floatingActionButton3;

    private DrawerLayout mDrawerLayout;

    public static int happy_start = 16;
    public static int happy_end = 20;

    public static Alcoolemie alcoolemie = new Alcoolemie();

    public static void setAlcoolemieResult(){
        alcoolemieResult.setText(String.format(java.util.Locale.US, "%.2f", alcoolemie.get(drinkList)));
    }

    public static boolean get_happy() {

        Calendar cal = Calendar.getInstance();
        int currenthour = cal.get(Calendar.HOUR_OF_DAY);

        Boolean happy=false;
        if(currenthour >= happy_start && currenthour <happy_end) happy = true;

        // Change button color function of happy hour
        if(happy) {
            materialDesignFAM.setMenuButtonColorNormal(Color.GREEN);
            materialDesignFAM.setMenuButtonColorPressed(Color.GREEN);
            materialDesignFAM.setMenuButtonColorRipple(Color.GREEN);
        }else{
            materialDesignFAM.setMenuButtonColorNormal(Color.RED);
            materialDesignFAM.setMenuButtonColorPressed(Color.RED);
            materialDesignFAM.setMenuButtonColorRipple(Color.RED);
        }

        return happy;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        result = findViewById(R.id.result);
        alcoolemieResult=findViewById(R.id.alcoolemieResult);

        listview = findViewById(R.id.list_view);

        drinkList = drinkDB.drinkDAO().getAll();

        adapter=new MyAdapter(drinkList);

        recyclerView =findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);

        // adding item touch helper
        // only ItemTouchHelper.LEFT added to detect Right to Left swipe
        // if you want both Right -> Left and Left -> Right
        // add pass ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT as param
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);



        materialDesignFAM = findViewById(R.id.material_design_android_floating_action_menu);
        floatingActionButton1 = findViewById(R.id.material_design_floating_action_menu_item_tigre);
        floatingActionButton1.setOnClickListener(this);
        floatingActionButton2 = findViewById(R.id.material_design_floating_action_menu_item_vedett);
        floatingActionButton2.setOnClickListener(this);
        floatingActionButton3 = findViewById(R.id.material_design_floating_action_menu_item_karmeliet);
        floatingActionButton3.setOnClickListener(this);



        mDrawerLayout = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        // close drawer when item is tapped
                        mDrawerLayout.closeDrawers();

                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here

                        startActivity(new Intent(Main.this, SettingsActivity.class));

//                        setContentView(R.layout.settings);

                        return true;
                    }
                });


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(android.R.drawable.arrow_down_float);

        // Set automatic color changing for the FAM
        Intent activate = new Intent(Main.this, Alarm.class);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(Main.this, 0, activate, 0);
        AlarmManager alarms = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarms.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(),1000, alarmIntent);

        // init

        get_happy();
        setAlcoolemieResult();
    }


    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof MyViewHolder) {
            // get the removed item name to display it in snack bar
            String name = drinkList.get(viewHolder.getAdapterPosition()).getName();

            // backup of removed item for undo purpose
            final DrinkItem deletedItem = drinkList.get(viewHolder.getAdapterPosition());
            final int deletedIndex = viewHolder.getAdapterPosition();


            adapter.removeItem(drinkDB,viewHolder.getAdapterPosition());

            // showing snack bar with Undo option
            Snackbar snackbar = Snackbar
                    .make(mDrawerLayout, name + " removed from cart!", Snackbar.LENGTH_LONG);
            snackbar.setAction("UNDO", new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    // undo is selected, restore the deleted item
                    adapter.restoreItem(deletedItem, deletedIndex);
                }
            });
            snackbar.setActionTextColor(Color.YELLOW);

            snackbar.show();
        }
    }

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return true;
    }

    @Override
    public void onClick(View v) {

        boolean happy=get_happy();
        double price;

        DrinkItem drink = new DrinkItem();


        drink.setDrinkTime(Calendar.getInstance());

        switch(v.getId()) {

            case R.id.material_design_floating_action_menu_item_tigre:

                if(happy)   price =  2.9 ;
                else        price =  4.5 ;

                drink.setName("Tigre");
                drink.setPrice(price);
                drink.setAlcohol(0.055);
                break;

            case R.id.material_design_floating_action_menu_item_vedett:

                if(happy)   price =  4.2 ;
                else        price =  6.3 ;

                drink.setName("Vedett");
                drink.setPrice(price);
                drink.setAlcohol(0.06);

                break;

            case R.id.material_design_floating_action_menu_item_karmeliet:

                if(happy)   price =  4.3 ;
                else        price =  6.5 ;

                drink.setName("Karmeliet");
                drink.setPrice(price);
                drink.setAlcohol(0.084);

                break;

        }

        drink.setVolume(500);

        totalPrice += drink.getPrice();
        result.setText(String.format(java.util.Locale.US,"%.2f €", totalPrice ));

        drinkDB.drinkDAO().insert(drink);
        drinkList.add(drink);

        // refresh list
        adapter.notifyDataSetChanged();

        // Close the FAM
        materialDesignFAM.close(true);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
