package mx.edu.ebc.comisiones.services

import mx.edu.ebc.comisiones.comision.domain.AdminDeComisiones
import mx.edu.ebc.comisiones.pojos.*

interface AuthorizationService {
	def findAllAuthorizationByStatus(String status, String campus, Date initDateFrom, Date finDateFrom)
}