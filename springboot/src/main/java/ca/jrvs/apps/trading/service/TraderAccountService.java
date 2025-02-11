package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.model.Account;
//import ca.jrvs.apps.trading.model.Position;
import ca.jrvs.apps.trading.model.Trader;
import ca.jrvs.apps.trading.repository.TraderRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TraderAccountService {

  @Autowired
  private TraderRepository traderRepository;

  public Trader createTraderAndAccount(Trader trader) {

    if (trader.getId() != null) {
      throw new IllegalArgumentException("New trader id must be null");
    }

    try {
      Account account = new Account();
      account.setAmount(0.0);
      account.setTrader(trader);
//      trader.setAccount(account);
      return traderRepository.save(trader);
    } catch (Exception e) {
      throw new IllegalArgumentException("All required fields must be present and must be valid.");
    }
  }

  public void deleteTraderById(Integer traderId) {

    try {
      Trader trader = traderRepository.findById(traderId).get();
      if (trader.getAccount().getAmount() != 0) {
        throw new IllegalArgumentException("Unable to delete Trader: Account balance must be 0.");
      }
      traderRepository.deleteById(traderId);
    } catch (Exception e) {
      throw new IllegalArgumentException(e.getMessage());
    }

  }

  public Account deposit(Integer traderId, Double amount) {
    Trader trader;
    Account account;
    if (amount <= 0) {
      throw new IllegalArgumentException("Deposit amount must be greater than 0."
          + " Please enter a valid amount.");
    }

    try {
      trader = traderRepository.findById(traderId).get();
      account = trader.getAccount();
    } catch (Exception e) {
      throw new IllegalArgumentException("Trader not found. Please provide a valid Trader Id.", e);
    }

    Double newAmount = account.getAmount() + amount;
    account.setAmount(newAmount);
//    trader.setAccount(account);
    traderRepository.save(trader);
    return account;
  }

  public Account withdraw(Integer traderId, Double amount) {
    Trader trader;
    Account account;
    try {
      trader = traderRepository.findById(traderId).get();
      account = trader.getAccount();
    } catch (Exception e) {
      throw new IllegalArgumentException("Trader not found. Please provide a valid Trader Id.", e);
    }

    if (amount <= 0) {
      throw new IllegalArgumentException("Withdraw amount must be greater than 0."
          + " Please enter a valid withdraw amount.");
    } else if (amount > account.getAmount()) {
      throw new IllegalArgumentException("Requested withdraw exceeds available funds.");
    }

    Double newAmount = account.getAmount() - amount;
    account.setAmount(newAmount);
    trader.setAccount(account);
    traderRepository.save(trader);
    return account;
  }

  public Trader getTraderById(Integer traderId) {
    Optional<Trader> trader = traderRepository.findById(traderId);
    if (trader.isPresent()) {
      return trader.get();
    }
    throw new IllegalArgumentException("Trader nor found. Please provide a valid Trader Id.");
  }
}

