package ca.jrvs.apps.trading.model.view;

import ca.jrvs.apps.trading.model.Position;
import ca.jrvs.apps.trading.model.SecurityOrder;
import ca.jrvs.apps.trading.model.Trader;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.util.List;
import java.util.Set;
import org.hibernate.annotations.Subselect;

@Entity
//@Subselect("SELECT p.ticker, p.position, a.id AS account_id "
//    + "FROM position p JOIN public.account a "
//    + "ON p.account_id = a.id")
@Subselect("SELECT id AS account_id, "
    + "(SELECT * FROM position p WHERE p.account_id = a.id) AS positions FROM account a")
public class PortfolioView {

  @Id
  private Integer accountId;

  @ElementCollection
  private List<Position> positions;

  public Integer getAccountId() {
    return accountId;
  }

  public List<Position> getPositions() {
    return positions;
  }

}
