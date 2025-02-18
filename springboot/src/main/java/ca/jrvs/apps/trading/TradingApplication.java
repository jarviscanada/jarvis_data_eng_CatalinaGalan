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
			traderAccountController.createTrader("Carlos", "Fuentes",
					"1990-10-21", "Spain", "carlos@carlos.carlos");
			traderAccountService.deposit(1,150000.00);
			traderAccountController.createTrader("Alex", "WTV",
					"1998-05-11", "Paraguay", "alex@alex.alex");
			traderAccountService.deposit(1,150000.00);
			traderAccountService.deposit(2,150000.00);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		MarketOrder marketOrder0 = new MarketOrder();
		marketOrder0.setSize(40);
		marketOrder0.setOption(BUY);
		marketOrder0.setTicker("IBM");
		marketOrder0.setTraderId(1);
//		Position position1 = positionService.getPositionBy

		MarketOrder marketOrder1 = new MarketOrder();
		marketOrder1.setSize(10);
		marketOrder1.setOption(BUY);
		marketOrder1.setTicker("MSFT");
		marketOrder1.setTraderId(2);

		MarketOrder marketOrder2 = new MarketOrder();
		marketOrder2.setSize(15);
		marketOrder2.setOption(SELL);
		marketOrder2.setTicker("IBM");
		marketOrder2.setTraderId(1);

		try {
			quoteController.createNewQuote("IBM");
			quoteController.createNewQuote("MSFT");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		try {
			orderController.postMarketOrder(marketOrder0);
			orderController.postMarketOrder(marketOrder1);
			orderController.postMarketOrder(marketOrder2);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
}
