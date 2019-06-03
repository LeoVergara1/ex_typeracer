package mx.edu.ebc.comisiones.comision.repo

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
import mx.edu.ebc.comisiones.comision.storedProcedure.AutorizacionComisionesStoredProcedure

@SpringBootTest
@ContextConfiguration
@TestPropertySource("classpath:application-dev.properties")
class PaymentComissionRepositorySpec extends Specification{

	@Autowired
  PaymentComissionRepository paymentComissionRepository
	@Autowired
	AutorizacionComisionesStoredProcedure autorizacionComisionesStoredProcedure

  def "Spect 000 Check service inject"() {
    when:
    println paymentComissionRepository
    then:
    assert paymentComissionRepository
  }

	@Ignore
	def "Query to my first stored procedure"(){
		given: "Init date, fin date and campus"
		Date initDate = new Date()
		Date finDate = new Date()
		String campus = "CMX"
		when:
			def result = paymentComissionRepository.procedureName(initDate, finDate, campus)
			println "+"*100
			println result
		then:
			result

	}

	def "Stored procedure in old"(){
		given: ""
		Map parametros = new HashMap();
		parametros.put("p_fecha_pago_ini", new Date());
		parametros.put("p_fecha_pago_fin", new Date());
		parametros.put("p_campus", "CMX");
		when: ""
		Map results  = autorizacionComisionesStoredProcedure.execute(parametros);
		println "*"*100
		println results
		then:
			results

	}



}