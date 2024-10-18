package ca.jrvs.apps.stockquote.dao;

import ca.jrvs.apps.stockquote.model.Quote;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class QuoteDAO implements CrudDAO<Quote, String>{

  private Connection connection;

  public QuoteDAO(Connection connection) {
    this.connection = connection;
  }

  private static final String INSERT = "INSERT INTO quote (symbol, open, high, low, price, volume, "
      + "latest_trading_day, previous_close, change, change_percent) VALUES (?, ?, ?, ?, ?, ?, ?, "
      + "?, ?, ?) ON CONFLICT (symbol) DO UPDATE SET symbol = ?, open = ?, high = ?, low = ?, price = ?, "
      + "volume = ?, latest_trading_day = ?, previous_close = ?, change = ?, change_percent = ?";
//  private static final String UPDATE = "UPDATE quote SET open = ?, high = ?, low = ?, "
//      + "price = ?, volume = ?, latest_trading_day = ?, previous_close = ?, change = ?, change_percent = ?"
//      + " WHERE symbol = ?";
  private static final String GET_ONE = "SELECT symbol, open, high, low, price, volume, latest_trading_day, "
      + "previous_close, change, change_percent FROM quote WHERE symbol = ?";
  private static final String DELETE = "DELETE FROM quote WHERE symbol = ?";
  private static final String DELETE_ALL = "TRUNCATE quote";

  @Override
  public Quote save(Quote entity) throws IllegalArgumentException {
    try (PreparedStatement statement = this.connection.prepareStatement(INSERT)) {
      statement.setString(1, entity.getTicker());
      statement.setDouble(2, entity.getOpen());
      statement.setDouble(3, entity.getHigh());
      statement.setDouble(4, entity.getLow());
      statement.setDouble(5, entity.getPrice());
      statement.setInt(6, entity.getVolume());
      statement.setDate(7, (Date) entity.getLatestTradingDay());
      statement.setDouble(8, entity.getPreviousClose());
      statement.setDouble(9, entity.getChange());
      statement.setString(10, entity.getChangePercent());
      statement.execute();
      String ticker = entity.getTicker();
      return this.findById(ticker).orElseThrow();
    } catch (SQLException e) {
      throw new IllegalArgumentException(e);
    }
  }

  @Override
  public Optional<Quote> findById(String s) throws IllegalArgumentException {
    Quote quote = new Quote();
    try (PreparedStatement statement = this.connection.prepareStatement(GET_ONE)) {
      statement.setString(1, s);
      ResultSet resultSet = statement.executeQuery();
      while(resultSet.next()) {
        quote.setTicker(resultSet.getString("ticker"));
        quote.setOpen(resultSet.getDouble("open"));
        quote.setHigh(resultSet.getDouble("high"));
        quote.setLow(resultSet.getDouble("low"));
        quote.setPrice(resultSet.getDouble("price"));
        quote.setVolume(resultSet.getInt("volume"));
        quote.setLatestTradingDay(resultSet.getDate("latest_trading_day"));
        quote.setPreviousClose(resultSet.getDouble("previous_close"));
        quote.setChange(resultSet.getDouble("change"));
        quote.setChangePercent(resultSet.getString("change_percent"));
      }
      return Optional.of(quote);
    } catch (SQLException e ) {
      throw new IllegalArgumentException(e);
    }
  }

  @Override
  public Iterable<Quote> findAll() {
    return null;
  }

  @Override
  public void deleteById(String s) throws IllegalArgumentException {

  }

  @Override
  public void deleteAll() {

  }
}
