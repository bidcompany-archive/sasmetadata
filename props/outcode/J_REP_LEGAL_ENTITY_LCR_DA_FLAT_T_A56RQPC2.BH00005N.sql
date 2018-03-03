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
				select REP_LEGAL_ENTITY_LCR_DA_FLAT.*
				from P_KGU_GLM_LCRDA.REP_LEGAL_ENTITY_LCR_DA_FLAT REP_LEGAL_ENTITY_LCR_DA_FLAT 
			
		) by teradata;




/************************************
			END USER CODE 
************************************/
%mend;