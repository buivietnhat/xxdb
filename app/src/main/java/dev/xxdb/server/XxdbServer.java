package dev.xxdb.server;

import java.util.Scanner;

public class XxdbServer {
  public static void main(String[] args) throws XxdbException {
    Xxdb xxdb = new Xxdb.Builder().buildAll();
    Scanner scanner = new Scanner(System.in);
    while (true) {
      System.out.print("xxdb> ");
      System.out.flush();
      String query;
      try {
        query = scanner.nextLine().trim();
      } catch (Exception e) {
        // EOF or input closed
        System.out.println("EOF or input closed");
        break;
      }
      if (query.isEmpty()) {
        continue;
      }
      try {
        xxdb.execute(query);
      } catch (Exception e) {
        System.out.println("Error: " + e.getMessage());
      }
    }
    scanner.close();
  }
}
