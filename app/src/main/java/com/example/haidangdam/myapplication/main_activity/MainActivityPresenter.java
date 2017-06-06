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
  ArrayList<Task> taskList;

  /**
   * Initialize the presenter accordingly to the MVP architecture
   * @param view the view inference for the presenter to accesss
   * @param r the main repository access for the presenter of the function
   */
  public MainActivityPresenter(MainActivityInterface.View view, MainActivityRepository r) {
    Log.d("My Application", "Presenter is created");
    mainActivityView = view;
    taskRepo = r;
    mainActivityView.setPresenter(this);
    taskList = new ArrayList<Task>();
  }

  /**
   * When no task appear in the database, calling the noTaskAppear function from
   * view for the corresponding action.
   */
  @Override
  public void noTaskAppear() {
    mainActivityView.noTaskAppear();
  }


  /**
   * Get all task from database. If the database is empty, try to access firebase. If not,
   * populate list view.
   */
  @Override
  public void getAllTaskFromDatabase() {
    Log.d("My application", "Get all task from database");
    ArrayList<Task> listTask = taskRepo.r.getAllTask();
    if (listTask.isEmpty()) {
      Log.d("My application", "Get from firebase list task empty");
      getTaskFromFirebase();
    } else {
      mainActivityView.populateListView(taskRepo.r.getAllTask());
      loadTask();
    }
  }

  /**
   * When add button from the view is add, presenter will call the corresponding
   * action from the view
   */
  @Override
  public void pressButton() {
    mainActivityView.addNewTask();
  }

  /**
   * When loading task, calling the view to get task appear
   */
  @Override
  public void loadTask() {
    Log.d("Main presenter", "Load task");
    mainActivityView.taskAppear();
  }


  /**
   * When the task is updated in the view (by checking the checkbox or unchecking the check
   * box, the update task is called)
   * @param isChecked new status of the task's checkbox
   * @param ID the id of the
   */
  @Override
  public void updateTask(boolean isChecked, String ID) {
    taskRepo.r.updateIsChecked(isChecked, ID);
  }

  /**
   *t   */
  @Override
  public void getCompletedTask() {
    mainActivityView.populateListView(taskRepo.r.sortCompleted());
  }


  /**
   *
   */
  @Override
  public void getUncompletedTask() {
    mainActivityView.populateListView(taskRepo.r.sortUncompleted());
  }

  /**
   *
   */
  @Override
  public void deleteCompletedTask() {
    taskRepo.r.deleteCompletedTask();
    getAllTaskFromDatabase();
  }

  /**
   *
   * @param t
   */
  @Override
  public void enterEditTask(Task t) {
    mainActivityView.enterEditTask(t);
  }


  /**
   *
   * @return
   */
  @Override
  public boolean checkTimeFromDatabase() {
    return taskRepo.r.getExpiredData().isEmpty();
  }


  /**
   *
   */
  @Override
  public void getTaskFromFirebase() {
    Log.d("Main presenter", "Get task from firebase");
    taskRepo.r.deleteAllTask();
    taskRepo.r.getTaskFromFirebase(new MainActivityInterface.FirebaseCallback() {
      @Override
      public void callbackFirebase(ArrayList<Task> t) {
        Log.d("Main presenter", "Call back firebase");
        mainActivityView.populateListView(t);
        loadTask();
      }
    });
  }

}