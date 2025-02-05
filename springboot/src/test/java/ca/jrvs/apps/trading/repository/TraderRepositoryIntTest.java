package ca.jrvs.apps.trading.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import ca.jrvs.apps.trading.model.Trader;
import java.util.Arrays;
import java.util.List;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class TraderRepositoryIntTest {

  @Autowired
  private TraderRepository traderRepository;

  private Trader trader;
  private Integer validId;
  private Integer invalidId;

  @BeforeEach
  public void setUp() {
    trader = new Trader();
    trader.setFirstName("Roberto");
    trader.setLastName("Johns");
    trader.setCountry("Portugal");
    trader.setEmail("roberto@portugal.po");
    trader.setDob("1990-10-21");
    traderRepository.save(trader);
    validId = trader.getId();
    invalidId = -1;
  }

  @AfterEach
  public void clearDB() {
    traderRepository.deleteAll();
  }

  @Test
  public void findAllByIdTest() {
    List<Integer> allTraderIds = Arrays.asList(validId, invalidId);
    List<Trader> traders = Lists.newArrayList(traderRepository.findAllById(allTraderIds));
    assertEquals(1, traders.size());
    assertEquals(traders.get(0).getFirstName(), "Roberto");
  }

  @Test
  public void findByIdTest() {
    assertTrue(traderRepository.findById(validId).isPresent());
    assertEquals(traderRepository.findById(validId).get().getCountry(), "Portugal");
    assertTrue(traderRepository.findById(invalidId).isEmpty());
  }

  @Test
  public void existsByIdTest() {
    assertTrue(traderRepository.existsById(validId));
    assertFalse(traderRepository.existsById(invalidId));
  }

  @Test
  public void findAllTest() {
    assertFalse(traderRepository.findAll().isEmpty());
  }

  @Test
  public void countTest() {
    assertEquals(traderRepository.findAll().size(), traderRepository.count());
  }

  @Test
  public void deleteByIdTest() {
    traderRepository.deleteById(validId);
    assertFalse(traderRepository.existsById(validId));
    assertTrue(traderRepository.findById(validId).isEmpty());
  }

  @Test
  public void deleteAllTest() {
    traderRepository.deleteAll();
    assertTrue(traderRepository.findAll().isEmpty());
  }
}