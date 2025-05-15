package dev.xxdb.server;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

class XxdbTest {
  private Xxdb xxdb;
  private static final String FILE_PATH = "test_xxdb.db";

  @BeforeEach
  void setUp() throws Exception {
    xxdb = new Xxdb.Builder().filePath(FILE_PATH).buildAll();
  }

  @AfterEach
  void tearDown() throws IOException {
    Files.deleteIfExists(Paths.get(FILE_PATH));
  }

  @Test
  void testCreateTable() throws XxdbException {
    String output = xxdb.execute("CREATE TABLE Persons (PersonId INT, Name VARCHAR);");
    assertTrue(output.contains("OK"));
  }

  @Test
  void testInsertAndSelect() throws Exception {
    xxdb.execute("CREATE TABLE Persons (PersonId INT, Name VARCHAR);");
    xxdb.execute("INSERT INTO Persons (PersonId, Name) VALUES (1, 'Alice'), (2, 'Bob');");
    String output = xxdb.execute("SELECT PersonId, Name FROM Persons;");
    assertTrue(output.contains("Persons.PersonId | Persons.Name"));
    assertTrue(output.contains("1 | Alice"));
    assertTrue(output.contains("2 | Bob"));
  }

  @Test
  void testSelectWithPredicate() throws Exception {
    xxdb.execute("CREATE TABLE Persons (PersonId INT, Name VARCHAR);");
    xxdb.execute("INSERT INTO Persons (PersonId, Name) VALUES (1, 'Alice'), (2, 'Bob');");
    String output = xxdb.execute("SELECT PersonId, Name FROM Persons WHERE Name = 'Bob';");
    assertTrue(output.contains("Persons.PersonId | Persons.Name"));
    assertFalse(output.contains("Alice"));
    assertTrue(output.contains("2 | Bob"));
  }

  @Test
  void testJoinWithLimit() throws Exception {
    xxdb.execute("CREATE TABLE Persons (PersonId INT, Name VARCHAR);");
    xxdb.execute("CREATE TABLE Address (PersonId INT, City VARCHAR);");
    xxdb.execute("INSERT INTO Persons (PersonId, Name) VALUES (1, 'Alice'), (2, 'Bob'), (3, 'Carol');");
    xxdb.execute("INSERT INTO Address (PersonId, City) VALUES (1, 'New York'), (2, 'Los Angeles'), (3, 'Chicago');");
    String output = xxdb.execute("SELECT Persons.Name, Address.City FROM Persons JOIN Address ON Persons.PersonId = Address.PersonId LIMIT 2;");
    assertTrue(output.contains("Persons.Name | Address.City"));
    assertTrue(output.contains("Alice"));
    assertTrue(output.contains("Bob") || output.contains("Carol"));
  }
}