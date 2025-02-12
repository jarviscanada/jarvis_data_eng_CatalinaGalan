package ca.jrvs.apps.trading.service;

import static ca.jrvs.apps.trading.model.MarketOrder.Option.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ca.jrvs.apps.trading.model.Account;
import ca.jrvs.apps.trading.model.MarketOrder;
import ca.jrvs.apps.trading.model.Position;
import ca.jrvs.apps.trading.model.Quote;
import ca.jrvs.apps.trading.model.SecurityOrder;
import ca.jrvs.apps.trading.repository.PositionRepository;
import ca.jrvs.apps.trading.repository.QuoteRepository;
import ca.jrvs.apps.trading.repository.SecurityOrderRepository;
import ca.jrvs.apps.trading.repository.TraderRepository;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Arrays;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
  @Mock
  private PositionRepository positionRepository;
  @Mock
  private Optional<Position> optPosition;
  @Mock
  private Position position;

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
  void executeMarketOrderTest() {

    when(traderRepository.findAccountById(anyInt())).thenReturn(Optional.ofNullable(account));
    when(quoteRepository.findById(anyString())).thenReturn(Optional.ofNullable(quote));

    marketOrder.setOption(Arrays.stream(MarketOrder.Option.values()).findAny().get());
    marketOrder.setSize(900);

    orderService.executeMarketOrder(marketOrder);
    verify(securityOrderRepository).save(captorSecurityOrder.capture());
    SecurityOrder capturedSecurityOrder = captorSecurityOrder.getValue();
    assertSame(capturedSecurityOrder.getClass(), SecurityOrder.class);

    assertDoesNotThrow(()-> orderService.executeMarketOrder(marketOrder));

    marketOrder.setSize(1000);
    assertThrows(IllegalArgumentException.class, ()-> orderService.executeMarketOrder(marketOrder));

  }

  @Test
  void handleBuyMarketOrder() {

    marketOrder.setOption(BUY);
    marketOrder.setSize(900);

    assertDoesNotThrow(() -> orderService.handleBuyMarketOrder(marketOrder, securityOrder, account));

    marketOrder.setSize(1000);
    assertThrows(IllegalArgumentException.class,
        () -> orderService.handleBuyMarketOrder(marketOrder, securityOrder, account));

  }

  @Test
  void handleSellMarketOrderTest() {

    marketOrder.setOption(SELL);
    marketOrder.setSize(900);

    when(positionRepository.findByAccountIdAndTicker(anyInt(), anyString()))
        .thenReturn(optPosition);
    when(optPosition.isEmpty()).thenReturn(false);
    when(optPosition.get()).thenReturn(position);
    when(position.getPosition()).thenReturn(1000);

    assertDoesNotThrow(() -> orderService.handleSellMarketOrder(marketOrder, securityOrder, account));

    when(optPosition.isEmpty()).thenReturn(true);
    assertThrows(IllegalArgumentException.class,
        () -> orderService.handleSellMarketOrder(marketOrder, securityOrder, account));

    marketOrder.setSize(1100);
    assertThrows(IllegalArgumentException.class,
        () -> orderService.handleSellMarketOrder(marketOrder, securityOrder, account));

  }
}