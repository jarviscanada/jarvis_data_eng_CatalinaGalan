package ca.jrvs.apps.stockquote.service;

import ca.jrvs.apps.stockquote.dao.PositionDAO;
import ca.jrvs.apps.stockquote.model.Position;
import ca.jrvs.apps.stockquote.model.Quote;
import ca.jrvs.apps.stockquote.util.DatabaseConnectionManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;

public class PositionService {

  private final DatabaseConnectionManager dcm = new DatabaseConnectionManager("localhost",
      "stock_quote", "postgres", "password");
  private final Connection connection;

  {
    try {
      connection = dcm.getConnection();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  private final PositionDAO positionDAO = new PositionDAO(connection);

  /**
   * Processes a buy order and updates the database accordingly
   * @param ticker
   * @param numberOfShares
   * @param price
   * @return The position in our database after processing the buy
   */
  public Position buy(String ticker, int numberOfShares, double price) throws SQLException {

    QuoteService quoteService = new QuoteService();
    Optional<Quote> quoteOptional = quoteService.fetchQuoteDataFromAPI(ticker);
    Quote quote = quoteOptional.get();

    if (numberOfShares <= quote.getVolume() && Objects.equals(ticker, quote.getTicker())) {
      Position position = positionDAO.create(numberOfShares, quote);
      System.out.println("Position " + ticker + " has been successfully bought");
      return positionDAO.save(position);
    } else if (numberOfShares < 1) {
      System.out.println("Please enter a valid number of shares to buy");
    } else if (quote.getPrice() == 0.0) {
      System.out.println("The ticker provided is not valid");
    }
    return null;
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
