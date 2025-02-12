package ca.jrvs.apps.trading.model.view;

import ca.jrvs.apps.trading.model.Position;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.util.Set;
import org.hibernate.annotations.Immutable;

@Entity
@Immutable
public class PortfolioView {

  public PortfolioView(Integer traderId, Set<Position> positions) {
    this.traderId = traderId;
    this.positions = positions;
  }

  @Id
  private Integer traderId;

  @ElementCollection
  private Set<Position> positions;

  public Integer getTraderId() {
    return traderId;
  }

  public Set<Position> getPositions() {
    return positions;
  }
}
