package mx.edu.ebc.comisiones.comision.domain

import javax.persistence.*

@Entity
@Table(name = 'USER_CAMPUS')
class UserCampus {

  @EmbeddedId
  UserCampusComposite userCampusPK
  @Column(name = 'DATE_CREATED')
  Date dateCreated
  @Column(name = 'LAST_UPDATED')
  Date lastUpdated
  @Column(name = 'ROLE_DESCRIPTION')
  String roleDescription 
  @Column(name = 'NAME_LONG')
  String nameLong

  @Override
  String toString(){
    "UserCampus - " +
            "userName: ${userCampusPK.userName}, " +
            "campus: ${userCampusPK.campusCode}, " +
            "pidm: $userCampusPK.pidm, " +
            "dateCreatet: $dateCreated, " +
            "lastUpdated: $lastUpdated"
  }
}