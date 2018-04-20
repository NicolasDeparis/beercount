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
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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


public class Main extends AppCompatActivity implements View.OnTouchListener, View.OnClickListener {

    private double totalPrice=0;
    public static TextView result = null;
    public static TextView alcoolemieResult = null;
    private ListView listview = null;

    private List<HashMap<String, String>> liste = new ArrayList<HashMap<String, String>>();

    DrinkDatabase database  = DrinkDatabase.getDrinkDatabase(Main.this);

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

        materialDesignFAM = findViewById(R.id.material_design_android_floating_action_menu);
        floatingActionButton1 = findViewById(R.id.material_design_floating_action_menu_item_tigre);
        floatingActionButton1.setOnClickListener(this);
        floatingActionButton2 = findViewById(R.id.material_design_floating_action_menu_item_vedett);
        floatingActionButton2.setOnClickListener(this);
        floatingActionButton3 = findViewById(R.id.material_design_floating_action_menu_item_karmeliet);
        floatingActionButton3.setOnClickListener(this);

        ListAdapter adapter = new SimpleAdapter(this,
                liste,
                android.R.layout.simple_list_item_2,
                new String[]{"biere", "heure"},
                new int[]{android.R.id.text1, android.R.id.text2});

        listview.setAdapter(adapter);
        listview.setLongClickable(true);

        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, final View view,
                                    int position, long id) {

                final HashMap<String, String> item = (HashMap<String, String>) parent.getItemAtPosition(position);

                view.animate().setDuration(500).alpha(0).withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        liste.remove(item);
                        ((SimpleAdapter) listview.getAdapter()).notifyDataSetChanged();
                        view.setAlpha(1);
                    }
                });


                totalPrice -= drinkList.get(position).getPrice();
                if (totalPrice < 0) totalPrice=0;

                result.setText(String.format(java.util.Locale.US,"%.2f €", totalPrice));

                database.drinkDAO().delete(drinkList.get(position));
                drinkList.remove(position);

                return true;
            }

        });

        // refresh list
        ((SimpleAdapter) listview.getAdapter()).notifyDataSetChanged();

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

        drinkList = database.drinkDAO().getAll();
        for(int i = 0; i < drinkList.size(); i++){
            HashMap<String, String> element = new HashMap<String, String>();
            DrinkItem drink = drinkList.get(i);
            element.put("biere", drink.getName());
            element.put("heure", drink.getFormattedDrinkTime());
            liste.add(element);
        }

        setAlcoolemieResult();
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

        database.drinkDAO().insert(drink);
        drinkList.add(drink);

        // Add a list element
        HashMap<String, String> element = new HashMap<String, String>();
        element.put("biere", drink.getName());
        element.put("heure", drink.getFormattedDrinkTime());
        liste.add(element);

        // refresh list
        ((SimpleAdapter) listview.getAdapter()).notifyDataSetChanged();

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
