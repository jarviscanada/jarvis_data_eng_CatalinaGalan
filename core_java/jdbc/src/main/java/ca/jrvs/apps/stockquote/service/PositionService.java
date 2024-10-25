package ca.jrvs.apps.stockquote.service;

import ca.jrvs.apps.stockquote.dao.PositionDAO;
import ca.jrvs.apps.stockquote.model.Position;
import ca.jrvs.apps.stockquote.model.Quote;
import java.util.Optional;

public class PositionService {

  private PositionDAO positionDAO;

  /**
   * Processes a buy order and updates the database accordingly
   * @param ticker
   * @param numberOfShares
   * @param price
   * @return The position in our database after processing the buy
   */
  public Position buy(String ticker, int numberOfShares, double price) {
    QuoteService quoteService = new QuoteService();
    Optional<Quote> quoteOptional = quoteService.fetchQuoteDataFromAPI(ticker);
    Quote quote = quoteOptional.get();
    Position position = positionDAO.create(numberOfShares, quote);
    System.out.println("Position " + ticker + " has been successfully bought");
    return positionDAO.save(position);
  }

  /**
   * Sells all shares of the given ticker symbol
   * @param ticker
   */
  public void sell(String ticker) {
    positionDAO.deleteById(ticker);
    System.out.println("All shares of position " + ticker + " has been successfully sold");
  }
}
