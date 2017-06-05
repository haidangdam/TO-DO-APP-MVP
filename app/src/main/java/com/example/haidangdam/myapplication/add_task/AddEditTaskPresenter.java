package com.example.haidangdam.myapplication.add_task;

import android.os.Bundle;
import android.util.Log;
import com.example.haidangdam.myapplication.Task;
import com.example.haidangdam.myapplication.main_activity.MainActivityView;

/**
 * Created by haidangdam on 5/31/17.
 */

public class AddEditTaskPresenter implements AddEditTaskInterface.Presenter {

  AddEditTaskRepository repo;
  AddEditTaskInterface.View addTaskView;
  Bundle bundle;

  public AddEditTaskPresenter(AddEditTaskRepository repo, AddEditTaskInterface.View addTaskInterface) {
    this.repo = repo;
    this.addTaskView = addTaskInterface;
    addTaskView.setPresenter(this);
  }

  @Override
  public void buttonPress(String taskName, String description, Bundle bundle) {
    Log.d("Main Application", "Add button press");
    if (taskName.isEmpty() || description.isEmpty()) {
      addTaskView.emptyField();
    } else {
      if (bundle == null) {
        saveTask(taskName, description, false);
      } else {
        this.bundle = bundle;
        saveTask(taskName, description, true);
      }
      addTaskView.submitButton();
    }
  }

  @Override
  public void saveTask(String taskName, String description, boolean alreadyExisted) {
    if (!alreadyExisted) {
      repo.r.addData(new Task(taskName, description, false));
    } else {
      repo.r.updateTask(taskName, description, bundle.getString(MainActivityView.ID), bundle.getInt(MainActivityView.STATUS) == 1);
    }
  }

  @Override
  public void deleteButtonPress(Bundle bundle) {
    repo.r.deleteTask(bundle.getString(MainActivityView.ID));
    addTaskView.submitButton();
  }


}
