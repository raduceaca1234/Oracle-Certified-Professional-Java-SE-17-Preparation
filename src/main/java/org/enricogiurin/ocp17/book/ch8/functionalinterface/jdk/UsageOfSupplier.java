package org.enricogiurin.ocp17.book.ch8.functionalinterface.jdk;

import java.util.function.Supplier;

public class UsageOfSupplier {

  private Supplier<String> supplierAsClass = new Supplier<String>() {
    @Override
    public String get() {
      return "Hello";
    }
  };
  private Supplier<String> supplierAsLambda = () -> new String("World");

  private Supplier supplierWithNoGenerics = () -> Double.valueOf(5.2);

  public static void main(String[] args) {
    new UsageOfSupplier().consumeSupplier();
  }


  void consumeSupplier() {
    Supplier<String> supplier = supplierAsClass;
    String result = supplier.get();
    System.out.println(result);

    supplier = supplierAsLambda;
    result = supplier.get();
    System.out.println(result);

    //here I have an object
    Object o = supplierWithNoGenerics.get();
  }


  void supplierOfDouble() {
    //Do not confuse with DoubleSupplier
    Supplier<Double> supplier = () -> {
      return Double.valueOf(Math.random());
    };
    //it returns the Wrapper of Double
    Double aDouble = supplier.get();
    System.out.println(aDouble);
  }

}

