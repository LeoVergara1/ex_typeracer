package mx.edu.ebc.comisiones.comision.domain

import javax.persistence.*

@Embeddable
class UserCampusComposite implements Serializable{
  @Column(name = "CAMPUS_CODE")
  String campusCode
  @Column(name = "USER_NAME")
  String userName
  @Column(name = "PIDM")
  Long pidm

  @Override
  boolean equals(Object o) {
    if (this == o) return true
    if (!(o instanceof UserCampusComposite)) return false
    UserCampusComposite that = (UserCampusComposite) o
    return Objects.equals(campusCode, that.campusCode) &&
            Objects.equals(userName, that.userName) &&
            Objects.equals(pidm, that.pidm)
  }

  @Override
  int hashCode() {
    return Objects.hash(userName, campusCode, pidm)
  }
}
