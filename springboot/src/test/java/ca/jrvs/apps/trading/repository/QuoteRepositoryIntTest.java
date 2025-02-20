package ca.jrvs.apps.trading.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import ca.jrvs.apps.trading.model.Quote;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class QuoteRepositoryIntTest {

  @Autowired
  private QuoteRepository quoteRepository;

  @BeforeEach
  public void insertOneQuote() {
    Quote savedQuote = new Quote();
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
  public void cleanDb() {
    quoteRepository.deleteAll();
  }

  @Test
  public void countTest() {
    long expected = 1;
    long actual = quoteRepository.count();

    assertEquals(expected, actual);
  }

  @Test
  public void listAllTest() {
    List<Quote> allQuotes = quoteRepository.findAll();
    assertFalse(allQuotes.isEmpty());
  }

  @Test
  public void findByIdTest() {
    Quote quote = quoteRepository.findById("IBM").get();
    Integer expected = 13;
    Integer actual = quote.getAskSize();

    assertEquals(expected, actual);
  }

  @Test
  public void deleteByIdTest() {
    quoteRepository.deleteById("IBM");
    assertTrue(quoteRepository.findById("IBM").isEmpty());
  }

  @Test
  public void existByIdTest() {
    assertTrue(quoteRepository.existsById("IBM"));
  }
}
