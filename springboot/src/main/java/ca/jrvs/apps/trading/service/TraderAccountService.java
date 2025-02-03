package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.model.Account;
import ca.jrvs.apps.trading.model.Position;
import ca.jrvs.apps.trading.model.Trader;
import ca.jrvs.apps.trading.repository.TraderRepository;
import java.sql.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

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
      trader.setAccount(account);
    } catch (Exception e) {
      throw new IllegalArgumentException("All fields for trader must be provided.", e);
    }
    return traderRepository.save(trader);
  }

  public void deleteTraderById(Long traderId) {

    try {
      traderRepository.existsById(traderId);
      Account account = traderRepository.findAccountById(traderId);
//      List<Position> positions =

      if (account.getAmount() != 0) {
        throw new IllegalArgumentException("Unable to delete Trader #{traderId}. Account balance must be 0.");
      }
      
//      positions.forEach(position -> () {
////      securityOrderRepository.deleteById(positionId);
//      });
      traderRepository.deleteById(traderId);
    } catch (Exception e) {

    }

  }


  public Account deposit(Long traderId, Double amount) {

    Account account;

    if (amount <= 0) {
      throw new IllegalArgumentException("Withdraw amount must be greater than 0. Please enter a valid withdraw amount.");
    }

    try {
      account = traderRepository.findAccountById(traderId);
    } catch (Exception e) {
      throw new IllegalArgumentException("traderId not found. Please provide a valid traderId.", e);
    }

    double newAmount = account.getAmount() + amount;
    account.setAmount(newAmount);
    return account;
  }

  public Account withdraw(Long traderId, Double amount) {

    Account account;

    try {
      account = traderRepository.findAccountById(traderId);
    } catch (Exception e) {
      throw new IllegalArgumentException("traderId not found. Please provide a valid traderId.", e);
    }

    if (amount <= 0) {
      throw new IllegalArgumentException("Withdraw amount must be greater than 0. Please enter a valid withdraw amount.");
    } else if (amount > account.getAmount()) {
      throw new IllegalArgumentException("Requested withdraw exceeds available funds.");
    }

    double newAmount = account.getAmount() - amount;
    account.setAmount(newAmount);
    return account;
  }
}

