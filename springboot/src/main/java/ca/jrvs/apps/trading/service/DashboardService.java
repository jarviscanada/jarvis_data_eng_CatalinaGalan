package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.model.Position;
import ca.jrvs.apps.trading.model.view.PortfolioView;
import ca.jrvs.apps.trading.model.view.TraderAccountView;
import ca.jrvs.apps.trading.repository.TraderAccountViewRepository;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {

  @Autowired
  private PositionService positionService;

  @Autowired
  private TraderAccountViewRepository traderAccountViewRepository;

  public TraderAccountView getTraderAccountViewByTraderId(Integer traderId) {

    Optional<TraderAccountView> traderAccountView = traderAccountViewRepository.findById(traderId);

    if (traderAccountView.isEmpty()) {
      throw new IllegalArgumentException("Invalid Input: Trader not found."
          + " Please provide a valid TraderId.");
    }

    return traderAccountView.get();
  }

  public PortfolioView getProfileViewByTraderId(Integer traderId){

    List<Position> positions = positionService.getAllPositionsByAccountId(traderId);
    return new PortfolioView(traderId, positions);

  }
}
