package com.android.will.wnews.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
  private static final String DATABASE_NAME="wnews.db";
  private static final int SCHEMA_VERSION=1;
  private static DatabaseHelper singleton=null;

  /* Inner class that defines the table contents */
  public static abstract class Category implements BaseColumns {
    public static final String TABLE_NAME = "news_categories";
    public static final String COLUMN_NAME_CID = "cid";
    public static final String COLUMN_NAME_NAME = "name";
  }

  private static final String SQL_CREATE_CATEGORIES = "CREATE TABLE "+Category.TABLE_NAME+" ("+Category._ID+" INTEGER PRIMARY KEY, "+Category.COLUMN_NAME_CID +" INTEGER, "+Category.COLUMN_NAME_NAME+" TEXT);";

  private static final String SQL_DELETE_CATEGORIES =
          "DROP TABLE IF EXISTS " + Category.TABLE_NAME;

  public synchronized static DatabaseHelper getInstance(Context ctxt) {
    if (singleton == null) {
      singleton=new DatabaseHelper(ctxt.getApplicationContext());
    }

    return (singleton);
  }

  private DatabaseHelper(Context ctxt) {
    super(ctxt, DATABASE_NAME, null, SCHEMA_VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    db.execSQL(SQL_CREATE_CATEGORIES);

      //default value;
    ContentValues cv=new ContentValues();
    cv.put(Category.COLUMN_NAME_CID, 0);
    cv.put(Category.COLUMN_NAME_NAME, "Top news");
    db.insert(Category.TABLE_NAME, null, cv);

    cv.put(Category.COLUMN_NAME_CID, 1);
    cv.put(Category.COLUMN_NAME_NAME, "Sport");
    db.insert(Category.TABLE_NAME, null, cv);

    cv.put(Category.COLUMN_NAME_CID, 2);
    cv.put(Category.COLUMN_NAME_NAME, "National");
    db.insert(Category.TABLE_NAME, null, cv);

  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion,
                        int newVersion) {
    db.execSQL(SQL_DELETE_CATEGORIES);
    onCreate(db);
  }

}