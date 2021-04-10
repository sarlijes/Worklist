# Requirement analysis

## Purpose

The purpose of the app is to provide a simple tool for a machinery workshop to keep track of and manage their work list. The workshop employs 5 people, so it's important to keep everyone up to date.

## Roles

There is only one user group, so all users have equal user rights.

## Basic features

As a user...
 
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
* Allow using the software from multiple computers in the local network
* Allow undo on marking a job as done
* Hide old finished jobs after a certain period
* Separate list views for pending and completed jobs
* Move materials to database (instead of saving them as strings)
* Language selection
* Reports (for example all jobs done during one week)
* Kanban view with more job states ("to do", "in progress", "waiting to be shipped", "shipped")
* More states for payments of the jobs ("invoiced", "partially paid", "paid")


## Preliminary plan for Job class

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
