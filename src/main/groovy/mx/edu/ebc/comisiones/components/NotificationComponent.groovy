//package mx.edu.ebc.comisiones.components

import org.springframework.stereotype.Component
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Component
class NotificationComponent {

  Logger logger = LoggerFactory.getLogger(NotificationComponent.class)

  @Autowired
  private JavaMailSender javaMailSender;

  def sendNotification(String username){
    logger.info "Sending Email Notification"
    SimpleMailMessage msg = new SimpleMailMessage();
    msg.setFrom("brandon@makingdevs.com")
    msg.setTo(username)
    msg.setSubject("Binvenido a la aplicación")
    msg.setText("Hola muchas gracias por hacer el registro con nosotros\n Saludos")
    javaMailSender.send(msg)
    logger.info "Sent Email Notification"
  }
}
