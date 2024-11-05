package ca.jrvs.apps.stockquote.service;

import ca.jrvs.apps.stockquote.dao.QuoteDAO;
import ca.jrvs.apps.stockquote.dao.QuoteHttpHelper;
import ca.jrvs.apps.stockquote.model.Quote;
import ca.jrvs.apps.stockquote.util.DatabaseConnectionManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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
    try {
      Quote quote = quoteHttpHelper.fetchQuoteInfo(ticker);
      return Optional.of(quote);
    } catch (NullPointerException e) {
      throw new IllegalArgumentException(e);
    }
  }

  public QuoteDAO getQuoteDAO() {
    return quoteDAO;
  }

  public Optional<Quote> getOneQuote(String ticker) {
    return quoteDAO.findById(ticker);
  }


//  public Iterable<Quote> getAllRecentQuotes() {
//    Iterable<Quote> quotes = quoteDAO.findAll();
//    List<Quote> newQuotes = new ArrayList<>();
//    quotes.forEach(quote -> {
//      quote = quoteHttpHelper.fetchQuoteInfo(quote.getTicker());
//      newQuotes.add(quote);
//    });
//    return newQuotes;
//  }

}
