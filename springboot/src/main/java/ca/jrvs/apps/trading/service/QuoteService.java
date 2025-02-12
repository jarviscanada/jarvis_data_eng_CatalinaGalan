package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.model.AlphaQuote;
import ca.jrvs.apps.trading.model.Quote;
import ca.jrvs.apps.trading.repository.QuoteRepository;
import ca.jrvs.apps.trading.util.MarketDataHttpHelper;
import ca.jrvs.apps.trading.util.ResourceNotFoundException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
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
   * Update quote table against IEX source
   * - get quote for given ticker from the db
   * - get IexQuote for set ticker
   * - convert IexQuote to Quote entity
   * - persist quote to db
   *
   * @throws ResourceNotFoundException if ticker is not found from IEX
   * @throws DataAccessException if unable to retrieve data
   * @throws IllegalArgumentException for invalid input
   */
  public Quote updateMarketDataQuote(String ticker) throws ResourceNotFoundException, DataAccessException {

    Quote updatedQuote;

    if (quoteRepository.existsById(ticker)) {

      try {
          updatedQuote = saveQuote(ticker);
          return saveQuote(updatedQuote);
      } catch (IllegalArgumentException e) {
        if (e.getMessage().contains("Alpha Vantage")) {
          throw new ResourceNotFoundException(e.getMessage(), e);
        }
        throw new IllegalArgumentException(e.getMessage());
      } catch (DataRetrievalFailureException e) {
        throw new DataAccessException(e.toString()) {
          @Override
          public Throwable getRootCause() {
            return super.getRootCause();
          }
        };
      }

    } else {

      throw new IllegalArgumentException("Invalid Ticker: "
          + "Quote for ticker " + ticker + " not found in Daily List.");

    }

  }

  /**
   * Find an AlphaQuote
   * @param ticker
   * @return AlphaQuote object
   * @throws IllegalArgumentException if ticker is invalid
   */
    public AlphaQuote findAlphaQuoteByTicker(String ticker) {

      if (!ticker.isEmpty()) {
        return httpHelper.findQuoteByTicker(ticker).get();
      }

      throw new IllegalArgumentException("Ticker cannot be empty.");

    }

  /**
   * Update a given quote to the quote table without validation
   *
   * @param quote entity to save
   * @return the saved quote entity
   */
  public Quote saveQuote(Quote quote) {

    quoteRepository.save(quote);
    quote.setLastUpdated(Timestamp.from(Instant.now()));

    return quoteRepository.findById(quote.getTicker()).get();

  }

  /**
   * Find all quotes from the quote table
   * @return a list of quotes
   */
  public List<Quote> findAllQuotes() {

    return quoteRepository.findAll();

  }

  /**
   * Helper method to map an AlphaQuote to a Quote entity
   * Note: 'alphaQuote.getPrice() == null' if the stock market is closed
   * Default values for number field(s)
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
   * Helper method to validate and save a single ticker
   * Not to be confused with saveQuote(Quote quote)
   * @param ticker
   * @return Quote
   * @throws IllegalArgumentException if ticker is not found in Alpha Vantage
   */
  public Quote saveQuote(String ticker) {

      try {
        AlphaQuote alphaQuote = findAlphaQuoteByTicker(ticker);
        Quote newQuote = buildQuoteFromAlphaQuote(alphaQuote);
        newQuote.setLastUpdated(Timestamp.from(Instant.now()));
        return newQuote;
      } catch (IllegalArgumentException e) {
        throw new IllegalArgumentException(e.getMessage());
      }

    }
}
