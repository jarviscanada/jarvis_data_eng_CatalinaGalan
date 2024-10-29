package ca.jrvs.apps.stockquote.controller;

import ca.jrvs.apps.stockquote.service.PositionService;
import ca.jrvs.apps.stockquote.service.QuoteService;

public class StockQuoteController {

  private QuoteService quoteService;
  private PositionService positionService;

  public StockQuoteController(QuoteService quoteService, PositionService positionService) {
    this.quoteService = quoteService;
    this.positionService = positionService;
  }

  public void initClient() {
    System.out.println("\n -------- Welcome to the Stock-Quote App --------");
    System.out.println("\n Please choose one of the following options \n");

  }
}
