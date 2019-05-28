package mx.edu.ebc.comisiones.services

import groovy.transform.Memoized
import mx.edu.ebc.comisiones.comision.domain.Promoter
import mx.edu.ebc.comisiones.comision.domain.PidmAndUserNamePK
import mx.edu.ebc.comisiones.pojos.PromoterCode
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import wslite.json.JSONObject
import org.springframework.beans.factory.annotation.Value
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import mx.edu.ebc.comisiones.comision.repo.PromoterRepository
import mx.edu.ebc.comisiones.comision.repo.ProgramManagerRepository


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
  RestConnectionService restConnectionService
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

  @Override
  Map createPromoter(String userName, Long pidm, String recrCode) {
    logger.info "Saving new promoter: $userName"
    if(promoterRepository.findOneById_RecrCode(recrCode) || programManagerService.findOneById_RecrCode(recrCode)){
      logger.info RECR_CODE
      return [message: RECR_CODE, created: false]
    }
    if (promoterRepository.findOneById_UserName(userName)){
      logger.info ALREADY_EXISTS
      return [message: ALREADY_EXISTS, created: false]
    }

    PidmAndUserNamePK id = new PidmAndUserNamePK(userName: userName, pidm: pidm, recrCode: recrCode)
    Promoter promoter = new Promoter(id: id)
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
    def postStatus = restConnectionService.delete(
            clientComissions,
            "/v1/api/promoter/",
            [promoter_user_name: userName])
    postStatus?.statusCode == 200 ? {
      logger.info "Successfully deleted..."
      true
    }() : {
      logger.error("ERROR deleting promoter: $userName, statusCode: ${postStatus?.statusCode}")
      false
    }()
  }

  @Memoized
  List<PromoterCode> getRecrCodeCatalogue() {
    logger.info "Retrieving all Promoters in stvrecr catalogue"
    List<JSONObject> response = restConnectionService.get(
            clientApiBannerComission,
            "/v1/api/promoters/recr_code_catalogue"
    )
    response.inject([]) { listOfPromoterBanner, jsonObject ->
      listOfPromoterBanner << Promoter.createPromoterBannerForRecrCodeFromJSONObject(jsonObject)
      listOfPromoterBanner
    }
  }

  @Override
  Boolean isRecruiterCodeAlreadyInUse(String recrCode) {
    logger.info "Verifying recrCode in api-comisiones: $recrCode"
    JSONObject response = restConnectionService.get(
            clientComissions,
            "/v1/api/promoter/recruiter_code/occupied/$recrCode")
    logger.info "${response}"
    response ?
            new Boolean(response?.isValid)
            : null
  }

  List<Promoter> getPromotersOfManager(String username){
    def response = restConnectionService.get(clientComissions,
      "/v1/api/manager/program/${username}/promoters/",[:])
    response.collect{ promoter ->
      new Promoter(promoter)
    }
  }

  Boolean isAPromoterSavedWithRecrCode(String recrCode) {
    logger.info "Validating recrCode: $recrCode"
    (promoterRepository.findOneById_RecrCode(recrCode) ? true : false) ||
    (programManagerRepository.findOneById_RecrCode(recrCode) ? true : false)
  }

}
