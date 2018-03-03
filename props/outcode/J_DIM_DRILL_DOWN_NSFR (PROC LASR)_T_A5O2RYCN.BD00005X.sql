/***************************************
	Query Passthrough su TD
	Esempio USER CODE:
	execute (
				create view &tdStagedb..&lasrTable. as
				select geography_dim.*
				from &&tdStagedb..geography_dim geography_dim
			
		) by teradata;
***************************************/

%Macro create;
  %if %sysfunc(exist(&stgLib..&lasrTable)) %then %do;
    proc delete data=&stgLib..&lasrTable;run;
  %end;
  
  data &stgLib..&lasrTable;
    length variable $30;
    variable='LEGAL_ENTITY_CD               ';output;
    variable='REFERENCE_DT                  ';output;
    variable='FREQ_CD                       ';output;
    variable='CURRENCY_CD                   ';output;
    variable='INTRAGROUP_COUNTERPARTY_CD    ';output;
    variable='INTRAGROUP_COUNTERPARTY_IND   ';output;
    variable='CREDIT_STATUS_CD              ';output;
    variable='BALANCE_SHEET_ITEM_CD         ';output;
    variable='BALANCE_SHEET_ITEM_DESC       ';output;
    variable='BASEL3_COUNTERPARTY_TYPE_CD   ';output;
    variable='COUNTERPARTY_COUNTRY_CD       ';output;
    variable='REPORTING_COUNTRY             ';output;
    variable='FINREP_CPARTY_TYPE_CD         ';output;
    variable='GUARANTEE_TYPE_CD             ';output;
    variable='ISIN_NUM                      ';output;
    variable='RETAIL_IND                    ';output;
    variable='SECURITY_TYPE_CD              ';output;
    variable='COLLATERAL_CD                 ';output;
    variable='DERIVATIVE_CONTRACT_TYPE_CD   ';output;
    variable='INSTRUMENT_TYPE               ';output;
    variable='MATURITY_DATE                 ';output;
    variable='ENCUMBERANCE_TYPE             ';output;
    variable='ASSIGNED_COUNTRY              ';output;
    variable='GLRT_SOURCE_CD                ';output;
    variable='ASSET_CLASS                   ';output;
    variable='CATEGORY                      ';output;
    variable='DA_CAT_CD                     ';output;
    variable='DA_CAT_TYPE                   ';output;
    variable='SEC_CAT_B3_CAT_TYPE           ';output;
    variable='SEC_CAT_B3_EXP_TYPE           ';output;
    variable='SEC_CAT_DA_CAT_CD             ';output;
    variable='SEC_CAT_DA_CAT_TYPE           ';output;
    variable='NA                            ';output;
  run;
%mend Create;
%create;


%macro select;
/************************************
			BEGIN USER CODE 
************************************/



/************************************
			END USER CODE 
************************************/
%mend;