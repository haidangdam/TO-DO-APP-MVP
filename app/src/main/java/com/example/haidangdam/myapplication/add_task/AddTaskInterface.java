package com.example.haidangdam.myapplication.add_task;

/**
 * Created by haidangdam on 5/31/17.
 */

public interface AddTaskInterface {
  interface View {
    void submitButton();
    void setPresenter(AddTaskInterface.Presenter a);
    void emptyField();
  }

  interface Presenter {
    void buttonPress(String name, String description);
    void saveTask(String taskName, String description);

  }
}
