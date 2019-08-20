package mx.edu.ebc.comisiones.services

import mx.edu.ebc.comisiones.pojos.RoleCommand
import mx.edu.ebc.comisiones.pojos.*
import org.springframework.beans.factory.annotation.Value
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import wslite.json.JSONObject
import mx.edu.ebc.comisiones.network.NetworkService
import mx.edu.ebc.comisiones.network.HTTPMethod

@Service
class PersonServiceImpl implements PersonService {

	  Logger logger = LoggerFactory.getLogger(PersonServiceImpl.class)

		@Value('${url.apibannercomisiones}')
		String clientApiBannerComissions
		@Value('${url.apibannerseguridad}')
		String clientApiBannerSeguridad
    @Value('${url.apicomisiones}')
	  String clientComissions
		@Value('${managerRoleID}')
		String managerRoleID
		@Value('${promoterRoleId}')
		String promoterRoleId
    @Value('${namePortal}')
    String namePortal
 		@Autowired
 		CampusService campusService
    @Autowired
    SecurityApiService securityApiService
    @Autowired
    PromoterAsignmentService promoterAsignmentService
    @Autowired
    ManagerService managerService
    @Autowired
    PromoterService promoterService
    @Autowired
    UserCampusService userCampusService

  Person findPersonByUsername(String username) {
    Person.fromJsonObject(
      NetworkService.buildRequest(clientApiBannerComissions){
          endpointUrl "/v1/api/person/${username}"
      }.execute().json
    )
  }

  @Override
  Person setProfile(Person person, String portalName){
 	 person.profiles =  findPersonByUsernameAndPortalName(person.userName, portalName)
    (!person.profiles) ? (person.profiles = []) : "Nothing"
    person
  }

  @Override
  Person setCampuses(Person person){
    person.campuses = campusService.getAllCampusesforUser("FutureCampus",person.userName)
    person
  }

  @Override
  def saveRolAndCampus(String username, String codeCampus, String roleCode, String recrCode){
    recrCode = recrCode.toUpperCase()
    logger.info "Inicia Proceso para asignar el rol"
    Person person = findPersonByUsername(username)
    Long roleId = Long.valueOf(roleCode)
    Integer statusRole
    Integer validation
    String message
    if (roleCode == promoterRoleId || roleCode == managerRoleID){
      logger.info "Promoter/Manager Role found, validation in Banner starts"
      validation = promoterAsignmentService.isPromoterPidmAndRecrCodeValidForRegistration(person.pidm, recrCode)
      if (validation != 200){
        logger.info "Promoter or manager not valid in banner... aborting user creation"
        return [statusRole: validation, message: "Promoter or manager not valid in banner... aborting user creation"]
      }
    }
    Boolean securityRoleAssignment = securityApiService.saveRoleforUser(username,roleId)
    if (securityRoleAssignment){
      def userCampus = userCampusService.created(codeCampus, username, person.pidm)
      if (userCampus) {
        if (roleCode == managerRoleID)
          managerService.createManager(username, person.pidm, recrCode)
        else if(roleCode == promoterRoleId)
          promoterService.createPromoter(person, codeCampus, recrCode)
        statusRole = 201
      }else
        statusRole = 404
    }else{
      logger.error "Ha ocurrido un error"
      statusRole = 412
    }

    [statusRole:statusRole, message: message]
  }

  @Override
  Map deleteCampusAndRolToPerson(String username, String codeCampus, String roleCode) {
    logger.info "Deleting user: $username"
    def statusCampus =  userCampusService.deleteByCodeCampusAndUserName(codeCampus, username)

    def statusRole = NetworkService.buildRequest(clientApiBannerSeguridad){
       endpointUrl "/v2/api/user/role/"
       method HTTPMethod.DELETE
       query([user_name: username, role_id: roleCode])
    }.execute()

    if(roleCode==managerRoleID){
      logger.info "Manager Role detected, deleting..."
      logger.info managerService.deleteManager(username) ? "Success" : "Error"
    }else if(roleCode==promoterRoleId){
      logger.info "Promoter role detected, deleting..."
      logger.info promoterService.deletePromoter(username) ? "Success" : "Error"
    }

    [statusRole:statusRole?.statusCode,
    statusCampus: statusCampus?.statusCode]
  }

  @Override
  List<Profile> findPersonByUsernameAndPortalName(String userName, String portalName){
    def jsonObject = NetworkService.buildRequest(clientApiBannerSeguridad){
      endpointUrl "/v2/api/user/role/${userName}/${portalName}"
    }.execute()?.json
   jsonObject?.collect{ profile ->
      Profile.fromJsonObject(profile)
    }
  }

  def getMenusToPerson(String userName){
    NetworkService.buildRequest(clientApiBannerSeguridad){
      endpointUrl "/v2/api/user/profile/${userName}/${owner.namePortal}"
    }.execute()?.json?.accessProfile.collect(){ menu ->
      [
        shortName: menu.shortName,
        submenus: menu.accessProfile.collect(){ [ url: it.url, shortName: it.shortName] }
      ]
    }
  }

}