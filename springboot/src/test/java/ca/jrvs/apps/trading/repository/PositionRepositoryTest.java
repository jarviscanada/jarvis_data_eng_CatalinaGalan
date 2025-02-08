//package ca.jrvs.apps.trading.repository;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.anyInt;
//import static org.mockito.Mockito.when;
//
//import ca.jrvs.apps.trading.model.Account;
//import ca.jrvs.apps.trading.model.Position;
//import ca.jrvs.apps.trading.model.Quote;
//import ca.jrvs.apps.trading.model.SecurityOrder;
//import ca.jrvs.apps.trading.model.Trader;
//import java.sql.Timestamp;
//import java.time.Instant;
//import java.time.LocalDate;
//import java.util.Optional;
//import org.checkerframework.checker.units.qual.A;
//import org.junit.jupiter.api.AfterAll;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.TestPropertySource;
//
//@SpringBootTest
//@TestPropertySource(locations = "classpath:application-test.properties")
//class PositionRepositoryTest {
//
////  @Autowired
////  private PositionRepository positionRepository;
//
//  @Autowired
//  private SecurityOrderRepository securityOrderRepository;
//
//  @Autowired
//  private TraderRepository traderRepository;
//
//  @Autowired
//  private QuoteRepository quoteRepository;
//
//  Position position;
//  SecurityOrder securityOrder;
//  Trader trader;
//  Account account;
//  Quote quote;
//
//
//  @BeforeEach
//  public void setUp() {
//    LocalDate dobParsed = LocalDate.parse("1990-10-21");
//    account = new Account();
//    account.setAmount(150000.00);
//
//    trader = new Trader();
//    trader.setFirstName("Roberto");
//    trader.setLastName("Johns");
//    trader.setCountry("Portugal");
//    trader.setEmail("roberto@portugal.po");
//    trader.setDob(dobParsed);
//    trader.setAccount(account);
//    account.setTrader(trader);
//    traderRepository.save(trader);
//
//    traderRepository.save(trader);
//
//    quote = new Quote();
//    quote.setTicker("IBM");
//    quote.setAskPrice(200.5);
//    quote.setAskSize(13);
//    quote.setBidPrice(200.05);
//    quote.setBidSize(10);
//    quote.setLastPrice(200.00);
//    quote.setLastUpdated(Timestamp.from(Instant.now()));
//    quoteRepository.save(quote);
//
//    securityOrder = new SecurityOrder();
//    securityOrder.setStatus("FILLED");
//    securityOrder.setId(5);
//    securityOrder.setNotes("yeah");
//    securityOrder.setPrice(200.00);
//    securityOrder.setSize(1000);
//    securityOrder.setAccount(account);
//    securityOrder.setQuote(quote);
//    securityOrder = securityOrderRepository.save(securityOrder);
//    position = new Position();
//  }
//
//  @AfterEach
//  public void clearDB() {
////    positionRepository.deleteAll();
//  }
//
//  @Test
//  void saveTest() {
//    securityOrder.setStatus("OPEN");
////    assertThrows(Exception.class, () -> positionRepository.save(securityOrder));
//  }
//
//  @Test
//  void findByAccountIdAndTickerTest() {
//    securityOrder.setSize(500);
////    positionRepository.save(securityOrder);
////    Optional<Position> optPosition =
////        positionRepository.findByAccountIdAndTicker(8, "IBM");
//
////    assertEquals(8, optPosition.get().getAccountId());
////    assertEquals(1500, optPosition.get().getPosition());
//  }
//
//  @Test
//  void existsByAccountIdAndTickerTest() {
//  }
//
//  @Test
//  void findAllTest() {
//  }
//
//  @Test
//  void findAllByAccountIdTest() {
//  }
//
//  @Test
//  void deleteByIdTest() {
//  }
//
//  @Test
//  void deleteAllTest() {
//  }
//}