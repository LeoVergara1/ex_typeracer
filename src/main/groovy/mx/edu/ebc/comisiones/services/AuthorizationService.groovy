package mx.edu.ebc.comisiones.services

import mx.edu.ebc.comisiones.comision.domain.AdminDeComisiones
import mx.edu.ebc.comisiones.pojos.*

public interface AuthorizationService {
	def findAllAuthorizationByStatus(String status, Date initDateFrom, Date finDateFrom)
}