package ca.jrvs.practice.codingChallenges;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import org.junit.jupiter.api.Test;

class ArrayGameTest {

  ArrayGame game = new ArrayGame();

  @Test
  void canWin() {
    int[] board = new int[] {0, 0, 0, 1, 1, 1};
    assertTrue(game.canWin(board, 5));
  }
}