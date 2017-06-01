package com.example.haidangdam.myapplication.main_activity;

import com.example.haidangdam.myapplication.Task;
import java.util.ArrayList;

/**
 * Created by haidangdam on 5/30/17.
 */

public interface MainActivityInterface {
  interface View {
    void pressItem(int position);
    void populateListView(ArrayList<Task> a);
    void setPresenter(MainActivityInterface.Presenter p);
    void noTaskAppear();
    void addNewTask();
  }

  interface Presenter {
    //void loadTask();
    //void pressItem();
    void noTaskAppear();
    void getTaskFromDatabase();
    void pressButton();
  }
}
