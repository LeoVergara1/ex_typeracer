package mx.edu.ebc.comisiones.services

import mx.edu.ebc.comisiones.pojos.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import wslite.json.JSONObject
import org.springframework.beans.factory.annotation.Value
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import mx.edu.ebc.comisiones.network.NetworkService

@Service
class PromoterAsignmentServiceImpl implements PromoterAsignmentService{

  Logger logger = LoggerFactory.getLogger(PromoterAsignmentServiceImpl.class)

  @Autowired
  Properties properties
  @Autowired
  PersonService personService
  @Autowired
  PromoterService promoterService
	@Value('#{${roles}}')
	Map<String, String> rolesMap
	@Value('${promotersRadmCode}')
	String promotersRadmCode
  @Autowired
  CampusService campusService
  @Value('${url.apibannercomisiones}')
	String clientApiBannerComission

  @Override
  Integer isPromoterPidmAndRecrCodeValidForRegistration(Long promoterPidm, String recrCode) {
    logger.info "Validating pidm: $promoterPidm and recrCode: $recrCode"
    isValidRadmCode(promoterPidm) ? isValidRecrCode(recrCode) ? 200 : 401 : 400
  }

  @Override
  Boolean isValidRadmCode(Long promoterPidm){
    findAllPromotersFromBannerByRadmCode(
            promotersRadmCode
    ).find { promoterBanner ->
      promoterBanner.pidm == promoterPidm
    } ? true : {
      logger.info "Error, not valid EMRECR code"
      false
    }()
  }

  @Override
  Boolean isValidRecrCode(String recrCode) {
    (promoterService.getRecrCodeCatalogue().find { promoterCode ->
      promoterCode.recrCode == recrCode
    } ? true : {
      logger.info "Error, not in STVRECR catalogue"
      false
    }()) && !(promoterService.isAPromoterSavedWithRecrCode(recrCode))
  }

  @Override
  List<PromoterCode> findAllPromotersFromBannerByRadmCode(String radmCode) {
    logger.info "Finding all promoters in banner for radmCode: $radmCode"
    NetworkService.buildRequest(clientApiBannerComission){
      endpointUrl "/v1/api/promoters/$radmCode"
    }.execute().json?.collect { jsonObject ->
      PromoterCommand.createPromoterBannerForRadmCodeFromJSONObject(jsonObject)
    } ?: []
  }


}
