package ca.jrvs.practice.codingChallenges;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class FibonacciTest {

  Fibonacci fibonacci = new Fibonacci();

  @Test
  public void calculateFibonacciTestMinValue() {
    int expected = 0;
    int actual = fibonacci.fibonacci(0);
    assertEquals(expected, actual);
  }

  @Test
  public void calculateFibonacciTestMaxValue() {
    int expected = 832040;
    int actual = fibonacci.fibonacci(30);
    assertEquals(expected, actual);
  }
}
