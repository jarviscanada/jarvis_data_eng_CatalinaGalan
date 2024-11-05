package ca.jrvs.apps.stockquote.dao;

import static org.junit.jupiter.api.Assertions.*;

import ca.jrvs.apps.stockquote.model.Quote;
import ca.jrvs.apps.stockquote.util.DatabaseConnectionManager;
import ca.jrvs.apps.stockquote.util.JsonParser;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Optional;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class QuoteDAOTest {

  private static Connection connection;
  static QuoteDAO quoteDAO;
  DatabaseConnectionManager dcm;
  Quote quote;
  String id;
  String jsonString = "{\n"
      + "  \"Global Quote\": {\n"
      + "    \"01. symbol\": \"MSFT\",\n"
      + "    \"02. open\": \"332.3800\",\n"
      + "    \"03. high\": \"333.8300\",\n"
      + "    \"04. low\": \"326.3600\",\n"
      + "    \"05. price\": \"327.7300\",\n"
      + "    \"06. volume\": \"21085695\",\n"
      + "    \"07. latest trading day\": \"2023-10-13\",\n"
      + "    \"08. previous close\": \"331.1600\",\n"
      + "    \"09. change\": \"-3.4300\",\n"
      + "    \"10. change percent\": \"-1.0358%\"\n"
      + "  }\n"
      + "}";

  @BeforeEach
  public void init() throws SQLException, IOException {
    System.out.println("Init...");
    quote = JsonParser.toObjectFromJson(jsonString, Quote.class);
    quote.setTimestamp(Timestamp.from(Instant.now()));
    dcm = new DatabaseConnectionManager("localhost",
        "stock_quote", "postgres", "password");
    connection = dcm.getConnection();
    quoteDAO = new QuoteDAO(connection);
    id = quote.getTicker();
    Quote quote1 = quoteDAO.save(quote);
  }

  @AfterAll
  public static void closeConnection() throws SQLException {
    quoteDAO.findAll();
    if (connection != null) {
      connection.close();
      System.out.println("CONNECTION CLOSED");
    }
  }

  @Test
  void TestSave() {
    System.out.println("Testing Save");
    Quote newQuote = quoteDAO.save(quote);
    assertEquals(id, newQuote.getTicker());
  }

  @Test
  void TestFindByValidId() {
    System.out.println("Testing FindByValidId");
    Optional<Quote> found = quoteDAO.findById(id);
    assertEquals(id, found.get().getTicker());
  }

  @Test
  void TestFindByNotValidId() {
    System.out.println("Testing FindByNotValidId");
    assertEquals(Optional.empty(), quoteDAO.findById("_"));
  }

  @Test
  void TestFindAll() {
    System.out.println("Testing FindAll");
    assertInstanceOf(ArrayList.class, quoteDAO.findAll());
  }

  @Test
  void TestDeleteByNotValidId() {
    System.out.println("Testing DeleteByNotValidId");
    assertThrows(IllegalArgumentException.class, () -> quoteDAO.deleteById("_"),
        ("There is no quote record for the given symbol"));
  }

  @Test
  void TestDeleteByValidId() {
    System.out.println("Testing DeleteByValidId");
    try {
      quoteDAO.deleteById(id);
      assertEquals(Optional.empty(), quoteDAO.findById(id));
    } catch (IllegalArgumentException e) {
      assertNotEquals(Optional.empty(), quoteDAO.findById(id));
    }
  }

  @Test
  void TestDeleteAll() {
    System.out.println("Testing DeleteAll");
    quoteDAO.deleteAll();
    assertFalse(quoteDAO.findAll().iterator().hasNext());
  }
}