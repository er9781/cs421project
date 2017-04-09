--this is for (i) NOT FINAL
--load the data from HDFS and define the schema
raw = LOAD '/data2/cl03.csv' USING PigStorage(',') AS  (date, type:chararray, parl:int, prov:chararray, riding:chararray, lastname:chararray, firstname:chararray, gender:chararray, occupation:chararray, party:chararray, votes:int, percent:double, elected:int);

--eliminate candidates with less than 100 votes
filtervotes = FILTER raw by votes >=100;

--split winners and losers
SPLIT filtervotes INTO winners IF elected==1, losers IF elected==0;

--join
pairriding = JOIN winners by (date,type,parl,prov,riding), losers by(date,type,parl,prov,riding);

--project last names and get the difference between votes
pairdiff = foreach pairriding generate winners::lastname, losers::lastname, winners::votes - losers::votes AS difference;

--filter pairs with less than 10 votes of difference
res = FILTER pairdiff by difference < 10;

--store the results
STORE res INTO 'q2' USING PigStorage (',');
