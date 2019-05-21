package mx.edu.ebc.comisiones.services

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import mx.edu.ebc.comisiones.comision.repo.AdminDeComisionesRepository
import mx.edu.ebc.comisiones.comision.repo.PromoterAssociationRepository
import mx.edu.ebc.comisiones.comision.repo.PersonRepository
import mx.edu.ebc.comisiones.comision.domain.AdminDeComisiones;
import mx.edu.ebc.comisiones.comision.domain.PromoterAssociation;
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional
import mx.edu.ebc.comisiones.pojos.*
import wslite.json.JSONObject


@Service
public class AdministrationServiceImpl implements AdministrationService {

	Logger logger = LoggerFactory.getLogger(RestConnectionServiceImpl.class)

	@Autowired
	AdminDeComisionesRepository adminDeComisionesRepository
	@Autowired
	PromoterAssociationRepository promoterAssociationRepository
	@Autowired
	PersonRepository personRepository
	@Autowired
	RestConnectionService restConnectionService
	@Value('${url.apibannercomisiones}')
	String clientApiBannerComissions
	@Value('${managerRoleID}')
	String managerRoleId
	@Value('#{${roles}}')
	Map<String, String> roles
	@Autowired
	PersonService personService

	@Override
	List<AdminDeComisiones> findAllComission(){
		adminDeComisionesRepository.findAll()
	}

	@Override
	List<PromoterAssociation> findAllPromoters(){
		promoterAssociationRepository.findAll()
	}

	@Transactional
	@Override
	AdminDeComisiones updateCuotaFijaToComission(String id, String cuotaFija){
		AdminDeComisiones comissionToUpdate = adminDeComisionesRepository.findById(id.toInteger())
		comissionToUpdate.cuotaFija = cuotaFija.toInteger()
		adminDeComisionesRepository.save(comissionToUpdate)
	}

	@Override
	List<AdminDeComisiones> updateComissions(String comissionEjecutiva, String comissionCordinacion){
		List<AdminDeComisiones> adminDeComisionesList = adminDeComisionesRepository.findAll()
		adminDeComisionesList.each{ admin ->
			admin.comisionCoordinacion = comissionCordinacion.toInteger()
			admin.comisionEjecutivo = comissionEjecutiva.toInteger()
			adminDeComisionesRepository.save(admin)
		}
	}

	@Override
	Person findPerson(String user){
		personService.findPersonByUsername(user)
	}

	@Override
  Person setProfile(Person person, String username, String portalName){
    //List<Profile> profiles= profileService.findPersonByUsernameAndPortalName(username, portalName)
    //profiles.each{ profile ->
    //  person.profiles << profile
    //}
    person
  }

	@Override
	Map getPersonWithValidations(String username){
			Person person = personService.findPersonByUsername(username)
    if(person.userName){
    	person = personService.setProfile(person,person.userName, "Web-comisiones")
    	person = personService.setCampuses(person)
    }
    //Map mapRol = personService.getRolesFromProperties()
    [person:person,
    	mapRol: roles,
     	managerRoleId: managerRoleId]
	}

  @Override
  Person setCampuses(Person person){
   // List<UserCampus> campuses= userCampusService.getAllCampusesforUser("FutureCampus",person.userName)
   // campuses?.each{ campus ->
   //   person.campuses << campus
   // }
    person
  }

  @Override
  List<RoleCommand> getRoles(String portalName){
   // List<RoleCommand> roles = []
   // List<JSONObject> jsonObject = restConnectionService.get(properties.getProperty("core.url.apibannerseguridad"), "/v2/api/application/roles/${portalName}")
   // jsonObject?.each{role ->
   //   roles << RoleCommand.convertJSONtoRoles(role)
   // }
   // roles
  }
	@Override
	def saveRolAndCampus(String username, String codeCampus, String roleCode, String recrCode){
		personService.saveRolAndCampus(username, codeCampus, roleCode, recrCode)
	}
}