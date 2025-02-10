package ca.jrvs.apps.trading.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import org.springframework.transaction.annotation.Transactional;

@Entity
public class SecurityOrder {

  @Id
  @NotNull
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Integer id;

  @ManyToOne
  @NotNull
  @JoinColumn(name = "account_id", nullable = false)
  private Account account;

  @ManyToOne
  @NotNull
  @JoinColumn(name = "ticker", nullable = false)
  private Quote quote;

  @NotNull
  private String status;

  @NotNull
  private Integer size;

  @Column
  private Double price;

  @Column
  private String notes;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

//  public Account getAccount() {
//    return account;
//  }

  public void setAccount(Account account) {
    this.account = account;
    account.setOrder(this);
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public Integer getSize() {
    return size;
  }

  public void setSize(Integer size) {
    this.size = size;
  }

  public Double getPrice() {
    return price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }

  public String getNotes() {
    return notes;
  }

  public void setNotes(String notes) {
    this.notes = notes;
  }

  public Quote getQuote() {
    return quote;
  }

  public void setQuote(Quote quote) {
    this.quote = quote;
  }

  @Override
  public String toString() {
    return "SecurityOrder{" +
        "id=" + id +
        ", account=" + account +
        ", quote=" + quote +
        ", status='" + status + '\'' +
        ", size=" + size +
        ", price=" + price +
        ", notes='" + notes + '\'' +
        '}';
  }
}
