package com.example.haidangdam.myapplication.task;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by haidangdam on 5/30/17.
 */

public class TaskRepository extends SQLiteOpenHelper {

  public static final String DATABASE = "data.db";
  public static final String TABLE_NAME = "dataTask";
  public static final String TASK_NAME = "task_name";
  public static final String TASK_DESCRIPTION = "task_description";
  public static final String STATUS_COMPLETED = "status_completed";
  public static final String ID = "id";
  public static final String TIME = "time";

  private static int databaseVersion = 1;

  public TaskRepository(Context context) {
    super(context, DATABASE, null, databaseVersion);
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ( " + ID + " TEXT, "
        + TASK_NAME + " TEXT NOT NULL CHECK (" + TASK_NAME + " != ''), " + TASK_DESCRIPTION
        + " TEXT NOT NULL CHECK (" + TASK_DESCRIPTION + " != ''), " + STATUS_COMPLETED
        + " TEXT NOT NULL " + ", " + TIME + " INTEGER NOT NULL" + ");");
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    databaseVersion = newVersion;
    db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    onCreate(db);
  }
}
