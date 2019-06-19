package mx.edu.ebc.comisiones.comision.repo

import org.springframework.stereotype.Repository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestPropertySource
import mx.edu.ebc.comisiones.seguridad.repo.*
import org.springframework.test.context.ContextConfiguration
import mx.edu.ebc.comisiones.comision.domain.UserCampus
import spock.lang.Specification
import spock.lang.Ignore
import org.springframework.transaction.annotation.Transactional
import mx.edu.ebc.comisiones.comision.storedProcedure.AutorizacionComisionesStoredProcedure

@SpringBootTest
@ContextConfiguration
@TestPropertySource("classpath:application-dev.properties")
class CampaignRepositorySpec extends Specification{

	@Autowired
  CampaignRepository campaingRepository

  def "Spect 000 Check service inject"() {
    when:
    println campaingRepository
    then:
    assert campaingRepository
  }

	def "get all campaing register"(){
		when:
			def result = campaingRepository.findAll()
			println result[0].dump()
		then:
			assert result
			false
	}

}
