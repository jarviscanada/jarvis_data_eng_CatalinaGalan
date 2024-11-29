package ca.jrvs.practice.codingChallenges;

import java.util.Stack;

public class ValidPalindrome {

  public static boolean checkPalindromeTwoPointer(String s) {
    // two pointers
    char[] charsArray = s.toLowerCase().toCharArray();
    int l = 0;
    int r = charsArray.length - 1;

    while (l < r) {
      if (!Character.isLetterOrDigit(charsArray[r])) {
        r--;
      } else if (!Character.isLetterOrDigit(charsArray[l])) {
        l++;
      } else if (charsArray[l] == charsArray[r]) {
        r--;
        l++;
      } else {
        return false;
      }
    }
    return true;
  }

  public static boolean checkPalindromeRecursive(String s) {
    // recusion
    Stack<Character> forward = new Stack<>();
    Stack<Character> backward = new Stack<>();
    for (Character letter : s.toLowerCase().toCharArray()) {
      if (Character.isLetterOrDigit(letter)) {
        forward.push(letter);
      }
    }

    ValidPalindrome solution = new ValidPalindrome();
    backward = solution.checkPalindromeRecursive((Stack<Character>) forward.clone(), backward);

    return forward.equals(backward);
  }

  public Stack<Character> checkPalindromeRecursive(Stack<Character> forward, Stack<Character> backward) {
    ValidPalindrome solution = new ValidPalindrome();
    if (!forward.isEmpty()) {
      backward.push(forward.pop());
      backward = solution.checkPalindromeRecursive(forward, backward);
    }
    return backward;
  }
}
