package com.example.demo;

import com.example.demo.controller.CatController;
import com.example.demo.entity.Cat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

	@Autowired
	public CatController catController;

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(DemoApplication.class);
		app.run(args);
	}

	@Override
	public void run(String... args) throws Exception {
		catController.greeting();
		String catName = "LunaBug";
		String catName2 = "Suertuda";
		String catAge = "5";
		String catAge2 = "Very old!";
		Cat cat = catController.newCat(catName, catAge);
		System.out.println(cat);
		Cat cat2 = catController.newCat(catName2, catAge2);
		System.out.println(cat2);
		catController.showCat(cat.getName());
		catController.findAllCats();
//		catController.changeCatName(cat, "Luna");
//		catController.deleteCat(cat2);
	}
}
