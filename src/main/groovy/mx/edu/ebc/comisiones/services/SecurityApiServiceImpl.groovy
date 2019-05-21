package mx.edu.ebc.comisiones.services

import mx.edu.ebc.comisiones.enumeration.AdministratorAccess
import mx.edu.ebc.comisiones.pojos.Person
import mx.edu.ebc.comisiones.pojos.SecurityUser
import mx.edu.ebc.comisiones.services.RestConnectionService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.beans.factory.annotation.Value
import wslite.json.JSONObject

@Service
class SecurityApiServiceImpl implements SecurityApiService{

  @Autowired
  RestConnectionService restConnectionService
  @Autowired
  PersonService personService
	@Value('${url.apibannercomisiones}')
	String clientApiBannerComissions
	@Value('${url.apibannerseguridad}')
	String clientApiBannerSeguridad

  @Override
  Boolean createNewUserInSecurityApi(String name, String email, String userName, AdministratorAccess accessLvl, String idEnrollement) {
    "Creando usuario $name con nombre de usuario $userName"
    Integer statusCode
    log.info "Se inicia el proceso para registrar un nuevo usuario mediante API-SEGURIDAD username: $userName, idEnrollment: $idEnrollement"
    def response = restConnectionService.post(
            clientApiBannerSegurida,
            "/v2/api/user/",
            [
                    name: name,
                    email: email,
                    user_name: userName,
                    administrator: accessLvl.value.toString(),
                    id_enrollment: idEnrollement
            ])
    response instanceof Integer ? (statusCode = response) : (statusCode = response?.statusCode)
    if ([400,409,412].find { it == statusCode }) {
      log.error "Error al crear un nuevo usuario en api-seguridad service statusCode: $statusCode"
      return false
    }
    log.info "Usuario creado correctamente"
    true
  }

  @Override
  def saveRoleforUser(String userName, Long roleId) {
    log.info "Comienza el proceso de asignacion de rol $roleId a usuario $userName"
    Boolean roleAssignment
    Boolean userCreation
    SecurityUser securityUser = findSecurityUserByUserName(userName)
    if(!securityUser){
      log.info "El usuario no se encuentra en aplicación de seguridad..."
      Person person = personService.findPersonByUsername(userName)
      if (!person){
        log.error "FATAL, no se pudo encontrar usuario $userName en Banner"
        return false
      }
      userCreation = createNewUserInSecurityApi(
              person?.bannerName,
              person?.email,
              person?.userName,
              AdministratorAccess.NO_ACCESS,
              person?.enrollment)
      if (!userCreation) return false
    } else
      log.info "$userName encontrado..."
    roleAssignment = assignRoleToSecurityUser(userName, roleId)
    roleAssignment ?
            log.info("Proceso de asignacion de rol completado exitosamente") :
            log.error("Ocurrió un error durante la signacion de rol")
    roleAssignment
  }

  @Override
  SecurityUser findSecurityUserByUserName(String userName) {
    log.info "Buscando usuario en api-seguridad"
    JSONObject jsonObject = restConnectionService.get(
            clientApiBannerSegurida,
            "/v2/api/user/$userName")
    jsonObject ?
            new SecurityUser(jsonObject).fromJSONObjectToSecurityUser() :
            null
  }

  @Override
  Boolean assignRoleToSecurityUser(String userName, Long roleId) {
    log.info "Asignando rol $roleId a usuario $userName"
    Integer statusCode
    def response =  restConnectionService.post(clientApiBannerSeguridad,"/v2/api/user/role/", [user_name: userName, role_id: roleId])
    if (response instanceof Integer)
      statusCode = response
    else
      statusCode = response.statusCode
    if (statusCode == 409){
      log.error "Error al asignar rol a usuario: $userName"
      return false
    }
    if (statusCode == 412){
      log.error "Error precondicion fallida: $userName"
      return false
    }
    log.info "Rol $roleId asociado correctamente a $userName"
    return true
  }

}
