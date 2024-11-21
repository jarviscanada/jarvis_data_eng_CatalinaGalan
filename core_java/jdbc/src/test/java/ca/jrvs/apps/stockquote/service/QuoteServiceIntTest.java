package ca.jrvs.apps.stockquote.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import ca.jrvs.apps.stockquote.dao.QuoteDAO;
import ca.jrvs.apps.stockquote.dao.QuoteHttpHelper;
import ca.jrvs.apps.stockquote.model.Quote;
import ca.jrvs.apps.stockquote.util.DatabaseConnectionManager;
import io.github.cdimascio.dotenv.Dotenv;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;
import okhttp3.OkHttpClient;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class QuoteServiceIntTest {

  private static QuoteService quoteService;
  private static QuoteDAO quoteDAO;
  private static Connection connection;
  private static OkHttpClient client;
  private static QuoteHttpHelper quoteHttpHelper;
  private static DatabaseConnectionManager dcm;

  private String validTicker;
  private String invalidTicker;

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
    String apiKey = dotenv.get("X_RAPID_API_KEY");
    client = new OkHttpClient();
    quoteHttpHelper = new QuoteHttpHelper(apiKey, client);
    quoteDAO = new QuoteDAO(connection);
    quoteService = new QuoteService(quoteDAO, quoteHttpHelper);
  }

  @AfterAll
  static void tearDown() throws SQLException {
    quoteDAO.deleteAll();
    if (connection != null) {
      connection.close();
      System.out.println("Connection CLOSED");
    }
  }

  @Test
  void Test_fetchQuoteDataFromApiValidTicker() {
    validTicker = "MSFT";
    Optional<Quote> result = quoteService.fetchQuoteDataFromAPI(validTicker);
    assertTrue(result.isPresent());
  }

  @Test
  void Test_fetchQuoteDataFromApiInValidTicker() {
    invalidTicker = "-";
    assertThrows(IllegalArgumentException.class, () -> quoteService.fetchQuoteDataFromAPI(invalidTicker));
  }
}