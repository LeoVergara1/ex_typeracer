package mx.edu.ebc.comisiones.comision.domain

import javax.persistence.*

@Entity
@Table(name = "AUTORIZACION_COMISIONES")
class AuthorizationComission {
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_ID_AUTOR_COMISIONES")
  @SequenceGenerator(sequenceName = "SQ_ID_AUTOR_COMISIONES", allocationSize = 1, name = "SQ_ID_AUTOR_COMISIONES")
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
	Date dateCreated = new Date()
	@Column(name="LAST_UPDATED")
	Date lastUpdated = new Date()
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
 	double valorContratoReal = 0;
	@Column(name="PIDM")
 	Long pidm;
	@Column(name="COMMENTS")
 	String comment
	@Column(name="AD_COORDINADOR")
	String adCoordinador
	@Column(name="AD_PROMOTOR")
	String adPromotor
	@Column(name="DISCOUNT_PERCENT")
	String discountPercent
	@Column(name="USERNAME_MARKETING")
	String usernameMarketing
	@Column(name="STATUS_MARKETING")
	Boolean statusMarketing
	@Column(name="USERNAME_RECTOR")
	String usernameRector
	@Column(name="STATUS_RECTOR")
	Boolean statusRector


	AuthorizationComission(def json, username){
		this.user = username
		this.autorizadoDirector = json.autorizadoDirector
		this.campus = json.campus
		this.comision = json.comision
		this.comisionCoordinador = json.comisionCoordinador
		this.dateCreated = json.dateCreated
		this.fechaAutorizado = new Date()
		this.fechaDePago = new Date(Date.parse(json.fechaDePago.replace("-","/")))
		this.idAlumno = json.idAlumno
		this.idCoordinador = json.idCoordinador
		this.idPromotor = json.idPromotor
		this.nombreAlumno = json.nombreAlumno
		this.nombreCoordinador = json.nombreCoordinador
		this.nombrePromotor = json.nombrePromotor
		this.pagoInicial = json.pagoInicial
		this.periodo = json.periodo
		this.puesto = json.puesto
		this.totalDescuentos = json.totalDescuentos
		this.tipoPago = json.tipoPago
		this.valorContratoReal = json.valorContratoReal
		this.dateCreated = new Date()
		this.lastUpdated = new Date()
		this.adCoordinador = json.adCoordinador
		this.adPromotor = json.adPromotor
		this.pidm = json.pidm
		this.discountPercent = json.discountPercent
	}

	AuthorizationComission(){}


}