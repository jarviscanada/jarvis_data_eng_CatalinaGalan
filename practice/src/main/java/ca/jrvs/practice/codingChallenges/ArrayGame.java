package ca.jrvs.practice.codingChallenges;

import java.util.HashMap;
import java.util.Map;

public class ArrayGame {

  int[] board;
  int k;
  Map<Integer, Boolean> results = new HashMap<>();

  public boolean canWin(int[] board, int k) {
    this.board = board;
    this.k = k;

    return solve(0);
  }

  public boolean solve(int i) {
    if (i >= board.length) {
      return true;
    }
    if (i < 0 || board[i] == 1) {
      return false;
    }

    if (results.containsKey(i)) {
      return results.get(i);
    }
    results.put(i, false);

    boolean result = solve(i - 1) || solve(i + 1) || solve(i + k);

    results.put(i, result);
    return result;
  }
}
