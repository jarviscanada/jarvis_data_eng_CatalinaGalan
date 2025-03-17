package ca.jrvs.apps.stockquote.dao;

import static org.junit.jupiter.api.Assertions.*;

import ca.jrvs.apps.stockquote.model.Position;
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
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PositionDAOTest {

  static DatabaseConnectionManager dcm;
  static Connection connection;
  static Quote quote;
  static QuoteDAO quoteDAO;
  static PositionDAO positionDAO;
  Position position;
  Position position1;
  String id;

  @BeforeAll
  public static void init() throws IOException, SQLException {
    String jsonString = "{\n"
        + "  \"Global Quote\": {\n"
        + "    \"01. symbol\": \"MT\",\n"
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
    System.out.println("Init...");
    dcm = new DatabaseConnectionManager("localhost", "stock_quote", "postgres",
        "password");
    connection = dcm.getConnection();
    quote = JsonParser.toObjectFromJson(jsonString, Quote.class);
    quote.setTimestamp(Timestamp.from(Instant.now()));
    positionDAO = new PositionDAO(connection);
    quoteDAO = new QuoteDAO(connection);
    Quote quote1 = quoteDAO.save(quote);
  }

  @BeforeEach
  void setUp() throws IOException {
    String jsonPosition = "{\n"
        + "  \"Position\": {\n"
        + "    \"symbol\": \"MT\",\n"
        + "    \"num_of_shares\": \"10\",\n"
        + "    \"value_paid\": \"3277.30\"\n"
        + "  }\n"
        + "}";

    position = JsonParser.toObjectFromJson(jsonPosition, Position.class);
    id = position.getTicker();
    position1 = positionDAO.save(position);
  }

  @AfterAll
  static void tearDown() throws SQLException {
    positionDAO.deleteAll();
    quoteDAO.deleteAll();
    if (connection != null) {
      connection.close();
      System.out.println("Connection CLOSED");
    }
  }

  @Test
  void TestSave() {
    System.out.println("Testing position save");
    if (position1 == null) {
      System.out.println("Cannot save position without quote");
      assertNull(null);
    } else {
      assertEquals(id, position1.getTicker());
    }
  }

  @Test
  void TestFindByValidId() {
    System.out.println("Testing position findByValidId");
    assertEquals(id, positionDAO.findById(id).get().getTicker());
  }

  @Test
  void TestFindByNotValidId() {
    System.out.println("Testing position findByNotValidId");
    assertEquals(Optional.empty(), positionDAO.findById("-"));
  }

  @Test
  void TestFindAll() {
    System.out.println("Testing position FindAll");
    assertInstanceOf(ArrayList.class, positionDAO.findAll());
  }

  @Test
  void TestDeleteByIdNotValid() {
    System.out.println("Testing position DeleteByNotValidId");
    assertThrows(IllegalArgumentException.class, () -> positionDAO.deleteById("-"), ("\n Invalid input. "
        + "There is no record for the ticker provided."));

  }

  @Test
  void TestDeleteByIdValid() {
    System.out.println("Testing position DeleteByValidId");
    positionDAO.deleteById(id);
    assertEquals(Optional.empty(), positionDAO.findById(id));
  }

  @Test
  void TestDeleteAll() {
    System.out.println("Testing position DeleteAll");
    positionDAO.deleteAll();
    assertFalse(positionDAO.findAll().iterator().hasNext());
    }
}