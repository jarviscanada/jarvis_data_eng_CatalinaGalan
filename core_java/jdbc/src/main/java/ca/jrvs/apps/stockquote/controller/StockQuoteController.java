package ca.jrvs.apps.stockquote.controller;

import ca.jrvs.apps.stockquote.model.Position;
import ca.jrvs.apps.stockquote.model.Quote;
import ca.jrvs.apps.stockquote.service.PositionService;
import ca.jrvs.apps.stockquote.service.QuoteService;
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
    boolean exit = false;
    System.out.println("\n What would you like to do? \n");
    System.out.println("1- List my stocks\n"
        + "2- Buy stock\n"
        + "3- Sell stock\n"
        + "4- Exit");
    while (!exit) {
      System.out.print("\n> ");
      try {
        int choice = scanner.nextInt();
        switch (choice) {
          case (1) : listAllPositions();
            break;
          case (2) : buyStock();
            break;
          case (3) : sellStock();
            break;
          case (4) : System.out.println("\n--- Goodbye ---\n");
            logger.info("App exited successfully.");
            System.exit(0);
            break;
          default: System.out.println("Please enter a valid option:");
            break;
        }
      } catch (InputMismatchException e) {
        logger.error("Invalid input: non-integer value.");
        System.out.println("Please enter a valid option:");
        scanner.nextLine();
      }
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
              "\n - Last buying price: $" + paidPrice +
              "\n - Current price: $" + newPrice
          );
      }
      displayChoices();
    } catch (IllegalArgumentException e) {
      logger.error("API calls limit reached: {}", e.getMessage());
      System.out.println("\n *** Process interrupted. Please wait one minute and try again ***");
      displayChoices();
    } catch (NullPointerException e) {
      logger.error(e.getMessage());
      System.out.println("\n You don't have any stocks in your portfolio at the moment.");
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
      System.out.println("\n" + quote.get());
      System.out.println("\nEnter the number of shares you'd like to purchase:");
      System.out.print("> ");
      int numOfShares = scanner.nextInt();
      double price = quote.get().getPrice();
      quoteService.getQuoteDAO().save(quote.get());
      Position position = positionService.buy(ticker, numOfShares, price);
      System.out.println("\n Purchase successful:");
      System.out.println("\n" + position);
    } catch (SQLException  | InputMismatchException e) {
      logger.error("{}", e.getMessage());
      System.out.println("\n Invalid Input.");
      displayChoices();
    } catch (IllegalArgumentException e) {
      logger.error("API calls limit exceeded: {}", e.getMessage());
      System.out.println("\n *** Process interrupted. Please wait one minute and try again ***");
      displayChoices();
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
      System.out.println(e.getMessage());
      logger.error(e.getMessage());
    }
    displayChoices();
  }
}
