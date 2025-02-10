package ca.jrvs.apps.trading.repository;

import static org.junit.jupiter.api.Assertions.*;

import ca.jrvs.apps.trading.model.Account;
import ca.jrvs.apps.trading.model.Quote;
import ca.jrvs.apps.trading.model.SecurityOrder;
import ca.jrvs.apps.trading.model.Trader;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class SecurityOrderRepositoryTest {

  @Autowired
  private SecurityOrderRepository securityOrderRepository;

  @Autowired
  private TraderRepository traderRepository;

  @Autowired
  private QuoteRepository quoteRepository;

  private SecurityOrder securityOrder;
  private Trader trader;
  private Account account;
  private Quote quote;

  @BeforeEach
  public void setUp() {
    trader = new Trader();
    trader.setFirstName("John");
    trader.setLastName("Snow");
    LocalDate dob = LocalDate.parse("1981-10-21");
    trader.setDob(dob);
    trader.setCountry("Westeros");
    trader.setEmail("John@snowy.north");

    account = new Account();
    account.setTrader(trader);
    account.setAmount(15500.00);
    trader.setAccount(account);
    traderRepository.save(trader);

    quote = new Quote();
    quote.setTicker("IBM");
    quote.setAskPrice(200.5);
    quote.setAskSize(13);
    quote.setBidPrice(200.05);
    quote.setBidSize(10);
    quote.setLastPrice(200.00);
    quote.setLastUpdated(Timestamp.from(Instant.now()));
    quoteRepository.save(quote);

    securityOrder = new SecurityOrder();
    securityOrder.setQuote(quote);
    securityOrder.setAccount(account);
    securityOrder.setSize(10);
    securityOrder.setPrice(150.0);
    securityOrder.setStatus("Awesome.");
    securityOrder.setNotes("BUY");
    securityOrderRepository.save(securityOrder);

//    account.setOrder(securityOrder);
//    trader.setAccount(account);
//    traderRepository.save(trader);
  }

  @AfterEach
  public void cleaDb() {
    traderRepository.deleteAll();
    quoteRepository.deleteAll();
  }

  @Test
  public void findByIdTest() {
    Integer securityOrderId = securityOrder.getId();
    Integer size = 10;
    assertEquals(size, securityOrderRepository.findById(securityOrderId).get().getSize());
    assertEquals(Optional.empty(), securityOrderRepository.findById(-1));
  }

  @Test
  public void findAllTest() {
    List<SecurityOrder> orders = securityOrderRepository.findAll();
    assertFalse(orders.isEmpty());
  }

  @Test
  public void findOrdersInAccountTest() {
//    account.setOrder(securityOrder);
//    trader.setAccount(account);
//    traderRepository.save(trader);

//    account = traderRepository.findById(trader.getId()).get().getAccount();

    Account account = traderRepository.findAccountById(trader.getId()).get();

    Set<SecurityOrder> accountOrders = account.getOrders();
    System.out.println(accountOrders.toString());
    assertFalse(accountOrders.isEmpty());
  }

  @Test
  public void findAllByAccountTest() {
    SecurityOrder securityOrder2 = new SecurityOrder();
    securityOrder2.setQuote(quote);
    securityOrder2.setAccount(account);
    securityOrder2.setSize(-5);
    securityOrder2.setPrice(150.0);
    securityOrder2.setStatus("SO AMAZING.");
    securityOrder2.setNotes("SELL");
    securityOrderRepository.save(securityOrder2);
//    account.setOrder(securityOrder2);

    account = traderRepository.findAccountById(trader.getId()).get();
    Set<SecurityOrder> ordersByAccount = securityOrderRepository.findAllByAccount(account);
//    for(SecurityOrder order : ordersByAccount) { System.out.println(order.toString()); }
    assertFalse(ordersByAccount.isEmpty());
    assertEquals(2, ordersByAccount.size());
    System.out.println(ordersByAccount);
  }

  @Test
  public void countTest() {
    assertTrue(securityOrderRepository.count() >= 1);
  }

  @Test
  public void existsByIdTest() {
    Integer id = securityOrder.getId();
    assertTrue(securityOrderRepository.existsById(id));
  }

  @Test
  public void deleteByIdTest() {
    Integer id = securityOrder.getId();
    securityOrderRepository.deleteById(id);
    assertTrue(securityOrderRepository.findById(id).isEmpty());
  }

  @Test
  public void deleteAll() {
    Integer id = securityOrder.getId();
    securityOrderRepository.deleteAll();
    assertTrue(securityOrderRepository.findAll().isEmpty());
  }

}