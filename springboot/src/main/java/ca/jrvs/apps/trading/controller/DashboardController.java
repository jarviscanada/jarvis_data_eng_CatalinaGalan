package ca.jrvs.apps.trading.controller;

import ca.jrvs.apps.trading.model.view.PortfolioView;
import ca.jrvs.apps.trading.model.view.TraderAccountView;
import ca.jrvs.apps.trading.service.DashboardService;
import ca.jrvs.apps.trading.util.ResponseExceptionUtil;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

  private static final Logger logger = LoggerFactory.getLogger(DashboardController.class);

  @Autowired
  private DashboardService dashboardService;


  @Operation(summary = "Get Trader profile.", description = "Fetch Trader and Account information by Id.")
  @GetMapping("/profile/traderId/{traderId}")
  @ResponseBody
  @ResponseStatus(HttpStatus.OK)
  public TraderAccountView showTraderProfile(@PathVariable Integer traderId) {

    try {
      logger.info("Profile created.");
      return dashboardService.getTraderAccountViewByTraderId(traderId);
    } catch (Exception e) {
      logger.debug(e.getMessage(), e.getCause());
      throw ResponseExceptionUtil.getResponseStatusException(e);
    }
  }


  @Operation(summary = "Get Trader portfolio.", description = "Fetch all Trader's Positions by Id.")
  @GetMapping("/portfolio/traderId/{traderId}")
  @ResponseBody
  @ResponseStatus(HttpStatus.OK)
  public PortfolioView showTraderPortfolio(@PathVariable Integer traderId) {

    try {
      return dashboardService.getProfileViewByTraderId(traderId);
    } catch (Exception e) {
      logger.debug(e.getMessage(), e.getCause());
      throw ResponseExceptionUtil.getResponseStatusException(e);
    }
  }
}
