package mx.edu.ebc.comisiones.comision.domain

import javax.persistence.*

@Entity
@Table(name = "V_SPRIDEN")
class Person {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "SPRIDEN_PIDM")
	Integer id
	@Column(name = "SPRIDEN_ID")
	String idBanner
	@Column(name = "SPRIDEN_LAST_NAME")
	String lastName
	@Column(name = "SPRIDEN_FIRST_NAME")
	String nameBanner
	@Column(name = "SPRIDEN_MI")
	String secondNameBanner
}