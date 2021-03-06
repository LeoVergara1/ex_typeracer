package mx.edu.ebc.comisiones.comision.domain

import javax.persistence.*
import com.fasterxml.jackson.annotation.JsonManagedReference
import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonIdentityInfo
import com.fasterxml.jackson.annotation.ObjectIdGenerators

@JsonIdentityInfo(
  generator = ObjectIdGenerators.PropertyGenerator.class,
  property = "id")
@Entity
class Campaign {
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_ID_CAMPAIGN")
  @SequenceGenerator(sequenceName = "SQ_ID_CAMPAIGN", allocationSize = 1, name = "SQ_ID_CAMPAIGN")
	@Id
	Integer id
	String status
	@Column(name = "INIT_DATE")
	Date initDate
	@Column(name = "END_DATE")
	Date endDate
	String name
	String year
  Integer period
	@Column(name = "STATUS_SICOSS")
  String statusSicoss 
	@Column(name = "USERNAME_PROCESS")
  String usernameSicoss 
	@Column(name = "LAST_UPDATED")
	Date lastUpdated = new Date()
	@Column(name = "DATE_CREATED")
	Date dateCreated = new Date()
}