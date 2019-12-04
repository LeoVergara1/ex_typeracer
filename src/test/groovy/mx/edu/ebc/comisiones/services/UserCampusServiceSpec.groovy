package mx.edu.ebc.comisiones.services

import org.springframework.stereotype.Repository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestPropertySource
import mx.edu.ebc.comisiones.comision.repo.*
import org.springframework.test.context.ContextConfiguration
import mx.edu.ebc.comisiones.comision.domain.UserCampus
import spock.lang.Specification
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@ContextConfiguration
@TestPropertySource("classpath:application-dev.properties")
class UserCampusServiceSpec extends Specification{

	@Autowired
  UserCampusServiceImpl userCampusService


  def "Spect 000 Check repository"() {
    when:
    println userCampusService
    then:
    assert userCampusService
  }

	def "Create User Campus"(){
		given: "User Campus"
			String username = "r.martinez026"
			String campusCode = "CMX"
			Long pidm = 289
		when: "Is created"
			def result = userCampusService.created(campusCode, username, pidm, "Rodrigo", "Role Descriptio")
		then: "created"
			assert result
	}

	@Transactional
	def "User campus by code campus and username"(){
		given: "Code campus and username"
			String username = "r.martinez026"
			String codeCampus = "CMX"
		when: "get user campus"
			def result = userCampusService.findByCampusCodeAndUserName(codeCampus, username)
		then:
			result
	}

}