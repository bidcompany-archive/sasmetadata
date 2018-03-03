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
variable='SK_ID_NOCFL                   ';output;
variable='SCENARIO_CD                   ';output;
variable='SUBSCENARIO_CD                ';output;
variable='ORIGINAL_REFERENCE_DT         ';output;
variable='PROVIDER_CD                   ';output;
variable='GLRT_SOURCE_CD                ';output;
variable='FLOW_TYPE_CD                  ';output;
variable='INTRAGROUP_COUNTERPARTY_IND   ';output;
variable='INTRAGROUP_COUNTERPARTY_CD    ';output;
variable='FINREP_CPARTY_TYPE_CD         ';output;
variable='COUNTRY_CD                    ';output;
variable='COUNTRY_DESC                  ';output;
variable='ISIN_NUM                      ';output;
variable='BALANCE_SHEET_ITEM_CD         ';output;
variable='REPORTING_CURRENCY_CD         ';output;
variable='CURRENCY_CD                   ';output;
variable='ASSET_CLASS_CD                ';output;
variable='COLLATERAL_TYPE_CD            ';output;
variable='GUARANTEE_TYPE_CD             ';output;
variable='WTE_INSTRUMENT_TYPE_CD        ';output;
variable='ORIGINAL_MATURITY_CD          ';output;
variable='OPERATION_TYPE_CD             ';output;
variable='WTE_SIGN                      ';output;
variable='RETAIL_IND                    ';output;
variable='SECURITY_TYPE_CD              ';output;
variable='SPEC_CREDIT_TYPE_CD           ';output;
variable='INTRAGROUP_COUNTRY_CD         ';output;
variable='INTRAGROUP_COUNTRY_DESC       ';output;
variable='BRANCH_DESC                   ';output;
variable='STRUCTURED_IND                ';output;
variable='SUBORDINATED_IND              ';output;
variable='MORTGAGE_TYPE_CD              ';output;
variable='SPEC_DEBT_TYPE_CD             ';output;
variable='CREDIT_STATUS_CD              ';output;
variable='ASSIGNED_COUNTRY              ';output;
variable='CATEGORY                      ';output;
variable='B3_COUNTERPARTY_TYPE_CD       ';output;
variable='DIM_FB_LE_ISO_COUNTRY_CD      ';output;
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