//package mx.edu.ebc.comisiones.components
//
//import org.springframework.stereotype.Component
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.beans.factory.annotation.Value
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.slf4j.Logger
//import org.slf4j.LoggerFactory
//
//@Component
//class NotificationComponent {
//
//	Logger logger = LoggerFactory.getLogger(NotificationComponent.class)
//
//  @Autowired
//  private JavaMailSender javaMailSender;
//
//  def sendNotification(String username){
//    logger.info "Send Email Notification"
//    SimpleMailMessage msg = new SimpleMailMessage();
//    msg.setTo("brandon@makingdevs.com")
//    msg.setSubject("Testing from Spring Boot")
//    msg.setText("Hello World \n Spring Boot Email")
//    javaMailSender.send(msg)
//  }
//}
