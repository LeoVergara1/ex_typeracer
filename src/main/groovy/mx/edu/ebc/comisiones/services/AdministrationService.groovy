package mx.edu.ebc.comisiones.services

import mx.edu.ebc.comisiones.comision.domain.AdminDeComisiones
import mx.edu.ebc.comisiones.comision.domain.PromoterAssociation

public interface AdministrationService {
	List<AdminDeComisiones> findAllComission()
	List<PromoterAssociation> findAllPromoters()
}