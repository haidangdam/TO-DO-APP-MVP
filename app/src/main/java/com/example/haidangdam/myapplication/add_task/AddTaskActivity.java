package com.example.haidangdam.myapplication.add_task;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import com.example.haidangdam.myapplication.R;

/**
 * Created by haidangdam on 5/31/17.
 */

public class AddTaskActivity extends FragmentActivity {
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.add_task_activity);
    AddTaskInterface.View addTaskView = AddTaskView.newInstance();
    AddTaskRepository repo = new AddTaskRepository(this);
    AddTaskInterface.Presenter addTaskPresenter = new AddTaskPresenter(repo, addTaskView);

  }
}
