package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.repository.QuoteRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class QuoteService {

  @Autowired
  private QuoteRepository quoteRepository;

}
