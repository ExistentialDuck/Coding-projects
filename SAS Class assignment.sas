DATA  satgpa ;
INFILE  "C:/Users/Stephen Hanna/Documents/Classes/AMS 394/SATGPA.txt" 
     DSD 
     LRECL= 16 ;
INPUT
 MathSAT
 VerbalSAT
 GPA
;
RUN; 
data SAT;
set satgpa;
sat = mathsat + verbalsat;
run;
proc print data=SAT;
run;
data SAT;
set satgpa;
SAT = mathsat + verbalsat;
if SAT<= 1100 then SATLevel =1;
else if SAT<= 1200 then SATLevel =2;
else if SAT<=1300 then SATLevel = 3;
else SATLevel=4;
run;
data SAT;
set satgpa;
SAT = mathsat + verbalsat;
if SAT<= 1100 then SATLevel =1;
else if SAT<= 1200 then SATLevel =2;
else if SAT<=1300 then SATLevel = 3;
else SATLevel=4;
if gpa<= 2.8 then GPALEVEL =1;
else if gpa<= 3.2 then GPALEVEL =2;
else if gpa<=3.5 then GPALEVEL = 3;
else if gpa>3.5 then GPALEVEL = 4;
run; 
proc sort data=SAT out=SATGPALEVELSort;
by descending GPALEVEL descending sat;
proc freq data=SATGPALEVELSort;
tables SATlevel*GPALEVEL /chisq;
run;
proc means data=SATGPALEVELSort;
class GPALEVEL;
var gpa;
output out=means mean=gpa_mean;
run;

proc univariate data=SATGPALEVELSort;
class GPALEVEL;
var gpa;
run;
proc corr data=SATGPALEVELSort;
var mathsat verbalsat sat gpa;
run;
data SATGPALEVELSort;
set SATGPALEVELSort;
diff = mathsat-verbalsat;
run;
proc univariate data=SATGPALEVELSort normal;
var diff;
run;
data SATGPALEVELSort;
set SATGPALEVELSort;
diff = mathsat-verbalsat;
if diff>0 then true=1;
else true=0;
run;
proc freq data=SATGPALEVELSort order=freq;
tables true / binomial (level=1 p=.65);
exact binomial;
run;
proc reg data=SATGPALEVELsort;
model mathsat=verbalsat;
run;
proc corr data=SATGPALEVELsort;
var mathsat verbalsat;
run;
DATA  rdata ;
INFILE  "C:/Users/Stephen Hanna/Documents/Classes/AMS 394/mydata.txt" 
     DSD 
     LRECL= 27 ;
INPUT
 deaths
 drivers
 popden
 rural
 temp
 fuel
;
RUN;
proc reg data=rdata;
model deaths=drivers popden rural temp fuel;
run;

PROC REG DATA = rdata;
MODEL DEATHS = DRIVERS POPDEN RURAL TEMP FUEL / SELECTION = STEPWISE;
RUN;

3.
DATA scores;
INPUT Group $ Score Age $ @@;
DATALINES;
A 90 15-18 B 92 15-18 C 97 15-18
A 88 15-18 B 88 12-14 C 92 12-14
A 72 12-14 B 78 12-14 C 88 12-14
A 82 15-18 B 78 15-18 C 94 15-18
A 65 12-14 B 90 15-18 C 99 15-18
A 74 12-14 B 68 12-14 C 82 12-14
;
RUN;
PROC ANOVA DATA=scores;
class group;
model score=group;
means group;
run;
PROC glm DATA=scores;
class group;
model score=group;
contrast 'B VS A and C' group 1 -2 1;
DATA scores;
INPUT Group $ Score Age $ @@;
DATALINES;
A 90 15-18 B 92 15-18 C 97 15-18
A 88 15-18 B 88 12-14 C 92 12-14
A 72 12-14 B 78 12-14 C 88 12-14
A 82 15-18 B 78 15-18 C 94 15-18
A 65 12-14 B 90 15-18 C 99 15-18
A 74 12-14 B 68 12-14 C 82 12-14
;
RUN;
PROC glm DATA=scores;
class group age;
model score=group|age;
lsmeans group / stderr pdiff cov out=adjmeans;
run;
 
PROC FORMAT;
value group 
     1 = "1" 
     2 = "2" 
     3 = "3" 
     4 = "4" 
     5 = "5" 
     6 = "6" 
;

DATA  rdata ;
INFILE  "C:/Users/Stephen Hanna/Documents/Classes/AMS 394/cond.txt" 
     DSD 
     LRECL= 8 ;
INPUT
 y
 group
;
FORMAT group group. ;
RUN;
proc print data=rdata;
run;
PROC ANOVA DATA=rdata;
class group;
model y=group;
means group;
run;
