
## Temporary SQL stash for development use

```
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
details VARCHAR(2048),
customer VARCHAR(1024)
);
```
```
CREATE TABLE Job (id INT PRIMARY KEY AUTO_INCREMENT,
created DATETIME,
finished DATETIME,
deleted DATETIME,
duedate DATETIME,
name VARCHAR(1024),
quantity INTEGER,
material VARCHAR(1024),
workloadestimate FLOAT,
workloadactual FLOAT,
details VARCHAR(2048),
customer VARCHAR(1024)
);
```

```
insert into job (name, created, duedate, quantity, material, workloadestimate, details, customer) values 
('Injector plates', '2021-02-23T00:00:00.0', '2021-04-04T00:00:00.0', 5.0, 'Stainless steel', 4.0, 'details', 'Customer X');

insert into job (name, created, duedate, quantity, material, workloadestimate, details, customer) values 
('Rods', now(), '2021-08-08T00:00:00.0', 45.0, 'AlNiCo', 2.0, 'M2', 'Customer Y');

insert into job (name, created, duedate, quantity, material, workloadestimate, details, customer) values 
('Plates', now(), '2021-05-25T00:00:00.0', 100.0, '0.3 mm aluminium', 8.0, 'details', 'Customer 123');

insert into job (name, created, duedate, quantity, material, workloadestimate, details, customer) values 
('Thermalization plates', now(), '2021-02-02T00:00:00.0', 2.0, 'CU-OF', 1.0, '', 'internal');
```

```
CREATE TABLE Employee (id INT PRIMARY KEY AUTO_INCREMENT,
username VARCHAR(32),
password VARCHAR(32)
);
```

```
insert into employee (username, password) values ('esmeralda', '1234');
insert into employee (username, password) values ('vlad', '1234');
```