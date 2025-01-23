package ca.jrvs.apps.trading.config;

import ca.jrvs.apps.trading.model.MarketDataConfig;
import ca.jrvs.apps.trading.util.MarketDataHttpHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.sql.DataSource;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.client5.http.io.HttpClientConnectionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class AppConfig {

//  private Logger logger = LoggerFactory.getLogger(AppConfig.class);

  @Bean
  public MarketDataConfig marketDataConfig() {
    MarketDataConfig marketDataConfig = new MarketDataConfig();
    marketDataConfig.setToken("demo");
    marketDataConfig.setHost("https://www.alphavantage.co/query?function=GLOBAL_QUOTE&symbol=");
//    logger.atError();
    return marketDataConfig;
  }

  @Bean
  public HttpClientConnectionManager connectionManager() {
    PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
    connectionManager.setMaxTotal(50);
    connectionManager.setDefaultMaxPerRoute(50);
    return connectionManager;
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
