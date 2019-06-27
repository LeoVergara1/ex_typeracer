package mx.edu.ebc.comisiones.comision.repo

import org.springframework.stereotype.Repository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestPropertySource
import mx.edu.ebc.comisiones.seguridad.repo.*
import org.springframework.test.context.ContextConfiguration
import mx.edu.ebc.comisiones.comision.domain.UserCampus
import mx.edu.ebc.comisiones.comision.domain.Campaign
import spock.lang.Specification
import spock.lang.Ignore
import org.springframework.transaction.annotation.Transactional
import mx.edu.ebc.comisiones.comision.storedProcedure.AutorizacionComisionesStoredProcedure
import mx.edu.ebc.comisiones.comision.domain.Trimester
import java.text.SimpleDateFormat

@SpringBootTest
@ContextConfiguration
@TestPropertySource("classpath:application-dev.properties")
class TrimesterRepositorySpec extends Specification{

	@Autowired
  TrimesterRepository trimesterRepository

	@Autowired
  CampaignRepository campaignRepository

  def "Spect 000 Check service inject"() {
    when:
    println campaignRepository
    then:
    assert campaignRepository
  }

	def "get all campaing register"(){
		when:
			def result = trimesterRepository.findAll()
			println result[0].dump()
		then:
			assert result
	}

	@Transactional
	def "save campaing register"(){
		given: "A trimester"
			Trimester trimester = new Trimester(
				name: "Nombre",
				status: "created",
				endDate: new SimpleDateFormat("dd/MM/yyyy").parse("10/10/2000"),
				initDate: new SimpleDateFormat("dd/MM/yyyy").parse("11/11/2020"),
				year: "2019"
			)
		when:
			def result = trimesterRepository.save(trimester)
			println result
		then:
			assert result
	}

	@Transactional
	def "Save new campaign"(){
		given: "A campaign"
			Campaign campaign = new Campaign(
				name: "Nombre",
				status: "created",
				endDate: new SimpleDateFormat("dd/MM/yyyy").parse("10/10/2000"),
				initDate: new SimpleDateFormat("dd/MM/yyyy").parse("11/11/2020"),
				year: "2019"
			)
		when:
			def result = campaignRepository.save(campaign)
			println result
		then:
			assert result
	}

}
