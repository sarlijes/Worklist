# Project workload

|Date |Hours   |Description   |
|---|---|---|
|14.3.2021 | 1  |Project planning, documenting ideas   |
|15.3.2021   | 2  |Discuss ideas and needs with customer, define functionality, initialize project   |
|15.3.2021   | 2  |Preliminary requirement analysis, investigate Java - DB connection, refresh memory on how to use H2 console. Create table for Job class. Test database. |
|15.3.2021   | 2  |Configure JavaFX in project. Investigate using Swing instead. Attempt to set up FXML. |
|16.3.2021   | 2  |New attempt to configure JavaFX with IntelliJ. In Netbeans, can run current version. Database connection works, can show data from database in GUI.   |
|17.3.2021   | 2  |Create dao interface, implement JobDao's list method and Job class. Initial experimentation on listing jobs in a table  |
|21.3.2021   | 2  |Separate UI to its own class. Extend Job to include details and customer attributes. Extend job table view, add buttons to table for each job for marking them as done (logic to be implemented) |
|23.3.2021   | 3  |Initial UI implementation to add new jobs. Add first test.  |
|28.3.2021   | 4  |Implement creating new Jobs, update documentation  |
|29.3.2021   | 1  |Attend workshop (paja) to fix issue with re-rendering the view after adding new job  |
|29.3.2021   | 2  |Refactored UI Dialog to use inheritance. Add dialog to edit Jobs. Extend JobDao to include implementations on reading and updating jobs  |
|29.3.2021   | 1  |Bug fixes + format timestamps in a better way, demonstrate app to customer |
|30.3.2021   | 2  | Implement JobDao.delete to actually delete the job instead or marking it as deleted. Show the completed jobs with a different color in the UI |
|10.4.2021   | 4  | Refactored tests according to weekly course feedback. Investigate different validation possibilities. Refactored text field to use jfoenix text field. Added custom class to extend JFXTextField. |
|10.4.2021   | 1  | Implemented logic & UI to unmark jobs as done |
|10.4.2021   | 4  | Working on multi-language support. Investigate possible icon libraries. |
|11.4.2021   | 2  | More work on multi-language support |
|17.4.2021   | 3  | Implemented in-memory database. Refectored JobDao to take connection as parameter. Added test for setting job as done. |
|17.4.2021   | 2  | Finishing touches to refactoring JobDao. Extend tests, test coverage of dao now 96% (instructions) / 83% (branches). Removed some unused getters and setters. |
|17.4.2021   | 1  | Checkstyle into use. Configure to skip UI files, as instructed at the course material. Fix checkstyle errors. |
|18.4.2021   | 1  | Investigations and experiments on different ways to change app language on runtime |
|18.4.2021   | 2  | Implementing support to change app language on runtime |
|18.4.2021   | 2  | Implemented employee entity & employee login |
|19.4.2021   | 1  | Added logic to show past due dates with red color & the due dates within one week with orange. Refactored UI code into shorted methods. |
|19.4.2021   | 1  | Add class diagram in documentation directory. Update readme. Test on remote desktop. |
|20.4.2021   | 1  | Now possible to create new user accounts. Created new class SQLUtils for helper methods. |
|20.4.2021   | 1  | Add db files to gitignore, extend SQLUtils to create tables |
|21.4.2021   | 1  | Refactor tests to have SQLUtils create tables |
|24.4.2021   | 2  | Improvements to user login dialog, validation + ensure unique user names |
|24.4.2021   | 2  | Add tests to EmployeeDao. Extend Job to include Employee entity as creator. Show creator's username in table view. |
|27.4.2021   | 1  | Small bug fixes. Update documentation. Now possible to create executable jar file. |
|28.4.2021   | 3  | Move materials to database and save material id on Job. Implement MaterialDao. Refactor UI to shorted methods. Extend UI to contain materials and a form to add more materials. Refactor relevant tests.|
|3.5.2021   | 3  | Refactor package structure to meet requirements. Implement first test to Materials. Write JavaDoc for 5 classes. Extend architecture documentation and user guide. |
|8.5.2021   | 1  | Extend tests for Material |
|98.5.2021   | 4  | Added logic to avoid duplicate material names. Refectored code: removed commented code, made lines match the 120 char limit, fixed some typos. Removed duplicate files. Extend documentation. |

|   |   |   |
|total   | 68   |   |