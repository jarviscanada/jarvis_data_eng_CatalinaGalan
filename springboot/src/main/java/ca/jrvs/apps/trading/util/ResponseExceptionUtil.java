package ca.jrvs.apps.trading.util;

import jakarta.validation.ValidationException;
import java.time.format.DateTimeParseException;
import org.hibernate.TransactionException;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.server.ResponseStatusException;

public class ResponseExceptionUtil {

  private static final Logger logger = LoggerFactory.getLogger(ResponseExceptionUtil.class);

  public static ResponseStatusException getResponseStatusException(Exception e) {

    if (e instanceof IllegalArgumentException) {
      logger.debug("Invalid Input.", e);
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
    } else if (e instanceof DateTimeParseException) {
      logger.debug("Invalid Input: incorrect date format.");
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Expected Date Format: YYYY-MM-DD. " + e.getMessage());
    } else if (e instanceof ConstraintViolationException) {
      logger.debug("Invalid Input.", e);
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());

    } else if (e instanceof DataAccessException) {
      logger.debug("Failed Alpha Vantage api request.", e);
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
      
    } else if (e instanceof ResponseStatusException) {
      logger.debug("Exceeded daily Alpha Vantage requests limit.", e);
      throw new ResponseStatusException(((ResponseStatusException) e).getStatusCode(),
          e.getMessage());

    } else if (e instanceof ResourceNotFoundException) {
      logger.debug("Invalid Ticker: not found.", e);
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());

    } else {
      logger.error("Internal Error.", e);
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
          "Internal Error: please contact admin.");
    }
  }
}
