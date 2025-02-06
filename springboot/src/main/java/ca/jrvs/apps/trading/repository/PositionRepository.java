package ca.jrvs.apps.trading.repository;

import ca.jrvs.apps.trading.model.Position;
import java.util.Collection;
import java.util.Optional;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface PositionRepository extends ReadOnlyRepository<Position, Integer> {

  Optional<Position> findByTickerAndTraderId(String ticker, Integer traderId);

  List<Position> findAllByTraderId(Integer traderId);

  Position save(Position position);

  default Position replace(Position position) {
    delete(position);
    return save(position);
  }

  void delete(Position position);
}
