package ca.jrvs.apps.stockquote.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import ca.jrvs.apps.stockquote.dao.QuoteDAO;
import ca.jrvs.apps.stockquote.dao.QuoteHttpHelper;
import ca.jrvs.apps.stockquote.model.Quote;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class QuoteServiceUnitTest {

  @Mock
  QuoteHttpHelper mockQuoteHttpHelper;

  @Mock
  QuoteDAO mockQuoteDAO;

  private Quote quote;
  private String validTicker;
  private String invalidTicker;
  private QuoteService quoteService;

  @BeforeEach
  void init() {
    quoteService = new QuoteService(mockQuoteDAO, mockQuoteHttpHelper);
  }

  @Test
  void Test_quoteServiceFetchDataFromApiValidId() {
    validTicker = "MSFT";
    quote = new Quote();
    quote.setTicker("MSFT");
    quote.setOpen(332.38);
    quote.setHigh(333.83);
    quote.setPrice(327.73);
    quote.setVolume(21085695);
    quote.setLatestTradingDay(Date.valueOf(LocalDate.now()));
    quote.setPreviousClose(331.16);
    quote.setChange(-3.43);
    quote.setChangePercent("-1.0358%");
    quote.setTimestamp(Timestamp.from(Instant.now()));

    when(mockQuoteHttpHelper.fetchQuoteInfo(validTicker)).thenReturn(quote);
    when(mockQuoteDAO.save(any(Quote.class))).thenReturn(quote);
    when(mockQuoteDAO.findById(validTicker)).thenReturn(Optional.of(quote));

    Optional<Quote> optQuote = quoteService.fetchQuoteDataFromAPI(validTicker);

    assertTrue(optQuote.isPresent());
    assertEquals(validTicker, optQuote.get().getTicker());
  }

  @Test
  void Test_quoteServiceFetchDataFromApiInvalidId() {
    invalidTicker = " ";
    quote = new Quote();
    quote.setTicker(null);
    quote.setOpen(0.0);
    quote.setHigh(0.0);
    quote.setPrice(0.0);
    quote.setVolume(0);
    quote.setLatestTradingDay(null);
    quote.setPreviousClose(0.0);
    quote.setChange(0.0);
    quote.setChangePercent(null);
    quote.setTimestamp(null);

    when(mockQuoteHttpHelper.fetchQuoteInfo(invalidTicker)).thenReturn(quote);
    when(mockQuoteDAO.save(any(Quote.class))).thenReturn(quote);

    Optional<Quote> result = quoteService.fetchQuoteDataFromAPI(invalidTicker);

    assertTrue(result.isEmpty());
  }
}