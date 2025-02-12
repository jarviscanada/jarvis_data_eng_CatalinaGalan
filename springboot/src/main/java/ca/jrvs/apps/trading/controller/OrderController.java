package ca.jrvs.apps.trading.controller;

import ca.jrvs.apps.trading.model.MarketOrder;
import ca.jrvs.apps.trading.model.SecurityOrder;
import ca.jrvs.apps.trading.service.OrderService;
import ca.jrvs.apps.trading.util.ResponseExceptionUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Api(value = "order", produces = MediaType.APPLICATION_JSON_VALUE)
@Controller
@RequestMapping("/order")
public class OrderController {

  @Autowired
  private OrderService orderService;

  @ApiOperation(value = "Submit a market order.", notes = "Submit a market order.")
  @ApiResponses(value =
      {@ApiResponse(code =404, message = "TraderId or Ticker is not found."),
      @ApiResponse(code = 400, message = "Unable to deposit due to user input.")})
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
