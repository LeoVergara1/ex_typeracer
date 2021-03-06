package mx.edu.ebc.comisiones.services

import groovy.transform.Memoized
import mx.edu.ebc.comisiones.comision.domain.Promoter
import mx.edu.ebc.comisiones.comision.domain.UserCampus
import mx.edu.ebc.comisiones.pojos.Person
import mx.edu.ebc.comisiones.pojos.PromoterCommand
import mx.edu.ebc.comisiones.comision.domain.PidmAndUserNamePK
import mx.edu.ebc.comisiones.pojos.PromoterCode
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import wslite.json.JSONObject
import org.springframework.beans.factory.annotation.Value
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import mx.edu.ebc.comisiones.comision.repo.PromoterRepository
import mx.edu.ebc.comisiones.comision.repo.UserCampusRepository
import mx.edu.ebc.comisiones.comision.repo.ProgramManagerRepository
import mx.edu.ebc.comisiones.network.NetworkService

@Service
class PromoterServiceImpl implements PromoterService{

  Logger logger = LoggerFactory.getLogger(PromoterServiceImpl.class)
  private static String ALREADY_EXISTS = "ERROR, this relation already exists"
  private static String RECR_CODE = "ERROR, this recruiter code already exists"
  private static String NOT_FOUND = "ERROR, this promoter does not exists"
  private static String MANAGER_NOT_FOUND = "ERROR, this manager does not exists"
  private static String DB_ERROR = "ERROR, data base error"
  private static String SUCCESS = "Operation succesfully achieved..."

  @Autowired
  Properties properties
	@Value('${url.apicomisiones}')
	String clientComissions
  @Value('${url.apibannercomisiones}')
  String clientApiBannerComission
  @Autowired
  PromoterRepository promoterRepository
  @Autowired
  ProgramManagerService programManagerService
  @Autowired
  ProgramManagerRepository programManagerRepository
  @Autowired
  UserCampusRepository userCampusRepository
  @Autowired
  PersonService personService
  @Value('#{${campus}}')
	Map<String, String> campus

  @Override
  Map createPromoter(Person person, String campusCode, String recrCode) {
    logger.info "Saving new promoter: $person.userName"
    if(promoterRepository.findOneById_RecrCode(recrCode) || programManagerService.findOneById_RecrCode(recrCode)){
      logger.info RECR_CODE
      return [message: RECR_CODE, created: false]
    }
    if (promoterRepository.findOneById_UserName(person.userName)){
      logger.info ALREADY_EXISTS
      return [message: ALREADY_EXISTS, created: false]
    }

    PidmAndUserNamePK id = new PidmAndUserNamePK(userName: person.userName, pidm: person.pidm, recrCode: recrCode)
    Promoter promoter = new Promoter(id: id)
    promoter.idPromoter = person.enrollment
    promoter.promoterName = person.firstName
    promoter.apellidosPromoter = person.lastName
    promoter.clavePromoter = person.adminId?.replace("AD", "").toInteger()
    promoter.jobPromoter = "PROMOTOR"
    promoter.relationActive = "N"
    promoter.lastUpdated = new Date()
    promoter.campusCode = campusCode
    promoter.campusDesc = campus[campusCode]
    promoter = promoterRepository.save(promoter)
    promoter ? {
      logger.info "Successfuly created..."
      [message: "Successfuly created...", created: true]
    }() : {
      logger.info DB_ERROR
      [message: DB_ERROR, created: false]
    }()
  }

  @Override
  Boolean deletePromoter(String userName) {
    logger.info "Deleting promoter: $userName"
    def promoter = promoterRepository.findOneById_UserName(userName)
    if(promoter){
      promoterRepository.delete(promoter)
      return true
    }
    false
  }

  @Memoized
  List<PromoterCode> getRecrCodeCatalogue() {
    logger.info "Retrieving all Promoters in stvrecr catalogue"
    def response = NetworkService.buildRequest(clientApiBannerComission){
      endpointUrl "/v1/api/promoters/recr_code_catalogue"
    }.execute()?.json
    response.inject([]) { listOfPromoterBanner, jsonObject ->
      listOfPromoterBanner << PromoterCommand.createPromoterBannerForRecrCodeFromJSONObject(jsonObject)
      listOfPromoterBanner
    }
  }

  @Override
  Boolean isRecruiterCodeAlreadyInUse(String recrCode) {
    logger.info "Verifying recrCode in api-comisiones: $recrCode"
    def response = NetworkService.buildRequest(clientComissions){
      endpointUrl "/v1/api/promoter/recruiter_code/occupied/$recrCode"
    }.execute()?.json
    logger.info "${response}"
    response ?
            new Boolean(response?.isValid)
            : null
  }

  List<Promoter> getPromotersOfManager(String username){
    def response = NetworkService.buildRequest(clientComissions){
      endpointUrl "/v1/api/manager/program/${username}/promoters/"
    }.execute()?.json
    response.collect{ promoter ->
      new Promoter(promoter)
    }
  }

  Boolean isAPromoterSavedWithRecrCode(String recrCode) {
    logger.info "Validating recrCode: $recrCode"
    (promoterRepository.findOneById_RecrCode(recrCode) ? true : false) ||
    (programManagerRepository.findOneById_RecrCode(recrCode) ? true : false)
  }

  List<Map> getCoordinates(){
    promoterRepository.findAll().collect(){ promoter ->
      [
        promoter: [id: promoter.id, programManager: parserProgrmaManager(promoter.programManager)],
        campuses: listOfCampuses(userCampusRepository.findByUserCampusPK_UserName(promoter.id.userName)),
        person: personService.findPersonByUsername(promoter.id.userName),
        associate: false
      ]
    }
  }

  def parserProgrmaManager(def programManager){
    [userName: programManager?.id?.userName]
  }

  def listOfCampuses(List<UserCampus> listCampus){
    listCampus?.collect(){ [code: it.userCampusPK.campusCode, description: campus["${it.userCampusPK.campusCode}"]]}
  }

  def createListOfEnrollmentPromoterByCampus(String campusCode){
    List<Promoter> promoters = promoterRepository.findAllByCampusCode(campusCode)
    promoters?.collect(){ promoter -> promoter.idPromoter }
  }

}
