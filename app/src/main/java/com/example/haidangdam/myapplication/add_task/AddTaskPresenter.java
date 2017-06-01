package com.example.haidangdam.myapplication.add_task;

import com.example.haidangdam.myapplication.Task;

/**
 * Created by haidangdam on 5/31/17.
 */

public class AddTaskPresenter implements AddTaskInterface.Presenter {

  AddTaskRepository repo;
  AddTaskInterface.View addTaskView;

  public AddTaskPresenter(AddTaskRepository repo, AddTaskInterface.View addTaskInterface) {
    this.repo = repo;
    this.addTaskView = addTaskInterface;
    addTaskView.setPresenter(this);
  }

  @Override
  public void buttonPress(String taskName, String description) {
    if (taskName.isEmpty() || description.isEmpty()) {
      addTaskView.emptyField();
    } else {
      saveTask(taskName, description);
      addTaskView.submitButton();
    }
  }

  @Override
  public void saveTask(String taskName, String description) {
    repo.r.addData(new Task(taskName, description, false));
  }
}
