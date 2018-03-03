/************************************
			BEGIN USER CODE 
************************************/

%macro select;

execute(
   replace view &tdStagedb..US_Distinct_NOCFL as
   (
    select distinct
       cast(SK_ID_NOCFL as varchar(20)) as SK_ID_NOCFL,
       TEMPLATE_ROW_CD
      from &tdStagedb..US_TEMPLATE_DD
)  
) by teradata;

%put executed creation of US_Distinct_NOCFL;

execute(replace view &tdStagedb..US_Distinct_NOCFL1 as
  (
   select distinct SK_ID_NOCFL,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='010'       then 1 else null end) as varchar(1))),'1','010')           as varchar(3)) as FLG_010,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='010a'      then 1 else null end) as varchar(1))),'1','010a')          as varchar(4)) as FLG_010a,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='010b'      then 1 else null end) as varchar(1))),'1','010b')          as varchar(4)) as FLG_010b,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='010b.1'    then 1 else null end) as varchar(1))),'1','010b.1')        as varchar(6)) as FLG_010b1,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='010b.2'    then 1 else null end) as varchar(1))),'1','010b.2')        as varchar(6)) as FLG_010b2,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='010b.3'    then 1 else null end) as varchar(1))),'1','010b.3')        as varchar(6)) as FLG_010b3,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='010c.1'    then 1 else null end) as varchar(1))),'1','010c.1')        as varchar(6)) as FLG_010c1,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='010c.2'    then 1 else null end) as varchar(1))),'1','010c.2')        as varchar(6)) as FLG_010c2,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='010c.3'    then 1 else null end) as varchar(1))),'1','010c.3')        as varchar(6)) as FLG_010c3,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='010c.4'    then 1 else null end) as varchar(1))),'1','010c.4')        as varchar(6)) as FLG_010c4,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='010c.5'    then 1 else null end) as varchar(1))),'1','010c.5')        as varchar(6)) as FLG_010c5,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='010c.6'    then 1 else null end) as varchar(1))),'1','010c.6')        as varchar(6)) as FLG_010c6,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='020'       then 1 else null end) as varchar(1))),'1','020')           as varchar(3)) as FLG_020,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='020a.1'    then 1 else null end) as varchar(1))),'1','020a.1')        as varchar(6)) as FLG_020a1,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='020a.2'    then 1 else null end) as varchar(1))),'1','020a.2')        as varchar(6)) as FLG_020a2,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='020a.3'    then 1 else null end) as varchar(1))),'1','020a.3')        as varchar(6)) as FLG_020a3,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='020a.4'    then 1 else null end) as varchar(1))),'1','020a.4')        as varchar(6)) as FLG_020a4,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='020a.5'    then 1 else null end) as varchar(1))),'1','020a.5')        as varchar(6)) as FLG_020a5,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='020a.6'    then 1 else null end) as varchar(1))),'1','020a.6')        as varchar(6)) as FLG_020a6,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='030'       then 1 else null end) as varchar(1))),'1','030')           as varchar(3)) as FLG_030,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='040'       then 1 else null end) as varchar(1))),'1','040')           as varchar(3)) as FLG_040,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='040a.1'    then 1 else null end) as varchar(1))),'1','040a.1')        as varchar(6)) as FLG_040a1,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='040a.2'    then 1 else null end) as varchar(1))),'1','040a.2')        as varchar(6)) as FLG_040a2,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='040a.3'    then 1 else null end) as varchar(1))),'1','040a.3')        as varchar(6)) as FLG_040a3,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='040a.4'    then 1 else null end) as varchar(1))),'1','040a.4')        as varchar(6)) as FLG_040a4,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='050'       then 1 else null end) as varchar(1))),'1','050')           as varchar(3)) as FLG_050,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='060'       then 1 else null end) as varchar(1))),'1','060')           as varchar(3)) as FLG_060,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='060a'      then 1 else null end) as varchar(1))),'1','060a')          as varchar(4)) as FLG_060a,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='060b'      then 1 else null end) as varchar(1))),'1','060b')          as varchar(4)) as FLG_060b,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='070'       then 1 else null end) as varchar(1))),'1','070')           as varchar(3)) as FLG_070,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='070a'      then 1 else null end) as varchar(1))),'1','070a')          as varchar(4)) as FLG_070a,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='070b'      then 1 else null end) as varchar(1))),'1','070b')          as varchar(4)) as FLG_070b,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='070c'      then 1 else null end) as varchar(1))),'1','070c')          as varchar(4)) as FLG_070c,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='070d'      then 1 else null end) as varchar(1))),'1','070d')          as varchar(4)) as FLG_070d,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='080'       then 1 else null end) as varchar(1))),'1','080')           as varchar(3)) as FLG_080,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='080a'      then 1 else null end) as varchar(1))),'1','080a')          as varchar(4)) as FLG_080a,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='080a.1'    then 1 else null end) as varchar(1))),'1','080a.1')        as varchar(6)) as FLG_080a1,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='080a.2'    then 1 else null end) as varchar(1))),'1','080a.2')        as varchar(6)) as FLG_080a2,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='080b'      then 1 else null end) as varchar(1))),'1','080b')          as varchar(4)) as FLG_080b,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='080b.1'    then 1 else null end) as varchar(1))),'1','080b.1')        as varchar(6)) as FLG_080b1,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='080b.2'    then 1 else null end) as varchar(1))),'1','080b.2')        as varchar(6)) as FLG_080b2,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='090'       then 1 else null end) as varchar(1))),'1','090')           as varchar(3)) as FLG_090,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='100'       then 1 else null end) as varchar(1))),'1','100')           as varchar(3)) as FLG_100,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='110'       then 1 else null end) as varchar(1))),'1','110')           as varchar(3)) as FLG_110,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='120'       then 1 else null end) as varchar(1))),'1','120')           as varchar(3)) as FLG_120,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='120a.1'    then 1 else null end) as varchar(1))),'1','120a.1')        as varchar(6)) as FLG_120a1,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='120a.2'    then 1 else null end) as varchar(1))),'1','120a.2')        as varchar(6)) as FLG_120a2,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='120a.3'    then 1 else null end) as varchar(1))),'1','120a.3')        as varchar(6)) as FLG_120a3,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='120a.4'    then 1 else null end) as varchar(1))),'1','120a.4')        as varchar(6)) as FLG_120a4,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='130'       then 1 else null end) as varchar(1))),'1','130')           as varchar(3)) as FLG_130,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='130a'      then 1 else null end) as varchar(1))),'1','130a')          as varchar(4)) as FLG_130a,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='130a.1'    then 1 else null end) as varchar(1))),'1','130a.1')        as varchar(6)) as FLG_130a1,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='130a.2'    then 1 else null end) as varchar(1))),'1','130a.2')        as varchar(6)) as FLG_130a2,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='130a.3'    then 1 else null end) as varchar(1))),'1','130a.3')        as varchar(6)) as FLG_130a3,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='130b'      then 1 else null end) as varchar(1))),'1','130b')          as varchar(4)) as FLG_130b,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='130b.1'    then 1 else null end) as varchar(1))),'1','130b.1')        as varchar(6)) as FLG_130b1,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='130b.2'    then 1 else null end) as varchar(1))),'1','130b.2')        as varchar(6)) as FLG_130b2,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='130b.3'    then 1 else null end) as varchar(1))),'1','130b.3')        as varchar(6)) as FLG_130b3,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='140'       then 1 else null end) as varchar(1))),'1','140')           as varchar(3)) as FLG_140,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='140a.1'    then 1 else null end) as varchar(1))),'1','140a.1')        as varchar(6)) as FLG_140a1,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='140a.2'    then 1 else null end) as varchar(1))),'1','140a.2')        as varchar(6)) as FLG_140a2,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='140a.3'    then 1 else null end) as varchar(1))),'1','140a.3')        as varchar(6)) as FLG_140a3,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='150'       then 1 else null end) as varchar(1))),'1','150')           as varchar(3)) as FLG_150,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='150a.1'    then 1 else null end) as varchar(1))),'1','150a.1')        as varchar(6)) as FLG_150a1,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='150a.2'    then 1 else null end) as varchar(1))),'1','150a.2')        as varchar(6)) as FLG_150a2,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='150a.3'    then 1 else null end) as varchar(1))),'1','150a.3')        as varchar(6)) as FLG_150a3,
         cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='160'      then 1 else null end) as varchar(1))),'1','160')           as varchar(3)) as FLG_160,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='160a'      then 1 else null end) as varchar(1))),'1','160a')          as varchar(4)) as FLG_160a,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='160a.1'    then 1 else null end) as varchar(1))),'1','160a.1')        as varchar(6)) as FLG_160a1, 
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='160a.2'    then 1 else null end) as varchar(1))),'1','160a.2')        as varchar(6)) as FLG_160a2,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='160a.3'    then 1 else null end) as varchar(1))),'1','160a.3')        as varchar(6)) as FLG_160a3,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='160b'      then 1 else null end) as varchar(1))),'1','160b')          as varchar(4)) as FLG_160b,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='160b.1'    then 1 else null end) as varchar(1))),'1','160b.1')        as varchar(6)) as FLG_160b1,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='160b.2'    then 1 else null end) as varchar(1))),'1','160b.2')        as varchar(6)) as FLG_160b2,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='160b.3'    then 1 else null end) as varchar(1))),'1','160b.3')        as varchar(6)) as FLG_160b3,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='170'       then 1 else null end) as varchar(1))),'1','170')           as varchar(3)) as FLG_170,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='180'       then 1 else null end) as varchar(1))),'1','180')           as varchar(3)) as FLG_180,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='180a'      then 1 else null end) as varchar(1))),'1','180a')          as varchar(4)) as FLG_180a,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='180b'      then 1 else null end) as varchar(1))),'1','180b')          as varchar(4)) as FLG_180b,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='190'       then 1 else null end) as varchar(1))),'1','190')           as varchar(3)) as FLG_190,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='200'       then 1 else null end) as varchar(1))),'1','200')           as varchar(3)) as FLG_200,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='200a.1'    then 1 else null end) as varchar(1))),'1','200a.1')        as varchar(6)) as FLG_200a1,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='200a.2'    then 1 else null end) as varchar(1))),'1','200a.2')        as varchar(6)) as FLG_200a2,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='210'       then 1 else null end) as varchar(1))),'1','210')           as varchar(3)) as FLG_210,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='220'       then 1 else null end) as varchar(1))),'1','220')           as varchar(3)) as FLG_220,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='220a.1'    then 1 else null end) as varchar(1))),'1','220a.1')        as varchar(6)) as FLG_220a1,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='220a.2'    then 1 else null end) as varchar(1))),'1','220a.2')        as varchar(6)) as FLG_220a2,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='230'       then 1 else null end) as varchar(1))),'1','230')           as varchar(3)) as FLG_230,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='240'       then 1 else null end) as varchar(1))),'1','240')           as varchar(3)) as FLG_240,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='240a.1'    then 1 else null end) as varchar(1))),'1','240a.1')        as varchar(6)) as FLG_240a1,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='240a.2'    then 1 else null end) as varchar(1))),'1','240a.2')        as varchar(6)) as FLG_240a2,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='250'       then 1 else null end) as varchar(1))),'1','250')           as varchar(3)) as FLG_250,    
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='260'       then 1 else null end) as varchar(1))),'1','260')           as varchar(3)) as FLG_260,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='260a.1'    then 1 else null end) as varchar(1))),'1','260a.1')        as varchar(6)) as FLG_260a1,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='260a.2'    then 1 else null end) as varchar(1))),'1','260a.2')        as varchar(6)) as FLG_260a2,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='270'       then 1 else null end) as varchar(1))),'1','270')           as varchar(3)) as FLG_270,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='270a.1'    then 1 else null end) as varchar(1))),'1','270a.1')        as varchar(6)) as FLG_270a1,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='270a.2'    then 1 else null end) as varchar(1))),'1','270a.2')        as varchar(6)) as FLG_270a2,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='280'       then 1 else null end) as varchar(1))),'1','280')           as varchar(3)) as FLG_280,    
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='290'       then 1 else null end) as varchar(1))),'1','290')           as varchar(3)) as FLG_290,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='290a.1'    then 1 else null end) as varchar(1))),'1','290a.1')        as varchar(6)) as FLG_290a1,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='290a.2'    then 1 else null end) as varchar(1))),'1','290a.2')        as varchar(6)) as FLG_290a2,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='300'       then 1 else null end) as varchar(1))),'1','300')           as varchar(3)) as FLG_300,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='310'       then 1 else null end) as varchar(1))),'1','310')           as varchar(3)) as FLG_310,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='320'       then 1 else null end) as varchar(1))),'1','320')           as varchar(3)) as FLG_320,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='330'       then 1 else null end) as varchar(1))),'1','330')           as varchar(3)) as FLG_330,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='340'       then 1 else null end) as varchar(1))),'1','340')           as varchar(3)) as FLG_340,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='350'       then 1 else null end) as varchar(1))),'1','350')           as varchar(3)) as FLG_350,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='350a'      then 1 else null end) as varchar(1))),'1','350a')          as varchar(4)) as FLG_350a,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='350b'      then 1 else null end) as varchar(1))),'1','350b')       as varchar(4)) as FLG_350b,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='350c'      then 1 else null end) as varchar(1))),'1','350c')       as varchar(4)) as FLG_350c,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='360'       then 1 else null end) as varchar(1))),'1','360')           as varchar(3)) as FLG_360,        
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='370'       then 1 else null end) as varchar(1))),'1','370')           as varchar(3)) as FLG_370,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='380'       then 1 else null end) as varchar(1))),'1','380')           as varchar(3)) as FLG_380    
    from &tdStagedb..US_Distinct_NOCFL
    group by SK_ID_NOCFL
  )
) by teradata;

