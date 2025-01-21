package ca.jrvs.apps.trading.controller;

import ca.jrvs.apps.trading.model.AlphaQuote;
import ca.jrvs.apps.trading.service.QuoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("/quote")
public class QuoteController {

  @Autowired
  private QuoteService quoteService;

  @GetMapping("/alpha/ticker/{ticker}")
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public AlphaQuote getQuote(@PathVariable String ticker) {
    try {
      return quoteService.findAlphaQuoteByTicker(ticker);
    } catch (IllegalArgumentException | DataRetrievalFailureException e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(),
          e);
    } catch (ResponseStatusException e) {
      throw e;
    }
  }
}
