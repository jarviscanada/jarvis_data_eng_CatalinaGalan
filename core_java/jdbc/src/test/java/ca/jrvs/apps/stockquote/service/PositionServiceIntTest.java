package ca.jrvs.apps.stockquote.service;

import ca.jrvs.apps.stockquote.model.Position;
import ca.jrvs.apps.stockquote.model.Quote;
import java.sql.SQLException;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PositionServiceIntTest {

//  private static Connection connection;
//
//  private PositionDAO positionDAO;
  private static QuoteService quoteService;
  private static Quote quote;
  private String validTicker;
  private int validNumOfShares;
  private double price;
  private static Position position;
  private static PositionService positionService;

  @BeforeEach
  void init() {
    Optional<Quote> quoteOptional = quoteService.fetchQuoteDataFromAPI(validTicker);
    quote = quoteOptional.get();
    positionService = new PositionService();
    quoteService = new QuoteService();
  }

  @Test
  void buyValidInput() throws SQLException {
    validTicker = "MSFT";
    validNumOfShares = 100;
    price = 0.0;
    position = new Position();
    position = positionService.buy(validTicker, validNumOfShares, price);

  }

  @Test
  void sell() {
    validTicker = "MSFT";
    positionService.sell(validTicker);
  }
}