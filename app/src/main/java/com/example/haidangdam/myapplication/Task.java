package com.example.haidangdam.myapplication;

import java.util.Random;

/**
 * Created by haidangdam on 5/30/17
 */

public class Task {
  String name;
  String description;
  boolean completed;
  String id;

  public Task() {}

  public Task(String name, String description, boolean completed) {
    this.name = name;
    this.description = description;
    this.completed = completed;
    id = "" + description.hashCode() + "" + name.hashCode() + "" + (new Random()).nextInt(100000);
  }

  public String getname() {
    return name;
  }

  public String getdescription() {
    return description;
  }

  public boolean getcompleted() {
    return completed;
  }

  public void setcompleted(boolean status) {
    completed = status;
  }

  public void setname(String name) {
    this.name = name;
  }

  public void setdescription(String description) {
    this.description = description;
  }

  public String getid() {
    return id;
  }

  public void setid(String s) {
    id = s;
  }

  public void updateID() {
    id = "" + description.hashCode() + "" + name.hashCode() + "" + (new Random()).nextInt(100000);
  }

}
