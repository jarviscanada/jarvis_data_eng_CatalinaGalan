package com.example.demo.service;

import com.example.demo.entity.Cat;
import com.example.demo.repository.CatInterface;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CatService {

  @Autowired
  private CatInterface catInterface;

  public Cat saveNewCat(Cat cat) {
    return catInterface.save(cat);
  }

  public Cat getCat(int id) {
    Optional<Cat> cat = catInterface.findById(id);
    return cat.get();
  }

  public List<Cat> listAllCats() {
    return catInterface.findAll();
  }

  public Cat updateCatName(Cat cat, String name) {
    Cat catDb = catInterface.findById(cat.getId()).get();
    catDb.setName(name);
    return catInterface.save(catDb);
  }

  public void deleteCat(Cat cat) {
    catInterface.delete(cat);
  }
}
