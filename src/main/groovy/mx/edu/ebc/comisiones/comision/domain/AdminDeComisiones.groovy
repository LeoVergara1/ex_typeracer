package mx.edu.ebc.comisiones.comision.domain

import javax.persistence.*

@Entity
@Table(name = "ADMIN_DE_COMISIONES")
class AdminDeComisiones {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id
	@Column(name = "COMISION_COORDINACION")
	Integer comisionCoordinacion
	@Column(name = "COMISION_EJECUTIVO")
	Integer comisionEjecutivo
	@Column(name = "COMISION_COORDINACION_FOURTY")
	Integer comisionCoordinacionFourty
	@Column(name = "COMISION_EJECUTIVO_FOURTY")
	Integer comisionEjecutivoFourty
	@Column(name = "CAMPUS_CODE")
	String campusCode
	@Column(name = "CAMPUS_DESC")
	String campusDesc
	@Column(name = "CUOTA_FIJA")
	Integer cuotaFija
	Date lastupdate

}