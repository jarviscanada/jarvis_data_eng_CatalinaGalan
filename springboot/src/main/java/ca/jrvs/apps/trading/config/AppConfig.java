package ca.jrvs.apps.trading.config;

import ca.jrvs.apps.trading.model.MarketDataConfig;
import ca.jrvs.apps.trading.util.MarketDataHttpHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

//  private Logger logger = LoggerFactory.getLogger(AppConfig.class);

  @Bean
  public MarketDataConfig marketDataConfig() {
    MarketDataConfig marketDataConfig = new MarketDataConfig();
    marketDataConfig.setToken(System.getenv("API_KEY"));
    marketDataConfig.setHost("https://www.alphavantage.co/query?function=GLOBAL_QUOTE&symbol=");
//    logger.atError();
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
