package mx.edu.ebc.comisiones.services

import groovy.util.logging.Log4j
import mx.edu.ebc.comisiones.pojos.RoleCommand
import mx.edu.ebc.comisiones.pojos.*
import org.springframework.beans.factory.annotation.Value
//import mx.edu.ebc.api.service.ManagerService
//import mx.edu.ebc.api.service.PromoterAsignmentService
//import mx.edu.ebc.api.service.PromoterService
//import mx.edu.ebc.api.service.RestConnectionService
//import mx.edu.ebc.api.service.PersonService
//import mx.edu.ebc.api.service.ProfileService
//import mx.edu.ebc.api.service.SecurityApiService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import wslite.json.JSONObject

@Service
class PersonServiceImpl implements PersonService {

 		@Autowired
 		RestConnectionService restConnectionService
		@Value('${url.apibannercomisiones}')
		String clientApiBannerComissions
		@Value('${url.apibannerseguridad}')
		String clientApiBannerSeguridad
 // @Autowired
 // Properties properties
 // @Autowired
 // ProfileService profileService
 		@Autowired
 		CampusService campusService
 // @Autowired
 // SecurityApiService securityApiService
 // @Autowired
 // PromoterAsignmentService promoterAsignmentService
 // @Autowired
 // PromoterService promoterService
 // @Autowired
 // ManagerService managerService
 // @Autowired
 // Map rolesMap

 // @Override
 // JSONObject findByUsername(String username) {
 // }

 // @Override
  Person findPersonByUsername(String username) {
		Person.fromJsonObject(restConnectionService.get(clientApiBannerComissions, "/v1/api/person/${username}"))
  }

@Override
 Person setProfile(Person person, String username, String portalName){
	 def p = findPersonByUsernameAndPortalName(username, portalName)
	 println "H"*100
	 println p
	 person.profiles =  findPersonByUsernameAndPortalName(username, portalName)
	 println "R"*100
	 println person.profiles
   person
 }

  @Override
  Person setCampuses(Person person){
   	List<UserCampus> campuses= campusService.getAllCampusesforUser("FutureCampus",person.userName)
    campuses?.each{ campus ->
      person.campuses << campus
    }
    person
  }

 // @Override
 // List<RoleCommand> getRoles(String portalName){
 //   List<RoleCommand> roles = []
 //   List<JSONObject> jsonObject = restConnectionService.get(properties.getProperty("core.url.apibannerseguridad"), "/v2/api/application/roles/${portalName}")
 //   jsonObject?.each{role ->
 //     roles << RoleCommand.convertJSONtoRoles(role)
 //   }
 //   roles
 // }

 // @Override
 // def saveRolAndCampus(String username, String codeCampus, String roleCode, String recrCode){
 //   recrCode = recrCode.toUpperCase()
 //   log.info "Inicia Proceso para asignar el rol"
 //   Long pidm = findPersonByUsername(username).pidm
 //   Long roleId = Long.valueOf(roleCode)
 //   Integer statusRole
 //   Integer validation
 //   if (roleCode == properties.getProperty("promoterRoleId") || roleCode == properties.getProperty("managerRoleID")){
 //     log.info "Promoter/Manager Role found, validation in Banner starts"
 //     validation = promoterAsignmentService.isPromoterPidmAndRecrCodeValidForRegistration(pidm, recrCode)
 //     if (validation != 200){
 //       log.info "Promoter or manager not valid in banner... aborting user creation"
 //       return [statusRole: validation]
 //     }
 //   }
 //   Boolean securityRoleAssignment = securityApiService.saveRoleforUser(username,roleId)
 //   if (securityRoleAssignment){
 //     def statusCampus = restConnectionService.post(properties.getProperty("core.url.apicomisiones"),"/v1/api/user/", [campus_code: codeCampus, user_name:username, pidm:pidm])
 //     if (statusCampus.statusCode == 201) {
 //       if (roleCode == properties.getProperty("managerRoleID"))
 //         managerService.createManager(username, pidm, recrCode)
 //       else if(roleCode == properties.getProperty("promoterRoleId"))
 //         promoterService.createPromoter(username, pidm, recrCode)
 //       statusRole = 201
 //     }else
 //       statusRole = 404
 //   }else{
 //     log.error "Ha ocurrido un error"
 //     statusRole = 412
 //   }

 //   [statusRole:statusRole]
 // }

 // @Override
 // Map deleteCampusAndRolToPerson(String username, String codeCampus, String roleCode) {
 //   log.info "Deleting user: $username"
 //  def statusCampus =  restConnectionService.delete(properties.getProperty("core.url.apicomisiones"),"/v1/api/user/", [code_campus: codeCampus, user_name:username])

 //   def statusRole =  restConnectionService.delete(properties.getProperty("core.url.apibannerseguridad"),"/v2/api/user/role/", [user_name: username, role_id: roleCode])
 //   if(roleCode==properties.getProperty("managerRoleID")){
 //     log.info "Manager Role detected, deleting..."
 //     log.info managerService.deleteManager(username) ? "Success" : "Error"
 //   }else if(roleCode==properties.getProperty("promoterRoleId")){
 //     log.info "Promoter role detected, deleting..."
 //     log.info promoterService.deletePromoter(username) ? "Success" : "Error"
 //   }

 //  [statusRole:statusRole?.statusCode,
 //  statusCampus: statusCampus?.statusCode]
 // }


 // Map getRolesFromProperties(){
 //   def rolSession = properties.getProperty("roles")
 //   Map mapRol = [:]
 //   rolSession.split(",").each {  param ->
 //     def nameAndValue = param.split(":")
 //     mapRol[nameAndValue[0].toInteger()] = nameAndValue[1]
 //   }
 //   mapRol
 // }

  @Override
  List<Profile> findPersonByUsernameAndPortalName(String userName, String portalName){
    List<JSONObject> jsonObject = restConnectionService.get(clientApiBannerSeguridad, "/v2/api/user/role/${userName}/${portalName}")
		println "*"*100
		println jsonObject
    jsonObject?.collect{ profile ->
      Profile.fromJsonObject(profile)
    }
  }

}