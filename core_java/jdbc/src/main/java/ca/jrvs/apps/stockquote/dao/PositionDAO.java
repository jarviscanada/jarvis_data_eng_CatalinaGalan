package ca.jrvs.apps.stockquote.dao;

import ca.jrvs.apps.stockquote.model.Position;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PositionDAO implements CrudDAO<Position, String> {

  final Logger logger = LoggerFactory.getLogger(PositionDAO.class);

  private Connection connection;

  public PositionDAO(Connection connection) {
    this.connection = connection;
  }

  private static final String INSERT = "INSERT INTO position (symbol, num_of_shares, "
      + "value_paid) VALUES (?, ?, ?)";
  private static final String UPDATE = "UPDATE position SET num_of_shares = ?, "
      + "value_paid = ? WHERE symbol = ?";
  private static final String GET_ONE = "SELECT symbol, num_of_shares, value_paid FROM position "
      + "WHERE symbol = ?";
  private static final String GET_ALL = "SELECT symbol, num_of_shares, value_paid FROM position";
  private static final String DELETE = "DELETE FROM position WHERE symbol = ?";
  private static final String DELETE_ALL = "TRUNCATE position";

  @Override
  public Position save(Position entity) throws IllegalArgumentException {
    Optional<Position> optionalPosition;
    try(PreparedStatement statement = this.connection.prepareStatement(INSERT)) {
      statement.setString(1, entity.getTicker());
      statement.setInt(2, entity.getNumOfShares());
      statement.setDouble(3, entity.getValuePaid());
      statement.execute();
      logger.info("New position {} saved to database.", entity.getTicker());
    } catch (SQLException e) {
      try (PreparedStatement statement = this.connection.prepareStatement(UPDATE)) {
        statement.setInt(1, entity.getNumOfShares());
        statement.setDouble(2, entity.getValuePaid());
        statement.setString(3, entity.getTicker());
        statement.execute();
        logger.info("Position {} updated.", entity.getTicker());
      } catch (SQLException ex) {
        throw new RuntimeException(ex);
      }
    }
    optionalPosition = this.findById(entity.getTicker());
    return optionalPosition.orElse(null);
  }

  @Override
  public Optional<Position> findById(String s) throws IllegalArgumentException {
    Position position = new Position();
    try (PreparedStatement statement = this. connection.prepareStatement(GET_ONE)) {
      statement.setString(1, s);
      ResultSet resultSet = statement.executeQuery();
      if (resultSet.isBeforeFirst()) {
        while (resultSet.next()) {
          position.setTicker(resultSet.getString("symbol"));
          position.setNumOfShares(resultSet.getInt("num_of_shares"));
          position.setValuePaid(resultSet.getDouble("value_paid"));
        }
        return Optional.of(position);
      } else{
        logger.info("No position {} found in database.", s);
        return Optional.empty();
      }
    } catch (SQLException e) {
      logger.error(e.getMessage());
      throw new RuntimeException(e);
    }
  }

  @Override
  public Iterable<Position> findAll() {
    List<Position> allPositions = new ArrayList<>();
    try (PreparedStatement statement = this.connection.prepareStatement(GET_ALL)) {
      ResultSet resultSet = statement.executeQuery();
      if (resultSet.isBeforeFirst()) {
        while (resultSet.next()){
          Position position = new Position();
          position.setTicker(resultSet.getString("symbol"));
          position.setNumOfShares(resultSet.getInt("num_of_shares"));
          position.setValuePaid(resultSet.getDouble("value_paid"));
          allPositions.add(position);
        }
      }
    } catch (SQLException e) {
      logger.error(e.getMessage());
      throw new RuntimeException(e);
    }
    return allPositions;
  }

  @Override
  public void deleteById(String ticker) throws IllegalArgumentException {
    if (this.findById(ticker).isEmpty()) {
      throw new IllegalArgumentException("\n Invalid input. "
          + "There is no record for the ticker provided.");
    } else {
      try (PreparedStatement statement = this.connection.prepareStatement(DELETE)) {
        statement.setString(1, ticker);
        statement.execute();
        logger.info("Position {} deleted from database.", ticker);
      } catch (SQLException e) {
        logger.error(e.getMessage());
        throw new IllegalArgumentException(e);
      }
    }
  }

  @Override
  public void deleteAll() {
    try (PreparedStatement statement = this.connection.prepareStatement(DELETE_ALL)) {
      statement.execute();
      logger.info("All quote records deleted from database");
    } catch (SQLException e) {
      logger.error(e.getMessage());
      throw new IllegalArgumentException(e);
    }
  }
}
