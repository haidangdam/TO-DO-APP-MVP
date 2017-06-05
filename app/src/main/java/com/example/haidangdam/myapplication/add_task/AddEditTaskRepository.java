package com.example.haidangdam.myapplication.add_task;

import android.content.Context;
import com.example.haidangdam.myapplication.task.TaskRepositoryAction;

/**
 * Created by haidangdam on 5/31/17.
 */

public class AddEditTaskRepository {
  public static TaskRepositoryAction r;

  public AddEditTaskRepository(Context c) {
    if (r == null) {
      r = TaskRepositoryAction.newInstance(c);
    }
  }
}
