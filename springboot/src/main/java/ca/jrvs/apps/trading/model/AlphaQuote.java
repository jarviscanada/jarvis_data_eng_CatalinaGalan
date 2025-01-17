package ca.jrvs.apps.trading.model;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * https://www.alphavantage.co/documentation/#latestprice
 */
public class AlphaQuote {

  private String ticker;
  private double open;
  private double high;
  private double low;
  private double price;
  private int volume;
  private Date latestTradingDay;
  private double close;
  private double change;
  private String changePercent;
  private Timestamp timestamp;

  public String getTicker() {
    return ticker;
  }

  public void setTicker(String ticker) {
    this.ticker = ticker;
  }

  public double getOpen() {
    return open;
  }

  public void setOpen(double open) {
    this.open = open;
  }

  public double getHigh() {
    return high;
  }

  public void setHigh(double high) {
    this.high = high;
  }

  public double getLow() {
    return low;
  }

  public void setLow(double low) {
    this.low = low;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  public int getVolume() {
    return volume;
  }

  public void setVolume(int volume) {
    this.volume = volume;
  }

  public Date getLatestTradingDay() {
    return latestTradingDay;
  }

  public void setLatestTradingDay(Date latestTradingDay) {
    this.latestTradingDay = latestTradingDay;
  }

  public double getClose() {
    return close;
  }

  public void setClose(double close) {
    this.close = close;
  }

  public double getChange() {
    return change;
  }

  public void setChange(double change) {
    this.change = change;
  }

  public String getChangePercent() {
    return changePercent;
  }

  public void setChangePercent(String changePercent) {
    this.changePercent = changePercent;
  }

  public Timestamp getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Timestamp timestamp) {
    this.timestamp = timestamp;
  }

  @Override
  public String toString() {
    return "AlphaQuote{" +
        "symbol='" + ticker + '\'' +
        ", open=" + open +
        ", high=" + high +
        ", low=" + low +
        ", price=" + price +
        ", volume=" + volume +
        ", latestTradingDay=" + latestTradingDay +
        ", close=" + close +
        ", change=" + change +
        ", changePercent='" + changePercent + '\'' +
        ", timestamp=" + timestamp +
        '}';
  }
}
