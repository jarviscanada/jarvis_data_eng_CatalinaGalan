package ca.jrvs.apps.trading.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonGetter;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.sql.Timestamp;
import java.util.Set;

@Entity
@Table(schema = "public")
public class Quote {

    @Id
    private String ticker;

    @OneToMany(mappedBy = "quote")
    private Set<SecurityOrder> orders;

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
    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "EEE MMM dd HH:mm:ss zzz yyyy")
    private Timestamp lastUpdated;

  @JsonGetter("Ticker")
  public String getTicker() {
    return ticker;
  }

  public void setTicker(String ticker) {
    this.ticker = ticker;
  }

  @JsonGetter("Last Price")
  public Double getLastPrice() {
    return lastPrice;
  }

  public void setLastPrice(Double lastPrice) {
    this.lastPrice = lastPrice;
  }

  @JsonGetter("Bid Price")
  public Double getBidPrice() {
    return bidPrice;
  }

  public void setBidPrice(Double bidPrice) {
    this.bidPrice = bidPrice;
  }

  @JsonGetter("Bid Size")
  public Integer getBidSize() {
    return bidSize;
  }

  public void setBidSize(Integer bidSize) {
    this.bidSize = bidSize;
  }

  @JsonGetter("Ask Price")
  public Double getAskPrice() {
    return askPrice;
  }

  public void setAskPrice(Double askPrice) {
    this.askPrice = askPrice;
  }

  @JsonGetter("Ask Size")
  public Integer getAskSize() { return askSize; }

  public void setAskSize(Integer askSize) {
    this.askSize = askSize;
  }

  @JsonGetter("Last Updated")
  public Timestamp getLastUpdated() {
    return lastUpdated;
  }

  @JsonFormat(shape = Shape.ANY)
  public void setLastUpdated(Timestamp lastUpdated) {
    this.lastUpdated = lastUpdated;
  }

}
