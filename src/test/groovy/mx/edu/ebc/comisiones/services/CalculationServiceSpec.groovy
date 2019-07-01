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
class CalculationServiceSpec extends Specification{

	@Autowired
  CalculationService calculationService
	@Autowired
	CampaignRepository campaignRepository


  def "Spect 000 Check service inject"() {
    when:
    println calculationService
    then:
    assert calculationService
  }

	def "Get Authorization by semester"(){
		given: "Semester"
			def campaign = campaignRepository.findByPeriodAndStatus(20, "ACTIVE")
		when: ""
			def result = calculationService.getAuthorizationsByCampaign(campaign)
		then: ""
			result


	}

}