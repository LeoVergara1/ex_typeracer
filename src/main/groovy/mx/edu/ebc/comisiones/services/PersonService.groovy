package mx.edu.ebc.comisiones.services

import mx.edu.ebc.comisiones.pojos.Person
import mx.edu.ebc.comisiones.pojos.Profile
import mx.edu.ebc.comisiones.pojos.RoleCommand
import wslite.json.JSONObject

interface PersonService {
		Person findPersonByUsername(String username)
		List<Profile> findPersonByUsernameAndPortalName(String userName, String portalName)
		Person setProfile(Person person, String portalName)
	  Person setCampuses(Person person)
	  def saveRolAndCampus(String username, String codeCampus, String roleCode, String recrCode)
	  Map deleteCampusAndRolToPerson(String username, String codeCampus, String roleCode)
}
