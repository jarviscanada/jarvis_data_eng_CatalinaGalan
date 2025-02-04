package ca.jrvs.apps.trading.controller;

import ca.jrvs.apps.trading.model.Account;
import ca.jrvs.apps.trading.model.Trader;
import ca.jrvs.apps.trading.service.TraderAccountService;
import ca.jrvs.apps.trading.util.ResponseExceptionUtil;
//import jakarta.validation.ConstraintViolationException;
//import org.hibernate.exception.ConstraintViolationException;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping("/trader")
public class TraderAccountController {

  @Autowired
  private TraderAccountService traderAccountService;

  @PostMapping("/")
  @ResponseStatus(HttpStatus.CREATED)
  @ResponseBody
  public Trader createTrader(Trader trader) {
    try {
      return traderAccountService.createTraderAndAccount(trader);
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException(e.getMessage());
    } catch (Exception e) {
      throw ResponseExceptionUtil.getResponseStatusException(e);
    }
  }

  @PostMapping("/firstName/{firstName}/lastName/{lastName}/dob/{dob}/country/{country}/"
      + "email/{email}")
  @ResponseStatus(HttpStatus.CREATED)
  @ResponseBody
  public Trader createTrader(@PathVariable String firstName, @PathVariable String lastName,
      @PathVariable String dob, @PathVariable String country, @PathVariable String email) {

    Trader trader = new Trader();
    try {
      trader.setFirstName(firstName);
      trader.setLastName(lastName);
      trader.setDob(dob);
      trader.setCountry(country);
      trader.setEmail(email);
      return createTrader(trader);
    } catch (Exception e) {
      throw ResponseExceptionUtil.getResponseStatusException(e);
    }
  }

  @GetMapping("/{traderId}")
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public Trader showTrader(@PathVariable Long traderId) {
    try {
      return traderAccountService.getTraderById(traderId);
    } catch (Exception e) {
      throw ResponseExceptionUtil.getResponseStatusException(e);
    }
  }

  @DeleteMapping("/{traderId}")
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public void deleteTrader(@PathVariable Long traderId) {
    try {
      traderAccountService.deleteTraderById(traderId);
      System.out.println("Trader and Account successfully deleted");
    } catch (Exception e) {
      throw ResponseExceptionUtil.getResponseStatusException(e);
    }
  }

  @PutMapping("/{traderId}/deposit/amount/{amount}")
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public Account depositFunds(@PathVariable Long traderId, @PathVariable Double amount) {
    try {
      return traderAccountService.deposit(traderId, amount);
    } catch (Exception e) {
      throw ResponseExceptionUtil.getResponseStatusException(e);
    }
  }

  @PutMapping("/{traderId}/withdraw/amount/{amount}")
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public Account withdrawFunds(@PathVariable Long traderId, @PathVariable Double amount) {
    try {
      return traderAccountService.withdraw(traderId, amount);
    } catch (Exception e) {
      throw ResponseExceptionUtil.getResponseStatusException(e);
    }
  }
}
