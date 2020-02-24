package mx.edu.ebc.comisiones.components

import org.springframework.stereotype.Component
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value

@Component
class NotificationComponent {

  Logger logger = LoggerFactory.getLogger(NotificationComponent.class)
  @Value('${mail.head.marketing}')
  String mailMarketing
  @Value('${mail.head.coordinator}')
  String mailCoordinator
  @Value('${mail.from}')
  String mailFrom

  @Autowired
  private JavaMailSender javaMailSender;

  def sendNotification(String username){
    logger.info "Sending Email Notification"
    SimpleMailMessage msg = new SimpleMailMessage();
    msg.setFrom(mailFrom)
    msg.setTo(username)
    msg.setSubject("Binvenido a la aplicación")
    msg.setText("Hola muchas gracias por hacer el registro con nosotros\n Saludos")
    javaMailSender.send(msg)
    logger.info "Sent Email Notification"
  }

  def sendNotificationRegisters(def listCommissions){
    logger.info "Sending Email Notification"
    SimpleMailMessage msg = new SimpleMailMessage();
    msg.setFrom(mailFrom)
    msg.setTo(mailCoordinator)
    msg.setSubject("Registro de Comisiones")
    msg.setText("Se han registrado las siguientes notificaciones\n" + buildMessageWithComisssions(listCommissions))
    javaMailSender.send(msg)
    logger.info "Sent Email Notification"
  }

  String buildMessageWithComisssions(def listCommissions){
    String message = ""
    listCommissions.each{ commission ->
       message = message << "${commission.campus}/${commission.idAlumno}/${commission.nombreAlumno}/\$ ${commission.comision}\n"
    }
    message
  }
}
