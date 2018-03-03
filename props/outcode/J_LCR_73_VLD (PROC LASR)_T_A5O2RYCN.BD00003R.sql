/***************************************
	Query Passthrough su TD
	Esempio USER CODE:
	execute (
				create view &tdStagedb..&lasrTable. as
				select geography_dim.*
				from &&tdStagedb..geography_dim geography_dim
			
		) by teradata;
***************************************/

%macro select;
/************************************
            BEGIN USER CODE 
************************************/

execute (
        replace view &tdStagedb..&lasrTable. /*&table_target.*/ as
          select 
            tab1.TEMPLATE_CD ,
            tab1.PROVIDER_CD ,
            tab1.LEGAL_ENTITY_CD ,
            tab1.PERIMETER_CD ,
            tab1.REPORTING_CURRENCY_CD ,
            tab1.FREQ_CD ,
            tab1.REFERENCE_DT ,
            tab1.ORDER_ROW_NUM ,
            case tab1.item_id     
                when '6'  then '1171'     
                when '7'  then'1211'     
                when '11'  then '1281' 
                else tab1.row_num 
            end   AS ROW_NUM,
            tab1.ITEM_ID ,
            tab1.ITEM_DESC ,
            tab1.AMOUNT_10 ,
            tab1.AMOUNT_20 ,
            tab1.AMOUNT_30 ,
            tab1.STANDARD_WEIGHT_040 ,
            tab1.APPLICABLE_WEIGHT_050,
            tab1.OUTFLOW_AMOUNT,
            tab2.PERIMETER_DESC as LEGAL_ENTITY_DESC,
            tab2.SUBCONSOLIDATED_CD_SEC,
            tab2.CONSOLIDATED_CD_SEC,
            (case 
              when(( tab1.reference_dt )= (select max(s.reference_dt) from P_KGU_GLM_LCRDA.LCR_DA_C_73_TEMPLATE s) )then 'Current Date'
              else 'Other Dates'
            end) as "Current_Date" 
          from (
                (select 
                  tabx.TEMPLATE_CD ,
                  tabx.PROVIDER_CD ,
                  tabx.LEGAL_ENTITY_CD ,
                  tabx.PERIMETER_CD ,
                  tabx.REPORTING_CURRENCY_CD ,
                  tabx.FREQ_CD ,
                  tabx.REFERENCE_DT ,
                  tabx.ORDER_ROW_NUM ,
                  tabx.ROW_NUM,
                  tabx.ITEM_ID ,
                  tabx.ITEM_DESC ,
                  tabx.AMOUNT_10 ,
                  tabx.AMOUNT_20 ,
                  tabx.AMOUNT_30 ,
                  tabx.STANDARD_WEIGHT_040 ,
                  tabx.APPLICABLE_WEIGHT_050,
                  tabx.OUTFLOW_AMOUNT 
                from P_KGU_GLM_LCRDA.LCR_DA_C_73_TEMPLATE tabx)
                union all
                (SELECT DISTINCT 
                  '' as TEMPLATE_CD,
                  '' as PROVIDER_CD,
                  t1.LEGAL_ENTITY_CD, 
                  t1.PERIMETER_CD, 
                  t1.REPORTING_CURRENCY_CD, 
                  t1.FREQ_CD, 
                  t1.REFERENCE_DT,
                  (1135) as ORDER_ROW_NUM,                  
                  ('1135') as  ROW_NUM, 
                  ('') as ITEM_ID,
                  ('MEMORANDUM ITEM') as item_desc,
                  null as AMOUNT_10 ,
                  null as AMOUNT_20 ,
                  null as AMOUNT_30 ,
                  '' as STANDARD_WEIGHT_040 ,
                  null as APPLICABLE_WEIGHT_050,
                  null as OUTFLOW_AMOUNT
                  from  P_KGU_GLM_LCRDA.LCR_DA_C_73_TEMPLATE t1)           
                ) tab1 
                  left join P_KGU_GLM_LCRDA.REP_LEGAL_ENTITY_LCR_DA_FLAT tab2 
                    ON 
                    tab1.LEGAL_ENTITY_CD = tab2.LEGAL_ENTITY_CD_SEC

        ) by teradata;
/************************************
			END USER CODE 
************************************/
%mend;