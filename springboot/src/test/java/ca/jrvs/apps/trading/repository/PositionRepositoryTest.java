package ca.jrvs.apps.trading.repository;

import static org.junit.jupiter.api.Assertions.*;

import ca.jrvs.apps.trading.model.Account;
import ca.jrvs.apps.trading.model.Position;
import ca.jrvs.apps.trading.model.Quote;
import ca.jrvs.apps.trading.model.SecurityOrder;
import ca.jrvs.apps.trading.model.Trader;
import ca.jrvs.apps.trading.util.PositionId;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class PositionRepositoryTest {

  @Autowired
  private PositionRepository positionRepository;

  @Autowired
  private SecurityOrderRepository securityOrderRepository;

  @Autowired
  private TraderRepository traderRepository;

  @Autowired
  private QuoteRepository quoteRepository;

  Position position;
  SecurityOrder securityOrder;
  SecurityOrder securityOrder2;
  Trader trader;
  Account account;
  Quote quote;
  Quote quote2;
  Optional<Position> optPosition;

  @BeforeEach
  public void setUp() {
    trader = new Trader();
    trader.setFirstName("Roberto");
    trader.setLastName("Johns");
    trader.setCountry("Portugal");
    trader.setEmail("roberto@portugal.po");
    trader.setDob(LocalDate.parse("1990-10-21"));

    account = new Account();
    account.setAmount(150000.00);
    account.setTrader(trader);
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
    securityOrder.setAccount(account);
    securityOrder.setTicker(quote.getTicker());
    securityOrder.setStatus("FILLED");
    securityOrder.setNotes("BUY");
    securityOrder.setPrice(200.00);
    securityOrder.setSize(1000);
    securityOrderRepository.save(securityOrder);

    quote2 = new Quote();
    quote2.setTicker("MSFT");
    quote2.setAskPrice(200.5);
    quote2.setAskSize(13);
    quote2.setBidPrice(200.05);
    quote2.setBidSize(10);
    quote2.setLastPrice(200.00);
    quote2.setLastUpdated(Timestamp.from(Instant.now()));
    quoteRepository.save(quote2);

    securityOrder2 = new SecurityOrder();
    securityOrder2.setAccount(account);
    securityOrder2.setTicker(quote2.getTicker());
    securityOrder2.setStatus("FILLED");
    securityOrder2.setNotes("BUY");
    securityOrder2.setPrice(200.00);
    securityOrder2.setSize(500);
    securityOrderRepository.save(securityOrder2);
  }

  @AfterEach
  public void clearDB() {
  }

  @Test
  void findByAccountIdAndTickerTest() {

    optPosition =
        positionRepository.findByPositionId(new PositionId(account.getId(), "IBM"));
    position = optPosition.get();

    assertEquals(1000, position.getPosition());
  }

  @Test
  void existsByAccountIdAndTickerTest() {

    assertTrue(positionRepository.existsByPositionId(new PositionId(account.getId(), "IBM")));
    assertFalse(positionRepository.existsByPositionId(new PositionId(account.getId(), "zero")));
    assertTrue(positionRepository.existsByPositionId(new PositionId(account.getId(), "MSFT")));
  }

  @Test
  void updatePosition() {

    optPosition =
        positionRepository.findByPositionId(new PositionId(account.getId(), "IBM"));
    position = optPosition.get();
    assertEquals(1000, position.getPosition());

    SecurityOrder securityOrder3 = new SecurityOrder();
    securityOrder3.setAccount(account);
    securityOrder3.setTicker(quote.getTicker());
    securityOrder3.setStatus("FILLED");
    securityOrder3.setNotes("SELL");
    securityOrder3.setPrice(200.00);
    securityOrder3.setSize(-300);
    securityOrderRepository.save(securityOrder3);

    optPosition =
        positionRepository.findByPositionId(new PositionId(account.getId(), "IBM"));
    position = optPosition.get();
    assertEquals(700, position.getPosition());
  }

  @Test
  public void findAllTest() {

    List<Position> positions = positionRepository.findAll();
    assertFalse(positions.isEmpty());
  }
}