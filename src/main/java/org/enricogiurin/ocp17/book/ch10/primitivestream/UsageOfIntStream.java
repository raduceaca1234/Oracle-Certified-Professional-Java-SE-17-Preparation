package org.enricogiurin.ocp17.book.ch10.primitivestream;

import java.util.IntSummaryStatistics;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class UsageOfIntStream {

  public static void main(String[] args) {
    new UsageOfIntStream().summaryStatistics();
  }

  void average() {
    IntStream intStream = java.util.stream.IntStream.rangeClosed(0, 9);
    intStream.average().ifPresent((avg) -> System.out.println(avg)); //4.5
    //I can't call it on an existing stream
    //Exception in thread "main" java.lang.IllegalStateException: stream has already been operated upon or closed
    //	at java.base/java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:229)
    int sum = intStream.sum();
    System.out.println("sum is: " + sum);
  }

  void averageOptionalDouble() {
    OptionalDouble optionalDouble = IntStream.of(1, 5, 6, 7, 8, 9, 10)
        .average();
    if (optionalDouble.isPresent()) {
      //it's getAsDouble() not get()!
      System.out.println(optionalDouble.getAsDouble());
    }
  }

  void sum() {
    //9 included
    IntStream rangeClosed = IntStream.rangeClosed(0, 9);
    //note that IntStream.sum(0 returns an int, not a long!
    int sum = rangeClosed.sum();
    System.out.println("sum: " + sum); //45

    //9 not included
    IntStream range = IntStream.range(0, 9);
    sum = range.sum();
    System.out.println("sum: " + sum); //36
  }

  void minAndMax() {
    IntStream rangeClosed = IntStream.rangeClosed(0, 9);
    //note that it's an optional
    OptionalInt optional = rangeClosed.min();
    optional.ifPresent(System.out::println); //0

    rangeClosed = IntStream.rangeClosed(0, 9);
    optional = rangeClosed.max();
    optional.ifPresent(System.out::println);  //9
  }

  void boxed() {
    IntStream rangeClosed = IntStream.rangeClosed(0, 100);
    //create a stream with the wrapper Integer
    Stream<Integer> stream = rangeClosed.boxed();
    stream.forEach(n -> System.out.print(n + " "));
  }

  void parallel() {
    IntStream rangeClosed = IntStream.rangeClosed(0, 100);
    //note: by calling parallel() on an IntStream you get another IntStream, not Stream<Integer>
    IntStream parallelIntStream = rangeClosed.parallel();
    long count = parallelIntStream.peek((s) -> System.out.print(s + " "))
        .count();
    //count: 101
    System.out.println("count: " + count);
  }

  void concat() {
    IntStream odds = IntStream.iterate(1, n -> n + 2);
    IntStream evens = IntStream.iterate(0, n -> n + 2);
    IntStream.concat(odds, evens)
        .limit(5)
        .forEach(System.out::println);  //1,3,5,7,9
  }

  void summaryStatistics() {
    IntSummaryStatistics intSummaryStatistics = IntStream.range(1, 100)
        .filter(n -> n % 2 == 0)
        .summaryStatistics();
    double average = intSummaryStatistics.getAverage();
    long sum = intSummaryStatistics.getSum();
    //average: 50.000000 - sum: 2450
    System.out.println("average: %f - sum: %d".formatted(average, sum));
  }


}
