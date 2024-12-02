package ca.jrvs.practice.codingChallenges;

import java.util.Stack;

public class QueueUsingStacks {

  static Stack<Integer> myStack1 = new Stack<>();
  static Stack<Integer> myStack2 = new Stack<>();

  public void push(int x) {
    if (myStack1.isEmpty() && !myStack2.isEmpty()) {
      while (!myStack2.isEmpty()) {
        myStack1.push(myStack2.pop());
      }
    }
    myStack1.push(x);
  }

  public int pop() {
    if (myStack2.isEmpty() && !myStack1.isEmpty()) {
      while (!myStack1.isEmpty()) {
        myStack2.push(myStack1.pop());
      }
    }
    return myStack2.pop();
  }

  public int peek() {
    if (myStack2.isEmpty() && !myStack1.isEmpty()) {
      while (!myStack1.isEmpty()) {
        myStack2.push(myStack1.pop());
      }
    }
    return myStack2.peek();
  }

  public boolean empty() {
    return myStack1.isEmpty() && myStack2.isEmpty();
  }
}
