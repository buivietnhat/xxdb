# XXDB

![Coverage](.github/badges/jacoco.svg)

XXDB is a lightweight, educational SQL database engine written in Java. It supports basic SQL operations, table creation, insertion, selection, joins, and more. The project is designed for learning and experimentation with database internals, query execution, and storage management.

## Features
- SQL parsing and execution
- Table creation and data insertion
- Selection with predicates
- Hash joins
- Limit and projection
- Disk-backed storage
- Modular and extensible architecture
- JUnit-based test suite

## Getting Started

### Prerequisites
- Java 21+
- Gradle (wrapper included)

### Build and Run
```sh
./gradlew -p app build
```

To start the interactive SQL shell:
```sh
./gradlew -p app run
```

## Running Tests and Coverage
To run all tests and generate a coverage report:
```sh
./gradlew -p app test jacocoTestReport
```
The HTML coverage report will be at `app/build/reports/jacoco/test/html/index.html`.

## Contributing
Pull requests and issues are welcome!

---

© 2024 XXDB Authors
