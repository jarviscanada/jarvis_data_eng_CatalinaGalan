package ca.jrvs.practice.codingChallenges;

public class ArrayGame {

  int[] board;
  int k;

  public boolean canWin(int[] board, int k) {
    this.board = board;
    this.k = k;

    boolean answer = false;
    for (int i = 0; i <= board.length; i++) {
      answer = solve(i);
    }
    return answer;
  }

  public boolean solve(int i) {
    if (i < 0) {
      return false;
    }
    if (i >= board.length) {
      return true;
    }
    if (board[i] == 1) {
      return false;
    }
    board[i] = 1;
    return solve(i - 1) || solve(i + 1) || solve(i + k);
  }
}
