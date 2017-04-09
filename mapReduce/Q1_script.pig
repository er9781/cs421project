--load the data from HDFS and define the schema
raw = LOAD '/data2/cl03.csv' USING PigStorage(',') AS  (date, type:chararray, parl:int, prov:chararray, riding:chararray, lastname:chararray, firstname:chararray, gender:chararray, occupation:chararray, party:chararray, votes:int, percent:double, elected:int);

--filter candidates with 60% or more
fltrd = FILTER raw by percent >= 60.0;

--format the names
gen = foreach fltrd generate CONCAT(firstname,CONCAT('',lastname));

--eliminate duplicates
res = DISTINCT gen;

--store results in hdfs
STORE res INTO 'q1' USING PigStorage (',');
