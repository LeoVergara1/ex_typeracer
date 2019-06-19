package mx.edu.ebc.comisiones.comision.domain

import javax.persistence.*

@Entity
class Campaign {
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_ID_CAMPAING")
  @SequenceGenerator(sequenceName = "SQ_ID_CAMPAING", allocationSize = 1, name = "SQ_ID_CAMPAING")
	@Id
	Integer id
	String status
	@Column(name = "INIT_DATE")
	Date initDate
	@Column(name = "END_DATE")
	Date endDate
	String name
	String clave
	@Column(name = "LAST_UPDATED")
	Date lastUpdated
	@Column(name = "DATE_UPDATED")
	Date dateCreated

	@OneToMany(mappedBy = "campaing")
	Set<Goal> goals = new ArrayList<Goal>()

}