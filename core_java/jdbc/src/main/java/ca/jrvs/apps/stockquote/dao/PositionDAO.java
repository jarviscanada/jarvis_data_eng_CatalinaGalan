package ca.jrvs.apps.stockquote.dao;

import ca.jrvs.apps.stockquote.model.Position;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;

public class PositionDAO implements CrudDAO<Position, String> {

  private Connection connection;

  public PositionDAO(Connection connection) {
    this.connection = connection;
  }

  private static final String DELETE_ALL = "TRUNCATE position";

  @Override
  public Position save(Position entity) throws IllegalArgumentException {
    return null;
  }

  @Override
  public Optional<Position> findById(String s) throws IllegalArgumentException {
    return Optional.empty();
  }

  @Override
  public Iterable<Position> findAll() {
    return null;
  }

  @Override
  public void deleteById(String s) throws IllegalArgumentException {

  }

  @Override
  public void deleteAll() {
    try (PreparedStatement statement = this.connection.prepareStatement(DELETE_ALL)) {

    } catch (SQLException e) {
      throw new IllegalArgumentException(e);
    }
  }
}
