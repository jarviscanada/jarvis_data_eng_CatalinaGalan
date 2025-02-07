package ca.jrvs.apps.trading.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.util.List;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

@Entity
@Immutable
@Subselect("CREATE OR REPLACE VIEW public.position\n"
    + "AS\n"
    + "SELECT account_id,\n"
    + "       ticker,\n"
    + "       sum(size) AS position\n"
    + "FROM public.security_order\n"
    + "WHERE status = 'FILLED'\n"
    + "GROUP BY account_id, ticker;")
public class Position {

//  @Id
//  @GeneratedValue(strategy = GenerationType.SEQUENCE)
//  private Integer id;
//
//  @OneToOne
//  @JoinColumn(name = "account_id")
//  private SecurityOrder securityOrder;
//
//  public Integer getPosition() {
//    return position;
//  }
//
//  public void setPosition(Integer position) {
//    this.position = position;
//  }
//
//  @Column
//  private Integer position;
//
//  public String getTicker() {
//    return ticker;
//  }
//
//  public void setTicker(String ticker) {
//    this.ticker = ticker;
//  }
//
//  @Column
//  private String ticker;
//
//  public List<SecurityOrder> getAccountOrders() {
//    return accountOrders;
//  }
//
//  public void setSecurityOrder(SecurityOrder securityOrder) {
//    this.accountOrders.add(securityOrder);
//  }

}
