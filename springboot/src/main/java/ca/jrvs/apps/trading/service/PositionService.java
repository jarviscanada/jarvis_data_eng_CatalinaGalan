package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.model.Position;
import ca.jrvs.apps.trading.repository.PositionRepository;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PositionService {

  @Autowired
  private PositionRepository positionRepository;

  public Set<Position> getAllPositionsByAccountId(Integer accountId) {

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
