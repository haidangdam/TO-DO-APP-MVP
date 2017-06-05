package com.example.haidangdam.myapplication.main_activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import com.example.haidangdam.myapplication.R;
import com.google.firebase.FirebaseApp;


public class MainActivity extends AppCompatActivity {
  MainActivityInterface.Presenter mainActivityPresenter;
  MainActivityView mainActivityView;
  MainActivityRepository mainActivityRepository;
  private Toolbar toolBar;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    Log.d("Main Application", "Activity main");
    super.onCreate(savedInstanceState);
    FirebaseApp.initializeApp(this);
    setContentView(R.layout.main_activity);
    mainActivityRepository = new MainActivityRepository(this);
    toolBar = (Toolbar) findViewById(R.id.my_toolbar);
    setSupportActionBar(toolBar);
    mainActivityView = MainActivityView.newInstance();

    mainActivityPresenter = new MainActivityPresenter(mainActivityView,
        mainActivityRepository);
    FrameLayout frameLayoutActivity = (FrameLayout) findViewById(R.id.fragment_in_main);
    frameLayoutActivity.setId(R.id.fragment_in_main);
    getFragmentManager().beginTransaction().add(R.id.fragment_in_main, mainActivityView).commit();
  }

  @Override
  public void onResume() {
    super.onResume();
    mainActivityPresenter.getAllTaskFromDatabase();
    mainActivityPresenter.loadTask();
    mainActivityView.updateListView();
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent intent) {
    if (resultCode == Activity.RESULT_OK && requestCode == MainActivityInterface.REQUEST_CODE) {
      mainActivityPresenter.getAllTaskFromDatabase();
      mainActivityPresenter.loadTask();
      mainActivityView.updateListView();
    }
  }
  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    switch (item.getItemId()) {
      case R.id.clear_completed: {
        mainActivityView.deleteCompletedTask();
        return true;
      }
      case R.id.give_all_action: {
        mainActivityView.getAllTaskFromDatabase();
        return true;
      }
      case R.id.sort_by_completed: {
        mainActivityView.getCompletedTask();
        return true;
      }
      case R.id.sort_by_incomplete: {
        mainActivityView.getUncompletedTask();
        return true;
      }
      default: {
        return super.onOptionsItemSelected(item);
      }
    }
  }
}
