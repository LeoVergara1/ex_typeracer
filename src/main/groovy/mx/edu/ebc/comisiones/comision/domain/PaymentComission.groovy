package mx.edu.ebc.comisiones.comision.domain

import javax.persistence.*

@Entity
@NamedStoredProcedureQueries([
    @NamedStoredProcedureQuery(
        name = "java_procedure_name",
        procedureName = "genpub.pc_comisiones_li.pr_comisiones",
        parameters = [
          @StoredProcedureParameter(mode=ParameterMode.IN, name="p_fecha_pago_ini", type=Date.class),
          @StoredProcedureParameter(mode=ParameterMode.IN, name="p_fecha_pago_fin", type=Date.class),
          @StoredProcedureParameter(mode=ParameterMode.IN, name="p_campus", type=String.class),
          @StoredProcedureParameter(type = void.class, mode = ParameterMode.REF_CURSOR)
		])
])
class PaymentComission implements Serializable {

	@Id
	@Column(name = "id_promotor")
	Long id

}