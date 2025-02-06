package ca.jrvs.apps.trading.service;

import static org.junit.jupiter.api.Assertions.*;

import ca.jrvs.apps.trading.model.Account;
import ca.jrvs.apps.trading.model.Trader;
import ca.jrvs.apps.trading.repository.TraderRepository;
import java.time.LocalDate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class TraderAccountServiceIntTest {

  @Autowired
  private TraderRepository traderRepository;

  @Autowired
  private TraderAccountService traderAccountService;

  private Trader trader;

  @BeforeEach
  void setUp() {
    trader = new Trader();
    trader.setFirstName("Roberto");
    trader.setLastName("Johns");
    trader.setCountry("Portugal");
    trader.setEmail("roberto@portugal.po");
    LocalDate dob = LocalDate.parse("1991-10-21");
    trader.setDob(dob);
  }

  @AfterEach
  void tearDown() {
    traderRepository.deleteAll();
  }

  @Test
  void createTraderAndAccountTest() {
    Trader savedTraderAccount = traderAccountService.createTraderAndAccount(trader);
    Account savedAccount = savedTraderAccount.getAccount();
    assertTrue(traderRepository.existsById(savedTraderAccount.getId()));
    assertEquals(0.0, savedAccount.getAmount());
  }

  @Test
  void deleteTraderByIdTest() {
    Trader savedTraderAccount = traderAccountService.createTraderAndAccount(trader);
    Integer savedId = savedTraderAccount.getId();
    traderAccountService.deleteTraderById(savedId);
    assertTrue(traderRepository.findById(savedId).isEmpty());
  }

  @Test
  void depositTest() {
    Trader savedTraderAccount = traderAccountService.createTraderAndAccount(trader);
    Integer traderId = savedTraderAccount.getId();
    Account updatedAccount = traderAccountService.deposit(traderId, 100.0);
    assertEquals(100.0, updatedAccount.getAmount());
    assertEquals(updatedAccount.getAmount(),
        traderRepository.findAccountById(traderId).get().getAmount());
    assertEquals(100.0,
        traderRepository.findById(traderId).get().getAccount().getAmount());
    assertThrows(IllegalArgumentException.class, () ->
        traderAccountService.deposit(traderId, 0.0));
    assertThrows(IllegalArgumentException.class, () ->
        traderAccountService.deposit( -1, 100.0));
  }

  @Test
  void withdrawTest() {
    Trader savedTraderAccount = traderAccountService.createTraderAndAccount(trader);
    Integer traderId = savedTraderAccount.getId();
    traderAccountService.deposit(traderId, 100.0);
    Account updatedAccount = traderAccountService.withdraw(traderId, 40.0);

    assertEquals(60.0, updatedAccount.getAmount());
    assertEquals(updatedAccount.getAmount(),
        traderRepository.findAccountById(traderId).get().getAmount());
    assertEquals(60.0,
        traderRepository.findById(traderId).get().getAccount().getAmount());
    assertThrows(IllegalArgumentException.class, () ->
        traderAccountService.withdraw(traderId, 0.0));
    assertThrows(IllegalArgumentException.class, () ->
        traderAccountService.withdraw(traderId, 100.0));
    assertThrows(IllegalArgumentException.class, () ->
        traderAccountService.withdraw( -1, 10.0));
  }

  @Test
  void getTraderById() {
    Trader savedTraderAccount = traderAccountService.createTraderAndAccount(trader);
    Integer traderId = savedTraderAccount.getId();
    assertEquals("Portugal", traderAccountService.getTraderById(traderId).getCountry());
    assertThrows(IllegalArgumentException.class, () -> traderAccountService.getTraderById( -1));
  }
}