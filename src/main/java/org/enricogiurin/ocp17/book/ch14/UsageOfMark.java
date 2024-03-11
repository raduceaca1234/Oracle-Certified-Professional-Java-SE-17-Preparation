package org.enricogiurin.ocp17.book.ch14;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.util.Arrays;

public class UsageOfMark {

  public static void main(String[] args) throws IOException {
    new UsageOfMark().readBytes();
  }

  void mark() throws IOException {
    final String name = "enrico";
    try (InputStream inputStream = new ByteArrayInputStream(name.getBytes())) {
      if (inputStream.markSupported()) {
        inputStream.mark(1);
        System.out.print((char) inputStream.read());
        System.out.print((char) inputStream.read());
        inputStream.reset();
        System.out.println();
      }
      System.out.print((char) inputStream.read());
      System.out.print((char) inputStream.read());
      //en
      //en
    }
  }

  void markLimit() throws IOException {
    final String name = "ciao";
    try (InputStream inputStream = new ByteArrayInputStream(name.getBytes())) {
      if (inputStream.markSupported()) {
        inputStream.mark(1);
        //ciao￿￿￿￿￿￿
        System.out.print((char) inputStream.read());  //c
        System.out.print((char) inputStream.read());  //i
        System.out.print((char) inputStream.read());  //a
        System.out.print((char) inputStream.read());  //o
        System.out.print((char) inputStream.read());  //￿
        System.out.print((char) inputStream.read());  //￿
        System.out.print((char) inputStream.read());  //￿
        System.out.print((char) inputStream.read());  //￿
        System.out.print((char) inputStream.read());  //￿
        System.out.print((char) inputStream.read());  //￿
        inputStream.reset();
        System.out.println();
      }
      System.out.print((char) inputStream.read());
      System.out.print((char) inputStream.read());
      //ciao￿￿￿￿￿￿
      //ci
    }
  }

  void jumpAround() throws IOException {
    final String name = "0123456789";
    InputStream is = new ByteArrayInputStream(name.getBytes());
    try (is) {
      is.skip(1);  // -> 1
      is.read();  //1 -> 2
      is.skip(1); // -> 3
      is.mark(4);  //mark to (3)
      is.skip(1);  //-> 4
      is.reset();  //goes back to (3)
      System.out.print((char) is.read());  //3
    }
  }

  void markSkip() throws IOException {
    var sb = new StringBuilder();
    try (Reader reader = new StringReader("PEACOCKS")) {
      sb.append((char) reader.read());  //P added
      System.out.println("markSupported: " + reader.markSupported());

      //since we are using StringReader the parameter passed
      //(as long as greater than zero) has no effect
      reader.mark(1);
      for (int i = 0; i < 2; i++) {
        sb.append((char) reader.read());
        long skip = reader.skip(2);
        //  (0) E added | AC skipped
        //  (1) O added | CK skipped
        System.out.println("skip: " + skip);
      }
      //reset to E
      reader.reset();
      reader.skip(0);
      //E added
      sb.append((char) reader.read());
    }
    //PEOE
    System.out.println(sb);
  }

  void readBytes() throws IOException {
    final var luck = new byte[]{1, 2, 3, 4, 5, 6, 7};
    byte[] buffer = new byte[2];
    try (InputStream is = new ByteArrayInputStream(luck)) {
      is.read(buffer);
      System.out.println(Arrays.toString(buffer)); //[1, 2]
      if (!is.markSupported()) {
        return;
      }
      is.mark(5);
      is.read();
      is.read();
      //we skip other the size of the array (luck)
      is.skip(12);
      int read1 = is.read();  //this returns -1 as it is out of size
      System.out.println(read1);
      is.reset();  //we go back to (3) which is after read 1 and 2
      int read = is.read();
      System.out.println(read);  //3
    }

  }

}
