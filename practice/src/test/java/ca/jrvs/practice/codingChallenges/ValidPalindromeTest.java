package ca.jrvs.practice.codingChallenges;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ValidPalindromeTest {

  @Test
  void checkPalindromeTest1() {
    String s = "0P";
    assertFalse(ValidPalindrome.checkPalindromeRecursive(s));
    assertFalse(ValidPalindrome.checkPalindromeTwoPointer(s));
  }

  @Test
  void checkPalindromeTest2() {
    String s = "A man, a plan, a canal: Panama";
    assertTrue(ValidPalindrome.checkPalindromeRecursive(s));
    assertTrue(ValidPalindrome.checkPalindromeTwoPointer(s));
  }

  @Test
  void checkPalindromeTest3() {
    String s = " ";
    assertTrue(ValidPalindrome.checkPalindromeRecursive(s));
    assertTrue(ValidPalindrome.checkPalindromeTwoPointer(s));
  }
}