package com.example.demo.controller;

import com.example.demo.entity.Cat;
import com.example.demo.service.CatOwnerService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
//@RequestMapping("/cat")
public class CatController {

  @Autowired
  public CatOwnerService catOwnerService;

  @GetMapping("/")
  public String greeting() {
    return "Welcome to the Cat App";
  }

  @PostMapping("/cat")
  public Cat newCat(String name, String age) {
    Cat cat = new Cat();
    cat.setName(name);
    cat.setAge(age);
    System.out.println("New Cat Created!");
    return catOwnerService.saveNewCat(cat);
  }

  @GetMapping("/cats")
  public List<Cat> findAllCats() {
    return catOwnerService.listAllCats();
  }

  @GetMapping("/cat/{catName}")
  @ResponseBody
  public Cat showCat(@PathVariable String catName) {
    return catOwnerService.getCat(catName);
  }

  public Cat changeCatName(Cat cat, String name) {
    return catOwnerService.updateCatName(cat, name);
  }

  public void deleteCat(Cat cat) {
    catOwnerService.deleteCat(cat);
    System.out.println("Deleted from db: " + cat);
  }
}
