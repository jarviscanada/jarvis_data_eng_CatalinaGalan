package ca.jrvs.apps.trading.util;

import static org.junit.jupiter.api.Assertions.*;

import ca.jrvs.apps.trading.model.AlphaQuote;
import ca.jrvs.apps.trading.config.MarketDataConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.io.HttpClientConnectionManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class MarketDataHttpHelperTest {

  @Autowired
  private MarketDataHttpHelper marketDataHttpHelper;

  @Autowired
  private MarketDataConfig marketDataConfig;

  @Autowired
  private HttpClientConnectionManager connectionManager;

  @Test
  void findQuoteByValidTickerTest() {

    AlphaQuote alphaQuote = marketDataHttpHelper.findQuoteByTicker("IBM").get();
    assertEquals("IBM", alphaQuote.getTicker());
  }

  @Test
  public void findQuoteByInvalidTickerTest() {

    assertThrows(Exception.class, () -> {
      marketDataHttpHelper.findQuoteByTicker("AppleInc");
    });
  }

//  @Test
//  public void executeGetRequestDemo() {
//    String demoTicker = "IBM";
//    String url = marketDataConfig.getHost() + demoTicker + "&apikey=" + marketDataConfig.getDemoToken();
//    Optional<String> responseBody = marketDataHttpHelper.executeGetRequest(url);
//    assertTrue(responseBody.isPresent());
//  }

//  @Test
//  public void executeGetRequestInvalidUrl() {
//    String urlWrong = "https://www.aphavantage.co/helloGato";
//    String urlIncomplete = "https://www.alphavantage.co/helloGato";
//
//    try {
//      Optional<String> responseBody = marketDataHttpHelper.executeGetRequest(urlWrong);
//      System.out.println(responseBody);
//    } catch (Exception e) {
//      System.out.println(e);
//    }
//
//    try {
//      Optional<String> responseBody = marketDataHttpHelper.executeGetRequest(urlIncomplete);
//      System.out.println(responseBody);
//    } catch (Exception e) {
//      System.out.println(e);
//    }
//  }

}