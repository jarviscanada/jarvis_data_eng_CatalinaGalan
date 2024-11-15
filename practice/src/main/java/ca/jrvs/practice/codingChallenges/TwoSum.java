package ca.jrvs.practice.codingChallenges;

import java.util.Arrays;
import java.util.HashMap;

/**
 * <a href="https://www.notion.so/jarvisdev/Two-Sum-13dcc128f9de8169973ee839a9424eab?pvs=4">...</a>
 *
 */
public class TwoSum {

  public int[] twoSumBruteForce(int[] arr, int target) {
    // first loop through arr and save value in var
    for (int i = 0; i < arr.length; i++) {
      int num = arr[i];
      // second loop comparing to num and sum of num + num2
      for (int j = i + 1; j < arr.length; j++) {
        int num2 = arr[j];
        if (num + num2 == target) {
          return new int[]{i, j};
        }
      }
    }
    return new int[] {};
  }

  /**
   *
   * @param arr given int array
   * @param target given int
   * @return int array of 2 index of elements in arr that add to target
   * using sort O(n)
   */
  public int[] twoSumSort(int[] arr, int target) {
    Arrays.sort(arr);
    int l = 0;
    int r = arr.length - 1;
    while (arr[l] + arr[r] != target) {
      if (arr[l] + arr[r] > target) {
        r--;
      } else if (arr[l] + arr[r] < target) {
        l++;
      }
    }
    return new int[] {l, r};
  }

  /**
   *
   * @param arr int[]
   * @param target int
   * @return int[]
   */
  public int[] twoSumHashMap(int[] arr, int target) {
    HashMap<Integer, Integer> arrMap = new HashMap<>();
    for (int i = 0; i < arr.length; i++) {
      arrMap.put(arr[i], i);
      int key = target - arr[i];
      if (arrMap.containsKey(key)) {
        return new int[] {arrMap.get(key), i};
      }
    }
    return new int[] {};
  }
}

