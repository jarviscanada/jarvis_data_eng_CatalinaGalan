package ca.jrvs.practice.codingChallenges;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Fibonacci {

  public int fibonacci(int n) {
//    return (n <= 1) ? n : fibonacci(n - 1) + fibonacci(n - 2);
    int[] array = new int[n + 1];
    if (n <= 1) {
      return n;
    }
    array[1] = 1;
    for (int i = 2; i <= n; i++) {
      array[i] = array[i - 1] + array[i - 2];
    }
    return array[n];
  }

  public int climbStairsRecursion(int n) {
    return (n <= 2) ? n : climbStairsRecursion(n - 1) + climbStairsRecursion(n - 2);
  }

  public int climbingStairsMemo(int n, int... array) {
    if (n <= 2) {
      return n;
    }
    if (array.length == 0){
      array = new int[n + 1];
      array[1] = 1;
      array[2] = 2;
    }
    if (array[n] != 0) {
      return array[n];
    } else {
      array[n] = climbingStairsMemo(n - 1, array) + climbingStairsMemo(n - 2, array);
    }
    return array[n];
  }

  public int climbingStairsBottom(int n) {
    int[] array = new int[n + 1];
    array[1] = 1;
    array[2] = 2;
    for (int i = 3; i <= n; i++) {
      array[i] = array[i - 1] + array[i - 2];
    }
    return array[n];
  }
}
