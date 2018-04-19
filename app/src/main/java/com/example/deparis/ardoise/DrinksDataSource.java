package com.example.deparis.ardoise;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DrinksDataSource {

    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = { MySQLiteHelper.COLUMN_ID,
            MySQLiteHelper.COLUMN_NAME };

    public DrinksDataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Drink createDrink(String name) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_NAME, name);
        long insertId = database.insert(MySQLiteHelper.TABLE_DRINKS, null,
                values);
        Cursor cursor = database.query(MySQLiteHelper.TABLE_DRINKS,
                allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Drink newDrink = cursorToDrink(cursor);
        cursor.close();
        return newDrink;
    }

    public void deleteComment(Drink drink) {
        long id = drink.getId();
        System.out.println("Comment deleted with id: " + id);
        database.delete(MySQLiteHelper.TABLE_DRINKS, MySQLiteHelper.COLUMN_ID
                + " = " + id, null);
    }

    public List<Drink> getAllDrinks() {
        List<Drink> drinks = new ArrayList<Drink>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_DRINKS,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Drink drink = cursorToDrink(cursor);
            drinks.add(drink);
            cursor.moveToNext();
        }

        cursor.close();
        return drinks;
    }

    private Drink cursorToDrink(Cursor cursor) {
        Drink drink = new Drink();
        drink.setId(cursor.getLong(0));
        drink.setName(cursor.getString(1));
        return drink;
    }

}

