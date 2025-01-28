package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.model.Account;
import ca.jrvs.apps.trading.model.Trader;
import ca.jrvs.apps.trading.model.TraderAccountView;
import ca.jrvs.apps.trading.repository.AccountRepository;
import ca.jrvs.apps.trading.repository.TraderAccountViewRepository;
import ca.jrvs.apps.trading.repository.TraderRepository;
import java.sql.Date;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TraderAccountService {

  @Autowired
  private TraderRepository traderRepository;

  @Autowired
  private AccountRepository accountRepository;

  @Autowired
  private TraderAccountViewRepository viewRepository;

  public Trader createTraderAndAccount(Trader trader) {

    Trader savedTrader = traderRepository.save(trader);

    Account account = new Account();
//    account.setId(trader.getId());
    account.setAmount(0.0);
    account.setTrader(savedTrader);
    accountRepository.save(account);

//    return viewRepository.findByTraderId(savedTrader.getId());
    return savedTrader;
  }

//  public Trader createTrader(String firsName, String lastName, String dob,
//      String country, String email) {
//
//    Trader trader = new Trader();
//    trader.setFirstName(firsName);
//    trader.setLastName(lastName);
//    trader.setDob(dob);
//    trader.setCountry(country);
//    trader.setEmail(email);
//
//    return createTraderAndAccount(trader);
//  }

}
