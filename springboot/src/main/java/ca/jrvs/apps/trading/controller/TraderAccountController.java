package ca.jrvs.apps.trading.controller;

import ca.jrvs.apps.trading.model.Account;
import ca.jrvs.apps.trading.model.Trader;
import ca.jrvs.apps.trading.service.TraderAccountService;
import ca.jrvs.apps.trading.util.ResponseExceptionUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

@Api(value = "trader", produces = MediaType.APPLICATION_JSON_VALUE)
@Controller
@RequestMapping("/trader")
public class TraderAccountController {

  @Autowired
  private TraderAccountService traderAccountService;

  @ApiOperation(value = "Creates new Trader and Trader Account.")
  @PostMapping("/")
  @ResponseStatus(HttpStatus.CREATED)
  @ResponseBody
  public Trader createTrader(@RequestBody Trader trader) {

    try {
      return traderAccountService.createTraderAndAccount(trader);
    } catch (Exception e) {
      throw ResponseExceptionUtil.getResponseStatusException(e);
    }

  }

  @ApiOperation(value = "Creates new Trader and Trader Account.")
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
      throw ResponseExceptionUtil.getResponseStatusException(e);
    }

  }

  @ApiOperation(value = "Show all Trader details by Id.",
      notes = "Account and all Security Orders associated are accessed by Trader.")
  @ApiResponses(value = {@ApiResponse(code = 404, message = "Trader not found for given Trader Id.")})
  @GetMapping("/traderId/{traderId}")
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public Trader showTrader(@PathVariable Integer traderId) {

    try {
      return traderAccountService.getTraderById(traderId);
    } catch (Exception e) {
      throw ResponseExceptionUtil.getResponseStatusException(e);
    }

  }

  @ApiOperation(value = "Delete Trade by Id.")
  @ApiResponses(value = {@ApiResponse(code = 404, message = "Trader not found for given Trader Id.")})
  @DeleteMapping("/traderId/{traderId}")
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public void deleteTrader(@PathVariable Integer traderId) {

    try {
      traderAccountService.deleteTraderById(traderId);
    } catch (Exception e) {
      throw ResponseExceptionUtil.getResponseStatusException(e);
    }

  }

  @ApiOperation(value = "Deposit funds in Trader Account by Trader Id.")
  @ApiResponses(value = {@ApiResponse(code = 404, message = "Trader not found for given Trader Id."),
      @ApiResponse(code = 400, message = "Invalid amount input.")})
  @PutMapping("/traderId/{traderId}/deposit/amount/{amount}")
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public Account depositFunds(@PathVariable Integer traderId, @PathVariable Double amount) {

    try {
      return traderAccountService.deposit(traderId, amount);
    } catch (Exception e) {
      throw ResponseExceptionUtil.getResponseStatusException(e);
    }

  }

  @ApiOperation(value = "Withdraw funds from Trader Account by Trader Id.")
  @ApiResponses(value = {@ApiResponse(code = 404, message = "Trader not found for given Trader Id."),
      @ApiResponse(code = 400, message = "Invalid amount input.")})
  @PutMapping("/traderId/{traderId}/withdraw/amount/{amount}")
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public Account withdrawFunds(@PathVariable Integer traderId, @PathVariable Double amount) {

    try {
      return traderAccountService.withdraw(traderId, amount);
    } catch (Exception e) {
      throw ResponseExceptionUtil.getResponseStatusException(e);
    }

  }
}
