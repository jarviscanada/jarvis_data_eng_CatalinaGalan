package ca.jrvs.practice.codingChallenges;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import org.junit.jupiter.api.Test;

class SwapTwoNumbersTest {

  SwapTwoNumbers testing = new SwapTwoNumbers();
  @Test
  void swapTwoNumbersTest() {
    int[] nums = new int[] {79, 132};
    int[] expected = new int[] {132, 79};
    int[] actual = testing.swapTwoNumbers(nums);
    assertTrue(Arrays.equals(expected, actual));
  }

  @Test
  void swapTwoNumbersBinaryTest() {
    int[] nums = new int[] {79, 132};
    int[] expected = new int[] {132, 79};
    int[] actual = testing.swapTwoNumbersBinary(nums);
    assertTrue(Arrays.equals(expected, actual));
  }

  @Test
  void swapTwoNumbersMaxValueTest() {
    int[] nums = new int[] {Integer.MAX_VALUE, Integer.MIN_VALUE};
    int[] expected = new int[] {Integer.MIN_VALUE, Integer.MAX_VALUE};
    int[] actual = testing.swapTwoNumbers(nums);
    assertTrue(Arrays.equals(expected, actual));
  }

  @Test
  void swapTwoNumbersMaxValueBinaryTest() {
    int[] nums = new int[] {Integer.MAX_VALUE, Integer.MIN_VALUE};
    int[] expected = new int[] {Integer.MIN_VALUE, Integer.MAX_VALUE};
    int[] actual = testing.swapTwoNumbersBinary(nums);
    assertTrue(Arrays.equals(expected, actual));
  }

}