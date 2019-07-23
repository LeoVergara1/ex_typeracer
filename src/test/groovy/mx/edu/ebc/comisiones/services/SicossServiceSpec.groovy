package mx.edu.ebc.comisiones.services

import org.springframework.stereotype.Repository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestPropertySource
import mx.edu.ebc.comisiones.comision.repo.*
import org.springframework.test.context.ContextConfiguration
import mx.edu.ebc.comisiones.comision.domain.UserCampus
import spock.lang.Specification
import org.springframework.transaction.annotation.Transactional
import mx.edu.ebc.comisiones.comision.repo.CampaignRepository
import mx.edu.ebc.comisiones.comision.domain.Campaign
import mx.edu.ebc.comisiones.comision.domain.AuthorizationCrescent
import mx.edu.ebc.comisiones.comision.domain.AuthorizationComission

@SpringBootTest
@ContextConfiguration
@TestPropertySource("classpath:application-dev.properties")
class SicossServiceSpec extends Specification{

	@Autowired
  SicossService sicossService
  @Autowired
  CampaignRepository campaignRepository

  def "Spect 000 Check repository"() {
    when:
    println sicossService
    then:
    assert sicossService
  }

  def "get commissiosn crecents and normal"(){
    given: "A campaign"
      Campaign campaign = campaignRepository.findByPeriodAndStatus(20, "ACTIVE")
    when: "Execute method to get commissions by campaing dates"
      def result = sicossService.getCommissionNormalAndCrecients(campaign)
      println result.dump()
    then:
      result
  }

  def "Add one day to promoter with more than one payment in the same day"(){
    given: "Authorizations commisssion"
      List<AuthorizationComission> listAuthorizationComission = getListAuthorizationComission()
    when: "Plus day to promoter"
      def result = sicossService.separetePromoterAndCoordinater(listAuthorizationComission)
      println result.dump()
    then:
      result.size() > 100

  }

  List<AuthorizationComission> getListAuthorizationComission(){
    (1..10).collect(){
      new AuthorizationComission(idCoordinador: "M00203${it}", 
      fechaAutorizado: new Date(), 
      idPromotor: "M00103${it}",
      adPromotor: "AD872",
      adCoordinador: "AD9829")
    }
  }

}