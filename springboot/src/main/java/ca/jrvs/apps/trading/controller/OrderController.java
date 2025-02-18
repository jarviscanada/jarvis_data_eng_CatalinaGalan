package ca.jrvs.apps.trading.controller;

import ca.jrvs.apps.trading.model.MarketOrder;
import ca.jrvs.apps.trading.model.SecurityOrder;
import ca.jrvs.apps.trading.service.OrderService;
import ca.jrvs.apps.trading.util.ResponseExceptionUtil;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class OrderController {

  @Autowired
  private OrderService orderService;


  @Operation(summary = "Execute a Market Order.", description = "Handle BUY or SELL stock for "
      + "Trader by Id passed in MarketOrder object. "
      + "NOTE: Due to a problem with the Swagger UI it is necessary to manually add a wrapper "
      + "{\"MarketOrder\": ... } directly to the JSON, for correct mapping.")
  @PostMapping(value = "/marketOrder")
  @ResponseStatus(HttpStatus.CREATED)
  @ResponseBody
  public SecurityOrder postMarketOrder(@RequestBody MarketOrder marketOrder) {

    try {
      return orderService.executeMarketOrder(marketOrder);
    } catch (Exception e) {
      throw ResponseExceptionUtil.getResponseStatusException(e);
    }
  }
}
