package mx.edu.ebc.comisiones.comision.domain

import javax.persistence.*

@Entity
@Table(name = "AUTORIZACION_COMISIONES")
class PromoterAssociation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id
	@Column(name = "CAMPUS_CODE")
	String campusCode
	@Column(name = "CAMPUS_DESC")
	String campusDesc
	@Column(name = "ID_PROMOTOR")
	String idPromoter
	@Column(name = "NOMBRE_PROMOTOR")
	String promoterName
	@Column(name = "PUESTO_PROMOTOR")
	String job
	@Column(name = "CLAVE_EMP_PROMOTOR")
	String clavePromoter
	@Column(name = "ID_ALUMNO")
	String idStudent
	@Column(name = "NOMBRE_ALUMNO")
	String studentName
	@Column(name= "PAGO_INICIAL")
	String initPayment
	@Column(name="TOTAL_DESCUENTOS")
	BigDecimal descountTotal
	@Column(name="COMISION")
	String comission
	@Column(name="PERIODO")
	String period
	@Column(name="FECHA_DE_PAGO")
	Date paymenteDate
	@Column(name="AUTORIZADO_DIRECTOR")
	String directorAuthorize
	@Column(name="DATE_CREATED")
	Date dateCreated
	@Column(name="LAST_UPDATED")
	Date lastUpdated
	@Column(name="ID_COORDINADOR")
	String idCordinater
	@Column(name="NOMBRE_COORDINADOR")
	String cordinaterName
	@Column(name="COMISION_COORDINADOR")
	BigDecimal cordinaterComission
	@Column(name="FECHA_AUTORIZADO")
	Date authorizeDate
	@Column(name="CLAVE_EMP_COORDINADOR")
	String claveCoordinater
	@Column(name="USUARIO")
	String user


}