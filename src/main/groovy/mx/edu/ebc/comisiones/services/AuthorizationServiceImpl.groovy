package mx.edu.ebc.comisiones.services

import mx.edu.ebc.comisiones.comision.domain.AdminDeComisiones
import mx.edu.ebc.comisiones.comision.domain.PromoterAssociation
import mx.edu.ebc.comisiones.comision.domain.AuthorizationComission
import mx.edu.ebc.comisiones.pojos.*
import org.springframework.stereotype.Service
import mx.edu.ebc.comisiones.comision.storedProcedure.AutorizacionComisionesStoredProcedure
import org.springframework.beans.factory.annotation.Autowired
import mx.edu.ebc.comisiones.comision.repo.AuthorizationRepository

@Service
class AuthorizationServiceImpl implements AuthorizationService {

	@Autowired
	AutorizacionComisionesStoredProcedure autorizacionComisionesStoredProcedure
	@Autowired
	AuthorizationRepository authorizationRepository

	def getCalculation(String campus, String initDate, String finDate){
    Date initDateFrom = new Date(Date.parse(initDate))
    Date finDateFrom = new Date(Date.parse(finDate))
    Map params = [
      p_fecha_pago_ini: initDateFrom,
      p_fecha_pago_fin: finDateFrom,
      p_campus: campus
    ]
		println "*"*100
		println params
		[out_comisiones: filterAuthorizations(params)]
	}

	def saveListAuthorization(List<AuthorizationComission> listToAuthorization, String username){
		listToAuthorization.each{ authorized ->
			authorizationRepository.save(new AuthorizationComission(authorized, username))
		}
	}

	def filterAuthorizations(Map params){
		autorizacionComisionesStoredProcedure.execute(params).out_comisiones.findAll(){
			//!(authorizationRepository.findByIdPromotorAndIdCoordinadorAndIdAlumno(it.idPromotor, it.idCoordinador, it.idAlumno))
			true
		}
	}
}