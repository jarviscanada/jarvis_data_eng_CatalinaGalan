package ca.jrvs.practice.codingChallenges;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class FillWithPaintTest {

  FillWithPaint fill = new FillWithPaint();

  @Test
  void countSectionsTest() {
    char[][] picture = {
        {'A', 'A', 'A', 'B', 'B', 'C'},
        {'A', 'A', 'A', 'B', 'C', 'C'},
        {'A', 'B', 'C', 'C', 'C', 'B'}
    };
    assertEquals(5, fill.countSections(picture));
  }

  @Test
  void countSectionsTest2() {
    char[][] picture = {
        {'A', 'A', 'B', 'B', 'B', 'C'},
        {'A', 'A', 'A', 'B', 'C', 'C'},
        {'A', 'B', 'A', 'C', 'C', 'B'}
    };
    assertEquals(5, fill.countSections(picture));
  }

  @Test
  void countSectionsTest3() {
    char[][] picture = {
        {'A', 'A', 'B', 'B', 'B', 'B'},
        {'A', 'A', 'B', 'A', 'C', 'C'},
        {'A', 'B', 'A', 'A', 'A', 'C'},
        {'B', 'B', 'A', 'C', 'C', 'C'}
    };
    assertEquals(5, fill.countSections(picture));
  }

  @Test
  void countSectionsTest4() {
    char[][] picture = {
        {},
    };
    assertEquals(0, fill.countSections(picture));
  }

  @Test
  void countSectionsTest5() {
    char[][] picture = {
        {'A'},
    };
    assertEquals(1, fill.countSections(picture));
  }
}