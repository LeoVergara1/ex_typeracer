package mx.edu.ebc.comisiones.comision.domain

import javax.persistence.*

@Entity
class Goal {
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_ID_CAMPAING")
  @SequenceGenerator(sequenceName = "SQ_ID_CAMPAING", allocationSize = 1, name = "SQ_ID_CAMPAING")
	@Id
	Integer id
	String status
	String campus
	@Column(name = "NUM_REGISTERS")
	Integer numRegisters
	@Column(name = "PERCENT_COMMISSION")
	Integer percentCommission
	String username
	@Column(name = "LAST_UPDATED")
	Date lastUpdated
	@Column(name = "DATE_UPDATED")
	Date dateCreated

	@ManyToOne
	Campaign campaing

}