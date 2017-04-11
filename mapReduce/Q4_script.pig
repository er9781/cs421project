--load the data from HDFS and define the schema
raw = LOAD '/data2/cl03.csv' USING PigStorage(',') AS  (date, type:chararray, parl:int, prov:chararray, riding:chararray, lastname:chararray, firstname:chararray, gender:chararray, occupation:chararray, party:chararray, votes:int, percent:double, elected:int);

--filter winners of general elections
fltr = FILTER raw by (type matches 'Gen') and (elected == 1);

--get total seats in parl
grp = GROUP fltr by parl;
totSeats = FOREACH grp GENERATE group as parl, COUNT(fltr) as seats;

--get nmembers/parl
grp2 = GROUP fltr by (parl, party);
totMem = FOREACH grp2 GENERATE group, COUNT(fltr) as nMem;
totMem = FOREACH totMem GENERATE flatten($0), $1;

--join two result sets
joined = JOIN totSeats BY $0, totMem BY $0;
--dump joined;
final = FOREACH joined GENERATE $0 as parl, $3 as party, $4 as nMem, $1 as seats;

store final into 'q4' using PigStorage(',');


