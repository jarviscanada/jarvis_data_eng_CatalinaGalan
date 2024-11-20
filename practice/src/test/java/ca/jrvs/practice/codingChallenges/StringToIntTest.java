package ca.jrvs.practice.codingChallenges;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class StringToIntTest {

  StringToInt test = new StringToInt();

  @Test
  void myAtoiParseEmpty() {
    String s = "";
    int expected = 0;
    int actual = test.myAtoiParse(s);
    assertEquals(expected, actual);
  }

  @Test
  void myAtoiParseMin() {
    String s = "-91283472332";
    int expected = -2147483648;
    int actual = test.myAtoiParse(s);
    assertEquals(expected, actual);
  }

  @Test
  void myAtoiParseMax() {
    String s = "91283472332";
    int expected = 2147483647;
    int actual = test.myAtoiParse(s);
    assertEquals(expected, actual);
  }

  @Test
  void myAtoiParseTwoSymbols() {
    String s = "-+123";
    int expected = 0;
    int actual = test.myAtoiParse(s);
    assertEquals(expected, actual);
  }

  @Test
  void myAtoiParseAllSpaces() {
    String s = "   ";
    int expected = 0;
    int actual = test.myAtoiParse(s);
    assertEquals(expected, actual);
  }

  @Test
  void myAtoiParseNonDigit() {
    String s = "   uj-+ ";
    int expected = 0;
    int actual = test.myAtoiParse(s);
    assertEquals(expected, actual);
  }

  @Test
  void myAtoiParseDigitFirst() {
    String s = " 123-9  l";
    int expected = 123;
    int actual = test.myAtoiParse(s);
    assertEquals(expected, actual);
  }

  @Test
  void myAtoiParseDigitLast() {
    String s = " -L   123";
    int expected = 0;
    int actual = test.myAtoiParse(s);
    assertEquals(expected, actual);
  }

  @Test
  void myAtoiParseDigits() {
    String s = "123";
    int expected = 123;
    int actual = test.myAtoiParse(s);
    assertEquals(expected, actual);
  }

  @Test
  void myAtoiNoParseEmpty() {
    String s = "";
    int expected = 0;
    int actual = test.myAtoiNoParse(s);
    assertEquals(expected, actual);
  }

  @Test
  void myAtoiNoParseMin() {
    String s = "-91283472332";
    int expected = -2147483648;
    int actual = test.myAtoiNoParse(s);
    assertEquals(expected, actual);
  }

  @Test
  void myAtoiNoParseMax() {
    String s = "91283472332";
    int expected = 2147483647;
    int actual = test.myAtoiNoParse(s);
    assertEquals(expected, actual);
  }

  @Test
  void myAtoiNoParseTwoSymbols() {
    String s = "-+123";
    int expected = 0;
    int actual = test.myAtoiNoParse(s);
    assertEquals(expected, actual);
  }

  @Test
  void myAtoiNoParseAllSpaces() {
    String s = "   ";
    int expected = 0;
    int actual = test.myAtoiNoParse(s);
    assertEquals(expected, actual);
  }

  @Test
  void myAtoiNoParseNonDigit() {
    String s = "   uj-+ ";
    int expected = 0;
    int actual = test.myAtoiNoParse(s);
    assertEquals(expected, actual);
  }

  @Test
  void myAtoiNoParseDigitFirst() {
    String s = " 123-9  l";
    int expected = 123;
    int actual = test.myAtoiNoParse(s);
    assertEquals(expected, actual);
  }

  @Test
  void myAtoiNoParseDigitLast() {
    String s = " -L   123";
    int expected = 0;
    int actual = test.myAtoiNoParse(s);
    assertEquals(expected, actual);
  }

  @Test
  void myAtoiNoParseDigits() {
    String s = "123";
    int expected = 123;
    int actual = test.myAtoiNoParse(s);
    assertEquals(expected, actual);
  }


}