package ca.jrvs.apps.trading.util;

import java.time.format.DateTimeParseException;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ResponseExceptionUtil {

  private static final Logger logger = LoggerFactory.getLogger(ResponseExceptionUtil.class);

  public static ResponseStatusException getResponseStatusException(Exception e) {

    switch (e) {

      case IllegalArgumentException illegalArgumentException -> {
        logger.debug("Invalid Input.", e);
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
      }

      case DateTimeParseException dateTimeParseException -> {
        logger.debug("Invalid Input: incorrect date format.");
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
            "Expected Date Format: YYYY-MM-DD. " + e.getMessage());
      }

      case ConstraintViolationException constraintViolationException -> {
        logger.debug("Invalid Input.", e);
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
      }

      case DataAccessException dataAccessException -> {
        logger.debug("Failed Alpha Vantage api request.", e);
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
      }

      case ResponseStatusException responseStatusException -> {
        logger.debug("Exceeded daily Alpha Vantage requests limit.", e);
        throw new ResponseStatusException(responseStatusException.getStatusCode(),
            e.getMessage());
      }

      case ResourceNotFoundException resourceNotFoundException -> {
        logger.debug("Invalid Ticker: not found.", e);
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
      }

      case null, default -> {
        logger.error("Internal Error: " + e.getMessage(), e);
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
            "Internal Error: please contact admin.");

      }
    }
  }
}
