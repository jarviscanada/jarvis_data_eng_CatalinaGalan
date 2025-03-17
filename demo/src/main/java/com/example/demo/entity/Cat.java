package com.example.demo.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;

@Entity
public class Cat {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private int id;

  @Column(nullable = false)
  private String name;
  @Column(nullable = false)
  private String age;

  @OneToOne(mappedBy = "cat", cascade = CascadeType.ALL)
  @PrimaryKeyJoinColumn
  private Owner owner;

  public Owner getOwner() {
    return owner;
  }

  public void setOwner(Owner owner) {
    this.owner = owner;
  }

  public String getAge() {
    return age;
  }

  public void setAge(String age) {
    this.age = age;
  }

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
