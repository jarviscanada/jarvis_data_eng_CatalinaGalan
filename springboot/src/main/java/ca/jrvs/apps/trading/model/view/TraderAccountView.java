package ca.jrvs.apps.trading.model.view;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.time.LocalDate;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

@Entity
@Immutable
@Subselect("SELECT t.id AS trader_id, first_name, last_name, dob, country, email, amount AS account_funds "
    + "FROM public.trader t JOIN public.account a ON t.id = a.id")
public class TraderAccountView {

  @Id
  private Integer traderId;

  private String firstName;
  private String lastName;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MMM dd yyyy")
  private LocalDate dob;
  private String country;
  private String email;
  private Double account_funds;

  public Integer getTraderId() {
    return traderId;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  @JsonSerialize(using = LocalDateSerializer.class)
  public LocalDate getDob() {
    return dob;
  }

  public String getCountry() {
    return country;
  }

  public String getEmail() {
    return email;
  }

  public Double getAccount_funds() {
    return account_funds;
  }
}
