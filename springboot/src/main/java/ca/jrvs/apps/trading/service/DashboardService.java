package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.model.Position;
import ca.jrvs.apps.trading.model.view.PortfolioView;
import ca.jrvs.apps.trading.model.view.TraderAccountView;
import ca.jrvs.apps.trading.repository.TraderAccountViewRepository;
import ca.jrvs.apps.trading.repository.TraderRepository;
import java.util.Optional;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {

  private static final Logger logger = LoggerFactory.getLogger(DashboardService.class);

  @Autowired
  private PositionService positionService;

  @Autowired
  private TraderAccountViewRepository traderAccountViewRepository;

  @Autowired
  private TraderRepository traderRepository;


  /**
   * Method to create a View for a Trader and its Account.
   *
   * @param traderId to be found.
   * @return TraderAccount view.
   */
  public TraderAccountView getTraderAccountViewByTraderId(Integer traderId) {

    Optional<TraderAccountView> traderAccountView = traderAccountViewRepository.findById(traderId);

    if (traderAccountView.isEmpty()) {
      logger.debug("Invalid TraderId.");
      throw new IllegalArgumentException("Invalid Input: Trader not found."
          + " Please provide a valid TraderId.");
    }

    return traderAccountView.get();
  }


  /**
   * Method to create Portfolio of a Trader with all its Positions.
   *
   * @param traderId for Positions.
   * @return PortfolioView.
   */
  public PortfolioView getProfileViewByTraderId(Integer traderId){

    if (!traderRepository.existsById(traderId)) {
      logger.debug("Invalid TraderId.");
      throw new IllegalArgumentException("Invalid Input: Trader not found."
          + " Please provide a valid TraderId.");
    }

    Set<Position> positions = positionService.getAllPositionsByAccountId(traderId);
    return new PortfolioView(traderId, positions);
  }
}
