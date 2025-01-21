package ca.jrvs.apps.trading.controller;

import ca.jrvs.apps.trading.model.AlphaQuote;
import ca.jrvs.apps.trading.service.QuoteService;
import ca.jrvs.apps.trading.util.MarketDataHttpHelper;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
public class AppController {

//  @Autowired
//  private MarketDataHttpHelper marketDataHttpHelper;

//  @Autowired
//  private QuoteService quoteService;

  @GetMapping("/")
  public String greeting(){
    return "Welcome to the Trading App";
  }

//  @GetMapping("/apiDemo")
//  public AlphaQuote apiDemo() throws IOException {
//    return quoteService.findAlphaQuoteByTicker("IBM");
//  }
}
