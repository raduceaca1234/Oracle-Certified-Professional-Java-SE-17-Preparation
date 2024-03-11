package org.enricogiurin.ocp17.book.ch11;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

public class UsageOfDecimalFormat {

  public static void main(String[] args) {
    new UsageOfDecimalFormat().pattern();
  }

  void format() {
    double wealth = 500_000.45;
    final String format = "###,###,###.000000";
    NumberFormat nf = new DecimalFormat(format);
    String result = nf.format(wealth);
    System.out.println(result); //500,000.450000
  }

  void formatWithDefault() {
    double wealth = 500_000.45;
    DecimalFormatSymbols customSymbols = new DecimalFormatSymbols(Locale.getDefault());
    customSymbols.setGroupingSeparator('.');
    customSymbols.setDecimalSeparator(',');
    final String format = "###,###,###.000";
    NumberFormat nf = new DecimalFormat(format, customSymbols);
    String result = nf.format(wealth);
    System.out.println(result); //500.000,450

  }

  void chatGPT() {
    String pattern = "###,###.##"; // Thousands separator is a period, decimal separator is a comma

    // Create a DecimalFormat object with the pattern
    DecimalFormatSymbols customSymbols = new DecimalFormatSymbols(Locale.getDefault());
    customSymbols.setGroupingSeparator('.');
    customSymbols.setDecimalSeparator(',');

    DecimalFormat decimalFormat = new DecimalFormat(pattern, customSymbols);

    // Double value representing the money amount
    double moneyAmount = 1000.45;

    // Format the money amount using the DecimalFormat
    String formattedMoneyAmount = decimalFormat.format(moneyAmount);

    // Print the formatted money amount
    System.out.println("Formatted Money Amount: " + formattedMoneyAmount);
  }

  void pattern() {
    String pattern = "#,###,000.####";
    var message = DoubleStream.of(5.21, 8.499, 1234)
        .mapToObj(v -> new DecimalFormat(pattern).format(v))
        .collect(Collectors.joining("> <"));
    //005.21> <008.499> <1,234>
    System.out.println("<" + message + ">");
  }

  void manyFormat() {
    //<02.1> <06.9> <10,00>
    String[] patterns = {"##,00.##", "##,00.#", "0,00.#", "#,00.#", "0,00.0", "#,##.#"};
    for (String pattern : patterns) {
      var message = DoubleStream.of(2.1, 6.923, 1000)
          .mapToObj(v -> new DecimalFormat(pattern).format(v))
          .collect(Collectors.joining("> <"));
      System.out.print("<" + message + ">");
      System.out.println();
    }
  }


}
