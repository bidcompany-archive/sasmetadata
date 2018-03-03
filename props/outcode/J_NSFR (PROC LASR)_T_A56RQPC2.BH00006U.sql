/***************************************
	Query Passthrough su TD
	Esempio USER CODE:
	execute (
				create view &tdStagedb..&lasrTable. as
				select geography_dim.*
				from &&tdStagedb..geography_dim geography_dim
			
		) by teradata;
***************************************/
%macro new_row(row,plus,minus,mult);
  SELECT	INSERT_TS, SK_ID_NOCFL, REFERENCE_DT, SCENARIO_CD, SUBSCENARIO_CD,
		FREQ_CD, ORIGINAL_REFERENCE_DT, PROVIDER_CD, CFL_DATE_RICALCOLATA,
		TIME_BUCKET_CD, FLOW_TYPE_CD, GLRT_SOURCE_CD, TEMPLATE_CD, cast(&row as varchar(20)) TEMPLATE_ROW_CD , 
		LEGAL_ENTITY_CD, INTRAGROUP_COUNTERPARTY_IND, INTRAGROUP_COUNTERPARTY_CD,
		FINREP_CPARTY_TYPE_CD, INDIVIDUAL_PERIM_IND, CONSOLIDATED_PERIM_IND,
		NSFR_INDIVIDUAL_LE_CD,NSFR_CONSOLIDATED_LE_CD,
      case 
        when TEMPLATE_ROW_CD in (&plus) then NSFR_AMT*&mult
        when TEMPLATE_ROW_CD in (&minus) then NSFR_AMT*(-1)*&mult
        else 0
      end NSFR_AMT, 
      NSFR_CLUSTER_CD,
		NSFR_SOURCE_CD, ASSET_CLASS, BALANCE_SHEET_ITEM_CD, BALANCE_SHEET_ITEM_DESC,
		BALANCE_SHEET_ITEM_SCHEMA_CD, BASEL3_COUNTERPARTY_TYPE_CD, CAP_ELIG_IND,
		CATEGORY, CATEGORY_B3, CATEGORY_QIS, CATEGORY_QIS_BBG, CATEGORY_QIS_DET,
		CATEGORY_QIS_EXP_TYPE, CAT_TYPE, CAT_DEPO_DIFF_OUTFLOWS_CD, COLLATERALIZED,
		COLLATERAL_CD, COLLATERAL_PLATFORM_CD, COLLATERAL_TYPE_CD, COUNTERPARTY_COUNTRY_CD,
		CPTY_CD, CPTY_DESC, CREDIT_STATUS_CD, CURRENCY_CD, DERIVATIVE_CONTRACT_TYPE_CD,
		ENCUMBERANCE_TYPE, FIRST_DAY_FINAL_5Y, GUARANTEE_MATURITY, GUARANTEE_TYPE_CD,
		INSTRUMENT_TYPE, INSURED_IND, INTRAGROUP_COUNTERPARTY_DESC, ISIN_NUM,
		LE_DESC, LIQUIDITY_CREDIT_QUALITY, MATURITY_DATE, MORTGAGE_TYPE,
		NETTING, NET_INSTR, OPERATIONAL, RATING, RELATIONSHIP, REPORTING_COUNTRY_CD,
		REP_BANK, RETAIL_IND, RWA_LOWER_35, SECURITIZATION_CODE, SECURITY_TYPE_CD,
		SUB_BOND, TRANSACTIONAL, EQUITY_INDEX, FINREP_COUNTERPARTY_TYPE_CD,
		AMOUNT, ASSET_AMT, CARRYING_VALUE_AMT, CV_AMOUNT_INS, CV_AMOUNT_NOT_INS,
		CV_AMOUNT_NOT_NTWK, CV_AMOUNT_NTWK, LIABILITY_AMT, NET_AMT, TOTAL_AMOUNT,
		HOLDING_PERCENTAGE, INSURED_PERC, MIN_INT_PERC, SUB_AMM_PERC,
		PRICE, ENC_0_6, ENC_0_6_RVRE, ENC_0_6_SCAD_0_6, ENC_0_6_SCAD_6_12,
		ENC_0_6_SCAD_OV, ENC_0_6_TIT, ENC_6_12, ENC_6_12_RVRE, ENC_6_12_SCAD_0_6,
		ENC_6_12_SCAD_6_12, ENC_6_12_SCAD_OV, ENC_6_12_TIT, ENC_OVER_12,
		ENC_OV_RVRE, ENC_OV_SCAD_0_6, ENC_OV_SCAD_6_12, ENC_OV_SCAD_OV,
		ENC_OV_TIT, GIVE_0_6, GIVE_6_12, GIVE_OVER_12, SCAD_0_6, SCAD_6_12,
		SCAD_OV, TOT_ENC, TOT_ENC_RVRE, TOT_ENC_TIT, TOT_REC, TOT_REC_OTH,
		TOT_REC_RVRE, UNENC_SCAD_0_6, UNENC_SCAD_6_12, UNENC_SCAD_OV

