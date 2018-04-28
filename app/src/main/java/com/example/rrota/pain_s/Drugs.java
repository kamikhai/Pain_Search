package com.example.rrota.pain_s;

import android.annotation.TargetApi;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class Drugs extends AppCompatActivity {
    //Объявим переменные компонентов
    TextView textView;

    //Переменная для работы с БД
    private DatabaseHelper mDBHelper;
    private SQLiteDatabase mDb;
    Cursor userCursor;
    ListView userList;
    SimpleCursorAdapter userAdapter;

    Cursor cursor;
    Search searching;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drugs);

       searching = new Search(this);

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

        userList = (ListView)findViewById(R.id.list);
        userList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("myLog", "Начал");
                Intent intent = new Intent(getApplicationContext(), Analog.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });
        //Пропишем обработчик клика кнопки


    }


    @Override
    public void onResume() {
        super.onResume();
        // открываем подключение
//        mDb = mDBHelper.open();
        //получаем данные из бд в виде курсора
        userCursor =  mDb.rawQuery("select * from "+ DatabaseHelper.TABLE, null);
        // определяем, какие столбцы из курсора будут выводиться в ListView
        String[] headers = new String[] {DatabaseHelper.COLUMN_NAME, DatabaseHelper.COLUMN_PRICE, DatabaseHelper.COLUMN_IMG};
        // создаем адаптер, передаем в него курсор
        userAdapter = new SimpleCursorAdapter(this, R.layout.card,
                userCursor, headers, new int[]{R.id.name, R.id.price, R.id.drug}, 0);
        userList.setAdapter(userAdapter);

    }

    @Override
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.options_menu, menu);
        Log.d("Menu", "Created");

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            SearchManager manager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
            final SearchView search = (SearchView) menu.findItem(R.id.search).getActionView();
            search.setSearchableInfo(manager.getSearchableInfo(getComponentName()));

            search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

                @Override
                public boolean onQueryTextSubmit(String s) {
                    Log.d("myLog", "onQueryTextSubmit ");
                    cursor=searching.getStudentListByKeyword(s);
                    if (cursor==null){
                        Toast.makeText(Drugs.this,"No records found!",Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(Drugs.this, cursor.getCount() + " records found!",Toast.LENGTH_LONG).show();
                    }
                    userAdapter.swapCursor(cursor);

                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    Log.d("myLog", "onQueryTextChange ");
                    cursor=searching.getStudentListByKeyword(s);
                    if (cursor!=null){
                        userAdapter.swapCursor(cursor);
                    }
                    return false;
                }

            });

        }

        return true;

    }
}
