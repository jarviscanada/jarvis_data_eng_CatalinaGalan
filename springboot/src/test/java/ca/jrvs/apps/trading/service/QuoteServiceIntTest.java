package ca.jrvs.apps.trading.service;

import static org.junit.jupiter.api.Assertions.*;

import ca.jrvs.apps.trading.model.AlphaQuote;
import ca.jrvs.apps.trading.model.Quote;
import ca.jrvs.apps.trading.repository.QuoteRepository;
import ca.jrvs.apps.trading.util.ResourceNotFoundException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
  void updateMarketDataTest() {
    assertDoesNotThrow(() -> quoteService.updateMarketData());

    quoteRepository.deleteAll();
    assertThrows(ResourceNotFoundException.class, () -> quoteService.updateMarketData());
  }

  @Test
  void findAlphaQuoteByTickerTest() throws ResourceNotFoundException {
    String validTicker = "IBM";
    String emptyTicker = "";
    String blankTicker = " ";
    String invalidTicker = "Appl.";

    AlphaQuote alphaQuote = quoteService.findAlphaQuoteByTicker(validTicker);
    assertEquals("IBM", alphaQuote.getTicker());
    assertThrows(IllegalArgumentException.class, () -> quoteService.findAlphaQuoteByTicker(emptyTicker));
    assertThrows(IllegalArgumentException.class, () -> quoteService.findAlphaQuoteByTicker(blankTicker));
    assertThrows(IllegalArgumentException.class, () -> quoteService.findAlphaQuoteByTicker(invalidTicker));
  }

  @Test
  void updateQuoteObject() throws InterruptedException {
    savedQuote.setAskSize(15);
//    Thread.sleep(1000);
    Quote updatedQuote = quoteService.saveQuote(savedQuote);

    assertEquals(15, updatedQuote.getAskSize());
//    assertNotEquals(savedQuote.getLastUpdated(), updatedQuote.getLastUpdated());
  }

  @Test
  void createNewQuoteTest() throws ResourceNotFoundException {
    quoteService.saveQuote("MSFT");
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
    assertEquals(224.8000, testQuote.getLastPrice());
  }

  @Test
  void createNewQuoteDemoTickerTest() throws ResourceNotFoundException {
    String validDemoTicker = "MSFT";
    String emptyTicker = "";
    quoteService.saveQuote(validDemoTicker);

    assertTrue(quoteRepository.existsById(validDemoTicker));
    assertThrows(IllegalArgumentException.class, () -> quoteService.saveQuote(emptyTicker));
  }
}