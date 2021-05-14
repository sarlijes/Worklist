# Testing documentation


## Unit- and integration tests

The unit- and integration tests are divided into three test files. Each file tests both the entity and the DAO of a certain entity - for example [EmployeeTest](https://github.com/sarlijes/Worklist/blob/master/Worklist/src/test/java/worklist/EmployeeTest.java) includes the unit tests for both the constructor of the entity and the tests for the relevant DAO-class. The DAO classes take the ```Connection``` object as parameter, which allows them to be tested using an in-memory database when running the tests. 

The UI classes are excluded from automatic testing as testing those is not in the scope of this course.

## Test coverage

The line coverate of the automatic tests is 98% and the branch coverage 97%:

<img src="https://github.com/sarlijes/Worklist/blob/master/Documentation/pictures/test_coverage.PNG?raw=true">

The untested code includes methods that aren't implemented yet - for example editing and deleting employees.

## System testing

The app has been tested manually by downloading the jar file from the [Final release](https://github.com/sarlijes/Worklist/releases/tag/final). The testing was done both with Windows 10 by running:

```$ java -jar Worklist.jar```

The features of the [Requirement Analysis](https://github.com/sarlijes/Worklist/blob/master/Documentation/requirement-analysis.md) have been tested manually, also with invalid input such as empty values.