,SEC_CAT_B3_CAT_TYPE
,SEC_CAT_B3_EXP_TYPE
,SEC_CAT_DA_CAT_CD
,SEC_CAT_DA_CAT_TYPE
,TEMPLATE_ROW_CD TEMPLATE_ROW_CD_INPUT

      FROM P_KGU_GLM_UES.GLRT_WTE_NSFR
      WHERE TEMPLATE_ROW_CD in (&plus,&minus)

%mend new_row;


%macro select;
/************************************
			BEGIN USER CODE 
************************************/
execute (replace view &tdStagedb..NSFR_SHOULDER as
  select a1.*,a2.*
  from
  &tdStagedb..NSFR_TEMPLATE_ROW a1
  cross join
  (select distinct
    /*
    (case 
       when((reference_dt )= (select max(s.reference_dt) from P_KGU_GLM_UES.GLRT_WTE_US s) )then 'Current Date'
       else 'Other Dates'
     end 
    ) as "Current_Date" ,
    */ 
    REFERENCE_DT,
    FREQ_CD,
    INDIVIDUAL_PERIM_IND, 
    CONSOLIDATED_PERIM_IND,
    
    t2.ASSIGNED_COUNTRY,
    
    NSFR_INDIVIDUAL_LE_CD, 
    t2.BRANCH_DESC as  NSFR_INDIVIDUAL_LE_DESC,
    NSFR_CONSOLIDATED_LE_CD,
    t3.BRANCH_DESC as  NSFR_CONSOLIDATED_LE_DESC,
    /*      
    t2.LIQUIDITY_REFERENCE_BANK,
    */
    LEGAL_ENTITY_CD,
    CURRENCY_CD
    /*, 
    B3_COUNTERPARTY_TYPE_CD,
    t2.CATEGORY
    */ 
  FROM P_KGU_GLM_UES.GLRT_WTE_NSFR WTE left join P_DSASVA_STG18.US_TEC_COUNTRY_TABLE t2 
    on WTE.NSFR_INDIVIDUAL_LE_CD = t2.BRANCH_CD 
    left join P_DSASVA_STG18.US_TEC_COUNTRY_TABLE t3 
    on WTE.NSFR_CONSOLIDATED_LE_CD = t3.BRANCH_CD) a2
  ) by teradata;

execute(replace view &tdStagedb..NSFR_row_013 as
         (%new_row(%quote('013'),%quote('014','015','016'),%quote('Bazinga'),1)
         )
        ) by teradata;

execute(replace view &tdStagedb..NSFR_row_020 as
         (%new_row(%quote('020'),%quote('021','022','023'),%quote('Bazinga'),1)
         )
        ) by teradata;

execute(replace view &tdStagedb..NSFR_row_025 as
         (%new_row(%quote('025'),%quote('026','027','028'),%quote('Bazinga'),1)
         )
        ) by teradata;

execute(replace view &tdStagedb..NSFR_row_032 as
         (%new_row(%quote('032'),%quote('033','034','035'),%quote('Bazinga'),1)
         )
        ) by teradata;

