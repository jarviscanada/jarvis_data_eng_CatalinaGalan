package ca.jrvs.apps.stockquote.service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import ca.jrvs.apps.stockquote.model.Quote;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class QuoteServiceIntTest {

  private QuoteService quoteService;
  private String validTicker;
  private String invalidTicker;

  @BeforeEach
  void init() {
//    quoteService = new QuoteService();
  }

  @Test
  void Test_fetchQuoteDataFromApiValidTicker() {
    validTicker = "MSFT";
    Optional<Quote> result = quoteService.fetchQuoteDataFromAPI(validTicker);
    assertTrue(result.isPresent());
  }

  @Test
  void Test_fetchQuoteDataFromApiInValidTicker() {
    invalidTicker = " ";
    Optional<Quote> result = quoteService.fetchQuoteDataFromAPI(invalidTicker);
    assertTrue(result.isEmpty());
  }
}