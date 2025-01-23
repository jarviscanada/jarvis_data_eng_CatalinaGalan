package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.model.AlphaQuote;
import ca.jrvs.apps.trading.repository.QuoteRepository;
import ca.jrvs.apps.trading.util.MarketDataHttpHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuoteService {

  @Autowired
  private QuoteRepository quoteRepository;

  @Autowired
  private MarketDataHttpHelper httpHelper;

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
}
