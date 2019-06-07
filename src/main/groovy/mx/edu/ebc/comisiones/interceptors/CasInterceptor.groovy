package mx.edu.ebc.comisiones.interceptor

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import java.security.Principal
import org.springframework.beans.factory.annotation.Value
import mx.edu.ebc.comisiones.services.RestConnectionService
import org.springframework.security.core.userdetails.UserDetailsService
import mx.edu.ebc.comisiones.services.PersonService
import mx.edu.ebc.comisiones.pojos.Profile
import mx.edu.ebc.comisiones.pojos.EbcUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.SessionAttributes
import org.springframework.web.bind.annotation.ModelAttribute;

@Component
class CasInterceptor implements HandlerInterceptor {

  @Value('${url.apibannerseguridad}')
  String clientApiBannerSeguridad
  String roles = "v2/api/user/profile"
  String profiles = "v2/api/user/role"
  @Autowired
  RestConnectionService restConnectionService
  @Autowired
  PersonService personService

  @Override
  boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
     def userName = request.getUserPrincipal().getUserDetails()
     def session = request.getSession()
     (session.getAttribute("ebcUser")) ? 'Nothing' : addUserEbcTosession(userName.username, session)
     System.out.println("Pre Handle method is Calling");
     return true;
  }

  void addUserEbcTosession(String userName, def session){
    session.setAttribute("ebcUser", new EbcUser(
      profiles: personService.findPersonByUsernameAndPortalName(userName, "Pago%20de%20comisiones"),
      menus: personService.getMenusToPerson(userName)
    ))
  }

}
