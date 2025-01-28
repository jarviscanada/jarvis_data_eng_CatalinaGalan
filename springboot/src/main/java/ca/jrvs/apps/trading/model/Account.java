package ca.jrvs.apps.trading.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(schema = "public")
public class Account {
  @Id
  @Column
  private Long id;

  @OneToOne
  @MapsId
  private Trader trader;

  @Column(nullable = false)
  private double amount;


  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public double getAmount() {
    return amount;
  }

  public void setAmount(double amount) {
    this.amount = amount;
  }

  public Trader getTrader() {
    return trader;
  }

  public void setTrader(Trader trader) {
    this.trader = trader;
  }
}
