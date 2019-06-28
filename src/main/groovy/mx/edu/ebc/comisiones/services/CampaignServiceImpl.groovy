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
import mx.edu.ebc.comisiones.comision.repo.GoalRepository
import mx.edu.ebc.comisiones.comision.domain.Trimester;

@Service
class CampaignServiceImpl implements CampaignService {

	@Value('${campusToGoals}')
	List<String> campusToGoal
	@Autowired
	CampaignRepository campaignRepository

	List<Campaign> getALlCampaningsByYearAndCreateIfNotExist(String year){
		def campaigns = campaignRepository.findAllByYear(year)
		if(!campaigns){
			Campaign campaign = new Campaign(
				name: "",
				status: "INACTIVE",
				initDate: new Date(),
				endDate: new Date(),
				year: year,
				period: 20
			)
			Campaign campaign_two = new Campaign(
				name: "",
				status: "INACTIVE",
				initDate: new Date(),
				endDate: new Date(),
				year: year,
				period: 40
			)
			campaigns << campaignRepository.save(campaign)
			campaigns << campaignRepository.save(campaign_two)
		}
		campaigns.collect{ [campaign: it, editable: false]}
	}

}