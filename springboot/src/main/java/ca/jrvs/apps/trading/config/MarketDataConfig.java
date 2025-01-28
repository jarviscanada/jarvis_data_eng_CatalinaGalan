package ca.jrvs.apps.trading.config;

public class MarketDataConfig {

  private String host;
  private String token;
  private String demoToken;

  public String getHost() {
    return host;
  }

  public void setHost(String host) {
    this.host = host;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public String getDemoToken() {
    return demoToken;
  }

  public void setDemoToken(String demoToken) {
    this.demoToken = demoToken;
  }
}
