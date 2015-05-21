package com.android.will.wnews.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.android.will.wnews.model.Category;

import java.util.ArrayList;
import java.util.List;

/**
 * Paper      : COMP548-15A(HAM)
 * Student ID : 125491
 * Name       : Shuzu Li
 * Email      : lishuzu@gmail.com
 * <p/>
 * Created by Shuzu Li on 21/05/15.
 */
public class NewsCategoriesDataSource {
    public static final String TAG = "NewsCatDataSource";
    private SQLiteDatabase database;
    private DatabaseHelper db=null;
    private String[] allColumns = { DatabaseHelper.Category.COLUMN_NAME_CID,
            DatabaseHelper.Category.COLUMN_NAME_NAME };


    public NewsCategoriesDataSource(Context context) {
        db = DatabaseHelper.getInstance(context);
    }

    public void open() {
        Log.d(TAG, "open");
        database = db.getWritableDatabase();
    }
    public void close() {
        Log.d(TAG, "close");
        db.close();
    }

    public void createCategory(Category category) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.Category.COLUMN_NAME_CID, category.id);
        values.put(DatabaseHelper.Category.COLUMN_NAME_NAME, category.name);
        database.insert(
                DatabaseHelper.Category.TABLE_NAME,
                null,
                values);
    }


    public ArrayList<Category> getCategories() {
        ArrayList<Category> categoryList = new ArrayList<Category>();

        Cursor cursor = database.query(DatabaseHelper.Category.TABLE_NAME,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Category category = cursorToCategory(cursor);
            categoryList.add(category);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return categoryList;
    }

    public List<String> getCategoriesNames() {
        List<String> names = new ArrayList<String>();

        Cursor cursor = database.query(DatabaseHelper.Category.TABLE_NAME,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Category category = cursorToCategory(cursor);
            names.add(category.name);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return names;
    }


    public Category cursorToCategory(Cursor cursor) {
        Category category = new Category();
        category.id = cursor.getInt(0);
        category.name = cursor.getString(1);
        return category;
    }

    public int getCatIdByName(String catName) {

        int id = 0;
        Cursor cursor = database.query(DatabaseHelper.Category.TABLE_NAME,
                allColumns, DatabaseHelper.Category.COLUMN_NAME_NAME+" =?", new String[]{catName}, null, null, null, "0,1");
        cursor.moveToFirst();

        if(cursor.getCount() > 0){
            id = cursorToCategory(cursor).id;
        }
        cursor.close();

        return id;
    }

    public int updateCache(){
        return database.delete(DatabaseHelper.Category.TABLE_NAME, "1", null);
    }
}
