package com.example.haidangdam.myapplication.add_task;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import com.example.haidangdam.myapplication.R;
import com.example.haidangdam.myapplication.main_activity.MainActivityView;

/**
 * Created by haidangdam on 5/31/17.
 */

public class AddEditTaskView extends Fragment implements AddEditTaskInterface.View {

  public static AddEditTaskView addTaskView;
  EditText nameEditText;
  EditText descriptionEditText;
  Button addButton;
  AddEditTaskInterface.Presenter addTaskPresenter;
  Button deleteButton;
  public static Bundle bundle;

  public AddEditTaskView() {}

  public static AddEditTaskView newInstance() {
    if (addTaskView == null) {
      addTaskView = new AddEditTaskView();
    }
    return addTaskView;
  }

  public static AddEditTaskView newInstance(Bundle bundle1) {
    bundle = bundle1;
    if (addTaskView == null) {
      addTaskView = new AddEditTaskView();
    }
    return addTaskView;
  }

  @Override
  public void setPresenter(AddEditTaskInterface.Presenter a) {
    addTaskPresenter = a;
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.add_task_fragment, container, false);
    nameEditText = (EditText) v.findViewById(R.id.task_name_edit_text_add_or_edit);
    descriptionEditText = (EditText) v.findViewById(R.id.task_description_edit_text_add_or_edit);
    addButton = (Button) v.findViewById(R.id.add_button_add_or_edit);
    addButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
       addTaskPresenter.buttonPress(nameEditText.getText().toString(), descriptionEditText.getText().toString(), bundle);
      }
    });
    deleteButton = (Button) v.findViewById(R.id.delete_button_add_or_edit);
    if (bundle != null) {
      nameEditText.setText(bundle.getString(MainActivityView.NAME));
      descriptionEditText.setText(bundle.getString(MainActivityView.DESCRIPTION));
    } else {
      deleteButton.setVisibility(View.GONE);
    }
    deleteButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (bundle != null) {
          addTaskPresenter.deleteButtonPress(bundle);
        }
      }
    });
    return v;
  }

  @Override
  public void submitButton() {
    getActivity().setResult(Activity.RESULT_OK);
    getActivity().finish();
    Log.d("My application", "Switch to main");
  }

  @Override
  public void emptyField() {
    Snackbar.make(getActivity().findViewById(R.id.add_task_layout),
        "Not filling all field", Snackbar.LENGTH_LONG).show();
  }

}
