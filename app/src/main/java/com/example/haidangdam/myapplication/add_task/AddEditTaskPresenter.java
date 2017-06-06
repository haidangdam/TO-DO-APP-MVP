package com.example.haidangdam.myapplication.add_task;

import android.os.Bundle;
import android.util.Log;
import com.example.haidangdam.myapplication.Task;
import com.example.haidangdam.myapplication.main_activity.MainActivityInterface;
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

  /**
   * Save button press
   * @param taskName the new task name
   * @param description the new description
   * @param bundle bundle to differentiate between edit or add (edit
   *                will have already existed data in bundle)
   */
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
        addTaskView.submitButton();
      }
    }
  }

  /**
   * To save the task to the database
   * @param taskName task name in the name field
   * @param description task description in description field
   * @param alreadyExisted boolean to differentiate between edit or first-add. If edit, use
   *  update, otherwise, use add
   */
  @Override
  public void saveTask(String taskName, String description, boolean alreadyExisted) {
    if (!alreadyExisted) {
      repo.r.addTaskToFirebase(new Task(taskName, description, false), new MainActivityInterface.AddFirebaseCallback() {
        @Override
        public void callback() {
          addTaskView.submitButton();
        }
      });
    } else {
      repo.r.updateTask(taskName, description, bundle.getString(MainActivityView.ID), bundle.getInt(MainActivityView.STATUS) == 1);
    }
  }

  /**
   * If press the delete button when editting the task
   * @param bundle the data getting from the task
   */
  @Override
  public void deleteButtonPress(Bundle bundle) {
    repo.r.deleteTask(bundle.getString(MainActivityView.ID));
    addTaskView.submitButton();
  }


}
