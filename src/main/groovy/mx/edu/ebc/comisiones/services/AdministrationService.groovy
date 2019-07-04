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
	Map getPersonWithValidations(String username)
	def saveRolAndCampus(String username, String codeCampus, String roleCode, String recrCode)
	def deleteCampusAndRolToPerson(String username, String codeCampus, String roleCode)
	def saveAssociation(def listAssociations, def person)
}