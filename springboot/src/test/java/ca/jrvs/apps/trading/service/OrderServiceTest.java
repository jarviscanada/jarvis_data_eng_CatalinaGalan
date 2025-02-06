package ca.jrvs.apps.trading.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import ca.jrvs.apps.trading.model.Account;
import ca.jrvs.apps.trading.model.MarketOrder;
import ca.jrvs.apps.trading.model.Option;
import ca.jrvs.apps.trading.model.Quote;
import ca.jrvs.apps.trading.model.SecurityOrder;
import ca.jrvs.apps.trading.repository.QuoteRepository;
import ca.jrvs.apps.trading.repository.SecurityOrderRepository;
import ca.jrvs.apps.trading.repository.TraderRepository;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Arrays;
import java.util.Optional;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

  @Captor
  private ArgumentCaptor<SecurityOrder> captorSecurityOrder;

  @Mock
  private TraderRepository traderRepository;
  @Mock
  private QuoteRepository quoteRepository;
  @Mock
  private TraderAccountService traderAccountService;
  @Mock
  private SecurityOrderRepository securityOrderRepository;

  @InjectMocks
  private OrderService orderService = new OrderService();

  private MarketOrder marketOrder;
  private SecurityOrder securityOrder;
  private Account account;
  private Quote quote;

  @BeforeEach
  void setUp() {
    marketOrder = new MarketOrder();
    marketOrder.setTicker("IBM");
    marketOrder.setTraderId(1);

    account = new Account();
    account.setId(1);
    account.setAmount(150000.00);

    quote = new Quote();
    quote.setTicker("IBM");
    quote.setAskPrice(150.5);
    quote.setAskSize(1300);
    quote.setBidPrice(150.0);
    quote.setBidSize(1000);
    quote.setLastPrice(200.00);
    quote.setLastUpdated(Timestamp.from(Instant.now()));

    securityOrder = new SecurityOrder();
    securityOrder.setStatus(String.valueOf(marketOrder.getOption()));
    securityOrder.setAccount(account);
    securityOrder.setQuote(quote);
    securityOrder.setSize(marketOrder.getSize());
  }

  @Test
  void executeMarketOrder() {
    Integer traderId = marketOrder.getTraderId();
    String ticker = marketOrder.getTicker();
    SecurityOrder savedSecurityOrder = securityOrder;

    when(traderRepository.findAccountById(traderId)).thenReturn(Optional.ofNullable(account));
    when(quoteRepository.findById(ticker)).thenReturn(Optional.ofNullable(quote));
    when(securityOrderRepository.save(any(SecurityOrder.class))).thenReturn(securityOrder);
    when(securityOrderRepository.findById(savedSecurityOrder.getId())).thenReturn(Optional.of(securityOrder));

    marketOrder.setOption(Arrays.stream(Option.values()).findAny().get());
    marketOrder.setSize(900);
    assertDoesNotThrow(()-> orderService.executeMarketOrder(marketOrder));
    marketOrder.setSize(1000);
    assertThrows(IllegalArgumentException.class, ()-> orderService.executeMarketOrder(marketOrder));
  }

  @Test
  void handleBuyMarketOrder() {
    marketOrder.setOption(Option.BUY);

    marketOrder.setSize(1000);
    assertThrows(IllegalArgumentException.class,
        () -> orderService.handleBuyMarketOrder(marketOrder, securityOrder, account));
    marketOrder.setSize(900);
    assertDoesNotThrow(() -> orderService.handleBuyMarketOrder(marketOrder, securityOrder, account));
  }

  @Test
  void handleSellMarketOrder() {
  }
}