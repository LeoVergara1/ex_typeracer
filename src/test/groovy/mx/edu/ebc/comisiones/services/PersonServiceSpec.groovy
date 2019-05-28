package mx.edu.ebc.comisiones.services

import org.springframework.stereotype.Repository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestPropertySource
import mx.edu.ebc.comisiones.comision.repo.*
import org.springframework.test.context.ContextConfiguration
import mx.edu.ebc.comisiones.comision.domain.UserCampus
import spock.lang.Specification
import spock.lang.Unroll

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
	def "create a person with rol manager with satatus 401"(){
		given: "Username, codeCampus,  "
			String username = _username
			String codeCampus = _codeCampus
			String roleCode = _roleCode
			String recCred = _recCred
		when: "is created"
			def result = personService.saveRolAndCampus(username, codeCampus, roleCode, recCred)
			println result
		then: "created"
			assert result.statusRole == _statusRole
    where:
    _username  			|   _codeCampus  | _roleCode | _recCred  || _statusRole
    "ja.cortes002"  |   "CMX"        | "558"     | '000'     ||  401
	}

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