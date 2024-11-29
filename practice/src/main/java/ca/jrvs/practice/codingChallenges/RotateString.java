package ca.jrvs.practice.codingChallenges;

public class RotateString {
  public boolean rotateString(String s, String goal) {
    StringBuilder string = new StringBuilder(s);

    int i = 0;
    while (i < string.length()) {
      if (string.toString().equals(goal)) {
        return true;
      } else {
        string.append(string.charAt(0));
        string.deleteCharAt(0);
      }
      i++;
    }
    return false;
  }
}
