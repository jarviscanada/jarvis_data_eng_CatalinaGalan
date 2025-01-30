package com.example.demo.service;

import com.example.demo.entity.Cat;
import com.example.demo.entity.Owner;
import com.example.demo.repository.CatRepository;
import com.example.demo.repository.OwnerRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CatOwnerService {

  @Autowired
  private CatRepository catRepository;

  @Autowired
  private OwnerRepository ownerRepository;

  public Cat saveCat(Cat cat) {
    return catRepository.save(cat);
  }

  public Owner saveOwner(Owner owner) {return ownerRepository.save(owner); }

  public Cat saveCat(String name, String age) {
    Cat cat = new Cat();
    cat.setName(name);
    cat.setAge(age);
    return saveCat(cat);
  }

  public Owner saveOwner(Cat cat) {
    Owner owner = new Owner();
    owner.setCat(cat);
//    owner.setId(cat.getId());
    owner.setName(cat.getName() + "'s Owner");
    return saveOwner(owner);
  }

  public Cat getCat(String name) {
    Optional<Cat> cat = catRepository.findByName(name);
    return cat.get();
  }

  public List<Cat> listAllCats() {
    return catRepository.findAll();
  }

//  public Cat updateCatAge(Cat cat, String age) {
//    Cat catDb = catRepository.findByName(cat.getName()).get();
//    catDb.setAge(age);
//    return saveCat(catDb);
//  }

  public void deleteCat(Cat cat) {
    catRepository.delete(cat);
  }
}
