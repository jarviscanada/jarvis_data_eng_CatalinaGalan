package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.model.view.PortfolioView;
import ca.jrvs.apps.trading.repository.PortfolioViewRepository;
import ca.jrvs.apps.trading.repository.PositionRepository;
import ca.jrvs.apps.trading.repository.QuoteRepository;
import ca.jrvs.apps.trading.repository.TraderRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {

  @Autowired
  private TraderRepository traderRepository;

  @Autowired
  private QuoteRepository quoteRepository;

  @Autowired
  private PositionRepository positionRepository;

  @Autowired
  private PortfolioViewRepository portfolioViewRepository;

  public PortfolioView getProfileViewByTraderId(Integer traderId){

    // getAccount(traderId)
    // account.getOrders
    Optional<PortfolioView> portfolioView = portfolioViewRepository.findById(traderId);
    return portfolioView.get();
  }
}
