package mx.edu.ebc.comisiones.services

import org.springframework.stereotype.Repository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestPropertySource
import mx.edu.ebc.comisiones.comision.repo.*
import org.springframework.test.context.ContextConfiguration
import mx.edu.ebc.comisiones.comision.domain.UserCampus
import  mx.edu.ebc.comisiones.pojos.Person
import spock.lang.Specification
import spock.lang.Unroll
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@ContextConfiguration
@TestPropertySource("classpath:application-dev.properties")
class PersonServiceSpec extends Specification{

	@Autowired
  PersonService personService


  def "Spect 000 Check service inject"() {
    when:
    println personService
    then:
    assert personService
  }

	@Unroll
	@Transactional
	def "create a person with rol manager with satatus 401"(){
		given: "Username, codeCampus,  "
			String username = _username
			String codeCampus = _codeCampus
			String roleCode = _roleCode
			String recCred = _recCred
		when: "is created"
			def result = personService.saveRolAndCampus(username, codeCampus, roleCode, recCred)
		then: "created"
			assert result.statusRole == _statusRole
    where:
    _username  			|   _codeCampus  | _roleCode | _recCred  || _statusRole
    "ja.cortes002"  |   "CMX"        | "558"     | '000'     ||  201
    "ja.cortes002"  |   "CMX"        | "558"     | '001'     ||  401
    "ja.cortes002"  |   "CMX"        | "557"     | '000'     ||  201
	}

	def "Find a username for this portal name"(){
		given: "a username"
			String username = "ja.cortes002"
		when: "Is found"
			def result = personService.findPersonByUsername(username)
		then:
			result
	}

	def "Find a profiles and campus for this portal name"(){
		given: "a username"
			String username = "ja.cortes002"
			Person person = personService.findPersonByUsername(username)
			println person
		when: "Is found"
			def result = personService.setProfile(person, "comisiones-li")
			def resultCampus = personService.setCampuses(person)
			println "Campus"
			println resultCampus
		then:
			result
			resultCampus
	}

//	def "Deleting a username"(){
//		given: "a username"
//		when: "Is deleted"
//		then:
//	}

	//def "create a person with rol promoter"(){
	//	given: "by username"
	//		String username = "vi.mendoza"
	//		String codeCampus = ""
	//		String roleCode = ""
	//		String recCred = ""
	//	when: "is created"
	//		def result = personService.saveRolAndCampus(username, codeCampus, roleCode, recCred)
	//	then: "created"
	//		assert result
	//}

}