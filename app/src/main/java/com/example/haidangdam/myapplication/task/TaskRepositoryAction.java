package com.example.haidangdam.myapplication.task;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.util.Log;
import com.example.haidangdam.myapplication.Task;
import java.util.ArrayList;

/**
 * Created by haidangdam on 5/31/17.
 */

public class TaskRepositoryAction implements TaskRepositoryContract {

  TaskRepository taskRepository;
  public static TaskRepositoryAction taskRepositoryAction;

  /**
   *
   * @param ctx
   */
  private TaskRepositoryAction(Context ctx) {
    taskRepository = new TaskRepository(ctx);
  }

  /**
   *
   * @param ctx
   * @return
   */
  public static TaskRepositoryAction newInstance(Context ctx) {
    if (taskRepositoryAction == null) {
      taskRepositoryAction = new TaskRepositoryAction(ctx);
    }
    return taskRepositoryAction;
  }

  @Override
  public void addData(@NonNull Task t) {
    int completed;
    if (t.getStatusCompleted()) {
      completed = 1;
    } else {
      completed = 0;
    }
    SQLiteDatabase db = taskRepository.getWritableDatabase();
    if (t != null) {
      db.execSQL(
          "INSERT INTO " + taskRepository.TABLE_NAME + " (" + taskRepository.ID + ", " + taskRepository.TASK_NAME
              + ", " + taskRepository.TASK_DESCRIPTION + ", " + taskRepository.STATUS_COMPLETED
              + ") VALUES ('" + t.getId() + "', '" + t.getName() + "', '"
              + t.getDescription() + "', '" + completed + "');");
      Log.d("Database", "Add succeed");
    } }

  @Override
  public Task getTask(@NonNull String id) {
    Cursor cursor = taskRepository.getReadableDatabase().rawQuery("SELECT * FROM " + taskRepository.TABLE_NAME
        + " WHERE " + taskRepository.ID + " = " + id + ";", null);
    cursor.moveToFirst();
    return fromCursorToTask(cursor);
  }

  @Override
  public ArrayList<Task> getAllTask() {
    Log.d("Repo", "Load task from database");
    Cursor cursor = taskRepository.getReadableDatabase().rawQuery("SELECT * FROM " + taskRepository.TABLE_NAME + ";", null);
    ArrayList<Task> listTask = new ArrayList<>();
    if (cursor.getCount() > 0) {
      Log.d("Repo", "Get database successful");
      cursor.moveToFirst();
      do {
        Log.d("Repo", "Adding data to array list");
        Task t = fromCursorToTask(cursor);
        listTask.add(t);
      } while (cursor.moveToNext());
    }
    cursor.close();
    return listTask;
  }

  /**
   *
   * @param c
   * @return
   */
  private Task fromCursorToTask(@NonNull Cursor c) {
    String name = c.getString(c.getColumnIndex(taskRepository.TASK_NAME));
    String description = c.getString(c.getColumnIndex(taskRepository.TASK_DESCRIPTION));
    boolean completed = c.getString(c.getColumnIndex(taskRepository.STATUS_COMPLETED)).equals("1");
    Task t = new Task(name, description, completed);
    t.setId(c.getString(c.getColumnIndex(taskRepository.ID)));
    return t;
  }

  @Override
  public void updateTask(String name, String description, String ID, boolean status) {
    int completed;
    if (status) {
      completed = 1;
    } else {
      completed = 0;
    }
    SQLiteDatabase db = taskRepository.getWritableDatabase();
    Task t = new Task(name, description, status);
    db.execSQL("UPDATE " + taskRepository.TABLE_NAME + " SET " + taskRepository.TASK_NAME + " = '" + t.getName()
            + "', " + taskRepository.TASK_DESCRIPTION + " = '" + t.getDescription() + "', "
            + taskRepository.STATUS_COMPLETED + " = '" + completed + "', " + taskRepository.ID + " = '" + t.getId()
            + "' WHERE " + taskRepository.ID + " = '" + ID + "';");
  }

  @Override
  public void updateIsChecked(boolean isChecked, String ID) {
    String completed;
    if (isChecked) {
      completed = "1";
    } else {
      completed = "0";
    }
    SQLiteDatabase db = taskRepository.getWritableDatabase();
    db.execSQL("UPDATE " + taskRepository.TABLE_NAME + " SET " + taskRepository.STATUS_COMPLETED + " = '"
                + completed + "' WHERE " + taskRepository.ID + " = '" + ID + "';");
  }
  @Override
  public void deleteTask(@NonNull String id) {
    SQLiteDatabase db = taskRepository.getWritableDatabase();
    db.execSQL("DELETE FROM " + taskRepository.TABLE_NAME + " WHERE " + taskRepository.ID + " = '" + id + "';");
  }

  @Override
  public void deleteCompletedTask() {
    SQLiteDatabase db = taskRepository.getWritableDatabase();
    db.execSQL("DELETE FROM " + taskRepository.TABLE_NAME + " WHERE " + taskRepository.STATUS_COMPLETED + " = '1';");
  }
  @Override
  public void deleteAllTask() {
    SQLiteDatabase db = taskRepository.getWritableDatabase();
    db.execSQL("DELETE * FROM " + taskRepository.TABLE_NAME + ";");
  }

  @Override
  public ArrayList<Task> sortCompleted() {
    SQLiteDatabase db = taskRepository.getReadableDatabase();
    Cursor cursor = db.rawQuery("SELECT * FROM " + taskRepository.TABLE_NAME + " WHERE " + taskRepository.STATUS_COMPLETED
                + " = '1';", null);
    ArrayList<Task> listTask = new ArrayList<>();
    if (cursor.getCount() > 0) {
      Log.d("Repo", "Get database successful");
      cursor.moveToFirst();
      do {
        Log.d("Repo", "Adding data to array list");
        Task t = fromCursorToTask(cursor);
        listTask.add(t);
      } while (cursor.moveToNext());
    }
    return listTask;
  }

  @Override
  public ArrayList<Task> sortUncompleted() {
    SQLiteDatabase db = taskRepository.getReadableDatabase();
    Cursor cursor = db.rawQuery("SELECT * FROM " + taskRepository.TABLE_NAME + " WHERE " + taskRepository.STATUS_COMPLETED
        + " = '0';", null);
    ArrayList<Task> listTask = new ArrayList<>();
    if (cursor.getCount() > 0) {
      Log.d("Repo", "Get database successful");
      cursor.moveToFirst();
      do {
        Log.d("Repo", "Adding data to array list");
        Task t = fromCursorToTask(cursor);
        listTask.add(t);
      } while (cursor.moveToNext());
    }
    return listTask;
  }

}