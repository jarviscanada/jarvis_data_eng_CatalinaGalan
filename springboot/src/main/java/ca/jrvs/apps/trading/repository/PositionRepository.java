package ca.jrvs.apps.trading.repository;

import ca.jrvs.apps.trading.model.Account;
import ca.jrvs.apps.trading.model.Position;
import ca.jrvs.apps.trading.model.SecurityOrder;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.stereotype.Repository;

@Repository
public interface PositionRepository extends ReadOnlyRepository<Position, Integer> {

  Optional<Position> findByAccountIdAndTicker(Integer accountId, String ticker);

  boolean existsByAccountIdAndTicker(Integer accountId, String ticker);

  default List<Position> findAllByAccount(Account account) {
    Set<SecurityOrder> orders = account.getOrders();
    List<Position> positions = new ArrayList<>();
    for (SecurityOrder order : orders) {
      Optional<Position> position = findByAccountIdAndTicker(account.getId(), order.getQuote().getTicker());
      positions.add(position.get());
    }
    return positions;
  }
}
