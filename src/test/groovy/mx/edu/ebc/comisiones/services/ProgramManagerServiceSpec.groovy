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

@SpringBootTest
@ContextConfiguration
@TestPropertySource("classpath:application-dev.properties")
class ProgramManagerServiceSpec extends Specification{

	@Autowired
  ProgramManagerService programManagerService


  def "Spect 000 Check service inject"() {
    when:
    println programManagerService
    then:
    assert programManagerService
  }

	@Ignore
	def "Find program manager"(){
		given: "by username"
			String username = "vi.mendoza"
		when: "is created"
			def result = programManagerService.findOneById_UserName("vi.mendoza")
		then: "created"
			assert result
	}

	@Ignore
	def "Find program manager by reCred"(){
		given: "by username"
			String recCred = "HG87"
		when: "is created"
			def result = programManagerService.findOneById_RecrCode(recCred)
		then: "created"
			assert result
	}

}