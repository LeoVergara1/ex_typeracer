package mx.edu.ebc.comisiones.comision.data

import javax.persistence.*

//@Entity
class AdminDeComisiones {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id
	Integer comisionCoordinacion
	Integer comisionEjecutivo
	String campusCode
	String campusDesc
	Integer cuotaFija
	Date lastupdate

}