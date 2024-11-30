package ca.jrvs.practice.codingChallenges;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ValidAnagram {

  public static boolean validAnagram(String s, String t) {

    // using sort
    char[] sChars = s.toCharArray();
    char[] tChars = t.toCharArray();
    Arrays.sort(sChars);
    Arrays.sort(tChars);

    return Arrays.equals(sChars, tChars);

    // using a HashMap
//    Map<Character, Integer> letters = new HashMap<>();
//
//    for (char c : s.toCharArray()) {
//      letters.put(c, letters.getOrDefault(c, 0) + 1);
//    }
//
//    for (char c : t.toCharArray()) {
//      letters.put(c, letters.getOrDefault(c, 0) - 1);
//    }
//
//    Set<Integer> result = new HashSet<>(letters.values());
//    return result.equals(Set.of(0));

    // using StringBuilder
    // StringBuilder trings = new StringBuilder(t);

    // for (int i = 0; i < s.length(); i++) {
    //     String check = Character.toString(s.charAt(i));
    //     if (trings.indexOf(check) >= 0) {
    //         int j = trings.indexOf(check);
    //         trings.deleteCharAt(j);
    //     } else {
    //         return false;
    //     }
    // }
    // return true;
  }
}
