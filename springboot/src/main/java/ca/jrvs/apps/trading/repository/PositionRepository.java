package ca.jrvs.apps.trading.repository;

import ca.jrvs.apps.trading.model.Position;
import ca.jrvs.apps.trading.util.PositionId;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public interface PositionRepository extends ReadOnlyRepository<Position, Integer> {

  Optional<Position> findByPositionId(PositionId positionId);

  boolean existsByPositionId(PositionId positionId);

}
