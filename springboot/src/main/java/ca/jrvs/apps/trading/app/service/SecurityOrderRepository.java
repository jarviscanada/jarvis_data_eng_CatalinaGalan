package ca.jrvs.apps.trading.app.service;

import ca.jrvs.apps.trading.app.model.SecurityOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SecurityOrderRepository extends JpaRepository<SecurityOrder, Integer> {

}
