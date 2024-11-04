package ca.jrvs.apps.stockquote;

import ca.jrvs.apps.stockquote.controller.StockQuoteController;
import ca.jrvs.apps.stockquote.dao.PositionDAO;
import ca.jrvs.apps.stockquote.dao.QuoteDAO;
import ca.jrvs.apps.stockquote.dao.QuoteHttpHelper;
import ca.jrvs.apps.stockquote.service.PositionService;
import ca.jrvs.apps.stockquote.service.QuoteService;
import ca.jrvs.apps.stockquote.util.DatabaseConnectionManager;
import io.github.cdimascio.dotenv.Dotenv;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import okhttp3.OkHttpClient;

public class Main {

  public static void main(String[] args) {

    Map<String, String> properties = new HashMap<>();
    BufferedReader bufferedReader;

    try {
      bufferedReader = new BufferedReader(
          new FileReader("src/main/resources/properties.txt"));
          String line;
          while ((line = bufferedReader.readLine()) != null) {
            String[] tokens = line.split(":");
            properties.put(tokens[0], tokens[1]);
          }
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    DatabaseConnectionManager dcm = new DatabaseConnectionManager(properties.get("server"),
        properties.get("database"), properties.get("username"), properties.get("password"));
    try {
      Connection connection = dcm.getConnection();
      QuoteDAO quoteDAO = new QuoteDAO(connection);
      PositionDAO positionDAO = new PositionDAO(connection);
      OkHttpClient client = new OkHttpClient();
      Dotenv dotenv = Dotenv.load();
      QuoteHttpHelper quoteHttpHelper = new QuoteHttpHelper(dotenv.get("X_RAPID_API_KEY"), client);
      QuoteService quoteService = new QuoteService(quoteDAO, quoteHttpHelper);
      PositionService positionService = new PositionService(positionDAO, quoteService);
      StockQuoteController controller = new StockQuoteController(quoteService, positionService);
      controller.initClient();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

  }
}
