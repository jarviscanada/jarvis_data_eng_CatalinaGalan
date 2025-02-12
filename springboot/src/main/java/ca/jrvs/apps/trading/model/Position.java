package ca.jrvs.apps.trading.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

@Entity
@Immutable
@Subselect("SELECT account_id,\n"
    + "       ticker,\n"
    + "       sum(size) AS position\n"
    + "FROM public.security_order\n"
    + "WHERE status = 'FILLED'\n"
    + "GROUP BY account_id, ticker")
public class Position {

  @Id
  private Integer accountId;
  private String ticker;
  private Integer position;

  public Integer getAccountId() {
    return accountId;
  }

  public String getTicker() {
    return ticker;
  }

  public Integer getPosition() {
    return position;
  }
}
