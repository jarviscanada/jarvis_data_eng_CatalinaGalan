package ca.jrvs.apps.stockquote.service;

import ca.jrvs.apps.stockquote.dao.QuoteDAO;
import ca.jrvs.apps.stockquote.dao.QuoteHttpHelper;
import ca.jrvs.apps.stockquote.model.Quote;
import ca.jrvs.apps.stockquote.util.DatabaseConnectionManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

public class QuoteService {

  private QuoteDAO quoteDAO;
  private QuoteHttpHelper quoteHttpHelper;

  /**
   * Fetches latest quote data from endpoint
   * @param ticker
   * @return Latest quote information or empty optional if ticker symbol not found
   */
  public Optional<Quote> fetchQuoteDataFromAPI(String ticker) {

    quoteHttpHelper = new QuoteHttpHelper();
    Quote quote = quoteHttpHelper.fetchQuoteInfo(ticker);
    DatabaseConnectionManager dcm = new DatabaseConnectionManager("localhost",
        "stock_quote", "postgres", "password");

    try {
      Connection connection = dcm.getConnection();
      quoteDAO = new QuoteDAO(connection);
      quoteDAO.save(quote);
      return quoteDAO.findById(ticker);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
}
