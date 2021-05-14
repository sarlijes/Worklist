# Requirement analysis

## Purpose

The purpose of the app is to provide a simple tool for a machinery workshop to keep track of and manage their work list. The workshop employs 5 people, so it's important to keep everyone up to date.

## Roles

There is only one user group, so all users have equal user rights.

## Implemented features

As a user...

- [x]  I can create a user account
- [x]  I log in with my user account
 
- [x]  I can view a list of jobs
- [x]  I can add a new job to the list
- [x]  I can delete a job from the list
- [x]  I can edit the job
- [x]  I can mark the job as done and mark its actual work load hours
- [x]  I can mark undo marking the job as done

- [x]  I can add new materials to a list
- [x]  I can select one of those materials when creating or editing jobs

- [x] I can see which user created a certain job 

## Further development ideas

- [ ] Language selection
- [ ] Save passwords encrypted
- [ ] Implement complexity requirements for passwords
- [ ] Mark jobs as deleted instead of deleting them
- [ ] Save user as "deleted by" on a job
- [ ] Save user as "marked as finished by" on a job
- [ ] Allow using the software from multiple computers in the local network
- [ ] Hide old finished jobs after a certain period
- [ ] Separate list views for pending and completed jobs
- [ ] Reports (for example all jobs done during one week)
- [ ] Kanban view with more job states (for example "to do", "in progress", "waiting to be shipped", "shipped")
- [ ] More states for payments of the jobs (for example "invoiced", "partially paid", "paid")