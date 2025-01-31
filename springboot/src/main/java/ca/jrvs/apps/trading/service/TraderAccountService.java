package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.model.Account;
import ca.jrvs.apps.trading.model.Trader;
import ca.jrvs.apps.trading.repository.TraderRepository;
import java.sql.Date;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TraderAccountService {

  @Autowired
  private TraderRepository traderRepository;

  public Trader createTraderAndAccount(Trader trader) {

    Account account = new Account();
    account.setAmount(0.0);
    account.setTrader(trader);
    trader.setAccount(account);

    return traderRepository.save(trader);
  }

}
