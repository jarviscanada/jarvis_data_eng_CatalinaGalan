package ca.jrvs.practice.codingChallenges;

import static ca.jrvs.practice.codingChallenges.QueueUsingStacks.myStack1;
import static ca.jrvs.practice.codingChallenges.QueueUsingStacks.myStack2;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class QueueUsingStacksTest {

  QueueUsingStacks testing = new QueueUsingStacks();

  @Test
  void push() {
    testing.push(1);
    testing.push(2);
    testing.push(3);
    assertEquals(3, myStack1.peek());
  }

  @Test
  void pop() {
    myStack1.clear();
    myStack2.clear();
    myStack1.push(1);
    myStack1.push(2);
    myStack1.push(3);
    testing.pop();
    assertEquals(1, testing.peek());
  }

  @Test
  void peek() {
    myStack1.clear();
    myStack2.clear();
    myStack1.push(1);
    myStack1.push(2);
    myStack1.push(3);
    assertEquals(1, testing.peek());
  }

  @Test
  void empty() {
    if (!testing.empty()) {
      myStack1.clear();
      myStack2.clear();
    }
    assertTrue(testing.empty());
  }
}