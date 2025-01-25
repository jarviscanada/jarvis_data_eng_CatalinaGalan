package ca.jrvs.apps.trading.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(schema = "public")
public class Quote {

    @Id
    private String ticker;

    @Column(nullable = false)
    private Double lastPrice;
    @Column(nullable = false)
    private Double bidPrice;
    @Column(nullable = false)
    private Integer bidSize;
    @Column(nullable = false)
    private Double askPrice;
    @Column(nullable = false)
    private Integer askSize;

  public String getTicker() {
    return ticker;
  }

  public void setTicker(String ticker) {
    this.ticker = ticker;
  }

  public Double getLastPrice() {
    return lastPrice;
  }

  public void setLastPrice(Double lastPrice) {
    this.lastPrice = lastPrice;
  }

  public Double getBidPrice() {
    return bidPrice;
  }

  public void setBidPrice(Double bidPrice) {
    this.bidPrice = bidPrice;
  }

  public Integer getBidSize() {
    return bidSize;
  }

  public void setBidSize(Integer bidSize) {
    this.bidSize = bidSize;
  }

  public Double getAskPrice() {
    return askPrice;
  }

  public void setAskPrice(Double askPrice) {
    this.askPrice = askPrice;
  }

  public Integer getAskSize() { return askSize; }

  public void setAskSize(Integer askSize) {
    this.askSize = askSize;
  }
}
