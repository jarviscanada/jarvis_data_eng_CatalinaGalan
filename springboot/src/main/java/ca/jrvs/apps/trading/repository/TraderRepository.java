package ca.jrvs.apps.trading.repository;

import ca.jrvs.apps.trading.model.Account;
import ca.jrvs.apps.trading.model.Trader;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TraderRepository extends JpaRepository<Trader, Long> {

  default Optional<Account> findAccountById(Long id) {
    if (findById(id).isPresent()) {
      return Optional.of(findById(id).get().getAccount());
    }
    return Optional.empty();
  };
}
