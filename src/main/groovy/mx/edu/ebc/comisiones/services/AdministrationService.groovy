package mx.edu.ebc.comisiones.services

import mx.edu.ebc.comisiones.comision.domain.AdminDeComisiones
import mx.edu.ebc.comisiones.comision.domain.PromoterAssociation

public interface AdministrationService {
	List<AdminDeComisiones> findAllComission()
	List<PromoterAssociation> findAllPromoters()
	AdminDeComisiones updateCuotaFijaToComission(String id, String cuotaFija)
	List<AdminDeComisiones> updateComissions(String comissionEjecutiva, String comissionCordinacion)
	Map findPerson(String promoterId, String coordinater)
}