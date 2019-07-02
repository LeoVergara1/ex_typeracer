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
class AuthorizationRepositorySpec extends Specification{

	@Autowired
  AuthorizationRepository authorizationRepository

  def "Spect 000 Check service inject"() {
    when:
    println authorizationRepository
    then:
    assert authorizationRepository
  }

	def "guet roles"(){
		when:
			def result = authorizationRepository.findAll()
			println "+"*100
			println result
		then:
			result
	}

	@Ignore
	def "findByIdPromotorAndIdCoordinadorAndIdAlumno"(){
		when:
			def result = authorizationRepository.findByIdPromotorAndIdCoordinadorAndIdAlumno("M00253895","M00130851","M00994209")
			println "+"*100
			println result
		then:
			result
	}

}
