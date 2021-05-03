# Worklist

This project is a JavaFX app, which allows a user to keep track of and manage their work list. The app has been developed for a machinery shop called [Monikoneistus](https://www.monikoneistus.fi/)

It is a course project for the [Software Engineering](https://ohjelmistotekniikka-hy.github.io/) course at University or Helsinki. 

## Documentation

[Requirement analysis](https://github.com/sarlijes/Worklist/blob/master/Documentation/requirement-analysis.md)

[Project workload](https://github.com/sarlijes/Worklist/blob/master/Documentation/Workload.md)

[Architecture](https://github.com/sarlijes/Worklist/blob/master/Documentation/Architecture.md)

[User guide](https://github.com/sarlijes/Worklist/blob/master/Documentation/Userguide.md)

## Commands:

Navigate to project folder:
```
cd Worklist/
```
Run app:
```
mvn compile exec:java -Dexec.mainClass=Main
```
Run tests:
```
mvn test
```

Generate test report to target/site/jacoco/index.html: 
```
mvn test jacoco:report
```
Generate checkstyle report to target/site/checkstyle.html: 
```
mvn jxr:jxr checkstyle:checkstyle
```
Generate executable jar file: 
```
mvn package
```
Generate javadoc to target/site/apidocs/
```
mvn javadoc:javadoc
```
Combined command to remove target folder, generate ececutable jar file, test report (target/site/jacoco/index.htm), checkstyle report (target/site/checkstyle.html) and Javadoc (target/site/apidocs/)
```
mvn clean package && mvn test jacoco:report && mvn jxr:jxr checkstyle:checkstyle && mvn javadoc:javadoc
```

## Releases:
Week 5
