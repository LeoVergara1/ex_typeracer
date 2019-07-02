package mx.edu.ebc.comisiones.services

import mx.edu.ebc.comisiones.enumeration.AdministratorAccess
import mx.edu.ebc.comisiones.pojos.Person
import mx.edu.ebc.comisiones.pojos.SecurityUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.beans.factory.annotation.Value
import wslite.json.JSONObject
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import mx.edu.ebc.comisiones.network.NetworkService
import mx.edu.ebc.comisiones.network.HTTPMethod

@Service
class SecurityApiServiceImpl implements SecurityApiService{

	Logger logger = LoggerFactory.getLogger(SecurityApiServiceImpl.class)

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
    logger.info "Se inicia el proceso para registrar un nuevo usuario mediante API-SEGURIDAD username: $userName, idEnrollment: $idEnrollement"
    def response = NetworkService.buildRequest(clientApiBannerSeguridad){
       endpointUrl "/v2/api/user/"
       method HTTPMethod.POST
       query([
                    name: name,
                    email: email,
                    user_name: userName,
                    administrator: accessLvl.value.toString(),
                    id_enrollment: idEnrollement
            ])
    }.execute()
    response instanceof Integer ? (statusCode = response) : (statusCode = response?.statusCode)
    if ([400,409,412].find { it == statusCode }) {
      logger.error "Error al crear un nuevo usuario en api-seguridad service statusCode: $statusCode"
      return false
    }
    logger.info "Usuario creado correctamente"
    true
  }

  @Override
  def saveRoleforUser(String userName, Long roleId) {
    logger.info "Comienza el proceso de asignacion de rol $roleId a usuario $userName"
    Boolean roleAssignment
    Boolean userCreation
    SecurityUser securityUser = findSecurityUserByUserName(userName)
    if(!securityUser){
      logger.info "El usuario no se encuentra en aplicación de seguridad..."
      Person person = personService.findPersonByUsername(userName)
      if (!person){
        logger.error "FATAL, no se pudo encontrar usuario $userName en Banner"
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
      logger.info "$userName encontrado..."
    roleAssignment = assignRoleToSecurityUser(userName, roleId)
    roleAssignment ?
            logger.info("Proceso de asignacion de rol completado exitosamente") :
            logger.error("Ocurrió un error durante la signacion de rol")
    roleAssignment
  }

  @Override
  SecurityUser findSecurityUserByUserName(String userName) {
    logger.info "Buscando usuario en api-seguridad"
    def jsonObject = NetworkService.buildRequest(clientApiBannerSeguridad){
      endpointUrl "/v2/api/user/$userName"
    }.execute()?.json
    jsonObject ?
            new SecurityUser(jsonObject).fromJSONObjectToSecurityUser() :
            null
  }

  @Override
  Boolean assignRoleToSecurityUser(String userName, Long roleId) {
    logger.info "Asignando rol $roleId a usuario $userName"
    Integer statusCode
    def response = NetworkService.buildRequest(clientApiBannerSeguridad){
      endpointUrl "/v2/api/user/role/"
      method HTTPMethod.POST
      query([user_name: userName, role_id: roleId])
    }.execute()
    if (response instanceof Integer)
      statusCode = response
    else
      statusCode = response.statusCode
    if (statusCode == 409){
      logger.error "Error al asignar rol a usuario: $userName"
      return false
    }
    if (statusCode == 412){
      logger.error "Error precondicion fallida: $userName"
      return false
    }
    logger.info "Rol $roleId asociado correctamente a $userName"
    return true
  }

}
