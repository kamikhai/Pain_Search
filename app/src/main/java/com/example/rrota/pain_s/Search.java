package com.example.rrota.pain_s;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Search {
    private DatabaseHelper dbHelper;

    public Search(Context context) {
        dbHelper = new DatabaseHelper(context);
    }





    public Cursor getStudentListByKeyword(String search) {
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  rowid as " +
                DatabaseHelper.COLUMN_ID + "," +
                DatabaseHelper.COLUMN_NAME + "," +
                DatabaseHelper.COLUMN_PRICE + "," +
                DatabaseHelper.COLUMN_IMG +
                " FROM " + DatabaseHelper.TABLE +
                " WHERE " +  DatabaseHelper.COLUMN_NAME + "  LIKE  '%" +search + "%' "
                ;


        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        if (cursor == null) {
            return null;
        } else if (!cursor.moveToFirst()) {
            cursor.close();
            return null;
        }
        return cursor;


    }




}
