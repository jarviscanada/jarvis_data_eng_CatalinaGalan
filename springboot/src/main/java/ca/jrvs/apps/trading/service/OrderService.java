package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.model.Account;
import ca.jrvs.apps.trading.model.MarketOrder;
import ca.jrvs.apps.trading.model.SecurityOrder;
import ca.jrvs.apps.trading.repository.PositionRepository;
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
  private PositionRepository positionRepository;

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

    SecurityOrder securityOrder = new SecurityOrder();

    Account account;

    try {
      account = traderRepository.findAccountById(traderId).get();
    } catch (Exception e) {
      throw new IllegalArgumentException("Invalid Trader Id.");
    }

    securityOrder.setNotes(option);
    securityOrder.setSize(size);
    securityOrder.setAccount(account);
    securityOrder.setTicker(ticker);

    if (option.equals("BUY")) {
      try {
        handleBuyMarketOrder(marketOrder, securityOrder);
      } catch (IllegalArgumentException e) {
        securityOrder.setStatus("Transaction Failed: " + e.getMessage());
        securityOrderRepository.save(securityOrder);
      }

    } else if (option.equals("SELL")) {
      try {
        handleSellMarketOrder(marketOrder, securityOrder);
      } catch (IllegalArgumentException e) {
        securityOrder.setStatus("Transaction Failed: " + e.getMessage());
        securityOrderRepository.save(securityOrder);
      }
    }

    try {
      return securityOrderRepository.findById(securityOrder.getId()).get();
    } catch (Exception e) {
      throw new DataAccessException(e.getMessage()) {
        @Override
        public Throwable getRootCause() {
          return super.getRootCause();
        }
      };
    }




//      Double funds = account.get().getAmount();

//      Double askPrice = quote.get().getAskPrice();
//      Double bidPrice = quote.get().getBidPrice();
//    securityOrder.setSize(size);
//    securityOrder.setQuote(quote);
//    securityOrder.setAccount(traderRepository.findAccountById(traderId));
//    securityOrder.setNotes(option);
  }

  /**
   * Helper method to execute a buy order
   *
   * @param marketOrder user order
   * @param securityOrder to be saved in database
   */
  protected void handleBuyMarketOrder(MarketOrder marketOrder, SecurityOrder securityOrder) {
    Account account = securityOrder.getAccount();
    Integer size = marketOrder.getSize();
    Double funds = account.getAmount();
    Double price;

    try {
      price = quoteRepository.findById(marketOrder.getTicker()).get().getBidPrice();
    } catch (Exception e) {
      throw new IllegalArgumentException("Invalid ticker.");
    }

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
  protected void handleSellMarketOrder(MarketOrder marketOrder, SecurityOrder securityOrder) {
    //TODO
  }
}
