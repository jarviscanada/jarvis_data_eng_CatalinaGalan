package ca.jrvs.apps.trading.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonSetter;
import java.time.Instant;
import java.util.Date;

/**
 * https://www.alphavantage.co/documentation/#latestprice
 */
@JsonRootName("Global Quote")
public class AlphaQuote {

  private String ticker;
  private double open;
  private double high;
  private double low;
  private double price;
  private int volume;
  private double previousClose;
  private double change;
  private String changePercent;
  private Date latestTradingDay;

  @JsonGetter("Ticker")
  public String getTicker() {
    return ticker;
  }

  @JsonSetter("01. symbol")
  public void setTicker(String ticker) {
    this.ticker = ticker;
  }

  @JsonGetter("Open")
  public double getOpen() {
    return open;
  }

  @JsonSetter("02. open")
  public void setOpen(double open) {
    this.open = open;
  }

  @JsonGetter("High")
  public double getHigh() {
    return high;
  }

  @JsonSetter("03. high")
  public void setHigh(double high) {
    this.high = high;
  }

  @JsonGetter("Low")
  public double getLow() {
    return low;
  }

  @JsonSetter("04. low")
  public void setLow(double low) {
    this.low = low;
  }

  @JsonGetter("Price")
  public double getPrice() {
    return price;
  }

  @JsonSetter("05. price")
  public void setPrice(double price) {
    this.price = price;
  }

  @JsonGetter("Volume")
  public int getVolume() {
    return volume;
  }

  @JsonSetter("06. volume")
  public void setVolume(int volume) {
    this.volume = volume;
  }

  @JsonGetter("Latest Trading Day")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "EEE MMM dd HH:mm:ss zzz yyyy")
  public Date getLatestTradingDay() {
    return latestTradingDay;
  }

  @JsonSetter("07. latest trading day")
  public void setLatestTradingDay(Date latestTradingDay) {
    this.latestTradingDay = latestTradingDay;
  }

  @JsonGetter("Previous Close")
  public double getPreviousClose() {
    return previousClose;
  }

  @JsonSetter("08. previous close")
  public void setPreviousClose(double previousClose) {
    this.previousClose = previousClose;
  }

  @JsonGetter("Change")
  public double getChange() {
    return change;
  }

  @JsonSetter("09. change")
  public void setChange(double change) {
    this.change = change;
  }

  @JsonGetter("Change Percent")
  public String getChangePercent() {
    return changePercent;
  }

  @JsonSetter("10. change percent")
  public void setChangePercent(String changePercent) {
    this.changePercent = changePercent;
  }

//  @JsonGetter("Last Updated")
//  public Timestamp getLastUpdated() {
//    return lastUpdated;
//  }
//
//  public void setLastUpdated(Timestamp lastUpdated) {
//    this.lastUpdated = lastUpdated;
//  }

  @Override
  public String toString() {
    return "AlphaQuote {" +
        "ticker ='" + ticker + '\'' +
        ", open =" + open +
        ", high =" + high +
        ", low =" + low +
        ", price =" + price +
        ", volume =" + volume +
        ", latest trading day =" + latestTradingDay +
        ", close=" + previousClose +
        ", change=" + change +
        ", change percent=" + changePercent +
//        ", last updated=" + lastUpdated + '\'' +
        '}';
  }
}
