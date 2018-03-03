/***************************************
	Query Passthrough su TD
	Esempio USER CODE:
	execute (
				replace view &tdStagedb..&table_target. as
				select geography_dim.*
				from &tdStagedb..geography_dim geography_dim
			
		) by teradata;
***************************************/

%macro select;
/************************************
			BEGIN USER CODE 
************************************/

execute (
				replace view &tdStagedb..&table_target. as
				select tab1.*
				from P_KGU_GLM_LCRDA.&table_target tab1 
			
		) by teradata;




/************************************
			END USER CODE 
************************************/
%mend;