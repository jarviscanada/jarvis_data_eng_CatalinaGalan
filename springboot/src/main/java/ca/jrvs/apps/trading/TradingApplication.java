package ca.jrvs.apps.trading;

import ca.jrvs.apps.trading.controller.AppController;
import ca.jrvs.apps.trading.controller.DashboardController;
import ca.jrvs.apps.trading.controller.OrderController;
import ca.jrvs.apps.trading.controller.QuoteController;
import ca.jrvs.apps.trading.controller.TraderAccountController;
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

	private static Logger logger = LoggerFactory.getLogger(TradingApplication.class);

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
		try {
			app.run(args);
		} catch (Exception e) {
			logger.error(e.getMessage(), e.getCause());
		}
	}

	@Override
	public void run(String... args){
		try {
			appController.greeting();
		} catch (Exception e) {
			logger.error(e.getMessage(), e.getCause());
		}
	}
}
