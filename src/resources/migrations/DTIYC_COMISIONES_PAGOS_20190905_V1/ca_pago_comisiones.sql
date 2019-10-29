/*
REM **************************************************************************
REM Nombre del Script: ca_user_campus.sql
REM Proyecto: api-comisiones
REM --------------------------------------------------------------------------
REM AUDIT TRAIL:  Version 1
REM Realizado por: VMRB, 27  de Mayo  de 2019
*/
CREATE TABLE COMISIONLI.autorizacion_comisiones
  (
    id                  NUMBER,
    campus              VARCHAR2(150),
    fecha_inicial       DATE,
    fecha_final         DATE,
    id_promotor         VARCHAR2(100),
    nombre_promotor     VARCHAR2(250),
    puesto              VARCHAR2(250),
    id_alumno           VARCHAR2(15),
    nombre_alumno       VARCHAR2(250),
    pago_inicial        NUMBER(7,2),
    total_descuentos    NUMBER(7,2),
    comision            NUMBER(7,2),
    periodo             VARCHAR2(150),
    fecha_de_pago       DATE,
    autorizado_director CHAR,
    date_created        DATE,
    last_updated        DATE,
    CONSTRAINT pk_autorizacion_comisiones PRIMARY KEY (id)
  );

  CREATE TABLE "COMISIONLI"."MAIL_COMISIONES"
   (	"ID" NUMBER,
	"CAMPUS" VARCHAR2(3 CHAR),
	"PUESTO" VARCHAR2(30 CHAR),
	"RESPONSABLE" VARCHAR2(120 CHAR),
	"CORREO" VARCHAR2(100 CHAR),
	 CONSTRAINT "PK_MAIL_COMISIONES" PRIMARY KEY ("ID")
  );

  CREATE TABLE "COMISIONLI"."RELACION_CAMPUS"
   (	"ID" NUMBER,
	"CAMPUS" VARCHAR2(3 CHAR),
	"USUARIO" VARCHAR2(120 CHAR),
	"ESTATUS_ACTIVO" VARCHAR2(1 CHAR),
	"NOMBRE" VARCHAR2(200 CHAR),
	"DIRECTOR" VARCHAR2(1 CHAR),
	 CONSTRAINT "PK_RELACION_CAMPUS" PRIMARY KEY ("ID")
   );

  CREATE TABLE "COMISIONLI"."ADMIN_DE_COMISIONES"
   (	"ID" NUMBER,
	"COMISION_COORDINACION" NUMBER(3,1),
	"COMISION_EJECUTIVO" NUMBER(3,1),
	"CAMPUS_CODE" VARCHAR2(3 CHAR),
	"CAMPUS_DESC" VARCHAR2(30 CHAR),
	"CUOTA_FIJA" NUMBER(7,2),
	"LASTUPDATE" TIMESTAMP (6),
	 CONSTRAINT "PK_ADMIN_DE_COMISIONES" PRIMARY KEY ("ID")
   );

CREATE TABLE COMISIONLI.USER_CAMPUS(
  CAMPUS_CODE VARCHAR2(10) NOT NULL,
  USER_NAME VARCHAR2(250) NOT NULL,
  PIDM NUMBER NOT NULL,
  DATE_CREATED DATE NOT NULL,
  LAST_UPDATED DATE,
  CONSTRAINT USER_CAMPUS_PK PRIMARY KEY
  (
    CAMPUS_CODE,
    USER_NAME,
    PIDM
  )
  ENABLE
);


CREATE TABLE COMISIONLI.PROGRAM_MANAGER(
  PIDM NUMBER NOT NULL,
  USER_NAME VARCHAR2(20) NOT NULL,
  RECR_CODE VARCHAR2(4) NOT NULL,
  CONSTRAINT PROGRAM_MANAGER_PK PRIMARY KEY
  (
    PIDM,
    USER_NAME,
    RECR_CODE
  )
  ENABLE
);

