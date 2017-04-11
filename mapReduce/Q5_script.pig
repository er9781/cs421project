--load the data from HDFS and define the schema
raw = LOAD '/data2/emp.csv' USING PigStorage(',') AS (empid:int, fname:chararray, lname:chararray, deptname:chararray, isManager:chararray, mgrid:int, salary:int);

--get finance managers
managers = FILTER raw by (deptname matches 'Finance') and (isManager matches 'Y');

empsByManager = GROUP raw by mgrid;
empCount = FOREACH empsByManager GENERATE group, COUNT(raw);

joined = JOIN managers BY empid, empCount BY $0;

final = FOREACH joined GENERATE $0 as empid, $2 as lname, $8 as empCount;

--dump final;

--store final into 'q5' using PigStorage(',');

explain final;


