package mx.edu.ebc.comisiones.seguridad.repo

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
class RolesRepositorySpec extends Specification{

	@Autowired
  RolesRepository rolesRepository

  def "Spect 000 Check service inject"() {
    when:
    println rolesRepository
    then:
    assert rolesRepository
  }

	def "guet roles"(){
		when:
			def result = rolesRepository.findAll()
			println "+"*100
			println result
		then:
			result
	}

	def "guet roles by portal"(){
		when:
			def result = rolesRepository.findAllByNidRolPortal("869")
			println "+"*100
			println result
		then:
			result
	}
}
