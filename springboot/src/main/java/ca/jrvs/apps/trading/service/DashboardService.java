package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.model.Position;
import ca.jrvs.apps.trading.model.view.PortfolioView;
import ca.jrvs.apps.trading.repository.PortfolioViewRepository;
import ca.jrvs.apps.trading.repository.QuoteRepository;
import ca.jrvs.apps.trading.repository.TraderRepository;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {

  @Autowired
  private TraderRepository traderRepository;

  @Autowired
  private QuoteRepository quoteRepository;

  @Autowired
  private PositionService positionService;

  @Autowired
  private PortfolioViewRepository portfolioViewRepository;

  public PortfolioView getProfileViewByTraderId(Integer traderId){

    Set<Position> positions = positionService.getAllPositionsByAccountId(traderId);

    return new PortfolioView(traderId, positions);
  }
}
