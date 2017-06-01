package com.example.haidangdam.myapplication.main_activity;

import android.util.Log;

/**
 * Created by haidangdam on 5/30/17.
 */

public class MainActivityPresenter implements MainActivityInterface.Presenter {

  MainActivityInterface.View mainActivityView;
  MainActivityRepository taskRepo;

  public MainActivityPresenter(MainActivityInterface.View view, MainActivityRepository r) {
    mainActivityView = view;
    taskRepo = r;
    mainActivityView.setPresenter(this);
    Log.d("My Application", "Presenter is created");
  }

  @Override
  public void noTaskAppear() {
    mainActivityView.noTaskAppear();
  }

  @Override
  public void getTaskFromDatabase() {
    mainActivityView.populateListView(taskRepo.r.getAllTask());
  }

  @Override
  public void pressButton() {
    mainActivityView.addNewTask();
  }
}