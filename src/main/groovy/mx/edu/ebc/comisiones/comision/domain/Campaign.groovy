package mx.edu.ebc.comisiones.comision.domain

import javax.persistence.*
import com.fasterxml.jackson.annotation.JsonManagedReference
import com.fasterxml.jackson.annotation.JsonBackReference

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
	String year
	String clave
	@Column(name = "LAST_UPDATED")
	Date lastUpdated = new Date()
	@Column(name = "DATE_CREATED")
	Date dateCreated = new Date()

	@JsonBackReference
	@OneToMany(mappedBy = "campaing", fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
	Set<Goal> goals = new ArrayList<Goal>()

}