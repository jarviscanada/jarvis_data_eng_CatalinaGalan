package ca.jrvs.apps.trading.controller;

import ca.jrvs.apps.trading.model.Trader;
import ca.jrvs.apps.trading.model.TraderAccountView;
import ca.jrvs.apps.trading.service.TraderAccountService;
import java.sql.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping("/trader")
public class TraderAccountController {

  @Autowired
  private TraderAccountService traderAccountService;

//  public TraderAccountView createTrader(Trader trader) {
//    return traderAccountService.createTraderAndAccount(trader);
//  }

  @PostMapping("/")
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public Trader createTrader(String firsName, String lastName, String dob,
      String country, String email) {

    Trader trader = new Trader();
    trader.setFirstName(firsName);
    trader.setLastName(lastName);
    trader.setDob(dob);
    trader.setCountry(country);
    trader.setEmail(email);

    return traderAccountService.createTraderAndAccount(trader);
  }
}
