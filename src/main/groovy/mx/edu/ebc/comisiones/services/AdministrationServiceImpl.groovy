package mx.edu.ebc.comisiones.services

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import mx.edu.ebc.comisiones.comision.repo.AdminDeComisionesRepository
import mx.edu.ebc.comisiones.comision.repo.PersonRepository
import mx.edu.ebc.comisiones.comision.domain.AdminDeComisiones;
import mx.edu.ebc.comisiones.comision.domain.ProgramManager;
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional
import mx.edu.ebc.comisiones.pojos.*
import wslite.json.JSONObject
import mx.edu.ebc.comisiones.comision.domain.Promoter
import mx.edu.ebc.comisiones.comision.repo.PromoterRepository
import mx.edu.ebc.comisiones.seguridad.repo.RolesRepository
import mx.edu.ebc.comisiones.comision.repo.ProgramManagerRepository
import mx.edu.ebc.comisiones.comision.domain.Promoter


@Service
public class AdministrationServiceImpl implements AdministrationService {

	Logger logger = LoggerFactory.getLogger(RestConnectionServiceImpl.class)

	@Autowired
	AdminDeComisionesRepository adminDeComisionesRepository
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
	@Autowired
  PromoterRepository promoterRepository
	@Autowired
	ProgramManagerService programManagerService
	@Autowired
	ProgramManagerRepository programManagerRepository
	@Autowired
  RolesRepository rolesRepository

	@Override
	List<AdminDeComisiones> findAllComission(){
		adminDeComisionesRepository.findAll()
	}

	@Override
	List<Promoter> findAllPromoters(){
		promoterRepository.findAll()
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
    	person = personService.setProfile(person, "Pago%20de%20comisiones")
    	person = personService.setCampuses(person)
    }
    //Map mapRol = personService.getRolesFromProperties()
    [person:person,
    	mapRol: rolesRepository.findAllByNidRolPortal("869"),
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

	def saveAssociation(def listAssociations, def person){
		ProgramManager program = programManagerService.findOneById_UserName(person.userName)
		listAssociations.each(){ association ->
			Promoter promoter = promoterRepository.findOneById_UserName(association.promoter.id.userName)
			(association.associate) ? addPromoterToProgram(promoter, program, person) : removePromoterFromProgram(promoter, program)
		}
		[status: 200]
	}

	def addPromoterToProgram (Promoter promoter, ProgramManager program, def person){
		promoter.programManager = program
		promoter.idCoordinater = person.enrollment
		promoter.relationActive = 'Y'
		promoter.coordinaterName = person.firstName
		promoter.apellidosCoordinater = person.lastName
		promoter.claveCoordinater = person.adminId.replace("AD", "").toInteger()
		promoterRepository.save(promoter)
	}

	def removePromoterFromProgram(Promoter promoter, ProgramManager program){
		if(promoter.programManager?.id?.userName == program?.id?.userName){
			promoter.programManager = null
		promoter.idCoordinater = null
		promoter.coordinaterName = null
		promoter.relationActive = 'N'
		promoter.apellidosCoordinater = null
		promoter.claveCoordinater = null
			promoterRepository.save(promoter)
		}
	}

	@Override
	def deleteCampusAndRolToPerson(String username, String codeCampus, String roleCode) {
		personService.deleteCampusAndRolToPerson(username, codeCampus, roleCode)
	}
}