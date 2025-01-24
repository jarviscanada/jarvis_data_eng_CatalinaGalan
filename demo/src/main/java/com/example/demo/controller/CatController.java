package com.example.demo.controller;

import com.example.demo.entity.Cat;
import com.example.demo.service.CatService;
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
  public CatService catService;

  @GetMapping("/")
  public String greeting() {
    return "Welcome to the Cat App";
  }

  @PostMapping("/cat")
  public Cat newCat(Cat cat, String name, String age) {
//    cat.setId();
    cat.setName(name);
    cat.setAge(age);
    System.out.println("New Cat Created!");
    return catService.saveNewCat(cat);
  }

  @GetMapping("/cats")
  public List<Cat> findAllCats() {
    return catService.listAllCats();
  }

  @GetMapping("/cat/{catId}")
  @ResponseBody
  public Cat showCat(@PathVariable int catId) {
    return catService.getCat(catId);
  }

  public Cat changeCatName(Cat cat, String name) {
    return catService.updateCatName(cat, name);
  }

  public void deleteCat(Cat cat) {
    catService.deleteCat(cat);
    System.out.println("Deleted from db: " + cat);
  }
}
