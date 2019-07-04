package mx.edu.ebc.comisiones.controllers

import org.springframework.stereotype.Controller
import org.springframework.beans.factory.annotation.Autowired
import mx.edu.ebc.comisiones.pojos.EbcUser
import org.springframework.beans.factory.annotation.Value
import java.security.Principal
import org.springframework.security.cas.authentication.CasAuthenticationToken
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.servlet.ModelAndView
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.servlet.view.RedirectView
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler

@RequestMapping("/cas")
@Controller
class CallBackController {

  @Value('${security.cas.server.url.logout}')
  String casLogout

  @RequestMapping("/login")
  String getEbcUserFromCas(Principal principal){
		def usuarioCas = ((CasAuthenticationToken) principal).getUserDetails()
    return "redirect:/"
  }

  @RequestMapping("/logout")
  @ResponseBody
  RedirectView logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication()
    if (auth != null){
        new SecurityContextLogoutHandler().logout(request, response, auth);
    }
    RedirectView redirectView = new RedirectView();
    redirectView.setUrl(casLogout)
		return redirectView
  }

}