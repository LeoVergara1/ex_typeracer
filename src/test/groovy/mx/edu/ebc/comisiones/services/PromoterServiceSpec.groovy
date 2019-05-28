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
class PromoterServiceSpec extends Specification{

	@Autowired
  PromoterService promoterService


  def "Spect 000 Check service inject"() {
    when:
    println promoterService
    then:
    assert promoterService
  }

	def "Create create Promoter"(){
		given: "User Campus to create promoter"
			String username = "r.martinez026"
			Long pidm = 289
			String recrCode = "SUGHS"
		when: "is created"
			def result = promoterService.createPromoter("")
		then: "created"
			assert result
	}

	def "ERROR, This promoter already exists"(){
		given: "User Campus to create promoter"
			String username = "r.martinez026"
			Long pidm = 289
			String recrCode = "SUGHS"
		when: "is created"
			def result = promoterService.createPromoter("")
		then: "created"
			assert result
	}

	def "RROR, Recruiter code already in use"(){
		given: "User Campus to create promoter"
			String username = "r.martinez026"
			Long pidm = 289
			String recrCode = "SUGHS"
		when: "is created"
			def result = promoterService.createPromoter("")
		then: "created"
			assert result
	}

}