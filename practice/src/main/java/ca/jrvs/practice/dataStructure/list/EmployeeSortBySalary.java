package ca.jrvs.practice.dataStructure.list;

import java.util.Comparator;

public class EmployeeSortBySalary implements Comparator<Employee> {
  /**
   * @param e1 Employee 1 to be compared to
   * @param e2 Employee 2
   * @return integer result of comparison between two Employee.getSalary()
   */
  public int compare(Employee e1, Employee e2) {
    return Integer.compare(e1.getSalary(), e2.getSalary());
  }
}
