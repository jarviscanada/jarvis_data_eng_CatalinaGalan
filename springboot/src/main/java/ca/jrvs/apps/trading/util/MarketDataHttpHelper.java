package ca.jrvs.apps.trading.util;

import ca.jrvs.apps.trading.model.AlphaQuote;
import ca.jrvs.apps.trading.model.MarketDataConfig;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
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

public class MarketDataHttpHelper {

  @Autowired
  private PoolingHttpClientConnectionManager connectionManager;

  @Autowired
  private MarketDataConfig marketDataConfig;

  @Autowired
  private ObjectMapper objectMapper;

  public Optional<AlphaQuote> findQuoteByTicker(String ticker) throws IOException {
    String url = marketDataConfig.getHost() + ticker + "&apikey=" + marketDataConfig.getToken();
    String resposeBody = executeGetRequest(url).get();

    objectMapper.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, true);
    AlphaQuote alphaQuote = objectMapper.readValue(resposeBody, AlphaQuote.class);
    alphaQuote.setLastUpdated(Timestamp.from(Instant.now()));
    return Optional.of(alphaQuote);
  }

  private Optional<String> executeGetRequest(String url) throws IOException {
    HttpGet getRequest = new HttpGet(url);
    CloseableHttpClient client = getHttpClient();
    try (CloseableHttpResponse response = client.execute(getRequest)) {
      HttpEntity entity = response.getEntity();
      if (entity != null) {
        return Optional.of(EntityUtils.toString(entity));
      }
    } catch (DataRetrievalFailureException | ParseException e) {
      throw new RuntimeException(e);
    }
    return Optional.empty();
  }

  private CloseableHttpClient getHttpClient() {
//    HttpClientConnectionManager leasaedConnection = HttpClientConnectionManager.lease();
    return HttpClients.createMinimal(connectionManager);
//    return HttpClients.createDefault();
//    return client;
  }

}
