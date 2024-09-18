package ca.jrvs.practice.dataStructure.list;

import java.util.Comparator;

public class EmployeeSortByAge implements Comparator<Employee> {
  public int compare(Employee emp1, Employee emp2) {
    return Integer.compare(emp1.getAge(), emp2.getAge());
  }
}
