package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.model.Account;
import ca.jrvs.apps.trading.model.Position;
import ca.jrvs.apps.trading.model.SecurityOrder;
import ca.jrvs.apps.trading.repository.PositionRepository;
import ca.jrvs.apps.trading.repository.SecurityOrderRepository;
import ca.jrvs.apps.trading.repository.TraderRepository;
import ca.jrvs.apps.trading.util.PositionId;
import java.util.HashSet;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PositionService {

  private static final Logger logger = LoggerFactory.getLogger(PositionService.class);

  @Autowired
  private PositionRepository positionRepository;

  @Autowired
  private SecurityOrderRepository securityOrderRepository;

  @Autowired
  private TraderRepository traderRepository;


  /**
   * Method to returns all Positions associated with an Account.
   *
   * @param accountId of the account in question.
   * @return Set of Positions for this account.
   */
  public Set<Position> getAllPositionsByAccountId(Integer accountId) {

    if (!traderRepository.existsById(accountId)) {
      logger.debug("Invalid Input.");
      throw new IllegalArgumentException("Invalid Input: Trader not found. "
          + "Please provide a valid TraderId.");
    }

    Account account = traderRepository.findAccountById(accountId).get();
    Set<SecurityOrder> orders = account.getOrders();
    Set<Position> accountPositions = new HashSet<>();

    for (SecurityOrder order : orders) {
      System.out.println(order.getQuote().getTicker() + order.getSize());
      String ticker = order.getQuote().getTicker();
      Position position = positionRepository.findByPositionId(new PositionId(accountId, ticker)).get();
      System.out.println(position.getPositionId());
      accountPositions.add(position);
    }

    return accountPositions;
  }
}
