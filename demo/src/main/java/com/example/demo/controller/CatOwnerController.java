package com.example.demo.controller;

import com.example.demo.entity.Cat;
import com.example.demo.entity.Owner;
import com.example.demo.service.CatOwnerService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
//@RequestMapping("/cat")
public class CatOwnerController {

  @Autowired
  public CatOwnerService catOwnerService;

  @GetMapping("/")
  public String greeting() {
    return "Welcome to the Cat App";
  }

  @PostMapping("/cat")
  public Cat newCat(String name, String age) {
    Cat cat = catOwnerService.saveCat(name, age);
    Owner owner = catOwnerService.saveOwner(cat);
//    cat.setOwner(owner);
    System.out.println("New Cat and Owner Created!");
    return cat;
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

//  @PutMapping("/cat/{cat}")
//  @ResponseBody
//  public Cat changeCatAge(@RequestBody Cat cat, String age) {
//    return catOwnerService.updateCatAge(cat, age);
//  }

//  public void deleteCat(Cat cat) {
//    catOwnerService.deleteCat(cat);
//    System.out.println("Deleted from db: " + cat);
//  }
}
