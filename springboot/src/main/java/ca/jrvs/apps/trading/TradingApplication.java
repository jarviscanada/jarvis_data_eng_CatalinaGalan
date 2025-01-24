package ca.jrvs.apps.trading;

import ca.jrvs.apps.trading.config.AppConfig;
import ca.jrvs.apps.trading.controller.AppController;
import ca.jrvs.apps.trading.controller.QuoteController;
import ca.jrvs.apps.trading.util.MarketDataHttpHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.PropertySource;


@SpringBootApplication(exclude =  {JdbcTemplateAutoConfiguration.class})
//		DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
public class TradingApplication implements CommandLineRunner {

	private Logger logger = LoggerFactory.getLogger(AppConfig.class);

	@Autowired
	private AppController appController;

	@Autowired
	private QuoteController quoteController;

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(TradingApplication.class);
		app.run(args);
	}

	@Override
	public void run(String... args){
		System.out.println("Trading App running...");
		try {
			appController.greeting();
//			quoteController.getQuote("IBM");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
}
