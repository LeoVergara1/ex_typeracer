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
import mx.edu.ebc.comisiones.comision.repo.AdminDeComisionesRepository
import mx.edu.ebc.comisiones.comision.repo.AdminDeComisionesRepository
import mx.edu.ebc.comisiones.comision.repo.AuthorizationCrescentRepository
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
	@Autowired
	AdminDeComisionesRepository adminDeComisionesRepository
	@Autowired
	AuthorizationCrescentRepository authorizationCrescentRepository

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

	List<AuthorizationCrescent> getAuthorizationsCrescentcalculationByGoals(Trimester trimester ,String campus){
		Campaign campaign = getCampaignByTrimester(trimester)
		Goal goal = trimester.goals.find(){ it.campus == campus}
		println goal.dump()
		println trimester?.dump()
		println campaign?.dump()
		calculationByGoal(goal, campaign.initDate, campaign.endDate)
	}

	List<AuthorizationCrescent> getAuthorizationsCrescentcalculationByGoalsMarketing(Trimester trimester ,String campus){
		Campaign campaign = getCampaignByTrimester(trimester)
		Goal goal = trimester.goals.find(){ it.campus == campus }
		authorizationCrescentRepository.findAllByStatusMarketingAndStatusRectorAndCampusAndFechaAutorizadoBetween(false, true, goal.campus, campaign.initDate, campaign.endDate)
	}

	List<AuthorizationCrescent> getAuthorizationsCrescentcalculationByGoalsRector(Trimester trimester ,String campus){
		def authorizationsList = []
		Campaign campaign = getCampaignByTrimester(trimester)
		Goal goal = trimester.goals.find(){ it.campus == campus }
		if(campaign){
		 authorizationsList = authorizationCrescentRepository.findAllByStatusMarketingAndCampusAndFechaAutorizadoBetween(false, goal.campus, campaign.initDate, campaign.endDate)
		}
		authorizationsList
	}

	List<AuthorizationCrescent> getAuthorizationsCrescentcalculationByGoalsAndFilterAlreadyAuthorized(Trimester trimester ,String campus){
		List<AuthorizationCrescent> authorizationsToAuthorize = getAuthorizationsCrescentcalculationByGoals(trimester , campus)
		authorizationsToAuthorize.findAll(){
			!(authorizationCrescentRepository.findByIdPromotorAndIdCoordinadorAndIdAlumno(it.idPromotor, it.idCoordinador, it.idAlumno))
		}
	}

	List<AuthorizationCrescent> getAuthorizationsCrescentcalculationByGoalsAndFilterAlreadyAuthorizedToMarketing(Trimester trimester ,String campus){
		List<AuthorizationCrescent> authorizationsToAuthorize = getAuthorizationsCrescentcalculationByGoalsMarketing(trimester , campus)
		authorizationsToAuthorize
	}

	List<AuthorizationCrescent> getAuthorizationsCrescentcalculationByGoalsAndFilterAlreadyAuthorizedToRector(Trimester trimester ,String campus){
		List<AuthorizationCrescent> authorizationsToAuthorize = getAuthorizationsCrescentcalculationByGoalsRector(trimester , campus)
		authorizationsToAuthorize
	}



	List<AuthorizationCrescent> calculationByGoal(Goal goal, Date initDate, Date endDate){
		println "LLega aquí"
		List<AuthorizationComission> authorizationsCommissions = authorizationRepository.findAllByAutorizadoDirectorAndCampusAndFechaAutorizadoBetween("AUTORIZADO", goal.campus, initDate, endDate)
		(authorizationsCommissions.size() >= goal.numRegisters) ? makingCalculations(authorizationsCommissions, goal) : []
	}
	//TODO: Updating authorizations
	List<AuthorizationCrescent> updatingStatusAuthorizations(List<AuthorizationCrescent> authorizationsCrecent, Date initDate, Date endDate, String campus){
		List<AutorizacionComisiones> autorizacionComisiones = authorizationService.getCommissionsByDatesAndCampusFromBanner(campus, initDate, endDate)
		authorizationsCrecent
	}

	List<AuthorizationCrescent> makingCalculations(List<AuthorizationComission> authorizationsCommissions, goal){
		authorizationsCommissions.collect(){ authorization ->
			new AuthorizationCrescent(
  			campus: authorization.campus,
  			idPromotor: authorization.idPromotor,
  			nombrePromotor: authorization.nombrePromotor,
  			puesto: authorization.puesto,
  			idAlumno: authorization.idAlumno,
  			nombreAlumno: authorization.nombreAlumno,
  			pagoInicial: authorization.pagoInicial,
  			totalDescuentos: authorization.totalDescuentos,
  			comision: calculationComissionPromoter(authorization.pagoInicial.toDouble(), knowPerdiodToCommission(goal, authorization)),
  			periodo: authorization.periodo,
  			fechaDePago: authorization.fechaDePago,
  			autorizadoDirector: "CALCULADO",
  			dateCreated: new Date(),
  			lastUpdated: new Date(),
  			idCoordinador: authorization.idCoordinador,
  			nombreCoordinador: authorization.nombreCoordinador,
  			comisionCoordinador: calculationComissionCoordinater(authorization.pagoInicial.toDouble(), knowPerdiodToCommission(goal, authorization)),
  			fechaAutorizado: authorization.fechaAutorizado,
  			user: authorization.user,
  			tipoPago: authorization.tipoPago,
  			valorContratoReal: authorization.valorContratoReal,
  			pidm: authorization.pidm
			)
		}
	}

	double calculationComissionPromoter(double pagoInicial, float percentCommission){
		double result = (pagoInicial* (percentCommission/100))
		Math.round(result * 100) / 100
	}

	double calculationComissionCoordinater(double pagoInicial, float percentCommission){
		double commissionPromoter = (pagoInicial* (percentCommission/100))
		double result =(commissionPromoter * (adminDeComisionesRepository.findAll().first().comisionEjecutivo / 100))
		Math.round(result * 100) / 100
	}

	float knowPerdiodToCommission(Goal goal, def authorization){
		if(authorization.periodo.reverse().take(2).reverse() == "40")
			return goal.percentCommissionFourty
		return goal.percentCommission
	}

}