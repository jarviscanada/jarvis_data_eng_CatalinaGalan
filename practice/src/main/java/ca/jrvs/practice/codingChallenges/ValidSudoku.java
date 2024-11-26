package ca.jrvs.practice.codingChallenges;

import java.util.HashSet;
import java.util.Set;

public class ValidSudoku {

  public boolean validSudoku(char[][] board) {

    for (int i = 0; i < board.length; i++) {
      if (!checkSection(board[i])) {
        return false;
      };
    }

    for (int i = 0; i < 9; i++) {
     if (!checkSection(buildCol(board, i))) {
       return false;
     };
    }

    for (int i = 0; i < 9; i += 3) {
      for (int j = 0; j < 9; j += 3) {
        if (!checkSection(buildBox(board, i, j))) {
          return false;
        };
      }
    }

    return true;
  }

  public boolean checkSection(char[] section) {
    Set<Character> set = new HashSet<>();
    int count = 0;
    for (int i = 0; i < section.length; i++) {
      if (section[i] != '.') {
        set.add(section[i]);
        count ++;
      }
    }
    return set.size() == count;
  }

  public char[] buildCol(char[][] board, int i) {
    char[] col = new char[9];
    for (int j = 0; j < 9; j++) {
      col[j] = board[j][i];
    }
    return col;
  }

  public char[] buildBox(char[][] board, int i, int j) {
    char[] box = new char[9];
    int index = 0;
    for (int k = i; k < i + 3; k++) {
      for (int l = j; l < j + 3; l++) {
        box[index] = board[k][l];
        index++;
      }
    }
    return box;
  }
}