execute(replace view &tdStagedb..NSFR_row_060 as
         (%new_row(%quote('060'),%quote('061','062','063'),%quote('Bazinga'),1)
         )
        ) by teradata;

execute(replace view &tdStagedb..NSFR_row_059 as
         (%new_row(%quote('059'),%quote('049','219'),%quote('054','213'),1)
         )
        ) by teradata;

execute(replace view &tdStagedb..NSFR_row_230 as
         (%new_row(%quote('230'),%quote('054','213'),%quote('049','219'),1)
         )
        ) by teradata;

execute(replace view &tdStagedb..NSFR_row_231 as
         (%new_row(%quote('231'),%quote('049'),%quote('Bazinga'),0.2)
         )
        ) by teradata;

execute(replace view &tdStagedb..NSFR_row_232 as
         (%new_row(%quote('232'),%quote('234','235','236'),%quote('Bazinga'),1)
         )
        ) by teradata;

execute(replace view &tdStagedb..NSFR_row_246 as
         (%new_row(%quote('246'),%quote('234','235','236','245'),%quote('237'),1)
         )
        ) by teradata;

execute(replace view &tdStagedb..NSFR_row_247 as
         (%new_row(%quote('247'),%quote('006'),%quote('247'),1)
         )
        ) by teradata;

execute(replace view &tdStagedb..NSFR_row_251 as
         (%new_row(%quote('251'),%quote('251','247'),%quote('006'),1)
         )
        ) by teradata;

