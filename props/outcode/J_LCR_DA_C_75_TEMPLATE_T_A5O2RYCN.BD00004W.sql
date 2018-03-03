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
				select LCR_DA_C_75_TEMPLATE.*
				from P_KGU_GLM_LCRDA.LCR_DA_C_75_TEMPLATE LCR_DA_C_75_TEMPLATE 
			
		) by teradata;




/************************************
			END USER CODE 
************************************/
%mend;