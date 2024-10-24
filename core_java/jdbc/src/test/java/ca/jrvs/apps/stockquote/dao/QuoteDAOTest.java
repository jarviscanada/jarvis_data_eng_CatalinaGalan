package ca.jrvs.apps.stockquote.dao;

import static org.junit.jupiter.api.Assertions.*;

import ca.jrvs.apps.stockquote.model.Quote;
import ca.jrvs.apps.stockquote.util.DatabaseConnectionManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class QuoteDAOTest {

  Connection connection;
  QuoteDAO quoteDAO;
  QuoteHttpHelper quoteHttpHelper;
  DatabaseConnectionManager dcm;
  Quote quote;
  String id;

  @BeforeEach
  public void init() throws SQLException {
    quoteHttpHelper = new QuoteHttpHelper();
    quote = quoteHttpHelper.fetchQuoteInfo("MSFT");
    dcm = new DatabaseConnectionManager("localhost",
        "stock_quote", "postgres", "password");
    connection = dcm.getConnection();
    quoteDAO = new QuoteDAO(connection);
    id = quote.getTicker();
  }

  @AfterEach
  public void tearDown() throws SQLException {
    if (connection != null) {
      connection.close();
    }
  }

  @Test
  void TestNewQuoteDAO() throws SQLException {
    assertNotNull(quoteDAO);
  }

  @Test
  void TestSave() {
    Quote newQuote = quoteDAO.save(quote);
    assertEquals(id, newQuote.getTicker());
  }

  @Test
  void TestFindByIdValid() {
    Optional<Quote> found = quoteDAO.findById(id);
    assertEquals(id, found.get().getTicker());
  }

  @Test
  void TestFindByIdNotValid() {
    assertEquals(Optional.empty(), quoteDAO.findById("_"));
  }

  @Test
  void TestDeleteByIdNotValid() {
    quoteDAO.deleteById("-");
    assertEquals(Optional.empty(), quoteDAO.findById("-"));
  }

  @Test
  void TestFindAll() {
    assertInstanceOf(ArrayList.class, quoteDAO.findAll());
  }

  @Test
  void TestDeleteAll() {
    quoteDAO.deleteAll();
    assertFalse(quoteDAO.findAll().iterator().hasNext());
  }

  @Test
  void TestDeleteByIdValid() {
    quoteDAO.deleteById(id);
    assertEquals(Optional.empty(), quoteDAO.findById(id));
  }
}