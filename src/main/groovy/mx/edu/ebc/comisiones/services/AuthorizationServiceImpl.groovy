package mx.edu.ebc.comisiones.services

import mx.edu.ebc.comisiones.comision.domain.AdminDeComisiones
import mx.edu.ebc.comisiones.comision.domain.AuthorizationComission
import mx.edu.ebc.comisiones.comision.domain.AutorizacionComisiones
import mx.edu.ebc.comisiones.comision.domain.AuthorizationCrescent
import mx.edu.ebc.comisiones.comision.domain.Trimester
import mx.edu.ebc.comisiones.pojos.*
import org.springframework.stereotype.Service
import mx.edu.ebc.comisiones.comision.storedProcedure.AutorizacionComisionesStoredProcedure
import org.springframework.beans.factory.annotation.Autowired
import mx.edu.ebc.comisiones.comision.repo.AuthorizationRepository
import mx.edu.ebc.comisiones.comision.repo.AuthorizationCrescentRepository
import mx.edu.ebc.comisiones.comision.repo.PromoterRepository
import java.text.SimpleDateFormat

@Service
class AuthorizationServiceImpl implements AuthorizationService {

	@Autowired
	AutorizacionComisionesStoredProcedure autorizacionComisionesStoredProcedure
	@Autowired
	AuthorizationRepository authorizationRepository
	@Autowired
	AuthorizationCrescentRepository authorizationCrescentRepository
	@Autowired
	PromoterRepository promoterRepository
	@Autowired
	TrimesterService trimesterService
	@Autowired
	CalculationService calculationService

	def getCalculation(String campus, String initDate, String finDate){
    Date initDateFrom = new SimpleDateFormat("dd/MM/yyyy").parse(initDate)
    Date finDateFrom = new SimpleDateFormat("dd/MM/yyyy").parse(finDate)
    Map params = [
      p_fecha_pago_ini: initDateFrom,
      p_fecha_pago_fin: finDateFrom,
      p_campus: campus
    ]
		def comissions = filterAuthorizations(params)

		[out_comisiones: comissions, comissionsGroups: comissions.groupBy({ it.idPromotor })]
	}

	def getCalculationMarketing(String campus, String initDate, String finDate){
    Date initDateFrom = new SimpleDateFormat("dd/MM/yyyy").parse(initDate)
    Date finDateFrom = new SimpleDateFormat("dd/MM/yyyy").parse(finDate)
		def comissions = authorizationRepository.findAllByStatusMarketingAndStatusRectorAndCampusAndFechaAutorizadoBetween(false, true, campus, initDateFrom, finDateFrom)
		[out_comisiones: comissions, comissionsGroups: comissions.groupBy({ it.idPromotor })]
	}

	def getCalculationRector(String campus, String initDate, String finDate){
    Date initDateFrom = new SimpleDateFormat("dd/MM/yyyy").parse(initDate)
    Date finDateFrom = new SimpleDateFormat("dd/MM/yyyy").parse(finDate)
		def comissions = authorizationRepository.findAllByStatusMarketingAndStatusRectorAndCampusAndFechaAutorizadoBetween(false, false, campus, initDateFrom, finDateFrom)
		[out_comisiones: comissions, comissionsGroups: comissions.groupBy({ it.idPromotor })]
	}

	def saveListAuthorization(List<AuthorizationComission> listToAuthorization, String username){
		listToAuthorization.each{ authorized ->
			authorizationRepository.save(new AuthorizationComission(authorized, username))
		}
	}

	def saveListAuthorizationMarketing(List<AuthorizationComission> listToAuthorization, String username){
		listToAuthorization.each{ authorized ->
			authorized.usernameMarketing = username
			authorizationRepository.save(authorized)
		}
	}

	def saveListAuthorizationRector(List<AuthorizationComission> listToAuthorization, String username){
		listToAuthorization.each{ authorized ->
			authorized.usernameRector = username
			authorizationRepository.save(authorized)
		}
	}

	def saveListAuthorizationCrecent(List<AuthorizationCrescent> listToAuthorization, String username){
		listToAuthorization.each{ authorized ->
			authorized.user = username
			authorizationRepository.save(authorized)
		}
	}

	def saveListAuthorizationCrecentMarketing(List<AuthorizationCrescent> listToAuthorization, String username){
		listToAuthorization.each{ authorized ->
			authorized.usernameMarketing = username
			authorizationRepository.save(authorized)
		}
	}

