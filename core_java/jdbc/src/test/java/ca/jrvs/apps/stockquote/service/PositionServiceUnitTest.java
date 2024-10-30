package ca.jrvs.apps.stockquote.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import ca.jrvs.apps.stockquote.dao.PositionDAO;
import ca.jrvs.apps.stockquote.model.Position;
import ca.jrvs.apps.stockquote.model.Quote;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PositionServiceUnitTest {

  @Mock
  PositionDAO mockPositionDAO;

  @Mock
  QuoteService mockQuoteService;

  private PositionService positionService;
  private Quote quote;
  private String invalidTicker;
  private String validTicker;
  private int invalidNumOfShares;
  private int validNumOfShares;
  private double invalidPrice;
  private double validPrice;

  @BeforeEach
  void init(){
    positionService = new PositionService(mockPositionDAO, mockQuoteService);
    validTicker = "MSFT";
    validNumOfShares = 3000;
    validPrice = 327.73;

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
  }

  @Test
  void Test_positionServiceBuyInvalidTicker() throws SQLException {
    invalidTicker = " ";

    when(mockQuoteService.fetchQuoteDataFromAPI(invalidTicker)).thenReturn(Optional.empty());
    Position result = positionService.buy(invalidTicker, validNumOfShares, validPrice);

    assertNull(result);
  }

  @Test
  void Test_positionServiceBuyInvalidNumOfShares() throws SQLException {
    invalidNumOfShares = 21085695 + 1;

    when(mockQuoteService.fetchQuoteDataFromAPI(validTicker)).thenReturn(Optional.of(quote));

    Position result = positionService.buy(validTicker, invalidNumOfShares, validPrice);
    assertNull(result);
  }

  @Test
  void Test_positionServiceBuyInvalidPrice() throws SQLException {
    invalidPrice = 325.76;

    when(mockQuoteService.fetchQuoteDataFromAPI(validTicker)).thenReturn(Optional.of(quote));
    Position result = positionService.buy(validTicker, validNumOfShares, invalidPrice);

    assertNull(result);
  }

  @Test
  void Test_positionServiceBuyValidInputs() throws SQLException {
    Position position = new Position();
    position.setTicker(validTicker);
    position.setNumOfShares(validNumOfShares);
    position.setValuePaid(validPrice * validNumOfShares);

    when(mockQuoteService.fetchQuoteDataFromAPI(validTicker)).thenReturn(Optional.of(quote));
    when(mockPositionDAO.save(any(Position.class))).thenReturn(position);

    Position result = positionService.buy(validTicker, validNumOfShares, validPrice);

    assertEquals(validTicker, result.getTicker());
    assertEquals(validNumOfShares, result.getNumOfShares());
    assertEquals(validPrice * validNumOfShares, result.getValuePaid());
  }

  @Test
  void Test_listAllEmpty() {
    Iterable<Position> allPositions = new ArrayList<>();
    when(mockPositionDAO.findAll()).thenReturn(allPositions);
    Iterable<Position> result = positionService.listAll();

    assertNull(result);
  }

  @Test
  void Test_listAll() {
    List<Position> allPositions = new ArrayList<>();
    Position position = new Position();
    position.setTicker(validTicker);
    position.setNumOfShares(validNumOfShares);
    position.setValuePaid(validPrice * validNumOfShares);
    allPositions.add(position);

    when(mockPositionDAO.findAll()).thenReturn(allPositions);
    Iterable<Position> result = positionService.listAll();

    assertTrue(result.iterator().hasNext());
  }
}