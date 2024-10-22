package ca.jrvs.apps.stockquote.dao;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import ca.jrvs.apps.stockquote.model.Quote;
import ca.jrvs.apps.stockquote.util.DatabaseConnectionManager;
import java.sql.Connection;
import java.sql.SQLException;
import org.junit.jupiter.api.AfterEach;
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
  String id;
  Connection connection;
  QuoteHttpHelper quoteHttpHelper = new QuoteHttpHelper();
  DatabaseConnectionManager dcm = new DatabaseConnectionManager("localhost",
    "stock_quote", "postgres", "password");

  @BeforeEach
  public void init() {
    quote = quoteHttpHelper.fetchQuoteInfo("MSFT");
    id = quote.getTicker();
    try {
      connection = dcm.getConnection();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @AfterEach
  public void closeConnection() throws SQLException {
    if (connection != null) {
      connection.close();
    }
  }

  @Test
  void TestNewQuoteDAO() throws SQLException {
    QuoteDAO quoteDAO = new QuoteDAO(connection);
    assertNotNull(quoteDAO);
  }

  @Test
  void TestSave() {
    quoteDAOMock.save(quote);
    verify(quoteDAOMock, times(1)).save(quote);
  }

  @Test
  void TestFindById() {
    assertNotNull(quoteDAOMock.findById(id));
  }

  @Test
  void TestDeleteById() {
    quoteDAOMock.deleteById(id);
    assert(quoteDAOMock.findById(id).isEmpty());
  }

//  @Test
//  void TestDeleteAll() {
//
//  }
}