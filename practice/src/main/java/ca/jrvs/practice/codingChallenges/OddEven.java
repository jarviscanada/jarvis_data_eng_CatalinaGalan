package ca.jrvs.practice.codingChallenges;

/**
 * ticket: <a href="https://www.notion.so/jarviscanada/Check-if-a-number-is-even-or-odd-4cbdd9acd9504c04b9b4ef0213f8084d">...</a>
 */
public class OddEven {

  /**
   * Big-O: O(1)
   * Justification: it's an arithmetic operation
   */
  public String oddEvenMod(int i){
    return i % 2 == 0 ? "even" : "odd";
  }

  /**
   * Big-O:O(1)
   * Justification:
   */
  public String oddEvenBit(int i) {
    return (i ^ 1) > i ? "even" : "odd";
  }
}