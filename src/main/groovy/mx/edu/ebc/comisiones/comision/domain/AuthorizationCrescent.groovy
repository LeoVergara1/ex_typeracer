package mx.edu.ebc.comisiones.comision.domain

import javax.persistence.*

@Entity
@Table(name = "AUTHORIZATION_CRESCENT")
class AuthorizationCrescent {
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_ID_AUTHORIZATION_CRESCENT")
  @SequenceGenerator(sequenceName = "SQ_ID_AUTHORIZATION_CRESCENT", allocationSize = 1, name = "SQ_ID_AUTHORIZATION_CRESCENT")
	@Id
	Integer id
	@Column(name = "CAMPUS")
	String campus
	@Column(name = "ID_PROMOTOR")
	String	idPromotor
	@Column(name = "NOMBRE_PROMOTOR")
	String nombrePromotor
	@Column(name = "PUESTO")
	String puesto
	@Column(name = "ID_ALUMNO")
	String idAlumno
	@Column(name = "NOMBRE_ALUMNO")
	String nombreAlumno
	@Column(name= "PAGO_INICIAL")
	String pagoInicial
	@Column(name="TOTAL_DESCUENTOS")
	BigDecimal totalDescuentos
	@Column(name="COMISION")
	String comision
	@Column(name="PERIODO")
	String periodo
	@Column(name="FECHA_DE_PAGO")
	Date fechaDePago
	@Column(name="AUTORIZADO_DIRECTOR")
	String autorizadoDirector
	@Column(name="DATE_CREATED")
	Date dateCreated
	@Column(name="LAST_UPDATED")
	Date lastUpdated
	@Column(name="ID_COORDINADOR")
	String idCoordinador
	@Column(name="NOMBRE_COORDINADOR")
	String nombreCoordinador
	@Column(name="COMISION_COORDINADOR")
	BigDecimal comisionCoordinador
	@Column(name="FECHA_AUTORIZADO")
	Date fechaAutorizado
	@Column(name="USUARIO")
	String user
	@Column(name="TIPO_PAGO")
 	String tipoPago;
	@Column(name="VALOR_CONTRATO_REAL")
 	double valorContratoReal;
	@Column(name="PIDM")
 	Long pidm;

}