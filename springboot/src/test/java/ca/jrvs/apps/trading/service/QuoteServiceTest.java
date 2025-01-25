package ca.jrvs.apps.trading.service;

import static org.junit.jupiter.api.Assertions.*;

import ca.jrvs.apps.trading.repository.QuoteRepository;
import ca.jrvs.apps.trading.util.MarketDataHttpHelper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class QuoteServiceIntTest {

  @Autowired
  private MarketDataHttpHelper marketDataHttpHelper;

  @Autowired
  private static QuoteRepository quoteRepository;

  @AfterAll
  public static void clearDatabase() {
    quoteRepository.deleteAll();
  }

  @Test
  void updateMarketData() {
  }

  @Test
  void findAlphaQuoteByTicker() {
  }

  @Test
  void saveQuote() {
  }

  @Test
  void findAllQuotes() {
  }

  @Test
  void buildQuoteFromAlphaQuote() {
  }

  @Test
  void saveNewQuote() {
  }
}