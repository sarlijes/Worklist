
## Temporary stash for development use


## Class diagram backup
```
[Job|id : INTEGER; created : TIMESTAMP; finished : TIMESTAMP; deleted : TIMESTAMP; duedate : TIMESTAMP; name : VARCHAR(1024); quantity : INTEGER; workloadestimate : FLOAT; workloadactual : FLOAT; details : VARCHAR(2048); customer : VARCHAR(1024); creator_id : INTEGER; material_id : integer]

[Employee|id : INTEGER; username VARCHAR(32); password VARCHAR(32)]

[Material|id : INTEGER; name VARCHAR(32); details VARCHAR(1024)]

[Employee]->[Job]
[Material]->[Job]
```