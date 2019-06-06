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

@RequestMapping("/cas")
@Controller
class CallBackController {

	@Value('${url.apibannerseguridad}')
	String clientApiBannerSeguridad
  String roles = "v2/api/user/profile"
  String profiles = "v2/api/user/role"

  @RequestMapping("/login")
  String getEbcUserFromCas(Principal principal){
		def usuarioCas = ((CasAuthenticationToken) principal).getUserDetails()
    println "*"*100
    println usuarioCas.dump()
    return "redirect:/"
  }

  @RequestMapping("/")
  @ResponseBody
  public ModelAndView authorization() {
		ModelAndView model = new ModelAndView("index");
		model.addObject("content", "authorization");
		//def list = adminDeComisionesRepository.findAll()
		return model
  }

}