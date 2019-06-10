package mx.edu.ebc.comisiones.services

import mx.edu.ebc.comisiones.comision.domain.AdminDeComisiones
import mx.edu.ebc.comisiones.comision.domain.PromoterAssociation
import mx.edu.ebc.comisiones.pojos.*
import org.springframework.stereotype.Service
import mx.edu.ebc.comisiones.comision.storedProcedure.AutorizacionComisionesStoredProcedure
import org.springframework.beans.factory.annotation.Autowired

@Service
class AuthorizationServiceImpl implements AuthorizationService {

	@Autowired
	AutorizacionComisionesStoredProcedure autorizacionComisionesStoredProcedure

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
		autorizacionComisionesStoredProcedure.execute(params)
	}
}