package ca.jrvs.practice.codingChallenges;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StringOnlyDigitsTest {

  StringOnlyDigits tester = new StringOnlyDigits();

  @Test
  void checkOnlyDigitsACSII() {
    String valid = "1234";
    String invalid = "12,34";
    String empty = "";
    assertTrue(tester.checkOnlyDigitsACSII(valid));
    assertFalse(tester.checkOnlyDigitsACSII(invalid));
    assertFalse(tester.checkOnlyDigitsACSII(empty));
  }

  @Test
  void checkOnlyDigitsInteger() {
    String valid = "1234";
    String invalid = "12,34";
    String empty = "";
    assertTrue(tester.checkOnlyDigitsInteger(valid));
    assertFalse(tester.checkOnlyDigitsInteger(invalid));
    assertFalse(tester.checkOnlyDigitsInteger(empty));
  }

  @Test
  void checkOnlyDigitsRegex() {
    String valid = "1234";
    String invalid = "12,34";
    String empty = "";
    assertTrue(tester.checkOnlyDigitsRegex(valid));
    assertFalse(tester.checkOnlyDigitsRegex(invalid));
    assertFalse(tester.checkOnlyDigitsRegex(empty));
  }
}