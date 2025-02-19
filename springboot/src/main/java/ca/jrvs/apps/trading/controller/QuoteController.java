package ca.jrvs.apps.trading.controller;

import ca.jrvs.apps.trading.model.AlphaQuote;
import ca.jrvs.apps.trading.model.Quote;
import ca.jrvs.apps.trading.service.QuoteService;
import ca.jrvs.apps.trading.util.ResponseExceptionUtil;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping("/quote")
public class QuoteController {

  @Autowired
  private QuoteService quoteService;


  @Operation(summary = "Get a quote from Alpha Vantage Api.",
      description = "Fetch a quote by its ticker/symbol.")
  @GetMapping("/alphaVantage/ticker/{ticker}")
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public AlphaQuote getQuote(@PathVariable String ticker) {
    try {
      return quoteService.findAlphaQuoteByTicker(ticker);
    } catch (Exception e) {
      throw ResponseExceptionUtil.getResponseStatusException(e);
    }
  }


  @Operation(summary = "Update Daily List",
      description = "Update all quotes in Daily List with data from Alpha Vantage Api.")
  @PutMapping("/alphaVantageMarketData")
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public void UpdateMarketData() {
    try {
      quoteService.updateMarketData();
    } catch (Exception e) {
      throw ResponseExceptionUtil.getResponseStatusException(e);
    }
  }


  @Operation(summary = "Update a quote.",
      description = "For testing purposes only, update a quote object. "
          + "NOTE: Due to a problem with the Swagger UI it is necessary to manually add a wrapper "
          + "{\"Quote\": ... } directly to the JSON, for correct mapping.")
  @PutMapping("/")
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public Quote putQuote(@RequestBody Quote quote) {
    try {
      return quoteService.saveQuote(quote);
    } catch (Exception e) {
      throw ResponseExceptionUtil.getResponseStatusException(e);
    }
  }


  @Operation(summary = "Create a new Quote.",
      description = "Add a new Quote to the Daily List. Fetch quote data from Api by its ticker.")
  @PostMapping("/tickerId/{ticker}")
  @ResponseStatus(HttpStatus.CREATED)
  @ResponseBody
  public Quote createNewQuote(@PathVariable String ticker) {
    try {
//      Quote quote = quoteService.saveQuote(ticker);
      return quoteService.saveQuote(ticker);
    } catch (Exception e) {
      throw ResponseExceptionUtil.getResponseStatusException(e);
    }
  }


  @Operation(summary = "Get all Quotes.", description = "Fetch all Quotes from database.")
  @GetMapping("/dailyList")
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public List<Quote> getDailyList() {
    try {
      return quoteService.findAllQuotes();
    } catch (Exception e) {
      throw ResponseExceptionUtil.getResponseStatusException(e);
    }
  }
}
