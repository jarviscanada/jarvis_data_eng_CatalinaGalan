package ca.jrvs.practice.codingChallenges;

import java.util.Map;

public class CompareMaps {

  public <K, V> boolean compareWithEquals(Map<K, V> map1, Map<K, V> map2) {
    return map1.equals(map2);
  }

  public <K, V> boolean implementEquals(Map<K, V> map1, Map<K, V> map2) {
    if (map1.size() != map2.size()) {
      return false;
    }
    for (Object key : map1.keySet()) {
      if (!map1.get(key).equals(map2.get(key))) {
        return false;
      }
    }
    return true;
  }

}
