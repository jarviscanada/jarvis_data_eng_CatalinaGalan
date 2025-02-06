package ca.jrvs.apps.trading.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.lang.NonNull;

public class MarketOrder {

  @NotNull
  private String ticker;
  @NotNull
  private int size;
  @NotNull
  private int traderId;
  @NotNull
  private Option option;

  public Option getOption() {
    return option;
  }

  public void setOption(Option option) {
    this.option = option;
  }


  public String getTicker() {
    return ticker;
  }

  public void setTicker(String ticker) {
    this.ticker = ticker;
  }

  public int getSize() {
    return size;
  }

  public void setSize(int size) {
    this.size = size;
  }

  public int getTraderId() {
    return traderId;
  }

  public void setTraderId(int traderId) {
    this.traderId = traderId;
  }
}
