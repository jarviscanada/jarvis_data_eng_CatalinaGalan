package ca.jrvs.practice.codingChallenges;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class NotSoSimpleCalculatorImpTest {

  NotSoSimpleCalculator calculator;

  @Mock
  SimpleCalculator calMock;

  @BeforeEach
  void init() {
//    calMock = new SimpleCalculatorImp();
    calculator = new NotSoSimpleCalculatorImp(calMock);
  }

  @Test
  void power() {
    int expected = 8;
    int actual = calculator.power(2, 3);
    assertEquals(expected, actual);
  }

  @Test
  void abs() {
    when(calMock.multiply(anyInt(), anyInt())).thenReturn(1);
    int expected = 1;
    int actual = calculator.abs(-1);
    assertEquals(expected, actual);
  }

  @Test
  void sqrt() {
    double expected = 4.0;
    double actual = calculator.sqrt(16);
    assertEquals(expected, actual);
  }
}