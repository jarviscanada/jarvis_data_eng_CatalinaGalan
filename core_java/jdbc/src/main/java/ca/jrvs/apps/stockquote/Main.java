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
import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

  public static void main(String[] args) {

    PropertyConfigurator.configure("src/main/resources/log4j.properties");
    final Logger logger = LoggerFactory.getLogger(Main.class);

    Map<String, String> properties = new HashMap<>();
    BufferedReader bufferedReader;
    String apiKey = System.getenv("X_RAPID_API_KEY");
    if (apiKey == null) {
      Dotenv dotenv = Dotenv.load();
      apiKey = dotenv.get("X_RAPID_API_KEY");
    }

    try {
      bufferedReader = new BufferedReader(
          new FileReader("src/main/resources/properties.txt"));
          String line;
          while ((line = bufferedReader.readLine()) != null) {
            String[] tokens = line.split(":");
            properties.put(tokens[0], tokens[1]);
          }
    } catch (FileNotFoundException e) {
      logger.error(e.getMessage(), e);
//      throw new IllegalArgumentException(e);
    } catch (IOException e) {
      logger.error(e.getMessage(), e);
//      throw new RuntimeException(e);
    }

    DatabaseConnectionManager dcm = new DatabaseConnectionManager(properties.get("server"),
        properties.get("database"), properties.get("username"), properties.get("password"));
    try {
      Connection connection = dcm.getConnection();
      QuoteDAO quoteDAO = new QuoteDAO(connection);
      PositionDAO positionDAO = new PositionDAO(connection);
      OkHttpClient client = new OkHttpClient();
      QuoteHttpHelper quoteHttpHelper = new QuoteHttpHelper(apiKey, client);
      QuoteService quoteService = new QuoteService(quoteDAO, quoteHttpHelper);
      PositionService positionService = new PositionService(positionDAO, quoteService);
      StockQuoteController controller = new StockQuoteController(quoteService, positionService);
      controller.initClient();
    } catch (SQLException e) {
      logger.error(e.getMessage(), e);
//      throw new RuntimeException(e);
    }

  }
}
