
package mx.edu.ebc.comisiones.seguridad.domain

import javax.persistence.*

@Entity
@Table(name = "PORTALES")
class Portales {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "NIDROL")
	Integer nidRol
	@Column(name = "VDESCRIPCIONROL")
	String descriptionRol
	@Column(name = "NIDPORTAL")
	String nidRolPortal

}