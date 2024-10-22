package ca.jrvs.apps.stockquote.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonSetter;
import java.sql.Timestamp;
import java.sql.Date;

@JsonRootName("Global Quote")
public class Quote {

  private String ticker; //id
  private double open;
  private double high;
  private double low;
  private double price;
  private int volume;
  private Date latestTradingDay;
  private double previousClose;
  private double change;
  private String changePercent;
  private Timestamp timestamp;


  public String getTicker() {
    return ticker;
  }

  @JsonSetter("01. symbol")
  public void setTicker(String ticker) {
    this.ticker = ticker;
  }

  public double getOpen() { return open; }

  @JsonSetter("02. open")
  public void setOpen(double open) {
    this.open = open;
  }

  public double getHigh() {
    return high;
  }

  @JsonSetter("03. high")
  public void setHigh(double high) {
    this.high = high;
  }

  public double getLow() {
    return low;
  }

  @JsonSetter("04. low")
  public void setLow(double low) {
    this.low = low;
  }

  public double getPrice() {
    return price;
  }

  @JsonSetter("05. price")
  public void setPrice(double price) {
    this.price = price;
  }

  public int getVolume() {
    return volume;
  }

  @JsonSetter("06. volume")
  public void setVolume(int volume) {
    this.volume = volume;
  }

  public Date getLatestTradingDay() {
    return latestTradingDay;
  }

  @JsonSetter("07. latest trading day")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  public void setLatestTradingDay(Date latestTradingDay) { this.latestTradingDay = latestTradingDay; }

  public double getPreviousClose() {
    return previousClose;
  }

  @JsonSetter("08. previous close")
  public void setPreviousClose(double previousClose) {
    this.previousClose = previousClose;
  }

  public double getChange() {
    return change;
  }

  @JsonSetter("09. change")
  public void setChange(double change) {
    this.change = change;
  }

  public String getChangePercent() {
    return changePercent;
  }

  @JsonSetter("10. change percent")
  public void setChangePercent(String changePercent) {
    this.changePercent = changePercent;
  }

  public Timestamp getTimestamp() { return timestamp; }

  public void setTimestamp(Timestamp timestamp) { this.timestamp = timestamp; }

  @Override
  public String toString() {
    return "Quote{" +
        "ticker='" + ticker + '\'' +
        ", open=" + open +
        ", high=" + high +
        ", low=" + low +
        ", price=" + price +
        ", volume=" + volume +
        ", latestTradingDay=" + latestTradingDay +
        ", previousClose=" + previousClose +
        ", change=" + change +
        ", changePercent='" + changePercent + '\'' +
        ", timestamp=" + timestamp +
        '}';
  }
}
