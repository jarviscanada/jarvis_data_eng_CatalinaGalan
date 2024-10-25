package ca.jrvs.apps.stockquote.dao;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import ca.jrvs.apps.stockquote.model.Position;
import ca.jrvs.apps.stockquote.model.Quote;
import ca.jrvs.apps.stockquote.util.DatabaseConnectionManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PositionDAOTest {

  static Connection connection;
  Position position;
  PositionDAO positionDAO;
  String id;
  DatabaseConnectionManager dcm;

  @Mock
  Quote mockQuote;

  @BeforeEach
  void setUp() throws SQLException {
    dcm = new DatabaseConnectionManager("localhost", "stock_quote", "postgres",
        "password");
    connection = dcm.getConnection();
    positionDAO = new PositionDAO(connection);
    when(mockQuote.getPrice()).thenReturn(327.73);
    when(mockQuote.getTicker()).thenReturn("MSFT");
    position = positionDAO.create(5695, mockQuote);
    id = position.getTicker();
    Position position1 = positionDAO.save(position);
  }

  @AfterAll
  static void tearDown() throws SQLException {
    if (connection != null) {
      connection.close();
    }
  }

  @Test
  void TestSave() {
    System.out.println("Testing position save");
    Position newPosition = positionDAO.save(position);
    System.out.println(position);
    assertEquals(id, newPosition.getTicker());
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
    positionDAO.deleteById("-");
    assertEquals(Optional.empty(), positionDAO.findById("-"));
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