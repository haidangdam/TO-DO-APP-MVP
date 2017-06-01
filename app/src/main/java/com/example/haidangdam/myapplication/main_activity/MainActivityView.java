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
import com.example.haidangdam.myapplication.add_task.AddTaskActivity;
import java.util.ArrayList;
/**
 * Created by haidangdam on 5/30/1.
 */

public class MainActivityView extends Fragment implements MainActivityInterface.View,
    OnClickListener
{

  FloatingActionButton addButton;
  ListView listItem;
  TextView textNoItem;
  MainActivityInterface.Presenter presenter;

  public static MainActivityView mainActivityView;
  ArrayList<Task> listTask;
  /**
   *
   */
  public MainActivityView() {}

  public static MainActivityView newInstance() {
    if (mainActivityView == null) {
      mainActivityView = new MainActivityView();
    }
    return mainActivityView;
  }

  /**
   *
   * @param presenter
   */
  @Override
  public void setPresenter(MainActivityInterface.Presenter presenter) {
    this.presenter = presenter;
    this.presenter.getTaskFromDatabase();
    Log.d("My Application", "Presenter is set");
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.activity_fragment, container, false);
    addButton = (FloatingActionButton) view.findViewById(R.id.fab);
    listItem = (ListView) view.findViewById(R.id.main_activity_list);
    textNoItem = (TextView) view.findViewById(R.id.text_view_in_main_screen);
    addButton.setOnClickListener(this);
    if (listTask != null && listTask.size() == 0) {
      textNoItem.setVisibility(View.VISIBLE);
    } else if (listTask != null && listTask.size() != 0) {
      textNoItem.setVisibility(View.GONE);
      setUpListView();
    }
    return view;
  }

  @Override
  public void onClick(View v) {
    Log.d("My application", "Add button is set");
    presenter.pressButton();
  }

  private void setUpListView() {
    BaseAdapter adapter = new MainActivityAdapter(listTask);
    listItem.setAdapter(adapter);
    listItem.setOnItemClickListener(new OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        pressItem(position);
      }
    });
  }

  @Override
  public void pressItem(int position) {
    //Intent intent = new Intent(getActivity(), AddTaskActivity.class);
    //getActivity().startActivity(intent);
  }

  @Override
  public void noTaskAppear() {textNoItem.setVisibility(View.VISIBLE);}

  @Override
  public void populateListView(ArrayList<Task> taskList) {
    listTask = taskList;
  }

  @Override
  public void addNewTask() {
    Intent intent = new Intent(getActivity(), AddTaskActivity.class);
    getActivity().startActivity(intent);
  }
  public static class MainActivityAdapter extends BaseAdapter {
    ArrayList<Task> taskList;

    public MainActivityAdapter(@NonNull ArrayList<Task> taskList) {
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
      if (convertView == null) {
        convertView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list_view,
            viewGroup, false);
      }
      Task task = (Task) getItem(position);
      CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.item_check_box);
      checkBox.setChecked(task.getStatusCompleted());
      TextView textViewItemInTask = (TextView) convertView.findViewById(R.id.item_name);
      textViewItemInTask.setText(task.getDescription());
      return convertView;
    }

    @Override
    public long getItemId(int position) {
      return position;
    }

  }

}
