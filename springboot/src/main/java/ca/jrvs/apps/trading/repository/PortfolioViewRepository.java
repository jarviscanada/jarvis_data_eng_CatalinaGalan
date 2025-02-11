package ca.jrvs.apps.trading.repository;

import ca.jrvs.apps.trading.model.Position;
import ca.jrvs.apps.trading.model.view.PortfolioView;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PortfolioViewRepository extends ReadOnlyRepository<PortfolioView, Integer> {

  Optional<PortfolioView> findById(Integer accountId);

}
