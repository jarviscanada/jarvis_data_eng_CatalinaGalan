package ca.jrvs.apps.trading.util;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class PositionId implements Serializable {

  private Integer accountId;
  private String ticker;

  public PositionId(Integer account_id, String ticker) {
    this.accountId = account_id;
    this.ticker = ticker;
  }

  public PositionId() {

  }

  public Integer getAccountId() {
    return accountId;
  }

  public String getTicker() {
    return ticker;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PositionId that = (PositionId) o;
    return Objects.equals(accountId, that.accountId) && Objects.equals(ticker,
        that.ticker);
  }

  @Override
  public int hashCode() {
    return Objects.hash(accountId, ticker);
  }
}
