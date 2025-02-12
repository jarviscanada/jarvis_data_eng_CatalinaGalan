package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.model.Position;
import ca.jrvs.apps.trading.repository.PositionRepository;
import ca.jrvs.apps.trading.repository.TraderRepository;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PositionService {

  @Autowired
  private PositionRepository positionRepository;

  @Autowired
  private TraderRepository traderRepository;

  public Set<Position> getAllPositionsByAccountId(Integer accountId) {

    if (!traderRepository.existsById(accountId)) {
      throw new IllegalArgumentException("Invalid Input: Trader not found. "
          + "Please provide a valid TraderId.");
    }

    List<Position> positions = positionRepository.findAll();
    Set<Position> accountPositions = new HashSet<>();

    for (Position position : positions) {
      if (position.getAccountId() == accountId) {
        accountPositions.add(position);
      }
    }

    return accountPositions;

  }
}