%put executed creation of US_Distinct_NOCFL1;

/* da levare il commento a partire dal 23 Marzo*/

execute(
   replace view &tdStagedb..NSFR_Distinct_NOCFL as  
   (
    select distinct
       cast(SK_ID_NOCFL as varchar(20)) as SK_ID_NOCFL,
       TEMPLATE_ROW_CD
      from &tdStagedb..NSFR
)  
) by teradata;


execute(replace view &tdStagedb..NSFR_Distinct_NOCFL1 as
  (
   select distinct SK_ID_NOCFL,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='006'    then 1 else null end) as varchar(1))),'1','006')       as varchar(3)) as FLG_006,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='007'    then 1 else null end) as varchar(1))),'1','007')       as varchar(3)) as FLG_007,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='008'    then 1 else null end) as varchar(1))),'1','008')       as varchar(3)) as FLG_008,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='009'    then 1 else null end) as varchar(1))),'1','009')       as varchar(3)) as FLG_009,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='010'    then 1 else null end) as varchar(1))),'1','010')       as varchar(3)) as FLG_010,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='011'    then 1 else null end) as varchar(1))),'1','011')       as varchar(3)) as FLG_011,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='012'    then 1 else null end) as varchar(1))),'1','012')       as varchar(3)) as FLG_012,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='013'    then 1 else null end) as varchar(1))),'1','013')       as varchar(3)) as FLG_013,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='014'    then 1 else null end) as varchar(1))),'1','014')       as varchar(3)) as FLG_014,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='015'    then 1 else null end) as varchar(1))),'1','015')       as varchar(3)) as FLG_015,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='016'    then 1 else null end) as varchar(1))),'1','016')       as varchar(3)) as FLG_016,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='017'    then 1 else null end) as varchar(1))),'1','017')       as varchar(3)) as FLG_017,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='018'    then 1 else null end) as varchar(1))),'1','018')       as varchar(3)) as FLG_018,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='019'    then 1 else null end) as varchar(1))),'1','019')       as varchar(3)) as FLG_019,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='020'    then 1 else null end) as varchar(1))),'1','020')       as varchar(3)) as FLG_020,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='021'    then 1 else null end) as varchar(1))),'1','021')       as varchar(3)) as FLG_021,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='022'    then 1 else null end) as varchar(1))),'1','022')       as varchar(3)) as FLG_022,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='023'    then 1 else null end) as varchar(1))),'1','023')       as varchar(3)) as FLG_023,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='024'    then 1 else null end) as varchar(1))),'1','024')       as varchar(3)) as FLG_024,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='025'    then 1 else null end) as varchar(1))),'1','025')       as varchar(3)) as FLG_025,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='026'    then 1 else null end) as varchar(1))),'1','025')       as varchar(3)) as FLG_026,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='027'    then 1 else null end) as varchar(1))),'1','027')       as varchar(3)) as FLG_027,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='028'    then 1 else null end) as varchar(1))),'1','028')       as varchar(3)) as FLG_028,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='029'    then 1 else null end) as varchar(1))),'1','029')       as varchar(3)) as FLG_029,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='030'    then 1 else null end) as varchar(1))),'1','030')       as varchar(3)) as FLG_030,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='031'    then 1 else null end) as varchar(1))),'1','031')       as varchar(3)) as FLG_031,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='032'    then 1 else null end) as varchar(1))),'1','032')       as varchar(3)) as FLG_032,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='033'    then 1 else null end) as varchar(1))),'1','033')       as varchar(3)) as FLG_033,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='034'    then 1 else null end) as varchar(1))),'1','034')       as varchar(3)) as FLG_034,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='035'    then 1 else null end) as varchar(1))),'1','035')       as varchar(3)) as FLG_035,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='036'    then 1 else null end) as varchar(1))),'1','036')       as varchar(3)) as FLG_036,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='037'    then 1 else null end) as varchar(1))),'1','037')       as varchar(3)) as FLG_037,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='038'    then 1 else null end) as varchar(1))),'1','038')       as varchar(3)) as FLG_038,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='039'    then 1 else null end) as varchar(1))),'1','039')       as varchar(3)) as FLG_039,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='040'    then 1 else null end) as varchar(1))),'1','040')       as varchar(3)) as FLG_040,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='041'    then 1 else null end) as varchar(1))),'1','041')       as varchar(3)) as FLG_041,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='042'    then 1 else null end) as varchar(1))),'1','042')       as varchar(3)) as FLG_042,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='043'    then 1 else null end) as varchar(1))),'1','043')       as varchar(3)) as FLG_043,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='044'    then 1 else null end) as varchar(1))),'1','044')       as varchar(3)) as FLG_044,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='045'    then 1 else null end) as varchar(1))),'1','045')       as varchar(3)) as FLG_045,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='046'    then 1 else null end) as varchar(1))),'1','046')       as varchar(3)) as FLG_046,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='047'    then 1 else null end) as varchar(1))),'1','047')       as varchar(3)) as FLG_047,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='048'    then 1 else null end) as varchar(1))),'1','048')       as varchar(3)) as FLG_048,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='049'    then 1 else null end) as varchar(1))),'1','049')       as varchar(3)) as FLG_049,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='050'    then 1 else null end) as varchar(1))),'1','050')       as varchar(3)) as FLG_050,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='051'    then 1 else null end) as varchar(1))),'1','051')       as varchar(3)) as FLG_051,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='052'    then 1 else null end) as varchar(1))),'1','052')       as varchar(3)) as FLG_052,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='053'    then 1 else null end) as varchar(1))),'1','053')       as varchar(3)) as FLG_053,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='054'    then 1 else null end) as varchar(1))),'1','054')       as varchar(3)) as FLG_054,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='055'    then 1 else null end) as varchar(1))),'1','055')       as varchar(3)) as FLG_055,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='056'    then 1 else null end) as varchar(1))),'1','056')       as varchar(3)) as FLG_056,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='057'    then 1 else null end) as varchar(1))),'1','057')       as varchar(3)) as FLG_057,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='058'    then 1 else null end) as varchar(1))),'1','058')       as varchar(3)) as FLG_058,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='059'    then 1 else null end) as varchar(1))),'1','059')       as varchar(3)) as FLG_059,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='060'    then 1 else null end) as varchar(1))),'1','060')       as varchar(3)) as FLG_060,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='061'    then 1 else null end) as varchar(1))),'1','061')       as varchar(3)) as FLG_061,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='062'    then 1 else null end) as varchar(1))),'1','062')       as varchar(3)) as FLG_062,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='063'    then 1 else null end) as varchar(1))),'1','063')       as varchar(3)) as FLG_063,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='064'    then 1 else null end) as varchar(1))),'1','064')       as varchar(3)) as FLG_064,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='065'    then 1 else null end) as varchar(1))),'1','065')       as varchar(3)) as FLG_065,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='066'    then 1 else null end) as varchar(1))),'1','066')       as varchar(3)) as FLG_066,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='067'    then 1 else null end) as varchar(1))),'1','067')       as varchar(3)) as FLG_067,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='068'    then 1 else null end) as varchar(1))),'1','068')       as varchar(3)) as FLG_068,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='069'    then 1 else null end) as varchar(1))),'1','069')       as varchar(3)) as FLG_069,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='070'    then 1 else null end) as varchar(1))),'1','070')       as varchar(3)) as FLG_070,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='071'    then 1 else null end) as varchar(1))),'1','071')       as varchar(3)) as FLG_071,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='072'    then 1 else null end) as varchar(1))),'1','072')       as varchar(3)) as FLG_072,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='073'    then 1 else null end) as varchar(1))),'1','073')       as varchar(3)) as FLG_073,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='074'    then 1 else null end) as varchar(1))),'1','074')       as varchar(3)) as FLG_074,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='075'    then 1 else null end) as varchar(1))),'1','075')       as varchar(3)) as FLG_075,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='076'    then 1 else null end) as varchar(1))),'1','076')       as varchar(3)) as FLG_076,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='084'    then 1 else null end) as varchar(1))),'1','084')       as varchar(3)) as FLG_084,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='085'    then 1 else null end) as varchar(1))),'1','085')       as varchar(3)) as FLG_085,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='086'    then 1 else null end) as varchar(1))),'1','086')       as varchar(3)) as FLG_086,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='087'    then 1 else null end) as varchar(1))),'1','087')       as varchar(3)) as FLG_087,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='088'    then 1 else null end) as varchar(1))),'1','088')       as varchar(3)) as FLG_088,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='089'    then 1 else null end) as varchar(1))),'1','089')       as varchar(3)) as FLG_089,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='090'    then 1 else null end) as varchar(1))),'1','090')       as varchar(3)) as FLG_090,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='091'    then 1 else null end) as varchar(1))),'1','091')       as varchar(3)) as FLG_091,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='092'    then 1 else null end) as varchar(1))),'1','092')       as varchar(3)) as FLG_092,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='093'    then 1 else null end) as varchar(1))),'1','093')       as varchar(3)) as FLG_093,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='094'    then 1 else null end) as varchar(1))),'1','094')       as varchar(3)) as FLG_094,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='095'    then 1 else null end) as varchar(1))),'1','095')       as varchar(3)) as FLG_095,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='096'    then 1 else null end) as varchar(1))),'1','096')       as varchar(3)) as FLG_096,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='097'    then 1 else null end) as varchar(1))),'1','097')       as varchar(3)) as FLG_097,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='098'    then 1 else null end) as varchar(1))),'1','098')       as varchar(3)) as FLG_098,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='099'    then 1 else null end) as varchar(1))),'1','099')       as varchar(3)) as FLG_099,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='100'    then 1 else null end) as varchar(1))),'1','100')       as varchar(3)) as FLG_100,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='101'    then 1 else null end) as varchar(1))),'1','101')       as varchar(3)) as FLG_101,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='102'    then 1 else null end) as varchar(1))),'1','102')       as varchar(3)) as FLG_102,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='103'    then 1 else null end) as varchar(1))),'1','103')       as varchar(3)) as FLG_103,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='104'    then 1 else null end) as varchar(1))),'1','104')       as varchar(3)) as FLG_104,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='105'    then 1 else null end) as varchar(1))),'1','105')       as varchar(3)) as FLG_105,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='106'    then 1 else null end) as varchar(1))),'1','106')       as varchar(3)) as FLG_106,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='107'    then 1 else null end) as varchar(1))),'1','107')       as varchar(3)) as FLG_107,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='108'    then 1 else null end) as varchar(1))),'1','108')       as varchar(3)) as FLG_108,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='109'    then 1 else null end) as varchar(1))),'1','109')       as varchar(3)) as FLG_109,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='110'    then 1 else null end) as varchar(1))),'1','110')       as varchar(3)) as FLG_110,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='111'    then 1 else null end) as varchar(1))),'1','111')       as varchar(3)) as FLG_111,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='112'    then 1 else null end) as varchar(1))),'1','112')       as varchar(3)) as FLG_112,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='113'    then 1 else null end) as varchar(1))),'1','113')       as varchar(3)) as FLG_113,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='114'    then 1 else null end) as varchar(1))),'1','114')       as varchar(3)) as FLG_114,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='115'    then 1 else null end) as varchar(1))),'1','115')       as varchar(3)) as FLG_115,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='116'    then 1 else null end) as varchar(1))),'1','116')       as varchar(3)) as FLG_116,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='117'    then 1 else null end) as varchar(1))),'1','117')       as varchar(3)) as FLG_117,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='118'    then 1 else null end) as varchar(1))),'1','118')       as varchar(3)) as FLG_118,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='119'    then 1 else null end) as varchar(1))),'1','119')       as varchar(3)) as FLG_119,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='120'    then 1 else null end) as varchar(1))),'1','120')       as varchar(3)) as FLG_120,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='121'    then 1 else null end) as varchar(1))),'1','121')       as varchar(3)) as FLG_121,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='122'    then 1 else null end) as varchar(1))),'1','122')       as varchar(3)) as FLG_122,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='123'    then 1 else null end) as varchar(1))),'1','123')       as varchar(3)) as FLG_123,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='124'    then 1 else null end) as varchar(1))),'1','124')       as varchar(3)) as FLG_124,  
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='125'    then 1 else null end) as varchar(1))),'1','125')       as varchar(3)) as FLG_125,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='126'    then 1 else null end) as varchar(1))),'1','126')       as varchar(3)) as FLG_126,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='128'    then 1 else null end) as varchar(1))),'1','128')       as varchar(3)) as FLG_128,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='130'    then 1 else null end) as varchar(1))),'1','130')       as varchar(3)) as FLG_130,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='131'    then 1 else null end) as varchar(1))),'1','131')       as varchar(3)) as FLG_131,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='132'    then 1 else null end) as varchar(1))),'1','132')       as varchar(3)) as FLG_132,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='134'    then 1 else null end) as varchar(1))),'1','134')       as varchar(3)) as FLG_134,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='136'    then 1 else null end) as varchar(1))),'1','136')       as varchar(3)) as FLG_136,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='137'    then 1 else null end) as varchar(1))),'1','137')       as varchar(3)) as FLG_137,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='138'    then 1 else null end) as varchar(1))),'1','138')       as varchar(3)) as FLG_138,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='140'    then 1 else null end) as varchar(1))),'1','140')       as varchar(3)) as FLG_140,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='142'    then 1 else null end) as varchar(1))),'1','142')       as varchar(3)) as FLG_142,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='143'    then 1 else null end) as varchar(1))),'1','143')       as varchar(3)) as FLG_143,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='144'    then 1 else null end) as varchar(1))),'1','144')       as varchar(3)) as FLG_144,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='146'    then 1 else null end) as varchar(1))),'1','146')       as varchar(3)) as FLG_146,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='147'    then 1 else null end) as varchar(1))),'1','147')       as varchar(3)) as FLG_147,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='149'    then 1 else null end) as varchar(1))),'1','149')       as varchar(3)) as FLG_149,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='150'    then 1 else null end) as varchar(1))),'1','150')       as varchar(3)) as FLG_150,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='151'    then 1 else null end) as varchar(1))),'1','151')       as varchar(3)) as FLG_151,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='152'    then 1 else null end) as varchar(1))),'1','152')       as varchar(3)) as FLG_152,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='154'    then 1 else null end) as varchar(1))),'1','154')       as varchar(3)) as FLG_154,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='155'    then 1 else null end) as varchar(1))),'1','155')       as varchar(3)) as FLG_155,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='156'    then 1 else null end) as varchar(1))),'1','156')       as varchar(3)) as FLG_156,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='158'    then 1 else null end) as varchar(1))),'1','158')       as varchar(3)) as FLG_158,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='160'    then 1 else null end) as varchar(1))),'1','160')       as varchar(3)) as FLG_160,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='161'    then 1 else null end) as varchar(1))),'1','161')       as varchar(3)) as FLG_161,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='162'    then 1 else null end) as varchar(1))),'1','162')       as varchar(3)) as FLG_162,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='164'    then 1 else null end) as varchar(1))),'1','164')       as varchar(3)) as FLG_164,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='166'    then 1 else null end) as varchar(1))),'1','166')       as varchar(3)) as FLG_166,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='167'    then 1 else null end) as varchar(1))),'1','167')       as varchar(3)) as FLG_167,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='168'    then 1 else null end) as varchar(1))),'1','168')       as varchar(3)) as FLG_168,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='170'    then 1 else null end) as varchar(1))),'1','170')       as varchar(3)) as FLG_170,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='172'    then 1 else null end) as varchar(1))),'1','172')       as varchar(3)) as FLG_172,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='173'    then 1 else null end) as varchar(1))),'1','173')       as varchar(3)) as FLG_173,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='174'    then 1 else null end) as varchar(1))),'1','174')       as varchar(3)) as FLG_174,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='176'    then 1 else null end) as varchar(1))),'1','176')       as varchar(3)) as FLG_176,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='178'    then 1 else null end) as varchar(1))),'1','178')       as varchar(3)) as FLG_178,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='179'    then 1 else null end) as varchar(1))),'1','179')       as varchar(3)) as FLG_179,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='180'    then 1 else null end) as varchar(1))),'1','180')       as varchar(3)) as FLG_180,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='182'    then 1 else null end) as varchar(1))),'1','182')       as varchar(3)) as FLG_182,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='184'    then 1 else null end) as varchar(1))),'1','184')       as varchar(3)) as FLG_184,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='185'    then 1 else null end) as varchar(1))),'1','185')       as varchar(3)) as FLG_185,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='186'    then 1 else null end) as varchar(1))),'1','186')       as varchar(3)) as FLG_186,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='188'    then 1 else null end) as varchar(1))),'1','188')       as varchar(3)) as FLG_188,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='190'    then 1 else null end) as varchar(1))),'1','190')       as varchar(3)) as FLG_190,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='191'    then 1 else null end) as varchar(1))),'1','191')       as varchar(3)) as FLG_191,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='192'    then 1 else null end) as varchar(1))),'1','192')       as varchar(3)) as FLG_192,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='194'    then 1 else null end) as varchar(1))),'1','194')       as varchar(3)) as FLG_194,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='196'    then 1 else null end) as varchar(1))),'1','196')       as varchar(3)) as FLG_196,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='197'    then 1 else null end) as varchar(1))),'1','197')       as varchar(3)) as FLG_197,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='198'    then 1 else null end) as varchar(1))),'1','198')       as varchar(3)) as FLG_198,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='200'    then 1 else null end) as varchar(1))),'1','200')       as varchar(3)) as FLG_200,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='202'    then 1 else null end) as varchar(1))),'1','202')       as varchar(3)) as FLG_202,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='203'    then 1 else null end) as varchar(1))),'1','203')       as varchar(3)) as FLG_203,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='204'    then 1 else null end) as varchar(1))),'1','204')       as varchar(3)) as FLG_204,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='206'    then 1 else null end) as varchar(1))),'1','206')       as varchar(3)) as FLG_206,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='208'    then 1 else null end) as varchar(1))),'1','208')       as varchar(3)) as FLG_208,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='209'    then 1 else null end) as varchar(1))),'1','209')       as varchar(3)) as FLG_209,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='210'    then 1 else null end) as varchar(1))),'1','210')       as varchar(3)) as FLG_210,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='211'    then 1 else null end) as varchar(1))),'1','211')       as varchar(3)) as FLG_211,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='213'    then 1 else null end) as varchar(1))),'1','213')       as varchar(3)) as FLG_213,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='215'    then 1 else null end) as varchar(1))),'1','215')       as varchar(3)) as FLG_215,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='216'    then 1 else null end) as varchar(1))),'1','216')       as varchar(3)) as FLG_216,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='219'    then 1 else null end) as varchar(1))),'1','219')       as varchar(3)) as FLG_219,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='222'    then 1 else null end) as varchar(1))),'1','222')       as varchar(3)) as FLG_222,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='223'    then 1 else null end) as varchar(1))),'1','223')       as varchar(3)) as FLG_223,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='225'    then 1 else null end) as varchar(1))),'1','225')       as varchar(3)) as FLG_225,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='227'    then 1 else null end) as varchar(1))),'1','227')       as varchar(3)) as FLG_227,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='228'    then 1 else null end) as varchar(1))),'1','228')       as varchar(3)) as FLG_228,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='230'    then 1 else null end) as varchar(1))),'1','230')       as varchar(3)) as FLG_230,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='231'    then 1 else null end) as varchar(1))),'1','231')       as varchar(3)) as FLG_231,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='232'    then 1 else null end) as varchar(1))),'1','232')       as varchar(3)) as FLG_232,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='234'    then 1 else null end) as varchar(1))),'1','234')       as varchar(3)) as FLG_234,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='235'    then 1 else null end) as varchar(1))),'1','235')       as varchar(3)) as FLG_235,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='236'    then 1 else null end) as varchar(1))),'1','236')       as varchar(3)) as FLG_236,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='237'    then 1 else null end) as varchar(1))),'1','237')       as varchar(3)) as FLG_237,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='239'    then 1 else null end) as varchar(1))),'1','239')       as varchar(3)) as FLG_239,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='242'    then 1 else null end) as varchar(1))),'1','242')       as varchar(3)) as FLG_242,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='243'    then 1 else null end) as varchar(1))),'1','243')       as varchar(3)) as FLG_243,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='245'    then 1 else null end) as varchar(1))),'1','245')       as varchar(3)) as FLG_245,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='246'    then 1 else null end) as varchar(1))),'1','246')       as varchar(3)) as FLG_246,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='247'    then 1 else null end) as varchar(1))),'1','247')       as varchar(3)) as FLG_247,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='248'    then 1 else null end) as varchar(1))),'1','248')       as varchar(3)) as FLG_248,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='249'    then 1 else null end) as varchar(1))),'1','249')       as varchar(3)) as FLG_249,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='251'    then 1 else null end) as varchar(1))),'1','251')       as varchar(3)) as FLG_251,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='255'    then 1 else null end) as varchar(1))),'1','255')       as varchar(3)) as FLG_255,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='256'    then 1 else null end) as varchar(1))),'1','256')       as varchar(3)) as FLG_256,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='257'    then 1 else null end) as varchar(1))),'1','257')       as varchar(3)) as FLG_257,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='258'    then 1 else null end) as varchar(1))),'1','258')       as varchar(3)) as FLG_258,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='259'    then 1 else null end) as varchar(1))),'1','259')       as varchar(3)) as FLG_259,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='260'    then 1 else null end) as varchar(1))),'1','260')       as varchar(3)) as FLG_260,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='262'    then 1 else null end) as varchar(1))),'1','262')       as varchar(3)) as FLG_262,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='263'    then 1 else null end) as varchar(1))),'1','263')       as varchar(3)) as FLG_263,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='264'    then 1 else null end) as varchar(1))),'1','264')       as varchar(3)) as FLG_264,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='265'    then 1 else null end) as varchar(1))),'1','265')       as varchar(3)) as FLG_265,
        cast(oreplace(trim(cast(min(case when trim(TEMPLATE_ROW_CD)='266'    then 1 else null end) as varchar(1))),'1','266')       as varchar(3)) as FLG_266
    from &tdStagedb..NSFR_Distinct_NOCFL
    group by SK_ID_NOCFL
  )
) by teradata;



