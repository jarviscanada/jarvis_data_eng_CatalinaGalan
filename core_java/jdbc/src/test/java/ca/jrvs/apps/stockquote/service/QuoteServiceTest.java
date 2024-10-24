package ca.jrvs.apps.stockquote.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ca.jrvs.apps.stockquote.dao.QuoteDAO;
import ca.jrvs.apps.stockquote.dao.QuoteHttpHelper;
import ca.jrvs.apps.stockquote.model.Quote;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class QuoteServiceTest {

  private String validTicker;
  private String invalidTicker;
  private Quote quote;
  private QuoteHttpHelper quoteHttpHelper;

  @Mock
  private QuoteDAO mockQuoteDAO;

  @BeforeEach
  void init() {
    quoteHttpHelper = new QuoteHttpHelper();
  }

  @Test
  void TestFetchQuoteDataFromApiValidTicket() {
    validTicker = "MSFT";
    quote = quoteHttpHelper.fetchQuoteInfo(validTicker);
    assertEquals(validTicker, quote.getTicker());
  }

  @Test
  void TestFetchQuoteDataFromApiInvalidTicker() {
    invalidTicker = " ";
    quote = quoteHttpHelper.fetchQuoteInfo(invalidTicker);
    assert ((Optional.of(quote).isEmpty()));
  }
}