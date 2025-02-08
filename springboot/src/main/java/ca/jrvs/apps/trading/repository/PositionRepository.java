package ca.jrvs.apps.trading.repository;

import ca.jrvs.apps.trading.model.Position;
import ca.jrvs.apps.trading.model.SecurityOrder;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;

@Repository
public interface PositionRepository extends JpaRepository<Position, Integer> {

//  @Query("CREATE OR REPLACE VIEW public.position\n"
//      + "AS\n"
//      + "SELECT account_id,\n"
//      + "       ticker,\n"
//      + "       sum(size) AS position\n"
//      + "FROM public.security_order\n"
//      + "WHERE status = 'FILLED'\n"
//      + "GROUP BY account_id, ticker")
  void save(SecurityOrder securityOrder);

  Optional<Position> findByAccountIdAndTicker(Integer accountId, String ticker);

  boolean existsByAccountIdAndTicker(Integer accountId, String ticker);

  List<Position> findAll();

  List<Position> findAllByAccountId(Integer accountId);

  void deleteById();

  void deleteAll();
}
