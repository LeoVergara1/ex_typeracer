/*
REM **************************************************************************
REM Nombre del Script: ca_user_campus.sql
REM Proyecto: api-comisiones
REM --------------------------------------------------------------------------
REM AUDIT TRAIL:  Version 1
REM Realizado por: VMRB, 3  de Dicimebre de 2019
*/
ALTER TABLE COMISIONLI.USER_CAMPUS ADD ROLE_DESCRIPTION VARCHAR2(255);
ALTER TABLE COMISIONLI.USER_CAMPUS ADD NAME_LONG VARCHAR2(255);
