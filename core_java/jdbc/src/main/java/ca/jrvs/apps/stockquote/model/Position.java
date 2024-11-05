package ca.jrvs.apps.stockquote.model;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonSetter;

@JsonRootName("Position")
public class Position {

  private String ticker;
  private int numOfShares;
  private double valuePaid;

  @JsonSetter("symbol")
  public void setTicker(String ticker) { this.ticker = ticker; }

  public String getTicker() { return ticker; }

  @JsonSetter("num_of_shares")
  public void setNumOfShares(int numOfShares) { this.numOfShares = numOfShares; }

  public int getNumOfShares() { return numOfShares; }

  @JsonSetter("value_paid")
  public void setValuePaid(double valuePaid) { this.valuePaid = valuePaid; }

  public double getValuePaid() { return valuePaid; }

}
