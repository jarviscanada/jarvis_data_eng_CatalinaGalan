package ca.jrvs.apps.trading.repository;


import ca.jrvs.apps.trading.model.Account;
import ca.jrvs.apps.trading.model.TraderAccountView;
import org.springframework.stereotype.Repository;

@Repository
public interface TraderAccountViewRepository extends ReadOnlyRepository<TraderAccountView, Long> {

  Account findAccountByTraderId(Long traderId);

}
