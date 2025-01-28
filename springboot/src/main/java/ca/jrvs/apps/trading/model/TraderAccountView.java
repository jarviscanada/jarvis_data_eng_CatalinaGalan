package ca.jrvs.apps.trading.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.MapsId;
import java.util.Date;
import org.hibernate.annotations.Immutable;

@Entity
@Immutable
public class TraderAccountView {

  @Id
  private Long id;

  @MapsId
  private Trader trader;

  private String traderFirstName;
  private String traderLastName;
  private String traderDob;
  private String traderCountry;
  private String traderEmail;

  private Account traderAccount;
  private Double traderAccountAmount;

  public Account getTraderAccount() {
    return traderAccount;
  }

  public Double getTraderAccountAmount() {
    return traderAccountAmount;
  }

  public Long getId() {
    return id;
  }

  public Trader getTrader() {
    return trader;
  }

  public String getTraderFirstName() {
    return traderFirstName;
  }

  public String getTraderLastName() {
    return traderLastName;
  }

  public String getTraderDob() {
    return traderDob;
  }

  public String getTraderCountry() {
    return traderCountry;
  }

  public String getTraderEmail() {
    return traderEmail;
  }

}
