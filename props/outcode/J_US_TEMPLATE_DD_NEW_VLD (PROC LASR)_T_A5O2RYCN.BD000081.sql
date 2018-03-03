/************************************
			BEGIN USER CODE 
************************************/

%macro select_sum(sum_row,plus,minus);

(
SELECT DISTINCT   
              (case 
                 when((reference_dt )= (select max(s.reference_dt) from P_KGU_GLM_UES.GLRT_WTE_US s) )
                 then 'Current Date'
                 else 'Other Dates'
              end ) as "Current_Date" ,
              REFERENCE_DT,cast(SK_ID_NOCFL as VARCHAR(20)) SK_ID_NOCFL, SCENARIO_CD, SUBSCENARIO_CD,
              FREQ_CD, ORIGINAL_REFERENCE_DT, PROVIDER_CD, 
               FLOW_TYPE_CD, GLRT_SOURCE_CD, TEMPLATE_CD, 
              &sum_row TEMPLATE_ROW_CD,
              ' ' TEMPLATE_ROW_DESC, 
              LEGAL_ENTITY_CD, INTRAGROUP_COUNTERPARTY_IND,
              INTRAGROUP_COUNTERPARTY_CD, FINREP_CPARTY_TYPE_CD, PERIMETER_J_FLAG,
              PERIMETER_C_FLAG, US_INDIVIDUAL_LE_CD, US_CONSOLIDATED_LE_CD,
              t1.COUNTRY_CD, COUNTRY_DESC, ISIN_NUM, BALANCE_SHEET_ITEM_CD, REPORTING_CURRENCY_CD,
              CURRENCY_CD, B3_COUNTERPARTY_TYPE_CD, ASSET_CLASS_CD, COLLATERAL_TYPE_CD,
              /*CONTRACT_TYPE_CD,*/ GUARANTEE_TYPE_CD, WTE_INSTRUMENT_TYPE_CD,
              ORIGINAL_MATURITY_CD, OPERATION_TYPE_CD, WTE_SIGN,  RETAIL_IND,
               SECURITY_TYPE_CD,
              SPEC_CREDIT_TYPE_CD, INTRAGROUP_COUNTRY_CD, INTRAGROUP_COUNTRY_DESC,
              t2.BRANCH_DESC,
              t2.CATEGORY,
              t2.ASSIGNED_COUNTRY,
              t2.LIQUIDITY_REFERENCE_BANK,
      /* Campi aggiunti v1.10*/
              STRUCTURED_IND,SUBORDINATED_IND,MORTGAGE_TYPE_CD,SPEC_DEBT_TYPE_CD,CREDIT_STATUS_CD,
      /* Campo richiesta Benetello*/
              DIM_FB_LE_ISO_COUNTRY_CD,
              (
                min(case when  trim(TEMPLATE_ROW_CD) in (&plus)  then (ACTUAL_CARRYING_VALUE) else 0 end)-
                min(case when  trim(TEMPLATE_ROW_CD) in (&minus) then (ACTUAL_CARRYING_VALUE) else 0 end)
              )   as ACTUAL_CARRYING_VALUE,      
              (
                sum(case when  trim(TEMPLATE_ROW_CD) in (&plus) then ACTUAL_NOMINAL_VALUE else 0 end)-
                sum(case when  trim(TEMPLATE_ROW_CD) in (&minus) then ACTUAL_NOMINAL_VALUE else 0 end)
              )   as ACTUAL_NOMINAL_VALUE,
(
sum(case when  trim(TEMPLATE_ROW_CD) in (&plus)  and MATURITY_CD='1M' then RUNOFF_VALUE else 0 end) -
sum(case when  trim(TEMPLATE_ROW_CD) in (&minus) and MATURITY_CD='1M' then RUNOFF_VALUE else 0 end)
) as M1,
(
sum(case when  trim(TEMPLATE_ROW_CD) in (&plus)  and MATURITY_CD='2M' then RUNOFF_VALUE else 0 end) -
sum(case when  trim(TEMPLATE_ROW_CD) in (&minus) and MATURITY_CD='2M' then RUNOFF_VALUE else 0 end)
) AS M2,
(
sum(case when  trim(TEMPLATE_ROW_CD) in (&plus)  and MATURITY_CD='3M' then RUNOFF_VALUE else 0 end) -
sum(case when  trim(TEMPLATE_ROW_CD) in (&minus) and MATURITY_CD='3M' then RUNOFF_VALUE else 0 end)
) AS M3,
(
sum(case when  trim(TEMPLATE_ROW_CD) in (&plus)  and MATURITY_CD='4M' then RUNOFF_VALUE else 0 end) -
sum(case when  trim(TEMPLATE_ROW_CD) in (&minus) and MATURITY_CD='4M' then RUNOFF_VALUE else 0 end)
) AS M4,
(
sum(case when  trim(TEMPLATE_ROW_CD) in (&plus)  and MATURITY_CD='5M' then RUNOFF_VALUE else 0 end) -
sum(case when  trim(TEMPLATE_ROW_CD) in (&minus) and MATURITY_CD='5M' then RUNOFF_VALUE else 0 end)
) AS M5,       
(
sum(case when  trim(TEMPLATE_ROW_CD) in (&plus)  and MATURITY_CD='6M' then RUNOFF_VALUE else 0 end) -
sum(case when  trim(TEMPLATE_ROW_CD) in (&minus) and MATURITY_CD='6M' then RUNOFF_VALUE else 0 end)
) AS M6,       
(
sum(case when  trim(TEMPLATE_ROW_CD) in (&plus)  and MATURITY_CD='7M' then RUNOFF_VALUE else 0 end) -
sum(case when  trim(TEMPLATE_ROW_CD) in (&minus) and MATURITY_CD='7M' then RUNOFF_VALUE else 0 end)
) AS M7,       
(
sum(case when  trim(TEMPLATE_ROW_CD) in (&plus)  and MATURITY_CD='8M' then RUNOFF_VALUE else 0 end) -
sum(case when  trim(TEMPLATE_ROW_CD) in (&minus) and MATURITY_CD='8M' then RUNOFF_VALUE else 0 end)
) AS M8,       
(
sum(case when  trim(TEMPLATE_ROW_CD) in (&plus)  and MATURITY_CD='9M' then RUNOFF_VALUE else 0 end) -
sum(case when  trim(TEMPLATE_ROW_CD) in (&minus) and MATURITY_CD='9M' then RUNOFF_VALUE else 0 end)
) AS M9,
(
sum(case when  trim(TEMPLATE_ROW_CD) in (&plus)  and MATURITY_CD='10M' then RUNOFF_VALUE else 0 end) -
sum(case when  trim(TEMPLATE_ROW_CD) in (&minus) and MATURITY_CD='10M' then RUNOFF_VALUE else 0 end)
) AS M10,
(
sum(case when  trim(TEMPLATE_ROW_CD) in (&plus)  and MATURITY_CD='11M' then RUNOFF_VALUE else 0 end) -
sum(case when  trim(TEMPLATE_ROW_CD) in (&minus) and MATURITY_CD='11M' then RUNOFF_VALUE else 0 end)
) AS M11,
(
sum(case when  trim(TEMPLATE_ROW_CD) in (&plus)  and MATURITY_CD='12M' then RUNOFF_VALUE else 0 end) -
sum(case when  trim(TEMPLATE_ROW_CD) in (&minus) and MATURITY_CD='12M' then RUNOFF_VALUE else 0 end)
) AS M12,
(
sum(case when  trim(TEMPLATE_ROW_CD) in (&plus)  and MATURITY_CD='13M' then RUNOFF_VALUE else 0 end) -
sum(case when  trim(TEMPLATE_ROW_CD) in (&minus) and MATURITY_CD='13M' then RUNOFF_VALUE else 0 end)
) AS M13,
(
sum(case when  trim(TEMPLATE_ROW_CD) in (&plus)  and MATURITY_CD='14M' then RUNOFF_VALUE else 0 end) -
sum(case when  trim(TEMPLATE_ROW_CD) in (&minus) and MATURITY_CD='14M' then RUNOFF_VALUE else 0 end)
) AS M14,
(
sum(case when  trim(TEMPLATE_ROW_CD) in (&plus)  and MATURITY_CD='15M' then RUNOFF_VALUE else 0 end) -
sum(case when  trim(TEMPLATE_ROW_CD) in (&minus) and MATURITY_CD='15M' then RUNOFF_VALUE else 0 end)
) AS M15,
(
sum(case when  trim(TEMPLATE_ROW_CD) in (&plus)  and MATURITY_CD='16M' then RUNOFF_VALUE else 0 end) -
sum(case when  trim(TEMPLATE_ROW_CD) in (&minus) and MATURITY_CD='16M' then RUNOFF_VALUE else 0 end)
) AS M16,
(
sum(case when  trim(TEMPLATE_ROW_CD) in (&plus)  and MATURITY_CD='17M' then RUNOFF_VALUE else 0 end) -
sum(case when  trim(TEMPLATE_ROW_CD) in (&minus) and MATURITY_CD='17M' then RUNOFF_VALUE else 0 end)
) AS M17,
(
sum(case when  trim(TEMPLATE_ROW_CD) in (&plus)  and MATURITY_CD='18M' then RUNOFF_VALUE else 0 end) -
sum(case when  trim(TEMPLATE_ROW_CD) in (&minus) and MATURITY_CD='18M' then RUNOFF_VALUE else 0 end)
) AS M18,
(
sum(case when  trim(TEMPLATE_ROW_CD) in (&plus)  and MATURITY_CD='19M' then RUNOFF_VALUE else 0 end) -
sum(case when  trim(TEMPLATE_ROW_CD) in (&minus) and MATURITY_CD='19M' then RUNOFF_VALUE else 0 end)
) AS M19,
(
sum(case when  trim(TEMPLATE_ROW_CD) in (&plus)  and MATURITY_CD='20M' then RUNOFF_VALUE else 0 end) -
sum(case when  trim(TEMPLATE_ROW_CD) in (&minus) and MATURITY_CD='20M' then RUNOFF_VALUE else 0 end)
) AS M20,
(
sum(case when  trim(TEMPLATE_ROW_CD) in (&plus)  and MATURITY_CD='21M' then RUNOFF_VALUE else 0 end) -
sum(case when  trim(TEMPLATE_ROW_CD) in (&minus) and MATURITY_CD='21M' then RUNOFF_VALUE else 0 end)
) AS M21,
(
sum(case when  trim(TEMPLATE_ROW_CD) in (&plus)  and MATURITY_CD='22M' then RUNOFF_VALUE else 0 end) -
sum(case when  trim(TEMPLATE_ROW_CD) in (&minus) and MATURITY_CD='22M' then RUNOFF_VALUE else 0 end)
) AS M22,
(
sum(case when  trim(TEMPLATE_ROW_CD) in (&plus)  and MATURITY_CD='23M' then RUNOFF_VALUE else 0 end) -
sum(case when  trim(TEMPLATE_ROW_CD) in (&minus) and MATURITY_CD='23M' then RUNOFF_VALUE else 0 end)
) AS M23,
(
sum(case when  trim(TEMPLATE_ROW_CD) in (&plus)  and MATURITY_CD='24M' then RUNOFF_VALUE else 0 end) -
sum(case when  trim(TEMPLATE_ROW_CD) in (&minus) and MATURITY_CD='24M' then RUNOFF_VALUE else 0 end)
) AS M24,
(
sum(case when  trim(TEMPLATE_ROW_CD) in (&plus)  and MATURITY_CD='25M' then RUNOFF_VALUE else 0 end) -
sum(case when  trim(TEMPLATE_ROW_CD) in (&minus) and MATURITY_CD='25M' then RUNOFF_VALUE else 0 end)
) AS M25,
(
sum(case when  trim(TEMPLATE_ROW_CD) in (&plus)  and MATURITY_CD='26M' then RUNOFF_VALUE else 0 end) -
sum(case when  trim(TEMPLATE_ROW_CD) in (&minus) and MATURITY_CD='26M' then RUNOFF_VALUE else 0 end)
) AS M26,
(
sum(case when  trim(TEMPLATE_ROW_CD) in (&plus)  and MATURITY_CD='27M' then RUNOFF_VALUE else 0 end) -
sum(case when  trim(TEMPLATE_ROW_CD) in (&minus) and MATURITY_CD='27M' then RUNOFF_VALUE else 0 end)
) AS M27,
(
sum(case when  trim(TEMPLATE_ROW_CD) in (&plus)  and MATURITY_CD='28M' then RUNOFF_VALUE else 0 end) -
sum(case when  trim(TEMPLATE_ROW_CD) in (&minus) and MATURITY_CD='28M' then RUNOFF_VALUE else 0 end)
) AS M28,
(
sum(case when  trim(TEMPLATE_ROW_CD) in (&plus)  and MATURITY_CD='29M' then RUNOFF_VALUE else 0 end) -
sum(case when  trim(TEMPLATE_ROW_CD) in (&minus) and MATURITY_CD='29M' then RUNOFF_VALUE else 0 end)
) AS M29,
(
sum(case when  trim(TEMPLATE_ROW_CD) in (&plus)  and MATURITY_CD='30M' then RUNOFF_VALUE else 0 end) -
sum(case when  trim(TEMPLATE_ROW_CD) in (&minus) and MATURITY_CD='30M' then RUNOFF_VALUE else 0 end)
) AS M30,
(
sum(case when  trim(TEMPLATE_ROW_CD) in (&plus)  and MATURITY_CD='31M' then RUNOFF_VALUE else 0 end) -
sum(case when  trim(TEMPLATE_ROW_CD) in (&minus) and MATURITY_CD='31M' then RUNOFF_VALUE else 0 end)
) AS M31,
(
sum(case when  trim(TEMPLATE_ROW_CD) in (&plus)  and MATURITY_CD='32M' then RUNOFF_VALUE else 0 end) -
sum(case when  trim(TEMPLATE_ROW_CD) in (&minus) and MATURITY_CD='32M' then RUNOFF_VALUE else 0 end)
) AS M32,
(
sum(case when  trim(TEMPLATE_ROW_CD) in (&plus)  and MATURITY_CD='33M' then RUNOFF_VALUE else 0 end) -
sum(case when  trim(TEMPLATE_ROW_CD) in (&minus) and MATURITY_CD='33M' then RUNOFF_VALUE else 0 end)
) AS M33,
(
sum(case when  trim(TEMPLATE_ROW_CD) in (&plus)  and MATURITY_CD='34M' then RUNOFF_VALUE else 0 end) -
sum(case when  trim(TEMPLATE_ROW_CD) in (&minus) and MATURITY_CD='34M' then RUNOFF_VALUE else 0 end)
) AS M34,
(
sum(case when  trim(TEMPLATE_ROW_CD) in (&plus)  and MATURITY_CD='35M' then RUNOFF_VALUE else 0 end) -
sum(case when  trim(TEMPLATE_ROW_CD) in (&minus) and MATURITY_CD='35M' then RUNOFF_VALUE else 0 end)
) AS M35,
(
sum(case when  trim(TEMPLATE_ROW_CD) in (&plus)  and MATURITY_CD='36M' then RUNOFF_VALUE else 0 end) -
sum(case when  trim(TEMPLATE_ROW_CD) in (&minus) and MATURITY_CD='36M' then RUNOFF_VALUE else 0 end)
) AS M36,
(
sum(case when  trim(TEMPLATE_ROW_CD) in (&plus)  and MATURITY_CD='37M' then RUNOFF_VALUE else 0 end) -
sum(case when  trim(TEMPLATE_ROW_CD) in (&minus) and MATURITY_CD='37M' then RUNOFF_VALUE else 0 end)
) AS M37,
(
sum(case when  trim(TEMPLATE_ROW_CD) in (&plus)  and MATURITY_CD='38M' then RUNOFF_VALUE else 0 end) -
sum(case when  trim(TEMPLATE_ROW_CD) in (&minus) and MATURITY_CD='38M' then RUNOFF_VALUE else 0 end)
) AS M38,
(
sum(case when  trim(TEMPLATE_ROW_CD) in (&plus)  and MATURITY_CD='39M' then RUNOFF_VALUE else 0 end) -
sum(case when  trim(TEMPLATE_ROW_CD) in (&minus) and MATURITY_CD='39M' then RUNOFF_VALUE else 0 end)
) AS M39,
(
sum(case when  trim(TEMPLATE_ROW_CD) in (&plus)  and MATURITY_CD='39Mto42M' then RUNOFF_VALUE else 0 end) -
sum(case when  trim(TEMPLATE_ROW_CD) in (&minus) and MATURITY_CD='39Mto42M' then RUNOFF_VALUE else 0 end)
) AS M39toM42,
(
sum(case when  trim(TEMPLATE_ROW_CD) in (&plus)  and MATURITY_CD='42Mto45M' then RUNOFF_VALUE else 0 end) -
sum(case when  trim(TEMPLATE_ROW_CD) in (&minus) and MATURITY_CD='42Mto45M' then RUNOFF_VALUE else 0 end)
) AS M42toM45,
(
sum(case when  trim(TEMPLATE_ROW_CD) in (&plus)  and MATURITY_CD='45Mto48M' then RUNOFF_VALUE else 0 end) -
sum(case when  trim(TEMPLATE_ROW_CD) in (&minus) and MATURITY_CD='45Mto48M' then RUNOFF_VALUE else 0 end)
) AS M45toM48,
(
sum(case when  trim(TEMPLATE_ROW_CD) in (&plus)  and MATURITY_CD='48Mto51M' then RUNOFF_VALUE else 0 end) -
sum(case when  trim(TEMPLATE_ROW_CD) in (&minus) and MATURITY_CD='48Mto51M' then RUNOFF_VALUE else 0 end)
) AS M48toM51,
(
sum(case when  trim(TEMPLATE_ROW_CD) in (&plus)  and MATURITY_CD='51Mto54M' then RUNOFF_VALUE else 0 end) -
sum(case when  trim(TEMPLATE_ROW_CD) in (&minus) and MATURITY_CD='51Mto54M' then RUNOFF_VALUE else 0 end)
) AS M51toM54,
(
sum(case when  trim(TEMPLATE_ROW_CD) in (&plus)  and MATURITY_CD='54Mto57M' then RUNOFF_VALUE else 0 end) -
sum(case when  trim(TEMPLATE_ROW_CD) in (&minus) and MATURITY_CD='54Mto57M' then RUNOFF_VALUE else 0 end)
) AS M54toM57,
(
sum(case when  trim(TEMPLATE_ROW_CD) in (&plus)  and MATURITY_CD='57Mto5Y' then RUNOFF_VALUE else 0 end) -
sum(case when  trim(TEMPLATE_ROW_CD) in (&minus) and MATURITY_CD='57Mto5Y' then RUNOFF_VALUE else 0 end)
) AS M57toY5,
(
sum(case when  trim(TEMPLATE_ROW_CD) in (&plus)  and MATURITY_CD='5Yto7Y' then RUNOFF_VALUE else 0 end) -
sum(case when  trim(TEMPLATE_ROW_CD) in (&minus) and MATURITY_CD='5Yto7Y' then RUNOFF_VALUE else 0 end)
)  AS Y5toY7,
(
sum(case when  trim(TEMPLATE_ROW_CD) in (&plus)  and MATURITY_CD='7Yto10Y' then RUNOFF_VALUE else 0 end) -
sum(case when  trim(TEMPLATE_ROW_CD) in (&minus) and MATURITY_CD='7Yto10Y' then RUNOFF_VALUE else 0 end)
) AS Y7toY10,
(
sum(case when  trim(TEMPLATE_ROW_CD) in (&plus)  and MATURITY_CD='10Yto15Y' then RUNOFF_VALUE else 0 end) -
sum(case when  trim(TEMPLATE_ROW_CD) in (&minus) and MATURITY_CD='10Yto15Y' then RUNOFF_VALUE else 0 end)
) AS Y10toY15,
(
sum(case when  trim(TEMPLATE_ROW_CD) in (&plus)  and MATURITY_CD='15Yto20Y' then RUNOFF_VALUE else 0 end) -
sum(case when  trim(TEMPLATE_ROW_CD) in (&minus) and MATURITY_CD='15Yto20Y' then RUNOFF_VALUE else 0 end)
) AS Y15toY20,
(
sum(case when  trim(TEMPLATE_ROW_CD) in (&plus)  and MATURITY_CD='OVER' then RUNOFF_VALUE else 0 end) -
sum(case when  trim(TEMPLATE_ROW_CD) in (&minus) and MATURITY_CD='OVER' then RUNOFF_VALUE else 0 end)
) AS _OVER



FROM P_KGU_GLM_UES.GLRT_WTE_US t1 
left join P_DSASVA_STG18.US_TEC_COUNTRY_TABLE t2 
  on t1.LEGAL_ENTITY_CD = t2.BRANCH_CD
where  trim(TEMPLATE_ROW_CD) in (&plus,&minus)
GROUP BY 1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,
       46,47,48,49,50
)	



