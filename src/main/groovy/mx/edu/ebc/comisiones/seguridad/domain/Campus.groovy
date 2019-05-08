
package mx.edu.ebc.comisiones.seguridad.domain

import javax.persistence.*

@Entity
@Table(name = "CAMPUS")
class Campus {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id
	@Column(name = "CAMPUS_CODE")
	String campusCode

}