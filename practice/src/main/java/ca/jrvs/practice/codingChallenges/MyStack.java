package ca.jrvs.practice.codingChallenges;

import java.util.LinkedList;
import java.util.Queue;

public class MyStack {
  static Queue<Integer> tempQueue = new LinkedList<>();

  void myStack() {
  }

  public void push(int x) {
    tempQueue.offer(x);
  }

  public int pop() {
    for (int i = 1; i < tempQueue.size(); i++) {
      tempQueue.offer(tempQueue.poll());
    }
    return tempQueue.poll();
  }

  public int top() {
    for (int i = 1; i < tempQueue.size(); i++) {
      tempQueue.offer(tempQueue.poll());
    }
    int n = tempQueue.peek();
    tempQueue.offer(tempQueue.poll());
    return n;
  }

  public boolean empty() {
    return tempQueue.isEmpty();
  }
}
