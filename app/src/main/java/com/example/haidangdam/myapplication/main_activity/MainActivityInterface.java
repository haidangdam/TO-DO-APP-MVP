package com.example.haidangdam.myapplication.main_activity;

import com.example.haidangdam.myapplication.Task;
import java.util.ArrayList;

/**
 * Created by haidangdam on 5/30/17.
 */

public interface MainActivityInterface {
  public static final int REQUEST_CODE = 1;
  interface View {
    void pressItem(int position);
    void populateListView(ArrayList<Task> a);
    void setPresenter(MainActivityInterface.Presenter p);
    void noTaskAppear();
    void addNewTask();
    void updateListView();
    void taskAppear();
    void getCompletedTask();
    void getUncompletedTask();
    void deleteCompletedTask();
    void getAllTaskFromDatabase();
    void enterEditTask(Task t);

  }

  interface Presenter {
    void loadTask();
    void noTaskAppear();
    void getAllTaskFromDatabase();
    void pressButton();
    void updateTask(boolean isCheck, String id);
    void getCompletedTask();
    void getUncompletedTask();
    void deleteCompletedTask();
    void enterEditTask(Task t);
  }
}
