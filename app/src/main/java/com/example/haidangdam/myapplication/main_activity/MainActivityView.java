package com.example.haidangdam.myapplication.main_activity;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import com.example.haidangdam.myapplication.R;
import com.example.haidangdam.myapplication.Task;
import com.example.haidangdam.myapplication.add_task.AddEditTaskActivity;
import com.example.haidangdam.myapplication.task.TaskRepositoryAction;
import java.util.ArrayList;
/**
 * Created by haidangdam on 5/30/1.
 */

public class MainActivityView extends Fragment implements MainActivityInterface.View,
    OnClickListener
{
  public static final String ID = "id";
  public static final String NAME = "name";
  public static final String DESCRIPTION = "description";
  public static final String STATUS = "status";
  FloatingActionButton addButton;
  ListView listItem;
  TextView textNoItem;
  public static MainActivityInterface.Presenter presenter;
  MainActivityAdapter adapter;
  public static MainActivityView mainActivityView;
  ArrayList<Task> listTask;

  /**
   *
   */
  public MainActivityView() {}

  public static MainActivityView newInstance() {
    if (mainActivityView == null) {
      Log.d("Main fragment", "fragment null");
      mainActivityView = new MainActivityView();
    }
    Log.d("Main fragment", "Create new fragment");
    return mainActivityView;
  }

  /**
   *
   * @param presenter
   */
  @Override
  public void setPresenter(MainActivityInterface.Presenter presenter) {
    this.presenter = presenter;
    Log.d("Main Presenter", "Presenter is set");
  }

  /**
   *
   */
  @Override
  public void updateListView() {
    Log.d("Main activity fragment", "update list view");
    if (listTask.isEmpty()) {
      this.presenter.noTaskAppear();
    } else {
      this.presenter.loadTask();
    }
  }

  /**
   *
   */
  @Override
  public void getCompletedTask() {
    presenter.getCompletedTask();
    updateListView();
  }

  /**
   *
   */
  @Override
  public void getUncompletedTask() {
    presenter.getUncompletedTask();
    updateListView();
  }

  /**
   *
   */
  @Override
  public void deleteCompletedTask() {
    presenter.deleteCompletedTask();
    updateListView();
  }

  /**
   *
   */
  @Override
  public void getAllTaskFromDatabase() {
    Log.d("Main activity", "Get all task from database");
    presenter.getAllTaskFromDatabase();
    updateListView();
  }

  /**
   *
   */
  @Override
  public void taskAppear() {
    Log.d("MainvityView", "taskAppear");
    setUpListView();
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    Log.d("Main fragment", "On Create view");
    View view = inflater.inflate(R.layout.activity_fragment, container, false);
    addButton = (FloatingActionButton) view.findViewById(R.id.fab);
    listItem = (ListView) view.findViewById(R.id.main_activity_list);
    textNoItem = (TextView) view.findViewById(R.id.text_view_in_main_screen);
    if (this.presenter.checkTimeFromDatabase()) {
      this.presenter.getAllTaskFromDatabase();
    } else {
      TaskRepositoryAction.HAVE_ADDED = false;
      this.presenter.getTaskFromFirebase();
    }
    addButton.setOnClickListener(this);
    textNoItem.setVisibility(View.GONE);
    if (listTask != null && listTask.size() == 0) {
      Log.d("Main fragment", "set no item visible");
      textNoItem.setVisibility(View.VISIBLE);
    } else if (listTask != null && listTask.size() != 0) {
      textNoItem.setVisibility(View.GONE);
    }
    return view;
  }


  @Override
  public void onClick(View v) {
    Log.d("My application", "Add button is set");
    presenter.pressButton();
  }

  /**
   *
   */
  public void setUpListView() {
    Log.d("Main fragment", "Set up list view");
    adapter = new MainActivityAdapter(listTask);
    listItem.setVisibility(View.VISIBLE);
    listItem.setAdapter(adapter);
    textNoItem.setVisibility(View.GONE);
    listItem.setOnItemClickListener(new OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        pressItem(position);
      }
    });
    adapter.notifyDataSetChanged();
    Log.d("anc", "" + listItem.getAdapter().getCount());
  }


  /**
   *
   * @param t
   */
  @Override
  public void enterEditTask(Task t) {
    Log.d("My application", "Enter edit task");
    Bundle bundle = new Bundle();
    bundle.putString(NAME, t.getname());
    bundle.putString(DESCRIPTION, t.getdescription());
    bundle.putString(ID, t.getid());
    if (t.getcompleted()) {
      bundle.putInt(STATUS, 1);
    } else {
      bundle.putInt(STATUS, 0);
    }
    Intent intent = new Intent(getActivity(), AddEditTaskActivity.class);
    intent.putExtras(bundle);
    getActivity().startActivity(intent);
  }

  @Override
  public void pressItem(int position) {
    //Intent intent = new Intent(getActivity(), AddTaskActivity.class);
    //getActivity().startActivity(intent);
  }

  /**
   *
   */
  @Override
  public void noTaskAppear() {
    Log.d("Main fragment", "No task appear");
    textNoItem.setVisibility(View.VISIBLE);
    listItem.setVisibility(View.GONE);
  }

  /**
   *
   * @param taskList
   */
  @Override
  public void populateListView(ArrayList<Task> taskList) {
    Log.d("Main fragment", "populate list view");
    listTask = taskList;

  }

  /**
   *
   */
  @Override
  public void addNewTask() {
    Intent intent = new Intent(getActivity(), AddEditTaskActivity.class);
    getActivity().startActivityForResult(intent, MainActivityInterface.REQUEST_CODE);
  }
  public static class MainActivityAdapter extends BaseAdapter {
    ArrayList<Task> taskList;

    public MainActivityAdapter(@NonNull ArrayList<Task> taskList) {
      Log.d("Main fragment", "Create adapter");
      this.taskList = taskList;
    }

    @Override
    public int getCount() {
      return taskList.size();
    }

    @Override
    public Object getItem(int position) {
      return taskList.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
      Log.d("Main fragment", "Create view list view "+ taskList.size());
      View v = convertView;
      if (v == null) {
        Log.d("Main fragment", "Convert view is null" + position);
        v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list_view,
            viewGroup, false);
      }
      final Task task = (Task) getItem(position);
      CheckBox checkBox = (CheckBox) v.findViewById(R.id.item_check_box);
      checkBox.setChecked(task.getcompleted());
      checkBox.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          if (task.getcompleted()) {
            checkBoxClick(false, task.getid());
          } else {
            checkBoxClick(true, task.getid());
          }
        }
      });
      TextView textViewItemInTask = (TextView) v.findViewById(R.id.item_name);
      textViewItemInTask.setText(task.getname());
      textViewItemInTask.setFocusable(false);
      v.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          presenter.enterEditTask(task);
        }
      });
      Log.d("NAME: ", task.getname());
      return v;
    }

    @Override
    public long getItemId(int position) {
      return position;
    }

    public void checkBoxClick(boolean bool, String taskID) {
      presenter.updateTask(bool, taskID);
    }
  }



}
