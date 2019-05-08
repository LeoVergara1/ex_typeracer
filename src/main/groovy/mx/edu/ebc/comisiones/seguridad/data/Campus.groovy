
package mx.edu.ebc.comisiones.seguridad.data

import javax.persistence.*

@Entity
@Table(name = "CAMPUS")
class Campus {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id
	String campusCode

}