package com.example.demo.service;

import static java.util.Objects.isNull;

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

  public Cat createCatAndOwner(Cat cat) {
    if (cat.getName().isEmpty() || cat.getName() == null || cat.getAge().isEmpty() || cat.getAge() == null) {
      throw new IllegalArgumentException("all fields must be present");
    }

    Owner owner = new Owner();
    owner.setCat(cat);
    owner.setName(cat.getName() + "'s owner");
    cat.setOwner(owner);


    Cat savedCat = catRepository.save(cat);
    return savedCat;
  }

  public Cat getCat(String name) {
    Optional<Cat> cat = catRepository.findByName(name);
    return cat.get();
  }

  public Owner getOwner(String catName) {
    Optional<Cat> cat = catRepository.findByName(catName);
    Owner owner = cat.get().getOwner();
    return owner;
  }
}
