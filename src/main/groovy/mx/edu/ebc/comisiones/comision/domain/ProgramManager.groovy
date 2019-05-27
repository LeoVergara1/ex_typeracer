package mx.edu.ebc.comisiones.comision.domain

import javax.persistence.CascadeType
import javax.persistence.EmbeddedId
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name = "PROGRAM_MANAGER")
class ProgramManager {

  @EmbeddedId
  PidmAndUserNamePK id
  @OneToMany(mappedBy = "programManager", fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
  Set<Promoter> promoters = new ArrayList<Promoter>()

  @Override
  String toString(){
    "ProgramManager - " +
            "pidm: $id.pidm, " +
            "userName: $id.userName, " +
            "recrCode: $id.recrCode"
  }
}
