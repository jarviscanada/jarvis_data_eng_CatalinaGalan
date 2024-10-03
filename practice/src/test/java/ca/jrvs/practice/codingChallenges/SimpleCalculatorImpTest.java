package ca.jrvs.practice.codingChallenges;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

public class SimpleCalculatorImpTest {

  SimpleCalculator calculator;

  @BeforeEach
  void init() {
    calculator = new SimpleCalculatorImp();
  }

  @Test
  public void testAdd() {
    int expected = 2;
    int actual = calculator.add(1, 1);
    assertEquals(expected, actual);
  }

  @Test
  public void testSubtract() {
    int expected = 0;
    int actual = calculator.subtract(1, 1);
    assertEquals(expected, actual);
  }

  @Test
  public void testMultiply() {
    int expected = 4;
    int actual = calculator.multiply(2, 2);
    assertEquals(expected, actual);
  }

  @Test
  public void testDivide() {
    double expected = 2.5;
    double actual = calculator.divide(5, 2);
    assertEquals(expected, actual);
  }
}