package com.example.haidangdam.myapplication.add_task;

import android.content.Context;
import com.example.haidangdam.myapplication.task.TaskRepositoryAction;

/**
 * Created by haidangdam on 5/31/17.
 */

public class AddTaskRepository {
  public static TaskRepositoryAction r;

  public AddTaskRepository(Context c) {
    r = TaskRepositoryAction.newInstance(c);
  }
}
