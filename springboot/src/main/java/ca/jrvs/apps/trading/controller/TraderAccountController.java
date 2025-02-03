package ca.jrvs.apps.trading.controller;

import ca.jrvs.apps.trading.model.Account;
import ca.jrvs.apps.trading.model.Trader;
import ca.jrvs.apps.trading.service.TraderAccountService;
import ca.jrvs.apps.trading.util.ResponseExceptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
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
    return traderAccountService.createTraderAndAccount(trader);
  }

  @PostMapping("/firstName/{firstName}/lastName/{lastName}/dob/{dob}/country/{country}/"
      + "email/{email}")
  @ResponseStatus(HttpStatus.CREATED)
  @ResponseBody
  public Trader createTrader(@PathVariable String firstName, @PathVariable String lastName,
      @PathVariable String dob, @PathVariable String country, @PathVariable String email) {

    Trader trader = new Trader();
    trader.setFirstName(firstName);
    trader.setLastName(lastName);
    trader.setDob(dob);
    trader.setCountry(country);
    trader.setEmail(email);

    return traderAccountService.createTraderAndAccount(trader);
  }

  @DeleteMapping("trader/{traderId}")
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public void deleteTrader(@PathVariable Long traderId) {
    traderAccountService.deleteTraderById(traderId);
    System.out.println("Trader and Account successfully deleted");
  }

  @PutMapping("/deposit/trader/{traderId}/amount/{amount}")
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public Account depositFunds(@PathVariable Long traderId, @PathVariable Double amount) {
    try {
      return traderAccountService.deposit(traderId, amount);
    } catch (Exception e) {
      throw ResponseExceptionUtil.getResponseStatusException(e);
    }
  }

  @PutMapping("/withdraw/trader/{traderID}/amount/{amount}")
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public Account withrowFunds(@PathVariable Long traderId, @PathVariable Double amount) {
    try {
      return traderAccountService.withdraw(traderId, amount);
    } catch (Exception e) {
      throw ResponseExceptionUtil.getResponseStatusException(e);
    }
  }
}
