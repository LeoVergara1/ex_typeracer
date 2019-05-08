
package mx.edu.ebc.comisiones.seguridad.domain

import javax.persistence.*

@Entity
@Table(name = "BITACORAS")
class Bitacora {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "NIDBITACORA")
	Integer id
	Integer nnumeroempleado
	Date dfecha
	String vnombreusuario
	String vtipo
	String vdescripcion
	String vnombreportal

}