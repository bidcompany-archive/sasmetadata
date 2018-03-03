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
				select US_TEMPLATE_BI.*
				from P_KGU_GLM_UES.US_TEMPLATE_BI US_TEMPLATE_BI 
			
		) by teradata;




/************************************
			END USER CODE 
************************************/
%mend;