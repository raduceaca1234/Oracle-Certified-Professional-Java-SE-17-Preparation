package org.enricogiurin.ocp17.book.ch9;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class UsageOfSet {

  public static void main(String[] args) {
    new UsageOfSet().equalsOfSet();
  }

  void create() {
    Set<String> setA = new HashSet<>();
    Set<String> setB = Set.of("a", "b");
    //mutable set
    Set<String> setC = new HashSet<>(setB);
    Set<String> tressSet = new TreeSet<>();
  }

  void equalsOfSet() {
    //no matter the set implementation, equals() returns true when they have the same elements
    Set<String> setA = new HashSet<>();
    setA.add("one");
    setA.add("two");
    Set<String> setB = Set.copyOf(setA);
    System.out.println(setA.equals(setB));  //true

    Set<String> setC = new TreeSet<>();
    setC.add("two");
    setC.add("one");
    System.out.println(setA.equals(setC));  //true
  }

  void treeSet() {
    TreeSet<Integer> set = new TreeSet<>();
    set.add(5);
    set.add(2);
    set.add(10);
    set.add(10);
    set.forEach(System.out::println);  //2, 5, 10
  }

  void treeSetDoesNotAllowNullValues() {
    TreeSet<Integer> set = new TreeSet<>();
    set.add(5);
    //Exception in thread "main" java.lang.NullPointerException
    //	at java.base/java.util.Objects.requireNonNull(Objects.java:209)
    set.add(null);  //I cannot add null to TreeSet

  }

  void treeSetWithComparator() {
    Comparator<Integer> comparator = (n1, n2) -> n1 - n2;
    TreeSet<Integer> set1 = new TreeSet<>(comparator);
    TreeSet<Integer> set2 = new TreeSet<>(Set.of(1, 2, 3));
  }

  void treeSetWithElementsThatDoNotImplementComparable() {
    Set<IDoNotImplementComparable> set = new TreeSet<>();
    // cannot be cast to class java.lang.Comparable (org.enricogiurin.ocp17.book.ch9.UseOfSet$IDoNotImplementComparable
    //here throws a ClassCastException
    set.add(new IDoNotImplementComparable(10));
    set.add(new IDoNotImplementComparable(20));
    for (IDoNotImplementComparable e : set) {
      System.out.println(e);
    }

  }

  static class IDoNotImplementComparable {

    private final int element;

    public IDoNotImplementComparable(int element) {
      this.element = element;
    }
  }


}
