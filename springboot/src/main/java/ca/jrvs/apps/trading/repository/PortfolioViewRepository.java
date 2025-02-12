package ca.jrvs.apps.trading.repository;

import ca.jrvs.apps.trading.model.view.PortfolioView;
import org.springframework.stereotype.Repository;

@Repository
public interface PortfolioViewRepository extends ReadOnlyRepository<PortfolioView, Integer> {

}
