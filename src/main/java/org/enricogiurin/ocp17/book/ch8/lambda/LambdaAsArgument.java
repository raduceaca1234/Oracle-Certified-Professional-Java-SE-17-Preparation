package org.enricogiurin.ocp17.book.ch8.lambda;

public class LambdaAsArgument {

  public static void main(String[] args) {
    var calculator = new Calculator();
    int result = calculator.mathematicalComputation((a, b) -> 2 * a + 2 * b, 4, 6);
    System.out.println(result);  //20
  }

  //This is a FI as it contains only one abstract method: add
  @FunctionalInterface
  interface AddNumbers {

    static int subtract(int x, int y) {
      return x - y;
    }

    int add(int x, int y);

    default int multiply(int x, int y) {
      return x * y;
    }
  }

  static class Calculator {

    int mathematicalComputation(AddNumbers addNumbers, int a, int b) {
      return addNumbers.add(a, b);
    }

    //I can call static method only on the Interface itself
    int callStaticMethod(int a, int b) {
      return AddNumbers.subtract(a, b);
    }
  }

}
