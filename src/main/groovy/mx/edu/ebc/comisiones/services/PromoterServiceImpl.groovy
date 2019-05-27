package mx.edu.ebc.comisiones.services

import groovy.transform.Memoized
import mx.edu.ebc.comisiones.pojos.Promoter
import mx.edu.ebc.comisiones.pojos.PromoterCode
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import wslite.json.JSONObject
import org.springframework.beans.factory.annotation.Value
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Service
class PromoterServiceImpl implements PromoterService{

  Logger logger = LoggerFactory.getLogger(PromoterServiceImpl.class)

  @Autowired
  RestConnectionService restConnectionService
  @Autowired
  Properties properties
	@Value('${url.apicomisiones}')
	String clientComissions
  @Value('${url.apibannercomisiones}')
  String clientApiBannerComission

  @Override
  Boolean createPromoter(String userName, Long pidm, String recrCode) {
    logger.info "Saving new promoter: $userName"
    def postStatus = restConnectionService.post(
            clientComissions,
            "/v1/api/promoter/",
            [promoter_user_name: userName,
             promoter_pidm: pidm,
             promoter_recr_code: recrCode])
    switch (postStatus?.statusCode) {
      case 201:
        logger.info "Successfully saved..."
        true
        break
      case 412:
        logger.error "ERROR, This promoter already exists"
        false
        break
      case 428:
        logger.error "ERROR, Recruiter code already in use"
        false
        break
      default:
        false
    }
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

}
