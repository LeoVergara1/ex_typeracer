/*
REM **************************************************************************
REM Nombre del Script: ca_pago_comisiones.sql
REM Proyecto: api-comisiones
REM --------------------------------------------------------------------------
REM AUDIT TRAIL:  Version 1
REM Realizado por: VMRB, 08  de Julio  de 2019
*/

create or replace PACKAGE        genpub.pc_pago_comisiones AS

  FUNCTION fn_fecha_filtro (p_pidm IN NUMBER, p_periodo IN VARCHAR2) RETURN DATE;
  FUNCTION fn_comision (p_pidm IN NUMBER, p_periodo IN VARCHAR2)RETURN NUMBER;
  FUNCTION fn_monto_pago_inicial (p_pidm IN NUMBER, p_periodo IN VARCHAR2) RETURN NUMBER;
  FUNCTION fn_monto_descuentos (p_pidm IN NUMBER, p_periodo IN VARCHAR2) RETURN NUMBER;
  FUNCTION fn_comision_coordinador (p_pidm IN NUMBER, p_periodo IN VARCHAR2) RETURN NUMBER;
  PROCEDURE pr_inicializar_variables;
  PROCEDURE pr_comisiones(p_fecha_pago_ini IN DATE, p_fecha_pago_fin IN DATE, p_campus IN VARCHAR2, out_comisiones OUT sys_refcursor);

END pc_pago_comisiones;
/

