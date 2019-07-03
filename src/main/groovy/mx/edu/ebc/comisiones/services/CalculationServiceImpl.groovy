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
import mx.edu.ebc.comisiones.comision.repo.CampaignRepository
import mx.edu.ebc.comisiones.comision.repo.AuthorizationRepository
import mx.edu.ebc.comisiones.comision.repo.GoalRepository
import mx.edu.ebc.comisiones.comision.domain.Trimester;

@Component
class CalculationServiceImpl implements CalculationService {

	@Autowired
	AuthorizationRepository authorizationRepository
	@Autowired
	CampaignRepository campaignRepository

	def getAuthorizationsByCampaign(Campaign campaign){
		authorizationRepository.findAllByAutorizadoDirectorAndFechaAutorizadoBetween("AUTORIZADO", campaign.initDate, campaign.endDate)
	}

	Boolean validateGoal(Goal goal) {
		println goal.dump()
		Campaign campaign = getCampaignByTrimester(goal.trimester)
		false
	}

	Campaign getCampaignByTrimester(Trimester trimester){
		println trimester.dump()
		println "*"*100
		Integer period =  trimester.clave.take(2).toInteger()
		campaignRepository.findByPeriodAndStatus(trimester.period, "ACTIVE")
	}

}