package ca.jrvs.apps.stockquote.controller;

import ca.jrvs.apps.stockquote.dao.PositionDAO;
import ca.jrvs.apps.stockquote.model.Position;
import ca.jrvs.apps.stockquote.service.PositionService;
import ca.jrvs.apps.stockquote.service.QuoteService;
import java.util.ArrayList;
import java.util.Scanner;

public class StockQuoteController {

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
    System.out.println("\n -------- Welcome to the Stock-Quote App --------");
    System.out.println("\n What would you like to do today? \n");
    displayChoices();
  }

  public void displayChoices() {
    Scanner scanner = new Scanner(System.in);
    System.out.println("1- List my stocks\n"
        + "2- Buy stock\n"
        + "3- Sell stock\n"
        + "4- Exit");
    System.out.print("\n> ");
    int choice = scanner.nextInt();
    switch (choice) {
      case (1) :
        listAllPositions();
        break;
      case (2) : System.out.println("2");
        break;
      case (3) : System.out.println("3");
        break;
      case (4) : System.out.println("\n--- Goodbye ---");
        break;
    }
  }

  public void listAllPositions() {
    Iterable<Position> allPositions = positionService.listAll();
    try {
      while (allPositions.iterator().hasNext()) {
        System.out.println("\n - " + allPositions.iterator().next().getTicker());
      }
    } catch (NullPointerException e) {
        System.out.println("\nPlease choose another option: \n");
        displayChoices();
    }
  }
}
