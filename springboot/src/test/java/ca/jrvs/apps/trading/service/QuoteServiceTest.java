package ca.jrvs.apps.trading.service;

import static org.junit.jupiter.api.Assertions.*;

import ca.jrvs.apps.trading.model.AlphaQuote;
import ca.jrvs.apps.trading.model.Quote;
import ca.jrvs.apps.trading.repository.QuoteRepository;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class QuoteServiceIntTest {

  @Autowired
  private QuoteService quoteService;

  @Autowired
  private QuoteRepository quoteRepository;

  private Quote savedQuote;

  @BeforeEach
  public void saveDemoQuote() {
    savedQuote = new Quote();
    savedQuote.setTicker("IBM");
    savedQuote.setAskPrice(200.5);
    savedQuote.setAskSize(13);
    savedQuote.setBidPrice(200.05);
    savedQuote.setBidSize(10);
    savedQuote.setLastPrice(200.00);
    savedQuote.setLastUpdated(Timestamp.from(Instant.now()));
    quoteRepository.save(savedQuote);
  }

  @AfterEach
  public void clearDatabase() {
    quoteRepository.deleteAll();
  }

  @Test
  void updateMarketDataValidQuoteTest() {
    String validDemoTicker = "IBM";
    assertDoesNotThrow(() -> quoteService.updateMarketData(validDemoTicker));
  }

  @Test
  void updateMarketDataInvalidQuoteTest() {
    String notSavedTicker = "MSFT";
    assertThrows(DataAccessException.class, () -> quoteService.updateMarketData(notSavedTicker));
  }

  @Test
  void updateMarketDataInvalidTickerTest() {
    String invalidTicker = "AppleInc.";
    assertThrows(IllegalArgumentException.class, () -> quoteService.updateMarketData(invalidTicker));
  }

  @Test
  void findAlphaQuoteByEmptyTickerTest() {
    String emptyTicker = "";
    assertThrows(IllegalArgumentException.class, () -> quoteService.findAlphaQuoteByTicker(emptyTicker));
  }

  @Test
  void findAlphaQuoteByValidTickerTest() {
    String validTicker = "IBM";
    AlphaQuote alphaQuote = quoteService.findAlphaQuoteByTicker(validTicker);
    System.out.println(alphaQuote);
    assertEquals("IBM", alphaQuote.getTicker());
  }

  @Test
  void saveQuoteObject() throws InterruptedException {
    savedQuote.setAskSize(15);
    Thread.sleep(1000);
    Quote updatedQuote = quoteService.saveQuote(savedQuote);
    assertEquals(15, updatedQuote.getAskSize());
    assertNotEquals(savedQuote.getLastUpdated(), updatedQuote.getLastUpdated());
  }

  @Test
  void findAllQuotesTest() {
    Quote newQuote = quoteService.saveQuote("300135.SHZ");
    quoteService.saveQuote(newQuote);
    List<Quote> allSavedQuotes = quoteService.findAllQuotes();
    assertEquals(2, allSavedQuotes.size());
  }

  @Test
  void buildQuoteFromAlphaQuoteTest() {
    AlphaQuote testAlphaQuote = new AlphaQuote();
    testAlphaQuote.setTicker("IBM");
    testAlphaQuote.setPrice(224.8000);
    testAlphaQuote.setLatestTradingDay(Date.from(Instant.now()));
    Quote testQuote = quoteService.buildQuoteFromAlphaQuote(testAlphaQuote);
    System.out.println(testQuote.toString());
    assertEquals(224.8000, testQuote.getLastPrice());
  }

  @Test
  void createNewQuoteValidDemoTickerTest() {
    String validTicker = "300135.SHZ";
    Quote newQuote = quoteService.saveQuote(validTicker);
    System.out.println(newQuote);
    assertEquals("300135.SHZ", newQuote.getTicker());
  }

  @Test
  void createNewQuoteInvalidTickerTest() {
    String invalidTicker = "AppleInc.";
    assertThrows(IllegalArgumentException.class, () -> quoteService.saveQuote(invalidTicker));
  }

  @Test
  void createNewQuoteEmptyTickerTest() {
    String invalidTicker = "";
    assertThrows(IllegalArgumentException.class, () -> quoteService.saveQuote(invalidTicker));
  }
}