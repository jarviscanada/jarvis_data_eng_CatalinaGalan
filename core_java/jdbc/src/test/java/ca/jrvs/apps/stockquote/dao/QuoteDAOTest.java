package ca.jrvs.apps.stockquote.dao;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import ca.jrvs.apps.stockquote.model.Quote;
import ca.jrvs.apps.stockquote.util.DatabaseConnectionManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class QuoteDAOTest {

  @Mock
  QuoteDAO quoteDAOMock;

  Quote quote;
  Connection connection;
  QuoteHttpHelper quoteHttpHelper = new QuoteHttpHelper();
  DatabaseConnectionManager dcm = new DatabaseConnectionManager("localhost",
    "stock_quote", "postgres", "password");

  @BeforeEach
  public void init() {
    quote = quoteHttpHelper.fetchQuoteInfo("MSFT");
    try {
      connection = dcm.getConnection();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  void Testsave() {
    when(quoteDAOMock.save(quote)).thenReturn(null);
    assertNull(quoteDAOMock.save(quote));
  }

  @Test
  void TestfindById() {
    when(quoteDAOMock.findById("MSFT")).thenReturn(Optional.ofNullable(quote));
    assertEquals(quoteDAOMock.findById("MSFT"), Optional.ofNullable(quote));
  }

  @Test
  void TestDelete() {

  }

//      Quote quote = quoteHttpHelper.fetchQuoteInfo("MSFT");
//      Quote quote2 = quoteHttpHelper.fetchQuoteInfo("MSF");
//      quoteDAO.save(quote);
//      quoteDAO.save(quote2);
//    quoteDAO.deleteAll();
}