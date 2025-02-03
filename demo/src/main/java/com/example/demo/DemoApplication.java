package com.example.demo;

import com.example.demo.controller.CatOwnerController;
import com.example.demo.entity.Cat;
import com.example.demo.entity.Owner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

	@Autowired
	public CatOwnerController catOwnerController;

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(DemoApplication.class);
		app.run(args);
	}

	@Override
	public void run(String... args) throws Exception {
		catOwnerController.greeting();
		String catName = "LunaBug";
		String catAge = "5";
		Cat cat = catOwnerController.newCat(catName, catAge);
		System.out.println(cat);
//		Owner owner = catOwnerController.newOwner(cat);
//		String catName2 = "Suertuda";
//		String catAge2 = "Very old!";
//		Cat cat2 = catOwnerController.newCat(catName2, catAge2);
//		System.out.println(cat2);
		catOwnerController.showCat(cat.getName());
		catOwnerController.showOwner(cat.getName());
	}
}
