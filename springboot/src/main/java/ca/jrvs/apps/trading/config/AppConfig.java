package ca.jrvs.apps.trading.config;

import ca.jrvs.apps.trading.model.MarketDataConfig;
import ca.jrvs.apps.trading.util.MarketDataHttpHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

  @Bean
  public MarketDataConfig marketDataConfig() {
    MarketDataConfig marketDataConfig = new MarketDataConfig();
    marketDataConfig.setToken("demo");
    marketDataConfig.setHost("https://www.alphavantage.co/query?function=GLOBAL_QUOTE&symbol=");
    return marketDataConfig;
  }

  @Bean PoolingHttpClientConnectionManager connectionManager() {
    return new PoolingHttpClientConnectionManager();
  }

  @Bean
  public MarketDataHttpHelper marketDataHttpHelper() {
    return new MarketDataHttpHelper();
  }

  @Bean
  public ObjectMapper objectMapper() {
    return new ObjectMapper();
  }
}
