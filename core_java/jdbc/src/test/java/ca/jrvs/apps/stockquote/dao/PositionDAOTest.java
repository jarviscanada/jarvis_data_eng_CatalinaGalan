package ca.jrvs.apps.stockquote.dao;

import static org.junit.jupiter.api.Assertions.*;

import ca.jrvs.apps.stockquote.model.Position;
import ca.jrvs.apps.stockquote.util.DatabaseConnectionManager;
import java.sql.Connection;
import java.sql.SQLException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

class PositionDAOTest {

  Position position;
  PositionDAO positionDAO;
  String id;
  DatabaseConnectionManager dcm;
  Connection connection;

  @BeforeEach
  void setUp() throws SQLException {
    dcm = new DatabaseConnectionManager("localhost", "stock_quote", "postgres",
        "password");
    connection = dcm.getConnection();
    position = new Position();
    id = position.getTicker();
    positionDAO = new PositionDAO(connection);
  }

  @AfterEach
  void tearDown() throws SQLException {
    if (connection != null) {
      connection.close();
    }
  }
}