package mx.edu.ebc.comisiones.comision.domain

import javax.persistence.EmbeddedId
import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.JoinColumns
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "PROMOTER")
class Promoter {

  @EmbeddedId
  PidmAndUserNamePK id

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
