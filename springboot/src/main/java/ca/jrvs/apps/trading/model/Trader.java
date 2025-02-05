package ca.jrvs.apps.trading.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(schema = "public")
public class Trader {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Integer id;

  @NotBlank(message = "First Name must be provided.")
  private String firstName;

  @NotBlank(message = "Last Name must be provided.")
  private String lastName;

  @NotBlank(message = "dob must be provided.")
//  @Temporal(TemporalType.DATE)
  private LocalDate dob;

  @NotBlank(message = "Country must be provided.")
  private String country;

  @NotBlank(message = "Email must be provided.")
  @Email(message = "Email must be valid.")
  private String email;

  @OneToOne(mappedBy = "trader", cascade = CascadeType.ALL)
  @PrimaryKeyJoinColumn
  private Account account;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Account getAccount() {
    return account;
  }

  public void setAccount(Account account) {
    this.account = account;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public LocalDate getDob() {
    return dob;
  }

  public void setDob(String dob) {
//    try {
      this.dob = LocalDate.parse(dob);
//    } catch ()
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }
}
