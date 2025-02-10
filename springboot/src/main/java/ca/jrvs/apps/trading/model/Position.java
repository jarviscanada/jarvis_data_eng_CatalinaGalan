package ca.jrvs.apps.trading.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.transaction.Transactional;
import java.util.List;
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
