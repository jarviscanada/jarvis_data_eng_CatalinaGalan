package ca.jrvs.apps.trading.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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

  @OneToMany(mappedBy = "account",fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  public Set<SecurityOrder> orders = new HashSet<>();

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
    trader.setAccount(this);
  }

  public Set<SecurityOrder> getOrders() {
    return orders;
  }

  public void setOrder(SecurityOrder order) {
    orders.add(order);
  }
}
