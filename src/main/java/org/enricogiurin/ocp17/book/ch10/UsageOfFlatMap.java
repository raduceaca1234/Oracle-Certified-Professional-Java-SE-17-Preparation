package org.enricogiurin.ocp17.book.ch10;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class UsageOfFlatMap {

  public static void main(String[] args) {
    new UsageOfFlatMap().simpleFlatMap();
  }

  //partially generated by chatGPT
  void getAllAuthors() {
    List<Book> books = List.of(
        new Book("Book 1", List.of("Author 1", "Author 2")),
        new Book("Book 2", List.of("Author 3", "Author 5")),
        new Book("Book 3", List.of("Author 4", "Author 5"))
    );
    // FlatMap to extract authors
    Set<String> allAuthors = books.stream()
        .flatMap(book -> book.authors().stream())
        .collect(Collectors.toSet());
    System.out.println(allAuthors);
  }

  void flatMap() {
    List<Integer> listA = List.of(1, 2, 3, 4, 5);
    List<Integer> listB = List.of(6, 7, 8, 9, 10);
    //we merge two list
    Stream.of(listA, listB)
        .flatMap(list -> list.stream())
        .forEach(System.out::println);  //1...10
  }

  void flatMapToInt() {
    List<String> numbers = List.of("123", "456", "789");
    IntStream intStream = numbers.stream()
        .flatMapToInt(s -> s.chars())
        .map(Character::getNumericValue);
    //1 2 3 4 5 6 7 8 9
    intStream.forEach(a -> System.out.print(a + " "));
  }

  void complex() {
    var pears = List.of(1, 2, 3, 4, 5, 6);
    final var sum = pears.stream()
        .skip(1)
        .limit(3)
        .flatMapToInt(s -> IntStream.of(s))
        .skip(1)
        .boxed()
        .mapToDouble(s -> s)
        .peek(System.out::println) //3.0 - 4.0
        .sum();
    System.out.print(sum); //7.0
  }

  void wrongFlatMap() {
    List.of(1, 2, 3)
        .stream()
        //  .flatMap(n-->n)  //wrong it has to be a stream //DOES NOT COMPILE
        .flatMap(n -> List.of(n).stream())  //this produces a stream
        .toList();

  }

  void mergeCollections() {
    List<String> list = List.of("a", "ab", "abc");
    Set<String> set = Set.of("1", "23", "345");
    List<String> merge = Stream.of(list, set)
        .flatMap(c -> c.stream())
        .collect(Collectors.toList());
    merge.forEach(System.out::println);
  }

  void simpleFlatMap() {
    List<Integer> list = Stream.of(Stream.of(1, 2),
            Stream.of(4, 5))
        .flatMap(x -> x)
        .toList();
    //[1, 2, 4, 5]
    System.out.println(list);
  }

  record Book(String title, List<String> authors) {

  }
}



