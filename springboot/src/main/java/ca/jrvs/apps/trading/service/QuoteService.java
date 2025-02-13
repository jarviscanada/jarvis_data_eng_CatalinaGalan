package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.model.AlphaQuote;
import ca.jrvs.apps.trading.model.Quote;
import ca.jrvs.apps.trading.repository.QuoteRepository;
import ca.jrvs.apps.trading.util.MarketDataHttpHelper;
import ca.jrvs.apps.trading.util.ResourceNotFoundException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Service;

@Service
public class QuoteService {

  @Autowired
  private QuoteRepository quoteRepository;

  @Autowired
  private MarketDataHttpHelper httpHelper;


  /**
   * Find an AlphaQuote from Alpha Vantage Api.
   *
   * @param ticker
   * @return AlphaQuote object.
   * @throws IllegalArgumentException for invalid input.
   * @throws ResourceNotFoundException if ticker is not found in Alpha Vantage.
   */
  public AlphaQuote findAlphaQuoteByTicker(String ticker) throws ResourceNotFoundException {

    if ((ticker.length() > 5) || !ticker.matches("[:upper:]")
        || ticker.isEmpty() || ticker.isBlank()) {
      throw new IllegalArgumentException("Invalid Input.");
    }

    Optional<AlphaQuote> alphaQuote = httpHelper.findQuoteByTicker(ticker);

    if (alphaQuote.isEmpty() || alphaQuote.get().getTicker() == null) {
      throw new ResourceNotFoundException("Symbol not found in Alpha Vantage.");
    }

    return alphaQuote.get();
  }


  /**
   * Update quote table against Alpha Vantage source.
   * - NOTE: Alpha Vantage provides a maximum of 25 API calls for free ApiKey.
   *
   * @throws IllegalArgumentException for invalid input.
   * @throws ResourceNotFoundException if ticker is not found in Alpha Vantage.
   */
  public void updateMarketData() throws ResourceNotFoundException {

    List<Quote> dailyList = findAllQuotes();
    List<String> tickers = new ArrayList<>();

    for (Quote quote : dailyList) {
      tickers.add(quote.getTicker());
    }

    saveQuotes(tickers);
  }


  /**
   * Update a given quote to the quote table without validation.
   *
   * @param quote entity to save.
   * @return the saved Quote entity.
   */
  public Quote saveQuote(Quote quote) {
    return quoteRepository.save(quote);
  }


  /**
   * Saves a new Quote to quote table from Alpha Vantage.
   * - NOTE: Alpha Vantage provides a maximum of 25 API calls for free ApiKey.
   *
   * @param ticker
   * @return created Quote entity.
   * @throws ResourceNotFoundException if ticker is not found in Alpha Vantage.
   */
  public Quote saveQuote(String ticker) throws ResourceNotFoundException {

    AlphaQuote alphaQuote = findAlphaQuoteByTicker(ticker);
    Quote newQuote = buildQuoteFromAlphaQuote(alphaQuote);
//        newQuote.setLastUpdated(Timestamp.from(Instant.now()));
    return quoteRepository.save(newQuote);
  }


  /**
   * @throws DataAccessException if unable to retrieve data.
   * @return List of Quote entities from db.
   */
  public List<Quote> findAllQuotes() {

    try {
      return quoteRepository.findAll();
    } catch (Exception e) {
      throw new DataAccessException(e.getMessage()) {
        @Override
        public Throwable getRootCause() {
          return super.getRootCause();
        }
      };
    }
  }


  /**
   * Helper method to map an AlphaQuote to a Quote entity.
   *
   * @param alphaQuote object to map.
   * @return Quote entity.
   */
    protected static Quote buildQuoteFromAlphaQuote(AlphaQuote alphaQuote) {

      Double price = alphaQuote.getPrice();
      Double spread = price * 0.05;
      Double bidPrice = price - spread;
      Double askPrice = price + spread;

      Quote quote = new Quote();

      quote.setTicker(alphaQuote.getTicker());
      quote.setLastPrice(alphaQuote.getPrice());
      quote.setBidPrice(bidPrice);
      quote.setAskPrice(askPrice);
      quote.setBidSize(1000);
      quote.setAskSize(500);
      quote.setLastUpdated(Timestamp.from(alphaQuote.getLatestTradingDay().toInstant()));

      return quote;
    }


  /**
   * Helper method to update a list of Quotes in quote table with Alpha Vantage data.
   * - NOTE: Alpha Vantage provides a maximum of 25 API calls for free ApiKey.
   *
   * @param tickers
   * @return list of converted quote entities.
   * @throws ResourceNotFoundException if ticker is not found from Alpha Quote.
   */
  protected List<Quote> saveQuotes(List<String> tickers) throws ResourceNotFoundException {

    for (String ticker : tickers) {
      AlphaQuote alphaQuote = findAlphaQuoteByTicker(ticker);
      Quote quote = buildQuoteFromAlphaQuote(alphaQuote);
      saveQuote(quote);
    }

    return findAllQuotes();
  }
}
