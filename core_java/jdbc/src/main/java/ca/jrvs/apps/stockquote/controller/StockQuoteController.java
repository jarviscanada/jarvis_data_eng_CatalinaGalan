package ca.jrvs.apps.stockquote.controller;

import ca.jrvs.apps.stockquote.model.Position;
import ca.jrvs.apps.stockquote.model.Quote;
import ca.jrvs.apps.stockquote.service.PositionService;
import ca.jrvs.apps.stockquote.service.QuoteService;
import ca.jrvs.apps.stockquote.util.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Optional;
import java.util.Scanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StockQuoteController {

  final Logger logger = LoggerFactory.getLogger(StockQuoteController.class);

  private QuoteService quoteService;
  private PositionService positionService;

  public StockQuoteController(QuoteService quoteService, PositionService positionService) {
    this.quoteService = quoteService;
    this.positionService = positionService;
  }

  /**
   * User interface for application
   */
  public void initClient() {
    logger.info("Controller initialized successfully.");

    System.out.println("\n -------- Welcome to the Stock-Quote App --------");
    displayChoices();
  }

  /**
   * This menu will display possible actions for the user and call necessary methods.
   */
  public void displayChoices() {
    Scanner scanner = new Scanner(System.in);
    System.out.println("\n What would you like to do? \n");
    System.out.println("1- List my stocks\n"
        + "2- Buy stock\n"
        + "3- Sell stock\n"
        + "4- Exit");
    System.out.print("\n> ");
    int choice = scanner.nextInt();
    switch (choice) {
      case (1) : listAllPositions();
        break;
      case (2) : buyStock();
        break;
      case (3) : sellStock();
        break;
      case (4) : System.out.println("\n--- Goodbye ---");
        logger.info("App exited successfully.");
        break;
    }
  }

  /**
   * Display a list of all current stocks in user's database, along with the
   * current price of set stock.
   */
  public void listAllPositions() {
    Iterable<Position> allPositions = positionService.listAll();
    String ticker;
    double valuePaid;
    double paidPrice;
    double newPrice;
    int numOfShares;

    try {
      for (Position position : allPositions) {
          ticker = position.getTicker();
          Optional<Quote> quote = quoteService.getOneQuote(ticker);
          Optional<Quote> newQuote = quoteService.fetchQuoteDataFromAPI(ticker);
          valuePaid = position.getValuePaid();
          numOfShares = position.getNumOfShares();
          paidPrice = quote.get().getPrice();
          newPrice = newQuote.get().getPrice();

          System.out.println(
              "\n - Symbol: " + ticker +
              "\n - Number of shares: " + numOfShares +
              "\n - Total value paid: $" + valuePaid +
              "\n - Price paid per share: $" + paidPrice +
              "\n - Current price per share: $" + newPrice
          );
      }
      displayChoices();
    } catch (NullPointerException | IllegalArgumentException e) {
      logger.error(e.getMessage());
      System.out.println(" Please choose another option.");
      displayChoices();
    }
  }

  /**
   * Take user input to fetch stock info from API and simulate the "buy" action by saving a
   * new position object in the user's database.
   */
  public void buyStock() {
    Scanner scanner = new Scanner(System.in);
    System.out.println("\nPlease provide the symbol for the stock you would like to buy:");
    System.out.print("> ");
    String ticker = scanner.next();
    try {
      Optional<Quote> quote = quoteService.fetchQuoteDataFromAPI(ticker);
      String q = JsonParser.toJson(quote.get(), true, true);
      System.out.println(q);
      System.out.println("\nPlease enter the number of shares you'd like to purchase:");
      System.out.print("> ");
      int numOfShares = scanner.nextInt();
      double price = quote.get().getPrice();
      quoteService.getQuoteDAO().save(quote.get());
      Position position = positionService.buy(ticker, numOfShares, price);
      String p = JsonParser.toJson(position, true, true);
      System.out.println("\n Purchase successful:");
      System.out.println(p);
    } catch (IllegalArgumentException | SQLException | InputMismatchException |
             JsonProcessingException e) {
      logger.error(e.getMessage());
      System.out.println("\n Invalid Input.");
      System.out.println(e.getMessage());
    }
    displayChoices();
  }

  /**
   * Find a specific position in the user's database and simulate "sell" action by deleting it.
   */
  public void sellStock() {
    Scanner scanner = new Scanner(System.in);
    System.out.println("\n Please enter the symbol of the stock you would like to sell:");
    System.out.print("> ");
    String ticker = scanner.next();
    try {
      positionService.sell(ticker);
      System.out.println("\n Sale successful.");
    } catch (IllegalArgumentException e) {
      logger.error(e.getMessage());
      System.out.println(e.getMessage());
    }
    displayChoices();
  }
}
