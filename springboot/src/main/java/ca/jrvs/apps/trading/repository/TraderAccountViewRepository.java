package ca.jrvs.apps.trading.repository;

import ca.jrvs.apps.trading.model.view.TraderAccountView;
import org.springframework.stereotype.Repository;

@Repository
public interface TraderAccountViewRepository extends ReadOnlyRepository<TraderAccountView, Integer> {

}
