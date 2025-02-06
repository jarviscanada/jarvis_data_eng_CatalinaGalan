package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.model.Account;
import ca.jrvs.apps.trading.model.MarketOrder;
import ca.jrvs.apps.trading.model.Quote;
import ca.jrvs.apps.trading.model.SecurityOrder;
import ca.jrvs.apps.trading.repository.QuoteRepository;
import ca.jrvs.apps.trading.repository.SecurityOrderRepository;
import ca.jrvs.apps.trading.repository.TraderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

  @Autowired
  private TraderRepository traderRepository;

  @Autowired
  private SecurityOrderRepository securityOrderRepository;

  @Autowired
  private QuoteRepository quoteRepository;

  @Autowired
  private TraderAccountService traderAccountService;

  /**
   * Execute a market order
   * - validate the order (e.g. size and ticker)
   * - create a securityOrder
   * - handle buy or sell orders
   * 	- buy order : check account balance
   * 	- sell order : check position for the ticker/symbol
   * 	- do not forget to update the securityOrder.status
   * - save and return securityOrder
   *
   * NOTE: you are encouraged to make some helper methods (protected or private)
   *
   * @param marketOrder market order
   * @return SecurityOrder from security_order table
   * @throws DataAccessException if unable to get data from DAO
   * @throws IllegalArgumentException for invalid inputs
   */
  public SecurityOrder executeMarketOrder(MarketOrder marketOrder) {
    String ticker = marketOrder.getTicker();
    Integer size = marketOrder.getSize();
    Integer traderId = marketOrder.getTraderId();
    String option = String.valueOf(marketOrder.getOption());

    Account account;
    Quote quote;

    try {
      account = traderRepository.findAccountById(traderId).get();
      quote = quoteRepository.findById(ticker).get();
    } catch (Exception e) {
      throw new IllegalArgumentException("Invalid input. Please verify TraderId and Ticker.");
    }
    if (size <= 0) {
      throw new IllegalArgumentException("Invalid input. Market Order size must be greater than 0.");
    }

    SecurityOrder securityOrder = new SecurityOrder();
    securityOrder.setNotes(option);
    securityOrder.setSize(size);
    securityOrder.setAccount(account);
    securityOrder.setQuote(quote);
    securityOrder.setStatus("Pending");
    SecurityOrder savedSecurityOrder = securityOrderRepository.save(securityOrder);

    if (option.equals("BUY")) {
      if (quote.getAskSize() < size) {
        throw new IllegalArgumentException("Invalid input. Market Order size must not exceed ask size.");
      }
      try {
        handleBuyMarketOrder(marketOrder, securityOrder, account);
      } catch (IllegalArgumentException e) {
        securityOrder.setStatus("Transaction Failed: " + e.getMessage());
        securityOrderRepository.save(securityOrder);
        throw new IllegalArgumentException("Transaction Failed: " + e.getMessage());
      }

    } else if (option.equals("SELL")) {
      if (quote.getBidSize() < size) {
        throw new IllegalArgumentException("Invalid input. Market Order size must not exceed bid size.");
      }
      try {
        handleSellMarketOrder(marketOrder, securityOrder, account);
      } catch (IllegalArgumentException e) {
        securityOrder.setStatus("Transaction Failed: " + e.getMessage());
        securityOrderRepository.save(securityOrder);
        throw new IllegalArgumentException("Transaction Failed: " + e.getMessage());
      }
    }

    try {
      return securityOrderRepository.findById(savedSecurityOrder.getId()).get();
    } catch (Exception e) {
      throw new DataAccessException(e.getMessage()) {
        @Override
        public Throwable getRootCause() {
          return super.getRootCause();
        }
      };
    }
  }

  /**
   * Helper method to execute a buy order
   *
   * @param marketOrder user order
   * @param securityOrder to be saved in database
   */
  protected void handleBuyMarketOrder(MarketOrder marketOrder, SecurityOrder securityOrder, Account account) {
//    Account account = securityOrder.getAccount();
    Quote quote = securityOrder.getQuote();
    Integer size = marketOrder.getSize();
    Double funds = account.getAmount();
    Double price = quote.getAskPrice();

    if (funds >= price * size) {
      traderAccountService.withdraw(account.getId(), price * size);
      securityOrder.setStatus("Transaction Completed: Purchase successful.");
    } else {
      securityOrder.setStatus("Transaction Failed: Insufficient funds.");
    }
    securityOrderRepository.save(securityOrder);
  }

  /**
   * Helper method to execute a sell order
   *
   * @param marketOrder user order
   * @param securityOrder to be saved in database
   */
  protected void handleSellMarketOrder(MarketOrder marketOrder, SecurityOrder securityOrder, Account account) {
//    Account account = securityOrder.getAccount();
    Quote quote = securityOrder.getQuote();
    Integer size = marketOrder.getSize();
    Double funds = account.getAmount();
    Double price = quote.getBidPrice();

    if (funds >= price * size) {
      traderAccountService.withdraw(account.getId(), price * size);
      securityOrder.setStatus("Transaction Completed: Purchase successful.");
    } else {
      securityOrder.setStatus("Transaction Failed: Insufficient funds.");
    }
    securityOrderRepository.save(securityOrder);
  }
}
