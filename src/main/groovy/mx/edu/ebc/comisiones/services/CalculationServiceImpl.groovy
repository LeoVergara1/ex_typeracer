package mx.edu.ebc.comisiones.services

import groovy.util.logging.Log4j
import groovy.transform.Memoized
import mx.edu.ebc.comisiones.pojos.Campus
import mx.edu.ebc.comisiones.pojos.UserCampusCommand
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import wslite.json.JSONObject
import org.springframework.beans.factory.annotation.Value
import mx.edu.ebc.comisiones.comision.domain.Goal
import mx.edu.ebc.comisiones.comision.domain.Campaign
import mx.edu.ebc.comisiones.comision.repo.CampaignRepository
import mx.edu.ebc.comisiones.comision.repo.AuthorizationRepository
import mx.edu.ebc.comisiones.comision.repo.GoalRepository
import mx.edu.ebc.comisiones.comision.domain.Trimester;

@Service
class CalculationServiceImpl implements CalculationService {

	@Autowired
	AuthorizationRepository authorizationRepository

	def getAuthorizationsByCampaign(Campaign campaign){
		authorizationRepository.findAllByAutorizadoDirectorAndFechaAutorizadoBetween("AUTORIZADO", campaign.initDate, campaign.endDate)
	}

}