package mx.edu.ebc.comisiones.services

import mx.edu.ebc.comisiones.pojos.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import wslite.json.JSONObject

@Service
class PromoterAsignmentServiceImpl implements PromoterAsignmentService{

  @Autowired
  RestConnectionService restConnectionService
  @Autowired
  Properties properties
  @Autowired
  PersonService personService
  @Autowired
  PromoterService promoterService
  @Autowired
  Map rolesMap
  @Autowired
  CampusService campusService

  //@Override
  //List<AssignablePromoter> findAllPromotersByCampus(String campusCode) {
  //  log.info "Consultando todos los promotores por campus en API-COMISIONES"
  //  Long roleId = Long.valueOf(properties.getProperty('promoterRoleId'))
  //  List<UserCampus> userCampusList = getAllUsersByCampusCode(campusCode)
  //  List<SecurityUser> securityUserList = getAllUsersByRoleId(roleId)
  //  List<String> intersection = userCampusList.userName.intersect(securityUserList.userName)
  //  List<Promoter> promoters = findPromotersByUserNames(intersection)
  //  List<AssignablePromoter> assignablePromoters = new ArrayList<AssignablePromoter>()
  //  intersection.each { promoter ->
  //    Person promoterBanner = personService.findPersonByUsername(promoter)
  //    assignablePromoters << new AssignablePromoter(
  //            lastName: promoterBanner?.lastName ?: '',
  //            firstName: promoterBanner?.firstName ?: '',
  //            pidm: userCampusList.find {it.userName == promoter}?.pidm,
  //            enrollment: promoterBanner?.enrollment ?: '',
  //            middleName: promoterBanner?.middleName ?: '',
  //            userName: promoter,
  //            campus: userCampusList.find {it.userName == promoter}?.description,
  //            campusCode: userCampusList.find{ it.userName == promoter}?.campusCode,
  //            managerPidm: promoters.find{it.userName == promoter}?.managerPidm,
  //            managerUserName: promoters.find{it.userName == promoter}?.managerUserName
  //    )
  //  }
  //  assignablePromoters.sort { it.userName }
  //}

  //@Override
  //List<UserCampus> getAllUsersByCampusCode(String campusCode) {
  //  log.info "Consultando todos los usuarios por campus en API-COMISIONES"
  //  List<UserCampus> userCampusList = new ArrayList<UserCampus>()
  //  List<CampusCommand> listCampuses = campusService.list()
  //  List<JSONObject> jsonObjects = restConnectionService.get(
  //          properties.getProperty("core.url.apicomisiones"), "/v1/api/users/campus/${campusCode}")
  //  jsonObjects.each { userCampus ->
  //    String description = listCampuses?.findAll{ code -> code.code == userCampus.campusCode }.description.join()
  //    UserCampusCommand userCampusCommand = UserCampusCommand.fromJsonObject(userCampus, description)
  //    userCampusList << userCampusCommand.getUserCampus()
  //  }
  //  userCampusList
  //}

  //@Override
  //List<SecurityUser> getAllUsersByRoleId(Long roleId) {
  //  log.info "Consultando todos los usuarios por rol en API-SEGURIDAD"
  //  List<SecurityUser> securityUserArrayList = new ArrayList<SecurityUser>()
  //  List<JSONObject> jsonObject = restConnectionService.get(
  //          properties.getProperty("core.url.apibannerseguridad"), "/v2/api/users/role/${roleId}")
  //  jsonObject?.each{ profile ->
  //    securityUserArrayList << new SecurityUserCommand(profile).fromJSONObjectToSecurityUser()
  //  }
  //  securityUserArrayList
  //}

 // @Override
 // List<Promoter> findPromotersByUserNames(List<String> userNamesList) {
 //   String userNameJoin = userNamesList.join(',')
 //   log.info "Consultando lista de promotores: $userNameJoin"
 //   List<Promoter> promoters = new ArrayList<Promoter>()
 //   List<JSONObject> jsonObjects = restConnectionService.get(
 //           properties.getProperty("core.url.apicomisiones"),
 //           "/v1/api/promoters/${userNameJoin}")
 //   jsonObjects.each { promoter ->
 //     promoters << new PromoterCommand(promoter).createPromoterFromJSONObject()
 //   }
 //   promoters
 // }

