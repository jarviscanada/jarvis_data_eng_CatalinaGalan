package ca.jrvs.apps.trading;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

//import ca.jrvs.apps.trading.config.TestConfig;
import ca.jrvs.apps.trading.model.Quote;
import ca.jrvs.apps.trading.repository.QuoteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class QuoteRepositoryIntTest {

  @Autowired
  private QuoteRepository quoteRepository;

  private Quote savedQuote;

  @BeforeEach
  public void insertOneQuote() {
    Quote savedQuote = new Quote();
    savedQuote.setTicker("IBM");
    savedQuote.setAskPrice(200.5);
    savedQuote.setAskSize(13);
    savedQuote.setBidPrice(200.05);
    savedQuote.setBidSize(10);
    savedQuote.setLastPrice(200.00);
    quoteRepository.save(savedQuote);
  }

  @Test
  public void saveQuoteTest() {
    long expected = 1;
    long actual = quoteRepository.count();
    assertEquals(expected, actual);
  }
}
