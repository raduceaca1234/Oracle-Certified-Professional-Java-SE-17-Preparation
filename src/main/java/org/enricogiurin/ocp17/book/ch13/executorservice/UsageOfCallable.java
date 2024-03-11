package org.enricogiurin.ocp17.book.ch13.executorservice;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Stream;

public class UsageOfCallable {

  public static void main(String[] args) {
    new UsageOfCallable().futureThrowsException();
  }

  void submit() {
    Callable<String> forCallable = () -> {
      for (int j = 0; j < 1_000; j++) {
        //System.out.print(j + " ");
      }
      System.out.println();
      return "done";
    };
    ExecutorService executorService = Executors.newSingleThreadExecutor();
    try {
      Future<String> future = executorService.submit(forCallable);
      String result = future.get(1L, TimeUnit.SECONDS);
      System.out.println(result);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      executorService.shutdown();
    }
  }

  void futureThrowsException() {
    ExecutorService executorService = Executors.newSingleThreadExecutor();
    Future<String> future = executorService.submit(() -> "hello");
    executorService.shutdown();
    String result;
    try {
      result = future.get(1, TimeUnit.SECONDS);
      //mind the three checked exceptions to be caught
    } catch (InterruptedException | ExecutionException | TimeoutException e) {
      throw new RuntimeException(e);
    }
    System.out.println(result);
  }

  void typeInferenceWIthCallable() {
    var executorService = Executors.newSingleThreadExecutor();
    Callable rawCallable = () -> Stream.of(4, 5, 6);

    //but also
    Callable<Stream<Integer>> callableWithGenerics = () -> Stream.of(4, 5, 6);

    //why does this compile? :-(
    Future<Stream<String>> futureOfInvalidType = executorService.submit(rawCallable);
    //but this does not compile as it expect Future<Stream<Integer>>
    //Future<Stream<String>> futureOfValidType = executorService.submit(callableWithGenerics);   //DOES NOT COMPILE!
    Future<Stream<Integer>> futureOfValidType = executorService.submit(callableWithGenerics);
  }

  void validCallable() {
    //in spite does not return a value it's still a valid Callable
    Callable<String> c =  () -> {throw new RuntimeException();};
  }

  void simpleCallable() {
    Callable<String> callable = () -> "hello";
  }

  void anonymousCallable() {
    new Callable<String>() {

      @Override
      public String call() throws Exception {
        return "hello";
      }
    };
  }


}

