package ca.jrvs.apps.trading.config;

import ca.jrvs.apps.trading.util.MarketDataHttpHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.client5.http.io.HttpClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

  private Logger logger = LoggerFactory.getLogger(AppConfig.class);

  @Bean
  public MarketDataConfig marketDataConfig() {

    MarketDataConfig marketDataConfig = new MarketDataConfig();
    marketDataConfig.setToken("NSX82Y1GPREVE1MI");
    marketDataConfig.setDemoToken("demo");

    try {
      marketDataConfig.setHost("https://www.alphavantage.co/query?function=GLOBAL_QUOTE&symbol=");
    } catch (Exception e) {
      logger.error(e.getMessage(),e.getCause());
    }

    return marketDataConfig;
  }

  @Bean
  public HttpClientConnectionManager connectionManager() {

    PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();

    try {
      connectionManager.setMaxTotal(50);
      connectionManager.setDefaultMaxPerRoute(50);
    } catch (Exception e) {
      logger.error(e.getMessage(), e.getCause());
    }

    return connectionManager;
  }

  @Bean
  public MarketDataHttpHelper marketDataHttpHelper() {
    return new MarketDataHttpHelper();
  }
}
