package mx.edu.ebc.comisiones.comision.domain

import javax.persistence.EmbeddedId
import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.JoinColumns
import javax.persistence.ManyToOne
import javax.persistence.Table
import javax.persistence.Column
import com.fasterxml.jackson.annotation.JsonIdentityInfo
import com.fasterxml.jackson.annotation.ObjectIdGenerators

@JsonIdentityInfo(
  generator = ObjectIdGenerators.PropertyGenerator.class,
  property = "id")
@Entity
@Table(name = "ASOCIACION_PROMOTOR")
class Promoter {

  @EmbeddedId
  PidmAndUserNamePK id
  @Column(name = "ID_COORDINADOR")
	String idCoordinater
	@Column(name = "NOMBRE_COORDINADOR")
	String coordinaterName
	@Column(name = "APELLIDOS_COORDINADOR")
	String apellidosCoordinater
	@Column(name = "CLAVE_EMP_COORDINADOR")
	Integer claveCoordinater
	@Column(name = "CAMPUS_CODE")
	String campusCode
	@Column(name = "CAMPUS_DESC")
	String campusDesc
	@Column(name = "ID_PROMOTOR")
	String idPromoter
	@Column(name = "NOMBRE_PROMOTOR")
	String promoterName
	@Column(name = "APELLIDOS_PROMOTOR")
	String apellidosPromoter
	@Column(name = "CLAVE_EMP_PROMOTOR")
	Integer clavePromoter
	@Column(name = "PUESTO_PROMOTOR")
	String jobPromoter
	@Column(name = "RELACION_ACTIVA")
	String relationActive
	@Column(name = "USUARIO")
	String user
	@Column(name = "LAST_UPDATED")
	Date lastUpdated

  @ManyToOne
  @JoinColumns([
          @JoinColumn(name = "PIDM_MANAGER", referencedColumnName = "PIDM"),
          @JoinColumn(name = "USER_NAME_MANAGER", referencedColumnName = "USER_NAME"),
          @JoinColumn(name = "RECR_CODE_MANAGER", referencedColumnName = "RECR_CODE")
  ])
  ProgramManager programManager

  @Override
  String toString(){
    "Promoter - pidm: $id.pidm, userName: $id.userName, programManager: ${programManager?.id?.userName}"
  }
}
