package com.example.haidangdam.myapplication.task;

import com.example.haidangdam.myapplication.Task;
import java.util.ArrayList;

/**
 * Created by haidangdam on 5/31/17.
 */

public interface TaskRepositoryContract {
  Task getTask(String id);
  ArrayList<Task> getAllTask();
  void updateTask(Task t);
  void deleteTask(String id);
  void deleteAllTask();
  void addData(Task t);

}
