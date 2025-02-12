package ca.jrvs.apps.trading.util;

import ca.jrvs.apps.trading.model.AlphaQuote;
import ca.jrvs.apps.trading.config.MarketDataConfig;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Optional;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.io.HttpClientConnectionManager;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class MarketDataHttpHelper {

  @Autowired
  private HttpClientConnectionManager connectionManager;

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

    String url = marketDataConfig.getHost() + ticker + "&apikey=" + marketDataConfig.getDemoToken();
    String responseBody = null;

    try {
      Optional<String> responseBodyOpt = executeGetRequest(url);
      responseBody = responseBodyOpt.get();
    }
    catch (DataRetrievalFailureException e) {
      throw new DataRetrievalFailureException(e.getMessage());
    }

    try {
      objectMapper.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, true);

      AlphaQuote alphaQuote = objectMapper.readValue(responseBody, AlphaQuote.class);

      if (alphaQuote.getTicker() == null) {
        throw new IllegalArgumentException("Symbol not found in Alpha Vantage. "
            + "Please provide a valid Ticker.");
      }
      return Optional.of(alphaQuote);

    } catch (JsonProcessingException e) {
      if (responseBody.contains("Information")) {
        throw new ResponseStatusException(HttpStatus.PAYMENT_REQUIRED, responseBody);
      } else if (responseBody.contains("Error")) {
        throw new DataRetrievalFailureException(responseBody);
      }
    }

    return Optional.empty();

  }

  /**
   * Execute a GET request and return http entity/body as a string
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

      if (status == 404) {
        return Optional.empty();
      }
      return Optional.of(EntityUtils.toString(entity));
    }
    catch (ParseException | IOException e) {
      throw new DataRetrievalFailureException(e.toString());
    }

  }

  /**
   * Borrow a HTTP client from the HttpClientConnectionManager
   * @return a HttpClient
   */
  private CloseableHttpClient getHttpClient() {

    return HttpClients.createMinimal(connectionManager);

  }
}
