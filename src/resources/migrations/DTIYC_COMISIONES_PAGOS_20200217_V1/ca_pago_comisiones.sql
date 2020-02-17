/*
REM **************************************************************************
REM Nombre del Script: ca_user_campus.sql
REM Proyecto: api-comisiones
REM --------------------------------------------------------------------------
REM AUDIT TRAIL:  Version 1
REM Realizado por: VMRB, 17  de Diciembre  de 2020
*/

ALTER TABLE COMISIONLI.ADMIN_DE_COMISIONES ADD COMISION_EJECUTIVO_FOURTY NUMBER DEFAULT 0;
ALTER TABLE COMISIONLI.ADMIN_DE_COMISIONES ADD COMISION_COORDINACION_FOURTY NUMBER DEFAULT 0;
ALTER TABLE COMISIONLI.GOAL ADD PERCENT_COMMISSION_FOURTY NUMBER DEFAULT 0;