	def saveListAuthorizationCrecentRector(List<AuthorizationCrescent> listToAuthorization, String username){
		listToAuthorization.each{ authorized ->
			authorized.usernameRector = username
			authorizationRepository.save(authorized)
		}
	}

	def filterAuthorizations(Map params){
		autorizacionComisionesStoredProcedure.execute(params).out_comisiones.findAll(){
			!(authorizationRepository.findByIdPromotorAndIdCoordinadorAndIdAlumno(it.idPromotor, it.idCoordinador, it.idAlumno))
		}
	}

	List<AutorizacionComisiones> getCommissionsByDatesAndCampusFromBanner(String campus, Date initDate, Date endDate){
		autorizacionComisionesStoredProcedure.execute([p_fecha_pago_ini: initDate, p_fecha_pago_fin: endDate, p_campus: campus]).out_comisiones
	}

	def getCommissionsByStatus(Map params){
    Date initDateFrom = new SimpleDateFormat("dd/MM/yyyy").parse(params.selectInit)
    Date finDateFrom = new SimpleDateFormat("dd/MM/yyyy").parse(params.selectFin)
    Map paramsStored = [
      p_fecha_pago_ini: initDateFrom,
      p_fecha_pago_fin: finDateFrom,
      p_campus: params.campus
    ]
		if(params.status == "POR_AUTORIZAR")
			return filterAuthorizations(paramsStored)
		if(params.status == "AUTORIZADO" || params.status == "RECHAZADA")
			return findAllAuthorizationByStatus(params.status, params.campus, initDateFrom, finDateFrom)
	}

	def findAllAuthorizationByStatus(String status, String campus, Date initDateFrom, Date finDateFrom){
		if(campus == "TODOS")
			return authorizationRepository.findAllByAutorizadoDirectorAndFechaAutorizadoBetween(status, initDateFrom, finDateFrom)
		authorizationRepository.findAllByAutorizadoDirectorAndCampusAndFechaAutorizadoBetween(status, campus,initDateFrom, finDateFrom)
	}

	def structureGrups(def groups){
		groups.collect(){ k, v ->
			[namePromoter: v[0].nombrePromotor, job: v[0].puesto, numberStudents: v.size(), datePayment: v[0].fechaDePago, comission: v.sum{ it.comision.toFloat() }, idPromoter: v[0].idPromotor]
		}
	}

	def getAllAuthorizationsCommisionsWithStructureToReport(String status, Date initDateFrom, Date finDateFrom, String campus){
		List<AuthorizationComission> authorizations = []
		if(campus == "TODOS"){
			authorizations = authorizationRepository.findAllByAutorizadoDirectorAndFechaAutorizadoBetween(status, initDateFrom, finDateFrom)
		}
		else {
			authorizations = authorizationRepository.findAllByAutorizadoDirectorAndCampusAndFechaAutorizadoBetween(status, campus, initDateFrom, finDateFrom)
		}
		generateStructuraToReport(authorizations)
	}

	def generateStructuraToReport(List<AuthorizationComission> authorizations){
		authorizations.collect(){ authorized ->
			[authorization: authorized, promoter: promoterRepository.findOneByIdPromoter(authorized.idPromotor), campus: authorized.campus]
		}
	}

	List<AuthorizationCrescent> getCommissionsCrecentByStatus(data){
		if(data.status == "POR_AUTORIZAR")
			return filterToAuthorizationsCrecents(data)
		if(data.status == "AUTORIZADO" || data.status == "RECHAZADA")
			return findAllByAutorizadoDirectorAndCampusAndFechaAutorizadoBetween(data)
	}

	List<AuthorizationCrescent> filterToAuthorizationsCrecents(data){
    calculationService.getAuthorizationsCrescentcalculationByGoalsAndFilterAlreadyAuthorized(data.trimester, data.campus)
	}

	List<AuthorizationCrescent> findAllByAutorizadoDirectorAndCampusAndFechaAutorizadoBetween(data){
    Date initDateFrom = new SimpleDateFormat("dd/MM/yyyy").parse(data.selectInit)
    Date finDateFrom = new SimpleDateFormat("dd/MM/yyyy").parse(data.selectFin)
		if(data.campus == "TODOS"){
			return authorizationCrescentRepository.findAllByAutorizadoDirectorAndFechaAutorizadoBetween(data.status, initDateFrom, finDateFrom)
		}
		authorizationCrescentRepository.findAllByAutorizadoDirectorAndCampusAndFechaAutorizadoBetween(data.status, data.campus, initDateFrom, finDateFrom)
	}

}