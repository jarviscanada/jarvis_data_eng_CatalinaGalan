package ca.jrvs.practice.codingChallenges;

public class NotSoSimpleCalculatorImp implements NotSoSimpleCalculator {

  /**
   * the object NotSoSimpleCalculator need a SimpleCalculator obj as an argument to be instantiated.
   */
  private SimpleCalculator cal;

  public NotSoSimpleCalculatorImp(SimpleCalculator cal) {
    this.cal = cal;
  }

  @Override
  public int power(int x, int y) {
    return (int) Math.pow(x, y);
  }

  @Override
  public int abs(int x) {
    if (x <= 0) {
      x = cal.multiply(x, -1);
    }
    return x;
  }

  @Override
  public double sqrt(int x) {
    return Math.sqrt(x);
  }
}
