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
import mx.edu.ebc.comisiones.comision.repo.TrimesterRepository
import mx.edu.ebc.comisiones.comision.repo.GoalRepository
import mx.edu.ebc.comisiones.comision.domain.Trimester;

@Service
class TrimesterServiceImpl implements TrimesterService {

	@Autowired
	GoalRepository goalRepository
	@Value('${campusToGoals}')
	List<String> campusToGoal
	@Autowired
	TrimesterRepository trimesterRepository

	List<Goal> createAllGolsToCampaing(Integer id){
		Trimester trimester = trimesterRepository.findById(id)
		List<Goal> goals = getGoalsFromTrimester(trimester)
		if(goals)
			return goals
		campusToGoal.collect{ goalCampus ->
			def goalCampusToSave = new Goal(
				status:"created",
				campus: goalCampus,
				trimester: trimester
			)
			goalRepository.save(goalCampusToSave)
		}
	}

	List<Goal> getGoalsFromTrimester(Trimester trimester){
		goalRepository.findAllByTrimester(trimester)
	}
}