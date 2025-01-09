package com.example.demo.controller;

import com.example.demo.entity.Cat;
import com.example.demo.service.CatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CatController {

  @Autowired
  public CatService catService;

  @PostMapping(path = "/cat")
  public String newCat(Cat cat) {
//    cat.setId();
    cat.setName("Luna");
    Cat newCat = catService.saveNewCat(cat);
    return newCat.getName();
  }

  @GetMapping(path = "/cat/{catId}")
  public String showCat(@PathVariable int catId) {
    return catService.getCat(catId);
  }
}
