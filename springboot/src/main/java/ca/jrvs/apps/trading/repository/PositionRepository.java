package ca.jrvs.apps.trading.repository;

import ca.jrvs.apps.trading.model.Position;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface PositionRepository extends ReadOnlyRepository<Position, Integer> {

}
