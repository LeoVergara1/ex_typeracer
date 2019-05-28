package mx.edu.ebc.comisiones.services

import org.springframework.stereotype.Repository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestPropertySource
import mx.edu.ebc.comisiones.comision.repo.*
import org.springframework.test.context.ContextConfiguration
import mx.edu.ebc.comisiones.comision.domain.UserCampus
import spock.lang.Specification

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

	def "create a person with rol manager"(){
		given: "Username, codeCampus,  "
			String username = "ja.cortes002"
			String codeCampus = "CMX"
			String roleCode = "558"
			String recCred = "000"
		when: "is created"
			def result = personService.saveRolAndCampus(username, codeCampus, roleCode, recCred)
		then: "created"
			assert result
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