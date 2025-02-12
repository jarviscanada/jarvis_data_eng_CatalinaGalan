package ca.jrvs.apps.trading.controller;

import ca.jrvs.apps.trading.model.view.PortfolioView;
import ca.jrvs.apps.trading.model.view.TraderAccountView;
import ca.jrvs.apps.trading.service.DashboardService;
import ca.jrvs.apps.trading.util.ResponseExceptionUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Api(value = "dashboard", produces = MediaType.APPLICATION_JSON_VALUE)
@Controller
@RequestMapping("/dashboard")
public class DashboardController {

  @Autowired
  private DashboardService dashboardService;

  @ApiOperation(value = "Show Trader profile by TraderId.", notes = "Show Trader and Account details.")
  @ApiResponses(value =
      {@ApiResponse(code =404, message = "TraderId or Ticker is not found.")})
  @GetMapping("/profile/traderId/{traderId}")
  @ResponseBody
  @ResponseStatus(HttpStatus.OK)
  public TraderAccountView showTraderProfile(@PathVariable Integer traderId) {

    try {
      return dashboardService.getTraderAccountViewByTraderId(traderId);
    } catch (Exception e) {
      throw ResponseExceptionUtil.getResponseStatusException(e);
    }

  }

  @ApiOperation(value = "Show Trader Portfolio by TraderId.")
  @ApiResponses(value =
      {@ApiResponse(code =404, message = "TraderId not found.")})
  @GetMapping("/portfolio/traderId/{traderId}")
  @ResponseBody
  @ResponseStatus(HttpStatus.OK)
  public PortfolioView showTraderPortfolio(@PathVariable Integer traderId) {

    try {
      return dashboardService.getProfileViewByTraderId(traderId);
    } catch (Exception e) {
      throw ResponseExceptionUtil.getResponseStatusException(e);
    }

  }
}
