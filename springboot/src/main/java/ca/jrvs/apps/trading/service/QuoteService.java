package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.model.AlphaQuote;
import ca.jrvs.apps.trading.model.Quote;
import ca.jrvs.apps.trading.repository.QuoteRepository;
import ca.jrvs.apps.trading.util.MarketDataHttpHelper;
import ca.jrvs.apps.trading.util.ResourceNotFoundException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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
  public void updateMarketData(String ticker) {
    Optional<Quote> quote = quoteRepository.findById(ticker);
    if (quote.isPresent()) {
      try {
        findAlphaQuoteByTicker(quote.get().getTicker());
      } catch (IllegalArgumentException e){
        throw new IllegalArgumentException(e.getMessage());
      } catch (Exception e) {
        throw e;
//        throw new DataAccessException();
      }
      saveQuote(quote.get());
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
        System.out.println("form QuoteService - Throwing IllegalArgumentException for empty ticker");
        throw new IllegalArgumentException("Empty ticker.");
      }

      try {
        return httpHelper.findQuoteByTicker(ticker).get();
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
   * Helper method to map an IexQuote to a Quote entity
   * Note: 'iexQuote.getLatestPrice() == null' if the stock market is closed
   * Make sure to set a default value for number field(s)
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
      return quote;
    }

  /**
   * Helper method to validate and save a single ticker
   * Not to be confused with saveQuote(Quote quote)
   * @param ticker
   * @return Quote
   * @throws IllegalArgumentException if ticker is not found in Alpha Vantage
   */
    protected Quote saveNewQuote(String ticker) {
      try {
        AlphaQuote alphaQuote = findAlphaQuoteByTicker(ticker);
        return buildQuoteFromAlphaQuote(alphaQuote);
      } catch (IllegalArgumentException e) {
//        throw new ResourceNotFoundException(e. getMessage());
        throw e;
      }
    }
}
