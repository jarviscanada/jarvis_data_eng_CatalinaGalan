package ca.jrvs.apps.stockquote;

import ca.jrvs.apps.jdbc.DatabaseConnectionManager;
import ca.jrvs.apps.stockquote.dao.QuoteDAO;
import java.sql.Connection;
import java.sql.SQLException;

public class Main {

  public static void main(String[] args) {
    DatabaseConnectionManager dcm = new DatabaseConnectionManager(
        "localhost", "stock_quote", "postgres", "password");
    try {
      Connection connection = dcm.getConnection();
      QuoteDAO quoteDAO = new QuoteDAO(connection);


    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
}
