package com.example.haidangdam.myapplication.add_task;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.FrameLayout;
import com.example.haidangdam.myapplication.R;

/**
 * Created by haidangdam on 5/31/17.
 */

public class AddEditTaskActivity extends AppCompatActivity {
  AddEditTaskView addTaskView;
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.add_task_activity);
    if (getIntent().getExtras() != null) {
      Log.d("My application", "Get data");
      addTaskView = AddEditTaskView.newInstance(getIntent().getExtras());
    }
    addTaskView = AddEditTaskView.newInstance();
    AddEditTaskRepository repo = new AddEditTaskRepository(this);
    AddEditTaskInterface.Presenter addTaskPresenter = new AddEditTaskPresenter(repo, addTaskView);
    FrameLayout frameLayoutAddTask = (FrameLayout) findViewById(R.id.add_task_fragment);
    frameLayoutAddTask.setId(R.id.add_task_fragment);
    getFragmentManager().beginTransaction().add(R.id.add_task_fragment, addTaskView).commit();
  }
}
