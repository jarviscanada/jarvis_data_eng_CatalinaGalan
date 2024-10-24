package ca.jrvs.apps.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCExecutor {

  public static void main(String[] args) {
    DatabaseConnectionManager dcm = new DatabaseConnectionManager(
        "localhost", "hplussport", "postgres", "password");
    try {
      Connection connection = dcm.getConnection();

      OrderDAO orderDao = new OrderDAO(connection);
      Order order = orderDao.findById(1000);
      System.out.println(order);

      CustomerDAO customerDAO = new CustomerDAO(connection);
      Customer customer = customerDAO.findById(1000);
      System.out.println(customer);
      customer.setEmail("lalalalalala@lalalalalala.lala");
      Customer updateCustomer = customerDAO.update(customer);
      System.out.println(updateCustomer);


    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
//  public static void main(String[] args) {
//    DatabaseConnectionManager dcm = new DatabaseConnectionManager(
//        "localhost", "hplussport", "postgres", "password");
//    try {
//      Connection connection = dcm.getConnection();
//      CustomerDAO customerDAO = new CustomerDAO(connection);
//
//      Customer newCustomer = new Customer();
//      newCustomer.setFirstName("Poop");
//      newCustomer.setLastName("PoopPoop");
//      newCustomer.setEmail("poooopy@poopspoops.poop");
//      newCustomer.setPhone("555-poopy");
//      newCustomer.setAddress("123 The Butt");
//      newCustomer.setCity("Drain");
//      newCustomer.setState("Wasted");
//      newCustomer.setZipcode("9009");
//
//      Customer DBcustomer = customerDAO.create(newCustomer);;
//      System.out.println(DBcustomer);
//
//      DBcustomer = customerDAO.findById(DBcustomer.getId());
//      System.out.println(DBcustomer);
//
//      DBcustomer.setEmail("lalalalalal@lala,lala");
//      DBcustomer = customerDAO.update(DBcustomer);
//      System.out.println(DBcustomer);
//
//      customerDAO.delete(DBcustomer.getId());
//
//    } catch (SQLException e) {
//      e.printStackTrace();
//    }
  }

