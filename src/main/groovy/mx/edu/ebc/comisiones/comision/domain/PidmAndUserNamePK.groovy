package mx.edu.ebc.comisiones.comision.domain

import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
class PidmAndUserNamePK implements Serializable {

  @Column(name = "PIDM", nullable = false)
  Long pidm
  @Column(name = "USER_NAME", nullable = false)
  String userName
  @Column(name = "RECR_CODE", nullable = false)
  String recrCode

}
