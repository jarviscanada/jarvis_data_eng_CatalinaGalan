package ca.jrvs.practice.codingChallenges;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Fibonacci {

  public int fibonacci(int n) {
    return (n <= 1) ? n : fibonacci(n - 1) + fibonacci( n - 2);
  }
}
