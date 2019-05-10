package mx.edu.ebc.comisiones.comision.domain

import javax.persistence.*

@Entity
@Table(name = "ASOCIACION_PROMOTOR")
class PromoterAssociation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id
	@Column(name = "ID_COORDINADOR")
	String idCoordinater
	@Column(name = "NOMBRE_COORDINADOR")
	String coordinaterName
	@Column(name = "APELLIDOS_COORDINADOR")
	String apellidosCoordinater
	@Column(name = "CLAVE_EMP_COORDINADOR")
	Integer claveCoordinater
	@Column(name = "CAMPUS_CODE")
	String campusCode
	@Column(name = "CAMPUS_DESC")
	String campusDesc
	@Column(name = "ID_PROMOTOR")
	String idPromoter
	@Column(name = "NOMBRE_PROMOTOR")
	String promoterName
	@Column(name = "APELLIDOS_PROMOTOR")
	String apellidosPromoter
	@Column(name = "CLAVE_EMP_PROMOTOR")
	Integer clavePromoter
	@Column(name = "PUESTO_PROMOTOR")
	String jobPromoter
	@Column(name = "RELACION_ACTIVA")
	String relationActive
	@Column(name = "USUARIO")
	String user
	@Column(name = "LASTUPDATE")
	String lastUpdated


}