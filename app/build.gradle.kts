/*
 * This file was generated by the Gradle 'init' task.
 *
 * This generated file contains a sample Java application project to get you started.
 * For more details on building Java & JVM projects, please refer to https://docs.gradle.org/8.13/userguide/building_java_projects.html in the Gradle documentation.
 */

plugins {
  // Apply the application plugin to add support for building a CLI application in Java.
  application
  antlr
}

repositories {
  // Use Maven Central for resolving dependencies.
  mavenCentral()
}

dependencies {
  // Use JUnit test framework.
  testImplementation("org.junit.jupiter:junit-jupiter:5.10.1")

  // Mockito Core
  testImplementation("org.mockito:mockito-core:5.11.0")

  // Mockito + JUnit5 integration
  testImplementation("org.mockito:mockito-junit-jupiter:5.11.0")

  // This dependency is used by the application.
  implementation(libs.guava)

  antlr("org.antlr:antlr4:4.13.2")
}

// Apply a specific Java toolchain to ease working on different environments.
java { toolchain { languageVersion = JavaLanguageVersion.of(21) } }

application {
  // Define the main class for the application.
  mainClass = "dev.xxdb.App"
}

tasks.test { useJUnitPlatform() }
tasks.test { useJUnitPlatform() }
tasks.test {
    jvmArgs("-XX:+EnableDynamicAgentLoading", "-XX:-UseSharedSpaces")
    useJUnitPlatform()
}

