package com.example.demo.service;

import com.example.demo.entity.Cat;
import com.example.demo.repository.CatInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CatService {

  @Autowired
  private CatInterface catInterface;

  public Cat saveNewCat(Cat cat) {
    return catInterface.save(cat);
  }

  public String getCat(int id) {
    return catInterface.findById(id).toString();
  }
}
