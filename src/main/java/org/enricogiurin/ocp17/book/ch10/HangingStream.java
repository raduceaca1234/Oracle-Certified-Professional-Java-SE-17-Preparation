package org.enricogiurin.ocp17.book.ch10;

import java.util.stream.IntStream;
import java.util.stream.Stream;

public class HangingStream {

  public static void main(String[] args) {
    new HangingStream().limitGreaterThanNumElements();
  }

  //this is hanging till you kill
  void hanging() {
    Stream.generate(() -> "Enrico")
        //the filter does not allow anything so then limit 2 is never reached
        .filter(s -> s.length() <= 2)
        .limit(2L)
        .forEach(System.out::println);
  }

  void limitGreaterThanNumElements() {
    int sum = IntStream.range(1, 10)
        .limit(12L)  //it's not affecting
        .sum();
    System.out.println(sum);  //45
  }
}
