package ca.jrvs.apps.trading.service;

import static ca.jrvs.apps.trading.model.MarketOrder.Option.*;

import ca.jrvs.apps.trading.model.Account;
import ca.jrvs.apps.trading.model.MarketOrder;
import ca.jrvs.apps.trading.model.MarketOrder.Option;
import ca.jrvs.apps.trading.model.Position;
import ca.jrvs.apps.trading.model.Quote;
import ca.jrvs.apps.trading.model.SecurityOrder;
import ca.jrvs.apps.trading.repository.PositionRepository;
import ca.jrvs.apps.trading.repository.QuoteRepository;
import ca.jrvs.apps.trading.repository.SecurityOrderRepository;
import ca.jrvs.apps.trading.repository.TraderRepository;
import java.util.Optional;
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

  @Autowired
  private PositionRepository positionRepository;

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
   * @param marketOrder marketOrderData
   * @return SecurityOrder from security_order table
   * @throws DataAccessException if unable to get data from DAO
   * @throws IllegalArgumentException for invalid inputs
   */
  public SecurityOrder executeMarketOrder(MarketOrder marketOrder) throws DataAccessException {

    String ticker = marketOrder.getTicker();
    Integer size = marketOrder.getSize();
    Integer traderId = marketOrder.getTraderId();
    Option option = marketOrder.getOption();

    Account account;
    Quote quote;

    try {
      account = traderRepository.findAccountById(traderId).get();
      quote = quoteRepository.findById(ticker).get();
    } catch (Exception e) {
      throw new IllegalArgumentException("Invalid input. Please verify TraderId and Ticker.");
    }

    SecurityOrder securityOrder = new SecurityOrder();
    securityOrder.setNotes(String.valueOf(option));
    securityOrder.setQuote(quote);
    securityOrder.setStatus("OPEN");
    securityOrder.setAccount(account);

    if (option.equals(BUY)) {
      securityOrder.setSize(size);
      securityOrder.setPrice(quote.getAskPrice());
      handleBuyMarketOrder(marketOrder, securityOrder, account);
    } else if (option.equals(SELL)) {
      securityOrder.setSize(size * -1);
      securityOrder.setPrice(quote.getBidPrice());
      handleSellMarketOrder(marketOrder, securityOrder, account);
    }

    securityOrder.setStatus("FILLED");
    SecurityOrder savedSecurityOrder = securityOrderRepository.save(securityOrder);

    return savedSecurityOrder;

  }


  /**
   * Helper method to execute a buy order
   *
   * @param marketOrder user order
   * @param securityOrder to be saved in database
   */
  protected void handleBuyMarketOrder(MarketOrder marketOrder, SecurityOrder securityOrder, Account account) {

    Quote quote = securityOrder.getQuote();
    Integer size = marketOrder.getSize();
    Double funds = account.getAmount();
    Double price = quote.getAskPrice();

    if (size > quote.getAskSize()) {
      throw new IllegalArgumentException(
          "Invalid input. Market Order size must not exceed ask size.");
    } else if (funds < price * size) {
      throw new IllegalArgumentException("Transaction Failed: Insufficient funds.");
    }

    traderAccountService.withdraw(account.getId(), price * size);

  }


  /**
   * Helper method to execute a sell order
   *
   * @param marketOrder user order
   * @param securityOrder to be saved in database
   */
  protected void handleSellMarketOrder(MarketOrder marketOrder, SecurityOrder securityOrder, Account account) {

    Quote quote = securityOrder.getQuote();
    Integer size = marketOrder.getSize();
    Double price = quote.getBidPrice();
    String ticker = quote.getTicker();
    Integer accountId = account.getId();
    Optional<Position> position = positionRepository.findByAccountIdAndTicker(accountId, ticker);

    if (size > quote.getBidSize()) {
      throw new IllegalArgumentException(
          "Invalid input. Market Order size must not exceed bid size.");
    } else if (position.isEmpty()) {
      throw new IllegalArgumentException("Position for ticker " + ticker + " not found.");
    }

    if (position.get().getPosition() >= size) {
      traderAccountService.deposit(account.getId(), price * size);
    } else {
      throw new IllegalArgumentException("Transaction Failed: Insufficient stocks to sell.");
    }

  }
}
