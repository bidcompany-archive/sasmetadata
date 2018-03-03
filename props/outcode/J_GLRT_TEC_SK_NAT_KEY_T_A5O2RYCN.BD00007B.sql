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


execute (replace view &tdStagedb..&table_target as


          (			
			  select 
             t1.TEMPLATE_ROW_CD,
             t2.*
          from(select distinct TEMPLATE_ROW_CD,GLRT_SOURCE_CD
               from P_KGU_GLM_UES.GLRT_WTE_US
              ) t1 left join (SELECT	SK_CODE,COLUMNNAME,COLUMN_TYPE
                              FROM /*P_KGU_GLM_UES.GLRT_TEC_SK_NAT_KEY*/ P_DSASVA_STG18.COMMON_SOURCE_COLUMN
                             ) t2
             on t1.GLRT_SOURCE_CD=t2.SK_CODE
) 
) by teradata;



/************************************
			END USER CODE 
************************************/
%mend;