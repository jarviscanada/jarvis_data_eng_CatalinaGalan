package ca.jrvs.apps.trading;

import static ca.jrvs.apps.trading.model.MarketOrder.Option.BUY;
import static ca.jrvs.apps.trading.model.MarketOrder.Option.SELL;

import ca.jrvs.apps.trading.config.AppConfig;
import ca.jrvs.apps.trading.controller.AppController;
import ca.jrvs.apps.trading.controller.DashboardController;
import ca.jrvs.apps.trading.controller.OrderController;
import ca.jrvs.apps.trading.controller.QuoteController;
import ca.jrvs.apps.trading.controller.TraderAccountController;
import ca.jrvs.apps.trading.model.MarketOrder;
import ca.jrvs.apps.trading.model.Quote;
import ca.jrvs.apps.trading.model.Trader;
import ca.jrvs.apps.trading.service.TraderAccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;

@SpringBootApplication
@EnableAutoConfiguration(exclude =  {JdbcTemplateAutoConfiguration.class})
//		DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
public class TradingApplication implements CommandLineRunner {

	private Logger logger = LoggerFactory.getLogger(AppConfig.class);

	@Autowired
	private AppController appController;

	@Autowired
	private QuoteController quoteController;

	@Autowired
	private TraderAccountController traderAccountController;

	@Autowired
	private DashboardController dashboardController;

	@Autowired
	private OrderController orderController;

	@Autowired
	private TraderAccountService traderAccountService;

	public static void main(String[] args) {

		SpringApplication app = new SpringApplication(TradingApplication.class);
		app.run(args);

	}

	@Override
	public void run(String... args){

		System.out.println("Trading App running...");

		try {
			appController.greeting();
			Trader trader = traderAccountController.createTrader("Carlos", "Fuentes", "1990-10-21",
					"Spain", "carlos@carlos.carlos");
			traderAccountService.deposit(1,15000000.00);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		MarketOrder marketOrder = new MarketOrder();
		marketOrder.setSize(400);
		marketOrder.setOption(BUY);
		marketOrder.setTicker("IBM");
		marketOrder.setTraderId(1);;

		MarketOrder marketOrder2 = new MarketOrder();
		marketOrder2.setSize(100);
		marketOrder2.setOption(SELL);
		marketOrder2.setTicker("IBM");
		marketOrder2.setTraderId(1);;

		try {
			Quote quote = quoteController.createNewQuote("IBM");
			System.out.println(quote.toString());
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		try {
			orderController.postMarketOrder(marketOrder);
			orderController.postMarketOrder(marketOrder2);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
}