CREATE TABLE COMISIONLI.ASOCIACION_PROMOTOR(
  PIDM NUMBER NOT NULL,
  USER_NAME VARCHAR2(20) NOT NULL,
  RECR_CODE VARCHAR2(4) NOT NULL,
  PIDM_MANAGER NUMBER,
  USER_NAME_MANAGER VARCHAR2(20),
  RECR_CODE_MANAGER VARCHAR2(4),
  ID_COORDINADOR VARCHAR2(100),
  NOMBRE_COORDINADOR VARCHAR2(100),
  APELLIDOS_COORDINADOR VARCHAR2(100),
  CLAVE_EMP_COORDINADOR VARCHAR2(100),
  CAMPUS_CODE VARCHAR2(255),
  CAMPUS_DESC VARCHAR2(255),
  ID_PROMOTOR VARCHAR2(255),
  NOMBRE_PROMOTOR VARCHAR2(255),
  APELLIDOS_PROMOTOR VARCHAR2(255),
  CLAVE_EMP_PROMOTOR VARCHAR2(100),
  PUESTO_PROMOTOR VARCHAR2(100),
  RELACION_ACTIVA VARCHAR2(4),
  USUARIO VARCHAR2(255),
  LAST_UPDATED DATE,
  CONSTRAINT ASOCIACION_PROMOTOR_PK PRIMARY KEY
  (
    USER_NAME,
    PIDM,
    RECR_CODE
  ),
  CONSTRAINT ASOCIACION_PROMOTOR_FK FOREIGN KEY
  (
    PIDM_MANAGER,
    USER_NAME_MANAGER,
    RECR_CODE_MANAGER
  )
  REFERENCES COMISIONLI.PROGRAM_MANAGER
  (
    PIDM,
    USER_NAME,
    RECR_CODE
  )
  ENABLE
);

ALTER TABLE COMISIONLI.AUTORIZACION_COMISIONES MODIFY AUTORIZADO_DIRECTOR VARCHAR2(255);
ALTER TABLE COMISIONLI.AUTORIZACION_COMISIONES ADD TIPO_PAGO VARCHAR2(255);
ALTER TABLE COMISIONLI.AUTORIZACION_COMISIONES ADD VALOR_CONTRATO_REAL NUMBER;
ALTER TABLE COMISIONLI.AUTORIZACION_COMISIONES ADD PIDM NUMBER;

CREATE TABLE COMISIONLI.TRIMESTER(
  ID NUMBER,
  STATUS VARCHAR(255),
  INIT_DATE DATE,
  END_DATE DATE,
  NAME VARCHAR(255),
  YEAR VARCHAR(20),
  CLAVE VARCHAR(255),
  PERIOD NUMBER,
  USERNAME VARCHAR(100),
  DATE_CREATED DATE,
  LAST_UPDATED DATE,
  CONSTRAINT PK_TRIMESTER PRIMARY KEY (ID)
);

CREATE SEQUENCE "COMISIONLI"."SQ_ID_TRIMESTER"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1;

CREATE TABLE COMISIONLI.GOAL(
  ID NUMBER,
  STATUS VARCHAR(255),
  TYPE VARCHAR(255),
  CAMPUS VARCHAR(20),
  NUM_REGISTERS NUMBER,
  PERCENT_COMMISSION NUMBER,
  USERNAME VARCHAR(100),
  DATE_CREATED DATE,
  LAST_UPDATED DATE,
  TRIMESTER_ID NUMBER,
  CONSTRAINT PK_GOAL PRIMARY KEY (ID)
);

ALTER TABLE COMISIONLI.GOAL
  ADD CONSTRAINT TRIMESTER_FK
      FOREIGN KEY (TRIMESTER_ID)
      REFERENCES COMISIONLI.TRIMESTER(ID);

CREATE SEQUENCE "COMISIONLI"."SQ_ID_GOAL"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1;


CREATE TABLE COMISIONLI.CAMPAIGN(
  ID NUMBER,
  STATUS VARCHAR(255),
  INIT_DATE DATE,
  END_DATE DATE,
  NAME VARCHAR(255),
  YEAR VARCHAR(20),
  USERNAME VARCHAR(100),
  PERIOD NUMBER,
  DATE_CREATED DATE,
  LAST_UPDATED DATE,
  CONSTRAINT PK_CAMPAIGN PRIMARY KEY (ID)
);

CREATE SEQUENCE "COMISIONLI"."SQ_ID_CAMPAIGN"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1;

