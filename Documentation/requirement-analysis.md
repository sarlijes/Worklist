#Requirement analysis

##Purpose

The purpose of the app is to provide a simple tool for a machinery workshop to keep track of and manage their work list. The workshop employs 5 people, so it's important to keep everyone up to date.

##Roles

There is only one user group -  all users have equal user rights.

## Basic features

As an user...
 
* I can view the list of jobs
* I can add a new job to the list
* I can delete a job from the list
* I can edit the job
* I can mark the job as done and mark its actual work load hours

## Additional features

* User login
  * Add new user
  * Log in as user
  * Show error if logging in fails
  * Save user as "created by" on a job
  * Save user as "deleted by" on a job
  * Save user as "marked as finished by" on a job
  
* Allow undo on marking a job as done
* Allow undo on marking a job as deleted
* Hide old finished jobs after a certain period
* Separate lsit views for pending and completed jobs
* Move materials to database (instead of saving them as strings)
* Language selection
* Reports (for example all jobs done during one week)
* Kanban view with more job states ("to do", "in progress", "waiting to be shipped", "shipped")
* More states for payments of the jobs ("invoiced", "partially paid", "paid")


## Preliminary database plan for Job class

|Attribute |Data type   |
|---|---|
|id|int primary key|
|Created|timestamp|
|Finished|timestamp|
|Deleted|timestamp|
|Name   |string   |
|Quantity   |int   |
|Material   |string   |
|Due date   |timestamp   |
|Customer   |string   |
|Work load estimate   |double   |
|Actual work load   |double   |
|Details   |string   |

`
CREATE TABLE Job (id INT PRIMARY KEY AUTO_INCREMENT,
created TIMESTAMP,
finished TIMESTAMP,
deleted TIMESTAMP,
duedate TIMESTAMP,
name VARCHAR(1024),
quantity INTEGER,
material VARCHAR(1024),
workloadestimate FLOAT,
workloadactual FLOAT,
details VARCHAR(2048)
);
`
`
insert into job (name, created, duedate, quantity, material, workloadestimate, details) values 
('Injector plates', now(), '2021-04-04', 5.0, 'Stainless steel', 4.0, 'details');
insert into job (name, created, duedate, quantity, material, workloadestimate, details) values 
('Rods', now(), '2021-03-01', 45.0, 'AlNiCo', 2.0, 'M2');
insert into job (name, created, duedate, quantity, material, workloadestimate, details) values 
('Plates', now(), '2021-04-04', 100.0, '0.3 mm aluminium', 8.0, 'details');
insert into job (name, created, duedate, quantity, material, workloadestimate, details) values 
('Thermalization plates', now(), '2021-04-28', 2.0, 'CU-OF', 1.0, '');
`