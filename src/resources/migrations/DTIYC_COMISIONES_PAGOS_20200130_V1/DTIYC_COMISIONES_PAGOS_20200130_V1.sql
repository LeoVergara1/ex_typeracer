REM ***************************************************************************
PROMPT Nombre del Script:  DTIYC_COMISIONES_PAGOS_20200129_V1
PROMPT Proyecto: CMISIONES_PAGOS
PROMPT VMR 10 de Enero 2020
PROMPT insets para comisiones
REM ***************************************************************************


SET ECHO OFF
SET VERIFY OFF

SPOOL DTIYC_COMISIONES_PAGOS_20200129_V1.log

PROMPT ***********************************************************************
ACCEPT BANINST1_PASSWORD PROMPT 'BANINST1 Password:' HIDE
CONNECT baninst1/&&baninst1_password
PROMPT ***********************************************************************

SELECT 'Fecha de ejecución: ' || TO_CHAR(SYSDATE,'DD-MON-YYYY HH24:MI') Fecha_Ejecucion FROM DUAL;

SET DEFINE OFF

PROMPT * EJECUCION DE SCRIPT *
@ca_pago_comisiones.sql

SHOW ERRORS

PROMPT Close output file.

SPOOL OFF

EXIT