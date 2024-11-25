package ca.jrvs.practice.codingChallenges;

import java.util.HashSet;
import java.util.Set;

public class ValidSudoku {


  public boolean validSudoku(char[][] board) {

    boolean answer = false;

    for (int i = 0; i < board.length; i++) {
      answer = checkSection(board[i]);
    }

    for (int i = 0; i < 9; i++) {
     answer = checkSection(buildCol(board, i));
    }

    for (int i = 0; i < 9; i++) {
      for (int j = 0; j < 9; j++) {
      answer = checkSection(buildBox(board, i, j));
//      char[] box = new char[9];
//        int index = 0;
//        for (int k = i; k < i + 3; k++) {
//          for (int l = j; l < j + 3; l++) {
//            box[index] = board[k][l];
//            index++;
//          }
//        }

      }
    }

    return answer;
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
    int index = 0;
    for (int j = 0; j < 9; j++) {
      col[index] = board[i][j];
    }
    return col;
  }

  public char[] buildBox(char[][] board, int i, int j) {
    char[] box = new char[9];
    int index = 0;
    for (int row = 0; row < i + 3; row ++) {
      for (int col = 0; col < j + 3; col++) {
        box[index] = board[row][col];
        index++;
      }
    }
    return box;
  }


}
