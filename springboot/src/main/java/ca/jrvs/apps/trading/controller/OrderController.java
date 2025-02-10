package ca.jrvs.apps.trading.controller;

import ca.jrvs.apps.trading.model.MarketOrder;
import ca.jrvs.apps.trading.model.SecurityOrder;
import ca.jrvs.apps.trading.service.OrderService;
import ca.jrvs.apps.trading.util.ResponseExceptionUtil;
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

  @PostMapping("/marketOrder")
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
