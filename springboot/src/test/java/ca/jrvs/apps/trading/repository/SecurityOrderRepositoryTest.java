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
    account = trader.getAccount();
    Integer traderId = traderRepository.findByFirstName("John").getId();

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
    securityOrder.setQuote(quoteRepository.findById("IBM").get());
    securityOrder.setAccount(traderRepository.findAccountById(traderId).get());
    securityOrder.setSize(10);
    securityOrder.setPrice(150.0);
    securityOrder.setStatus("Awesome.");
    securityOrder.setNotes("BUY");
    account.setOrder(securityOrder);
    securityOrderRepository.save(securityOrder);
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
  public void findAccountTest() {
    Set<SecurityOrder> accountOrders = account.getOrders();
    assertFalse(accountOrders.isEmpty());
  }

  @Test
  public void findAllByAccountTest() {
    Set<SecurityOrder> ordersByAccount = securityOrderRepository.findAllByAccount(account);
    System.out.println(ordersByAccount.toString());
    for(SecurityOrder order : ordersByAccount) { System.out.println(order.toString()); }
    assertFalse(ordersByAccount.isEmpty());
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