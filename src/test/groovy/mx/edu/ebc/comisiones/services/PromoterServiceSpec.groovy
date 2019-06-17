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
import mx.edu.ebc.comisiones.pojos.Person

@SpringBootTest
@ContextConfiguration
@TestPropertySource("classpath:application-dev.properties")
class PromoterServiceSpec extends Specification{

	@Autowired
  PromoterService promoterService
	@Autowired
	PersonService personService


  def "Spect 000 Check service inject"() {
    when:
    println promoterService
    then:
    assert promoterService
  }

	@Transactional
	def "Create create Promoter"(){
		given: "User Campus to create promoter"
			String username = "r.martinez026"
			Long pidm = 289
			String recrCode = "SUG"
		when: "is created"
			def person = personService.findPersonByUsername(username)
			def result = promoterService.createPromoter(person, recrCode)
		then: "created"
			assert result.message == "Successfuly created..."
	}

	@Ignore
	def "ERROR, This promoter already exists"(){
		given: "User Campus to create promoter"
			String username = "r.martinez026"
			Long pidm = 289
			String recrCode = "SUGH"
		when: "is created"
			def person = personService.findPersonByUsername(username)
			def result = promoterService.createPromoter(person,recrCode)
		then: "created"
			assert result.message == "ERROR, This promoter already exists"
	}

	@Transactional
	def "RROR, Recruiter code already in use"(){
		given: "User Campus to create promoter"
			String username = "r.martinez026"
			Long pidm = 289
			String recrCode = "SUGHS"
		when: "is created"
			def person = personService.findPersonByUsername(username)
			def result = promoterService.createPromoter(person,recrCode)
		then: "created"
			assert result
	}

}