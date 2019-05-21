package mx.edu.ebc.comisiones.services

import mx.edu.ebc.comisiones.enumeration.AdministratorAccess
import mx.edu.ebc.comisiones.pojos.SecurityUser

interface SecurityApiService {
  Boolean createNewUserInSecurityApi(String name, String email, String userName, AdministratorAccess accessLvl, String idEnrollement)
  def saveRoleforUser(String userName, Long roleId)
  SecurityUser findSecurityUserByUserName(String userName)
  Boolean assignRoleToSecurityUser(String userName, Long roleId)
}
