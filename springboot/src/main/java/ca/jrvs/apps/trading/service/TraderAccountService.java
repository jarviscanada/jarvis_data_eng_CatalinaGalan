package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.model.Account;
import ca.jrvs.apps.trading.model.Trader;
import ca.jrvs.apps.trading.repository.TraderRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TraderAccountService {

  private static final Logger logger = LoggerFactory.getLogger(TraderAccountService.class);

  @Autowired
  private TraderRepository traderRepository;


  /**
   * Method to create a new Trader and its associated Account set with amount = 0.0.
   *
   * @param trader DTO to be persisted in the db.
   * @return a new Trader.
   */
  public Trader createTraderAndAccount(Trader trader) {

    if (trader.getId() != null) {
      logger.debug("Invalid Input.");
      throw new IllegalArgumentException("New trader id must be null");
    }

    try {
      Account account = new Account();
      account.setAmount(0.0);
      account.setTrader(trader);

      return traderRepository.save(trader);

    } catch (Exception e) {
      logger.debug("Invalid Input.");
      throw new IllegalArgumentException("All required fields must be present and must be valid: "
          + e.getCause().getCause().getMessage());
    }
  }


  /**
   * Delete a single Trader by ID.
   *
   * @param traderId to delete.
   */
  public void deleteTraderById(Integer traderId) {

    try {
      Trader trader = traderRepository.findById(traderId).get();
      if (trader.getAccount().getAmount() != 0) {
        logger.debug("Unable to delete: Account balance is not 0.");
        throw new IllegalArgumentException("Unable to delete Trader: Account balance must be 0.");
      }

      traderRepository.deleteById(traderId);

    } catch (Exception e) {
      logger.debug("Invalid Input.");
      throw new IllegalArgumentException(e.getMessage());
    }
  }


  /**
   * Method to add funds to a Trader's Account.
   *
   * @param traderId associated to the Account. NOTE: A Trader can only have one account.
   *                 Trader and Account share the same ID.
   * @param amount to be added.
   * @return Account object.
   */
  public Account deposit(Integer traderId, Double amount) {

    Trader trader;
    Account account;

    if (amount <= 0) {
      logger.info("Invalid Input.");
      throw new IllegalArgumentException("Deposit amount must be greater than 0."
          + " Please enter a valid amount.");
    }

    try {
      trader = traderRepository.findById(traderId).get();
      account = trader.getAccount();
    } catch (Exception e) {
      logger.debug("Invalid Input.");
      throw new IllegalArgumentException("Trader not found. Please provide a valid Trader Id.", e);
    }

    Double newAmount = account.getAmount() + amount;
    account.setAmount(newAmount);
    traderRepository.save(trader);

    return account;
  }


  /**
   * Method to withdraw funds to a Trader's Account.
   *
   * @param traderId associated to the Account. NOTE: A Trader can only have one account.
   *                 Trader and Account share the same ID.
   * @param amount to be subtracted.
   * @return Account object.
   */
  public Account withdraw(Integer traderId, Double amount) {

    Trader trader;
    Account account;

    try {
      trader = traderRepository.findById(traderId).get();
      account = trader.getAccount();
    } catch (Exception e) {
      logger.debug("Invalid Input.");
      throw new IllegalArgumentException("Trader not found. Please provide a valid Trader Id.", e);
    }

    if (amount <= 0) {
      logger.debug("Invalid Input.");
      throw new IllegalArgumentException("Withdraw amount must be greater than 0."
          + " Please enter a valid withdraw amount.");
    } else if (amount > account.getAmount()) {
      logger.debug("Invalid Input.");
      throw new IllegalArgumentException("Requested withdraw exceeds available funds.");
    }

    Double newAmount = account.getAmount() - amount;
    account.setAmount(newAmount);
    trader.setAccount(account);
    traderRepository.save(trader);

    return account;
  }


  /**
   * Find a Trader by ID in the db.
   *
   * @param traderId to be found.
   * @return Trader.
   */
  public Trader getTraderById(Integer traderId) {

    Optional<Trader> trader = traderRepository.findById(traderId);
    if (trader.isEmpty()) {
      logger.debug("Invalid Input.");
      throw new IllegalArgumentException("Trader nor found. Please provide a valid Trader Id.");
    }

    return trader.get();
  }
}

