package ca.jrvs.apps.jdbc;

import ca.jrvs.apps.jdbc.util.DataAccessObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderDAO extends DataAccessObject<Order> {

  protected OrderDAO(Connection connection) {
    super(connection);
  }

  private static final String GET_ONE = "SELECT"
  + " c.first_name, c.last_name, c.email, o.order_id,"
  + " o.creation_date, o.total_due, o.status,"
  + " s.first_name, s.last_name, s.email,"
  + " oi.quantity,"
  + " p.code, p.name, p.size, p.variety, p.price"
  + " FROM orders o"
  + " JOIN customer c ON o.customer_id = c.customer_id"
  + " JOIN salesperson s ON o.salesperson_id=s.salesperson_id"
  + " JOIN order_item oi ON oi.order_id=o.order_id"
  + " JOIN product p ON oi.product_id = p.product_id"
  + " WHERE o.order_id = ?";

  @Override
  public Order findById(long id) {
    Order order = new Order();
    try(PreparedStatement statement = this.connection.prepareStatement(GET_ONE)) {
      statement.setLong(1, id);
      ResultSet rs = statement.executeQuery();
      List<OrderItem> orderItems = new ArrayList<>();
      long orderId = 0;
      while(rs.next()) {
        if (orderId == 0) {
          order.setCustomerFirstName(rs.getString(1));
          order.setCustomerLastName(rs.getString(2));
          order.setCustomerEmail(rs.getString(3));
          order.setId(rs.getLong(4));
          orderId = order.getId();
          order.setCreationDate(new Date(rs.getDate(5).getTime()));
          order.setTotalDue(rs.getBigDecimal(6));
          order.setStatus(rs.getString(7));
          order.setSalespersonFirstName(rs.getString(8));
          order.setSalespersonLastName(rs.getString(9));
          order.setSalespersonEmail(rs.getString(10));
        }
        OrderItem orderItem = new OrderItem();
        orderItem.setQuantity(rs.getInt(11));
        orderItem.setProductCode(rs.getString(12));
        orderItem.setProductName(rs.getString(13));
        orderItem.setProductSize(rs.getInt(14));
        orderItem.setProductVariety(rs.getString(15));
        orderItem.setProductPrice(rs.getBigDecimal(16));
        orderItems.add(orderItem);
      }
      order.setOrderItems(orderItems);
    } catch (SQLException e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
    return order;
  }

  @Override
  public List<Order> findAll() {
//    return List.of();
    return null;
  }

  @Override
  public Order update(Order dto) {
    return null;
  }

  @Override
  public Order create(Order dto) {
    return null;
  }

  @Override
  public void delete(long id) {

  }
}
