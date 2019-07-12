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

	List<AuthorizationCrescent> getAuthorizationsCrescentcalculationByGoals(Trimester trimester ,String campus){
		Campaign campaign = getCampaignByTrimester(trimester)
		Goal goal = trimester.goals.find(){ it.campus == campus}
		calculationByGoal(goal, campaign.initDate, campaign.endDate)
	}

	List<AuthorizationCrescent> calculationByGoal(Goal goal, Date initDate, Date endDate){
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
  			comision: calculationComissionPromoter(authorization.valorContratoReal, goal.percentCommission),
  			periodo: authorization.periodo,
  			fechaDePago: authorization.fechaDePago,
  			autorizadoDirector: authorization.autorizadoDirector,
  			dateCreated: new Date(),
  			lastUpdated: new Date(),
  			idCoordinador: authorization.idCoordinador,
  			nombreCoordinador: authorization.nombreCoordinador,
  			comisionCoordinador: authorization.comisionCoordinador,
  			fechaAutorizado: authorization.fechaAutorizado,
  			user: authorization.user,
  			tipoPago: authorization.tipoPago,
  			valorContratoReal: authorization.valorContratoReal,
  			pidm: authorization.pidm
			) 
		}
	}

	double calculationComissionPromoter(double valorContratoReal, float percentCommission){
		(valorContratoReal* (percentCommission/100)) 
	}

	double calculationComissionCoordinater(double valorContratoReal, float percentCommission){
		double commissionPromoter = (valorContratoReal* (percentCommission/100)) 
		commissionPromoter * 0.15 
	}

}