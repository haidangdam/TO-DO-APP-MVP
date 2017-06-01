package com.example.haidangdam.myapplication.add_task;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import com.example.haidangdam.myapplication.R;
import com.example.haidangdam.myapplication.main_activity.MainActivity;

/**
 * Created by haidangdam on 5/31/17.
 */

public class AddTaskView extends Fragment implements AddTaskInterface.View {

  public static AddTaskView addTaskView;
  EditText nameEditText;
  EditText descriptionEditText;
  Button addButton;
  AddTaskInterface.Presenter addTaskPresenter;

  public AddTaskView() {}

  public static AddTaskView newInstance() {
    if (addTaskView == null) {
      addTaskView = new AddTaskView();
    }
    return addTaskView;
  }

  @Override
  public void setPresenter(AddTaskInterface.Presenter a) {
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
       addTaskPresenter.buttonPress(nameEditText.getText().toString(), descriptionEditText.getText().toString());
      }
    });
    return v;
  }

  @Override
  public void submitButton() {
    Intent intent = new Intent(getActivity(), MainActivity.class);
    getActivity().startActivity(intent);
  }

  @Override
  public void emptyField() {
    Snackbar.make(getActivity().findViewById(R.id.add_task_layout),
        "Not filling all field", Snackbar.LENGTH_LONG).show();
  }

}
