package ca.jrvs.apps.trading.util;

import ca.jrvs.apps.trading.model.AlphaQuote;
import ca.jrvs.apps.trading.model.MarketDataConfig;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class MarketDataHttpHelper {

  @Autowired
  private PoolingHttpClientConnectionManager connectionManager;

  @Autowired
  private MarketDataConfig marketDataConfig;

  @Autowired
  private ObjectMapper objectMapper;

  /**
   * Get an IexQuote
   *
   * @param ticker
   * @throws IllegalArgumentException if a given ticker is invalid
   * @throws DataRetrievalFailureException if HTTP request failed
   */
  public Optional<AlphaQuote> findQuoteByTicker(String ticker) {
    String url = marketDataConfig.getHost() + ticker + "&apikey=" + marketDataConfig.getToken();
    String responseBody = null;
    try {
      System.out.println("from MarketDataHttpHelper - findQuoteById: executeGetRequest(url)");
      responseBody = executeGetRequest(url).get();
    } catch (DataRetrievalFailureException | NoSuchElementException e) {
      System.out.println("from MarketDataHttpHelper - caught DataRetrievalFailureException: findQuoteByTicker: executeGetRequest(url)");
      throw new DataRetrievalFailureException(e.getMessage());
    }

    try {
      objectMapper.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, true);
      System.out.println("from MarketDataHttpHelper - findQuoteById: deserializing JSON");
      AlphaQuote alphaQuote = objectMapper.readValue(responseBody, AlphaQuote.class);
      alphaQuote.setLastUpdated(Timestamp.from(Instant.now()));
      System.out.println(alphaQuote);
      return Optional.of(alphaQuote);
    } catch (JsonProcessingException e) {
      System.out.println(responseBody);
      System.out.println("from MarketDataHttpHelper - caught JsonProcessingException: findQuoteByTicker: objectMapper.readValue()");
      if (responseBody.contains("Information")) {
        throw new ResponseStatusException(HttpStatus.PAYMENT_REQUIRED, responseBody);
      }
      throw new IllegalArgumentException("Invalid Ticker.");
    }
  }

  /**
   * Execute a GET request and return http entity/body as a string
   * Tip: use EntitiyUtils.toString to process HTTP entity
   *
   * @param url resource URL
   * @return http response body or Optional.empty for 404 response
   * @throws DataRetrievalFailureException if HTTP failed or status code is unexpected
   */
  private Optional<String> executeGetRequest(String url) {
    HttpGet getRequest = new HttpGet(url);
    CloseableHttpClient client = getHttpClient();
    try (CloseableHttpResponse response = client.execute(getRequest)) {
      HttpEntity entity = response.getEntity();
      int status = response.getCode();
      System.out.println(
          "from MarketDataHttpHelper - executeGet: response status code = " + status);
      if (entity != null && status != 404) {
        return Optional.of(EntityUtils.toString(entity));
      }
    } catch (ParseException | IOException e) {
      System.out.println("from MarketDataHttpHelper - executeGet: DataRetrievalFailureException");
      throw new DataRetrievalFailureException(e.getMessage());
    }
    return Optional.empty();
  }

  /**
   * Borrow a HTTP client from the HttpClientConnectionManager
   * @return a HttpClient
   */
  private CloseableHttpClient getHttpClient() {
//    HttpClientConnectionManager leasaedConnection = HttpClientConnectionManager.lease();
//    return HttpClients.createDefault();
//    return client;
    return HttpClients.createMinimal(connectionManager);
  }

}
