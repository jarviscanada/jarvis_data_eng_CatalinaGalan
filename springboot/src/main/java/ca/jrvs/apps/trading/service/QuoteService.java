package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.model.AlphaQuote;
import ca.jrvs.apps.trading.model.Quote;
import ca.jrvs.apps.trading.repository.QuoteRepository;
import ca.jrvs.apps.trading.util.MarketDataHttpHelper;
import ca.jrvs.apps.trading.util.ResourceNotFoundException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
  public void updateMarketData(String ticker) throws ResourceNotFoundException, DataAccessException {

    Quote updatedQuote;

    try {
//      AlphaQuote alphaQuote = findAlphaQuoteByTicker(ticker);
//      updatedQuote = buildQuoteFromAlphaQuote(alphaQuote);
      updatedQuote = createNewQuote(ticker);
    } catch (IllegalArgumentException e){
      throw new IllegalArgumentException(e.getMessage());
    } catch (DataRetrievalFailureException | ResponseStatusException e) {
      throw new ResourceNotFoundException(e.toString());
    }

    Optional<Quote> quote = quoteRepository.findById(ticker);

    if (quote.isPresent()) {
        saveQuote(updatedQuote);
    } else {
      throw new DataAccessException("Quote for ticker " + ticker + " not found in database.") {
        @Override
        public Throwable getRootCause() {
          return super.getRootCause();
        }
      };
    }
  }

  /**
   * Find an AlphaQuote
   * @param ticker
   * @return AlphaQuote object
   * @throws IllegalArgumentException if ticker is invalid
   */
    public AlphaQuote findAlphaQuoteByTicker(String ticker) {
      if (ticker.isEmpty()) {
        System.out.println(
            "form QuoteService - Throwing IllegalArgumentException for empty ticker");
        throw new IllegalArgumentException("Empty ticker.");
      }

      try {
        Optional<AlphaQuote> alphaQuoteOpt = httpHelper.findQuoteByTicker(ticker);
        return alphaQuoteOpt.get();
      } catch (Exception e) {
        System.out.println("from QuoteService - caught Exception");
        throw e;
      }
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
      quote.setLastUpdated(Timestamp.from(alphaQuote.getLatestTradingDay()));

      return quote;
    }

  /**
   * Helper method to validate and save a single ticker
   * Not to be confused with saveQuote(Quote quote)
   * @param ticker
   * @return Quote
   * @throws IllegalArgumentException if ticker is not found in Alpha Vantage
   */
    protected Quote createNewQuote(String ticker) {
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
