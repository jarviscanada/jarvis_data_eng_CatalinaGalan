package ca.jrvs.practice.codingChallenges;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class DuplicateCharacters {

  public char[] duplicateChars(String s) {
    char[] result = new char[s.length()];
    Set<Character> checkDuplicates = new HashSet<>();
    int i = 0;
    for (Character c : s.toCharArray()) {
      if (Character.isLetter(c) && !checkDuplicates.add(c)) {
        result[i] = c;
        i++;
      }
    }
    return Arrays.copyOfRange(result, 0, i);
  }
}
