# Architecture documentation

## Functionality

The functionality of the app has is described in [requirement analysis](https://github.com/sarlijes/Worklist/blob/master/Documentation/requirement-analysis.md)

## Package structure

The package structure of the app is as follows:

- package ```entity``` contains the classes that represent database table entries:

<img src="https://github.com/sarlijes/Worklist/blob/master/Documentation/pictures/package_entity.PNG?raw=true" style="margin-top: 20px; margin-bottom: 20px;">

- package ```dao``` contains the database logics and a utility class:

<img src="https://github.com/sarlijes/Worklist/blob/master/Documentation/pictures/package_dao.PNG?raw=true" style="margin-top: 20px; margin-bottom: 20px;">

- package ```ui``` contains the user interface components and functionality:

<img src="https://github.com/sarlijes/Worklist/blob/master/Documentation/pictures/package_ui.PNG?raw=true" style="margin-top: 20px; margin-bottom: 20px;">




## Database

The app uses a H2 database, which is created locally on the user's computer when running the app for the first time. The unit tests use an "in-memory" version of the H2 database.

The logical data model is constructed by three main classes: Job, Employee and Material, which model the jobs that are added to the work list, the users of the app and the different materials of the jobs:

<img src="https://github.com/sarlijes/Worklist/blob/master/Documentation/pictures/class_diagram.PNG?raw=true" style="margin-top: 20px; margin-bottom: 20px;">

UI (user interface)-classes handle the the positioning and the logics of the UI components. They make requests to DAO ([Data Access Object](https://en.wikipedia.org/wiki/Data_access_object))-classes, which are in charge of the CRUD (create, read, update, delete) operations. The DAO-classes are given the ```Connection``` object as a parameter, which allows thorough unit testing using an in-memory database.
## Example: user login
<img src="https://github.com/sarlijes/Worklist/blob/master/Documentation/pictures/employee_login_sequence.PNG?raw=true" style="margin-top: 20px; margin-bottom: 20px;">

1. User has ran the app and written their username and password on the form on ```LoginDialog```
2. User clicks ```loginButton```
3. ```loginButton```'s actionlistener makes a request to ```EmployeeDao``` to authenticate the employee
4. If authentication is successful (an user was found with the username and password), employeeDao returns an ```Employee``` object
5. The ```LoginDialog``` is closed and the main view is shown to the user
6. The ```Employee``` object is kept in the ```loggedInEmployee``` variable for future reference