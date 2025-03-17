package ca.jrvs.apps.stockquote.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DatabaseConnectionManager {

  final Logger logger = LoggerFactory.getLogger(DatabaseConnectionManager.class);

  private String url;
  private Properties properties;

  public DatabaseConnectionManager(String host, String database, String username, String password) {
    this.url = "jdbc:postgresql://" + host + "/" + database;
    this.properties = new Properties();
    this.properties.setProperty("user", username);
    this.properties.setProperty("password", password);
  }

  public Connection getConnection() throws SQLException {
    Connection connection = DriverManager.getConnection(this.url, this.properties);
    logger.info("Connection created successfully");
    return connection;
  }
}
