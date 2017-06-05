package com.example.haidangdam.myapplication.task;

import com.example.haidangdam.myapplication.Task;
import java.util.ArrayList;

/**
 * Created by haidangdam on 5/31/17.
 */

public interface TaskRepositoryContract {
  Task getTask(String id);
  ArrayList<Task> getAllTask();
  void updateTask(String name, String description, String ID, boolean status);
  void deleteTask(String id);
  void deleteAllTask();
  void addData(Task t, long time);
  ArrayList<Task> sortCompleted();
  ArrayList<Task> sortUncompleted();
  void deleteCompletedTask();
  void updateIsChecked(boolean isChecked, String ID);
  ArrayList<Task> getExpiredData();
  ArrayList<Task> getTaskFromFirebase();
  void addListTask(ArrayList<Task> t);
}
