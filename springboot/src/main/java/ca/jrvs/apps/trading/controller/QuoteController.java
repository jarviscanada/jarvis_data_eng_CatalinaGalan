package ca.jrvs.apps.trading.controller;

import ca.jrvs.apps.trading.model.AlphaQuote;
import ca.jrvs.apps.trading.model.Quote;
import ca.jrvs.apps.trading.service.QuoteService;
import ca.jrvs.apps.trading.util.ResponseExceptionUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Api(value = "quote", produces = MediaType.APPLICATION_JSON_VALUE)
@Controller
@RequestMapping("/quote")
public class QuoteController {

  @Autowired
  private QuoteService quoteService;

  @ApiOperation(value = "Show AlphaQuote .", notes = "Show AlphaQuote for a given ticker/symbol.")
  @ApiResponses(value =
      {@ApiResponse(code =404, message = "Ticker is not found.")})
  @GetMapping("/alpha/ticker/{ticker}")
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public AlphaQuote getQuote(@PathVariable String ticker) {

    try {
      return quoteService.findAlphaQuoteByTicker(ticker);
    } catch (Exception e) {
      throw ResponseExceptionUtil.getResponseStatusException(e);
    }

  }

  @ApiOperation(value = "Update a Quote using Alpha Vantage data.",
      notes = "Use Alpha Vantage trading API as market data.")
  @PutMapping("/update/{ticker}")
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public Quote updateQuoteMarketData(@PathVariable String ticker) {

    try {
      return quoteService.updateMarketDataQuote(ticker);
    } catch (Exception e) {
      throw ResponseExceptionUtil.getResponseStatusException(e);
    }

  }

  @ApiOperation(value = "Save a new Quote to Daily List fromAlpha Vantage data.",
      notes = "Use Alpha Vantage trading API as market data.")
  @PostMapping("/tickerId/{ticker}")
  @ResponseStatus(HttpStatus.CREATED)
  @ResponseBody
  public Quote createNewQuote(@PathVariable String ticker) {

    try {
      Quote newQuote = quoteService.saveQuote(ticker);
      return quoteService.saveQuote(newQuote);
    } catch (Exception e) {
      throw ResponseExceptionUtil.getResponseStatusException(e);
    }

  }

  @ApiOperation(value = "Show all Quotes saved into Daily List.")
  @ApiResponses(value = {@ApiResponse(code = 404, message = "No Quotes found in database.")})
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
