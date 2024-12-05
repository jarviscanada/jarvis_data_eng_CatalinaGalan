package ca.jrvs.practice.codingChallenges;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PrintLetterWithNumberTest {

  PrintLetterWithNumber testing = new PrintLetterWithNumber();
  @Test
  void lettersWithNumbers() {
    String s = "abzcdAZ";
    String expected = "a1b2z26c3d4A27Z52";
    String actual = testing.lettersWithNumbers(s);
    assertEquals(expected, actual);
  }
}