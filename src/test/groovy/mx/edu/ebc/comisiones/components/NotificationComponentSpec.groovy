package mx.edu.ebc.comisiones.components

import org.springframework.stereotype.Repository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestPropertySource
import mx.edu.ebc.comisiones.comision.repo.*
import org.springframework.test.context.ContextConfiguration
import mx.edu.ebc.comisiones.comision.domain.UserCampus
import mx.edu.ebc.comisiones.comision.domain.Campaign
import mx.edu.ebc.comisiones.comision.domain.Trimester
import mx.edu.ebc.comisiones.comision.domain.Goal
import mx.edu.ebc.comisiones.comision.domain.AuthorizationCrescent
import spock.lang.Specification
import spock.lang.Ignore
import spock.lang.Unroll
import org.springframework.transaction.annotation.Transactional
import mx.edu.ebc.comisiones.comision.domain.AuthorizationComission

@SpringBootTest
@ContextConfiguration
@TestPropertySource("classpath:application-dev.properties")
class NotificationComponentSpec extends Specification{

  @Autowired
  NotificationComponent notificationComponent

  def "Spect 000 Check component inject"() {
    when:
    println notificationComponent
    then:
    assert notificationComponent
  }

  def "Spect 001 Send Notification"() {
    given:
      String username = "brandon@makingdevs.com"
    when:
    println notificationComponent
      def send = notificationComponent.sendNotificationRegisters()
    then:
      assert notificationComponent
  }

  def "Spect 002 build message"(){
    given: 
      def listCommission = createAuthorizations()
      println listCommission.dump()
    when:
      def message = notificationComponent.buildMessageWithComisssions(listCommission)
    then:
      assert message
  }

  def createAuthorizations(){
    (1..10).collect{
      new AuthorizationComission(campus: "QRO", idAlumno: "M00000000", nombreAlumno: "NOMBRE ALUMNO JAJA", comision: "${it}")
    }
  }

}