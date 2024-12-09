package ca.jrvs.practice.codingChallenges;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import org.junit.jupiter.api.Test;

class DuplicateCharactersTest {

  DuplicateCharacters testing = new DuplicateCharacters();
  @Test
  void duplicateCharsTest() {
    String s = "A black cat";
    char[] expected = new char[]{'c', 'a'};
    char[] actual = testing.duplicateChars(s);
    assertTrue(Arrays.equals(expected, actual));
  }
}