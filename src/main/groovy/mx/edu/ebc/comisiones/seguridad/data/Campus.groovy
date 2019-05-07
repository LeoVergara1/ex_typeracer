
package mx.edu.ebc.comisiones.seguridad.data

import javax.persistence.*

@Entity
class Campus {
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