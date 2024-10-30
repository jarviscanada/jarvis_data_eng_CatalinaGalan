package ca.jrvs.apps.stockquote.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import ca.jrvs.apps.stockquote.dao.PositionDAO;
import ca.jrvs.apps.stockquote.dao.QuoteDAO;
import ca.jrvs.apps.stockquote.dao.QuoteHttpHelper;
import ca.jrvs.apps.stockquote.model.Position;
import ca.jrvs.apps.stockquote.model.Quote;
import ca.jrvs.apps.stockquote.util.DatabaseConnectionManager;
import io.github.cdimascio.dotenv.Dotenv;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;
import okhttp3.OkHttpClient;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class PositionServiceIntTest {

  private static PositionDAO positionDAO;
  private static QuoteDAO quoteDAO;
  private static Connection connection;
  private static QuoteHttpHelper quoteHttpHelper;
  private static OkHttpClient client;
  private static DatabaseConnectionManager dcm;
  private static String apiKey;
  private static QuoteService quoteService;
  private static PositionService positionService;
  private static Optional<Quote> quote;
  private Position result;
  private String validTicker;
  private String invalidTicker;
  private int validNumOfShares;
  private int invalidNumOfShares;
  private double validPrice;
  private double invalidPrice;

  @BeforeAll
  static void init() {
    dcm = new DatabaseConnectionManager("localhost",
        "stock_quote", "postgres", "password");
    try {
      connection = dcm.getConnection();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    Dotenv dotenv = Dotenv.load();
    apiKey = dotenv.get("X_RAPID_API_KEY");
    client = new OkHttpClient();
    quoteHttpHelper = new QuoteHttpHelper(apiKey, client);
    quoteDAO = new QuoteDAO(connection);
    positionDAO = new PositionDAO(connection);
    quoteService = new QuoteService(quoteDAO, quoteHttpHelper);
    positionService = new PositionService(positionDAO, quoteService);
    quote = quoteService.fetchQuoteDataFromAPI("MSFT");
  }

  @Test
  void Test_buyValidInput() throws SQLException {
    validTicker = "MSFT";
    validNumOfShares = 1;
    validPrice = quote.get().getPrice();

    result = new Position();
    result = positionService.buy(validTicker, validNumOfShares, validPrice);

    assertEquals(validTicker, result.getTicker());
    assertEquals(validPrice * validNumOfShares, result.getValuePaid());
  }

  @Test
  void Test_buyInvalidTicker() throws SQLException {
    invalidTicker = " ";
    validNumOfShares = 1;
    validPrice = 327.73;

    result = new Position();
    result = positionService.buy(invalidTicker, validNumOfShares, validPrice);

    assertNull(result);
  }

  @Test
  void Test_buyInvalidNumOfShares() throws SQLException {
    validTicker = "MSFT";
    invalidNumOfShares = 0;
    validPrice = quote.get().getPrice();

    result = new Position();
    result = positionService.buy(validTicker, invalidNumOfShares, validPrice);

    assertNull(result);
  }

  @Test
  void Test_buyInvalidPrice() throws SQLException {
    validTicker = "MSFT";
    validNumOfShares = 1;
    invalidPrice = 0.0;

    result = new Position();
    result = positionService.buy(validTicker, validNumOfShares, invalidPrice);

    assertNull(result);
  }

  @Test
  void Test_sellInvalidTicker() {
    invalidTicker = " ";
    positionService.sell(invalidTicker);

    assertTrue(positionDAO.findById(invalidTicker).isEmpty());
  }

  @Test
  void Test_sellValidTicker() {
    validTicker = "MSFT";
    positionService.sell(validTicker);

    assertTrue(positionDAO.findById(invalidTicker).isEmpty());
  }

  @Test
  void Test_listAllEmpty() {
    positionDAO.deleteAll();
    Iterable<Position> result = positionService.listAll();

    assertNull(result);
  }

  @Test
  void Test_listAll() {
    Position position = new Position();
    position.setTicker("MSFT");
    position.setNumOfShares(1);
    position.setValuePaid(300.00);
    positionDAO.save(position);

    Iterable<Position> result = positionService.listAll();

    assertTrue(result.iterator().hasNext());
  }
}