execute (
           replace view &tdStagedb..&lasrTable as
    (                                             
    SELECT  cast(t1.SK_ID_NOCFL as VARCHAR(20)) SK_ID_NOCFL, GLRT_SOURCE_CD, PROVIDER_CD, LEGAL_ENTITY_CD,
            INSERT_TS, PERIMETER_CD, DEAL_SK_ID, BALANCE_SHEET_ITEM_CD, SIGN WTE_SIGN,
            ISIN_NUM, OPERATION_TYPE_CD, PRODUCT_CD, CURRENCY_CD, COUNTERPARTY_CD,
            INTRAGROUP_COUNTERPARTY_IND, INTRAGROUP_COUNTERPARTY_CD, B3_COUNTERPARTY_TYPE_CD,
            COUNTERPARTY_COUNTRY_CD, ORIGINAL_MATURITY_CD, FINREP_CPARTY_TYPE_CD,
            FINREP_PRODUCT_TYPE_CD, RELATIONSHIP_IND, INSURED_IND, PREPAY_CLAUSE_IND,
            NOTICE_DAYS_NUM, TRANSACTIONAL_IND, SECURITY_LENDING_IND, COLLATERAL_CD,
            PHANTOM_LIABILITY_IND, COLLATERAL_PLATFORM_CD, OPERATIONAL_IND,
            COLLATERAL_MATURITY_DT, CAT_DEPO_DIFF_OUTFLOWS_CD, PHANTOM_CD,
            SPV_FLG, EVIDENCE_FLG, SPEC_DEBT_TYPE_CD, DERIVATIVE_CONTRACT_TYPE_CD,
            QUOTATION_CD, SETTLEMENT_TYPE_CD, FLOW_CRNCY_CD, COLLATERALIZED_DERIVATIVE_IND,
            CONTRACT_MAT_DT, CENTRAL_CPARTY_FLG, NETTING_FLG, LEG, COMM_CPARTY_TYPE_CD,
            SETTLEMENT_DT, CONTRACT_MATURITY_DT, TRANSACTION_TYPE_CD, TOBE_RECEIVED_CCY_CD,
            TOBE_DELIVERED_CCY_CD, RETAIL_IND, OWNER_EARLY_EXERCISE_OPT_IND,
            MORTGAGE_TYPE_CD, GUARANTEE_TYPE_CD, GUARANTEE_CODE_CD, GUARANTEE_MATURITY_CD,
            ASSET_CLASS_CD, SPV_IND, SECURITIZATION_IND, OPERATIONAL_TYPE_CD,
            MAT_TYPE_CD, SPEC_CREDIT_TYPE_CD, ELG_CREDIT_CLAIM_FLG, CLEARING_HOUSE_FLG,
            CREDIT_STATUS_CD, COLLATERALIZED_TYPE_CD, MARGIN_CALL_TYPE_CD,
            LEGAL_OWNERSHIP_FLG, FIELD_CD, INSTRUMENT_TYPE_CD WTE_INSTRUMENT_TYPE_CD, CREDIT_LINE_TYPE_CD,
            SPEC_OFF_BAL_TYPE_CD, SIDE_CD, REG_CPARTY_FLG, LOCAL_CURRENCY_IND,
            SECURITY_LENDING_CCY_CD, LIQUIDITY_PORTFOLIO_IND, SECURITY_LENDING_TYPE_CD,
            COUNTERPARTY_RWA_PCT, SECURITY_TYPE_CD CONTRACT_TYPE_CD, FIRST_NOTICE_DATE_CD,
            RWA_B2_STAND_APPR_PCT, COLLATERAL_TYPE_CD, CATEGORY_CD, CATEGORY_NUM,
            CATEGORY_DESC, CATEGORY_TYPE, SIGN_OF_TRANSACTION_CD, RATIO_CD,
            EBA_FP_CODE, INTRAGROUP_CPARTY_FLG, INTRAGROUP_CPARTY_CD, TEMPLATE_CD,
            VALUE_TYPE_CD, ALMT_MATURITY_CD, REPORT_DT, SOURCE_CD, REFERENCE_DT,
            FREQ_CD, ORIGINAL_REFERENCE_DT, SCENARIO_CD, SUBSCENARIO_CD,
            CFL_DATE_RICALCOLATA, TIME_BUCKET_CD, FLOW_TYPE_CD, COUNTERPARTY_DESC,
            GROSS_CARRYING_VAL_AMT, NOMINAL_AMT, NOMINAL_CCY_AMT, CARRYING_VALUE_AMT,
            BUCKET_TYPE_AMT, QIS_KEY, DQ_KEY, PAY_BUCKET_CD, PAY_AMT, CARRYING_VAL_EUR_AMT,
            NOMINAL_HOLD_AMT, NOMINAL_HOLD_CCY_AMT, INSURED_AMT, INSURED_PCT,
            TOBE_RECEIVED_CCY_AMT, TOBE_RECEIVED_AMT, TOBE_DELIVERED_CCY_AMT,
            TOBE_DELIVERED_AMT, TRANSACT_SPOT_CTRVL_AMT, TRANS_SPOT_CNTRVAL_CRNCY_AMT,
            FV_EUR_AMT, FV_CRNCY_AMT, FUND_WRITEDOWN_AMT, HAIRCUT_PCT, FIELD_AMT,
            NOMINAL_UNDRAWN_AMT, NOMINAL_UNDRAWN_CCY_AMT, ORIGINATOR_IND,
            SELF_SECURITIZATION_IND, FAIR_VALUE_AMT, EQUIVALENT_COUNTERVALUE_AMT,
            FV_ISSUING_CCY_AMT, COLLATERAL_CASH_AMT, SECURITY_DESC, SECURITY_MATURITY_DT,
            CALLABLE_IND, PUTABLE_IND, STRUCTURED_IND, SUBORDINATED_IND,
            GUARANTEE_IND, ISSUER_CD, ISSUER_DESC, INTRAGROUP_ISSUER_IND,
            SUBCONSOLIDATED_ISSUER_CD, B3_ISSUER_TYPE_CD, ISSUER_COUNTRY_CD,
            ISSUANCE_RATING_CD, GUARANTOR_CD, GUARANTOR_TYPE_CD, GUARANTOR_COUNTRY_CD,
            ELIGIBILITY_IND, BANK_ELIGIBILITY_CD, EQUITY_INDEX_IND, PRICE_AMT,
            POOL_FACTOR_AMT, INTRAGROUP_ISSUER_CD, COVERED_BOND_HAIRCUT_PCT,
            CENTRAL_BANK_HAIRCUT_PCT, TRANCHE_ABS_CD, OPERATION_CD, RATING_TYPE_CD,
            LIQ_CREDIT_QUALITY_CD, SPECIFIC_ISSUER_CD, SPECIFIC_GUARANTOR_CD,
            CIU_UNDER_ASSET_TYPE_CD, GUAR_ASSET_FLG, INFRA_ORIG_ASSET_FLG,
            FULL_REC_FLG, LOAN_TO_VALUE_PCT, PART_RETAINER_FLG, WAL_DT, ISSUED_DT,
            FINREP_ISSUING_TYPE_CD, FINREP_GUA_TYPE_CD, EQUIVALENT_CNTRVAL_CCY_AMT,
            FV_HEDGING_DERIVATIVES_AMT, COLLATERAL_USED_AMT, COLLATERAL_REC_AMT,
            RECORD_ID, DEPOSITED_AMT, ENCUMBERED_AMT, UNENCUMBERED_AMT, ON_BALANCE_FLOW_AMT,
            CB_HAIRCUT_PCT, ON_BALANCE_FLOW_NOMINAL_AMT, FUND_SEC_LEND_AMT,
            UNSEC_LEND_AMT, PCT, AMOUNT, COMM_TYPE_CD, COMM_CTP_CD, COMM_PRODUCT_CD,
            PROJ_EXPIRED_IND, ORIGINAL_WAL_DT, PROJ_WAL, ORIGINAL_CARRYING_VALUE,
            ORIGINAL_FAIR_VALUE_AMT, ORIGINAL_NOMINAL_AMT, ORIGINAL_FFV,
            ORIGINAL_CONTRACT_MATURITY, DIM_BSI_SCHEMA_CD, DIM_BSI_SCHEMA_DESC,
            DIM_BSI_SIGN_CD, AF11_SECURITY_DESC, AF11_SECURITY_TYPE_CD, AF11_CURRENCY_CD,
            AF11_SUBORDINATED_IND, AF11_ISSUER_CD, AF11_ISSUER_DESC, AF11_INTRAGROUP_ISSUER_IND,
            AF11_B3_ISSUER_TYPE_CD, AF11_ISSUER_COUNTRY_CD, AF11_ISSUANCE_RATING_CD,
            AF11_EQUITY_INDEX_IND, AF11_INTRAGROUP_ISSUER_CD, AF11_OPERATION_CD,
            AF11_ASSET_CLASS_CD, AF11_LIQ_CREDIT_QUALITY_CD, AF11_FINREP_ISSUING_TYPE_CD,
            AF11_SECURITY_MATURITY_DT, AF11_CALLABLE_IND, AF11_PUTABLE_IND,
            AF11_FIRST_NOTICE_DATE_CD, AF11_STRUCTURED_IND, AF11_GUARANTEE_IND,
            AF11_SUBCONSOLIDATED_ISSUER_CD, AF11_RWA_B2_STAND_APPR_PCT, AF11_GUARANTOR_CD,
            AF11_GUARANTOR_TYPE_CD, AF11_GUARANTOR_COUNTRY_CD, AF11_ELIGIBILITY_IND,
            AF11_BANK_ELIGIBILITY_CD, AF11_PRICE_AMT, AF11_POOL_FACTOR_AMT,
            AF11_COVERED_BOND_HAIRCUT_PCT, AF11_MORTGAGE_TYPE_CD, AF11_CENTRAL_BANK_HAIRCUT_PCT,
            AF11_TRANCHE_ABS_CD, AF11_RATING_TYPE_CD, AF11_SPECIFIC_ISSUER_CD,
            AF11_SPECIFIC_GUARANTOR_CD, AF11_CIU_UNDER_ASSET_TYPE_CD, AF11_GUAR_ASSET_FLG,
            AF11_INFRA_ORIG_ASSET_FLG, AF11_FULL_REC_FLG, AF11_LOAN_TO_VALUE_PCT,
            AF11_PART_RETAINER_FLG, AF11_COMM_CPARTY_TYPE_CD, AF11_WAL_DT,
            AF11_ISSUED_DT, AF11_FINREP_GUA_TYPE_CD, AF11_CALC_ORIGINAL_MATURITY_CD,
            AF11_INTRAGROUP_ISSUER_DESC, SEC_CAT_ID_BB_GLOBAL, SEC_CAT_DA_CAT_TYPE,
            SEC_CAT_B3_CAT_TYPE, SEC_CAT_B3_EXP_TYPE, SEC_CAT_COND, SEC_CAT_DA_CAT_NUM,
            SEC_CAT_DA_CAT_CD, SEC_CAT_DA_CAT_DES, SEC_CAT_DA_ITS_ROW_NUM,
            SEC_CAT_ISO_CURRENCY_CD, SEC_CAT_LE_ISO_COUNTRY_CD, AF31_NOMINAL_AMT,
            AF31_INSURED_AMT, AF31_INSURED_PCT, DIM_FB_BRANCH_DESC, DIM_FB_LE_CD,
            DIM_FB_LE_DESC, DIM_FB_LE_ISO_COUNTRY_CD, DIM_FB_SUBCONSOLIDATED_CD,
            DIM_FB_SUBCONSOLIDATED_DESC, DIM_FB_CONSOLIDATED_CD, DIM_FB_CONSOLIDATED_DESC,
            DIM_FB_REPORTING_BANK_CD, DIM_FB_IN_BRANCH_DESC, DIM_FB_IN_LE_CD,
            DIM_FB_IN_LE_DESC, DIM_FB_IN_LE_ISO_COUNTRY_CD, DIM_FB_IN_SUBCONSOLIDATED_CD,
            DIM_FB_IN_SUBCONSOLIDATED_DESC, DIM_FB_IN_CONSOLIDATED_CD, DIM_FB_IN_CONSOLIDATED_DESC,
            DIM_FB_IN_REPORTING_BANK_CD, PERIMETER_I_IND, PERIMETER_S_IND,
            PERIMETER_C_IND, PERIMETER_X_IND, PERIMETER_COUNTRY_IND, AMLT41_NOMINAL_HOLD_AMT_PER_I,
            AMLT41_NOMINAL_AMT_PER_I, AMLT41_HOLDING_PCT_PER_I, AMLT41_NOMINAL_HOLD_AMT_PER_S,
            AMLT41_NOMINAL_AMT_PER_S, AMLT41_HOLDING_PCT_PER_S, AMLT41_NOMINAL_HOLD_AMT_PER_C,
            AMLT41_NOMINAL_AMT_PER_C, AMLT41_HOLDING_PCT_PER_C, AF2_ORIGINATOR_IND,
            AF2_SELF_SECURITIZATION_IND, AF4_RETAIL_IND, SUM_ALL_BUCKET_AMT,
            SUM_CF_BUCKET_AMT, COUNT_ALL_BUCKETS, COUNT_CF_BUCKETS, SUM_ABS_BUCKET_AMT,
            SUM_BUCKET_AMT_SCAD_0_6, SUM_BUCKET_AMT_SCAD_6_12, SUM_BUCKET_AMT_SCAD_12_OVER,
            SUM_BUCKET_30DAYS, BUCKET_30DAYS_EXPIRED_IND, US_MATURITY_CD,
            US_COUNTRY_CD, US_COUNTRY_DESC COUNTRY_DESC, US_INDIVIDUAL_LE_CD, US_CONSOLIDATED_LE_CD,
            US_INDIVIDUAL_PERIM_IND, US_CONSOLIDATED_PERIM_IND, US_INTRAGROUP_COUNTRY_CD INTRAGROUP_COUNTRY_CD,
            US_INTRAGROUP_COUNTRY_DESC INTRAGROUP_COUNTRY_DESC, QIS_CAT_CD, QIS_CAT_DES, QIS_LEVEL,
            NSFR_CLUSTER_CD, NSFR_INDIVIDUAL_LE_CD, NSFR_CONSOLIDATED_LE_CD,
            NSFR_INDIVIDUAL_PERIM_IND, NSFR_CONSOLIDATED_PERIM_IND, NSFR_MANUAL_DATA_INPUT_VALUE,
            NSFR_ISIN_CAP_EX_SUB_TYPE, NSFR_POOLF_OBFNA_GIVE_0_6, NSFR_POOLF_OBFNA_GIVE_6_12,
            NSFR_POOLF_OBFNA_GIVE_12_OVER, NSFR_POOLF_OBFNA_RECEIVE_TOT,
            NSFR_POOLF_CCL_CAT_CD, MIN_GROSS_CARRYING_VAL_AMT, MIN_NOMINAL_AMT,
            MIN_CARRYING_VALUE_AMT, MIN_FAIR_VALUE_AMT, MIN_ENCUMBERED_AMT,
            MIN_UNENCUMBERED_AMT,
            oreplace('"'||coalesce(t2.FLG_010,'')  ||'","'||
                        coalesce(t2.FLG_010a,'') ||'","'||
                        coalesce(t2.FLG_010b,'') ||'","'||
                        coalesce(t2.FLG_010b1,'')||'","'||
                        coalesce(t2.FLG_010b2,'')||'","'||
                        coalesce(t2.FLG_010b3,'')||'","'||
                        coalesce(t2.FLG_010c1,'')||'","'||
                        coalesce(t2.FLG_010c2,'')||'","'||
                        coalesce(t2.FLG_010c3,'')||'","'||
                        coalesce(t2.FLG_010c4,'')||'","'||
                        coalesce(t2.FLG_010c5,'')||'","'||
                        coalesce(t2.FLG_010c6,'')||'","'||
                        coalesce(t2.FLG_020,'')  ||'","'||
                        coalesce(t2.FLG_020a1,'')||'","'||
                        coalesce(t2.FLG_020a2,'')||'","'||
                        coalesce(t2.FLG_020a3,'')||'","'||
                        coalesce(t2.FLG_020a4,'')||'","'||
                        coalesce(t2.FLG_020a5,'')||'","'||
                        coalesce(t2.FLG_020a6,'')||'","'||
                        coalesce(t2.FLG_030,'')  ||'","'||
                        coalesce(t2.FLG_040,'')  ||'","'||
                        coalesce(t2.FLG_040a1,'')||'","'||
                        coalesce(t2.FLG_040a2,'')||'","'||
                        coalesce(t2.FLG_040a3,'')||'","'||
                        coalesce(t2.FLG_040a4,'')||'","'||
                        coalesce(t2.FLG_050,'')  ||'","'||
                        coalesce(t2.FLG_060,'')  ||'","'||
                        coalesce(t2.FLG_060a,'') ||'","'||
                        coalesce(t2.FLG_060b,'') ||'","'||
                        coalesce(t2.FLG_070,'')  ||'","'||
                        coalesce(t2.FLG_070a,'')  ||'","'||
                        coalesce(t2.FLG_070b,'')  ||'","'||                        
                        coalesce(t2.FLG_070c,'')  ||'","'||
                        coalesce(t2.FLG_070d,'')  ||'","'||
                        coalesce(t2.FLG_080,'')  ||'","'||
                        coalesce(t2.FLG_080a,'')  ||'","'||
                        coalesce(t2.FLG_080a1,'')  ||'","'||    
                        coalesce(t2.FLG_080a2,'')  ||'","'||
                        coalesce(t2.FLG_080b,'')  ||'","'||
                        coalesce(t2.FLG_080b1,'')  ||'","'||
                        coalesce(t2.FLG_080b2,'')  ||'","'||
                        coalesce(t2.FLG_090,'')  ||'","'||
                        coalesce(t2.FLG_100,'')  ||'","'||
                        coalesce(t2.FLG_110,'')  ||'","'||
                        coalesce(t2.FLG_120,'')  ||'","'||
                        coalesce(t2.FLG_120a1,'')  ||'","'||
                        coalesce(t2.FLG_120a2,'')  ||'","'||
                        coalesce(t2.FLG_120a3,'')  ||'","'||
                        coalesce(t2.FLG_120a4,'')  ||'","'||
                        coalesce(t2.FLG_130,'')  ||'","'||
                        coalesce(t2.FLG_130a,'')  ||'","'||
                        coalesce(t2.FLG_130a1,'')  ||'","'||
                        coalesce(t2.FLG_130a2,'')  ||'","'||                        
                        coalesce(t2.FLG_130a3,'')  ||'","'||
                        coalesce(t2.FLG_130b,'')  ||'","'||
                        coalesce(t2.FLG_130b1,'')  ||'","'||
                        coalesce(t2.FLG_130b2,'')  ||'","'||
                        coalesce(t2.FLG_130b3,'')  ||'","'||
                        coalesce(t2.FLG_140,'')  ||'","'||
                        coalesce(t2.FLG_140a1,'')  ||'","'||
                        coalesce(t2.FLG_140a2,'')  ||'","'||
                        coalesce(t2.FLG_140a3,'')  ||'","'||
                        coalesce(t2.FLG_150,'')  ||'","'||
                        coalesce(t2.FLG_150a1,'')  ||'","'||
                        coalesce(t2.FLG_150a2,'')  ||'","'||
                        coalesce(t2.FLG_150a3,'')  ||'","'||
                        coalesce(t2.FLG_160,'')  ||'","'||
                        coalesce(t2.FLG_160a,'')  ||'","'||
                        coalesce(t2.FLG_160a1,'')  ||'","'||
                        coalesce(t2.FLG_160a2,'')  ||'","'||
                        coalesce(t2.FLG_160a3,'')  ||'","'||
                        coalesce(t2.FLG_160b,'')  ||'","'||
                        coalesce(t2.FLG_160b1,'')  ||'","'||
                        coalesce(t2.FLG_160b2,'')  ||'","'||
                        coalesce(t2.FLG_160b3,'')  ||'","'||
                        coalesce(t2.FLG_170,'')  ||'","'||
                        coalesce(t2.FLG_180,'')  ||'","'||
                        coalesce(t2.FLG_180a,'')  ||'","'||
                        coalesce(t2.FLG_180b,'')  ||'","'||
                        coalesce(t2.FLG_190,'')  ||'","'||
                        coalesce(t2.FLG_200,'')  ||'","'||
                        coalesce(t2.FLG_200a1,'')  ||'","'||
                        coalesce(t2.FLG_200a2,'')  ||'","'||
                        coalesce(t2.FLG_210,'')  ||'","'||
                        coalesce(t2.FLG_220,'')  ||'","'||
                        coalesce(t2.FLG_220a1,'')  ||'","'||
                        coalesce(t2.FLG_220a2,'')  ||'","'||
                        coalesce(t2.FLG_230,'')  ||'","'||
                        coalesce(t2.FLG_240,'')  ||'","'||
                        coalesce(t2.FLG_240a1,'')  ||'","'||
                        coalesce(t2.FLG_240a2,'')  ||'","'||
                        coalesce(t2.FLG_250,'')  ||'","'||
                        coalesce(t2.FLG_260,'')  ||'","'||
                        coalesce(t2.FLG_260a1,'')  ||'","'||
                        coalesce(t2.FLG_260a2,'')  ||'","'||
                        coalesce(t2.FLG_270,'')  ||'","'||
                        coalesce(t2.FLG_270a1,'')  ||'","'||
                        coalesce(t2.FLG_270a2,'')  ||'","'||
                        coalesce(t2.FLG_280,'')  ||'","'||
                        coalesce(t2.FLG_290,'')  ||'","'||
                        coalesce(t2.FLG_290a1,'')  ||'","'||
                        coalesce(t2.FLG_290a2,'')  ||'","'||
                        coalesce(t2.FLG_300,'')  ||'","'||
                        coalesce(t2.FLG_310,'')  ||'","'||
                        coalesce(t2.FLG_320,'')  ||'","'||
                        coalesce(t2.FLG_330,'')  ||'","'||
                        coalesce(t2.FLG_340,'')  ||'","'||
                        coalesce(t2.FLG_350,'')  ||'","'||
                        coalesce(t2.FLG_350a,'')  ||'","'||
                        coalesce(t2.FLG_350b,'')  ||'","'||
                        coalesce(t2.FLG_350c,'')  ||'","'||
                        coalesce(t2.FLG_360,'')  ||'","'||
                        coalesce(t2.FLG_370,'')  ||'","'||
                        coalesce(t2.FLG_380,'')  ||'"'                         
                        ,'"",','') as TEMPLATE_ROW_FLG
/*Da togliere commento dopo 23 Marzo*/

,
            oreplace('"'||
                        coalesce(t3.FLG_006,'')  ||'","'||
                        coalesce(t3.FLG_007,'')  ||'","'||
                        coalesce(t3.FLG_008,'')  ||'","'||
                        coalesce(t3.FLG_009,'')  ||'","'||
                        coalesce(t3.FLG_010,'')  ||'","'||
                        coalesce(t3.FLG_011,'')  ||'","'||
                        coalesce(t3.FLG_012,'')  ||'","'||
                        coalesce(t3.FLG_013,'')  ||'","'||
                        coalesce(t3.FLG_014,'')  ||'","'||
                        coalesce(t3.FLG_015,'')  ||'","'||
                        coalesce(t3.FLG_016,'')  ||'","'||
                        coalesce(t3.FLG_017,'')  ||'","'||
                        coalesce(t3.FLG_018,'')  ||'","'||
                        coalesce(t3.FLG_019,'')  ||'","'||
                        coalesce(t3.FLG_020,'')  ||'","'||
                        coalesce(t3.FLG_021,'')  ||'","'||
                        coalesce(t3.FLG_022,'')  ||'","'||
                        coalesce(t3.FLG_023,'')  ||'","'||
                        coalesce(t3.FLG_024,'')  ||'","'||
                        coalesce(t3.FLG_025,'')  ||'","'||
                        coalesce(t3.FLG_026,'')  ||'","'||
                        coalesce(t3.FLG_027,'')  ||'","'||
                        coalesce(t3.FLG_028,'')  ||'","'||
                        coalesce(t3.FLG_029,'')  ||'","'||
                        coalesce(t3.FLG_030,'')  ||'","'||
                        coalesce(t3.FLG_031,'')  ||'","'||
                        coalesce(t3.FLG_032,'')  ||'","'||
                        coalesce(t3.FLG_033,'')  ||'","'||
                        coalesce(t3.FLG_034,'')  ||'","'||
                        coalesce(t3.FLG_035,'')  ||'","'||
                        coalesce(t3.FLG_036,'')  ||'","'||
                        coalesce(t3.FLG_037,'')  ||'","'||
                        coalesce(t3.FLG_038,'')  ||'","'||
                        coalesce(t3.FLG_039,'')  ||'","'||
                        coalesce(t3.FLG_040,'')  ||'","'||
                        coalesce(t3.FLG_041,'')  ||'","'||
                        coalesce(t3.FLG_042,'')  ||'","'||
                        coalesce(t3.FLG_043,'')  ||'","'||
                        coalesce(t3.FLG_044,'')  ||'","'||
                        coalesce(t3.FLG_045,'')  ||'","'||
                        coalesce(t3.FLG_046,'')  ||'","'||
                        coalesce(t3.FLG_047,'')  ||'","'||
                        coalesce(t3.FLG_048,'')  ||'","'||
                        coalesce(t3.FLG_049,'')  ||'","'||
                        coalesce(t3.FLG_050,'')  ||'","'||
                        coalesce(t3.FLG_051,'')  ||'","'||
                        coalesce(t3.FLG_052,'')  ||'","'||
                        coalesce(t3.FLG_053,'')  ||'","'||
                        coalesce(t3.FLG_054,'')  ||'","'||
                        coalesce(t3.FLG_055,'')  ||'","'||
                        coalesce(t3.FLG_056,'')  ||'","'||
                        coalesce(t3.FLG_057,'')  ||'","'||
                        coalesce(t3.FLG_058,'')  ||'","'|| 
                        coalesce(t3.FLG_059,'')  ||'","'||
                        coalesce(t3.FLG_060,'')  ||'","'||
                        coalesce(t3.FLG_061,'')  ||'","'||
                        coalesce(t3.FLG_062,'')  ||'","'||
                        coalesce(t3.FLG_063,'')  ||'","'||
                        coalesce(t3.FLG_064,'')  ||'","'||
                        coalesce(t3.FLG_065,'')  ||'","'||
                        coalesce(t3.FLG_066,'')  ||'","'||
                        coalesce(t3.FLG_067,'')  ||'","'||
                        coalesce(t3.FLG_068,'')  ||'","'||
                        coalesce(t3.FLG_069,'')  ||'","'||
                        coalesce(t3.FLG_070,'')  ||'","'||
                        coalesce(t3.FLG_071,'')  ||'","'||
                        coalesce(t3.FLG_072,'')  ||'","'||
                        coalesce(t3.FLG_073,'')  ||'","'||
                        coalesce(t3.FLG_074,'')  ||'","'||
                        coalesce(t3.FLG_075,'')  ||'","'||
                        coalesce(t3.FLG_076,'')  ||'","'||
                        coalesce(t3.FLG_084,'')  ||'","'||
                        coalesce(t3.FLG_085,'')  ||'","'||
                        coalesce(t3.FLG_086,'')  ||'","'||
                        coalesce(t3.FLG_087,'')  ||'","'||
                        coalesce(t3.FLG_088,'')  ||'","'||
                        coalesce(t3.FLG_089,'')  ||'","'||
                        coalesce(t3.FLG_090,'')  ||'","'||
                        coalesce(t3.FLG_091,'')  ||'","'||
                        coalesce(t3.FLG_092,'')  ||'","'||
                        coalesce(t3.FLG_093,'')  ||'","'||
                        coalesce(t3.FLG_094,'')  ||'","'||
                        coalesce(t3.FLG_095,'')  ||'","'||
                        coalesce(t3.FLG_096,'')  ||'","'||
                        coalesce(t3.FLG_097,'')  ||'","'||
                        coalesce(t3.FLG_098,'')  ||'","'||
                        coalesce(t3.FLG_099,'')  ||'","'||
                        coalesce(t3.FLG_100,'')  ||'","'||
                        coalesce(t3.FLG_101,'')  ||'","'||
                        coalesce(t3.FLG_102,'')  ||'","'||
                        coalesce(t3.FLG_103,'')  ||'","'||
                        coalesce(t3.FLG_104,'')  ||'","'||
                        coalesce(t3.FLG_105,'')  ||'","'||
                        coalesce(t3.FLG_106,'')  ||'","'||
                        coalesce(t3.FLG_107,'')  ||'","'||
                        coalesce(t3.FLG_108,'')  ||'","'||
                        coalesce(t3.FLG_109,'')  ||'","'||
                        coalesce(t3.FLG_110,'')  ||'","'||
                        coalesce(t3.FLG_111,'')  ||'","'||
                        coalesce(t3.FLG_112,'')  ||'","'||
                        coalesce(t3.FLG_113,'')  ||'","'||
                        coalesce(t3.FLG_114,'')  ||'","'||
                        coalesce(t3.FLG_115,'')  ||'","'||
                        coalesce(t3.FLG_116,'')  ||'","'||
                        coalesce(t3.FLG_117,'')  ||'","'||
                        coalesce(t3.FLG_118,'')  ||'","'||
                        coalesce(t3.FLG_119,'')  ||'","'||
                        coalesce(t3.FLG_120,'')  ||'","'||
                        coalesce(t3.FLG_121,'')  ||'","'||
                        coalesce(t3.FLG_122,'')  ||'","'||
                        coalesce(t3.FLG_123,'')  ||'","'||
                        coalesce(t3.FLG_124,'')  ||'","'||
                        coalesce(t3.FLG_125,'')  ||'","'||
                        coalesce(t3.FLG_126,'')  ||'","'||
                        coalesce(t3.FLG_128,'')  ||'","'||
                        coalesce(t3.FLG_130,'')  ||'","'||
                        coalesce(t3.FLG_131,'')  ||'","'||
                        coalesce(t3.FLG_132,'')  ||'","'||
                        coalesce(t3.FLG_134,'')  ||'","'||
                        coalesce(t3.FLG_136,'')  ||'","'||
                        coalesce(t3.FLG_137,'')  ||'","'||
                        coalesce(t3.FLG_138,'')  ||'","'||
                        coalesce(t3.FLG_140,'')  ||'","'||
                        coalesce(t3.FLG_142,'')  ||'","'||
                        coalesce(t3.FLG_143,'')  ||'","'||
                        coalesce(t3.FLG_144,'')  ||'","'||
                        coalesce(t3.FLG_146,'')  ||'","'||
                        coalesce(t3.FLG_147,'')  ||'","'||
                        coalesce(t3.FLG_149,'')  ||'","'||
                        coalesce(t3.FLG_150,'')  ||'","'||
                        coalesce(t3.FLG_151,'')  ||'","'||
                        coalesce(t3.FLG_152,'')  ||'","'||
                        coalesce(t3.FLG_154,'')  ||'","'||
                        coalesce(t3.FLG_155,'')  ||'","'||
                        coalesce(t3.FLG_156,'')  ||'","'||
                        coalesce(t3.FLG_158,'')  ||'","'||
                        coalesce(t3.FLG_160,'')  ||'","'||
                        coalesce(t3.FLG_161,'')  ||'","'||
                        coalesce(t3.FLG_162,'')  ||'","'||
                        coalesce(t3.FLG_164,'')  ||'","'||
                        coalesce(t3.FLG_166,'')  ||'","'||
                        coalesce(t3.FLG_167,'')  ||'","'||
                        coalesce(t3.FLG_168,'')  ||'","'||
                        coalesce(t3.FLG_170,'')  ||'","'||
                        coalesce(t3.FLG_172,'')  ||'","'||
                        coalesce(t3.FLG_173,'')  ||'","'||
                        coalesce(t3.FLG_174,'')  ||'","'||
                        coalesce(t3.FLG_176,'')  ||'","'||
                        coalesce(t3.FLG_178,'')  ||'","'||
                        coalesce(t3.FLG_179,'')  ||'","'||
                        coalesce(t3.FLG_180,'')  ||'","'||
                        coalesce(t3.FLG_182,'')  ||'","'||
                        coalesce(t3.FLG_184,'')  ||'","'||
                        coalesce(t3.FLG_185,'')  ||'","'||
                        coalesce(t3.FLG_186,'')  ||'","'||
                        coalesce(t3.FLG_188,'')  ||'","'||
                        coalesce(t3.FLG_190,'')  ||'","'||
                        coalesce(t3.FLG_191,'')  ||'","'||
                        coalesce(t3.FLG_192,'')  ||'","'||
                        coalesce(t3.FLG_194,'')  ||'","'||
                        coalesce(t3.FLG_196,'')  ||'","'||
                        coalesce(t3.FLG_197,'')  ||'","'||
                        coalesce(t3.FLG_198,'')  ||'","'||
                        coalesce(t3.FLG_200,'')  ||'","'||
                        coalesce(t3.FLG_202,'')  ||'","'||
                        coalesce(t3.FLG_203,'')  ||'","'||
                        coalesce(t3.FLG_204,'')  ||'","'||
                        coalesce(t3.FLG_206,'')  ||'","'||
                        coalesce(t3.FLG_208,'')  ||'","'||
                        coalesce(t3.FLG_209,'')  ||'","'||
                        coalesce(t3.FLG_210,'')  ||'","'||
                        coalesce(t3.FLG_211,'')  ||'","'||
                        coalesce(t3.FLG_213,'')  ||'","'||
                        coalesce(t3.FLG_215,'')  ||'","'||
                        coalesce(t3.FLG_216,'')  ||'","'||
                        coalesce(t3.FLG_219,'')  ||'","'||
                        coalesce(t3.FLG_222,'')  ||'","'||
                        coalesce(t3.FLG_223,'')  ||'","'||
                        coalesce(t3.FLG_225,'')  ||'","'||
                        coalesce(t3.FLG_227,'')  ||'","'||
                        coalesce(t3.FLG_228,'')  ||'","'||
                        coalesce(t3.FLG_230,'')  ||'","'||
                        coalesce(t3.FLG_231,'')  ||'","'||
                        coalesce(t3.FLG_232,'')  ||'","'||
                        coalesce(t3.FLG_234,'')  ||'","'||
                        coalesce(t3.FLG_235,'')  ||'","'||
                        coalesce(t3.FLG_236,'')  ||'","'||
                        coalesce(t3.FLG_237,'')  ||'","'||
                        coalesce(t3.FLG_239,'')  ||'","'||
                        coalesce(t3.FLG_242,'')  ||'","'||
                        coalesce(t3.FLG_243,'')  ||'","'||
                        coalesce(t3.FLG_245,'')  ||'","'||
                        coalesce(t3.FLG_246,'')  ||'","'||
                        coalesce(t3.FLG_247,'')  ||'","'||
                        coalesce(t3.FLG_248,'')  ||'","'||
                        coalesce(t3.FLG_249,'')  ||'","'||
                        coalesce(t3.FLG_251,'')  ||'","'||
                        coalesce(t3.FLG_255,'')  ||'","'||
                        coalesce(t3.FLG_256,'')  ||'","'||
                        coalesce(t3.FLG_257,'')  ||'","'||
                        coalesce(t3.FLG_258,'')  ||'","'||
                        coalesce(t3.FLG_259,'')  ||'","'||
                        coalesce(t3.FLG_260,'')  ||'","'||
                        coalesce(t3.FLG_262,'')  ||'","'||
                        coalesce(t3.FLG_263,'')  ||'","'||
                        coalesce(t3.FLG_264,'')  ||'","'||
                        coalesce(t3.FLG_265,'')  ||'","'||
                        coalesce(t3.FLG_266,'')  ||'"'
                        ,'"",','') as NSFR_TEMPLATE_ROW_FLG
 
    FROM   P_KGU_GLM_UES.GLRT_COMMON_SOURCE_V t1 left join &tdStagedb..US_Distinct_NOCFL1 t2
        on trim(cast(t1.SK_ID_NOCFL as VARCHAR(20)))=trim(t2.SK_ID_NOCFL)
/*Commendo da togliere dopo 23 marzo*/
       
        left join &tdStagedb..NSFR_Distinct_NOCFL1 t3
        on trim(cast(t1.SK_ID_NOCFL as VARCHAR(20)))=trim(t3.SK_ID_NOCFL)
       
    )         
)  by teradata;

%put executed creation of COMMON_SOURCE;



/************************************
			END USER CODE 
************************************/
%mend;