 // @Override
 // Person getManagerByUserName(String userName) {
 //   Person person = personService.findPersonByUsername(userName)
 //   if(person.userName){
 //     person = personService.setProfile(person,person.userName, properties.getProperty("security.portal.name"))
 //     person = personService.setCampuses(person)
 //   }
 //   person
 // }

  //@Override
  //Map getManagerAndPromotersFromMap(Map<String, String> formParameters) {
  //  String manager = formParameters['manager']
  //  List<PromoterCommand> assignablePromoters = []
  //  formParameters.each { key, value ->
  //    PromoterCommand promoter = new PromoterCommand()
  //    if (key.contains('user')) {
  //      def match = (key =~ /user\[([0-9]+)\]/)[0][1]
  //      promoter.userName = formParameters["user[$match]"]
  //      promoter.pidm = Long.valueOf(formParameters["pidm[$match]"]?:0L)
  //      promoter.check = formParameters["check[$match]"] ? true : false
  //      assignablePromoters << promoter
  //      log.info promoter
  //    }
  //  }
  //  [manager: manager, promoters: assignablePromoters]
  //}

  //@Override
  //void asignAndDeletePromotersForManager(String managerUserName, List<PromoterCommand> promoterChanges) {
  //  Person manager = getManagerByUserName(managerUserName)
  //  List<AssignablePromoter> filteredAssignablePromoters = findPromotersOfAManagerByPidm(manager.pidm, findAllPromotersByCampus(manager?.campuses[0]?.campusCode))
  //  asignListOfPromotersToManager(findPromotersForManagerAssignment(managerUserName, filteredAssignablePromoters, promoterChanges))
  //  unasignListOfPromotersToManager(findPromotersForManagerUnassignment(managerUserName, filteredAssignablePromoters, promoterChanges))
  //}

  //@Override
  //boolean newManagerPromoterRelation(PromoterManagerAssociation promoterManagerAssociation) {
  //  log.info "Saving new relation with manager: $promoterManagerAssociation.managerUserName and promoter $promoterManagerAssociation.promoterUserName"
  //  def postStatus = restConnectionService.put(
  //          properties.getProperty("core.url.apicomisiones"),
  //          "/v1/api/promoter/assign",
  //          [promoter_user_name: promoterManagerAssociation.promoterUserName,
  //           manager_user_name: promoterManagerAssociation.managerUserName])
  //  postStatus.statusCode == 200 ? {
  //    log.info "Successfully assigned..."
  //    true
  //  }() : {
  //    log.error("ERROR saving new realtion for manager: $promoterManagerAssociation.managerUserName and promoter: $promoterManagerAssociation.promoterUserName - statusCode: $postStatus.statusCode")
  //    false
  //  }()
  //}

  //@Override
  //boolean deleteManagerPromoterRelation(PromoterManagerAssociation promoterManagerAssociation) {
  //  log.info "Deleting relation with manager: $promoterManagerAssociation.managerUserName and promoter $promoterManagerAssociation.promoterUserName"
  //  def postStatus = restConnectionService.put(
  //          properties.getProperty("core.url.apicomisiones"),
  //          "/v1/api/promoter/unassign",
  //          [promoter_user_name: promoterManagerAssociation.promoterUserName,
  //           manager_user_name: promoterManagerAssociation.managerUserName])
  //  if (postStatus.statusCode == 200){
  //    log.info "Correctly deleted"
  //    return true
  //  }else if (postStatus.statusCode == 404)
  //    log.error "Error deleting manager: $promoterManagerAssociation.managerUserName does not exists - ServiceStatus: $postStatus.statusCode"
  //  else if (postStatus.statusCode == 409)
  //    log.error "Error deleting manager: $promoterManagerAssociation.managerUserName api-comisiones error - ServiceStatus: $postStatus.statusCode"
  //  false
  //}

  //@Override
  //List<AssignablePromoter> findPromotersOfAManagerByPidm(Long managerPidm, List<AssignablePromoter> assignablePromoters){
  //  assignablePromoters.findAll { promoter ->
  //    promoter.managerPidm == managerPidm
  //  }
  //}

