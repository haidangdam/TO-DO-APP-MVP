package com.example.haidangdam.myapplication.main_activity;

import android.content.Context;
import android.util.Log;
import com.example.haidangdam.myapplication.task.TaskRepositoryAction;

/**
 * Created by haidangdam on 5/31/17.
 */

public class MainActivityRepository {
  public static TaskRepositoryAction r;

  public MainActivityRepository(Context c) {
    Log.d("Main repository", "Create repo");
    if (r == null) {
      Log.d("Main repository", "Create new instance");
      r = TaskRepositoryAction.newInstance(c);
    }
  }
}
