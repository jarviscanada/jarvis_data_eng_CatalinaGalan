package ca.jrvs.apps.trading.model;

import ca.jrvs.apps.trading.util.PositionId;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import org.hibernate.annotations.Subselect;

@Entity
@Subselect("SELECT account_id,\n"
    + "       ticker,\n"
    + "       sum(size) AS position\n"
    + "FROM public.security_order\n"
    + "WHERE status = 'FILLED'\n"
    + "GROUP BY account_id, ticker")
public class Position {

  @EmbeddedId
  private PositionId positionId;
  private Integer position;

  public PositionId getPositionId() {
    return positionId;
  }

  public Integer getPosition() {
    return position;
  }
}
