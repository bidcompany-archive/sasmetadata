/************************************
			BEGIN USER CODE 
************************************/



execute(
   /*create table  P_DSASVA_STG18.US_Distinct_NOCFL_ARARA as
   (*/
    select distinct
       cast(SK_ID_NOCFL as varchar(20)) as SK_ID_NOCFL,
       TEMPLATE_ROW_CD
      from  P_DSASVA_STG18.US_TEMPLATE_DD
/*)*/  
) by teradata;

/*%put executed creation of US_Distinct_NOCFL_ARARA;

execute(replace view &tdStagedb..US_Distinct_NOCFL1_ARARA as
  (
   select distinct SK_ID_NOCFL,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='010'       then 1 else null end) as varchar(1))),'1','010')           as varchar(3)) as FLG_010
    from &tdStagedb..US_Distinct_NOCFL_ARARA
    group by SK_ID_NOCFL
  )
) by teradata;

%put executed creation of US_Distinct_NOCFL1_ARARA;*/





/************************************
			END USER CODE 
************************************/
