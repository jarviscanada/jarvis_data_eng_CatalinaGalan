package ca.jrvs.practice.codingChallenges;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

public class TwoSumTest {

  TwoSum twoSum;

  @BeforeEach
  void init() {
    twoSum = new TwoSum();
  }

  @Test
  public void twoSumSortedTest() {
    int[] arr = {1, 3, 4, 6, 8};
    int target = 10;
    int[] expected = {2, 3};
    int[] actual1 = twoSum.twoSumBruteForce(arr, target);
    int[] actual2 = twoSum.twoSumSort(arr, target);
    int[] actual3 = twoSum.twoSumHashMap(arr, target);
    assertArrayEquals(expected, actual1);
    assertArrayEquals(expected, actual2);
    assertArrayEquals(expected, actual3);
  }

  @Test
  public void twoSumUnsortedTest() {
    int[] arr = {1, 4, 3, 8, 6};
    int target = 10;
    int[] expected = {1, 4};
    int[] actual1 = twoSum.twoSumBruteForce(arr, target);
    int[] actual3 = twoSum.twoSumHashMap(arr, target);
    int[] actual2 = twoSum.twoSumSort(arr, target);
    assertArrayEquals(expected, actual1);
    assertFalse(Arrays.equals(expected, actual2));
    assertArrayEquals(expected, actual3);
  }
}