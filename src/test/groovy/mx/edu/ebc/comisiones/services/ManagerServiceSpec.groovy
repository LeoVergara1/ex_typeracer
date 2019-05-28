package mx.edu.ebc.comisiones.services

import org.springframework.stereotype.Repository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestPropertySource
import mx.edu.ebc.comisiones.comision.repo.*
import org.springframework.test.context.ContextConfiguration
import mx.edu.ebc.comisiones.comision.domain.UserCampus
import spock.lang.Specification
import spock.lang.Ignore
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@ContextConfiguration
@TestPropertySource("classpath:application-dev.properties")
class ManagerServiceSpec extends Specification{

	@Autowired
  ManagerService managerService


  def "Spect 000 Check service inject"() {
    when:
    println managerService
    then:
    assert managerService
  }

	@Transactional
	def "created manager"(){
		given: "User Campus to create promoter"
			String username = "r.martinez026"
			Long pidm = 289
			String recrCode = "SUG"
		when: "is created"
			def result = managerService.createManager(username, pidm, recrCode)
		then: "created"
			assert result.message == "Successfuly created..."
	}

	@Ignore
	def "created manager if Exist"(){
		given: "User Campus to create promoter"
			String username = "r.martinez026"
			Long pidm = 289
			String recrCode = "SUG"
		when: "is created"
			def result = managerService.createManager(username, pidm, recrCode)
		then: "created"
			assert result.message == "ERROR, this recruiter code already exists"
	}
}