package com.example.haidangdam.myapplication.task;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.util.Log;
import com.example.haidangdam.myapplication.Task;
import com.example.haidangdam.myapplication.main_activity.MainActivityInterface;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

/**
 * Created by haidangdam on 5/31/17.
 */

public class TaskRepositoryAction implements TaskRepositoryContract {
  public static boolean HAVE_ADDED = false;
  TaskRepository taskRepository;
  public static TaskRepositoryAction taskRepositoryAction;
  private DatabaseReference taskDatabaseReference;
  private static ArrayList<Task> taskFromFirebase;
  private boolean completed = false;
  /**
   *
   * @param ctx
   */
  private TaskRepositoryAction(Context ctx) {
    taskRepository = new TaskRepository(ctx);
    taskDatabaseReference = FirebaseDatabase.getInstance().getReference(TaskRepository.TABLE_NAME);
    taskFromFirebase = new ArrayList();
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

  /**
   *
   * @param t
   * @param time
   */
  @Override
  public void addData(@NonNull Task t, @NonNull long time) {
    int completed;
    if (t.getcompleted()) {
      completed = 1;
    } else {
      completed = 0;
    }
    SQLiteDatabase db = taskRepository.getWritableDatabase();
    if (t != null) {
      db.execSQL(
          "INSERT INTO " + taskRepository.TABLE_NAME + " (" + taskRepository.ID + ", " + taskRepository.TASK_NAME
              + ", " + taskRepository.TASK_DESCRIPTION + ", " + taskRepository.STATUS_COMPLETED
              + ", " + taskRepository.TIME + ") VALUES ('" + t.getid() + "', '" + t.getname() + "', '"
              + t.getdescription() + "', '" + completed + "', " + time + ");");
      Log.d("Database", "Add succeed");
    } }

  /**
   *
   * @param id
   * @return
   */
  @Override
  public Task getTask(@NonNull String id) {
    Cursor cursor = taskRepository.getReadableDatabase().rawQuery("SELECT * FROM " + taskRepository.TABLE_NAME
        + " WHERE " + taskRepository.ID + " = " + id + ";", null);
    cursor.moveToFirst();
    return fromCursorToTask(cursor);
  }

  /**
   *
   * @return
   */
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
    t.setid(c.getString(c.getColumnIndex(taskRepository.ID)));
    return t;
  }

  /**
   *
   * @param name
   * @param description
   * @param ID
   * @param status
   */
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
    db.execSQL("UPDATE " + taskRepository.TABLE_NAME + " SET " + taskRepository.TASK_NAME + " = '" + t.getname()
            + "', " + taskRepository.TASK_DESCRIPTION + " = '" + t.getdescription() + "', "
            + taskRepository.STATUS_COMPLETED + " = '" + completed + "', " + taskRepository.ID + " = '" + t.getid()
            + "' WHERE " + taskRepository.ID + " = '" + ID + "';");
  }

  /**
   *
   * @param isChecked
   * @param ID
   */
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

  /**
   *
   * @param id
   */
  @Override
  public void deleteTask(@NonNull String id) {
    SQLiteDatabase db = taskRepository.getWritableDatabase();
    db.execSQL("DELETE FROM " + taskRepository.TABLE_NAME + " WHERE " + taskRepository.ID + " = '" + id + "';");
  }


  /**
   *
   */
  @Override
  public void deleteCompletedTask() {
    SQLiteDatabase db = taskRepository.getWritableDatabase();
    db.execSQL("DELETE FROM " + taskRepository.TABLE_NAME + " WHERE " + taskRepository.STATUS_COMPLETED + " = '1';");
  }

  /**
   *
   */
  @Override
  public void deleteAllTask() {
    Log.d("My application", "Delete all from data");
    SQLiteDatabase db = taskRepository.getWritableDatabase();
    db.execSQL("DELETE FROM " + taskRepository.TABLE_NAME + ";");
  }

  /**
   *
   * @return
   */
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

  /**
   *
   * @return
   */
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

  /**
   *
   * @return
   */
  @Override
  public ArrayList<Task> getExpiredData() {
    SQLiteDatabase db = taskRepository.getReadableDatabase();
    Cursor cursor = db.rawQuery("SELECT * FROM " + taskRepository.TABLE_NAME + " WHERE " + System.currentTimeMillis()
                  + " - " + taskRepository.TIME + " > " + MainActivityInterface.MAXIMUM_ALLOW_TIME + ";", null);
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
  public void getTaskFromFirebase(MainActivityInterface.FirebaseCallback callback1) {
    Log.d("My application", "Get task from firebase");
    final MainActivityInterface.FirebaseCallback callback = callback1;
    taskDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
      @Override
      public void onDataChange(DataSnapshot dataSnapshot) {
        for (DataSnapshot a : dataSnapshot.getChildren()) {
          Log.d("My application", "Add task from firebase to list");
          taskFromFirebase.add((Task) a.getValue(Task.class));
        }
        callback.callbackFirebase(taskFromFirebase);
        addListTask(taskFromFirebase);
      }
      @Override
      public void onCancelled(DatabaseError err) {
          Log.d("My application", err.getDetails());
        }
    });
  }

  @Override
  public void addTaskToFirebase(Task t, MainActivityInterface.AddFirebaseCallback callback) {
    final Task task = t;
    final MainActivityInterface.AddFirebaseCallback firebaseCallback = callback;
    taskFromFirebase.add(t);
    taskDatabaseReference.setValue(taskFromFirebase, new DatabaseReference.CompletionListener() {
      @Override
      public void onComplete(DatabaseError e, DatabaseReference r) {
        Log.d("Task Repository", "Add task to database successful");
        addData(task, System.currentTimeMillis());
        firebaseCallback.callback();
      }
    });
  }

  @Override
  public void addListTask(ArrayList<Task> t) {
    for (Task task : t) {
      addData(task, System.currentTimeMillis());
    }
  }

}