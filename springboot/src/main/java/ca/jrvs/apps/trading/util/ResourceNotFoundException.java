package ca.jrvs.apps.trading.util;

public class ResourceNotFoundException extends Exception {

  public ResourceNotFoundException () {}

  public ResourceNotFoundException (String message) {
    super (message);
  }

  public ResourceNotFoundException (Throwable cause) {
    super (cause);
  }

  public ResourceNotFoundException (String message, Throwable cause) {
    super (message, cause);
  }

}
