package ca.jrvs.practice.codingChallenges;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CompareMapsTest {

  Map<Object, Object> map1;
  Map<Object, Object> map2;

  CompareMaps compare = new CompareMaps();

  @BeforeEach
  public void init() {
    map1 = new HashMap<>();
    map2 = new HashMap<>();
  }

  @Test
  void compareWithEquals() {
    map1.put("hello", "darling");
    map2.put("hello", "not darling");
    assertFalse(compare.compareWithEquals(map1, map2));
    map2.put("hello", "darling");
    assertTrue(compare.compareWithEquals(map1, map2));
  }

  @Test
  void implementEqualsTrue() {
    System.out.println("Testing for equal HashMaps...");
    map1.put("hello", "darling");
    map1.put("hi", "there");
    map1.put(1, 2);
    map2.put(1, 2);
    map2.put("hi", "there");
    map2.put("hello", "darling");
    assertTrue(compare.implementEquals(map1, map2));
  }

  @Test
  public void implementEqualsFalse() {
    System.out.println("Testing for same size HashMaps...");
    map1.put("hello", "darling");
    map2.put("hell", "darling");
    assertFalse(compare.implementEquals(map1, map2));
    System.out.println("Testing for different size HashMaps...");
    map1.put("hell", "darling");
    map2.put("hello", "darling");
    map2.put(1, 3);
    assertFalse(compare.implementEquals(map1, map2));
  }
}