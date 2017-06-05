package com.example.haidangdam.myapplication.main_activity;

import android.util.Log;
import com.example.haidangdam.myapplication.Task;
import java.util.ArrayList;

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
  public void getAllTaskFromDatabase() {
    ArrayList<Task> listTask = taskRepo.r.getAllTask();
    if (listTask.isEmpty()) {
      getTaskFromFirebase();
    } else {
      mainActivityView.populateListView(taskRepo.r.getAllTask());
    }
  }

  @Override
  public void pressButton() {
    mainActivityView.addNewTask();
  }

  @Override
  public void loadTask() {
    mainActivityView.taskAppear();
  }

  @Override
  public void updateTask(boolean isChecked, String ID) {
    taskRepo.r.updateIsChecked(isChecked, ID);
  }

  @Override
  public void getCompletedTask() {
    mainActivityView.populateListView(taskRepo.r.sortCompleted());
  }

  @Override
  public void getUncompletedTask() {
    mainActivityView.populateListView(taskRepo.r.sortUncompleted());
  }

  @Override
  public void deleteCompletedTask() {
    taskRepo.r.deleteCompletedTask();
    getAllTaskFromDatabase();
  }

  @Override
  public void enterEditTask(Task t) {
    mainActivityView.enterEditTask(t);
  }

  @Override
  public boolean checkTimeFromDatabase() {
    return taskRepo.r.getExpiredData().isEmpty();
  }

  @Override
  public void getTaskFromFirebase() {
    ArrayList<Task> taskList = taskRepo.r.getTaskFromFirebase();
    taskRepo.r.deleteAllTask();
    mainActivityView.populateListView(taskList);
    taskRepo.r.addListTask(taskList);
  }
}