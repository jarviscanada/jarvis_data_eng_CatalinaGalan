package ca.jrvs.practice.codingChallenges;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringOnlyDigits {

  public boolean checkOnlyDigitsACSII(String s) {
    if (s.isEmpty()) {
      return false;
    }
    for (char c : s.toCharArray()) {
      if ((int) c < 48 || (int) c > 57) {
        return false;
      }
    }
    return true;
  }

  public boolean checkOnlyDigitsInteger(String s) {
    try {
      Integer.parseInt(s);
    } catch (NumberFormatException e) {
      return false;
    }
    return true;
  }

  public boolean checkOnlyDigitsRegex(String s) {
    // matches -> "\\d+"
    Pattern pattern = Pattern.compile("\\d+");
    Matcher matcher = pattern.matcher(s);

    return matcher.matches();
  }

}
