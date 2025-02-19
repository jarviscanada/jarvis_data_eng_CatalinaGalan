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
import ca.jrvs.apps.trading.util.PositionId;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

  private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

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
   * Method to execute a MarketOrder for a specific Trader.
   *
   * @param marketOrder DTO.
   * @return SecurityOrder created from executing the MarketOrder.
   * @throws DataAccessException if Data can't be accessed.
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
      logger.debug("Invalid Input.");
      throw new IllegalArgumentException("Invalid input. Please verify TraderId and Ticker.");
    }

    SecurityOrder securityOrder = new SecurityOrder();
    securityOrder.setNotes(String.valueOf(option));
    securityOrder.setQuote(quote);
    securityOrder.setStatus("OPEN");
    securityOrder.setAccount(account);
    securityOrder.setSize(size);
    securityOrderRepository.save(securityOrder);

    if (option.equals(BUY)) {
      securityOrder.setPrice(quote.getAskPrice());
      handleBuyMarketOrder(marketOrder, securityOrder, account);
    } else if (option.equals(SELL)) {
      securityOrder.setSize(size * -1);
      securityOrder.setPrice(quote.getBidPrice());
      handleSellMarketOrder(marketOrder, securityOrder, account);
    }

    return securityOrder;
  }


  /**
   * Helper method to execute a buy order.
   *
   * @param marketOrder user order.
   * @param securityOrder to be updated in database when buy order is successfully executed.
   */
  protected void handleBuyMarketOrder(MarketOrder marketOrder, SecurityOrder securityOrder, Account account) {

    Quote quote = securityOrder.getQuote();
    Integer size = marketOrder.getSize();
    Double funds = account.getAmount();
    Double price = quote.getAskPrice();

    if (size > quote.getAskSize()) {
      securityOrder.setStatus("REJECTED");
      securityOrder.setNotes("Buy Order Failed: Market Order size must not exceed ask size.");
      securityOrderRepository.save(securityOrder);

      logger.debug("Invalid Input.");
      throw new IllegalArgumentException(
          "Invalid input. Market Order size must not exceed ask size.");

    } else if (funds < price * size) {
      securityOrder.setStatus("REJECTED");
      securityOrder.setNotes("Buy Order Failed: Insufficient funds.");
      securityOrderRepository.save(securityOrder);

      logger.debug("Invalid Input.");
      throw new IllegalArgumentException("Transaction Failed: Insufficient funds.");
    }

    traderAccountService.withdraw(account.getId(), price * size);
    securityOrder.setStatus("FILLED");
    securityOrderRepository.save(securityOrder);
    logger.info("new buy SecurityOrder created.");
  }


  /**
   * Helper method to execute a sell order.
   *
   * @param marketOrder user order.
   * @param securityOrder to be updated when sell order is successfully executed.
   */
  protected void handleSellMarketOrder(MarketOrder marketOrder, SecurityOrder securityOrder, Account account) {

    Quote quote = securityOrder.getQuote();
    Integer size = marketOrder.getSize();
    Double price = quote.getBidPrice();
    String ticker = quote.getTicker();
    Integer accountId = account.getId();
    Optional<Position> position = positionRepository.findByPositionId(new PositionId(accountId, ticker));

    if (size > quote.getBidSize()) {
      securityOrder.setStatus("REJECTED");
      securityOrder.setNotes("Sell Order Failed: Market Order size must not exceed ask size.");
      securityOrderRepository.save(securityOrder);

      logger.debug("Invalid Input.");
      throw new IllegalArgumentException(
          "Invalid input. Market Order size must not exceed bid size.");

    } else if (position.isEmpty()) {
      securityOrder.setStatus("REJECTED");
      securityOrder.setNotes("Position for Ticker " + ticker + " not found.");
      securityOrderRepository.save(securityOrder);

      logger.debug("Invalid Input.");
      throw new IllegalArgumentException("Position for TraderID " + accountId + " and Ticker "
           + ticker + " not found.");
    }

    if (position.get().getPosition() >= size) {
      traderAccountService.deposit(account.getId(), price * size);
      securityOrder.setStatus("FILLED");
      securityOrderRepository.save(securityOrder);
      logger.info("new sell SecurityOrder created.");

    } else {
      logger.debug("Failed to execute sell MarketOrder.");
      throw new IllegalArgumentException("Transaction Failed: Insufficient stocks to sell.");
    }
  }
}
