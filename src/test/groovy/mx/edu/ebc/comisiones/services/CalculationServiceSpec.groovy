package mx.edu.ebc.comisiones.services

import org.springframework.stereotype.Repository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestPropertySource
import mx.edu.ebc.comisiones.comision.repo.*
import org.springframework.test.context.ContextConfiguration
import mx.edu.ebc.comisiones.comision.domain.UserCampus
import mx.edu.ebc.comisiones.comision.domain.Campaign
import mx.edu.ebc.comisiones.comision.domain.Trimester
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
	@Autowired
	GoalRepository goalRepository
  @Autowired
  TrimesterRepository trimesterRepository


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

	def "Init Validations to goal '(meta)'"(){
		given: "Semester"
			def goal = goalRepository.findAll().first()
			println goal.dump()
		when: ""
			def result = calculationService.calculationByGoal(goal, new Date(Date.parse("2019/1/1")), new Date(Date.parse("2019/7/1")))
		then: ""
			result
	}

	def "Check authorization in banner to current status of authorization"(){
		given: "Semester"
			def goal = goalRepository.findAll().first()
			println goal.dump()
		and: "Authorizations to update"	
			def authorizationsCommissions = calculationService.calculationByGoal(goal, new Date(Date.parse("2019/1/1")), new Date(Date.parse("2019/7/1")))
		when: ""
			def authorizationsUpdated = calculationService.updatingStatusAuthorizations(authorizationsCommissions, new Date(Date.parse("2019/1/1")), new Date(Date.parse("2019/7/1")))
		then: ""
			authorizationsUpdated
	}

	def "Calculation"(){
		given: "Semester By dates"
		String year = (new Date().year + 1900).toString()
		List<Campaign> campaign = campaignRepository.findByYearAndStatus(year, "ACTIVE")
		and: "Dates"
    Date initDate = new Date(Date.parse("2019/1/1"))
    Date endDate = new Date(Date.parse("2019/7/1"))
    and: "Smetester"
    Trimester trimester = trimesterRepository.findByInitDateGreaterThanAndEndDateLessThan(initDate, endDate)
    println trimester
    println trimester.goals
		when:
		def result = "0"
		then:
		false

	}

}