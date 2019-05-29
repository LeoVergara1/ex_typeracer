package mx.edu.ebc.comisiones.services

import mx.edu.ebc.comisiones.comision.domain.UserCampus

interface UserCampusService {
	UserCampus created(String campusCode, String userName, Long pidm)
	UserCampus findByCampusCodeAndUserName(String codeCampus, String userName)
}