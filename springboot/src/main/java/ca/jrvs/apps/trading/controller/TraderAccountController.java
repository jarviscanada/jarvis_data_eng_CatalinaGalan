package ca.jrvs.apps.trading.controller;

import ca.jrvs.apps.trading.model.Account;
import ca.jrvs.apps.trading.model.Trader;
import ca.jrvs.apps.trading.service.TraderAccountService;
import ca.jrvs.apps.trading.util.ResponseExceptionUtil;
import io.swagger.v3.oas.annotations.Operation;
import java.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping("/trader")
public class TraderAccountController {

  private static final Logger logger = LoggerFactory.getLogger(TraderAccountController.class);

  @Autowired
  private TraderAccountService traderAccountService;


  @Operation(summary = "Create a new Trader and Account.",
      description = "Create a new trader from JSON data mapped to Trader Object. "
          + "NOTE: Due to a problem with the Swagger UI it is necessary to "
          + "manually edit the JSON for correct mapping: "
          + "deleting the \"Account:\" field (and everything contained in it), "
          + "setting ID to null, "
          + "setting dob with format \"MMM dd yyyy\" (example: Oct 12 1990), "
          + "and adding a wrapper {\"Trader\": ... }")
  @PostMapping("/")
  @ResponseStatus(HttpStatus.CREATED)
  @ResponseBody
  public Trader createTrader(@RequestBody Trader trader) {

    try {
      return traderAccountService.createTraderAndAccount(trader);
    } catch (Exception e) {
      logger.debug(e.getMessage(), e.getCause());
      throw ResponseExceptionUtil.getResponseStatusException(e);
    }
  }


  @Operation(summary = "Create a new Trader and Account",
      description = "Create a new Trader from passed arguments in the url. "
          + "NOTE: dob accepted format is YYYY-MM-DD")
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
      trader.setDob(LocalDate.parse(dob));
      trader.setCountry(country);
      trader.setEmail(email);

      return traderAccountService.createTraderAndAccount(trader);

    } catch (Exception e) {
      logger.debug(e.getMessage(), e.getCause());
      throw ResponseExceptionUtil.getResponseStatusException(e);
    }
  }


  @Operation(summary = "Get a Trader by Id.",
      description = "Fetch Trader and Account information by Id.")
  @GetMapping("/traderId/{traderId}")
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public Trader showTrader(@PathVariable Integer traderId) {

    try {
      return traderAccountService.getTraderById(traderId);
    } catch (Exception e) {
      logger.debug(e.getMessage(), e.getCause());
      throw ResponseExceptionUtil.getResponseStatusException(e);
    }
  }


  @Operation(summary = "Delete a Trader.",
      description = "Delete a Trader by Id. NOTE: Account amount must be 0.")
  @DeleteMapping("/traderId/{traderId}")
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public void deleteTrader(@PathVariable Integer traderId) {

    try {
      traderAccountService.deleteTraderById(traderId);
    } catch (Exception e) {
      logger.debug(e.getMessage(), e.getCause());
      throw ResponseExceptionUtil.getResponseStatusException(e);
    }
  }


  @Operation(summary = "Deposit funds into Trader's Account.",
      description = "Add funds to Trader Account's amount by Trader ID.")
  @PutMapping("/traderId/{traderId}/deposit/amount/{amount}")
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public Account depositFunds(@PathVariable Integer traderId, @PathVariable Double amount) {

    try {
      return traderAccountService.deposit(traderId, amount);
    } catch (Exception e) {
      logger.debug(e.getMessage(), e.getCause());
      throw ResponseExceptionUtil.getResponseStatusException(e);
    }
  }


  @Operation(summary = "Withdraw funds into Trader's Account.",
      description = "Subtract funds from Trader Account's amount by Trader ID.")
  @PutMapping("/traderId/{traderId}/withdraw/amount/{amount}")
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public Account withdrawFunds(@PathVariable Integer traderId, @PathVariable Double amount) {

    try {
      return traderAccountService.withdraw(traderId, amount);
    } catch (Exception e) {
      logger.debug(e.getMessage(), e.getCause());
      throw ResponseExceptionUtil.getResponseStatusException(e);
    }
  }
}
