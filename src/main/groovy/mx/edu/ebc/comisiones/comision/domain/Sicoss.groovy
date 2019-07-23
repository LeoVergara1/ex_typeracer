package mx.edu.ebc.comisiones.comision.domain

import javax.persistence.*

@Entity
@Table(name = "SICOSS")
class Sicoss {
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_ID_SICOSS")
  @SequenceGenerator(sequenceName = "SQ_ID_SICOSS", allocationSize = 1, name = "SQ_ID_SICOSS")
	@Id
	@Column(name = "ID_SICOSS")
	Integer idSicoss
	@Column(name = "CLAVE_EMPLOYEE")
	String claveEmployee
	@Column(name="DATE_CREATED")
	Date dateCreated
	@Column(name="LAST_UPDATED")
	Date lastUpdated
  @Column(name = "TYPE_PAYSHEET")
  String typePaysheet
  @Column(name = "CLAVE_PAYSHEET")
  String clavePaysheet
  @Column(name = "CONCEPT")
  String concept
  @Column(name = "DATE_MOVENMENT")
  Date dateMovenment
  @Column(name = "REFERENCE1")
  String reference1
  @Column(name = "REFERENCE2")
  String reference2
  @Column(name = "DATA_PAYSHEET")
  String dataPayhseet
  @Column(name = "SALARY")
  double salary
  @Column(name = "IMPORT")
  double importe
  @Column(name = "PAY_PERIOD")
  String payPeriod
  @Column(name = "CAMPUS")
  String campus
  @Column(name = "DATE_AUTHORIZED")
  Date dateAuthorized
  @Column(name = "TYPE_SICOSS")
  String typeSicoss
}