package ca.jrvs.apps.trading.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;

@Entity
public class Account {
  @Id
  private Long id;

  @OneToOne
  @MapsId
  private Trader trader;

  @Column(nullable = false)
  private double ammount;


  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public double getAmmount() {
    return ammount;
  }

  public void setAmmount(double ammount) {
    this.ammount = ammount;
  }

  public Trader getTrader() {
    return trader;
  }

  public void setTrader(Trader trader) {
    this.trader = trader;
  }
}