execute(replace view &tdStagedb..&lasrtable. as
	(SELECT	INSERT_TS, cast(SK_ID_NOCFL as varchar(20)) SK_ID_NOCFL, b1.REFERENCE_DT, SCENARIO_CD, SUBSCENARIO_CD,
		b1.FREQ_CD, ORIGINAL_REFERENCE_DT, PROVIDER_CD, CFL_DATE_RICALCOLATA,
		TIME_BUCKET_CD, FLOW_TYPE_CD, GLRT_SOURCE_CD, TEMPLATE_CD, b1.TEMPLATE_ROW_CD , TEMPLATE_ROW_CD_INPUT, b1.PARAGRAF, b1.ITEM,


      b1.Row_type,b1.Row_type_DESC,b1.Check_type,b1.Flag_ASF,b1.Flag_RSF,b1.Factor_6,b1.Factor_7_12,b1.Factor_12,
      b1.Factor_6_Desc,b1.Factor_7_12_Desc,b1.Factor_12_Desc,
 
		b1.LEGAL_ENTITY_CD, INTRAGROUP_COUNTERPARTY_IND, INTRAGROUP_COUNTERPARTY_CD,
		FINREP_CPARTY_TYPE_CD, b1.INDIVIDUAL_PERIM_IND, b1.CONSOLIDATED_PERIM_IND,
		b1.NSFR_INDIVIDUAL_LE_CD,b1.NSFR_CONSOLIDATED_LE_CD, 
      b1.NSFR_INDIVIDUAL_LE_DESC,b1.NSFR_CONSOLIDATED_LE_DESC,
      NSFR_AMT, b1.NSFR_CLUSTER_CD,
		NSFR_SOURCE_CD, ASSET_CLASS, BALANCE_SHEET_ITEM_CD, BALANCE_SHEET_ITEM_DESC,
		BALANCE_SHEET_ITEM_SCHEMA_CD, BASEL3_COUNTERPARTY_TYPE_CD, CAP_ELIG_IND,
		CATEGORY, CATEGORY_B3, CATEGORY_QIS, CATEGORY_QIS_BBG, CATEGORY_QIS_DET,
		CATEGORY_QIS_EXP_TYPE, CAT_TYPE, CAT_DEPO_DIFF_OUTFLOWS_CD, COLLATERALIZED,
		COLLATERAL_CD, COLLATERAL_PLATFORM_CD, COLLATERAL_TYPE_CD, COUNTERPARTY_COUNTRY_CD,
		CPTY_CD, CPTY_DESC, CREDIT_STATUS_CD, b1.CURRENCY_CD, DERIVATIVE_CONTRACT_TYPE_CD,
		ENCUMBERANCE_TYPE, FIRST_DAY_FINAL_5Y, GUARANTEE_MATURITY, GUARANTEE_TYPE_CD,
		INSTRUMENT_TYPE, INSURED_IND, INTRAGROUP_COUNTERPARTY_DESC, ISIN_NUM,
		LE_DESC, LIQUIDITY_CREDIT_QUALITY, MATURITY_DATE, MORTGAGE_TYPE,
		NETTING, NET_INSTR, OPERATIONAL, RATING, RELATIONSHIP, REPORTING_COUNTRY_CD,
		REP_BANK, RETAIL_IND, RWA_LOWER_35, SECURITIZATION_CODE, SECURITY_TYPE_CD,
		SUB_BOND, TRANSACTIONAL, EQUITY_INDEX, FINREP_COUNTERPARTY_TYPE_CD,
		AMOUNT, ASSET_AMT, CARRYING_VALUE_AMT, CV_AMOUNT_INS, CV_AMOUNT_NOT_INS,
		CV_AMOUNT_NOT_NTWK, CV_AMOUNT_NTWK, LIABILITY_AMT, NET_AMT, TOTAL_AMOUNT,
		HOLDING_PERCENTAGE, INSURED_PERC, MIN_INT_PERC, SUB_AMM_PERC,
		PRICE, ENC_0_6, ENC_0_6_RVRE, ENC_0_6_SCAD_0_6, ENC_0_6_SCAD_6_12,
		ENC_0_6_SCAD_OV, ENC_0_6_TIT, ENC_6_12, ENC_6_12_RVRE, ENC_6_12_SCAD_0_6,
		ENC_6_12_SCAD_6_12, ENC_6_12_SCAD_OV, ENC_6_12_TIT, ENC_OVER_12,
		ENC_OV_RVRE, ENC_OV_SCAD_0_6, ENC_OV_SCAD_6_12, ENC_OV_SCAD_OV,
		ENC_OV_TIT, GIVE_0_6, GIVE_6_12, GIVE_OVER_12, SCAD_0_6, SCAD_6_12,
		SCAD_OV, TOT_ENC, TOT_ENC_RVRE, TOT_ENC_TIT, TOT_REC, TOT_REC_OTH,
		TOT_REC_RVRE, UNENC_SCAD_0_6, UNENC_SCAD_6_12, UNENC_SCAD_OV,ASSIGNED_COUNTRY,

      case 
        when b1.NSFR_CLUSTER_CD='1' then coalesce(NSFR_AMT,0)*Flag_ASF*Factor_6
        else null
      end weigth_ASF_6,
      case 
        when b1.NSFR_CLUSTER_CD='1' then coalesce(NSFR_AMT,0)*Flag_RSF*Factor_6
        else null
      end weigth_RSF_6,
      case 
        when b1.NSFR_CLUSTER_CD='1' and row_type in (3,5,6) then coalesce(NSFR_AMT,0)
        else null
      end NSFR_AMT_6,

      case 
        when b1.NSFR_CLUSTER_CD='2' then coalesce(NSFR_AMT,0)*Flag_ASF*Factor_7_12
        else null
      end  weigth_ASF_7_12,
      case 
        when b1.NSFR_CLUSTER_CD='2' then coalesce(NSFR_AMT,0)*Flag_RSF*Factor_7_12
        else null
      end  weigth_RSF_7_12,
      case 
        when b1.NSFR_CLUSTER_CD='2' and row_type in (3,6) then coalesce(NSFR_AMT,0)
        else null
      end  NSFR_AMT_7_12,

      case
        when b1.NSFR_CLUSTER_CD='3' then coalesce(NSFR_AMT,0)*Flag_ASF*Factor_12
        else null
      end weigth_ASF_12,
      case
        when b1.NSFR_CLUSTER_CD='3' then coalesce(NSFR_AMT,0)*Flag_RSF*Factor_12
        else null
      end weigth_RSF_12,
      Case 
        when b1.NSFR_CLUSTER_CD='3' and row_type in (3,4) then coalesce(NSFR_AMT,0)
        else null 
      end NSFR_AMT_12

,SEC_CAT_B3_CAT_TYPE
,SEC_CAT_B3_EXP_TYPE
,SEC_CAT_DA_CAT_CD
,SEC_CAT_DA_CAT_TYPE

FROM	&tdStagedb..NSFR_SHOULDER as b1  
        left join (Select * from  &tdStagedb..NSFR_row_013  
                   union all
                   Select * from  &tdStagedb..NSFR_row_020  
                   union all
                   Select * from  &tdStagedb..NSFR_row_025  
                   union all
                   Select * from  &tdStagedb..NSFR_row_032 
                   union all
                   Select * from  &tdStagedb..NSFR_row_060   
                   union all
                   Select * from  &tdStagedb..NSFR_row_059  
                   union all
                   Select * from  &tdStagedb..NSFR_row_230 
                   union all
                   Select * from  &tdStagedb..NSFR_row_231 
                   union all
                   Select * from  &tdStagedb..NSFR_row_232
                   union all
                   Select * from  &tdStagedb..NSFR_row_246 
                   union all
                   Select * from  &tdStagedb..NSFR_row_247
                   union all
                   Select * from  &tdStagedb..NSFR_row_251 
                   union all
                   select a1.*, cast('' as varchar(20)) as TEMPLATE_ROW_CD_INPUT 
                   FROM P_KGU_GLM_UES.GLRT_WTE_NSFR a1
                   WHERE TEMPLATE_ROW_CD not in ('247','251')
                   
                  ) b2
      on (
    coalesce(b1.TEMPLATE_ROW_CD,'(NOVALUE)')=coalesce(b2.TEMPLATE_ROW_CD,'(NOVALUE)') AND
    b1.REFERENCE_DT=b2.REFERENCE_DT AND
    coalesce(b1.FREQ_CD,'(NOVALUE)')=coalesce(b2.FREQ_CD,'(NOVALUE)') AND
    coalesce(b1.INDIVIDUAL_PERIM_IND,'(NOVALUE)')=coalesce(b2.INDIVIDUAL_PERIM_IND,'(NOVALUE)') AND
    coalesce(b1.CONSOLIDATED_PERIM_IND,'(NOVALUE)')=coalesce(b2.CONSOLIDATED_PERIM_IND,'(NOVALUE)') AND
    /*
    coalesce(b1.ASSIGNED_COUNTRY,'(NOVALUE)')=coalesce(b2.ASSIGNED_COUNTRY,'(NOVALUE)') AND
    */
    coalesce(b1.NSFR_INDIVIDUAL_LE_CD,'(NOVALUE)')=coalesce(b2.NSFR_INDIVIDUAL_LE_CD,'(NOVALUE)') AND
    coalesce(b1.NSFR_CONSOLIDATED_LE_CD,'(NOVALUE)')=coalesce(b2.NSFR_CONSOLIDATED_LE_CD,'(NOVALUE)') AND 
    /*
    coalesce(b1.LIQUIDITY_REFERENCE_BANK,'(NOVALUE)')=coalesce(b2.LIQUIDITY_REFERENCE_BANK,'(NOVALUE)') AND
    */
    coalesce(b1.LEGAL_ENTITY_CD,'(NOVALUE)')=coalesce(b2.LEGAL_ENTITY_CD,'(NOVALUE)') AND
    /*
    coalesce(b1.B3_COUNTERPARTY_TYPE_CD,'(NOVALUE)')=coalesce(b2.B3_COUNTERPARTY_TYPE_CD,'(NOVALUE)') AND
    coalesce(b1.CATEGORY,'(NOVALUE)')=coalesce(b2.CATEGORY,'(NOVALUE)') AND
    */
    coalesce(b1.NSFR_CLUSTER_CD,'(NOVALUE)')=coalesce(b2.NSFR_CLUSTER_CD,'(NOVALUE)') AND   
    coalesce(b1.CURRENCY_CD,'(NOVALUE)')=coalesce(b2.CURRENCY_CD,'(NOVALUE)')
   )
   )
) by teradata;


%mend;
/************************************
			END USER CODE 
************************************/


/************************************
			END USER CODE 
************************************/