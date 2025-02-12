package ca.jrvs.apps.trading.repository;

import ca.jrvs.apps.trading.model.Account;
import ca.jrvs.apps.trading.model.SecurityOrder;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SecurityOrderRepository extends JpaRepository<SecurityOrder, Integer> {

  Set<SecurityOrder> findAllByAccount(Account account);

}
