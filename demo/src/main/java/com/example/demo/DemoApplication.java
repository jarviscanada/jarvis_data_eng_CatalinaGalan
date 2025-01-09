package com.example.demo;

import com.example.demo.controller.CatController;
import com.example.demo.entity.Cat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
		Cat cat = new Cat();
		Cat cat2 = new Cat();
		String catName = "LunaBug";
		String catName2 = "Suertuda";
		String catAge = "5";
		String catAge2 = "Very old!";
		catController.newCat(cat, catName, catAge);
		System.out.println(cat);
		catController.newCat(cat2, catName2, catAge2);
		System.out.println(cat2);
		catController.showCat(cat.getId());
		catController.findAllCats();
		catController.changeCatName(cat, "Luna");
		catController.deleteCat(cat2);
	}
}
