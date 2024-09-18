package ca.jrvs.practice.dataStructure.list;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class ArrayListAPIs {

  public static void main(String[] args) {
    List<String> animals = new ArrayList<>();

    animals.add("Tiger");
    animals.add("Cat");
    animals.add("Sloth");
    animals.add("LunaBug");

    int animalsSize = animals.size(); //returns int size (length)

    System.out.println(animals);
    System.out.println("The animals list has " + animals.size() + " animals");

    String lastElement = animals.get(animals.size() - 1); //returns first element in array

    boolean hasCat = animals.contains("Cat"); //return true if elements exists

    int catIndex = animals.indexOf("Cat"); //index of element

    boolean removeSloth = animals.remove("Sloth"); //return true when removed

    if (hasCat) {
      System.out.println("There is a cat in the list and the index is " + catIndex);
    } else {
      System.out.println("There is no cat");
    }

    if (removeSloth) System.out.println("Sloth has been removed");

    System.out.println(animals);

    String removedElement = animals.remove(catIndex); //returns removed element

    System.out.println("Now " + removedElement + " has been removed as well");

    System.out.println(animals);

    System.out.println(lastElement + " is the last (and best!) of the list");

    Employee emp = new Employee("Sofia", 8, 24, 20000);
    System.out.println(emp.getName());
  }
}