CREATE TABLE COMISIONLI.AUTHORIZATION_CRESCENT(
  ID	NUMBER,
  CAMPUS	VARCHAR2(150),
  ID_PROMOTOR	VARCHAR2(100),
  NOMBRE_PROMOTOR	VARCHAR2(250),
  PUESTO	VARCHAR2(250),
  ID_ALUMNO	VARCHAR2(15),
  NOMBRE_ALUMNO	VARCHAR2(250),
  PAGO_INICIAL	NUMBER(7,2),
  TOTAL_DESCUENTOS	NUMBER(7,2),
  COMISION	NUMBER(7,2),
  PERIODO	VARCHAR2(150),
  FECHA_DE_PAGO	DATE,
  AUTORIZADO_DIRECTOR	VARCHAR2(255),
  DATE_CREATED	DATE,
  LAST_UPDATED	DATE,
  ID_COORDINADOR	VARCHAR2(15),
  NOMBRE_COORDINADOR	VARCHAR2(250),
  COMISION_COORDINADOR	NUMBER(7,2),
  FECHA_AUTORIZADO	DATE,
  USUARIO	VARCHAR2(150),
  TIPO_PAGO VARCHAR(50),
  PIDM NUMBER,
  VALOR_CONTRATO_REAL NUMBER,
  CONSTRAINT PK_AUTHORIZATION_CRESCENT PRIMARY KEY (ID)

);

CREATE SEQUENCE "COMISIONLI"."SQ_ID_AUTHORIZATION_CRESCENT"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1;

ALTER TABLE COMISIONLI.AUTORIZACION_COMISIONES ADD COMMENTS VARCHAR2(255);
ALTER TABLE COMISIONLI.AUTHORIZATION_CRESCENT ADD COMMENTS VARCHAR2(255);
ALTER TABLE COMISIONLI.AUTORIZACION_COMISIONES ADD ad_promotor VARCHAR2(255);
ALTER TABLE COMISIONLI.AUTHORIZATION_CRESCENT ADD ad_promotor VARCHAR2(255);
ALTER TABLE COMISIONLI.AUTORIZACION_COMISIONES ADD ad_coordinador VARCHAR2(255);
ALTER TABLE COMISIONLI.AUTHORIZATION_CRESCENT ADD ad_coordinador VARCHAR2(255);
ALTER TABLE COMISIONLI.CAMPAIGN ADD USERNAME_PROCESS VARCHAR2(255);
ALTER TABLE COMISIONLI.CAMPAIGN ADD STATUS_SICOSS VARCHAR2(255);
ALTER TABLE COMISIONLI.AUTORIZACION_COMISIONES ADD DISCOUNT_PERCENT VARCHAR2(255);
ALTER TABLE COMISIONLI.AUTHORIZATION_CRESCENT ADD DISCOUNT_PERCENT VARCHAR2(255);



CREATE TABLE COMISIONLI.SICOSS(
  ID_SICOSS	NUMBER,
  CLAVE_EMPLOYEE	VARCHAR2(150),
  TYPE_PAYSHEET	VARCHAR2(100),
  CLAVE_PAYSHEET	VARCHAR2(250),
  CONCEPT	VARCHAR2(250),
  DATE_MOVENMENT	DATE,
  REFERENCE1	VARCHAR2(250),
  REFERENCE2	VARCHAR2(250),
  DATA_PAYSHEET VARCHAR2(250),
  SALARY NUMBER,
  DATE_CREATED	DATE,
  LAST_UPDATED	DATE,
  IMPORT NUMBER,
  PAY_PERIOD VARCHAR2(250),
  CAMPUS VARCHAR2(250),
  DATE_AUTHORIZED DATE,
  TYPE_SICOSS VARCHAR(100),
  CONSTRAINT PK_SICOSS PRIMARY KEY (ID_SICOSS)

);
CREATE SEQUENCE "COMISIONLI"."SQ_ID_SICOSS"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1;

ALTER TABLE COMISIONLI.AUTORIZACION_COMISIONES ADD USERNAME_MARKETING VARCHAR2(255);
ALTER TABLE COMISIONLI.AUTORIZACION_COMISIONES ADD STATUS_MARKETING  NUMBER(1) DEFAULT 0 NOT NULL;
ALTER TABLE COMISIONLI.AUTHORIZATION_CRESCENT ADD USERNAME_MARKETING VARCHAR2(255);
ALTER TABLE COMISIONLI.AUTHORIZATION_CRESCENT ADD STATUS_MARKETING NUMBER(1) DEFAULT 0 NOT NULL;

ALTER TABLE COMISIONLI.AUTORIZACION_COMISIONES ADD USERNAME_RECTOR VARCHAR2(255);
ALTER TABLE COMISIONLI.AUTORIZACION_COMISIONES ADD STATUS_RECTOR  NUMBER(1) DEFAULT 0 NOT NULL;
ALTER TABLE COMISIONLI.AUTHORIZATION_CRESCENT ADD USERNAME_RECTOR VARCHAR2(255);
ALTER TABLE COMISIONLI.AUTHORIZATION_CRESCENT ADD STATUS_RECTOR NUMBER(1) DEFAULT 0 NOT NULL;