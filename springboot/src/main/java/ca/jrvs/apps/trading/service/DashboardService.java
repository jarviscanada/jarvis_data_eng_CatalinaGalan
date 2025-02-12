package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.model.Account;
import ca.jrvs.apps.trading.model.Position;
import ca.jrvs.apps.trading.model.Trader;
import ca.jrvs.apps.trading.model.view.PortfolioView;
import ca.jrvs.apps.trading.model.view.TraderAccountView;
import ca.jrvs.apps.trading.repository.PortfolioViewRepository;
import ca.jrvs.apps.trading.repository.QuoteRepository;
import ca.jrvs.apps.trading.repository.TraderAccountViewRepository;
import ca.jrvs.apps.trading.repository.TraderRepository;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {

  @Autowired
  private TraderAccountService traderAccountService;

  @Autowired
  private TraderRepository traderRepository;

  @Autowired
  private PositionService positionService;

  @Autowired
  private PortfolioViewRepository portfolioViewRepository;

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

    Set<Position> positions = positionService.getAllPositionsByAccountId(traderId);
    return new PortfolioView(traderId, positions);
  }
}
