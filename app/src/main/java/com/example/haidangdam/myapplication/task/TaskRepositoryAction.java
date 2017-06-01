package com.example.haidangdam.myapplication.task;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import com.example.haidangdam.myapplication.Task;
import java.util.ArrayList;

/**
 * Created by haidangdam on 5/31/17.
 */

public class TaskRepositoryAction implements TaskRepositoryContract {

  TaskRepository taskRepository;
  public static TaskRepositoryAction taskRepositoryAction;

  private TaskRepositoryAction(Context ctx) {
    taskRepository = new TaskRepository(ctx);
  }

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
              + ") VALUES (" + t.getId() + ", " + t.getName() + ", "
              + t.getDescription() + ", " + completed);
    } }

  @Override
  public Task getTask(@NonNull String id) {
    Cursor cursor = taskRepository.getReadableDatabase().rawQuery("SELECT * FROM " + taskRepository.TABLE_NAME
        + " WHERE " + taskRepository.ID + " = " + id, null);
    cursor.moveToFirst();
    return fromCursorToTask(cursor);
  }

  @Override
  public ArrayList<Task> getAllTask() {
    Cursor cursor = taskRepository.getReadableDatabase().rawQuery("SELECT * FROM " + taskRepository.TABLE_NAME, null);
    ArrayList<Task> listTask = new ArrayList<>();
    if (cursor.getCount() > 0) {
      cursor.moveToFirst();
      while (!cursor.isAfterLast()) {
        Task t = fromCursorToTask(cursor);
        listTask.add(t);
      }
    }
    return listTask;
  }

  private Task fromCursorToTask(@NonNull Cursor c) {
    String name = c.getString(c.getColumnIndex(taskRepository.TASK_NAME));
    String description = c.getString(c.getColumnIndex(taskRepository.TASK_DESCRIPTION));
    boolean completed = c.getInt(c.getColumnIndex(taskRepository.STATUS_COMPLETED)) > 0;
    Task t = new Task(name, description, completed);
    t.setId(c.getString(c.getColumnIndex(taskRepository.ID)));
    return t;
  }

  @Override
  public void updateTask(@NonNull Task t) {
    int completed;
    if (t.getStatusCompleted()) {
      completed = 1;
    } else {
      completed = 0;
    }
    SQLiteDatabase db = taskRepository.getWritableDatabase();
    String id = t.getId();
    t.updateID();
    db.execSQL("UPDATE " + taskRepository.TABLE_NAME + " SET " + taskRepository.TASK_NAME + " = " + t.getName()
            + ", " + taskRepository.TASK_DESCRIPTION + " = " + t.getDescription() + ", " + taskRepository.STATUS_COMPLETED
            + " = " + completed + ", " + taskRepository.ID + " = " + t.getId() + "WHERE " + taskRepository.ID + " = " + id, null);
  }

  @Override
  public void deleteTask(@NonNull String id) {
    SQLiteDatabase db = taskRepository.getWritableDatabase();
    db.execSQL("DELETE FROM " + taskRepository.TABLE_NAME + " WHERE " + taskRepository.ID + " = " + id);
  }

  @Override
  public void deleteAllTask() {
    SQLiteDatabase db = taskRepository.getWritableDatabase();
    db.execSQL("DELETE * FROM " + taskRepository.TABLE_NAME);
  }


}