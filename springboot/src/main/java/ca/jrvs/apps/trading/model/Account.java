package ca.jrvs.apps.trading.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(schema = "public")
public class Account {

  @Id
  private Integer id;

  @Column(nullable = false)
  private Double amount;

  @OneToOne
  @JoinColumn(name = "id")
  @MapsId
  private Trader trader;

  @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
  private List<SecurityOrder> orders = new ArrayList<>();

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getId() { return id; }

  public Double getAmount() {
    return amount;
  }

  public void setAmount(Double amount) {
    this.amount = amount;
  }

  public void setTrader(Trader trader) {
    this.trader = trader;
  }

  public List<SecurityOrder> getOrders() {
    return orders;
  }

  public void setOrder(SecurityOrder order) {
    orders.add(order);
  }
}
