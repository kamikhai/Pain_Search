package com.example.rrota.pain_s;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
/**
 * Created by KamillaKhairullina
 * Аналоги лекарств
 */
public class Analog extends AppCompatActivity {

    TextView Name, Price, Subs, DrName, DrPrice;
    ImageView img, imgDr;

    DatabaseHelper mDBHelper;
    SQLiteDatabase mDb;
    Cursor userCursor;
    long id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.analog);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        //Поиск элементов разметки
        Name = findViewById(R.id.an_name);
        Price = findViewById(R.id.an_price);
        Subs = findViewById(R.id.substance);
        img = findViewById(R.id.an_img);
        DrName = findViewById(R.id.dr_name);
        DrPrice = findViewById(R.id.dr_price);
        imgDr = findViewById(R.id.dr_img);

        //Подключение к SQLite
        mDBHelper = new DatabaseHelper(this);

        try {
            mDBHelper.updateDataBase();
        } catch (IOException mIOException) {
            throw new Error("UnableToUpdateDatabase");
        }

        try {
            mDb = mDBHelper.getWritableDatabase();
        } catch (SQLException mSQLException) {
            throw mSQLException;
        }

        //Получение id эл-та, который выбрали в ListView
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            id = extras.getLong("id");
        }

        // получение элемента по id из бд
        userCursor = mDb.rawQuery("select * from " + DatabaseHelper.TABLE + " where " +
                DatabaseHelper.COLUMN_ID + " =? ", new String[] {
                String.valueOf(id)
        });
        userCursor.moveToFirst();

        //Вывод данных на экран
        DrName.setText(userCursor.getString(1));
        DrPrice.setText(userCursor.getString(2));
        Name.setText(userCursor.getString(4));
        Price.setText(userCursor.getString(5));
        Subs.setText(userCursor.getString(7));
        img.setImageResource(userCursor.getInt(6));
        imgDr.setImageResource(userCursor.getInt(3));
        userCursor.close();

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Обработка нажатия клавиши Назад
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}