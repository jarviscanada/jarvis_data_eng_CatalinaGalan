package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.model.Account;
import ca.jrvs.apps.trading.model.Position;
import ca.jrvs.apps.trading.model.SecurityOrder;
import ca.jrvs.apps.trading.repository.PositionRepository;
import ca.jrvs.apps.trading.repository.SecurityOrderRepository;
import ca.jrvs.apps.trading.repository.TraderRepository;
import ca.jrvs.apps.trading.util.PositionId;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PositionService {

  @Autowired
  private PositionRepository positionRepository;

  @Autowired
  private SecurityOrderRepository securityOrderRepository;

  @Autowired
  private TraderRepository traderRepository;

  public List<Position> getAllPositionsByAccountId(Integer accountId) {

    if (!traderRepository.existsById(accountId)) {
      throw new IllegalArgumentException("Invalid Input: Trader not found. "
          + "Please provide a valid TraderId.");
    }

    Account account = traderRepository.findAccountById(accountId).get();
    Set<SecurityOrder> orders = account.getOrders();
    List<Position> accountPositions = new ArrayList<>();

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
