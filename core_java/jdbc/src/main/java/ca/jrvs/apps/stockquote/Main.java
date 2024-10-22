package ca.jrvs.apps.stockquote;

import ca.jrvs.apps.stockquote.dao.QuoteDAO;
import ca.jrvs.apps.stockquote.dao.QuoteHttpHelper;
import ca.jrvs.apps.stockquote.model.Quote;
import ca.jrvs.apps.stockquote.util.DatabaseConnectionManager;
import java.sql.Connection;
import java.sql.SQLException;

public class Main {

  public static void main(String[] args) {
//    QuoteHttpHelper quoteHttpHelper = new QuoteHttpHelper();
//
//    DatabaseConnectionManager dcm = new DatabaseConnectionManager("localhost",
//        "stock_quote", "postgres", "password");
//    try {
//      Connection connection = dcm.getConnection();
//      QuoteDAO quoteDAO = new QuoteDAO(connection);
//      Quote quote = quoteHttpHelper.fetchQuoteInfo("MSFT");
//      Quote quote2 = quoteHttpHelper.fetchQuoteInfo("MSF");
//      quoteDAO.save(quote);
//      quoteDAO.save(quote2);
//      quoteDAO.deleteAll();
//    } catch (SQLException e) {
//      throw new RuntimeException(e);
//    }
  }
}
