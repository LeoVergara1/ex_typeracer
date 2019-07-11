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
import spock.lang.Unroll
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
	@Autowired
	TrimesterService trimesterService


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
			goal
	}

	def "Check authorization in banner to current status of authorization"(){
		given: "Semester"
			def goal = goalRepository.findAll().first()
			println goal.dump()
		and: "Get trimester by goal"
			Campaign campaign = calculationService.getCampaignByTrimester(goal.trimester)
		and: "Authorizations to update"
			def authorizationsCommissions = calculationService.calculationByGoal(goal, campaign.initDate, campaign.endDate)
			println "Calculados"
			println authorizationsCommissions
			authorizationsCommissions.each(){ println it.dump()}
		when: ""
			def authorizationsUpdated = calculationService.updatingStatusAuthorizations(authorizationsCommissions, campaign.initDate, campaign.endDate, goal.campus)
		then: ""
			authorizationsUpdated
	}

	def "Calculation"(){
		given: "Semester By dates"
		String year = (new Date().year + 1900).toString()
		List<Campaign> campaigns = campaignRepository.findByYearAndStatus(year, "ACTIVE")
		and: "Dates"
    Date initDate = new Date(Date.parse("2019/1/1"))
    Date endDate = new Date(Date.parse("2019/7/1"))
    and: "Smetester"
    Trimester trimester = trimesterRepository.findByInitDateGreaterThanAndEndDateLessThan(initDate, endDate)
    println trimester
    println trimester.goals
		when: "Get campaing"
		Campaign campaign = calculationService.getCampaignByTrimester(trimester)
		then:
		campaign

	}

	@Unroll
	def "1 Buscar trimestre para el calculo"(){
		given: "Rango de fechas para la busqueda"
			String initDate = _initDate
			String endDate = _endDate
		when: "Se obtine el trimeste"
			List<Trimester> trimesters = trimesterService.findByInitDateGreaterThanAndEndDateLessThan(initDate, endDate)
		then: ""
			trimesters.size() == _result
    where:
    _initDate   | _endDate 		 || _result
    "01/01/2019"| "11/07/2019" ||  1
    "01/01/2019"| "11/11/2019" ||  2
	}

	@Unroll
	def "2 obtener la campaña correspondiente al trimestre"(){
		given: "Trimestre"
			Trimester trimester = new Trimester(clave: "20ANYNAME", year: "2019")
		when: "Se obtiene la Campaña"
			Campaign campaign = calculationService.getCampaignByTrimester(trimester)
		then:
			campaign?.class == _result
		where:
		_name 			| _year || _result
		"20ANYNAME" | "2019"|| Campaign
		"40OTHER"		| "2019"|| null
	}

}