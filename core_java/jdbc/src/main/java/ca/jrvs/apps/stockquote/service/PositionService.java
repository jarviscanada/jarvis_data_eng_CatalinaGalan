package ca.jrvs.apps.stockquote.service;

import ca.jrvs.apps.stockquote.dao.PositionDAO;
import ca.jrvs.apps.stockquote.model.Position;
import ca.jrvs.apps.stockquote.model.Quote;
import java.sql.SQLException;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PositionService {

  final Logger logger = LoggerFactory.getLogger(PositionService.class);

  private PositionDAO positionDAO;
  private QuoteService quoteService;

  public PositionService(PositionDAO positionDAO, QuoteService quoteService) {
    this.positionDAO = positionDAO;
    this.quoteService = quoteService;
  }

  /**
   * Processes a buy order and updates the database accordingly
   * @param ticker
   * @param numberOfShares
   * @param price
   * @return The position in our database after processing the buy
   */
  public Position buy(String ticker, int numberOfShares, double price)
      throws SQLException {

    Position position = new Position();
    Optional<Quote> quote = quoteService.fetchQuoteDataFromAPI(ticker);

    if (quote.get().getVolume() < numberOfShares || numberOfShares < 1) {
      System.out.print("\n The number of shares entered is not permitted for this purchase. ");
      logger.error("Invalid input for numberOfShares: {}", IllegalArgumentException.class);
      throw new IllegalArgumentException();
    }
    position.setTicker(ticker.toUpperCase());
    position.setNumOfShares(numberOfShares);
    position.setValuePaid(price * numberOfShares);
    try {
      logger.info("Position {} bought successfully.", ticker);
      return positionDAO.save(position);
    } catch (IllegalArgumentException e) {
      logger.error("Position {} failed to save to database.", ticker);
      throw new IllegalArgumentException(e);
    }
  }

  /**
   * Sells all shares of the given ticker symbol
   * @param ticker
   */
  public void sell(String ticker) {
    ticker = ticker.toUpperCase();
    positionDAO.deleteById(ticker);
    quoteService.getQuoteDAO().deleteById(ticker);
    logger.info("Position {} sold successfully.", ticker);
  }

  public Iterable<Position> listAll() {
    Iterable<Position> allPositions = positionDAO.findAll();
    if (!allPositions.iterator().hasNext()) {
      return null;
    }
    return allPositions;
  }
}
