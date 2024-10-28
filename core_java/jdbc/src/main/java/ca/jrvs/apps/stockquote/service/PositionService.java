package ca.jrvs.apps.stockquote.service;

import ca.jrvs.apps.stockquote.dao.PositionDAO;
import ca.jrvs.apps.stockquote.model.Position;
import java.sql.SQLException;

public class PositionService {

  private PositionDAO positionDAO;

  public PositionService(PositionDAO positionDAO) {
    this.positionDAO = positionDAO;
  }

  /**
   * Processes a buy order and updates the database accordingly
   * @param ticker
   * @param numberOfShares
   * @param price
   * @return The position in our database after processing the buy
   */
  public Position buy(String ticker, int numberOfShares, double price) throws SQLException {

    Position position = new Position();
    position.setTicker(ticker);
    position.setNumOfShares(numberOfShares);
    position.setValuePaid(price * numberOfShares);

    return positionDAO.save(position);
  }

  /**
   * Sells all shares of the given ticker symbol
   * @param ticker
   */
  public void sell(String ticker) {
      positionDAO.deleteById(ticker);
  }
}
