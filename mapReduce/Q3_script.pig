--load the data from HDFS and define the schema
raw = LOAD '/data2/cl03.csv' USING PigStorage(',') AS  (date, type:chararray, parl:int, prov:chararray, riding:chararray, lastname:chararray, firstname:chararray, gender:chararray, occupation:chararray, party:chararray, votes:int, percent:double, elected:int);

--filter winners of general elections
fltr = FILTER raw by (type matches 'Gen') and (elected == 1);

--group by parliment
grparl = GROUP fltr by parl;

--get parliment and number of total members
totalmemb = FOREACH grparl GENERATE group, COUNT(fltr) AS nmembers;

--create new relation, shifted by 1
shifttotalmemb = FOREACH totalmemb GENERATE group+1 AS sgroup, nmembers;

--join the two relations
jmembers = JOIN totalmemb by group, shifttotalmemb by sgroup;

--between each parliment, get the difference in members
--there is no difference for parliment 1
res = FOREACH jmembers GENERATE group as Parliment, totalmemb::nmembers - shifttotalmemb::nmembers as count;

DUMP res;
