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

  public QuoteService(QuoteDAO quoteDAO, QuoteHttpHelper quoteHttpHelper) {
    this.quoteDAO = quoteDAO;
    this.quoteHttpHelper = quoteHttpHelper;
  }

  /**
   * Fetches latest quote data from endpoint
   * @param ticker
   * @return Latest quote information or empty optional if ticker symbol not found
   */
  public Optional<Quote> fetchQuoteDataFromAPI(String ticker) {

    Quote quote = quoteHttpHelper.fetchQuoteInfo(ticker);
    quoteDAO.save(quote);

    return quoteDAO.findById(ticker);
  }
}
