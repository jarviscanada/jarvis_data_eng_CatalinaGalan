package ca.jrvs.apps.trading;

import ca.jrvs.apps.trading.config.AppConfig;
import ca.jrvs.apps.trading.controller.AppController;
import ca.jrvs.apps.trading.controller.QuoteController;
import ca.jrvs.apps.trading.controller.TraderAccountController;
import ca.jrvs.apps.trading.model.Trader;
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

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(TradingApplication.class);
		app.run(args);
	}

	@Override
	public void run(String... args){
		System.out.println("Trading App running...");
		try {
			appController.greeting();
			Trader trader = traderAccountController.createTrader("Carlos", "Fuentes", "1981-10-21",
					"Spain", "carlos@carlos.carlos");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
}
