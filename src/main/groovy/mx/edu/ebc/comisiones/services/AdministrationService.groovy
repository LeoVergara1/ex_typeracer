package mx.edu.ebc.comisiones.services

import mx.edu.ebc.comisiones.comision.domain.AdminDeComisiones
import mx.edu.ebc.comisiones.comision.domain.PromoterAssociation
import mx.edu.ebc.comisiones.commands.*

public interface AdministrationService {
	List<AdminDeComisiones> findAllComission()
	List<PromoterAssociation> findAllPromoters()
	AdminDeComisiones updateCuotaFijaToComission(String id, String cuotaFija)
	List<AdminDeComisiones> updateComissions(String comissionEjecutiva, String comissionCordinacion)
	Map findPerson(String user)
	PersonCommand setProfile(PersonCommand person, String username, String portalName)
	PersonCommand setCampuses(PersonCommand person)
	List<RoleCommand> getRoles(String portalName)
}