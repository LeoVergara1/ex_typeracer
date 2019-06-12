package mx.edu.ebc.comisiones.services

import mx.edu.ebc.comisiones.comision.domain.AdminDeComisiones
import mx.edu.ebc.comisiones.comision.domain.PromoterAssociation
import mx.edu.ebc.comisiones.comision.domain.AuthorizationComission
import mx.edu.ebc.comisiones.pojos.*
import org.springframework.stereotype.Service
import mx.edu.ebc.comisiones.comision.storedProcedure.AutorizacionComisionesStoredProcedure
import org.springframework.beans.factory.annotation.Autowired
import mx.edu.ebc.comisiones.comision.repo.AuthorizationRepository
import java.text.SimpleDateFormat

@Service
class AuthorizationServiceImpl implements AuthorizationService {

	@Autowired
	AutorizacionComisionesStoredProcedure autorizacionComisionesStoredProcedure
	@Autowired
	AuthorizationRepository authorizationRepository

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

	def saveListAuthorization(List<AuthorizationComission> listToAuthorization, String username){
		listToAuthorization.each{ authorized ->
			authorizationRepository.save(new AuthorizationComission(authorized, username))
		}
	}

	def filterAuthorizations(Map params){
		autorizacionComisionesStoredProcedure.execute(params).out_comisiones.findAll(){
			!(authorizationRepository.findByIdPromotorAndIdCoordinadorAndIdAlumno(it.idPromotor, it.idCoordinador, it.idAlumno))
		}
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
		if(params.status == "AUTORIZADO")
			return findAllAuthorizationByStatus(params.status, initDateFrom, finDateFrom)
		if(params.status == "RECHAZADO")
			return [1,23,4]
	}

	def findAllAuthorizationByStatus(String status, Date initDateFrom, Date finDateFrom){
		println initDateFrom
		println finDateFrom
		authorizationRepository.findAllByAutorizadoDirectorAndFechaAutorizadoBetween(status, initDateFrom, finDateFrom)
	}

	def structureGrups(def groups){
		groups.collect(){ k, v ->
			[namePromoter: v[0].nombrePromotor, job: v[0].puesto, numberStudents: v.size(), datePayment: v[0].fechaDePago, comission: v.sum{ it.comision.toFloat() }]
		}
	}
}