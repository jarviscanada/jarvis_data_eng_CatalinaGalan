package ca.jrvs.practice.dataStructure.list;

import java.util.ArrayList;
import java.util.Collections;

public class Employee implements Comparable<Employee> {
  private String name;
  private int id;
  private int age;
  private int salary;

  public Employee(String name, int id, int age, int salary) {
    this.name = name;
    this.id = id;
    this.age = age;
    this.salary = salary;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public int getSalary() {
    return salary;
  }

  public void setSalary(int salary) {
    this.salary = salary;
  }

  @Override
  public int compareTo(Employee o) {
    return Integer.compare(getId(), o.getId());
  }

  public static void main(String[] args) {
    Employee emp1 = new Employee("Carlos", 1, 45, 45000);
    Employee emp2 = new Employee("Sandra", 4, 34, 60000);
    Employee emp3 = new Employee("Lola", 8, 26, 45000);
    Employee emp4 = new Employee("Lucia", 2, 48, 85000);

    ArrayList<Employee> employees = new ArrayList<>();

    employees.add(emp1);
    employees.add(emp2);
    employees.add(emp3);
    employees.add(emp4);


    System.out.println("Before sort:");
    employees.forEach(employee -> System.out.println(employee.getName() + " " + employee.getId()));
    Collections.sort(employees);
    System.out.println("After sort (by id):");
    employees.forEach(employee -> System.out.println(employee.getName() + " " + employee.getId()));

    EmployeeSortBySalary sortBySalary = new EmployeeSortBySalary();
    employees.sort(sortBySalary);
    System.out.println("After sortBySalary:");
    employees.forEach(employee -> System.out.println(employee.getName() + " " + employee.getSalary()));

    EmployeeSortByAge sortByAge = new EmployeeSortByAge();
    employees.sort(sortByAge);
    System.out.println("After sortByAge:");
    employees.forEach(employee -> System.out.println(employee.getName() + " is " + employee.getAge() + " years old"));
  }
}
