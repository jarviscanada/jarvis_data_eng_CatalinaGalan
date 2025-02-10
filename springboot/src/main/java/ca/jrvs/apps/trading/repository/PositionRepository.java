package ca.jrvs.apps.trading.repository;

import ca.jrvs.apps.trading.model.Position;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public interface PositionRepository extends ReadOnlyRepository<Position, Integer> {

  Optional<Position> findByAccountIdAndTicker(Integer accountId, String ticker);

  boolean existsByAccountIdAndTicker(Integer accountId, String ticker);

}
