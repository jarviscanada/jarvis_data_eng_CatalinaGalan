package ca.jrvs.practice.codingChallenges;

public class FillWithPaint {

  public int countSections(char[][] picture) {
    int result = 0;
    for (int i = 0; i < picture.length; i++) {
      for (int j = 0; j < picture[0].length; j++) {
        if (picture[i][j] != '.') {
          char[][] nextPicture = checkAdjacentCells(picture, i, j);
          if (nextPicture != null) { result ++; }
        }
      }
    }
    return result;
  }

  public char[][] checkAdjacentCells(char[][] picture, int x, int y) {
    char currentChar = picture[x][y];
    picture[x][y] = '.';

    if (y > 0 && currentChar == picture[x][y - 1]) {
      checkAdjacentCells(picture, x, y - 1);
    }

    if (x > 0 && currentChar == picture[x - 1][y]) {
      checkAdjacentCells(picture, x - 1, y);
    }

    if (y + 1 < picture[0].length && currentChar == picture[x][y + 1]) {
      checkAdjacentCells(picture, x, y + 1);
    }

    if (x + 1 < picture.length && currentChar == picture[x + 1][y]) {
      checkAdjacentCells(picture, x + 1, y);
    }

    return picture;
  }
}
