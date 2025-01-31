package com.example.demo.controller;

import com.example.demo.entity.Cat;
import com.example.demo.entity.Owner;
import com.example.demo.service.CatOwnerService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

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

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("/cat/catName/{name}/catAge/{age}")
  @ResponseBody
  public Cat newCat(@PathVariable String name, @PathVariable String age) {
    try {
      Cat cat = new Cat();
      cat.setName(name);
      cat.setAge(age);
      System.out.println("New Cat Created!");
      return catOwnerService.createCatAndOwner(cat);
    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
    }
  }

  @GetMapping("/cat/{catName}")
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public Cat showCat(@PathVariable String catName) {
    return catOwnerService.getCat(catName);
  }

  @GetMapping("/owner/{catName}")
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public Owner showOwner(@PathVariable String catName) {
    return catOwnerService.getOwner(catName);
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
