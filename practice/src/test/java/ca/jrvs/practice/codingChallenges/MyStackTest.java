package ca.jrvs.practice.codingChallenges;

import static org.junit.jupiter.api.Assertions.*;

import java.util.LinkedList;
import java.util.Queue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class MyStackTest {

  MyStack myStack = new MyStack();

  @Test
  void myStack() {
    myStack.push(1);
    myStack.push(2);
    assertEquals(2, myStack.top());
    myStack.push(3);
    assertEquals(3, myStack.pop());
    assertEquals(2, myStack.pop());
    assertFalse(myStack.empty());
    assertEquals(1, myStack.pop());
    assertTrue(myStack.empty());
  }
}