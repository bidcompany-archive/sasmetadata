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
          DROP TABLE P_DSASVA_STG18.ROW_NUM_76;
        ) by teradata;

execute (
          CREATE  table P_DSASVA_STG18.ROW_NUM_76
          ( ROW_NUM  VARCHAR(20),		  
           ITEM_DESC  VARCHAR(500) )
        ) by teradata;

execute (
  INSERT INTO P_DSASVA_STG18.ROW_NUM_76 (ROW_NUM, ITEM_DESC) VALUES ('001','CALCULATIONS')
        ) by teradata;
execute (
  INSERT INTO P_DSASVA_STG18.ROW_NUM_76 (ROW_NUM, ITEM_DESC) VALUES ('005','NUMERATOR, DENOMINATOR,RATIO')
        ) by teradata;
execute (
INSERT INTO P_DSASVA_STG18.ROW_NUM_76 (ROW_NUM, ITEM_DESC) VALUES ('035','NUMERATOR  CALCULATION')
        ) by teradata;
execute (
INSERT INTO P_DSASVA_STG18.ROW_NUM_76 (ROW_NUM, ITEM_DESC) VALUES ('295','DENOMINATOR CALCULATIONS') 
        ) by teradata;
execute (
INSERT INTO P_DSASVA_STG18.ROW_NUM_76 (ROW_NUM, ITEM_DESC) VALUES ('375','PILLAR 2') 
        ) by teradata;

execute (
          replace view P_DSASVA_STG18.DISTINCT_TEMPLATE_C_76 as
   SELECT DISTINCT 
                          '' as TEMPLATE_CD,
                          '' as PROVIDER_CD,
                          t0.LEGAL_ENTITY_CD, 
                          t0.PERIMETER_CD, 
                          t0.REPORTING_CURRENCY_CD, 
                          t0.FREQ_CD, 
                          t0.REFERENCE_DT,                 
                          ('') as ITEM_ID,
                          null as FINAL_AMOUNT from P_KGU_GLM_LCRDA.LCR_DA_C_76_TEMPLATE t0
 ) by teradata;

execute (
  replace view P_DSASVA_STG18.DISTINCT_TEMPLATE_C_76_ as
  SELECT t1.*,t2.*
                  from P_DSASVA_STG18.DISTINCT_TEMPLATE_C_76 t1 cross join P_DSASVA_STG18.ROW_NUM_76 t2          
                ) by teradata;

execute (
        replace view &tdStagedb..&lasrTable.  as
          select 
            tab1.TEMPLATE_CD ,
            tab1.PROVIDER_CD ,
            tab1.LEGAL_ENTITY_CD ,
            tab1.PERIMETER_CD ,
            tab1.REPORTING_CURRENCY_CD ,
            tab1.FREQ_CD ,
            tab1.REFERENCE_DT ,
            tab1.ROW_NUM ,
            tab1.ITEM_ID ,
            tab1.ITEM_DESC ,
            tab1.FINAL_AMOUNT ,       
            tab2.PERIMETER_DESC,
            tab2.SUBCONSOLIDATED_CD_SEC,
            tab2.CONSOLIDATED_CD_SEC,
            (case 
              when(( tab1.reference_dt )= (select max(s.reference_dt) from P_KGU_GLM_LCRDA.LCR_DA_C_76_TEMPLATE s) )then 'Current Date'
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
                  tabx.ROW_NUM ,
                  tabx.ITEM_ID ,
                  tabx.ITEM_DESC ,
                  tabx.FINAL_AMOUNT 
                from P_KGU_GLM_LCRDA.LCR_DA_C_76_TEMPLATE tabx)
                union all
                (SELECT 
                  t1.TEMPLATE_CD ,
                  t1.PROVIDER_CD ,
                  t1.LEGAL_ENTITY_CD ,
                  t1.PERIMETER_CD ,
                  t1.REPORTING_CURRENCY_CD ,
                  t1.FREQ_CD ,
                  t1.REFERENCE_DT ,
                  t1.ROW_NUM ,
                  t1.ITEM_ID ,
                  t1.ITEM_DESC ,
                  t1.FINAL_AMOUNT  from P_DSASVA_STG18.DISTINCT_TEMPLATE_C_76_ t1)           
                ) tab1 
                  left join P_KGU_GLM_LCRDA.REP_LEGAL_ENTITY_LCR_DA_FLAT tab2 
                    ON 
                    tab1.LEGAL_ENTITY_CD = tab2.LEGAL_ENTITY_CD_SEC

        ) by teradata;
/*
execute (
          DROP TABLE P_DSASVA_STG18.ROW_NUM_76;
        ) by teradata;
*/
/************************************
			END USER CODE 
************************************/
%mend;