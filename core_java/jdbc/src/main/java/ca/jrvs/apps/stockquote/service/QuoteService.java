package ca.jrvs.apps.stockquote.service;

import ca.jrvs.apps.stockquote.dao.QuoteDAO;
import ca.jrvs.apps.stockquote.dao.QuoteHttpHelper;
import ca.jrvs.apps.stockquote.model.Quote;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QuoteService {

  final Logger logger = LoggerFactory.getLogger(QuoteService.class);

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
      logger.info("Quote info successfully fetched from API.");
      return Optional.of(quote);
    } catch (NullPointerException e) {
      logger.error("Invalid ticker given for API call: {}", e.getMessage(), e);
      throw new IllegalArgumentException(e);
    }
  }

  public QuoteDAO getQuoteDAO() {
    return quoteDAO;
  }

  public Optional<Quote> getOneQuote(String ticker) {
    return quoteDAO.findById(ticker);
  }

}
