package ca.jrvs.apps.trading.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Account {
  @Id
  private int id;

  @Column(nullable = false)
  private double ammount;


}
