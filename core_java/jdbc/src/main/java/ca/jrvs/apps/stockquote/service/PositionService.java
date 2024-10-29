package ca.jrvs.apps.stockquote.service;

import ca.jrvs.apps.stockquote.dao.PositionDAO;
import ca.jrvs.apps.stockquote.model.Position;
import ca.jrvs.apps.stockquote.model.Quote;
import java.sql.SQLException;
import java.util.Optional;

public class PositionService {

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
  public Position buy(String ticker, int numberOfShares, double price) throws SQLException {

    Position position = new Position();
    Optional<Quote> quote = quoteService.fetchQuoteDataFromAPI(ticker);

    if (quote.isEmpty()) {
      System.out.println("The ticker provided is not valid");
      return null;
    }
    if (quote.get().getVolume() < numberOfShares || numberOfShares < 1) {
      System.out.println("Invalid number of shares");
      return null;
    }
    if (quote.get().getPrice() != price) {
      System.out.println("The price entered is incorrect");
      return null;
    }

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
