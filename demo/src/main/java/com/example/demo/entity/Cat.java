package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Cat {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private int id;

  String name;

  public String getAge() {
    return age;
  }

  public void setAge(String age) {
    this.age = age;
  }

  String age;

  public int getId() {
    return id;
  }

  public void setId(int id) {
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return "Cat{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", age='" + age + '\'' +
        '}';
  }
}