%mend select_sum;

%macro select;

execute (replace view &tdStagedb..US_SHOULDER as
  select a1.*,a2.* 
  from
  &tdStagedb..US_ROW_TEMPLATE a1
  cross join
  (select distinct 
    (case 
       when((reference_dt )= (select max(s.reference_dt) from P_KGU_GLM_UES.GLRT_WTE_US s) )then 'Current Date'
       else 'Other Dates'
     end 
    ) as "Current_Date" ,
    REFERENCE_DT,
    FREQ_CD,
    PERIMETER_J_FLAG,
    PERIMETER_C_FLAG,
    t2.ASSIGNED_COUNTRY,
    US_INDIVIDUAL_LE_CD,
    US_CONSOLIDATED_LE_CD,        
    t2.LIQUIDITY_REFERENCE_BANK,
    LEGAL_ENTITY_CD, 
    B3_COUNTERPARTY_TYPE_CD,
    t2.CATEGORY/*,
	t1.CURRENCY_CD*/
  FROM P_KGU_GLM_UES.GLRT_WTE_US t1 left join P_DSASVA_STG18.US_TEC_COUNTRY_TABLE t2 
    on t1.LEGAL_ENTITY_CD = t2.BRANCH_CD) a2
  ) by teradata;

execute (
				  replace view &tdStagedb..US_DATA as
(			(SELECT DISTINCT  
              (case 
                      when((reference_dt )= (select max(s.reference_dt) from P_KGU_GLM_UES.GLRT_WTE_US s) )then 'Current Date'
                      else 'Other Dates'
                   end ) as "Current_Date" ,
         REFERENCE_DT,cast(SK_ID_NOCFL as VARCHAR(20)) SK_ID_NOCFL, SCENARIO_CD, SUBSCENARIO_CD,
        FREQ_CD, ORIGINAL_REFERENCE_DT, PROVIDER_CD, 
         FLOW_TYPE_CD, GLRT_SOURCE_CD, TEMPLATE_CD, TEMPLATE_ROW_CD,
        TEMPLATE_ROW_DESC, LEGAL_ENTITY_CD, INTRAGROUP_COUNTERPARTY_IND,
        INTRAGROUP_COUNTERPARTY_CD, FINREP_CPARTY_TYPE_CD, PERIMETER_J_FLAG,
        PERIMETER_C_FLAG, US_INDIVIDUAL_LE_CD, US_CONSOLIDATED_LE_CD,
        t1.COUNTRY_CD, COUNTRY_DESC, ISIN_NUM, BALANCE_SHEET_ITEM_CD, REPORTING_CURRENCY_CD,
        CURRENCY_CD, B3_COUNTERPARTY_TYPE_CD, ASSET_CLASS_CD, COLLATERAL_TYPE_CD,
        /*CONTRACT_TYPE_CD,*/ GUARANTEE_TYPE_CD, WTE_INSTRUMENT_TYPE_CD,
        ORIGINAL_MATURITY_CD, OPERATION_TYPE_CD, WTE_SIGN,  RETAIL_IND,
        SECURITY_TYPE_CD,
        SPEC_CREDIT_TYPE_CD, INTRAGROUP_COUNTRY_CD, INTRAGROUP_COUNTRY_DESC,
        t2.BRANCH_DESC,
        t2.CATEGORY,
        t2.ASSIGNED_COUNTRY,
        t2.LIQUIDITY_REFERENCE_BANK,
/* Campi aggiunti v1.10*/
        STRUCTURED_IND,SUBORDINATED_IND,MORTGAGE_TYPE_CD,SPEC_DEBT_TYPE_CD,CREDIT_STATUS_CD,
/* Campo richiesta Benetello*/
        DIM_FB_LE_ISO_COUNTRY_CD,
        MIN(ACTUAL_CARRYING_VALUE) AS ACTUAL_CARRYING_VALUE,
        SUM(ACTUAL_NOMINAL_VALUE) AS ACTUAL_NOMINAL_VALUE,
        SUM(CASE WHEN MATURITY_CD='1M' THEN RUNOFF_VALUE ELSE 0 END ) AS M1,     
        SUM(CASE WHEN MATURITY_CD='2M' THEN RUNOFF_VALUE ELSE 0 END ) AS M2,
        SUM(CASE WHEN MATURITY_CD='3M' THEN RUNOFF_VALUE ELSE 0 END ) AS M3,
        SUM(CASE WHEN MATURITY_CD='4M' THEN RUNOFF_VALUE ELSE 0 END ) AS M4,
        SUM(CASE WHEN MATURITY_CD='5M' THEN RUNOFF_VALUE ELSE 0 END ) AS M5,        
        SUM(CASE WHEN MATURITY_CD='6M' THEN RUNOFF_VALUE ELSE 0 END ) AS M6,        
        SUM(CASE WHEN MATURITY_CD='7M' THEN RUNOFF_VALUE ELSE 0 END ) AS M7,        
        SUM(CASE WHEN MATURITY_CD='8M' THEN RUNOFF_VALUE ELSE 0 END ) AS M8,        
        SUM(CASE WHEN MATURITY_CD='9M' THEN RUNOFF_VALUE ELSE 0 END ) AS M9,
        SUM(CASE WHEN MATURITY_CD='10M' THEN RUNOFF_VALUE ELSE 0 END ) AS M10,
        SUM(CASE WHEN MATURITY_CD='11M' THEN RUNOFF_VALUE ELSE 0 END ) AS M11,
        SUM(CASE WHEN MATURITY_CD='12M' THEN RUNOFF_VALUE ELSE 0 END ) AS M12,
        SUM(CASE WHEN MATURITY_CD='13M' THEN RUNOFF_VALUE ELSE 0 END ) AS M13,
        SUM(CASE WHEN MATURITY_CD='14M' THEN RUNOFF_VALUE ELSE 0 END ) AS M14,
        SUM(CASE WHEN MATURITY_CD='15M' THEN RUNOFF_VALUE ELSE 0 END ) AS M15,
        SUM(CASE WHEN MATURITY_CD='16M' THEN RUNOFF_VALUE ELSE 0 END ) AS M16,
        SUM(CASE WHEN MATURITY_CD='17M' THEN RUNOFF_VALUE ELSE 0 END ) AS M17,
        SUM(CASE WHEN MATURITY_CD='18M' THEN RUNOFF_VALUE ELSE 0 END ) AS M18,
        SUM(CASE WHEN MATURITY_CD='19M' THEN RUNOFF_VALUE ELSE 0 END ) AS M19,
        SUM(CASE WHEN MATURITY_CD='20M' THEN RUNOFF_VALUE ELSE 0 END ) AS M20,
        SUM(CASE WHEN MATURITY_CD='21M' THEN RUNOFF_VALUE ELSE 0 END ) AS M21,
        SUM(CASE WHEN MATURITY_CD='22M' THEN RUNOFF_VALUE ELSE 0 END ) AS M22,
        SUM(CASE WHEN MATURITY_CD='23M' THEN RUNOFF_VALUE ELSE 0 END ) AS M23,
        SUM(CASE WHEN MATURITY_CD='24M' THEN RUNOFF_VALUE ELSE 0 END ) AS M24,
        SUM(CASE WHEN MATURITY_CD='25M' THEN RUNOFF_VALUE ELSE 0 END ) AS M25,
        SUM(CASE WHEN MATURITY_CD='26M' THEN RUNOFF_VALUE ELSE 0 END ) AS M26,
        SUM(CASE WHEN MATURITY_CD='27M' THEN RUNOFF_VALUE ELSE 0 END ) AS M27,
        SUM(CASE WHEN MATURITY_CD='28M' THEN RUNOFF_VALUE ELSE 0 END ) AS M28,
        SUM(CASE WHEN MATURITY_CD='29M' THEN RUNOFF_VALUE ELSE 0 END ) AS M29,
        SUM(CASE WHEN MATURITY_CD='30M' THEN RUNOFF_VALUE ELSE 0 END ) AS M30,
        SUM(CASE WHEN MATURITY_CD='31M' THEN RUNOFF_VALUE ELSE 0 END ) AS M31,
        SUM(CASE WHEN MATURITY_CD='32M' THEN RUNOFF_VALUE ELSE 0 END ) AS M32,
        SUM(CASE WHEN MATURITY_CD='33M' THEN RUNOFF_VALUE ELSE 0 END ) AS M33,
        SUM(CASE WHEN MATURITY_CD='34M' THEN RUNOFF_VALUE ELSE 0 END ) AS M34,
        SUM(CASE WHEN MATURITY_CD='35M' THEN RUNOFF_VALUE ELSE 0 END ) AS M35,
        SUM(CASE WHEN MATURITY_CD='36M' THEN RUNOFF_VALUE ELSE 0 END ) AS M36,
        SUM(CASE WHEN MATURITY_CD='37M' THEN RUNOFF_VALUE ELSE 0 END ) AS M37,
        SUM(CASE WHEN MATURITY_CD='38M' THEN RUNOFF_VALUE ELSE 0 END ) AS M38,
        SUM(CASE WHEN MATURITY_CD='39M' THEN RUNOFF_VALUE ELSE 0 END ) AS M39,
        SUM(CASE WHEN MATURITY_CD='39Mto42M' THEN RUNOFF_VALUE ELSE 0 END ) AS M39toM42,
        SUM(CASE WHEN MATURITY_CD='42Mto45M' THEN RUNOFF_VALUE ELSE 0 END ) AS M42toM45,
        SUM(CASE WHEN MATURITY_CD='45Mto48M' THEN RUNOFF_VALUE ELSE 0 END ) AS M45toM48,
        SUM(CASE WHEN MATURITY_CD='48Mto51M' THEN RUNOFF_VALUE ELSE 0 END ) AS M48toM51,
        SUM(CASE WHEN MATURITY_CD='51Mto54M' THEN RUNOFF_VALUE ELSE 0 END ) AS M51to54M,
        SUM(CASE WHEN MATURITY_CD='54Mto57M' THEN RUNOFF_VALUE ELSE 0 END ) AS M54toM57,
        SUM(CASE WHEN MATURITY_CD='57Mto5Y' THEN RUNOFF_VALUE ELSE 0 END ) AS M57toY5,
        SUM(CASE WHEN MATURITY_CD='5Yto7Y' THEN RUNOFF_VALUE ELSE 0 END ) AS Y5toY7,
        SUM(CASE WHEN MATURITY_CD='7Yto10Y' THEN RUNOFF_VALUE ELSE 0 END ) AS Y7toY10,
        SUM(CASE WHEN MATURITY_CD='10Yto15Y' THEN RUNOFF_VALUE ELSE 0 END ) AS Y10toY15,
        SUM(CASE WHEN MATURITY_CD='15Yto20Y' THEN RUNOFF_VALUE ELSE 0 END ) AS Y15toY20,
        SUM(CASE WHEN MATURITY_CD='OVER' THEN RUNOFF_VALUE ELSE 0 END ) AS _OVER
 FROM P_KGU_GLM_UES.GLRT_WTE_US t1 left join P_DSASVA_STG18.US_TEC_COUNTRY_TABLE t2 
  on t1.LEGAL_ENTITY_CD = t2.BRANCH_CD

GROUP BY 1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,
         46,47,48,49,50
)

union all
%select_sum(%quote('010b'),%quote('010b.1','010b.2','010b.3'),%quote('BAZINGA'))

union all
%select_sum(%quote('010c.6'),%quote('010'),%quote('010c.1','010c.2','010c.3','010c.4','010c.5'))

union all
%select_sum(%quote('020a.6'),%quote('020'),%quote('020a.1','020a.2','020a.3','020a.4','020a.5'))

union all
%select_sum(%quote('050'),%quote('010','020','030','040'),%quote('BAZINGA')) 

union all
%select_sum(%quote('060'),%quote('060a','060b'),%quote('BAZINGA')) 

union all
%select_sum(%quote('070'),%quote('070a','070b','070c','070d'),%quote('BAZINGA')) 

union all
%select_sum(%quote('080'),%quote('080a.1','080a.2','080b.1','080b.2'),%quote('BAZINGA')) 

union all
%select_sum(%quote('080a'),%quote('080a.1','080a.2'),%quote('BAZINGA')) 

union all
%select_sum(%quote('080b'),%quote('080b.1','080b.2'),%quote('BAZINGA')) 

union all
%select_sum(%quote('090'),%quote('010','020','030','040','060a','060b', '070a','070b','070c','070d', '080a.1','080a.2','080b.1','080b.2'),%quote('BAZINGA')) 

union all
%select_sum(%quote('110'),%quote('010','020','030','040','060a','060b', '070a','070b','070c','070d', '080a.1','080a.2','080b.1','080b.2','100'),%quote('BAZINGA')) 

union all
%select_sum(%quote('130'),%quote('130a','130b'),%quote('BAZINGA')) 

union all
%select_sum(%quote('160'),%quote('160a','160b'),%quote('BAZINGA')) 

union all
%select_sum(%quote('170'),%quote('010','020','030','040','060a','060b', '070a','070b','070c','070d', '080a.1','080a.2','080b.1','080b.2','100','120','130a','130b','140','150','160a','160b'),%quote('BAZINGA')) 

union all
%select_sum(%quote('180'),%quote('180a','180b'),%quote('BAZINGA')) 

union all
%select_sum(%quote('190'),%quote('010','020','030','040','060a','060b', '070a','070b','070c','070d', '080a.1','080a.2','080b.1','080b.2','100','120','130a','130b','140','150','160a','160b','180a','180b'),%quote('BAZINGA')) 

union all
%select_sum(%quote('300'),%quote('200','210','220','230','240','250','260','270','280','290'),%quote('BAZINGA'))

union all
%select_sum(%quote('350'),%quote('350a','350b','350c'),%quote('BAZINGA')) 

union all
%select_sum(%quote('360'),%quote('310','320','330','340','350a','350b','350c'),%quote('BAZINGA')) 

union all
%select_sum(%quote('370'),%quote('010','020','030','040','060a','060b', '070a','070b','070c','070d', '080a.1','080a.2','080b.1','080b.2','100','120','130a','130b','140','150','160a','160b','180a','180b',
                                 '200','210','220','230','240','250','260','270','280','290',
                                 '310','320','330','340','350a','350b','350c'),%quote('BAZINGA')) 



union all
%select_sum(%quote('380'),%quote('BAZINGA'),%quote('010','020','030','040','060a','060b', '070a','070b','070c','070d', '080a.1','080a.2','080b.1','080b.2','100','120','130a','130b','140','150','160a','160b','180a','180b',
                                 '200','210','220','230','240','250','260','270','280','290',
                                 '310','320','330','340','350a','350b','350c')) 

	) )by teradata;
  
  execute (
  replace view &tdStagedb..&lasrTable. as
(
        select 
          b1.*,    
          b2.SK_ID_NOCFL, 
          b2.SCENARIO_CD, 
        b2.SUBSCENARIO_CD,
        b2.ORIGINAL_REFERENCE_DT, 
        b2.PROVIDER_CD, 
        b2.GLRT_SOURCE_CD, 
        b2.FLOW_TYPE_CD,
        b2.TEMPLATE_CD, 
        b2.INTRAGROUP_COUNTERPARTY_IND,
        b2.INTRAGROUP_COUNTERPARTY_CD, 
        b2.FINREP_CPARTY_TYPE_CD,    
        b2.COUNTRY_CD, 
        b2.COUNTRY_DESC, 
        b2.ISIN_NUM, 
        b2.BALANCE_SHEET_ITEM_CD,
        b2.REPORTING_CURRENCY_CD,
        b2.CURRENCY_CD, 
        b2.ASSET_CLASS_CD, 
        b2.COLLATERAL_TYPE_CD,
/* Campi eliminati v1.10*/
        /*b2.CONTRACT_TYPE_CD, */
        b2.GUARANTEE_TYPE_CD,
        b2.WTE_INSTRUMENT_TYPE_CD,
        b2.ORIGINAL_MATURITY_CD, 
        b2.OPERATION_TYPE_CD, 
        b2.WTE_SIGN,  
        b2.RETAIL_IND,
        b2.SECURITY_TYPE_CD,
        b2.SPEC_CREDIT_TYPE_CD, 
        b2.INTRAGROUP_COUNTRY_CD, 
        b2.INTRAGROUP_COUNTRY_DESC,
        b2.BRANCH_DESC,
/* Campi aggiunti v1.10*/
        b2.STRUCTURED_IND,b2.SUBORDINATED_IND,b2.MORTGAGE_TYPE_CD,b2.SPEC_DEBT_TYPE_CD,b2.CREDIT_STATUS_CD,
/* Campo richiesta Benetello*/
        b2.DIM_FB_LE_ISO_COUNTRY_CD,
        b2.ACTUAL_CARRYING_VALUE,
        /*b2.ACTUAL_CARRYING_VALUE_, */
        b2.ACTUAL_NOMINAL_VALUE,
        /*b2.ACTUAL_NOMINAL_VALUE_,*/
        b2.M1,
        /*b2.M1_,*/
        b2.M2,
        b2.M3,
        b2.M4,
        b2.M5,        
        b2.M6,        
        b2.M7,        
        b2.M8,        
        b2.M9,
        b2.M10,
        b2.M11,
        b2.M12,
        b2.M13,
        b2.M14,
        b2.M15,
        b2.M16,
        b2.M17,
        b2.M18,
        b2.M19,
        b2.M20,
        b2.M21,
        b2.M22,
        b2.M23,
        b2.M24,
        b2.M25,
        b2.M26,
        b2.M27,
        b2.M28,
        b2.M29,
        b2.M30,
        b2.M31,
        b2.M32,
        b2.M33,
        b2.M34,
        b2.M35,
        b2.M36,
        b2.M37,
        b2.M38,
        b2.M39,
        b2.M39toM42,
        b2.M42toM45,
        b2.M45toM48,
        b2.M48toM51,
        b2.M51to54M,
        b2.M54toM57,
        b2.M57toY5,
        b2.Y5toY7,
        b2.Y7toY10,
        b2.Y10toY15,
        b2.Y15toY20,
        b2._OVER 
      from &tdStagedb..US_SHOULDER b1 left join &tdStagedb..US_DATA b2
on (
    coalesce(b1.TEMPLATE_ROW_CD,'(NOVALUE)')=coalesce(b2.TEMPLATE_ROW_CD,'(NOVALUE)') AND
    b1.REFERENCE_DT=b2.REFERENCE_DT AND
    coalesce(b1.FREQ_CD,'(NOVALUE)')=coalesce(b2.FREQ_CD,'(NOVALUE)') AND
    coalesce(b1.PERIMETER_J_FLAG,'(NOVALUE)')=coalesce(b2.PERIMETER_J_FLAG,'(NOVALUE)') AND
    coalesce(b1.PERIMETER_C_FLAG,'(NOVALUE)')=coalesce(b2.PERIMETER_C_FLAG,'(NOVALUE)') AND
    coalesce(b1.ASSIGNED_COUNTRY,'(NOVALUE)')=coalesce(b2.ASSIGNED_COUNTRY,'(NOVALUE)') AND
    coalesce(b1.US_INDIVIDUAL_LE_CD,'(NOVALUE)')=coalesce(b2.US_INDIVIDUAL_LE_CD,'(NOVALUE)') AND
    coalesce(b1.US_CONSOLIDATED_LE_CD,'(NOVALUE)')=coalesce(b2.US_CONSOLIDATED_LE_CD,'(NOVALUE)') AND  
    coalesce(b1.LIQUIDITY_REFERENCE_BANK,'(NOVALUE)')=coalesce(b2.LIQUIDITY_REFERENCE_BANK,'(NOVALUE)') AND
    coalesce(b1.LEGAL_ENTITY_CD,'(NOVALUE)')=coalesce(b2.LEGAL_ENTITY_CD,'(NOVALUE)') AND
    coalesce(b1.B3_COUNTERPARTY_TYPE_CD,'(NOVALUE)')=coalesce(b2.B3_COUNTERPARTY_TYPE_CD,'(NOVALUE)') AND
    coalesce(b1.CATEGORY,'(NOVALUE)')=coalesce(b2.CATEGORY,'(NOVALUE)') 
	
   )
   )
) by teradata;



/************************************
			END USER CODE 
************************************/
%mend;