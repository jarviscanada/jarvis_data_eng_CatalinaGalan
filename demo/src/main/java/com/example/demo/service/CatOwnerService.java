package com.example.demo.service;

import com.example.demo.entity.Cat;
import com.example.demo.repository.CatRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CatOwnerService {

  @Autowired
  private CatRepository catRepository;

  public Cat saveNewCat(Cat cat) {
    return catRepository.save(cat);
  }

  public Cat saveNewCat(String name, String age) {
    Cat cat = new Cat();
    cat.setName(name);
    cat.setAge(age);
    return catRepository.save(cat);
  }

  public Cat getCat(String name) {
    Optional<Cat> cat = catRepository.findByName(name);
    return cat.get();
  }

  public List<Cat> listAllCats() {
    return catRepository.findAll();
  }

  public Cat updateCatName(Cat cat, String name) {
    Cat catDb = catRepository.findById(cat.getId()).get();
    catDb.setName(name);
    return catRepository.save(catDb);
  }

  public void deleteCat(Cat cat) {
    catRepository.delete(cat);
  }
}
