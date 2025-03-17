package ca.jrvs.apps.jdbc;

import ca.jrvs.apps.jdbc.util.DataTransferObject;
import java.math.BigDecimal;

public class OrderItem {


  private long id;
  private int quantity;
  private String productCode;
  private String productName;
  private int productSize;
  private String productVariety;
  private BigDecimal productPrice;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  public String getProductCode() {
    return productCode;
  }

  public void setProductCode(String productCode) {
    this.productCode = productCode;
  }

  public String getProductName() {
    return productName;
  }

  public void setProductName(String productName) {
    this.productName = productName;
  }

  public int getProductSize() {
    return productSize;
  }

  public void setProductSize(int productSize) {
    this.productSize = productSize;
  }

  public String getProductVariety() {
    return productVariety;
  }

  public void setProductVariety(String productVariety) {
    this.productVariety = productVariety;
  }

  public BigDecimal getProductPrice() {
    return productPrice;
  }

  public void setProductPrice(BigDecimal productPrice) {
    this.productPrice = productPrice;
  }

  @Override
  public String toString() {
    return "OrderItem{" +
        "id=" + id +
        ", quantity=" + quantity +
        ", productCode='" + productCode + '\'' +
        ", productName='" + productName + '\'' +
        ", productSize=" + productSize +
        ", productVariety='" + productVariety + '\'' +
        ", productPrice=" + productPrice +
        '}';
  }
}
