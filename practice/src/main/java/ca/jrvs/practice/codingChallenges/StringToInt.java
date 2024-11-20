package ca.jrvs.practice.codingChallenges;

public class StringToInt {

  public int myAtoiParse(String s) {

    long answer = 0;
    int symbol = 1;
    int i = 0;
    s = s.trim();

    if (s.isEmpty()) return 0;

    if (s.charAt(0) == '-') {
      symbol = -1;
      i++;
    }
    if (s.charAt(0) == '+') {
      i++;
    }

    while (i < s.length()) {
      if (Character.isDigit(s.charAt(i))) {
        answer = answer * 10 + Integer.parseInt(Character.toString(s.charAt(i)));
        if (answer * symbol > Integer.MAX_VALUE) {
          return Integer.MAX_VALUE;
        } else if (answer * symbol <= Integer.MIN_VALUE) {
          return Integer.MIN_VALUE;
        }
        i++;
      } else {
        break;
      }
    }
    return (int)answer * symbol;
  }

  public int myAtoiNoParse(String s) {
    return 0;
  }
}
