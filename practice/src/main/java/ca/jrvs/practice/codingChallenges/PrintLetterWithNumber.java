package ca.jrvs.practice.codingChallenges;

import java.util.Arrays;

public class PrintLetterWithNumber {
  // return string as letterIndexletterIndexletterIndex...
  // a = 1
  // A = 27
  // ACSII a = 97 ; A = 41

  public String lettersWithNumbers(String s) {
    char[] newString = new char[s.length() * 3];

    int n;
    String number;
    int i = 0;
    for (char c : s.toCharArray()) {
      if (Character.isLowerCase(c)) {
        n = (c - 96);
      } else {
        n = (c - 38);
      }
      newString[i] = c;
      number = Integer.toString(n);
      for (char num : number.toCharArray()) {
        i++;
        newString[i] = num;
      }
      i++;
    }
    return String.copyValueOf(newString, 0, i);
  }

}
