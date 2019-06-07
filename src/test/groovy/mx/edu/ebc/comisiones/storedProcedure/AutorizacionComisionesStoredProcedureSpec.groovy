package mx.edu.ebc.comisiones.comision.storedProcedure

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
class AutorizacionComisionesStoredProcedureSpec extends Specification{

	@Autowired
	AutorizacionComisionesStoredProcedure autorizacionComisionesStoredProcedure


  def "Spect 000 Check service inject"() {
    when:
    println autorizacionComisionesStoredProcedure
    then:
    assert autorizacionComisionesStoredProcedure
  }

  def "Get payment commission"(){
    given: "A period"
    Date initDate = new Date(Date.parse("1/1/2019"))
    Date finDate = new Date(Date.parse("10/10/2019"))
    Map params = [
      p_fecha_pago_ini: initDate,
      p_fecha_pago_fin: finDate,
      p_campus: "LEO"
    ]
    when:
		Map results  = autorizacionComisionesStoredProcedure.execute(params)
    then:
      results
  }

}