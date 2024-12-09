package ca.jrvs.practice.codingChallenges;

import java.util.HashSet;
import java.util.Set;

public class Solution {

  public void returnSum(int[] arr, int n) {
    int[] v = new int[n];
    Set<Integer> set = new HashSet<>();

    for (int i = 0; i < n; i++) {
      v[i] = i + 1;
      set.add(i + 1);
    }

    int sum = 0;
    for (int j = 0; j < n; j++) {
      sum += v[j];
    }

    // v[i] == arr[i] + 1;
    // if (arr[i] == v[0] || == v[n - 1] || > 2 && < n - 1

    for (int i = 0; i < arr.length; i++) {
      if (set.contains(arr[i])) {
        int x = v[0];
        v[0] = v[n - 1];
        v[n - 1] = x;
      } else {
        set.remove(v[n - 1]);
        sum -= v[n - 1];
        v[n - 1] = arr[i];
        sum += v[n - 1];
        set.add(v[n - 1]);
      }
      System.out.println(sum);
    }
  }

  public static void main(String[] args) {
    int[] arr = new int[]{1, 7, -1, 3};
    int n = 5;
    Solution solution = new Solution();
    solution.returnSum(arr, n);
  }
}
