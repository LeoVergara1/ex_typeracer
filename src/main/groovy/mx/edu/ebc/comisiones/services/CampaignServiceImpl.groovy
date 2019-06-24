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
import mx.edu.ebc.comisiones.comision.repo.CampaignRepository
import mx.edu.ebc.comisiones.comision.repo.GoalRepository
import mx.edu.ebc.comisiones.comision.domain.Campaign;

@Service
class CampaignServiceImpl implements CampaignService {

	@Autowired
	GoalRepository goalRepository
	@Value('${campusToGoals}')
	List<String> campusToGoal
	@Autowired
	CampaignRepository campaignRepository

	List<Goal> createAllGolsToCampaing(Integer id){
		Campaign campaign = campaignRepository.findById(id)
		List<Goal> goals = getGoalsFromCampaign(campaign)
		if(goals)
			return goals
		campusToGoal.collect{ goalCampus ->
			println goalCampus
			def goalCampusToSave = new Goal(
				status:"created",
				campus: goalCampus,
				campaign: campaign
			)
			goalRepository.save(goalCampusToSave)
		}
	}

	List<Goal> getGoalsFromCampaign(Campaign campaign){
		goalRepository.findAllByCampaign(campaign)
	}
}