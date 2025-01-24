package ca.jrvs.apps.trading.util;

import static org.junit.jupiter.api.Assertions.*;

import ca.jrvs.apps.trading.model.AlphaQuote;
import ca.jrvs.apps.trading.model.MarketDataConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.io.HttpClientConnectionManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MarketDataHttpHelperTest {

  @Autowired
  private MarketDataHttpHelper marketDataHttpHelper;

  @Autowired
  private MarketDataConfig marketDataConfig;

  @Autowired
  private HttpClientConnectionManager connectionManager;

  @Autowired
  private ObjectMapper objectMapper;

  private AlphaQuote alphaQuote;

  @Test
  void findQuoteByTicker() {
    alphaQuote = marketDataHttpHelper.findQuoteByTicker("IBM").get();
    assertEquals("IBM", alphaQuote.getTicker());
  }
}