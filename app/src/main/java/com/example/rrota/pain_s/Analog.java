package com.example.rrota.pain_s;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
//TODO Кнопка назад в верхней "шапке"
public class Analog extends AppCompatActivity {

    TextView Name,Price,Subs,DrName,DrPrice;
    ImageView img,imgDr;

    DatabaseHelper mDBHelper;
    SQLiteDatabase mDb;
    Cursor userCursor;
    long userId=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.analog);

        Name=findViewById(R.id.an_name);
        Price=findViewById(R.id.an_price);
        Subs=findViewById(R.id.substance);
        img=findViewById(R.id.an_img);
        DrName=findViewById(R.id.dr_name);
        DrPrice=findViewById(R.id.dr_price);
        imgDr=findViewById(R.id.dr_img);

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

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userId = extras.getLong("id");
        }

        // получаем элемент по id из бд
        userCursor = mDb.rawQuery("select * from " + DatabaseHelper.TABLE + " where " +
                DatabaseHelper.COLUMN_ID + " =? ", new String[]{String.valueOf(userId)});
        userCursor.moveToFirst();
        DrName.setText(userCursor.getString(1));
        DrPrice.setText(userCursor.getString(2));
        Name.setText(userCursor.getString(4));
        Price.setText(userCursor.getString(5));
        Subs.setText(userCursor.getString(7));
        img.setImageResource(userCursor.getInt(6));
        imgDr.setImageResource(userCursor.getInt(3));
        userCursor.close();

    }
    public void save(View view){
        ContentValues cv = new ContentValues();
//        cv.put(DatabaseHelper.COLUMN_NAME, nameBox.getText().toString());
//        cv.put(DatabaseHelper.COLUMN_YEAR, Integer.parseInt(yearBox.getText().toString()));

        if (userId > 0) {
            mDb.update(DatabaseHelper.TABLE, cv, DatabaseHelper.COLUMN_ID + "=" + String.valueOf(userId), null);
        } else {
            mDb.insert(DatabaseHelper.TABLE, null, cv);
        }
        goHome();
    }
    public void delete(View view){
        mDb.delete(DatabaseHelper.TABLE, "_id = ?", new String[]{String.valueOf(userId)});
        goHome();
    }
    private void goHome(){
        // закрываем подключение
        mDb.close();
        // переход к главной activity
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }
}