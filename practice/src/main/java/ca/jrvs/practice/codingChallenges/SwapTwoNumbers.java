package ca.jrvs.practice.codingChallenges;

import java.util.Arrays;

public class SwapTwoNumbers {

  public int[] swapTwoNumbers(int[] nums) {
    nums[0] = nums[0] + nums[1];
    nums[1] = nums[0] - nums[1];
    nums[0] = nums[0] - nums[1];
    return nums;
  }

  public int[] swapTwoNumbersBinary(int[] nums) {
    nums[0] = nums[0] ^ nums[1];
    nums[1] = nums[0] ^ nums[1];
    nums[0] = nums[0] ^ nums[1];
    return nums;
  }

}
