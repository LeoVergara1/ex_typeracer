package mx.edu.ebc.comisiones.services

import mx.edu.ebc.comisiones.comision.domain.AdminDeComisiones
import mx.edu.ebc.comisiones.pojos.*
import mx.edu.ebc.comisiones.comision.domain.Promoter

public interface AdministrationService {
	List<AdminDeComisiones> findAllComission()
	List<Promoter> findAllPromoters()
	AdminDeComisiones updateCuotaFijaToComission(String id, String cuotaFija)
	List<AdminDeComisiones> updateComissions(String comissionEjecutiva, String comissionCordinacion)
	Person findPerson(String user)
	Person setProfile(Person person, String username, String portalName)
	Person setCampuses(Person person)
	Map getPersonWithValidations(String username)
	List<RoleCommand> getRoles(String portalName)
	def saveRolAndCampus(String username, String codeCampus, String roleCode, String recrCode)
	def deleteCampusAndRolToPerson(String username, String codeCampus, String roleCode)
	def saveAssociation(def listAssociations, def person)
}