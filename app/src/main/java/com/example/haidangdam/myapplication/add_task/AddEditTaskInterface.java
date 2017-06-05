package com.example.haidangdam.myapplication.add_task;

import android.os.Bundle;

/**
 * Created by haidangdam on 5/31/17.
 */

public interface AddEditTaskInterface {
  interface View {
    void submitButton();
    void setPresenter(AddEditTaskInterface.Presenter a);
    void emptyField();
  }

  interface Presenter {
    void buttonPress(String name, String description, Bundle bundle);
    void saveTask(String taskName, String description, boolean alreadyExisted);
    void deleteButtonPress(Bundle bundle);
  }
}