  //@Override
  //List<PromoterManagerAssociation> findPromotersForManagerAssignment(String managerUserName, List<AssignablePromoter> assignablePromoters, List<PromoterCommand> checkedPromoters) {
  //  List<PromoterManagerAssociation> promoterManagerAssociations = []
  //  checkedPromoters.findAll {it.check}.each { promoter ->
  //    AssignablePromoter assignablePromoter = assignablePromoters.find { it.pidm == promoter.pidm }
  //    if (!assignablePromoter)
  //      promoterManagerAssociations << new PromoterManagerAssociation(
  //              promoterUserName: promoter.userName,
  //              promoterPidm: promoter.pidm,
  //              managerUserName: managerUserName)
  //  }
  //  promoterManagerAssociations
  //}

  //@Override
  //List<PromoterManagerAssociation> findPromotersForManagerUnassignment(String managerUserName, List<AssignablePromoter> assignablePromoters, List<PromoterCommand> checkedPromoters) {
  //  List<PromoterManagerAssociation> promoterManagerAssociations = []
  //  assignablePromoters.each { assignablePromoter ->
  //    PromoterCommand promoterCommand = checkedPromoters.find{it.pidm == assignablePromoter.pidm}
  //    if (promoterCommand && !promoterCommand?.check)
  //      promoterManagerAssociations << new PromoterManagerAssociation(
  //              promoterPidm: assignablePromoter.pidm,
  //              promoterUserName: assignablePromoter.userName,
  //              managerUserName: managerUserName)
  //  }
  //  promoterManagerAssociations
  //}

  //@Override
  //void asignListOfPromotersToManager(List<PromoterManagerAssociation> promoterManagerAssociations) {
  //  promoterManagerAssociations.each { promoterManagerAssociation ->
  //    newManagerPromoterRelation(promoterManagerAssociation) ?
  //            log.info("Manager-Promoter realtion created: $promoterManagerAssociation.managerUserName-$promoterManagerAssociation.promoterUserName") :
  //            log.error("Manager-Promoter NOT CREATED $promoterManagerAssociation.managerUserName-$promoterManagerAssociation.promoterUserName")
  //  }
  //}

  //@Override
  //void unasignListOfPromotersToManager(List<PromoterManagerAssociation> promoterManagerAssociations) {
  //  promoterManagerAssociations.each { promoterManagerAssociation ->
  //    deleteManagerPromoterRelation(promoterManagerAssociation)
  //  }
  //}

  //@Override
  //Map getCampusAndRoleDescriptionFromAManager(Person manager) {
  //  [campus: manager?.campuses[0]?.description.with {substring(0,size()-2)},
  //   roleDescription: manager?.profiles[0]?.description]
  //}

  @Override
  Integer isPromoterPidmAndRecrCodeValidForRegistration(Long promoterPidm, String recrCode) {
    log.info "Validating pidm: $promoterPidm and recrCode: $recrCode"
    isValidRadmCode(promoterPidm) ? isValidRecrCode(recrCode) ? 200 : 401 : 400
  }

  @Override
  Boolean isValidRadmCode(Long promoterPidm){
    findAllPromotersFromBannerByRadmCode(
            properties.getProperty("promotersRadmCode")
    ).find { promoterBanner ->
      promoterBanner.pidm == promoterPidm
    } ? true : {
      log.info "Error, not valid EMRECR code"
      false
    }()
  }

  @Override
  Boolean isValidRecrCode(String recrCode) {
    (promoterService.getRecrCodeCatalogue().find { promoterCode ->
      promoterCode.recrCode == recrCode
    } ? true : {
      log.info "Error, not in STVRECR catalogue"
      false
    }()) && !(promoterService.isRecruiterCodeAlreadyInUse(recrCode))
  }

  //@Override
  //List<PromoterCode> findAllPromotersFromBannerByRadmCode(String radmCode) {
  //  log.info "Finding all promoters in banner for radmCode: $radmCode"
  //  restConnectionService.get(
  //          properties.getProperty("core.url.apibannercomisiones"),
  //          "/v1/api/promoters/$radmCode"
  //  )?.collect{ jsonObject ->
  //    PromoterCommand.createPromoterBannerForRadmCodeFromJSONObject(jsonObject)
  //  } ?: []
  //}


}
