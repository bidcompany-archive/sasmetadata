/***************************************
	Query Passthrough su TD
	Esempio USER CODE:
	execute (
				create view &tdStagedb..&lasrTable. as
				select geography_dim.*
				from &&tdStagedb..geography_dim geography_dim
			
		) by teradata;
***************************************/

%Macro create;
  %if %sysfunc(exist(&stgLib..&lasrTable)) %then %do;
    proc delete data=&stgLib..&lasrTable;run;
  %end;
  
  data &stgLib..&lasrTable;
    length perimeter $1;
    perimeter='J';output;
    perimeter='C';output;
  run;
%mend Create;
%create;


%macro select;
/************************************
			BEGIN USER CODE 
************************************/



/************************************
			END USER CODE 
************************************/
%mend;