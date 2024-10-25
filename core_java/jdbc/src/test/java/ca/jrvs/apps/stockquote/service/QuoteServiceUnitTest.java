package ca.jrvs.apps.stockquote.service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import ca.jrvs.apps.stockquote.model.Quote;
import java.io.IOException;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class QuoteServiceUnitTest {

  QuoteService quoteService;
  String ticker;

  @BeforeEach
  void init() throws IOException {
    quoteService = new QuoteService();
    ticker = "MSFT";
  }

  @Test
  void Test_fetchQuoteDataFromApi() {
    Optional<Quote> result = quoteService.fetchQuoteDataFromAPI(ticker);
    assertTrue(result.isPresent());
  }
}