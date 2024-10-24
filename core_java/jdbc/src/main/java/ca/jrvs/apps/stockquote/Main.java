package ca.jrvs.apps.stockquote;

import ca.jrvs.apps.stockquote.dao.QuoteDAO;
import ca.jrvs.apps.stockquote.dao.QuoteHttpHelper;
import ca.jrvs.apps.stockquote.model.Quote;
import ca.jrvs.apps.stockquote.util.DatabaseConnectionManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Main {
//
//  public static void main(String[] args) {
//    QuoteHttpHelper quoteHttpHelper = new QuoteHttpHelper();
//
//    DatabaseConnectionManager dcm = new DatabaseConnectionManager("localhost",
//        "stock_quote", "postgres", "password");
//    try {
//      Connection connection = dcm.getConnection();
//      QuoteDAO quoteDAO = new QuoteDAO(connection);
////      Quote quote = quoteHttpHelper.fetchQuoteInfo("MSFT");
////      Quote quote2 = quoteHttpHelper.fetchQuoteInfo("MSF");
////
////      Quote update = quoteDAO.save(quote);
////      Quote update2 = quoteDAO.save(quote2);
////      System.out.println(update);
////      System.out.println(update2);
////      Quote found = quoteDAO.findById("MSF").get();
////      System.out.println(found);
//        Iterable<Quote> all = quoteDAO.findAll();
//        for (Quote q : all) {
//          System.out.println(q);
//        }
//
//
//    } catch (SQLException e) {
//      throw new RuntimeException(e);
//    }
//  }
}
