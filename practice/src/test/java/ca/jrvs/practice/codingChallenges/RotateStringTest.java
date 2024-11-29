package ca.jrvs.practice.codingChallenges;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class RotateStringTest {

  RotateString test = new RotateString();

  @Test
  void rotateValidStringTest() {
    String s = "hello";
    String goal = "ohell";
    assertTrue(test.rotateString(s, goal));
  }

  @Test
  void rotateInvalidStringTest() {
    String s = "hello";
    String goal = "hell";
    assertFalse(test.rotateString(s, goal));
  }
}