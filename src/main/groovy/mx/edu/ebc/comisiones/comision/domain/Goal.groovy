package mx.edu.ebc.comisiones.comision.domain

import javax.persistence.*
import com.fasterxml.jackson.annotation.JsonManagedReference
import com.fasterxml.jackson.annotation.JsonIdentityInfo
import com.fasterxml.jackson.annotation.ObjectIdGenerators

@JsonIdentityInfo(
  generator = ObjectIdGenerators.PropertyGenerator.class,
  property = "id")
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
	Integer numRegisters = 0
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