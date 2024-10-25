package ca.jrvs.apps.stockquote.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import ca.jrvs.apps.stockquote.dao.QuoteHttpHelper;
import ca.jrvs.apps.stockquote.model.Quote;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class QuoteServiceUnitTest {

  private QuoteHttpHelper quoteHttpHelper;
  private Quote quote;
  private String validTicker;
  private String invalidTicker;

  @BeforeEach
  void init() {
    quoteHttpHelper = new QuoteHttpHelper();
    quote = new Quote();
  }

  @Test
  void Test_quoteServiceFetchDataFromApiValidId() {
    validTicker = "MSFT";
    quote = quoteHttpHelper.fetchQuoteInfo(validTicker);
    assertEquals(validTicker, quote.getTicker());
  }

  @Test
  void Test_quoteServiceFetchDataFromApiInvalidId() {
    invalidTicker = " ";
    quote = quoteHttpHelper.fetchQuoteInfo(invalidTicker);
    assertNull(quote.getTicker());
  }
}