create or replace PACKAGE BODY        genpub.pc_pago_comisiones AS

  CURSOR c_asignacion_descuentos (cp_pidm NUMBER, cp_periodo VARCHAR2, cp_detail_code VARCHAR2) IS
        SELECT DISTINCT
           substr(tbbexpt_exemption_code,1,4) tbbexpt_exemption_code,
           tbredet_percent porcentaje,
           tbbestu_exemption_priority
      FROM tbbexpt
      JOIN tbredet ON tbbexpt_exemption_code = tbredet_exemption_code AND tbbexpt_term_code = tbredet_term_code
      JOIN tbbestu ON tbbestu_exemption_code = tbredet_exemption_code AND tbbestu_term_code = tbredet_term_code
     WHERE tbbexpt_term_code = cp_periodo
       AND tbbestu_pidm = cp_pidm
       AND substr(tbredet_detail_code,1,2) = cp_detail_code
       ORDER BY tbbestu_exemption_priority;

    CURSOR c_porcentaje_comision (cp_campus VARCHAR2) IS
      SELECT comision_ejecutivo/100 comision_ejecutivo FROM pgocomis.admin_de_comisiones WHERE campus_code = cp_campus;


    CURSOR c_porcentaje_comision_coor (cp_campus VARCHAR2) IS
      SELECT comision_coordinacion/100 comision_ejecutivo_coor FROM pgocomis.admin_de_comisiones WHERE campus_code = cp_campus;


    CURSOR c_campus (cp_pidm NUMBER, cp_periodo VARCHAR2) IS
      SELECT DISTINCT
             sovlcur_camp_code
        FROM sovlcur
       WHERE sovlcur_pidm = cp_pidm
         AND sovlcur_term_code = cp_periodo
         AND sovlcur_key_seqno = 99
         AND sovlcur_priority_no = 1
         AND sovlcur_cact_code = 'ACTIVE'
         AND sovlcur_current_ind = 'Y'
         AND sovlcur_active_ind = 'Y';

    CURSOR c_valor_contrato(cp_pidm NUMBER, cp_periodo VARCHAR2) IS
      SELECT sum(tbraccd_amount) tbraccd_amount
        FROM tbraccd
       WHERE tbraccd_pidm = cp_pidm
         AND tbraccd_term_code = cp_periodo
         AND substr(tbraccd_detail_code,1,2) IN ('VC','VI');


    CURSOR c_tipo_pago (cp_pidm NUMBER, cp_periodo VARCHAR2)IS
      SELECT sgbstdn_rate_code
        FROM sgbstdn
       WHERE sgbstdn_pidm = cp_pidm
         AND sgbstdn_term_code_eff = cp_periodo;


     v_tipo_pago VARCHAR2(10);
     v_valor_contrato NUMBER(16,2);
     v_descuento NUMBER(16,2);
     v_remanente NUMBER(16,2);
     v_sin_comsiones NUMBER := 0;
     v_dummy1 NUMBER := 0;
     v_campus VARCHAR2(3);
     v_campus_coordinador VARCHAR2(3);
     v_porcentaje_comision NUMBER(16,2);
     v_codigo_crossref VARCHAR2(10);
     v_descuentos NUMBER(16,2) := 0;
     v_porcentaje_comision_coor NUMBER(16,2);

   PROCEDURE pr_inicializar_variables AS
      BEGIN

        v_tipo_pago := NULL;
        v_valor_contrato := 0;
        v_descuento := 0;
        v_remanente := 0;
        v_sin_comsiones := 0;
        v_dummy1 := 0;
        v_campus := NULL;
        v_campus_coordinador := NULL;
        v_porcentaje_comision := 0;
        v_porcentaje_comision_coor := 0;
        v_codigo_crossref := NULL;
        v_descuentos := 0;

    END pr_inicializar_variables;





  FUNCTION fn_fecha_filtro (p_pidm IN NUMBER, p_periodo IN VARCHAR2) RETURN DATE AS

    CURSOR c_fecha_busqueda (cp_pidm NUMBER, cp_periodo VARCHAR2) IS

      SELECT MAX(add1.tbraccd_effective_date) tbraccd_effective_date
        FROM tbraccd add1
       WHERE fn_monto_pago_inicial(cp_pidm,cp_periodo) <= (SELECT SUM(add3.tbraccd_amount)
                                                                            FROM tbraccd add3
                                                                           WHERE substr(add3.tbraccd_detail_code,1,2) IN ('BC','CC','LC','AS')
                                                                             AND add3.tbraccd_pidm = cp_pidm
                                                                             AND add3.tbraccd_term_code =cp_periodo )
        AND add1.tbraccd_pidm = cp_pidm
        AND add1.tbraccd_term_code = cp_periodo
        AND substr(add1.tbraccd_detail_code,1,2) IN ('BC','CC','LC','AS');

    BEGIN

      FOR i IN c_fecha_busqueda(p_pidm, p_periodo) loop
        RETURN i.tbraccd_effective_date;
      END loop;

      RETURN NULL;
  END fn_fecha_filtro;



   FUNCTION fn_comision (p_pidm IN NUMBER, p_periodo IN VARCHAR2)RETURN NUMBER AS

    BEGIN

      pr_inicializar_variables;

      FOR i IN c_valor_contrato (p_pidm, p_periodo) loop
        v_valor_contrato := i.tbraccd_amount;
      END loop;

      FOR i IN c_tipo_pago (p_pidm, p_periodo) loop
        v_tipo_pago := i.sgbstdn_rate_code;
      END loop;

      FOR i IN c_campus (p_pidm, p_periodo) loop
        v_campus := i.sovlcur_camp_code;
      END loop;

      FOR i IN c_porcentaje_comision (v_campus)loop
        v_porcentaje_comision := i.comision_ejecutivo;
      END loop;


      IF v_tipo_pago = 'CREDA' THEN
          for i in c_asignacion_descuentos(p_pidm, p_periodo, 'VC') loop
              IF i.tbbexpt_exemption_code not in ('3220','3221')  THEN
                  v_valor_contrato := v_valor_contrato - ((v_valor_contrato) * (i.porcentaje/100));
              END IF;
          END loop;

          for i in c_asignacion_descuentos(p_pidm, p_periodo, 'PI') loop
              IF i.tbbexpt_exemption_code not in ('3220','3221')  THEN
                  IF v_dummy1 = 0 THEN
                      v_valor_contrato := v_valor_contrato/6;
                      v_dummy1:= 1;
                  END IF;
                  v_valor_contrato := v_valor_contrato - ((v_valor_contrato) * (i.porcentaje/100));
              END IF;
          END loop;

          IF v_dummy1 = 0 THEN
              RETURN trunc((v_valor_contrato/6)* v_porcentaje_comision,2);
          END IF;

          RETURN trunc(v_valor_contrato * v_porcentaje_comision,2);


       ELSIF v_tipo_pago = 'CONTA' THEN

          for i in c_asignacion_descuentos(p_pidm, p_periodo, 'VC') loop
              IF i.tbbexpt_exemption_code not in ('3220','3221')  THEN
                  v_valor_contrato := v_valor_contrato - ((v_valor_contrato) * (i.porcentaje/100));
              END IF;
          END LOOP;

          v_valor_contrato := (v_valor_contrato-(v_valor_contrato)*.05);

          for i in c_asignacion_descuentos(p_pidm, p_periodo, 'CV') loop
              IF i.tbbexpt_exemption_code not in ('3220','3221')  THEN
                  v_valor_contrato := v_valor_contrato - ((v_valor_contrato) * (i.porcentaje/100));
              END IF;
          END LOOP;

              RETURN trunc(((v_valor_contrato)/6)* v_porcentaje_comision,2);

      END IF;

      RETURN 0;

    END fn_comision;




  FUNCTION fn_monto_pago_inicial (p_pidm IN NUMBER, p_periodo IN VARCHAR2)RETURN NUMBER AS
    BEGIN
      pr_inicializar_variables;

      FOR i IN c_valor_contrato (p_pidm, p_periodo) loop
        v_valor_contrato := i.tbraccd_amount;
      END loop;

      FOR i IN c_tipo_pago (p_pidm, p_periodo) loop
        v_tipo_pago := i.sgbstdn_rate_code;
      END loop;



      IF v_tipo_pago = 'CREDA' THEN



          for i in c_asignacion_descuentos(p_pidm, p_periodo, 'VC') loop

                  v_valor_contrato := v_valor_contrato - ((v_valor_contrato) * (i.porcentaje/100));
          END loop;

          for i in c_asignacion_descuentos(p_pidm, p_periodo, 'PI') loop
                  IF v_dummy1 = 0 THEN
                      v_valor_contrato := v_valor_contrato/6;
                      v_dummy1:= 1;
                  END IF;
                  v_valor_contrato := v_valor_contrato - ((v_valor_contrato) * (i.porcentaje/100));
          END loop;

          if v_dummy1 = 0 then

              RETURN trunc((v_valor_contrato/6));
          END IF;



          RETURN trunc(v_valor_contrato);


       ELSIF v_tipo_pago = 'CONTA' THEN


          for i in c_asignacion_descuentos(p_pidm, p_periodo, 'VC') loop
                  v_valor_contrato := v_valor_contrato - ((v_valor_contrato) * (i.porcentaje/100));
          END LOOP;

          for i in c_asignacion_descuentos(p_pidm, p_periodo, 'CV') loop
                  if v_dummy1 = 0 then
                      v_valor_contrato := (v_valor_contrato-(v_valor_contrato)* .05)/6;
                      v_dummy1:= 1;
                  END IF;
                  v_valor_contrato := v_valor_contrato - ((v_valor_contrato) * (i.porcentaje/100));

          END LOOP;

          if v_dummy1 = 0 then
              RETURN trunc(((v_valor_contrato-(v_valor_contrato)* .05)/6));
          END IF;

          RETURN trunc(v_valor_contrato);

      END IF;

      RETURN 0;

    END fn_monto_pago_inicial;



  FUNCTION fn_monto_descuentos (p_pidm IN NUMBER, p_periodo IN VARCHAR2)RETURN NUMBER AS

    BEGIN

      pr_inicializar_variables;

      FOR i IN c_valor_contrato (p_pidm, p_periodo) loop
        v_valor_contrato := i.tbraccd_amount;
      END loop;

      FOR i IN c_tipo_pago (p_pidm, p_periodo) loop
        v_tipo_pago := i.sgbstdn_rate_code;
      END loop;

      FOR i IN c_porcentaje_comision (v_campus)loop
        v_porcentaje_comision := i.comision_ejecutivo;
      END loop;


      IF v_tipo_pago = 'CREDA' THEN
          for i in c_asignacion_descuentos(p_pidm, p_periodo, 'VC') loop
              IF i.tbbexpt_exemption_code not in ('3220','3221') THEN
                  v_descuentos := v_descuentos + ((v_valor_contrato) * (i.porcentaje/100));
                  v_valor_contrato := v_valor_contrato - ((v_valor_contrato) * (i.porcentaje/100));
              END IF;
          END loop;

          for i in c_asignacion_descuentos(p_pidm, p_periodo, 'PI') loop
              IF i.tbbexpt_exemption_code not in ('3220','3221') THEN
                  IF v_dummy1 = 0 THEN
                      v_valor_contrato := v_valor_contrato/6;
                      v_dummy1:= 1;
                  END IF;
                  v_descuentos := v_descuentos + ((v_valor_contrato) * (i.porcentaje/100));
                  v_valor_contrato := v_valor_contrato - ((v_valor_contrato) * (i.porcentaje/100));
              END IF;
          END loop;

          RETURN trunc(v_descuentos,2);


       ELSIF v_tipo_pago = 'CONTA' THEN


          for i in c_asignacion_descuentos(p_pidm, p_periodo, 'VC') loop
              IF i.tbbexpt_exemption_code not in ('3220','3221') THEN
                  v_descuentos := v_descuentos + ((v_valor_contrato) * (i.porcentaje/100));
                  v_valor_contrato := v_valor_contrato - ((v_valor_contrato) * (i.porcentaje/100));
              END IF;
          END LOOP;


          v_descuentos := v_descuentos + ((v_valor_contrato)*.05);
          v_valor_contrato := (v_valor_contrato-(v_valor_contrato)*.05);


          for i in c_asignacion_descuentos(p_pidm, p_periodo, 'CV') loop
              IF i.tbbexpt_exemption_code not in ('3220','3221') THEN
                  v_descuentos := v_descuentos + ((v_valor_contrato) * (i.porcentaje/100));
                  v_valor_contrato := v_valor_contrato - ((v_valor_contrato) * (i.porcentaje/100));
              END IF;
          END LOOP;

          RETURN trunc(v_descuentos,2);

      END IF;
    RETURN 0;
    END fn_monto_descuentos;


    FUNCTION fn_comision_coordinador (p_pidm IN NUMBER, p_periodo IN VARCHAR2) RETURN NUMBER AS

        BEGIN

        pr_inicializar_variables;

        FOR i IN c_valor_contrato (p_pidm, p_periodo) loop
        v_valor_contrato := i.tbraccd_amount;
        END loop;

        FOR i IN c_tipo_pago (p_pidm, p_periodo) loop
          v_tipo_pago := i.sgbstdn_rate_code;
        END loop;

        FOR i IN c_campus (p_pidm, p_periodo) loop
          v_campus := i.sovlcur_camp_code;
        END loop;

        FOR i IN c_porcentaje_comision (v_campus)loop
          v_porcentaje_comision := i.comision_ejecutivo;
        END loop;

        FOR i IN c_campus (p_pidm, p_periodo) loop
            v_campus_coordinador := i.sovlcur_camp_code;
        END loop;

        FOR i IN c_porcentaje_comision_coor(v_campus_coordinador) loop
            v_porcentaje_comision_coor := i.comision_ejecutivo_coor;
        END loop;

      IF v_tipo_pago = 'CREDA' THEN
          for i in c_asignacion_descuentos(p_pidm, p_periodo, 'VC') loop
              IF i.tbbexpt_exemption_code not in ('3220','3221') THEN
                  v_valor_contrato := v_valor_contrato - ((v_valor_contrato) * (i.porcentaje/100));
              END IF;
          END loop;

          for i in c_asignacion_descuentos(p_pidm, p_periodo, 'PI') loop
              IF i.tbbexpt_exemption_code not in ('3220','3221') THEN
                  IF v_dummy1 = 0 THEN
                      v_valor_contrato := v_valor_contrato/6;
                      v_dummy1:= 1;
                  END IF;
                  v_valor_contrato := v_valor_contrato - ((v_valor_contrato) * (i.porcentaje/100));
              END IF;
          END loop;

          IF v_dummy1 = 0 THEN
              RETURN trunc(((v_valor_contrato/6)* v_porcentaje_comision ) * v_porcentaje_comision_coor,2);
          END IF;

          RETURN trunc((v_valor_contrato * v_porcentaje_comision) * v_porcentaje_comision_coor,2);


       ELSIF v_tipo_pago = 'CONTA' THEN

          for i in c_asignacion_descuentos(p_pidm, p_periodo, 'VC') loop
              IF i.tbbexpt_exemption_code not in ('3220','3221') THEN
                  v_valor_contrato := v_valor_contrato - ((v_valor_contrato) * (i.porcentaje/100));
              END IF;
          END loop;

          v_valor_contrato := (v_valor_contrato-(v_valor_contrato)*.05);

          for i in c_asignacion_descuentos(p_pidm, p_periodo, 'CV') loop
              IF i.tbbexpt_exemption_code not in ('3220','3221') THEN
                  v_valor_contrato := v_valor_contrato - ((v_valor_contrato) * (i.porcentaje/100));
              END IF;
          END loop;

              RETURN trunc((((v_valor_contrato)/6)* v_porcentaje_comision) * v_porcentaje_comision_coor,2);

      END IF;

      RETURN 0;


    END fn_comision_coordinador;





    PROCEDURE pr_comisiones(p_fecha_pago_ini IN DATE, p_fecha_pago_fin IN DATE, p_campus IN VARCHAR2, out_comisiones out sys_refcursor) AS
    v_consulta VARCHAR2(20000);



    BEGIN

        v_consulta :=  'SELECT DISTINCT
                               sovlcur_camp_code campus,
                               sovarol_id id_promotor,
                               sovarol_last_name||'' ''|| sovarol_first_name||'' ''||sovarol_mi nombre_promotor,
                               (select distinct puesto_promotor from pgocomis.asociacion_promotor where id_promotor = sovarol_id) puesto,
                               (select spriden_id from spriden where spriden_change_ind is null and spriden_pidm = srb.srbrecr_pidm) id_alumno,
                               (select spriden_last_name||'' ''||spriden_first_name||'' ''||spriden_mi  from spriden where spriden_change_ind is null and spriden_pidm = srb.srbrecr_pidm) nombre_alumno,
                               sovlcur_term_code, sgbstdn_pidm,
                               pc_pago_comisiones.fn_monto_pago_inicial(sgbstdn_pidm, sovlcur_term_code) pago_inicial,
                               pc_pago_comisiones.fn_monto_descuentos(sgbstdn_pidm, sovlcur_term_code) total_descuentos,
                               pc_pago_comisiones.fn_comision(sgbstdn_pidm, sovlcur_term_code) comision,
                               sovlcur_term_code periodo,
                               pc_pago_comisiones.fn_fecha_filtro(sgbstdn_pidm, sovlcur_term_code) fecha_de_pago,
                               (select distinct id_coordinador from pgocomis.asociacion_promotor where id_promotor = sovarol_id and relacion_activa = ''Y'') id_coordinador,
                               (select distinct apellidos_coordinador||'' ''||nombre_coordinador  from pgocomis.asociacion_promotor where id_promotor = sovarol_id and relacion_activa = ''Y'') nombre_coordinador,
                               pc_pago_comisiones.fn_comision_coordinador(sgbstdn_pidm, sovlcur_term_code) comision_coordinador,
															 (SELECT sum(tbraccd_amount) tbraccd_amount
                                    FROM tbraccd
                                   WHERE tbraccd_pidm = sgbstdn_pidm
                                     AND tbraccd_term_code =  sovlcur_term_code
                                     AND substr(tbraccd_detail_code,1,2) IN (''VC'',''VI'')) valor_contrato,
																(SELECT distinct sgbstdn_rate_code
                                    FROM sgbstdn
                                   WHERE sgbstdn_pidm = outss.sgbstdn_pidm
                                     AND sgbstdn_term_code_eff = sovlcur_term_code) tipo_pago,
                                    (select spr_out.SPRIDEN_ID 
                                            from spriden spr_out
                                                where spr_out.spriden_create_date = ( select max(spr_in.spriden_create_date)
                                                    from spriden spr_in
                                                        where spr_in.spriden_pidm = spr_out.spriden_pidm)
                                                        and spr_out.spriden_pidm = sorainf_arol_pidm
                                                        and ROWNUM = 1) ad_promotor,
                                    (select spr_out.SPRIDEN_ID 
                                            from spriden spr_out
                                                where spr_out.spriden_create_date = ( select max(spr_in.spriden_create_date)
                                                    from spriden spr_in
                                                        where spr_in.spriden_pidm = spr_out.spriden_pidm)
                                                        and spr_out.spriden_pidm = alias_t.pidm
                                                        and ROWNUM = 1) ad_coordinador
                          FROM sgbstdn outss
                          JOIN srbrecr srb on sgbstdn_pidm = srb.srbrecr_pidm and srbrecr_term_code = sgbstdn_term_code_eff
                          JOIN sorainf on srb.srbrecr_pidm = sorainf_pidm and srb.srbrecr_admin_seqno = sorainf_seqno and srb.srbrecr_term_code = sorainf_term_code
                          JOIN sovarol on sorainf_arol_pidm = sovarol_pidm and sorainf_radm_code = sovarol_radm_code
                          JOIN sovlcur on sgbstdn_pidm = sovlcur_pidm
                          left join pgocomis.asociacion_promotor alias_t on id_promotor = sovarol_id and relacion_activa = ''Y''
                          WHERE srb.srbrecr_admin_seqno = (SELECT MAX(b.srbrecr_admin_seqno) FROM srbrecr b
                                                           WHERE b.srbrecr_pidm = srb.srbrecr_pidm AND b.srbrecr_term_code = srb.srbrecr_term_code )
                           AND sovlcur_key_seqno = 99
                           AND sovlcur_priority_no = 1
                           AND sovlcur_cact_code = ''ACTIVE''
                           AND sovlcur_current_ind = ''Y''
                           AND sovlcur_active_ind = ''Y''
                           AND sovlcur_levl_code = ''LI''
                           AND substr(sovlcur_term_code,5,2) in (''18'',''20'',''38'',''40'')
                           AND (SELECT SUM(tbraccd_amount)
                                  FROM taismgr.tbraccd
                                 WHERE tbraccd_pidm = sgbstdn_pidm
                                   AND tbraccd_term_code = sovlcur_term_code
                                   AND substr(tbraccd_detail_code,1,2) in (''VC'',''VI'')) > 0
                           AND NOT EXISTS (SELECT * FROM pgocomis.autorizacion_comisiones
                                            WHERE id_promotor = sovarol_id
                                              AND id_alumno = (SELECT spriden_id FROM spriden WHERE spriden_change_ind IS NULL AND spriden_pidm = sgbstdn_pidm)
                                              AND periodo = sovlcur_term_code)
                           AND pc_pago_comisiones.fn_fecha_filtro(sgbstdn_pidm, sovlcur_term_code) >= :p_fecha_pago_ini
                           AND pc_pago_comisiones.fn_fecha_filtro(sgbstdn_pidm, sovlcur_term_code) <= :p_fecha_pago_fin
                           AND sovlcur_camp_code = :p_campus';

        OPEN out_comisiones FOR v_consulta USING p_fecha_pago_ini,p_fecha_pago_fin,p_campus;

    END pr_comisiones;

END pc_pago_comisiones;