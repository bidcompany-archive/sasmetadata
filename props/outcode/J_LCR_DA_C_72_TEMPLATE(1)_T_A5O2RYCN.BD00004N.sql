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
            select tab1.*,
                   tab2.PERIMETER_DESC,
                   tab2.SUBCONSOLIDATED_CD_SEC,
                   tab2.CONSOLIDATED_CD_SEC,
                   (case 
                      when(( tab1.reference_dt )= (select max(s.reference_dt) from P_KGU_GLM_LCRDA.LCR_DA_C_72_TEMPLATE s) )then 'Current Date'
                      else 'Other Dates'
                   end ) as Current_Date_1 

				from ((select LCR_DA_C_72_TEMPLATE.*
				      from P_KGU_GLM_LCRDA.LCR_DA_C_72_TEMPLATE LCR_DA_C_72_TEMPLATE)
                    union
	            ( SELECT DISTINCT t1.REFERENCE_DT, 
	               t1.PERIMETER_CD, 
	               t1.LEGAL_ENTITY_CD, 
	               t1.FREQ_CD, 
	               t1.REPORTING_CURRENCY_CD, 
	               /* Row_NUM */
	               ('475') as  Row_NUM, 
	               /* item_desc */
	               ('MEMORANDUM ITEM') as item_desc from  P_KGU_GLM_LCRDA.LCR_DA_C_72_TEMPLATE t1)
                 ) tab1 
                    left join P_KGU_GLM_LCRDA.REP_LEGAL_ENTITY_LCR_DA_FLAT tab2 
                     ON 
                       tab1.LEGAL_ENTITY_CD = tab2.LEGAL_ENTITY_CD_SEC

		) by teradata;




/************************************
			END USER CODE 
************************************/
%mend;