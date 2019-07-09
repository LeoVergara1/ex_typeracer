package mx.edu.ebc.comisiones.services

import groovy.util.logging.Log4j
import groovy.transform.Memoized
import mx.edu.ebc.comisiones.pojos.Campus
import mx.edu.ebc.comisiones.pojos.UserCampusCommand
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import wslite.json.JSONObject
import org.springframework.beans.factory.annotation.Value
import mx.edu.ebc.comisiones.comision.domain.Goal
import mx.edu.ebc.comisiones.comision.domain.Campaign
import mx.edu.ebc.comisiones.comision.domain.AuthorizationCrescent
import mx.edu.ebc.comisiones.comision.domain.AuthorizationComission
import mx.edu.ebc.comisiones.comision.repo.CampaignRepository
import mx.edu.ebc.comisiones.comision.repo.AuthorizationRepository
import mx.edu.ebc.comisiones.comision.repo.GoalRepository
import mx.edu.ebc.comisiones.comision.domain.Trimester
import mx.edu.ebc.comisiones.comision.domain.AutorizacionComisiones

@Component
class CalculationServiceImpl implements CalculationService {

	@Autowired
	AuthorizationRepository authorizationRepository
	@Autowired
	CampaignRepository campaignRepository
	@Autowired
	AuthorizationService authorizationService

	List<AuthorizationComission> getAuthorizationsByCampaign(Campaign campaign){
		authorizationRepository.findAllByAutorizadoDirectorAndFechaAutorizadoBetween("AUTORIZADO", campaign.initDate, campaign.endDate)
	}

	Boolean validateGoal(Goal goal) {
		Campaign campaign = getCampaignByTrimester(goal.trimester)
		List<AuthorizationComission> authorizations = getAuthorizationsByCampaign(campaign)
		false
	}

	Campaign getCampaignByTrimester(Trimester trimester){
		Integer period =  trimester.clave.take(2).toInteger()
		campaignRepository.findByPeriodAndStatus(period, "ACTIVE")
	}

	List<AuthorizationCrescent> getAuthorizationsCrescentcalculationByGoals(List<Goal> goals){

	}

	List<AuthorizationCrescent> calculationByGoal(Goal goal, Date initDate, Date endDate){
		List<AuthorizationComission> authorizationsCommissions = authorizationRepository.findAllByAutorizadoDirectorAndCampusAndFechaAutorizadoBetween("AUTORIZADO", goal.campus, initDate, endDate)
		(authorizationsCommissions.size() >= goal.numRegisters) ? authorizationsCommissions : [] 
	}

	List<AuthorizationCrescent> updatingStatusAuthorizations(List<AuthorizationCrescent> authorizationsCrecent, Date initDate, Date endDate, String campus){
		List<AutorizacionComisiones> autorizacionComisiones = authorizationService.getCommissionsByDatesAndCampusFromBanner(campus, initDate, endDate)
		println "******BANNER*****"
		println autorizacionComisiones
		authorizationsCrecent	
	}

}