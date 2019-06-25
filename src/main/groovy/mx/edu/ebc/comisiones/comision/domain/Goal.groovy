package mx.edu.ebc.comisiones.comision.domain

import javax.persistence.*

@Entity
class Goal {
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_ID_GOAL")
  @SequenceGenerator(sequenceName = "SQ_ID_GOAL", allocationSize = 1, name = "SQ_ID_GOAL")
	@Id
	Integer id
	String status
	String campus
	String type
	@Column(name = "NUM_REGISTERS")
	Integer numRegisters
	@Column(name = "PERCENT_COMMISSION")
	float percentCommission = 0
	String username
	@Column(name = "LAST_UPDATED")
	Date lastUpdated = new Date()
	@Column(name = "DATE_CREATED")
	Date dateCreated = new Date()

	@ManyToOne
	Campaign campaign

}