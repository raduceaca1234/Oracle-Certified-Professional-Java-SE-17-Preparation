package org.enricogiurin.ocp17.book.ch14;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class WriteBuffer {

  public static void main(String[] args) throws IOException {
    new WriteBuffer().copyFileWithNoBuffer();
  }

  void copyStream(InputStream inputStream, OutputStream outputStream) throws IOException {
    int b;
    while ((b = inputStream.read()) != -1) {
      outputStream.write(b);
    }
  }

  // run this before:
  // $ echo 0123456789 > /tmp/source.txt
  void copyFile() throws IOException {
    File file1 = new File("/tmp/source.txt");
    File file2 = new File("/tmp/target.txt");
    var reader = new InputStreamReader(new FileInputStream(file1));
    try (var writer = new FileWriter(file2)) {
      char[] buffer = new char[10];
      while (reader.read(buffer) != -1) {
        writer.write(buffer);
        // n1
      }
    }
  }
  //source.txt: 0123456789
  //target.txt :
  // 0123456789
  // 123456789

  //In second iteration the buffer contains 0123456789 but only the first three are replaced,
  // resulting in 012\n3456789

  void copyFile_correctWay() throws IOException {
    File file1 = new File("/tmp/source.txt");
    File file2 = new File("/tmp/target.txt");
    try (var reader = new InputStreamReader(new FileInputStream(file1));
        var writer = new FileWriter(file2)) {
      char[] buffer = new char[10];
      int bytesRead;
      while ((bytesRead = reader.read(buffer)) != -1) {
        System.out.println("read bytes: " + bytesRead);
        //I only copy the characters effectively read
        writer.write(buffer, 0, bytesRead); // Corrected line
      }
    }
    //source.txt: 0123456789
    //target.txt :0123456789

    //read bytes: 10
    //read bytes: 3
  }

  void copyFileWithNoBuffer() throws IOException {
    String source = "/tmp/source.txt";
    String target = "/tmp/target2.txt";
    try (var is = new FileInputStream(source);
        OutputStream os = new FileOutputStream(target)) {
      int byteRead;
      // a single byte is read and written on each iteration of the loop
      while ((byteRead = is.read()) > 0) {
        System.out.println("read %s".formatted((char)byteRead));
        os.write(byteRead);
      }
    }
  }

}
