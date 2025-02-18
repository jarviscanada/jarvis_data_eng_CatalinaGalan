package ca.jrvs.apps.trading.model;

import ca.jrvs.apps.trading.util.PositionId;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import org.hibernate.annotations.Subselect;

@Entity
//@Immutable
@Subselect("SELECT account_id,\n"
    + "       ticker,\n"
    + "       sum(size) AS position\n"
    + "FROM public.security_order\n"
    + "WHERE status = 'FILLED'\n"
    + "GROUP BY id, account_id, ticker")
public class Position {


  @EmbeddedId
  private PositionId positionId;
//  private Integer accountId;
//  private String ticker;
  private Integer position;
//
//  public Position(PositionId positionId) {
//    this.positionId = positionId;
//  }

  public PositionId getPositionId() {
    return positionId;
  }

//  public Integer getAccountId() {
//    return accountId;
//  }
//
//  public String getTicker() {
//    return ticker;
//  }

  public Integer getPosition() {
    return position;
  }
}
