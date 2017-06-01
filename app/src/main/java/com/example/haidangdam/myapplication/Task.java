package com.example.haidangdam.myapplication;

import java.util.Random;

/**
 * Created by haidangdam on 5/30/17.
 */

public class Task {
  String name;
  String description;
  boolean completed;
  String id;
  Random r;

  public Task(String name, String description, boolean completed) {
    this.name = name;
    this.description = description;
    this.completed = completed;
    r = new Random();
    id = "" + description.hashCode() + "" + name.hashCode() + "" + r.nextInt(100000);
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public boolean getStatusCompleted() {
    return completed;
  }

  public void setCompletedStatus(boolean status) {
    completed = status;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getId() {
    return id;
  }

  public void setId(String s) {
    id = s;
  }

  public void updateID() {
    id = "" + description.hashCode() + "" + name.hashCode() + "" + r.nextInt(100000);
  }

}
