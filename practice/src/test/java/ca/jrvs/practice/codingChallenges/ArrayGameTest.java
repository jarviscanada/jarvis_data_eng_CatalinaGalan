package ca.jrvs.practice.codingChallenges;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ArrayGameTest {

  ArrayGame game = new ArrayGame();

  @Test
  void canWinTrue() {
    int[] board = new int[] {0, 0, 0, 1, 1, 1};
    assertTrue(game.canWin(board, 4));
  }

  @Test
  void canWinFalse() {
    int[] board = new int[] {1};
    assertFalse(game.canWin(board, 0));
  }
}