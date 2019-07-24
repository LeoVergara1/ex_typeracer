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
import spock.lang.Unroll
import java.time.LocalDate

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
      result.size() == 20
  }

  def "when there duplicate then plus one day"(){
    given: "Authorizations commisssion"
      List<AuthorizationComission> listAuthorizationComission = getListAuthorizationComission()
      listAuthorizationComission << new AuthorizationComission(idCoordinador: "M0020323", 
      fechaAutorizado: new Date(),
      idPromotor: "M001030",
      adPromotor: "AD8721",
      adCoordinador: "AD9829",
      comision: "2",
      comisionCoordinador: 32
      )
    when: "Plus day to promoter"
      def result = sicossService.separetePromoterAndCoordinater(listAuthorizationComission)
      def duplicates = sicossService.plusOneDayThanMoreClavesSames(result)
      def validation = duplicates.findAll(){ it.claveEmployee == "8721"}
    then:
      duplicates.size() == 22
      validation[0].dateMovenment != validation[1].dateMovenment
  }

	@Unroll
  def "calculate quincena from date"(){
    given: "A date"
      LocalDate date = _currentDate
    when: ""
      def result = sicossService.calculateQuincena(date)
    then:
      result == _result 
    where:
    _currentDate                  || _result
    LocalDate.now()               || 14
    LocalDate.parse("2007-12-03") || 23
  }

  def "Save list of sicoss"(){
    given: "A list with calculations"
      List<AuthorizationComission> listAuthorizationComission = getListAuthorizationComission()
      listAuthorizationComission << new AuthorizationComission(idCoordinador: "M0020323", 
      fechaAutorizado: new Date(),
      idPromotor: "M001030",
      adPromotor: "AD8721",
      adCoordinador: "AD9829",
      comision: "2",
      comisionCoordinador: 32
      )
      when:
        def result = sicossService.separetePromoterAndCoordinater(listAuthorizationComission)
        def duplicates = sicossService.plusOneDayThanMoreClavesSames(result)
        sicossService.saveSicossList(duplicates)
      then:
        duplicates

  }

  List<AuthorizationComission> getListAuthorizationComission(){
    (1..10).collect(){
      new AuthorizationComission(idCoordinador: "M00203${it}", 
      fechaAutorizado: new Date(), 
      idPromotor: "M00103${it}",
      adPromotor: "AD872${it}",
      adCoordinador: "AD9829${it}",
      comision: "2",
      comisionCoordinador: 32
      )
    }
  }

}