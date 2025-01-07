package ca.jrvs.apps.trading.app.service;

import ca.jrvs.apps.trading.app.model.Trader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TraderRepository extends JpaRepository<Trader, Integer> {

}
