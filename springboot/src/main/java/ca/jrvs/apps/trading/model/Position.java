package ca.jrvs.apps.trading.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import org.hibernate.annotations.Immutable;

@Entity
@Immutable
public class Position {

  public SecurityOrder getSecurityOrder() {
    return securityOrder;
  }

  public void setSecurityOrder(SecurityOrder securityOrder) {
    this.securityOrder = securityOrder;
  }

  @OneToOne
  private SecurityOrder securityOrder;
}
