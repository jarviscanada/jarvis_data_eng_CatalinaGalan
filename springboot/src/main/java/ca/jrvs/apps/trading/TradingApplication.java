package ca.jrvs.apps.trading;

import ca.jrvs.apps.trading.controller.AppController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class TradingApplication implements CommandLineRunner {

	@Autowired
	public AppController appController;

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(TradingApplication.class);
		app.run(args);
	}

	@Override
	public void run(String... args) throws Exception{
		System.out.println("Trading App running...");
		appController.greeting();
		appController.apiDemo();
	}
}
